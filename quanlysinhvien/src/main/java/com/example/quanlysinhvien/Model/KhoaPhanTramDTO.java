package com.example.quanlysinhvien.Model;

public class KhoaPhanTramDTO {

    private String tenKhoa;
    private Long soSinhVien;
    private Double phanTram;

    // Constructor cho JPQL (chỉ cần 2 tham số, phanTram tính sau)
    public KhoaPhanTramDTO(String tenKhoa, Long soSinhVien) {
        this.tenKhoa = tenKhoa;
        this.soSinhVien = soSinhVien;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public Long getSoSinhVien() {
        return soSinhVien;
    }

    public void setSoSinhVien(Long soSinhVien) {
        this.soSinhVien = soSinhVien;
    }

    public Double getPhanTram() {
        return phanTram;
    }

    public void setPhanTram(Double phanTram) {
        this.phanTram = phanTram;
    }
}
