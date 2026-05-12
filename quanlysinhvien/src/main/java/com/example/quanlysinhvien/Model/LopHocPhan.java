package com.example.quanlysinhvien.Model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "lop_hoc_phans")
public class LopHocPhan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_lop_hp", nullable = false, unique = true)
    private String maLopHP;

    @Column(name = "ten_lop_hp", nullable = false)
    private String tenLopHP;

    private Integer hocKy;
    private String namHoc;
    private Integer siSoToiDa;
    private String phongHoc;

    // ✅ Tách gioHoc thành 3 field rõ ràng
    private Integer thuTrongTuan; // 2=Thứ2 ... 7=Thứ7
    private Integer caHoc; // 1=Ca1, 2=Ca2, 3=Ca3, 4=Ca4
    private String gioHoc; // VD: "07:00 - 09:25" (hiển thị)

    @ManyToOne
    @JoinColumn(name = "hoc_phan_id")
    private HocPhan hocPhan;

    @ManyToOne
    @JoinColumn(name = "giang_vien_id")
    private Nhanvien giangVien;

    @OneToMany(mappedBy = "lopHocPhan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KetQuaHocTap> danhSachKetQua = new ArrayList<>();

    public LopHocPhan() {
    }

    public LopHocPhan(Integer id, String maLopHP, String tenLopHP, Integer hocKy,
            String namHoc, Integer siSoToiDa, String phongHoc,
            Integer thuTrongTuan, Integer caHoc, String gioHoc,
            HocPhan hocPhan, Nhanvien giangVien, List<KetQuaHocTap> danhSachKetQua) {
        this.id = id;
        this.maLopHP = maLopHP;
        this.tenLopHP = tenLopHP;
        this.hocKy = hocKy;
        this.namHoc = namHoc;
        this.siSoToiDa = siSoToiDa;
        this.phongHoc = phongHoc;
        this.thuTrongTuan = thuTrongTuan;
        this.caHoc = caHoc;
        this.gioHoc = gioHoc;
        this.hocPhan = hocPhan;
        this.giangVien = giangVien;
        this.danhSachKetQua = danhSachKetQua;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaLopHP() {
        return maLopHP;
    }

    public void setMaLopHP(String maLopHP) {
        this.maLopHP = maLopHP;
    }

    public String getTenLopHP() {
        return tenLopHP;
    }

    public void setTenLopHP(String tenLopHP) {
        this.tenLopHP = tenLopHP;
    }

    public Integer getHocKy() {
        return hocKy;
    }

    public void setHocKy(Integer hocKy) {
        this.hocKy = hocKy;
    }

    public String getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(String namHoc) {
        this.namHoc = namHoc;
    }

    public Integer getSiSoToiDa() {
        return siSoToiDa;
    }

    public void setSiSoToiDa(Integer siSoToiDa) {
        this.siSoToiDa = siSoToiDa;
    }

    public String getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }

    public Integer getThuTrongTuan() {
        return thuTrongTuan;
    }

    public void setThuTrongTuan(Integer thuTrongTuan) {
        this.thuTrongTuan = thuTrongTuan;
    }

    public Integer getCaHoc() {
        return caHoc;
    }

    public void setCaHoc(Integer caHoc) {
        this.caHoc = caHoc;
    }

    public String getGioHoc() {
        return gioHoc;
    }

    public void setGioHoc(String gioHoc) {
        this.gioHoc = gioHoc;
    }

    public HocPhan getHocPhan() {
        return hocPhan;
    }

    public void setHocPhan(HocPhan hocPhan) {
        this.hocPhan = hocPhan;
    }

    public Nhanvien getGiangVien() {
        return giangVien;
    }

    public void setGiangVien(Nhanvien giangVien) {
        this.giangVien = giangVien;
    }

    public List<KetQuaHocTap> getDanhSachKetQua() {
        return danhSachKetQua;
    }

    public void setDanhSachKetQua(List<KetQuaHocTap> danhSachKetQua) {
        this.danhSachKetQua = danhSachKetQua;
    }
}