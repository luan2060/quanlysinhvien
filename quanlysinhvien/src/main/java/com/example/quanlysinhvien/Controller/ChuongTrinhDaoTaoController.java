package com.example.quanlysinhvien.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quanlysinhvien.Model.ChuongTrinhDaoTao;
import com.example.quanlysinhvien.Model.Nganh;
import com.example.quanlysinhvien.Service.ChuongTrinhDaoTaoService;
import com.example.quanlysinhvien.repository.NganhRepository;

@Controller
@RequestMapping("/admin/ctdt")
public class ChuongTrinhDaoTaoController {

    private final ChuongTrinhDaoTaoService ctdtService;
    private final NganhRepository nganhRepo;

    public ChuongTrinhDaoTaoController(ChuongTrinhDaoTaoService ctdtService, NganhRepository nganhRepo) {
        this.ctdtService = ctdtService;
        this.nganhRepo = nganhRepo;
    }

    // Danh sách
    @GetMapping
    public String danhSach(Model model) {
        List<ChuongTrinhDaoTao> list = ctdtService.getAll();
        model.addAttribute("listCTDT", list);
        model.addAttribute("tongCTDT", list.size());
        model.addAttribute("ctdtDangApDung", ctdtService.countDangApDung());
        model.addAttribute("ctdtNgungApDung", ctdtService.countNgungApDung());
        return "/admin/CTDT";
    }

    // Form thêm mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("ctdt", new ChuongTrinhDaoTao());
        model.addAttribute("danhSachNganh", ctdtService.getAllNganh());
        return "layout/CTDT/create";
    }

    // Lưu mới
    @PostMapping("/create")
    public String save(@ModelAttribute ChuongTrinhDaoTao ctdt,
            @RequestParam("nganhid") int nganhid) {
        Nganh nganh = nganhRepo.findById(nganhid).orElse(null);
        ctdt.setNganh(nganh);
        ctdtService.save(ctdt);
        return "redirect:/admin/ctdt";
    }

    // Form sửa
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        model.addAttribute("ctdt", ctdtService.getById(id));
        model.addAttribute("danhSachNganh", ctdtService.getAllNganh());
        return "layout/CTDT/update";
    }

    // Lưu sửa
    @PostMapping("/update{id}")
    public String update(@ModelAttribute ChuongTrinhDaoTao ctdt,
            @RequestParam("nganhid") int nganhid) {
        Nganh nganh = nganhRepo.findById(nganhid).orElse(null);
        ctdt.setNganh(nganh);
        ctdtService.save(ctdt);
        return "redirect:/admin/ctdt";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        ctdtService.delete(id);
        return "redirect:/admin/ctdt";
    }
}
