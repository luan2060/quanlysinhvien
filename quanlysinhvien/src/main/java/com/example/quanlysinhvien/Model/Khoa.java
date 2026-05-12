package com.example.quanlysinhvien.Model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.*;

@Entity
@Table(name = "khoas")
public class Khoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_khoa", nullable = false, unique = true)
    private String maKhoa;

    @Column(name = "ten_khoa", nullable = false)
    private String tenKhoa;

    private Integer namThanhLap;
    private String thongTinLienHe;
    private String trangThai;
    private String truongKhoa;
    @OneToMany(mappedBy = "khoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nganh> listNganh;

    public Khoa() {
    }

    public Khoa(Integer id, String maKhoa, String tenKhoa, Integer namThanhLap, String thongTinLienHe, String trangThai,
            String truongKhoa, List<Nganh> listNganh) {
        this.id = id;
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.namThanhLap = namThanhLap;
        this.thongTinLienHe = thongTinLienHe;
        this.trangThai = trangThai;
        this.truongKhoa = truongKhoa;
        this.listNganh = listNganh;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public Integer getNamThanhLap() {
        return namThanhLap;
    }

    public void setNamThanhLap(Integer namThanhLap) {
        this.namThanhLap = namThanhLap;
    }

    public String getThongTinLienHe() {
        return thongTinLienHe;
    }

    public void setThongTinLienHe(String thongTinLienHe) {
        this.thongTinLienHe = thongTinLienHe;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public List<Nganh> getListNganh() {
        return listNganh;
    }

    public void setListNganh(List<Nganh> listNganh) {
        this.listNganh = listNganh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTruongKhoa() {
        return truongKhoa;
    }

    public void setTruongKhoa(String truongKhoa) {
        this.truongKhoa = truongKhoa;
    }

}