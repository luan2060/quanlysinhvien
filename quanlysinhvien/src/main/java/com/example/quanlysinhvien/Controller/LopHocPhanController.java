package com.example.quanlysinhvien.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.repository.LopHocPhanRepository;

@Controller
@RequestMapping("/admin/lophocphan")
public class LopHocPhanController {

    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;

    @GetMapping("/{id}/sinhvien")
    public String xemSinhVien(@PathVariable("id") Integer id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<LopHocPhan> lopOpt = lopHocPhanRepository.findById(id);
        if (lopOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lớp học phần!");
            return "redirect:/admin/hocphan";
        }
        LopHocPhan lop = lopOpt.get();
        model.addAttribute("lophp", lop);
        model.addAttribute("danhSachKetQua", lop.getDanhSachKetQua());
        return "admin/lophocphan/sinhvien";
    }
}