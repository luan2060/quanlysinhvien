package com.example.quanlysinhvien.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chuong_trinh_dao_taos")
public class ChuongTrinhDaoTao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ma_ctdt", nullable = false, unique = true)
    private String maCTDT;

    @Column(name = "ten_ctdt", nullable = false)
    private String tenCTDT;

    private Integer namApDung;

    private String trangThai;
    @ManyToOne
    @JoinColumn(name = "nganh_id")
    private Nganh nganh;

    public ChuongTrinhDaoTao() {
    }

    public ChuongTrinhDaoTao(int id, String maCTDT, String tenCTDT, Integer namApDung, Nganh nganh, String trangThai) {
        this.id = id;
        this.maCTDT = maCTDT;
        this.tenCTDT = tenCTDT;
        this.namApDung = namApDung;
        this.nganh = nganh;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaCTDT() {
        return maCTDT;
    }

    public void setMaCTDT(String maCTDT) {
        this.maCTDT = maCTDT;
    }

    public String getTenCTDT() {
        return tenCTDT;
    }

    public void setTenCTDT(String tenCTDT) {
        this.tenCTDT = tenCTDT;
    }

    public Integer getNamApDung() {
        return namApDung;
    }

    public void setNamApDung(Integer namApDung) {
        this.namApDung = namApDung;
    }

    public Nganh getNganh() {
        return nganh;
    }

    public void setNganh(Nganh nganh) {
        this.nganh = nganh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}
