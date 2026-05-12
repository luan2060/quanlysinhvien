/*
 * Author: Hỏi Dân IT - @hoidanit 
 *
 * This source code is developed for the course
 * "Java Spring Siêu Tốc - Tự Học Java Spring Từ Số 0 Dành Cho Beginners từ A tới Z".
 * It is intended for educational purposes only.
 * Unauthorized distribution, reproduction, or modification is strictly prohibited.
 *
 * Copyright (c) 2025 Hỏi Dân IT. All Rights Reserved.
 */
package com.example.quanlysinhvien.bean.lifecycle;

import org.springframework.beans.factory.InitializingBean;

import jakarta.annotation.PostConstruct;

//@Component
public class DatabaseConnector implements InitializingBean {

	// Thuộc tính sẽ được thiết lập (DI) trước khi Custom Init được gọi
	private String connectionUrl = "unknown";
	private boolean driverLoaded = false;

	public DatabaseConnector() {
		System.out.println("==============================================================");
		System.out.println("Step 1: Constructor (Instantiation) - Bean đã được tạo.");
		System.out.println("Init Connection URL value = " + this.connectionUrl);
		System.out.println();
	}

	// Setter để mô phỏng DI (Giai đoạn 2)
	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
		System.out.println("Step 2: Thiết lập Phụ thuộc (DI) - Connection URL đã được set. ");
		System.out.println("Current Connection URL value = " + this.connectionUrl);
		System.out.println();
	}

	/*
	 * CÁCH 1: Sử dụng Annotation @PostConstruct (Cách hiện đại, ưu tiên sử dụng)
	 */
	@PostConstruct
	public void postConstructInitialization() {
		System.out.println("Step 3 (1): @PostConstruct - Sẵn sàng mở kết nối tới " + connectionUrl);
	}

	/*
	 * CÁCH 2: Sử dụng Interface InitializingBean (Thường không khuyến khích vì làm
	 * coupling code với Spring)
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Step 3 (2): InitializingBean.afterPropertiesSet() - Đang thiết lập cấu hình...");
	}

	/*
	 * CÁCH 3: Sử dụng initMethod (Sẽ được gọi qua cấu hình Java hoặc XML)
	 */
	public void customInitMethod() {
		System.out.println("\n[3. Khởi tạo Tùy chỉnh] Bắt đầu chạy customInitMethod...");

		// **LOGIC NGHIỆP VỤ QUAN TRỌNG:**

		// 1. Kiểm tra tính hợp lệ của thuộc tính đã được tiêm (DI)
		if (connectionUrl == null || !connectionUrl.startsWith("jdbc:")) {
			// Ném ngoại lệ nếu cấu hình sai -> ngăn Bean khởi tạo
			throw new IllegalArgumentException("Lỗi: Connection URL không hợp lệ.");
		}

		// 2. Thực hiện các hành động tốn tài nguyên (giả lập)
		System.out.println("   -> Đang tải driver cho: " + connectionUrl);

		// Giả lập việc tải driver database thành công
		this.driverLoaded = true;

		System.out.println("   -> Driver đã tải thành công.");

		System.out.println("[3. Khởi tạo Tùy chỉnh] Bean đã sẵn sàng hoạt động!");
	}

}
