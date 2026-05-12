package com.example.quanlysinhvien.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quanlysinhvien.Model.HocPhan;
import com.example.quanlysinhvien.Model.KetQuaHocTap;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.Service.KetQuaHocTapService;
import com.example.quanlysinhvien.repository.KetQuaHocTapRepository;
import com.example.quanlysinhvien.repository.SinhVienRepository;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {

    private final KetQuaHocTapService ketQuaService;
    private final SinhVienRepository sinhVienRepo;
    private final KetQuaHocTapRepository ketQuaRepo;

    public SinhVienController(KetQuaHocTapService ketQuaService,
            SinhVienRepository sinhVienRepo,
            KetQuaHocTapRepository ketQuaRepo) {
        this.ketQuaService = ketQuaService;
        this.sinhVienRepo = sinhVienRepo;
        this.ketQuaRepo = ketQuaRepo;
    }

    @GetMapping("/diem")
    public String xemKetQua(
            @RequestParam(required = false) Integer hocKy,
            @RequestParam(required = false) String namHoc,
            Model model,
            Principal principal) {
        String email = principal.getName();

        List<KetQuaHocTap> danhSach;
        if (hocKy != null && namHoc != null) {
            danhSach = ketQuaService.getKetQuaByHocKy(email, hocKy, namHoc);
        } else {
            danhSach = ketQuaService.getAllKetQua(email);
        }

        double gpa = ketQuaService.tinhGPA(danhSach);
        double tb10 = ketQuaService.tinhTBHe10(danhSach);
        int tinChiTichLuy = ketQuaService.tinhTinChiTichLuy(danhSach);

        model.addAttribute("danhSachKetQua", danhSach);
        model.addAttribute("gpa", String.format("%.2f", gpa));
        model.addAttribute("tb10", String.format("%.2f", tb10));
        model.addAttribute("tinChiTichLuy", tinChiTichLuy);
        model.addAttribute("xepLoai", ketQuaService.xepLoai(gpa));
        model.addAttribute("hocKyChon", hocKy);
        model.addAttribute("namHocChon", namHoc);

        return "sinhvien/Diem";
    }

    @GetMapping("/taichinh")
    public String xemTaiChinh(
            @RequestParam(required = false) Integer hocKy,
            @RequestParam(required = false) String namHoc,
            Model model,
            Principal principal) {
        String email = principal.getName();
        SinhVien sv = sinhVienRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

        int hk = (hocKy != null) ? hocKy : 1; // ← Sửa mặc định từ 2 → 1
        String nh = (namHoc != null) ? namHoc : "2025-2026";

        List<KetQuaHocTap> danhSach = ketQuaRepo
                .findBySinhVien_IdAndLopHocPhan_HocKyAndLopHocPhan_NamHoc(sv.getId(), hk, nh);

        List<Map<String, Object>> chiTiet = new ArrayList<>();
        long tongHocPhi = 0;

        for (KetQuaHocTap kq : danhSach) {
            Map<String, Object> item = new HashMap<>();
            HocPhan hp = kq.getLopHocPhan().getHocPhan();

            int donGia = (hp.getHocPhi() != null) ? hp.getHocPhi() : 0;
            int soTC = (hp.getSoTinChi() != null) ? hp.getSoTinChi() : 0;
            long thanhTien = (long) donGia * soTC;
            tongHocPhi += thanhTien;

            item.put("kq", kq);
            item.put("donGia", donGia);
            item.put("soTinChi", soTC);
            item.put("thanhTien", thanhTien);
            chiTiet.add(item);
        }

        model.addAttribute("chiTiet", chiTiet);
        model.addAttribute("tongHocPhi", tongHocPhi);
        model.addAttribute("hocKyChon", hk);
        model.addAttribute("namHocChon", nh);
        model.addAttribute("sinhVien", sv);

        return "sinhvien/taichinh";
    }
}