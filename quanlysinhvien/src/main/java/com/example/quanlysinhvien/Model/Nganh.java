package com.example.quanlysinhvien.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "nganhs")
public class Nganh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_nganh", nullable = false, unique = true)
    private String maNganh;

    @Column(name = "ten_nganh", nullable = false)
    private String tenNganh;

    private String trangThai;
    @ManyToOne
    @JoinColumn(name = "khoa_id")
    private Khoa khoa;
    @OneToMany(mappedBy = "nganh", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LopHanhChinh> listLopHanhChinh;
    @OneToMany(mappedBy = "nganh", cascade = CascadeType.ALL)
    private List<HocPhan> listHocPhan;

    @OneToMany(mappedBy = "nganh", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChuongTrinhDaoTao> listChuongTrinh;

    public Nganh() {
    }

    public Nganh(Integer id, String maNganh, String tenNganh, String trangThai, Khoa khoa,
            List<LopHanhChinh> listLopHanhChinh, List<HocPhan> listHocPhan, List<ChuongTrinhDaoTao> listChuongTrinh) {
        this.id = id;
        this.maNganh = maNganh;
        this.tenNganh = tenNganh;
        this.trangThai = trangThai;
        this.khoa = khoa;
        this.listLopHanhChinh = listLopHanhChinh;
        this.listHocPhan = listHocPhan;
        this.listChuongTrinh = listChuongTrinh;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public Khoa getKhoa() {
        return khoa;
    }

    public void setKhoa(Khoa khoa) {
        this.khoa = khoa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public List<ChuongTrinhDaoTao> getListChuongTrinh() {
        return listChuongTrinh;
    }

    public void setListChuongTrinh(List<ChuongTrinhDaoTao> listChuongTrinh) {
        this.listChuongTrinh = listChuongTrinh;
    }

    public List<LopHanhChinh> getListLopHanhChinh() {
        return listLopHanhChinh;
    }

    public void setListLopHanhChinh(List<LopHanhChinh> listLopHanhChinh) {
        this.listLopHanhChinh = listLopHanhChinh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<HocPhan> getListHocPhan() {
        return listHocPhan;
    }

    public void setListHocPhan(List<HocPhan> listHocPhan) {
        this.listHocPhan = listHocPhan;
    }

}
