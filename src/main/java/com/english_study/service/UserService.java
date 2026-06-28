package com.english_study.service;

import com.english_study.exception.UserAlreadyExistsException;
import com.english_study.exception.UserNotFoundException;
// Khuyến nghị tạo thêm InvalidCredentialsException cho lỗi sai mật khẩu
import com.english_study.mapper.UserMapper;
import com.english_study.model.dto.UserDTO;
import com.english_study.model.entity.UserEntity;
import com.english_study.model.request.UpdateProfileRequest;
import com.english_study.repository.UserRepository;
import com.english_study.repository.UserDailyStatRepository;
import com.english_study.model.entity.UserDailyStat;
import com.english_study.model.entity.UserVocabSet;
import com.english_study.model.response.UserStatsResponse;
import com.english_study.repository.UserVocabSetRepository;
import com.english_study.repository.UserStreakRepository;
import com.english_study.model.entity.UserStreak;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserVocabSetRepository userVocabSetRepository;
    private final UserStreakRepository userStreakRepository;
    private final UserDailyStatRepository userDailyStatRepository;

    public UserDTO checkUser(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);

        if (user == null || (user.getPassword() != null && !passwordEncoder.matches(password, user.getPassword()))) {
            throw new UserNotFoundException("Sai tên đăng nhập hoặc mật khẩu");
        }

        if ("GOOGLE".equals(user.getAuthProvider()) && user.getPassword() == null) {
            throw new RuntimeException("Tài khoản này được đăng ký qua Google, vui lòng đăng nhập bằng Google.");
        }

        if (!user.isEmailVerified() && "LOCAL".equals(user.getAuthProvider())) {
            throw new RuntimeException("Vui lòng kiểm tra email để xác thực tài khoản trước khi đăng nhập.");
        }

        if (user.isDelete()) {
            throw new RuntimeException("Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên.");
        }

        return userMapper.toUserDTO(user);
    }

    public UserDTO updateUserRole(String userId, String roleId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setRoleId(roleId);
        return userMapper.toUserDTO(userRepository.save(user));
    }

    public UserDTO toggleUserBlock(String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setDelete(!user.isDelete()); // toggle block state
        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Transactional
    public void saveRefreshToken(UserDTO userDTO, String refreshToken) {
        userRepository.updateRefreshtoken(userDTO.getId(),refreshToken);
    }

    @Transactional
    public UserDTO create(String email, String fullName, String name, String password, String role, String verificationToken) {
        String normalizedEmail = email != null ? email.trim().toLowerCase() : null;
        String normalizedName = name != null ? name.trim().toLowerCase() : null;

        if (userRepository.existsByUsername(normalizedName)) {
            throw new UserAlreadyExistsException("Tên đăng nhập đã tồn tại!");
        }
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new UserAlreadyExistsException("Email đã được sử dụng!");
        }

        UserEntity user = new UserEntity(normalizedName, normalizedEmail, passwordEncoder.encode(password), fullName, role);
        user.setVerificationToken(verificationToken);
        user.setAuthProvider("LOCAL");
        user.setEmailVerified(false);
        user = userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Transactional
    public UserDTO createGoogleUser(String email, String fullName, String avatar) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email đã được sử dụng!");
        }
        
        // Tạo username từ email (bỏ phần @...)
        String username = email.split("@")[0] + "_" + System.currentTimeMillis();

        UserEntity user = new UserEntity(username, email, null, fullName, com.english_study.model.role.Role.USER.getAuthority());
        user.setAvatar(avatar);
        user.setAuthProvider("GOOGLE");
        user.setEmailVerified(true);
        user = userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    public UserDTO getUserByID(String id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserDTO updateUser(String userID, UpdateProfileRequest updateRequest) {
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setEmail(updateRequest.email());
        user.setLevel(updateRequest.level());
        user.setFullName(updateRequest.name());
        return userMapper.toUserDTO(userRepository.save(user));
    }

    public UserDTO updateAvatar(String userID, String avatarUrl) {
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setAvatar(avatarUrl);
        return userMapper.toUserDTO(userRepository.save(user));
    }

    public UserStatsResponse getUserStats(String userID) {
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        
        List<UserVocabSet> vocabSets = userVocabSetRepository.findByUserID(userID);
        int totalLearnedWords = 0;
        if (vocabSets != null) {
            for (UserVocabSet set : vocabSets) {
                totalLearnedWords += set.getLearningProgress();
            }
        }
        int currentStreak = 0;
        int longestStreak = 0;
        UserStreak userStreak = userStreakRepository.findByUserId(userID).orElse(null);
        if (userStreak != null) {
            currentStreak = userStreak.getCurrentStreak();
            longestStreak = userStreak.getLongestStreak();
        }
        
        int totalWordToday = userDailyStatRepository
                .findByUserIdAndDate(userID, java.time.LocalDate.now())
                .map(UserDailyStat::getNumMemorizeNew)
                .orElse(0);
        
        return UserStatsResponse.builder()
                .totalLearnedWords(totalLearnedWords)
                .currentStreak(currentStreak)
                .longestStreak(longestStreak)
                .totalWordToday(totalWordToday)
                .totalXp(user.getTotalXP())
                .level(user.getLevel())
                .rank(user.getRank())
                .build();
    }

    public UserDTO addXP(String userID, int xp) {
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setTotalXP(user.getTotalXP() + xp);
        return userMapper.toUserDTO(userRepository.save(user));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .toList();
    }
}