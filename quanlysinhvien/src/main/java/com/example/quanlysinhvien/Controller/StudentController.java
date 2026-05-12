package com.example.quanlysinhvien.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Model.UpdateProfileDTO;
import com.example.quanlysinhvien.Service.DangKyHocPhanService;
import com.example.quanlysinhvien.Service.SinhVienIFService;

import org.springframework.ui.Model;
import jakarta.validation.Valid;

@Controller
public class StudentController {
    private final SinhVienIFService sinhVienService;

    private final DangKyHocPhanService dangKyService;

    // Cập nhật constructor (thêm tham số mới)
    public StudentController(SinhVienIFService sinhVienService,
            DangKyHocPhanService dangKyService) {
        this.sinhVienService = sinhVienService;
        this.dangKyService = dangKyService;
    }

    @GetMapping("/sinhvien/trangchu")
    public String showtrangchusinhvien() {
        return ("Sinhvien/Trangchu");
    }

    @GetMapping("/sinhvien/trangcanhan")
    public String showTCNsinhvien(Model model) {
        model.addAttribute("sinhVien", sinhVienService.getCurrentSinhVien());
        UpdateProfileDTO dto = new UpdateProfileDTO();
        dto.setSdt(sinhVienService.getCurrentSinhVien().getSdt());
        dto.setDiaChi(sinhVienService.getCurrentSinhVien().getDiaChi());
        model.addAttribute("updateProfileDTO", dto);
        return "Sinhvien/TCN";
    }

    @PostMapping("/sinhvien/trangcanhan/capnhat")
    public String updateProfile(
            @Valid @ModelAttribute("updateProfileDTO") UpdateProfileDTO dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("sinhVien", sinhVienService.getCurrentSinhVien());
            model.addAttribute("showModal", true);
            return "Sinhvien/TCN";
        }
        sinhVienService.updateProfile(dto);
        redirectAttributes.addFlashAttribute("successMsg", "Cập nhật thành công!");
        return "redirect:/sinhvien/trangcanhan";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "layout/login"; // → templates/login.html
    }

    @GetMapping("/sinhvien/hocphan")
    public String showDangKyHocPhan(
            @RequestParam(defaultValue = "1") Integer hocKy,
            @RequestParam(defaultValue = "2024-2025") String namHoc,
            Model model) {

        List<LopHocPhan> danhSachLop = dangKyService.getDanhSachLop(hocKy, namHoc);

        // ✅ Tránh null khi chưa có dữ liệu
        if (danhSachLop == null) {
            danhSachLop = new java.util.ArrayList<>();
        }

        Set<Integer> idLopDaDangKy = dangKyService.getIdLopDaDangKy(hocKy, namHoc);
        int tongTinChi = dangKyService.getTongTinChi(hocKy, namHoc);

        model.addAttribute("danhSachLop", danhSachLop);
        model.addAttribute("idLopDaDangKy", idLopDaDangKy);
        model.addAttribute("tongTinChi", tongTinChi);
        model.addAttribute("hocKy", hocKy);
        model.addAttribute("namHoc", namHoc);

        return "Sinhvien/DKhocphan";
    }

    @PostMapping("/sinhvien/hocphan/dang-ky")
    public String dangKy(
            @RequestParam int lopId,
            @RequestParam(defaultValue = "1") Integer hocKy,
            @RequestParam(defaultValue = "2024-2025") String namHoc,
            RedirectAttributes redirectAttributes) {
        try {
            dangKyService.dangKy(lopId);
            redirectAttributes.addFlashAttribute("successMsg", "Đăng ký học phần thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/sinhvien/hocphan?hocKy=" + hocKy + "&namHoc=" + namHoc;
    }

    @PostMapping("/sinhvien/hocphan/huy-dang-ky")
    public String huyDangKy(
            @RequestParam int lopId,
            @RequestParam(defaultValue = "1") Integer hocKy,
            @RequestParam(defaultValue = "2024-2025") String namHoc,
            RedirectAttributes redirectAttributes) {
        try {
            dangKyService.huyDangKy(lopId);
            redirectAttributes.addFlashAttribute("successMsg", "Hủy đăng ký thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/sinhvien/hocphan?hocKy=" + hocKy + "&namHoc=" + namHoc;
    }

}
