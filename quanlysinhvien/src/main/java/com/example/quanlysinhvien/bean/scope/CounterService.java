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

package com.example.quanlysinhvien.bean.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("prototype") // <--- Đặt là PROTOTYPE
@Service
public class CounterService {

	private static int creationCount = 0;
	private final int instanceId;

	public CounterService() {
		creationCount++;
		this.instanceId = this.hashCode();
		System.out.println("-> [Constructor] Đã tạo instance thứ: " + creationCount);
	}

	public String getInfo() {
		return "Instance ID: " + instanceId;
	}
}
