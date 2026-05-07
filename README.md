# Backend English Study

Dự án này là backend cho ứng dụng học tiếng Anh, được xây dựng bằng **Spring Boot** kết hợp với cơ sở dữ liệu **MongoDB**.

## Cấu trúc thư mục (Folder Structure)

Dự án áp dụng mô hình phân lớp tiêu chuẩn của Spring Boot để đảm bảo code rõ ràng, dễ bảo trì và dễ dàng mở rộng. Dưới đây là cấu trúc chi tiết của thư mục `src/main/java/com/english_study/`:

```text
src/main/java/com/english_study/
├── config/         # Chứa các class cấu hình hệ thống (Cấu hình Spring Security, Cors, MongoDB, v.v.)
├── controller/     # REST Controllers: Nơi tiếp nhận các HTTP request từ phía client và định tuyến các Endpoints (RESTful API).
├── exception/      # Chứa các lớp Custom Exception và Global Exception Handler (UserNotFoundException, InvalidCredentialException, v.v.)
├── mapper/         # Các Mapper chuyển đổi dữ liệu qua lại giữa Entity (DB) và DTO (View) (ví dụ: UserMapper, VideoMapper).
├── model/          # Định nghĩa cấu trúc dữ liệu toàn dự án:
│   ├── dto/        # Data Transfer Objects: Các đối tượng dùng để trả về data cho client (ẩn các trường nhạy cảm).
│   ├── entity/     # Các Class Entity map trực tiếp với các collection trong MongoDB (User, Word, Video, v.v.).
│   ├── request/    # Các class hứng dữ liệu payload từ request body của client (RegisterRequest, LoginRequest).
│   └── response/   # Các class bao bọc dữ liệu trả về cho client (TokenResponse, BaseResponse).
├── repository/     # Tầng Data Access (DAO): Chứa các interface kế thừa từ MongoRepository để thao tác trực tiếp với cơ sở dữ liệu.
├── sercurity/      # Chứa các class chuyên phục vụ bảo mật: Xử lý Authentication, bộ lọc JWT, phân quyền.
├── service/        # Tầng Business Logic: Chứa các Service thực thi logic nghiệp vụ (UserService, WordService). Nơi xử lý dữ liệu trước khi lưu/trả về.
└── until/          # (Utils) Chứa các class, hàm tiện ích (helper functions) có thể dùng chung ở nhiều nơi trong dự án.
```

## Luồng hoạt động cơ bản (Data Flow)

Khi một Client (Frontend) gửi một yêu cầu tới hệ thống, dữ liệu thường sẽ đi theo luồng sau:
1.  **Client** gửi HTTP Request -> **Controller** (hứng Request Body bằng các đối tượng ở thư mục `model/request`).
2.  **Controller** gọi **Service** để thực thi nghiệp vụ.
3.  **Service** xử lý logic, gọi đến **Repository** nếu cần tương tác với DB (đọc, ghi **Entity** ở thư mục `model/entity`).
4.  Sau khi lấy được Entity từ DB, **Service** sử dụng **Mapper** (`mapper/`) để chuyển đổi **Entity** thành **DTO** (`model/dto`).
5.  **Service** trả **DTO** lại cho **Controller**.
6.  **Controller** bọc **DTO** trong các đối tượng Response (`model/response` hoặc `ResponseEntity`) và trả về lại cho **Client**.
