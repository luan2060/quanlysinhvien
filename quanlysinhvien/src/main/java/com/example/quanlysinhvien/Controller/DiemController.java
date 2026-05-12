package com.example.quanlysinhvien.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlysinhvien.Model.KetQuaHocTap;
import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Service.DiemService;

@Controller
@RequestMapping("/admin/diem")
public class DiemController {

    @Autowired
    private DiemService diemService;

    // ---------------------------------------------------------------
    // GET /admin/diem
    // Trang chọn lớp học phần để nhập/sửa điểm
    // → Thymeleaf: layout/Diem/danhsach.html
    // → Model: listLopHP
    // ---------------------------------------------------------------
    @GetMapping
    public String danhSachLopHocPhan(Model model) {
        List<LopHocPhan> listLopHP = diemService.getAllLopHocPhan();
        model.addAttribute("listLopHP", listLopHP);
        return "admin/NhapDiem";
    }

    // ---------------------------------------------------------------
    // GET /admin/diem/lophocphan/{id}
    // Bảng điểm chi tiết của một lớp học phần
    // → Thymeleaf: layout/Diem/chitiet.html
    // → Model: lophocphan, listDiem
    // ---------------------------------------------------------------
    @GetMapping("/lophocphan/{id}")
    public String bangDiemChiTiet(@PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes) {
        Optional<LopHocPhan> opt = diemService.getLopHocPhanById(id);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lớp học phần!");
            return "redirect:/admin/diem";
        }

        List<KetQuaHocTap> listDiem = diemService.getDiemByLopHocPhan(id);
        model.addAttribute("lophocphan", opt.get());
        model.addAttribute("listDiem", listDiem);
        return "layout/diem/update";
    }

    // ---------------------------------------------------------------
    // POST /admin/diem/update-inline
    // Lưu điểm inline từ nút Sửa/Lưu trên từng dòng sinh viên
    //
    // Params nhận từ form HTML (form ẩn trong chitiet.html):
    // id → ID bản ghi KetQua cần update
    // lopHocPhanId → để redirect về đúng lớp sau khi lưu
    // diemChuyenCan → Float, nullable
    // diemGiuaKy → Float, nullable
    // diemCuoiKy → Float, nullable
    // ---------------------------------------------------------------
    @PostMapping("/update-inline")
    public String updateDiemInline(@RequestParam Integer id,
            @RequestParam Integer lopHocPhanId,
            @RequestParam(required = false) Float diemChuyenCan,
            @RequestParam(required = false) Float diemGiuaKy,
            @RequestParam(required = false) Float diemCuoiKy,
            RedirectAttributes redirectAttributes) {
        try {
            validateDiem(diemChuyenCan, "Chuyên Cần");
            validateDiem(diemGiuaKy, "Giữa Kỳ");
            validateDiem(diemCuoiKy, "Cuối Kỳ");

            diemService.capNhatDiem(id, diemChuyenCan, diemGiuaKy, diemCuoiKy);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật điểm thành công!");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật điểm: " + e.getMessage());
        }

        return "redirect:/admin/diem/lophocphan/" + lopHocPhanId;
    }

    // ---------------------------------------------------------------
    // GET /admin/diem/delete/{id}?lopHocPhanId=xxx
    // Xóa một bản ghi điểm, redirect về đúng trang lớp sau khi xóa
    //
    // Trong HTML truyền thêm lopHocPhanId vào URL:
    // th:href="@{/admin/diem/delete/{id}(id=${d.id},
    // lopHocPhanId=${lophocphan.id})}"
    // ---------------------------------------------------------------
    @GetMapping("/delete/{id}")
    public String xoaDiem(@PathVariable Integer id,
            @RequestParam(required = false) Integer lopHocPhanId,
            RedirectAttributes redirectAttributes) {
        try {
            // Nếu không truyền lopHocPhanId thì tự lấy từ bản ghi trước khi xóa
            if (lopHocPhanId == null) {
                Optional<KetQuaHocTap> kq = diemService.getKetQuaById(id);
                lopHocPhanId = kq.map(k -> k.getLopHocPhan().getId()).orElse(null);
            }

            diemService.xoaKetQua(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa bản ghi điểm thành công!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa: " + e.getMessage());
        }

        if (lopHocPhanId != null) {
            return "redirect:/admin/diem/lophocphan/" + lopHocPhanId;
        }
        return "redirect:/admin/diem";
    }

    // ---------------------------------------------------------------
    // HELPER: Validate điểm phải trong khoảng 0 - 10
    // ---------------------------------------------------------------
    private void validateDiem(Float diem, String tenDiem) {
        if (diem != null && (diem < 0f || diem > 10f)) {
            throw new IllegalArgumentException("Điểm " + tenDiem + " phải trong khoảng 0 - 10!");
        }
    }
}
