package com.example.quanlysinhvien.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quanlysinhvien.Model.KetQuaHocTap;
import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.repository.KetQuaRepository;
import com.example.quanlysinhvien.repository.LopHocPhanRepository;

import jakarta.transaction.Transactional;

@Service
public class DiemService {

    @Autowired
    private KetQuaRepository ketQuaRepository;

    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;

    public List<LopHocPhan> getAllLopHocPhan() {
        return lopHocPhanRepository.findAll();
    }

    public Optional<LopHocPhan> getLopHocPhanById(Integer id) {
        return lopHocPhanRepository.findById(id);
    }

    public List<KetQuaHocTap> getDiemByLopHocPhan(Integer lopHocPhanId) {
        return ketQuaRepository.findByLopHocPhanId(lopHocPhanId);
    }

    public Optional<KetQuaHocTap> getKetQuaById(Integer id) {
        return ketQuaRepository.findById(id);
    }

    @Transactional
    public KetQuaHocTap capNhatDiem(Integer id, Float cc, Float gk, Float ck) {
        KetQuaHocTap kq = ketQuaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi điểm ID: " + id));

        kq.setDiemChuyenCan(cc);
        kq.setDiemGiuaKy(gk);
        kq.setDiemCuoiKy(ck);

        // Chỉ tính tổng kết khi đủ cả 3 thành phần điểm
        if (cc != null && gk != null && ck != null) {
            float tongKet = cc * 0.1f + gk * 0.3f + ck * 0.6f;
            tongKet = Math.round(tongKet * 10.0f) / 10.0f; // Làm tròn 1 chữ số thập phân
            kq.setDiemTongKet(tongKet);
            kq.setDiemChu(tinhDiemChu(tongKet));
            kq.setTrangThaiQuaMon(tongKet >= 5.0f);
        } else {
            // Chưa đủ điểm → xóa kết quả tổng
            kq.setDiemTongKet(null);
            kq.setDiemChu(null);
            kq.setTrangThaiQuaMon(null);
        }

        return ketQuaRepository.save(kq);
    }

    // ---------------------------------------------------------------
    // XÓA bản ghi điểm
    // ---------------------------------------------------------------
    @Transactional
    public void xoaKetQua(Integer id) {
        if (!ketQuaRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy bản ghi điểm ID: " + id);
        }
        ketQuaRepository.deleteById(id);
    }

    // ---------------------------------------------------------------
    // TÍNH ĐIỂM CHỮ theo thang 10
    // ---------------------------------------------------------------
    private String tinhDiemChu(float diem) {
        if (diem >= 9.0f)
            return "A+";
        if (diem >= 8.5f)
            return "A";
        if (diem >= 8.0f)
            return "B+";
        if (diem >= 7.0f)
            return "B";
        if (diem >= 6.5f)
            return "C+";
        if (diem >= 5.5f)
            return "C";
        if (diem >= 5.0f)
            return "D+";
        if (diem >= 4.0f)
            return "D";
        return "F";
    }
}

// ====
