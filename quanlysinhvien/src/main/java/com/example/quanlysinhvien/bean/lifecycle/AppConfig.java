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

import org.springframework.context.annotation.Bean;

//@Configuration
public class AppConfig {

	@Bean(initMethod = "customInitMethod") // Chỉ định tên init method
	public DatabaseConnector databaseConnector() {
		DatabaseConnector connector = new DatabaseConnector();
		// Thực hiện DI cho thuộc tính (Setter Injection)
		connector.setConnectionUrl("jdbc:postgresql://localhost:5432/mydb");
		return connector;
	}
}
