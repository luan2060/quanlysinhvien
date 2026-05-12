package com.example.quanlysinhvien.Controller;

import com.example.quanlysinhvien.Model.HocPhan;
import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.Service.HocPhanService;
import com.example.quanlysinhvien.Service.NganhService;
import com.example.quanlysinhvien.repository.NhanvienRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/hocphan")
public class HocPhanController {

    @Autowired
    private HocPhanService hocPhanService;

    @Autowired
    private NganhService nganhService;

    @Autowired
    private NhanvienRepository nhanvienRepository;

    // ===================== LIST + SEARCH =====================
    @GetMapping
    public String index(@RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
        model.addAttribute("listHocPhan", hocPhanService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "admin/HocPhan";
    }

    // ===================== CREATE =====================
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("hocphan", new HocPhan());
        model.addAttribute("listgiangvien", nhanvienRepository.findAll());
        model.addAttribute("danhSachNganh", nganhService.findAll());
        return "layout/Hocphan/create";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute("hocphan") HocPhan hocPhan,
            @RequestParam(value = "nganhId", required = false) Integer nganhId,
            @RequestParam(value = "giangVienIds", required = false) List<Integer> giangVienIds,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        try {
            List<LopHocPhan> listLop = parseLopHPFromRequest(request);
            hocPhanService.save(hocPhan, listLop, giangVienIds, nganhId);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm học phần thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/hocphan";
    }

    // ===================== UPDATE =====================
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<HocPhan> opt = hocPhanService.findById(id);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy học phần!");
            return "redirect:/admin/hocphan";
        }
        model.addAttribute("hocphan", opt.get());
        model.addAttribute("listgiangvien", nhanvienRepository.findAll());
        model.addAttribute("danhSachNganh", nganhService.findAll());
        return "layout/Hocphan/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("hocphan") HocPhan hocPhan,
            @RequestParam(value = "nganhId", required = false) Integer nganhId,
            @RequestParam(value = "giangVienIds", required = false) List<Integer> giangVienIds,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        try {
            List<LopHocPhan> listLop = parseLopHPFromRequest(request);
            hocPhanService.update(hocPhan, listLop, giangVienIds, nganhId);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/hocphan";
    }

    // ===================== DELETE =====================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes) {
        try {
            hocPhanService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa học phần thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không thể xóa! Học phần này đang có dữ liệu liên quan.");
        }
        return "redirect:/admin/hocphan";
    }

    // ===================== PARSE LỚP HP TỪ FORM =====================
    private List<LopHocPhan> parseLopHPFromRequest(HttpServletRequest request) {
        List<LopHocPhan> result = new ArrayList<>();
        List<String> maDaThem = new ArrayList<>();

        int i = 0;
        while (request.getParameter("listLopHP[" + i + "].maLopHP") != null) {
            String maLop = request.getParameter("listLopHP[" + i + "].maLopHP");
            if (maLop == null || maLop.trim().isEmpty()) {
                i++;
                continue;
            }

            maLop = maLop.trim();

            if (maDaThem.contains(maLop)) {
                throw new RuntimeException("Mã lớp học phần '" + maLop + "' bị trùng trong danh sách!");
            }
            maDaThem.add(maLop);

            LopHocPhan lop = new LopHocPhan();
            lop.setMaLopHP(maLop);
            lop.setTenLopHP(request.getParameter("listLopHP[" + i + "].tenLopHP"));
            lop.setGioHoc(request.getParameter("listLopHP[" + i + "].gioHoc"));
            lop.setPhongHoc(request.getParameter("listLopHP[" + i + "].phongHoc"));
            lop.setNamHoc(request.getParameter("listLopHP[" + i + "].namHoc"));

            // ✅ Parse thuTrongTuan
            String thuStr = request.getParameter("listLopHP[" + i + "].thuTrongTuan");
            if (thuStr != null && !thuStr.isEmpty()) {
                lop.setThuTrongTuan(Integer.parseInt(thuStr));
            }

            // ✅ Parse caHoc
            String caStr = request.getParameter("listLopHP[" + i + "].caHoc");
            if (caStr != null && !caStr.isEmpty()) {
                lop.setCaHoc(Integer.parseInt(caStr));
            }

            String hocKyStr = request.getParameter("listLopHP[" + i + "].hocKy");
            if (hocKyStr != null && !hocKyStr.isEmpty()) {
                lop.setHocKy(Integer.parseInt(hocKyStr));
            }

            String siSoStr = request.getParameter("listLopHP[" + i + "].siSoToiDa");
            if (siSoStr != null && !siSoStr.isEmpty()) {
                lop.setSiSoToiDa(Integer.parseInt(siSoStr));
            }

            String idStr = request.getParameter("listLopHP[" + i + "].id");
            if (idStr != null && !idStr.isEmpty()) {
                lop.setId(Integer.parseInt(idStr));
            }

            String gvIdStr = request.getParameter("listLopHP[" + i + "].giangVien.id");
            if (gvIdStr != null && !gvIdStr.isEmpty()) {
                Nhanvien gv = new Nhanvien();
                gv.setId(Integer.parseInt(gvIdStr));
                lop.setGiangVien(gv);
            }

            result.add(lop);
            i++;
        }
        return result;
    }
}