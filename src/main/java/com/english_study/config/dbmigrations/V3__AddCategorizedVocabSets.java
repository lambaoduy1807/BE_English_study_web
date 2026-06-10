package com.english_study.config.dbmigrations;

import com.english_study.model.entity.VocabSetEntity;
import com.english_study.model.entity.Word;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ChangeUnit(id = "add-categorized-vocab-sets", order = "005", author = "Admin")
public class V3__AddCategorizedVocabSets {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        mongoTemplate.remove(new Query(), VocabSetEntity.class);
        mongoTemplate.remove(new Query(), Word.class);

        // ==================== 1. TẠO VOCAB SETS ====================
        // Nhóm IELTS (categoryID = 1)
        VocabSetEntity ielts1 = VocabSetEntity.builder().id("c_i1").title("IELTS Band 6.0").numOfWords(10).icon("menu_book").categoryID(1).build();
        VocabSetEntity ielts2 = VocabSetEntity.builder().id("c_i2").title("IELTS Advanced").numOfWords(10).icon("school").categoryID(1).build();

        // Nhóm TOEIC (categoryID = 2)
        VocabSetEntity toeic1 = VocabSetEntity.builder().id("c_t1").title("TOEIC 500 Basic").numOfWords(10).icon("work").categoryID(2).build();
        VocabSetEntity toeic2 = VocabSetEntity.builder().id("c_t2").title("TOEIC 850+").numOfWords(10).icon("trending_up").categoryID(2).build();

        // Nhóm Level (categoryID = 3)
        VocabSetEntity lvl1 = VocabSetEntity.builder().id("c_l1").title("B1 Intermediate").numOfWords(10).icon("stairs").categoryID(3).build();
        VocabSetEntity lvl2 = VocabSetEntity.builder().id("c_l2").title("A2 Foundation").numOfWords(10).icon("flight_takeoff").categoryID(3).build();

        // Nhóm Chuyên ngành (categoryID = 4)
        VocabSetEntity spec1 = VocabSetEntity.builder().id("c_s1").title("Information Technology").numOfWords(10).icon("computer").categoryID(4).build();
        VocabSetEntity spec2 = VocabSetEntity.builder().id("c_s2").title("Plant Pathology").numOfWords(10).icon("eco").categoryID(4).build();
        VocabSetEntity spec3 = VocabSetEntity.builder().id("c_s3").title("Commercial Law").numOfWords(10).icon("gavel").categoryID(4).build();

        mongoTemplate.insertAll(Arrays.asList(ielts1, ielts2, toeic1, toeic2, lvl1, lvl2, spec1, spec2, spec3));

        // ==================== 2. TẠO WORDS CHO TỪNG BỘ ====================
        List<Word> allWords = new ArrayList<>();

        // 1. Words cho IELTS Band 6.0 (c_i1)
        allWords.addAll(Arrays.asList(
                createWord("Analyze", "Phân tích", "Verb", "We need to analyze the data.", "c_i1"),
                createWord("Approach", "Phương pháp, tiếp cận", "Noun/Verb", "This is a new approach to the problem.", "c_i1"),
                createWord("Benefit", "Lợi ích", "Noun", "The benefits of exercise are clear.", "c_i1"),
                createWord("Context", "Ngữ cảnh", "Noun", "You must understand the historical context.", "c_i1"),
                createWord("Environment", "Môi trường", "Noun", "We must protect the environment.", "c_i1"),
                createWord("Factor", "Yếu tố", "Noun", "Price is a major factor in our decision.", "c_i1"),
                createWord("Function", "Chức năng", "Noun", "What is the function of this button?", "c_i1"),
                createWord("Identify", "Nhận dạng", "Verb", "Can you identify the suspect?", "c_i1"),
                createWord("Method", "Phương pháp", "Noun", "They developed a new testing method.", "c_i1"),
                createWord("Process", "Quá trình", "Noun", "Learning a language is a slow process.", "c_i1")
        ));

        // 2. Words cho IELTS Advanced (c_i2)
        allWords.addAll(Arrays.asList(
                createWord("Ambiguous", "Mơ hồ", "Adj", "The ending of the movie was ambiguous.", "c_i2"),
                createWord("Fluctuate", "Dao động", "Verb", "Prices fluctuate according to demand.", "c_i2"),
                createWord("Paradigm", "Mô hình, kiểu mẫu", "Noun", "There is a new paradigm in education.", "c_i2"),
                createWord("Qualitative", "Định tính", "Adj", "We conducted a qualitative research study.", "c_i2"),
                createWord("Subsequent", "Tiếp theo", "Adj", "Subsequent events proved him wrong.", "c_i2"),
                createWord("Synthesize", "Tổng hợp", "Verb", "The report synthesizes data from many sources.", "c_i2"),
                createWord("Tangible", "Hữu hình, rõ ràng", "Adj", "We need tangible evidence.", "c_i2"),
                createWord("Validate", "Xác nhận hợp lệ", "Verb", "The results validate our hypothesis.", "c_i2"),
                createWord("Empirical", "Dựa trên thực nghiệm", "Adj", "There is no empirical evidence to support this.", "c_i2"),
                createWord("Holistic", "Toàn diện", "Adj", "We need a holistic approach to healthcare.", "c_i2")
        ));

        // 3. Words cho TOEIC 500 (c_t1)
        allWords.addAll(Arrays.asList(
                createWord("Applicant", "Người nộp đơn", "Noun", "The successful applicant will start next week.", "c_t1"),
                createWord("Colleague", "Đồng nghiệp", "Noun", "She is a trusted colleague.", "c_t1"),
                createWord("Deadline", "Hạn chót", "Noun", "We must meet the Friday deadline.", "c_t1"),
                createWord("Equipment", "Thiết bị", "Noun", "The office needs new equipment.", "c_t1"),
                createWord("Interview", "Phỏng vấn", "Noun", "I have a job interview tomorrow.", "c_t1"),
                createWord("Manager", "Quản lý", "Noun", "The manager approved the budget.", "c_t1"),
                createWord("Office", "Văn phòng", "Noun", "He left the office at 5 PM.", "c_t1"),
                createWord("Schedule", "Lịch trình", "Noun", "Let's check the train schedule.", "c_t1"),
                createWord("Task", "Nhiệm vụ", "Noun", "Your first task is to organize the files.", "c_t1"),
                createWord("Update", "Cập nhật", "Verb", "Please update the software.", "c_t1")
        ));

        // 4. Words cho TOEIC 850+ (c_t2)
        allWords.addAll(Arrays.asList(
                createWord("Accommodate", "Đáp ứng, cung cấp chỗ ở", "Verb", "The hotel can accommodate up to 500 guests.", "c_t2"),
                createWord("Authorize", "Ủy quyền", "Verb", "Only the manager can authorize this payment.", "c_t2"),
                createWord("Compensate", "Bồi thường", "Verb", "We will compensate you for the delay.", "c_t2"),
                createWord("Implement", "Triển khai", "Verb", "They plan to implement the new policy next month.", "c_t2"),
                createWord("Negotiate", "Thương lượng", "Verb", "We are trying to negotiate a better deal.", "c_t2"),
                createWord("Preliminary", "Sơ bộ", "Adj", "The preliminary results look promising.", "c_t2"),
                createWord("Requisite", "Điều kiện tất yếu", "Adj", "A degree is requisite for this position.", "c_t2"),
                createWord("Subcontractor", "Nhà thầu phụ", "Noun", "They hired a subcontractor to finish the work.", "c_t2"),
                createWord("Unprecedented", "Chưa từng có", "Adj", "The company experienced unprecedented growth.", "c_t2"),
                createWord("Yield", "Mang lại, sinh lời", "Verb", "The investment yielded a high return.", "c_t2")
        ));

        // 5. Words cho B1 Intermediate (c_l1)
        allWords.addAll(Arrays.asList(
                createWord("Achieve", "Đạt được", "Verb", "She worked hard to achieve her goals.", "c_l1"),
                createWord("Challenge", "Thử thách", "Noun", "This project is a big challenge.", "c_l1"),
                createWord("Describe", "Miêu tả", "Verb", "Can you describe the man you saw?", "c_l1"),
                createWord("Encourage", "Khuyến khích", "Verb", "My parents always encourage me.", "c_l1"),
                createWord("Improve", "Cải thiện", "Verb", "I want to improve my English speaking skills.", "c_l1"),
                createWord("Opportunity", "Cơ hội", "Noun", "This is a great opportunity for you.", "c_l1"),
                createWord("Participate", "Tham gia", "Verb", "Everyone should participate in the discussion.", "c_l1"),
                createWord("Recognize", "Nhận ra", "Verb", "I didn't recognize you in that hat.", "c_l1"),
                createWord("Suggest", "Đề nghị", "Verb", "I suggest we take a break.", "c_l1"),
                createWord("Valuable", "Có giá trị", "Adj", "This painting is very valuable.", "c_l1")
        ));

        // 6. Words cho A2 Foundation (c_l2)
        allWords.addAll(Arrays.asList(
                createWord("Airport", "Sân bay", "Noun", "We arrived at the airport early.", "c_l2"),
                createWord("Ticket", "Vé", "Noun", "Can I see your train ticket?", "c_l2"),
                createWord("Luggage", "Hành lý", "Noun", "Where is your luggage?", "c_l2"),
                createWord("Restaurant", "Nhà hàng", "Noun", "We ate at a nice restaurant yesterday.", "c_l2"),
                createWord("Hotel", "Khách sạn", "Noun", "The hotel room was very clean.", "c_l2"),
                createWord("Weather", "Thời tiết", "Noun", "The weather is lovely today.", "c_l2"),
                createWord("Journey", "Chuyến đi", "Noun", "It was a long journey by bus.", "c_l2"),
                createWord("Money", "Tiền", "Noun", "I don't have enough money.", "c_l2"),
                createWord("Clothes", "Quần áo", "Noun", "I need to buy some new clothes.", "c_l2"),
                createWord("Camera", "Máy ảnh", "Noun", "He took a picture with his camera.", "c_l2")
        ));

        // 7. Words cho Information Technology (c_s1)
        allWords.addAll(Arrays.asList(
                createWord("Algorithm", "Thuật toán", "Noun", "The search algorithm needs optimization.", "c_s1"),
                createWord("Database", "Cơ sở dữ liệu", "Noun", "We use MongoDB as our primary database.", "c_s1"),
                createWord("Framework", "Khung lập trình", "Noun", "Spring Boot is a popular Java framework.", "c_s1"),
                createWord("Frontend", "Giao diện người dùng", "Noun", "React is widely used for frontend development.", "c_s1"),
                createWord("Backend", "Phần xử lý máy chủ", "Noun", "He is working on the backend APIs.", "c_s1"),
                createWord("Repository", "Kho lưu trữ code", "Noun", "Push your commits to the Git repository.", "c_s1"),
                createWord("Deployment", "Triển khai hệ thống", "Noun", "The deployment to the live server was successful.", "c_s1"),
                createWord("Variable", "Biến", "Noun", "Define a variable to store the data.", "c_s1"),
                createWord("Syntax", "Cú pháp", "Noun", "You have a syntax error on line 5.", "c_s1"),
                createWord("Authentication", "Xác thực", "Noun", "We use JWT for user authentication.", "c_s1")
        ));

        // 8. Words cho Plant Pathology (c_s2)
        allWords.addAll(Arrays.asList(
                createWord("Pathogen", "Mầm bệnh", "Noun", "Identifying the specific pathogen is crucial.", "c_s2"),
                createWord("Inoculation", "Chủng bệnh, cấy truyền", "Noun", "The inoculation process was done in the lab.", "c_s2"),
                createWord("Fungi", "Nấm", "Noun", "Many plant diseases are caused by fungi.", "c_s2"),
                createWord("Fusarium", "Nấm héo rũ", "Noun", "Fusarium oxysporum attacks the roots.", "c_s2"),
                createWord("Diaporthe", "Nấm thối quả", "Noun", "Diaporthe can cause severe damage to crops.", "c_s2"),
                createWord("Rhizoctonia", "Nấm lở cổ rễ", "Noun", "Rhizoctonia solani is a soil-borne pathogen.", "c_s2"),
                createWord("Lesion", "Vết bệnh", "Noun", "Brown lesions appeared on the leaves.", "c_s2"),
                createWord("Mycelium", "Hệ sợi nấm", "Noun", "White mycelium covered the agar plate.", "c_s2"),
                createWord("Spore", "Bào tử", "Noun", "Fungal spores are spread by the wind.", "c_s2"),
                createWord("Virulence", "Độc lực", "Noun", "The virulence of this strain is very high.", "c_s2")
        ));

        // 9. Words cho Commercial Law (c_s3)
        allWords.addAll(Arrays.asList(
                createWord("Contract", "Hợp đồng", "Noun", "Read the contract carefully before signing.", "c_s3"),
                createWord("Liability", "Trách nhiệm pháp lý", "Noun", "The company denied any liability.", "c_s3"),
                createWord("E-commerce", "Thương mại điện tử", "Noun", "E-commerce platforms are growing rapidly.", "c_s3"),
                createWord("Copyright", "Bản quyền", "Noun", "You cannot use this image due to copyright laws.", "c_s3"),
                createWord("Transaction", "Giao dịch", "Noun", "The online transaction was completed securely.", "c_s3"),
                createWord("Dispute", "Tranh chấp", "Noun", "They are involved in a legal dispute.", "c_s3"),
                createWord("Shareholder", "Cổ đông", "Noun", "The shareholders approved the merger.", "c_s3"),
                createWord("Bankruptcy", "Phá sản", "Noun", "The business was forced to declare bankruptcy.", "c_s3"),
                createWord("Compliance", "Sự tuân thủ", "Noun", "Compliance with the new regulations is mandatory.", "c_s3"),
                createWord("Trademark", "Nhãn hiệu", "Noun", "The logo is a registered trademark.", "c_s3")
        ));

        mongoTemplate.insertAll(allWords);
    }

    private Word createWord(String word, String mean, String type, String example, String vocabID) {
        return Word.builder()
                .word(word)
                .mean(mean)
                .type(type)
                .example(example)
                .vocabID(vocabID)
                .build();
    }

    @RollbackExecution
    public void rollbackExecution(MongoTemplate mongoTemplate) {
        mongoTemplate.remove(new Query(), VocabSetEntity.class);
        mongoTemplate.remove(new Query(), Word.class);
    }
}