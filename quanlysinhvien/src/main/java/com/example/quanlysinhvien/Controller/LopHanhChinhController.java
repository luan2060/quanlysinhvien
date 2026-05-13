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

import com.example.quanlysinhvien.Model.LopHanhChinh;
import com.example.quanlysinhvien.Service.LopHanhChinhService;
import com.example.quanlysinhvien.repository.NganhRepository;
import com.example.quanlysinhvien.repository.NhanvienRepository;

import org.springframework.ui.Model;

// LopHanhChinhController.java
@Controller
@RequestMapping("/admin/lop")
public class LopHanhChinhController {

    @Autowired
    private LopHanhChinhService lopService;

    @Autowired
    private NganhRepository nganhRepository;

    @Autowired
    private NhanvienRepository nhanvienRepository;

    // ===================== LIST + SEARCH =====================
    @GetMapping
    public String index(@RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
        List<LopHanhChinh> listLop = lopService.search(keyword);

        model.addAttribute("listLop", listLop);
        model.addAttribute("keyword", keyword);
        model.addAttribute("tongLop", lopService.findAll().size());
        model.addAttribute("lopDangHoc", lopService.countDangHoc());
        model.addAttribute("lopDaTotNghiep", lopService.countDaTotNghiep());

        return "/admin/Lop";
    }

    // ===================== CREATE =====================
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("lophc", new LopHanhChinh());
        model.addAttribute("listnganh", nganhRepository.findAll());
        model.addAttribute("listcovan", nhanvienRepository.findAll());
        return "layout/lop/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("lophc") LopHanhChinh lop,
            @RequestParam("nganhid") Integer nganhId,
            @RequestParam("covanid") Integer covanId,
            RedirectAttributes redirectAttributes) {
        try {
            lopService.save(lop, nganhId, covanId);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm lớp thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/lop";
    }

    // ===================== UPDATE =====================
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<LopHanhChinh> lopOpt = lopService.findById(id);
        if (lopOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lớp!");
            return "redirect:/admin/lop";
        }
        model.addAttribute("lophc", lopOpt.get());
        model.addAttribute("listnganh", nganhRepository.findAll());
        model.addAttribute("listcovan", nhanvienRepository.findAll());
        return "layout/lop/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("lophc") LopHanhChinh lop,
            @RequestParam("nganhid") Integer nganhId,
            @RequestParam("covanid") Integer covanId,
            RedirectAttributes redirectAttributes) {
        try {
            lopService.update(lop, nganhId, covanId);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật lớp thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/lop";
    }

    // ===================== DELETE =====================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes) {
        try {
            lopService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa lớp thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không thể xóa! Lớp này đang có sinh viên liên quan.");
        }
        return "redirect:/admin/lop";
    }
}