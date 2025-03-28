# 📝 To-Do List App

## 🚀 Giới thiệu
To-Do List App là một ứng dụng quản lý công việc được xây dựng bằng **Jetpack Compose** theo mô hình **Clean Architecture**. Ứng dụng sử dụng **ObjectBox** làm cơ sở dữ liệu, kết hợp **Hilt** để quản lý Dependency Injection.

## ✨ Tính năng
- ✅ Thêm công việc mới
- ❌ Xóa công việc
- ✏️ Sửa công việc
- 📋 Hiển thị danh sách công việc

## 🛠 Công nghệ sử dụng
- 🎨 **Jetpack Compose**: Xây dựng UI theo hướng khai báo
- 🏗 **Hilt**: Quản lý Dependency Injection
- 💾 **ObjectBox**: Lưu trữ dữ liệu cục bộ hiệu suất cao
- 🔄 **Flow**: Quản lý dữ liệu bất đồng bộ
- 📐 **Clean Architecture**: Tách biệt rõ ràng giữa các tầng Presentation, Domain, Data

## 📂 Cấu trúc dự án
```bash
com.example.todolist_jetpackcompose
├── components           # Các thành phần UI tái sử dụng
│   ├── CardCustom.kt    # Component hiển thị task
│   └── DialogCustom.kt  # Dialog xác nhận xóa
├── data                # Data Layer: Xử lý dữ liệu
│   ├── model            # Các entity cho database
│   │   └── TaskEntity.kt # Entity cho ObjectBox
│   └── repository       # Repository triển khai
│       └── TaskRepositoryImpl.kt # Triển khai TaskRepository
├── di                   # Dependency Injection với Hilt
│   └── AppModule.kt     # Cung cấp dependency (TaskRepository)
├── domain              # Domain Layer: Logic nghiệp vụ
│   ├── model            # Các entity nghiệp vụ
│   │   └── Task.kt      # Entity Task thuần túy
│   ├── repository       # Interface repository
│   │   └── TaskRepository.kt # Định nghĩa phương thức truy xuất dữ liệu
│   └── usecase          # Use cases cho logic nghiệp vụ
│       ├── AddTaskUseCase.kt    # Thêm task
│       ├── DeleteTaskUseCase.kt # Xóa task
│       ├── GetTaskUseCase.kt    # Lấy danh sách task
│       └── UpdateTaskUseCase.kt # Cập nhật task
└── presentation         # Presentation Layer: ViewModel và UI
    ├── ToDoListViewModel.kt # Quản lý state và logic UI
    └── screen               # Các màn hình UI
        ├── TaskDetailScreen.kt # Màn hình chi tiết task 
        └── TodoListScreen.kt   # Màn hình danh sách task

## 📥 Cài đặt
### 📌 Cấu hình đang sử dụng
- 🏗 Android Studio Meerkat
- 📝 Kotlin 2.0.20
- ⚙️ Gradle 8.11.1

### 🛠 Hướng dẫn
1. Clone repository:
   ```bash
   git clone https://github.com/LTDat11/TodoList-JetpackCompose
   cd TodoList-JetpackCompose

2. Mở project trong Android Studio và build lại.

3. Chạy ứng dụng trên thiết bị hoặc trình giả lập.

## 📖 Hướng dẫn sử dụng
- Nhấp vào nút FAB để thêm nội dung.
- Check vào checkbox để đánh dấu nội hoàn thành.
- Chọn nút xóa trên nội dung đã hoàn thành để xóa công việc.
- Nhấn vào nội chung để đến trang chi tiết cho phép chỉnh sửa và lưu lại nội dung.
- Nếu nội dung bị che khuất có thể kéo thả nút FAB đến vị trí mới.

---

## 💖 Cảm ơn!
Cảm ơn bạn đã dành thời gian đọc README này! Chúc bạn có trải nghiệm lập trình vui vẻ! 🎉  
