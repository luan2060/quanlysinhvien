package com.example.quanlysinhvien.Service;

import com.example.quanlysinhvien.Model.HocPhan;
import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.repository.HocPhanRepository;
import com.example.quanlysinhvien.repository.LopHocPhanRepository;
import com.example.quanlysinhvien.repository.NganhRepository;
import com.example.quanlysinhvien.repository.NhanvienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HocPhanService {

    @Autowired
    private HocPhanRepository hocPhanRepository;

    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;

    @Autowired
    private NhanvienRepository nhanvienRepository;

    @Autowired
    private NganhRepository nganhRepository;

    // ===================== FIND ALL =====================
    public List<HocPhan> findAll() {
        return hocPhanRepository.findAllWithNganh();
    }

    // ===================== SEARCH =====================
    public List<HocPhan> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return hocPhanRepository.findAllWithNganh();
        }
        return hocPhanRepository.searchByKeyword(keyword.trim());
    }

    // ===================== FIND BY ID =====================
    public Optional<HocPhan> findById(Integer id) {
        return hocPhanRepository.findById(id);
    }

    // ===================== SAVE =====================
    @Transactional
    public void save(HocPhan hocPhan, List<LopHocPhan> listLopHP,
            List<Integer> giangVienIds, Integer nganhId) {

        // Gán ngành trực tiếp (Many-to-One)
        if (nganhId != null) {
            nganhRepository.findById(nganhId).ifPresent(hocPhan::setNganh);
        }

        HocPhan saved = hocPhanRepository.save(hocPhan);

        // Lưu từng lớp học phần
        if (listLopHP != null) {
            for (int i = 0; i < listLopHP.size(); i++) {
                LopHocPhan lop = listLopHP.get(i);

                // Bỏ qua lớp không có mã
                if (lop.getMaLopHP() == null || lop.getMaLopHP().trim().isEmpty())
                    continue;

                // ✅ Kiểm tra trùng mã với DB — ném lỗi thay vì bỏ qua
                if (lopHocPhanRepository.existsByMaLopHP(lop.getMaLopHP())) {
                    throw new RuntimeException(
                            "Mã lớp học phần '" + lop.getMaLopHP() + "' đã tồn tại trong hệ thống!");
                }

                lop.setHocPhan(saved);

                // Gán giảng viên
                if (giangVienIds != null && i < giangVienIds.size()
                        && giangVienIds.get(i) != null) {
                    nhanvienRepository.findById(giangVienIds.get(i))
                            .ifPresent(lop::setGiangVien);
                }

                lopHocPhanRepository.save(lop);
            }
        }
    }

    // ===================== UPDATE =====================
    // ===================== UPDATE =====================
    @Transactional
    public void update(HocPhan hocPhan, List<LopHocPhan> listLopHP,
            List<Integer> giangVienIds, Integer nganhId) {

        HocPhan existing = hocPhanRepository.findById(hocPhan.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học phần!"));

        // 1. Cập nhật thông tin cơ bản học phần
        existing.setMaHocPhan(hocPhan.getMaHocPhan());
        existing.setTenHocPhan(hocPhan.getTenHocPhan());
        existing.setSoTinChi(hocPhan.getSoTinChi());
        existing.setSoTietLyThuyet(hocPhan.getSoTietLyThuyet());
        existing.setSoTietThucHanh(hocPhan.getSoTietThucHanh());
        existing.setHocPhi(hocPhan.getHocPhi());

        if (nganhId != null) {
            nganhRepository.findById(nganhId).ifPresent(existing::setNganh);
        } else {
            existing.setNganh(null);
        }

        hocPhanRepository.save(existing);

        // 2. ✅ Lấy danh sách lớp cũ để xử lý xóa
        List<LopHocPhan> lopCuTrongDB = lopHocPhanRepository.findByHocPhanId(existing.getId());
        Set<Integer> idGiuLai = new HashSet<>();

        if (listLopHP != null) {
            int giangVienIndex = 0; // ✅ index riêng, không bị lệch khi skip

            for (LopHocPhan lop : listLopHP) {
                if (lop.getMaLopHP() == null || lop.getMaLopHP().trim().isEmpty())
                    continue;

                lop.setHocPhan(existing);

                // 3. Gán giảng viên
                if (giangVienIds != null && giangVienIndex < giangVienIds.size()
                        && giangVienIds.get(giangVienIndex) != null) {
                    nhanvienRepository.findById(giangVienIds.get(giangVienIndex))
                            .ifPresent(lop::setGiangVien);
                }
                giangVienIndex++;

                // 4. ✅ Nếu caHoc có giá trị mà gioHoc trống thì tự sinh
                if (lop.getCaHoc() != null &&
                        (lop.getGioHoc() == null || lop.getGioHoc().trim().isEmpty())) {
                    lop.setGioHoc(mapCaHocToGio(lop.getCaHoc()));
                }

                Integer lopId = lop.getId();

                if (lopId == null) {
                    // Lớp mới — kiểm tra trùng mã
                    if (lopHocPhanRepository.existsByMaLopHP(lop.getMaLopHP())) {
                        throw new RuntimeException(
                                "Mã lớp học phần '" + lop.getMaLopHP() + "' đã tồn tại!");
                    }
                } else {
                    // Lớp cũ — chỉ kiểm tra trùng nếu mã bị đổi
                    lopHocPhanRepository.findById(lopId).ifPresent(lopCu -> {
                        if (!lopCu.getMaLopHP().equals(lop.getMaLopHP())
                                && lopHocPhanRepository.existsByMaLopHP(lop.getMaLopHP())) {
                            throw new RuntimeException(
                                    "Mã lớp học phần '" + lop.getMaLopHP() + "' đã tồn tại!");
                        }
                    });
                    idGiuLai.add(lopId); // ✅ đánh dấu giữ lại
                }

                lopHocPhanRepository.save(lop);
            }
        }
    }

    // 5. ✅ Xóa các lớp cũ không còn trong form
    @Transactional
    public void deleteById(Integer id) {
        hocPhanRepository.deleteById(id);
    }

    // Helper tự sinh giờ học từ ca học
    private String mapCaHocToGio(Integer caHoc) {
        return switch (caHoc) {
            case 1 -> "07:00 - 09:25";
            case 2 -> "09:35 - 12:00";
            case 3 -> "13:00 - 15:25";
            case 4 -> "15:35 - 18:00";
            default -> "";
        };
    }
}