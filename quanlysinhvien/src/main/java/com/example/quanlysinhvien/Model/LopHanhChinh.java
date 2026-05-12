package com.example.quanlysinhvien.Model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.*;

@Entity
@Table(name = "lop_hanh_chinhs")
public class LopHanhChinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ma_lop_hc", nullable = false, unique = true)
    private String maLopHc;

    @Column(name = "ten_lop_hc", nullable = false)
    private String tenLopHc;

    private String khoaHoc;
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "nganh_id")
    private Nganh nganh;

    @ManyToOne
    @JoinColumn(name = "co_van_id")
    private Nhanvien coVan;
    @OneToMany(mappedBy = "lopHanhChinh", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SinhVien> listSinhVien;

    public LopHanhChinh() {
    }

    public LopHanhChinh(int id, String maLopHc, String tenLopHc, String khoaHoc, String trangThai, Nganh nganh,
            Nhanvien coVan, List<SinhVien> listSinhVien) {
        this.id = id;
        this.maLopHc = maLopHc;
        this.tenLopHc = tenLopHc;
        this.khoaHoc = khoaHoc;
        this.trangThai = trangThai;
        this.nganh = nganh;
        this.coVan = coVan;
        this.listSinhVien = listSinhVien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaLopHc() {
        return maLopHc;
    }

    public void setMaLopHc(String maLopHc) {
        this.maLopHc = maLopHc;
    }

    public String getTenLopHc() {
        return tenLopHc;
    }

    public void setTenLopHc(String tenLopHc) {
        this.tenLopHc = tenLopHc;
    }

    public String getKhoaHoc() {
        return khoaHoc;
    }

    public void setKhoaHoc(String khoaHoc) {
        this.khoaHoc = khoaHoc;
    }

    public Nganh getNganh() {
        return nganh;
    }

    public void setNganh(Nganh nganh) {
        this.nganh = nganh;
    }

    public Nhanvien getCoVan() {
        return coVan;
    }

    public void setCoVan(Nhanvien coVan) {
        this.coVan = coVan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public List<SinhVien> getListSinhVien() {
        return listSinhVien;
    }

    public void setListSinhVien(List<SinhVien> listSinhVien) {
        this.listSinhVien = listSinhVien;
    }

}
