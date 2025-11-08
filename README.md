# 👗 Fashion Shop

Dự án **Website bán hàng thời trang** được xây dựng bằng **React**, **Spring Boot**, **Redis** và **ElasticSearch**.  
Mục tiêu của dự án là tạo một hệ thống bán hàng onlineonline, có thể tìm kiếm, quản lý đơn hàng và thanh toán trực tuyến.

---

## 🧭 1. Giới thiệu tính năng & Ảnh minh họa

### 🛍️ 1.1 Trang shop  
Hiển thị danh sách sản phẩm, có phân trang, lọc và sắp xếp.  
*(Hình ảnh minh họa được lấy ngẫu nhiên trên mạng nên có thể không khớp hoàn toàn với tên sản phẩm.)*

![Shop page 1](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083223.png?raw=true)
![Shop page 2](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083230.png?raw=true)
![Shop page 3](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083242.png?raw=true)

---

### 👕 1.2 Trang chi tiết sản phẩm  
Người dùng có thể xem chi tiết sản phẩm và chọn biến thể (màu, size,...).  
![Product detail](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083319.png?raw=true)

---

### 🛒 1.3 Trang giỏ hàng  
Giỏ hàng được lưu trên **Redis** để giảm tải cho cơ sở dữ liệu.  
Trước đây giỏ hàng được lưu trực tiếp trong DB, nhưng dễ gây quá tải nếu người dùng spam nên giỏ hàng đã được chuyển sang lưu tạm trên Redis (chỉ lưu id biến thể, số lượng và user id, khi hiển thị thì sẽ lấy ảnh, tên,.. từ db chính).

![Cart Page](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083357.png?raw=true)

---

### 💳 1.4 Trang điền thông tin thanh toán  
Người dùng nhập địa chỉ nhận hàng, phương thức thanh toán.  
![Order info page](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083617.png?raw=true)

---

### 🧾 1.5 Mã QR thanh toán (PayOS)  
Nếu chọn thanh toán online, hệ thống sẽ hiển thị mã QR từ **PayOS** để người dùng quét.  
![QR](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083651.png?raw=true)

---

### 📦 1.6 Lịch sử mua hàng & Theo dõi đơn  
Người dùng có thể xem lại các đơn đã mua và theo dõi tình trạng giao hàng.  
![Order history](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083747.png?raw=true)
![Order tracking](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083800.png?raw=true)

---

### 🔍 1.7 Tìm kiếm sản phẩm (ElasticSearch)  
Cho phép tìm kiếm **có gợi ý từ đồng nghĩa** và **chấp nhận sai chính tả nhẹ**.  
*(Hiện tại dự án chỉ mớimới cấu hình thử một vài từ đơn giản cho elastic search.)*

- **Tìm kiếm đồng nghĩa:**  
  ![Search 1](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084745.png?raw=true)
- **Tìm kiếm sai chính tả:**  
  ![Search 2](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084745.png?raw=true)

---

### 👩‍💼 1.8 Quản lý người dùng (Admin)  
Trang cho admin xem và quản lý thông tin người dùng.  
![Admin User management](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084823.png?raw=true)

---

### 🧰 1.9 Quản lý sản phẩm (Admin)  
Cho phép CRUD sản phẩm và biến thể.  
Nếu sản phẩm/biến thể thuộc đơn hàng có trạng thái **SHIPPING** hoặc **PENDING** thì không thể xóa.  
![Admin Product management](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084847.png?raw=true)

---

### 📋 1.10 Quản lý đơn hàng (Admin)  
Xem danh sách và chi tiết từng đơn hàng.  
![List order](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20085026.png?raw=true)
![Order detail](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20093708.png?raw=true)

---

### 📊 1.11 Trang thống kê  
Thống kê doanh thu, đơn hàng và các chỉ số kinh doanh.  
![Statistic 1](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084923.png?raw=true)
![Statistic 2](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084930.png?raw=true)

---

### 🤖 1.12 Chức năng đề xuất
Client gửi request chứa  ảnh lên server chính, server chính sẽ tiếp tục gửi request đến server python, dùng dữ liệu server python trả về để đề xuất sản phẩm.
![Recommendation 1](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-11-08%20135959.png?raw=true)

---

## 💬 2. Nhận xét

Dự án đã hoàn thiện hầu hết các tính năng chính, tuy nhiên vẫn còn một số phần đang phát triển:

- **OAuth2** mới được triển khai phía Backend, chưa tích hợp lên Frontend.  
- **Chức năng giảm giá** chưa hoàn chỉnh.  

Phần code còn cần được tối ưu và làm sạch hơn.  
Repo chứa source code phần redis:
🔗 [Fashion Cart (Redis Cache)](https://github.com/BinhUIT/fashion_cart)
Repo chứa source code cho server dùng để đề xuất sản phẩm
🔗 [Recommendation Server](https://github.com/BinhUIT/recommendation_server)



---

## ⚙️ 3. Công nghệ sử dụng
- **Frontend:** React, TailwindCSS, 
- **Backend:** Spring Boot (Java)  
- **Database:** MySQL  
- **Cache:** Redis  
- **Search Engine:** ElasticSearch  
- **Payment Gateway:** PayOS  

---
## 🚀 4. Cách chạy dự án
- B1: Clone repository
- B2: Cài đặt JDK-17 và Maven
- B3: Tạo file .env tại thư mục gốc của dự án và điền các thông tin trong file .env.example vào file vừa tạo
- B4: Chạy câu lệnh "mvn spring-boot::run" để chạy server

## 👤 Tác giả
**Đặng Lê Bình**  
📧 leyen15121971@gmail.com  
🌐 [GitHub Profile](https://github.com/BinhUIT)
