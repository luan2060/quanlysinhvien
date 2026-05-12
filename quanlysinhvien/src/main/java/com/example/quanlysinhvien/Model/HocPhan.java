package com.example.quanlysinhvien.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hoc_phans")
public class HocPhan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_hoc_phan", nullable = false, unique = true)
    private String maHocPhan;

    @Column(name = "ten_hoc_phan", nullable = false)
    private String tenHocPhan;

    private Integer soTinChi;
    private Integer soTietLyThuyet;
    private Integer soTietThucHanh;
    private Integer hocPhi;

    // Many-to-One: nhiều học phần thuộc một ngành
    @ManyToOne
    @JoinColumn(name = "nganh_id") // cột khóa ngoại trong bảng hoc_phan
    private Nganh nganh;

    @OneToMany(mappedBy = "hocPhan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LopHocPhan> listLopHP = new ArrayList<>();

    // ========== Getters & Setters ==========

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaHocPhan() {
        return maHocPhan;
    }

    public void setMaHocPhan(String maHocPhan) {
        this.maHocPhan = maHocPhan;
    }

    public String getTenHocPhan() {
        return tenHocPhan;
    }

    public void setTenHocPhan(String tenHocPhan) {
        this.tenHocPhan = tenHocPhan;
    }

    public Integer getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(Integer soTinChi) {
        this.soTinChi = soTinChi;
    }

    public Integer getSoTietLyThuyet() {
        return soTietLyThuyet;
    }

    public void setSoTietLyThuyet(Integer soTietLyThuyet) {
        this.soTietLyThuyet = soTietLyThuyet;
    }

    public Integer getSoTietThucHanh() {
        return soTietThucHanh;
    }

    public void setSoTietThucHanh(Integer soTietThucHanh) {
        this.soTietThucHanh = soTietThucHanh;
    }

    public Integer getHocPhi() {
        return hocPhi;
    }

    public void setHocPhi(Integer hocPhi) {
        this.hocPhi = hocPhi;
    }

    public Nganh getNganh() {
        return nganh;
    }

    public void setNganh(Nganh nganh) {
        this.nganh = nganh;
    }

    public List<LopHocPhan> getListLopHP() {
        return listLopHP;
    }

    public void setListLopHP(List<LopHocPhan> listLopHP) {
        this.listLopHP = listLopHP;
    }
}