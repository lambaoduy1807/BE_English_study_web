package com.english_study.config.dbmigrations;

import com.english_study.model.entity.VocabSetEntity;
import com.english_study.model.entity.Word;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ChangeUnit(id = "add-more-categorized-vocab-sets", order = "006", author = "Admin")
public class V4__AddMoreCategorizedVocabSets {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        // ==================== 1. TẠO THÊM VOCAB SETS MỚI ====================
        // Nhóm 1: IELTS (Thêm 3 bộ)
        VocabSetEntity ielts3 = VocabSetEntity.builder().id("c_i3").title("IELTS Writing").numOfWords(10).icon("edit_document").categoryID(1).build();
        VocabSetEntity ielts4 = VocabSetEntity.builder().id("c_i4").title("IELTS Speaking").numOfWords(10).icon("record_voice_over").categoryID(1).build();
        VocabSetEntity ielts5 = VocabSetEntity.builder().id("c_i5").title("IELTS Academic").numOfWords(10).icon("science").categoryID(1).build();

        // Nhóm 2: TOEIC (Thêm 3 bộ)
        VocabSetEntity toeic3 = VocabSetEntity.builder().id("c_t3").title("Human Resources").numOfWords(10).icon("groups").categoryID(2).build();
        VocabSetEntity toeic4 = VocabSetEntity.builder().id("c_t4").title("Finance & Accounting").numOfWords(10).icon("payments").categoryID(2).build();
        VocabSetEntity toeic5 = VocabSetEntity.builder().id("c_t5").title("Marketing & Sales").numOfWords(10).icon("campaign").categoryID(2).build();

        // Nhóm 3: Level (Thêm 3 bộ)
        VocabSetEntity lvl3 = VocabSetEntity.builder().id("c_l3").title("C1 Advanced").numOfWords(10).icon("workspace_premium").categoryID(3).build();
        VocabSetEntity lvl4 = VocabSetEntity.builder().id("c_l4").title("B2 Upper-Int").numOfWords(10).icon("trending_up").categoryID(3).build();
        VocabSetEntity lvl5 = VocabSetEntity.builder().id("c_l5").title("A1 Beginner").numOfWords(10).icon("child_care").categoryID(3).build();

        // Nhóm 4: Chuyên ngành (Thêm 3 bộ thiết thực)
        VocabSetEntity spec4 = VocabSetEntity.builder().id("c_s4").title("Graphic Design").numOfWords(10).icon("brush").categoryID(4).build();
        VocabSetEntity spec5 = VocabSetEntity.builder().id("c_s5").title("Motorcycle Mechanics").numOfWords(10).icon("two_wheeler").categoryID(4).build();
        VocabSetEntity spec6 = VocabSetEntity.builder().id("c_s6").title("Driving License (B2/C)").numOfWords(10).icon("directions_car").categoryID(4).build();

        mongoTemplate.insertAll(Arrays.asList(
                ielts3, ielts4, ielts5, toeic3, toeic4, toeic5, lvl3, lvl4, lvl5, spec4, spec5, spec6
        ));

        // ==================== 2. TẠO WORDS CHO CÁC BỘ MỚI ====================
        List<Word> allWords = new ArrayList<>();

        // IELTS Writing (c_i3)
        allWords.addAll(Arrays.asList(
                createWord("Illustrate", "Minh họa", "Verb", "The graph illustrates the trend.", "c_i3"),
                createWord("Proportion", "Tỷ lệ", "Noun", "A large proportion of the budget was spent.", "c_i3"),
                createWord("Respectively", "Tương ứng", "Adv", "They scored 80 and 90, respectively.", "c_i3"),
                createWord("Peak", "Đạt đỉnh", "Verb/Noun", "Sales peaked in December.", "c_i3"),
                createWord("Decline", "Suy giảm", "Verb", "The number declined gradually.", "c_i3"),
                createWord("Fluctuate", "Dao động", "Verb", "The temperature fluctuates greatly.", "c_i3"),
                createWord("Overall", "Nhìn chung", "Adv", "Overall, the trend is upward.", "c_i3"),
                createWord("Feature", "Đặc điểm", "Noun", "Notice the key features of the map.", "c_i3"),
                createWord("Trend", "Xu hướng", "Noun", "There is a downward trend in prices.", "c_i3"),
                createWord("Significantly", "Đáng kể", "Adv", "The rate dropped significantly.", "c_i3")
        ));

        // IELTS Speaking (c_i4)
        allWords.addAll(Arrays.asList(
                createWord("Hobby", "Sở thích", "Noun", "My favorite hobby is reading.", "c_i4"),
                createWord("Hometown", "Quê hương", "Noun", "I come from a quiet hometown.", "c_i4"),
                createWord("Memorable", "Đáng nhớ", "Adj", "It was a memorable trip.", "c_i4"),
                createWord("Ambition", "Hoài bão", "Noun", "His ambition is to become a doctor.", "c_i4"),
                createWord("Fascinating", "Hấp dẫn", "Adj", "That is a fascinating story.", "c_i4"),
                createWord("Routine", "Thói quen hàng ngày", "Noun", "I stick to my morning routine.", "c_i4"),
                createWord("Leisure", "Thời gian rảnh", "Noun", "What do you do in your leisure time?", "c_i4"),
                createWord("Generation", "Thế hệ", "Noun", "The older generation thinks differently.", "c_i4"),
                createWord("Traditional", "Truyền thống", "Adj", "We wore traditional clothes.", "c_i4"),
                createWord("Environment", "Môi trường", "Noun", "I care about the environment.", "c_i4")
        ));

        // IELTS Academic (c_i5)
        allWords.addAll(Arrays.asList(
                createWord("Hypothesis", "Giả thuyết", "Noun", "They tested the hypothesis.", "c_i5"),
                createWord("Theory", "Lý thuyết", "Noun", "Einstein developed the theory of relativity.", "c_i5"),
                createWord("Evidence", "Bằng chứng", "Noun", "There is no evidence to support this.", "c_i5"),
                createWord("Lecture", "Bài giảng", "Noun", "The professor gave a lecture on history.", "c_i5"),
                createWord("Thesis", "Luận văn", "Noun", "I am writing my graduation thesis.", "c_i5"),
                createWord("Evaluate", "Đánh giá", "Verb", "We need to evaluate the results.", "c_i5"),
                createWord("Concept", "Khái niệm", "Noun", "It is a difficult concept to grasp.", "c_i5"),
                createWord("Principle", "Nguyên tắc", "Noun", "He stood by his principles.", "c_i5"),
                createWord("Investigate", "Điều tra", "Verb", "Scientists are investigating the cause.", "c_i5"),
                createWord("Methodology", "Hệ phương pháp", "Noun", "The research methodology is flawed.", "c_i5")
        ));

        // TOEIC HR (c_t3)
        allWords.addAll(Arrays.asList(
                createWord("Recruit", "Tuyển dụng", "Verb", "We need to recruit new staff.", "c_t3"),
                createWord("Resume", "Sơ yếu lý lịch", "Noun", "Please send your resume.", "c_t3"),
                createWord("Payroll", "Bảng lương", "Noun", "The payroll is processed on the 25th.", "c_t3"),
                createWord("Leave", "Nghỉ phép", "Noun", "She is on maternity leave.", "c_t3"),
                createWord("Promote", "Thăng chức", "Verb", "He was promoted to manager.", "c_t3"),
                createWord("Evaluate", "Đánh giá", "Verb", "Performance is evaluated annually.", "c_t3"),
                createWord("Pension", "Lương hưu", "Noun", "He receives a state pension.", "c_t3"),
                createWord("Hire", "Thuê mướn", "Verb", "They hired three new engineers.", "c_t3"),
                createWord("Fire", "Sa thải", "Verb", "He was fired for being late.", "c_t3"),
                createWord("Candidate", "Ứng viên", "Noun", "She is the best candidate.", "c_t3")
        ));

        // TOEIC Finance (c_t4)
        allWords.addAll(Arrays.asList(
                createWord("Budget", "Ngân sách", "Noun", "We are over budget this month.", "c_t4"),
                createWord("Audit", "Kiểm toán", "Noun", "The annual audit will start soon.", "c_t4"),
                createWord("Revenue", "Doanh thu", "Noun", "Company revenue increased by 10%.", "c_t4"),
                createWord("Expense", "Chi phí", "Noun", "Travel expenses are covered.", "c_t4"),
                createWord("Tax", "Thuế", "Noun", "Don't forget to pay your taxes.", "c_t4"),
                createWord("Profit", "Lợi nhuận", "Noun", "They made a huge profit.", "c_t4"),
                createWord("Invoice", "Hóa đơn", "Noun", "Please pay this invoice within 30 days.", "c_t4"),
                createWord("Quarter", "Quý", "Noun", "Sales fell in the first quarter.", "c_t4"),
                createWord("Asset", "Tài sản", "Noun", "The house is his main asset.", "c_t4"),
                createWord("Deficit", "Thâm hụt", "Noun", "The budget deficit is growing.", "c_t4")
        ));

        // TOEIC Marketing (c_t5)
        allWords.addAll(Arrays.asList(
                createWord("Campaign", "Chiến dịch", "Noun", "The ad campaign was a success.", "c_t5"),
                createWord("Target", "Mục tiêu", "Noun", "Our target audience is teenagers.", "c_t5"),
                createWord("Launch", "Ra mắt", "Verb", "They will launch the product next week.", "c_t5"),
                createWord("Brand", "Thương hiệu", "Noun", "It is a well-known global brand.", "c_t5"),
                createWord("Consumer", "Người tiêu dùng", "Noun", "Consumer demand is rising.", "c_t5"),
                createWord("Survey", "Khảo sát", "Noun", "We conducted a customer survey.", "c_t5"),
                createWord("Advertise", "Quảng cáo", "Verb", "They advertise on television.", "c_t5"),
                createWord("Sponsor", "Tài trợ", "Verb", "The event is sponsored by Nike.", "c_t5"),
                createWord("Promotion", "Khuyến mãi", "Noun", "They are running a special promotion.", "c_t5"),
                createWord("Market", "Thị trường", "Noun", "The market for smartphones is huge.", "c_t5")
        ));

        // Level C1 (c_l3)
        allWords.addAll(Arrays.asList(
                createWord("Profound", "Sâu sắc", "Adj", "It had a profound impact on me.", "c_l3"),
                createWord("Ubiquitous", "Có mặt khắp nơi", "Adj", "Smartphones are ubiquitous today.", "c_l3"),
                createWord("Mitigate", "Giảm nhẹ", "Verb", "We must mitigate the risks.", "c_l3"),
                createWord("Obsolete", "Lỗi thời", "Adj", "That technology is now obsolete.", "c_l3"),
                createWord("Paradigm", "Mô hình", "Noun", "A paradigm shift is needed.", "c_l3"),
                createWord("Resilient", "Kiên cường", "Adj", "Children are often highly resilient.", "c_l3"),
                createWord("Scrutinize", "Xem xét kỹ lưỡng", "Verb", "The document was carefully scrutinized.", "c_l3"),
                createWord("Viable", "Khả thi", "Adj", "Is this a viable solution?", "c_l3"),
                createWord("Pragmatic", "Thực dụng", "Adj", "We need a pragmatic approach.", "c_l3"),
                createWord("Elicit", "Gợi ra", "Verb", "The question elicited a strong response.", "c_l3")
        ));

        // Level B2 (c_l4)
        allWords.addAll(Arrays.asList(
                createWord("Adequate", "Đầy đủ", "Adj", "The supply is adequate for our needs.", "c_l4"),
                createWord("Controversial", "Gây tranh cãi", "Adj", "It is a controversial topic.", "c_l4"),
                createWord("Crucial", "Quan trọng", "Adj", "Vitamins are crucial for health.", "c_l4"),
                createWord("Emphasize", "Nhấn mạnh", "Verb", "He emphasized the importance of study.", "c_l4"),
                createWord("Perceive", "Nhận thức", "Verb", "Cats perceive sounds we cannot hear.", "c_l4"),
                createWord("Sufficient", "Đủ", "Adj", "We have sufficient evidence.", "c_l4"),
                createWord("Sustain", "Duy trì", "Verb", "It is hard to sustain economic growth.", "c_l4"),
                createWord("Reveal", "Tiết lộ", "Verb", "The report reveals the truth.", "c_l4"),
                createWord("Consequently", "Hậu quả là", "Adv", "He failed, and consequently lost his job.", "c_l4"),
                createWord("Fascinate", "Lôi cuốn", "Verb", "Space exploration fascinates me.", "c_l4")
        ));

        // Level A1 (c_l5)
        allWords.addAll(Arrays.asList(
                createWord("Apple", "Quả táo", "Noun", "I eat an apple every day.", "c_l5"),
                createWord("Book", "Quyển sách", "Noun", "This is an interesting book.", "c_l5"),
                createWord("Cat", "Con mèo", "Noun", "The cat is sleeping.", "c_l5"),
                createWord("Dog", "Con chó", "Noun", "My dog likes to play.", "c_l5"),
                createWord("House", "Ngôi nhà", "Noun", "We live in a big house.", "c_l5"),
                createWord("Water", "Nước", "Noun", "Please give me some water.", "c_l5"),
                createWord("Friend", "Bạn bè", "Noun", "She is my best friend.", "c_l5"),
                createWord("Happy", "Vui vẻ", "Adj", "I feel very happy today.", "c_l5"),
                createWord("School", "Trường học", "Noun", "He goes to school by bus.", "c_l5"),
                createWord("Family", "Gia đình", "Noun", "My family is very important to me.", "c_l5")
        ));

        // Graphic Design (c_s4)
        allWords.addAll(Arrays.asList(
                createWord("Layer", "Lớp", "Noun", "Merge the layers in Photoshop.", "c_s4"),
                createWord("Vector", "Ảnh vector", "Noun", "Illustrator uses vector graphics.", "c_s4"),
                createWord("Resolution", "Độ phân giải", "Noun", "Check the image resolution.", "c_s4"),
                createWord("Palette", "Bảng màu", "Noun", "Choose a warm color palette.", "c_s4"),
                createWord("Opacity", "Độ mờ đục", "Noun", "Reduce the opacity to 50%.", "c_s4"),
                createWord("Stroke", "Đường viền", "Noun", "Adjust the stroke thickness.", "c_s4"),
                createWord("Typography", "Nghệ thuật chữ", "Noun", "Good typography improves readability.", "c_s4"),
                createWord("Layout", "Bố cục", "Noun", "The poster layout looks clean.", "c_s4"),
                createWord("Render", "Xuất file", "Verb", "It takes time to render the video.", "c_s4"),
                createWord("Canvas", "Vùng vẽ", "Noun", "Set the canvas size to 1080p.", "c_s4")
        ));

        // Motorcycle Mechanics (c_s5)
        allWords.addAll(Arrays.asList(
                createWord("Tensioner", "Tăng cam", "Noun", "The cam chain tensioner needs replacing.", "c_s5"),
                createWord("Clutch", "Côn, ly hợp", "Noun", "Pull the clutch to shift gears.", "c_s5"),
                createWord("Throttle", "Tay ga", "Noun", "Twist the throttle to accelerate.", "c_s5"),
                createWord("Lubricant", "Dầu nhớt", "Noun", "Change the engine lubricant regularly.", "c_s5"),
                createWord("Suspension", "Phuộc, giảm xóc", "Noun", "The front suspension is leaking oil.", "c_s5"),
                createWord("Mileage", "Số km đã đi (ODO)", "Noun", "Check the mileage before buying.", "c_s5"),
                createWord("Exhaust", "Ống xả, pô", "Noun", "Smoke is coming from the exhaust.", "c_s5"),
                createWord("Spark plug", "Bugi", "Noun", "Clean the spark plug if the engine stalls.", "c_s5"),
                createWord("Brake", "Phanh, thắng", "Noun", "The front brake pads are worn out.", "c_s5"),
                createWord("Engine", "Động cơ", "Noun", "The engine overheated during the trip.", "c_s5")
        ));

        // Driving License (c_s6)
        allWords.addAll(Arrays.asList(
                createWord("Intersection", "Ngã tư, giao lộ", "Noun", "Slow down at the intersection.", "c_s6"),
                createWord("Pedestrian", "Người đi bộ", "Noun", "Yield to pedestrians at the crosswalk.", "c_s6"),
                createWord("Highway", "Đường cao tốc", "Noun", "The speed limit on the highway is 120 km/h.", "c_s6"),
                createWord("Roundabout", "Vòng xuyến", "Noun", "Take the second exit at the roundabout.", "c_s6"),
                createWord("Vehicle", "Phương tiện", "Noun", "Keep a safe distance from other vehicles.", "c_s6"),
                createWord("Simulator", "Phần mềm mô phỏng", "Noun", "Practice with the driving simulator app.", "c_s6"),
                createWord("Penalty", "Tiền phạt", "Noun", "You will pay a penalty for speeding.", "c_s6"),
                createWord("Overtake", "Vượt xe", "Verb", "Do not overtake on a solid line.", "c_s6"),
                createWord("Sign", "Biển báo", "Noun", "Pay attention to the traffic signs.", "c_s6"),
                createWord("License", "Bằng lái", "Noun", "You must carry your driving license.", "c_s6")
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
        List<String> newSetIds = Arrays.asList("c_i3", "c_i4", "c_i5", "c_t3", "c_t4", "c_t5", "c_l3", "c_l4", "c_l5", "c_s4", "c_s5", "c_s6");
        mongoTemplate.remove(Query.query(Criteria.where("_id").in(newSetIds)), VocabSetEntity.class);
        mongoTemplate.remove(Query.query(Criteria.where("vocabID").in(newSetIds)), Word.class);
    }
}