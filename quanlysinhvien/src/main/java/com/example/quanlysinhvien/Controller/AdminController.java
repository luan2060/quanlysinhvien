package com.example.quanlysinhvien.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quanlysinhvien.Model.Khoa;
import com.example.quanlysinhvien.Model.LopHanhChinh;
import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.Service.KhoaService;
import com.example.quanlysinhvien.Service.LopHanhChinhService;
import com.example.quanlysinhvien.Service.NhanvienService;
import com.example.quanlysinhvien.Service.SinhVienService;

import jakarta.validation.Valid;

@Controller
public class AdminController {
    // private final StudentService studentService;
    // private final NhanvienService nhanvienService;
    // private final KhoaService khoaService;

    // public AdminController(StudentService studentService, NhanvienService
    // nhanvienService, KhoaService khoaService) {
    // this.studentService = studentService;
    // this.nhanvienService = nhanvienService;
    // this.khoaService = khoaService;
    // }

    // @GetMapping("/admin/tongquan")
    // public String showuser(Model model) {

    // List<Student> studentList = this.studentService.fetchStudents();
    // model.addAttribute("students", studentList);

    // List<Nhanvien> nhanvienList = this.nhanvienService.fetchNhanvien();
    // model.addAttribute("nhanviens", nhanvienList);
    // long totalStudents = this.studentService.countStudents();
    // model.addAttribute("totalStudents", totalStudents);

    // return "admin/homePage";
    // }

    // @GetMapping("/admin/nganh")
    // public String shownganh() {
    // return ("/admin/qlnganh");
    // }

    // @GetMapping("/admin/nhapdiem")
    // public String showdiem() {
    // return ("/admin/nhapDiem");

    // }

    // @GetMapping("/admin/hocphan")
    // public String showhocphan() {
    // return ("/admin/qlHocPhan");

    // }

    // @GetMapping("/admin/lop")
    // public String shownlop() {
    // return ("/admin/qlLop");

    // }

    // // SINH VIÊN
    // @GetMapping("/admin/sinhvien")
    // public String showsinhvien(
    // @RequestParam(value = "keyword", required = false) String keyword,
    // Model model) {

    // List<Student> studentList;
    // if (keyword != null && !keyword.trim().isEmpty()) {
    // studentList = this.studentService.findSinhVienByname(keyword);
    // } else {
    // studentList = this.studentService.fetchStudents();
    // }
    // model.addAttribute("students", studentList);
    // model.addAttribute("keyword", keyword);
    // long totalStudents = this.studentService.countStudents();
    // model.addAttribute("totalStudents", totalStudents);

    // long dangHoc = this.studentService.countSinhVienByTrangThai("Đang học");
    // long baoLuu = this.studentService.countSinhVienByTrangThai("Bảo lưu");
    // long nghiHoc = this.studentService.countSinhVienByTrangThai("Nghỉ học");

    // model.addAttribute("dangHoc", dangHoc);
    // model.addAttribute("baoLuu", baoLuu);
    // model.addAttribute("nghiHoc", nghiHoc);

    // return "admin/qlSinhVien";
    // }

    // @GetMapping("/admin/sinhvien/create")
    // public String getcreatesinhvien(Model model) {
    // model.addAttribute("student", new Student());
    // return "layout/Student/createSV";
    // }

    // @PostMapping("/admin/sinhvien/create")
    // public String postcreatesinhvien(@Valid @ModelAttribute Student
    // createStudent,
    // BindingResult bindingResult,
    // Model model) {

    // if (bindingResult.hasErrors()) {
    // model.addAttribute("students", studentService.fetchStudents());
    // return "admin/qlSinhVien";
    // }
    // this.studentService.createStudent(createStudent);
    // return "redirect:/admin/sinhvien";

    // }

    // }

    // // Nhan Viên
    // @GetMapping("/admin/nhanvien")
    // public String showgiangvien(Model model, @RequestParam(value = "keyword",
    // required = false) String keyword) {

    // List<Nhanvien> nhanvienList;
    // if (keyword != null && !keyword.trim().isEmpty()) {
    // nhanvienList = this.nhanvienService.findNhanVienByname(keyword);
    // } else {
    // nhanvienList = this.nhanvienService.fetchNhanvien();
    // }
    // model.addAttribute("nhanvien", nhanvienList);
    // model.addAttribute("keyword", keyword);

    // model.addAttribute("totalNV", nhanvienService.countAll());
    // // model.addAttribute("activeNV", nhanvienService.countActive());
    // // model.addAttribute("retiredNV", nhanvienService.countRetired());
    // model.addAttribute("expertNV", nhanvienService.countHighDegree());

    // return "/admin/qlnhanvien";
    // }

    // @GetMapping("admin/nhanvien/update/{id}")
    // public String getupdatenhanvien(@PathVariable("id") int id, Model model) {
    // Nhanvien updatnhanvien = this.nhanvienService.findNhanvienByID(id);
    // model.addAttribute("nhanvien", updatnhanvien);
    // model.addAttribute("id", id);
    // return ("layout/nhanvien/updateNV");
    // }

    // @PostMapping("admin/nhanvien/update")
    // public String postUpdatenhavien(
    // @Valid @ModelAttribute("nhanvien") Nhanvien updatenhanvien,
    // BindingResult bindingResult,
    // Model model) {

    // if (bindingResult.hasErrors()) {
    // model.addAttribute("nhanvien", updatenhanvien);
    // model.addAttribute("id", updatenhanvien.getId());
    // }

    // this.nhanvienService.updateNhanvien(updatenhanvien);

    // return "redirect:/admin/nhanvien";

    // }

    // @PostMapping("admin/nhanvien/delete/{id}")
    // public String deleteById(@PathVariable("id") int id) {
    // this.nhanvienService.deleteById(id);
    // return ("redirect:/admin/nhanvien");
    // }

    // // KHOA
    // @GetMapping("/admin/khoa")
    // public String showkhoa(Model model) {

    // List<Khoa> khoaList = khoaService.fetchKhoas();
    // model.addAttribute("khoas", khoaList);

    // return "admin/qlkhoa";
    // }

    // @GetMapping("admin/khoa/add")
    // public String getcreatekhoa(Model model) {
    // model.addAttribute("khoa", new Khoa());
    // return "admin/qlkhoa";
    // }

    // @PostMapping("/admin/khoa/add")
    // public String postcreatekhoa(@Valid @ModelAttribute Khoa createkhoa,
    // BindingResult bindingResult,
    // Model model) {

    // if (bindingResult.hasErrors()) {
    // model.addAttribute("khoas", khoaService.fetchKhoas());
    // return "admin/qlkhoa";
    // }

    // khoaService.createKhoa(createkhoa);
    // return "redirect:/admin/khoa";
    // }

    // @GetMapping("admin/khoa/{id}")
    // public String getupdatekhoa(Model model, @PathVariable("id") int id) {
    // Khoa updatekhoa = this.khoaService.findKhoaById(id);
    // model.addAttribute("khoa", updatekhoa);
    // model.addAttribute("id", id);
    // return "admin/khoa";
    // }

    // @PostMapping("admin/khoa/update")
    // public String postUpdateKhoa(
    // @Valid @ModelAttribute("khoa") Khoa updateKhoa,
    // BindingResult bindingResult,
    // Model model) {

    // if (bindingResult.hasErrors()) {
    // model.addAttribute("khoa", updateKhoa);
    // model.addAttribute("id", updateKhoa.getId());
    // }

    // khoaService.updateKhoa(updateKhoa);

    // return "admin/khoa";

    // }

    // @PostMapping("/admin/khoa/delete/{id}")
    // public String postdeleteKhoa(@PathVariable int id) {
    // this.khoaService.deleteKhoaById(id);
    // return "redirect:/admin/khoa";
    // }

    private final SinhVienService sinhVienService;
    private final LopHanhChinhService lopHanhChinhService;
    private final NhanvienService nhanvienService;
    private final KhoaService khoaService;

    public AdminController(SinhVienService sinhVienService, LopHanhChinhService lopHanhChinhService,
            KhoaService khoaService,
            NhanvienService nhanvienService) {
        this.sinhVienService = sinhVienService;
        this.lopHanhChinhService = lopHanhChinhService;
        this.nhanvienService = nhanvienService;
        this.khoaService = khoaService;
    }

    @GetMapping("admin/trangchu")
    public String showTongQuan(Model model) {
        long total = this.sinhVienService.countTotalSinhVien();
        model.addAttribute("tongSinhVien", total);

        // biểu đô tăng trưởng

        List<Long> dataGrowth = Arrays.asList(0L, 0L, 0L, 0L, 0L, total);
        model.addAttribute("dataGrowth", dataGrowth);
        long tongkhoa = this.khoaService.counttotalkhoa();
        model.addAttribute("tongkhoa", tongkhoa);
        long tonglop = this.lopHanhChinhService.counttotallop();
        model.addAttribute("tonglop", tonglop);
        model.addAttribute("listKhoa", sinhVienService.getPhanTramTheoKhoa());
        model.addAttribute("tyLe", sinhVienService.getTyLeTotNghiep());

        return "admin/TrangChu";
    }

    @GetMapping("admin/sinhvien")
    public String showSinhVien(@RequestParam(value = "keyword", required = false) String keyword, Model model) {

        List<SinhVien> listSinhVien = this.sinhVienService.fetchSinhVien(keyword);

        model.addAttribute("listSinhVien", listSinhVien);
        model.addAttribute("keyword", keyword);

        long total = this.sinhVienService.countTotalSinhVien();
        model.addAttribute("tongSinhVien", total);
        long totaldanghoc = this.sinhVienService.countSinhvienByTrangthai("Đang học");
        long totalbaoluu = this.sinhVienService.countSinhvienByTrangthai("Bảo lưu");
        long totalnghihoc = this.sinhVienService.countSinhvienByTrangthai("Nghỉ học");

        model.addAttribute("sinhvienđanghoc", totaldanghoc);
        model.addAttribute("sinhvienbaoluu", totalbaoluu);
        model.addAttribute("sinhviennghihoc", totalnghihoc);

        return "admin/SinhVien";
    }

    @GetMapping("admin/sinhvien/create")
    public String getcreatesinhvien(Model model) {
        model.addAttribute("sinhvien", new SinhVien());
        List<LopHanhChinh> listLop = this.lopHanhChinhService.findlophochanhchinh();
        model.addAttribute("listLop", listLop);
        return ("layout/sinhvien/createSV");

    }

    @PostMapping("/admin/sinhvien/create")
    public String postcreatesinhvien(
            @Valid @ModelAttribute("sinhvien") SinhVien createsinhvien,
            BindingResult bindingResult,
            @RequestParam("lopHanhChinh") int lopId,
            Model model) {

        // 1. Kiểm tra trùng mã sinh viên
        if (this.sinhVienService.checkMaSVExists(createsinhvien.getMaSV())) {

            bindingResult.rejectValue("maSV", "error.sinhvien", "Mã sinh viên này đã tồn tại trên hệ thống!");
        }

        // 2. Nếu có bất kỳ lỗi nào (lỗi trùng hoặc lỗi Validation khác)
        if (bindingResult.hasErrors()) {
            model.addAttribute("listLop", lopHanhChinhService.findlophochanhchinh());
            return "layout/sinhvien/createSV";
        }

        // 3. Xử lý lưu bình thường
        LopHanhChinh lop = this.lopHanhChinhService.findById(lopId);
        createsinhvien.setLopHanhChinh(lop);
        this.sinhVienService.createStudent(createsinhvien);

        return "redirect:/admin/sinhvien";

    }

    @GetMapping("/admin/sinhvien/delete/{id}")
    public String xoaSinhVien(@PathVariable("id") int id) {
        this.sinhVienService.deletesinhvienById(id);
        return "redirect:/admin/sinhvien";
    }

    @GetMapping("/admin/sinhvien/update/{id}")
    public String getupdatesinhvien(Model model, @PathVariable("id") int id) {
        SinhVien updatesinhvien = this.sinhVienService.findSinhVienById(id);
        model.addAttribute("sinhvien", updatesinhvien);
        model.addAttribute("listLop", lopHanhChinhService.findlophochanhchinh());

        // SỬA DÒNG NÀY THÀNH TÊN MỚI
        return "layout/sinhvien/update";
    }

    @PostMapping("/admin/sinhvien/update")
    public String postUpdatesinhvien(
            @Valid @ModelAttribute("sinhvien") SinhVien updatesinhvien,
            BindingResult bindingResult,
            @RequestParam("lopHanhChinh") int lopId, // Lấy ID lớp từ dropdown
            Model model) {

        if (bindingResult.hasErrors()) {
            // Nếu có lỗi, phải nạp lại danh sách lớp cho giao diện
            model.addAttribute("listLop", lopHanhChinhService.findlophochanhchinh());
            return "/layout/sinhvien/update"; // Quay lại trang sửa thay vì redirect
        }

        // Gán lớp hành chính trước khi update
        LopHanhChinh lop = this.lopHanhChinhService.findById(lopId);
        updatesinhvien.setLopHanhChinh(lop);

        // Gọi hàm update đã tối ưu ở bước trước
        this.sinhVienService.updateSinhVien(updatesinhvien);

        return "redirect:/admin/sinhvien";
    }

    @GetMapping("/admin/nhanvien")
    public String showgiangvien(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Nhanvien> NhanVien = this.nhanvienService.fetcNhanvien(keyword);

        model.addAttribute("keyword", keyword);

        model.addAttribute("NhanVien", NhanVien);

        Long totaldangday = this.nhanvienService.countNhanVienByTrangThai("Đang giảng dạy");
        model.addAttribute("totaldangday", totaldangday);

        long totalcongtac = this.nhanvienService.countNhanVienByTrangThai("Đang công tác");
        model.addAttribute("totalcongtac", totalcongtac);

        long totalnghihuu = this.nhanvienService.countNhanVienByTrangThai("Nghỉ hưu");
        long totalthoiviec = this.nhanvienService.countNhanVienByTrangThai("Thôi việc");
        long totalnghihuu_thoiviec = totalnghihuu + totalthoiviec;

        model.addAttribute("totalnghihuu_thoiviec", totalnghihuu_thoiviec);
        long sumnhanvien = this.nhanvienService.countNhanvien();
        model.addAttribute("sumnhanvien", sumnhanvien);
        return ("admin/NhanVien");
    }

    @GetMapping("/admin/nhanvien/create")
    public String getcreatenhanvien(Model model) {
        model.addAttribute("nhanvien", new Nhanvien());
        List<Khoa> listkhoa = this.khoaService.countkhoa();
        model.addAttribute("listkhoa", listkhoa);
        return ("layout/nhanvien/create");

    }

    @PostMapping("/admin/nhanvien/create")
    public String postcreatenhanvien(@Valid @ModelAttribute("nhanvien") Nhanvien createnhanvien, Model model,
            BindingResult bindingResult, @RequestParam("khoa") int khoaId) {
        if (this.nhanvienService.checkMaNVExists(createnhanvien.getMaNV())) {

            bindingResult.rejectValue("maNV", "error.sinhvien", "Mã nhân viên này đã tồn tại trên hệ thống!");
        }
        if (this.nhanvienService.checkEmailExists(createnhanvien.getEmail())) {

            bindingResult.rejectValue("email", "error.sinhvien", "Email nhân viên này đã tồn tại trên hệ thống!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("listkhoa", khoaService.countkhoa());
            return "layout/nhanvien/create";

        }
        Khoa khoa = this.khoaService.findById(khoaId);
        createnhanvien.setKhoa(khoa);

        this.nhanvienService.createnhanvien(createnhanvien);
        return ("redirect:/admin/nhanvien");
    }

    @GetMapping("/admin/nhanvien/update/{id}")
    public String getupdatenhanvien(Model model, @PathVariable("id") int id) {
        Nhanvien upNhanvien = this.nhanvienService.findNhanViennById(id);
        model.addAttribute("nhanvien", upNhanvien);
        List<Khoa> listkhoa = this.khoaService.countkhoa();
        model.addAttribute("listkhoa", listkhoa);
        return ("layout/nhanvien/update");
    }

    @PostMapping("/admin/nhanvien/update")
    public String postUpdateNhanvien(
            @Valid @ModelAttribute("nhanvien") Nhanvien updateNhanvien,
            BindingResult bindingResult,
            @RequestParam("khoaid") int khoaId,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("listkhoa", this.khoaService.countkhoa());
            return "layout/nhanvien/update";
        }

        Khoa khoa = this.khoaService.findById(khoaId);
        updateNhanvien.setKhoa(khoa);

        this.nhanvienService.updatenhanvien(updateNhanvien);

        return "redirect:/admin/nhanvien";
    }

    @GetMapping("/admin/nhanvien/delete/{id}")
    public String xoanhanvien(@PathVariable("id") int id) {
        this.nhanvienService.deletenhanvienById(id);
        return "redirect:/admin/nhanvien";
    }

    // KHOA

    @GetMapping("/admin/khoa")
    public String danhSachKhoa(Model model,
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<Khoa> listKhoa = this.khoaService.searchKhoa(keyword);

        model.addAttribute("keyword", keyword);

        Map<String, Long> mapSoSinhVien = new HashMap<>();
        Map<String, Long> mapSoLop = new HashMap<>();

        for (Khoa k : listKhoa) {
            mapSoSinhVien.put(String.valueOf(k.getId()), this.sinhVienService.countSinhVienByKhoa(k.getId()));
            mapSoLop.put(String.valueOf(k.getId()), this.lopHanhChinhService.countLopByKhoa(k.getId()));
        }

        model.addAttribute("listkhoa", listKhoa);
        model.addAttribute("mapSoSinhVien", mapSoSinhVien);
        model.addAttribute("mapSoLop", mapSoLop);

        long tongkhoa = this.khoaService.counttotalkhoa();
        model.addAttribute("tongkhoa", tongkhoa);

        long tonglop = this.lopHanhChinhService.counttotallop();
        model.addAttribute("tonglop", tonglop);

        long total = this.sinhVienService.countTotalSinhVien();
        model.addAttribute("tongsinhvien", total);

        return "admin/Khoa";
    }

    @GetMapping("/admin/khoa/create")
    public String getcreatekhoa(Model model) {
        model.addAttribute("khoa", new Khoa());
        model.addAttribute("danhSachNhanVien", nhanvienService.fetcNhanvien(null));

        return ("layout/khoa/create");

    }

    @PostMapping("/admin/khoa/create")
    public String postcreatenhanvien(@Valid @ModelAttribute("khoa") Khoa createkhoa, Model model,
            BindingResult bindingResult) {
        if (this.khoaService.checkMaKhoaExists(createkhoa.getMaKhoa())) {

            bindingResult.rejectValue("maKhoa", "error.khoa", "Mã khoa này đã tồn tại trên hệ thống!");
        }

        if (bindingResult.hasErrors()) {

            return "/layout/khoa/create";

        }

        this.khoaService.createkhoa(createkhoa);
        return ("redirect:/admin/khoa");
    }

    @GetMapping("/admin/khoa/update/{id}")
    public String getupdatekhoa(Model model, @PathVariable("id") int id) {
        Khoa updatekhoa = this.khoaService.findKhoaById(id);
        model.addAttribute("khoa", updatekhoa);
        model.addAttribute("danhSachNhanVien", nhanvienService.fetcNhanvien(null));

        return "layout/khoa/update";
    }

    @PostMapping("/admin/khoa/update")
    public String postUpdatekhoa(
            @Valid @ModelAttribute("khoa") Khoa updatekKhoa,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "/layout/sinhvien/update";
        }

        this.khoaService.updatekhoa(updatekKhoa);
        return "redirect:/admin/khoa";
    }

    @GetMapping("/admin/khoa/delete/{id}")
    public String xoakhoa(@PathVariable("id") int id) {
        this.khoaService.deletekhoaById(id);
        return "redirect:/admin/khoa";
    }

}
