package com.example.quanlysinhvien.Model;

import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sinhviens")
public class SinhVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ma_sv", nullable = false, unique = true)
    private String maSV;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySinh;

    private String gioiTinh;
    private String diaChi;

    @Column(name = "email_truong_cap", unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String sdt;

    @Column(nullable = false)
    private String password;

    @Column(name = "trang_thai")
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "lop_hc_id")
    private LopHanhChinh lopHanhChinh;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KetQuaHocTap> danhSachKetQuaHocTap;

    public SinhVien() {
    }

public SinhVien(int id, String maSV, String hoTen, Date ngaySinh, String gioiTinh, String diaChi, String email,
            String sdt, String password, String trangThai, LopHanhChinh lopHanhChinh,
            List<KetQuaHocTap> danhSachKetQuaHocTap) {
        this.id = id;
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.email = email;
        this.sdt = sdt;
        this.password = password;
        this.trangThai = trangThai;
        this.lopHanhChinh = lopHanhChinh;
        this.danhSachKetQuaHocTap = danhSachKetQuaHocTap;
    }

    

    public List<KetQuaHocTap> getDanhSachKetQuaHocTap() {
        return danhSachKetQuaHocTap;
    }

    public void setDanhSachKetQuaHocTap(List<KetQuaHocTap> danhSachKetQuaHocTap) {
        this.danhSachKetQuaHocTap = danhSachKetQuaHocTap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LopHanhChinh getLopHanhChinh() {
        return lopHanhChinh;
    }

    public void setLopHanhChinh(LopHanhChinh lopHanhChinh) {
        this.lopHanhChinh = lopHanhChinh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
