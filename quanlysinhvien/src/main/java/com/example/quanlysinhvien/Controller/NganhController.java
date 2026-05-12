package com.example.quanlysinhvien.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlysinhvien.Model.Nganh;
import com.example.quanlysinhvien.Service.NganhService;
import com.example.quanlysinhvien.repository.KhoaRepository;

import org.springframework.ui.Model;

// NganhController.java
@Controller
@RequestMapping("/admin/nganh")
public class NganhController {

    @Autowired
    private NganhService nganhService;

    @Autowired
    private KhoaRepository khoaRepository;

    // ===================== LIST + SEARCH =====================
    @GetMapping
    public String index(@RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
        List<Nganh> listNganh = nganhService.search(keyword);
        model.addAttribute("listNganh", listNganh);
        model.addAttribute("keyword", keyword);
        model.addAttribute("tongNganh", nganhService.findAll().size());
        model.addAttribute("nganhHoatDong", nganhService.countHoatDong());
        model.addAttribute("nganhTamDung", nganhService.countTamDung());
        return "admin/nganh"; // ✅ sửa lại đúng path template
    }

    // ===================== CREATE =====================
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("nganh", new Nganh());
        model.addAttribute("listkhoa", khoaRepository.findAll());
        return "layout/nganh/create";
    }

    @PostMapping("/create") // ✅ đổi từ /admin/nganh/create → /save
    public String save(@ModelAttribute("nganh") Nganh nganh,
            @RequestParam("khoaid") Integer khoaId,
            RedirectAttributes redirectAttributes) {
        try {
            nganhService.save(nganh, khoaId);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm ngành thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/nganh";
    }

    // ===================== UPDATE =====================
    @GetMapping("/update/{id}") // ✅ bỏ admin/nganh/ ở đầu
    public String showUpdateForm(@PathVariable("id") Integer id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Nganh> nganhOpt = nganhService.findById(id);
        if (nganhOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy ngành!");
            return "layout/nganh/update";
        }
        model.addAttribute("nganh", nganhOpt.get());
        model.addAttribute("listkhoa", khoaRepository.findAll());
        return "layout/nganh/update"; // ✅ sửa lại đúng path template
    }

    @PostMapping("/update") // ✅ bỏ /admin/nganh/ ở đầu
    public String update(@ModelAttribute("nganh") Nganh nganh,
            @RequestParam("khoaid") Integer khoaId,
            RedirectAttributes redirectAttributes) {
        try {
            nganhService.update(nganh, khoaId);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật ngành thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/nganh";
    }

    // ===================== DELETE =====================
    @GetMapping("/delete/{id}") // ✅ bỏ admin/nganh/ ở đầu
    public String delete(@PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes) {
        try {
            nganhService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa ngành thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không thể xóa! Ngành này đang có dữ liệu liên quan.");
        }
        return "redirect:/admin/nganh";
    }
}