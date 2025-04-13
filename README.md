# simple-task-management-system
Sebuah aplikasi task management sederhana berbasis **Spring Boot**, yang menyediakan fitur autentikasi dan pengelolaan task dengan sistem tahapan (stages).

---

## Fitur

### Autentikasi
- **Register** user baru
- **Login** menggunakan email dan password
- Mendapatkan JWT Token untuk akses endpoint yang dilindungi

### Manajemen Task
- **Add Task** – Menambahkan task baru
- **Edit Task** – Mengubah nama dan detail task
- **Delete Task** – Menghapus task
- **Update Task** – Memindahkan task ke stage lain (Todo → In Progress, dsb)
- **Complete Task** – Menandai task sebagai selesai
- **Get Tasks** – Menampilkan semua task, task yang belum selesai, atau yang sudah selesai

---

## Stage (Tahapan Task)

Setiap task bisa berada pada salah satu dari stage berikut:

1. **Todo**
2. **In Progress**
3. **Completed**

---

## 🔧 Teknologi yang Digunakan

- Java 17+
- Spring Boot 3.x
- Spring Security (JWT Auth)
- Spring Data JPA (Hibernate)
- PostgreSQL / MySQL / H2 (sesuai kebutuhan)
- Lombok
- Swagger / OpenAPI untuk dokumentasi API
