Giới thiệu dự án Fashion Shop
1. Giới thiệu tính năng và ảnh minh họa
   1.1 Trang shop ( hiển thị danh sách sản phẩm, phân trang, filter,..) ( ở đây em chỉ lấy hình random trên mạng nên các hình ảnh sẽ không được khớp với tên sản phẩm cho lắm)
   ![Shop page 1](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083223.png?raw=true)
   ![Shop page 2](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083230.png?raw=true)
   ![Shop page 3](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083242.png?raw=true)
   1.2 Trang chi tiết sản phẩm, có thể lựa chọn các biến thể
   ![Product detail](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083319.png?raw=true)
   1.3 Trang giỏ hàng ( trước đây em lưu giỏ hàng trực tiếp trong db nhưng nếu làm vậy thì khi người dùng spam thì db có nguy cơ chết nên em chuyển sang lưu cart ( chỉ lưu id của biến thể, số lượng cùng với user id, muốn hiển thị thì dùng các thông tin đó truy vấn sang db chính) sang redis )
   ![Cat Page](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083357.png?raw=true)
   1.4 Trang điền thông tin thanh toán
   ![Order info page](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083617.png?raw=true)
   1.5 Mã QR (PayOS) nếu chọn thanh toán online
   ![QR](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083651.png?raw=true)
   1.6 Lịch sử mua hàng và theo dõi đơn hàng
   ![Order history](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083747.png?raw=true)
   ![Order tracking](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20083800.png?raw=true)
   1.7 Search sản phẩm bằng từ khóa (Em dùng ElasticSearch để cho phép tìm kiếm nếu sai chính tả, và tìm kiếm dựa trên các từ đồng nghĩa (các từ đồng nghĩa sẽ được cấu hình thủ công, ở đây em mới cấu hình một vài từ đơn giản))
   Search đồng nghĩa
   ![Search 1](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084745.png?raw=true)
   Search sai chính tả
   ![Search 2](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084745.png?raw=true)
   1.8 Trang hiển thị thông tin người dùng cho admin
   ![Admin User management](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084823.png?raw=true)
   1.9 Trang quản lý sản phẩm ( có thể CRUD sản phẩm, biến thể, nếu xóa sản phẩm hay biến thể mà có Order đang trong trạng thái SHIPPING,PENDING thì không xóa được)
   ![Admin Product management](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084847.png?raw=true)
   1.10 Trang quản lý đơn hàng 
   ![List order](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20085026.png?raw=true)
   ![Order detail](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20093708.png?raw=true)
   1.11 Trang thống kê
   ![Statistic 1](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084923.png?raw=true)
   ![Statistic 2](https://github.com/BinhUIT/web_ban_hang/blob/master/project_images/Screenshot%202025-10-23%20084930.png?raw=true)
   2. Nhận xét
   Dự án về cơ bản đã hoàn thành một số tính năng chính, tuy nhiên vẫn còn nhiều thiếu sót và một số tính năng chưa được hoàn thiện ( OAuth2 mới chỉ được triển khai ở phía Backend, tính năng giảm giá,...) rất mong được anh/chị góp ý để cải thiện ạ!
   Code trong dự án còn nhiều chỗ chưa được sạch và tối ưu, em đang trong quá trình refactor trên một repository khác ạ, anh/ chị có thể xem tại https://github.com/BinhUIT/fashion_backend
   Đây là repository chứa code để lưu cache trên redis: https://github.com/BinhUIT/fashion_cart
   Em xin chân thành cảm ơn anh/chị đã dành thời gian để review dự án của em ạ!
