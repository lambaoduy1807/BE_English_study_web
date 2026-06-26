package com.english_study.service;

import com.english_study.exception.UserAlreadyExistsException;
import com.english_study.exception.UserNotFoundException;
// Khuyến nghị tạo thêm InvalidCredentialsException cho lỗi sai mật khẩu
import com.english_study.mapper.UserMapper;
import com.english_study.model.dto.UserDTO;
import com.english_study.model.entity.UserEntity;
import com.english_study.model.request.UpdateProfileRequest;
import com.english_study.model.response.ApiResponse;
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
import org.springframework.transaction.annotation.Transactional;

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
        UserEntity user = userRepository.findByName(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException("Sai tên đăng nhập hoặc mật khẩu");
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
    public UserDTO create(String email, String fullName, String name, String password, String role) {
        if (userRepository.existsByName(name)) {
            throw new UserAlreadyExistsException("Tên đăng nhập đã tồn tại!");
        }

        UserEntity user = new UserEntity(name, email, passwordEncoder.encode(password), fullName, role);
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