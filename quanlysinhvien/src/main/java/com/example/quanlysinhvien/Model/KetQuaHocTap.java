package com.example.quanlysinhvien.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.persistence.*;

@Entity
@Table(name = "ket_qua_hoc_taps")
public class KetQuaHocTap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sinh_vien_id", nullable = false)
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "lop_hp_id", nullable = false)
    private LopHocPhan lopHocPhan;

    @Column(name = "diem_chuyen_can")
    private Float diemChuyenCan;

    @Column(name = "diem_giua_ky")
    private Float diemGiuaKy;

    @Column(name = "diem_cuoi_ky")
    private Float diemCuoiKy;

    @Column(name = "diem_tong_ket")
    private Float diemTongKet;

    @Column(name = "diem_chu", length = 2)
    private String diemChu; // A, B+, B, C...

    @Column(name = "trang_thai_qua_mon")
    private Boolean trangThaiQuaMon; // true: Qua môn, false: Trượt

    public KetQuaHocTap() {
    }

    public KetQuaHocTap(int id, SinhVien sinhVien, LopHocPhan lopHocPhan, Float diemChuyenCan, Float diemGiuaKy,
            Float diemCuoiKy, Float diemTongKet, String diemChu, Boolean trangThaiQuaMon) {
        this.id = id;
        this.sinhVien = sinhVien;
        this.lopHocPhan = lopHocPhan;
        this.diemChuyenCan = diemChuyenCan;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        this.diemTongKet = diemTongKet;
        this.diemChu = diemChu;
        this.trangThaiQuaMon = trangThaiQuaMon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public LopHocPhan getLopHocPhan() {
        return lopHocPhan;
    }

    public void setLopHocPhan(LopHocPhan lopHocPhan) {
        this.lopHocPhan = lopHocPhan;
    }

    public Float getDiemChuyenCan() {
        return diemChuyenCan;
    }

    public void setDiemChuyenCan(Float diemChuyenCan) {
        this.diemChuyenCan = diemChuyenCan;
    }

    public Float getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public void setDiemGiuaKy(Float diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
    }

    public Float getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(Float diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

    public Float getDiemTongKet() {
        return diemTongKet;
    }

    public void setDiemTongKet(Float diemTongKet) {
        this.diemTongKet = diemTongKet;
    }

    public String getDiemChu() {
        return diemChu;
    }

    public void setDiemChu(String diemChu) {
        this.diemChu = diemChu;
    }

    public Boolean getTrangThaiQuaMon() {
        return trangThaiQuaMon;
    }

    public void setTrangThaiQuaMon(Boolean trangThaiQuaMon) {
        this.trangThaiQuaMon = trangThaiQuaMon;
    }

}
