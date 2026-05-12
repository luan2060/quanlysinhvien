package com.example.quanlysinhvien.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlysinhvien.Model.KetQuaHocTap;
import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.Model.UpdateProfileDTO;
import com.example.quanlysinhvien.Service.GiangVienService;
import com.example.quanlysinhvien.Service.LichGiangDayService;
import com.example.quanlysinhvien.Service.NhanvienService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

// @Controller
// public class NhanvienController {

//     @GetMapping("nhanvien/loppt")
//     public String showlopphutrach() {
//         return ("Nhanvien_GD/lopPT");
//     }

//     @GetMapping("nhanvien/lichtrinh")
//     public String showlichtrinh() {
//         return ("Nhanvien_GD/lichtrinh");
//     }

//     @GetMapping("nhanvien/trangchu")
//     public String showtrangchu() {
//         return ("Nhanvien_GD/trangchu");
//     }

// }

@Controller
@RequestMapping("/nhanvien")
public class NhanvienController {
    private final NhanvienService nhanvienService;
    private final LichGiangDayService lichGiangDayService;
    private final GiangVienService giangVienService;

    public NhanvienController(NhanvienService nhanvienService, GiangVienService giangVienService,
            LichGiangDayService lichGiangDayService) {
        this.nhanvienService = nhanvienService;
        this.lichGiangDayService = lichGiangDayService;
        this.giangVienService = giangVienService;
    }

    @GetMapping("/trangchu")
    public String showTCN() {
        return ("Nhanvien_GD/trangchu");
    }

    @GetMapping("/trangcanhan")
    public String trangCanNhan(Model model, Principal principal) {
        String email = principal.getName();
        Nhanvien nv = nhanvienService.getNhanvienByEmail(email);

        UpdateProfileDTO dto = new UpdateProfileDTO();
        dto.setSdt(nv.getSdt());
        dto.setDiaChi(nv.getDiaChi());

        model.addAttribute("nhanvien", nv);
        model.addAttribute("updateProfileDTO", dto);

        return "Nhanvien_GD/TCN";
    }

    @PostMapping("/trangcanhan/capnhat")
    public String capNhat(
            @Valid @ModelAttribute("updateProfileDTO") UpdateProfileDTO dto,
            BindingResult result,
            Model model,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        Nhanvien nv = nhanvienService.getNhanvienByEmail(email);

        // Nếu có lỗi validation → mở lại modal
        if (result.hasErrors()) {
            model.addAttribute("nhanvien", nv);
            model.addAttribute("updateProfileDTO", dto);
            model.addAttribute("showModal", true);
            return "Nhanvien_GD/TCN";
        }

        nhanvienService.capNhatThongTin(email, dto);
        redirectAttributes.addFlashAttribute("successMsg", "Cập nhật thông tin thành công!");
        return "redirect:/nhanvien/trangcanhan";
    }

    @GetMapping("/lichtrinh")
    public String lichGiangDay(
            @RequestParam(required = false, defaultValue = "1") Integer hocKy,
            @RequestParam(required = false, defaultValue = "2025-2026") String namHoc,
            Model model,
            Principal principal) {
        String email = principal.getName();

        Map<Integer, List<LopHocPhan>> lichTheoThu = lichGiangDayService.getLichTheoThu(email, hocKy, namHoc);

        model.addAttribute("lichTheoThu", lichTheoThu);
        model.addAttribute("hocKyChon", hocKy);
        model.addAttribute("namHocChon", namHoc);

        return "Nhanvien_GD/lichtrinh";
    }

    @GetMapping("/loppt")
    public String trangLopPhuTrach(
            @RequestParam(required = false) Integer idLop,
            Principal principal,
            Model model) {

        String email = principal.getName();
        Nhanvien gv = giangVienService.findByEmail(email);
        if (gv == null)
            return "redirect:/login";

        List<LopHocPhan> danhSachLop = giangVienService.getDanhSachLopPhuTrach(gv.getId());
        model.addAttribute("danhSachLop", danhSachLop);

        if (idLop != null) {
            LopHocPhan lopDaChon = giangVienService.getLopById(idLop);
            if (lopDaChon == null || lopDaChon.getGiangVien().getId() != gv.getId()) {
                return "redirect:/nhanvien/loppt";
            }
            List<KetQuaHocTap> danhSachKetQua = giangVienService.getDanhSachKetQua(idLop);
            model.addAttribute("lopDaChon", lopDaChon);
            model.addAttribute("danhSachKetQua", danhSachKetQua);
        }

        return "Nhanvien_GD/lopPT";
    }

    @PostMapping("/luudiem")
    public String luuDiem(
            @RequestParam("idLopHP") int idLopHP,
            @RequestParam(value = "idSV", required = false) List<Integer> danhSachIdSV,
            @RequestParam(value = "diemChuyenCan", required = false) List<Float> danhSachCC,
            @RequestParam(value = "diemGiuaKy", required = false) List<Float> danhSachGK,
            @RequestParam(value = "diemCuoiKy", required = false) List<Float> danhSachCK,
            Principal principal,
            RedirectAttributes ra) {

        String email = principal.getName();
        Nhanvien gv = giangVienService.findByEmail(email);
        if (gv == null)
            return "redirect:/login";

        // Không có SV nào được nhập điểm
        if (danhSachIdSV == null || danhSachIdSV.isEmpty()) {
            ra.addFlashAttribute("error", "⚠️ Chưa có sinh viên nào được nhập điểm.");
            return "redirect:/nhanvien/loppt?idLop=" + idLopHP;
        }

        try {
            LopHocPhan lop = giangVienService.getLopById(idLopHP);
            if (lop.getGiangVien().getId() != gv.getId()) {
                ra.addFlashAttribute("error", "Bạn không có quyền chỉnh sửa lớp này.");
                return "redirect:/nhanvien/loppt";
            }
            giangVienService.luuBangDiem(idLopHP, danhSachIdSV, danhSachCC, danhSachGK, danhSachCK);
            ra.addFlashAttribute("success", "✅ Lưu bảng điểm thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Lỗi: " + e.getMessage());
        }

        return "redirect:/nhanvien/loppt?idLop=" + idLopHP;
    }
}
