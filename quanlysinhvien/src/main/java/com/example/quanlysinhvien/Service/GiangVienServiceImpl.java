package com.example.quanlysinhvien.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quanlysinhvien.Model.KetQuaHocTap;
import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.repository.KetQuaHocTapRepository;
import com.example.quanlysinhvien.repository.LopHocPhanRepository;
import com.example.quanlysinhvien.repository.NhanvienRepository;
import com.example.quanlysinhvien.repository.SinhVienRepository;

import java.util.List;

@Service
public class GiangVienServiceImpl implements GiangVienService {

    private final LopHocPhanRepository lopHocPhanRepo;
    private final KetQuaHocTapRepository ketQuaRepo;
    private final NhanvienRepository nhanvienRepository;

    public GiangVienServiceImpl(LopHocPhanRepository lopHocPhanRepo, NhanvienRepository nhanvienRepository,
            KetQuaHocTapRepository ketQuaRepo) {
        this.lopHocPhanRepo = lopHocPhanRepo;
        this.ketQuaRepo = ketQuaRepo;
        this.nhanvienRepository = nhanvienRepository;
    }

    // ── Danh sách lớp phụ trách ───────────────────────────────────────
    @Override
    public List<LopHocPhan> getDanhSachLopPhuTrach(int idGV) {
        return lopHocPhanRepo.findByGiangVienId(idGV);
    }

    // ── Lấy 1 lớp theo id ────────────────────────────────────────────
    @Override
    public LopHocPhan getLopById(int idLop) {
        return lopHocPhanRepo.findById(idLop)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp: " + idLop));
    }

    // ── Danh sách kết quả học tập của lớp ────────────────────────────
    // Duyệt qua danhSachKetQua có sẵn trong LopHocPhan
    // Mỗi KetQuaHocTap đã có sinhVien → không cần query thêm SinhVien
    @Override
    @Transactional
    public List<KetQuaHocTap> getDanhSachKetQua(int idLop) {
        LopHocPhan lop = getLopById(idLop);

        // Duyệt qua danhSachKetQua (đã có sẵn trong entity)
        for (KetQuaHocTap kq : lop.getDanhSachKetQua()) {
            // Nếu chưa có row trong DB thì tạo mới (trường hợp SV mới đăng ký)
            ketQuaRepo
                    .findByLopHocPhanIdAndSinhVienId(idLop, kq.getSinhVien().getId())
                    .orElseGet(() -> {
                        KetQuaHocTap kqMoi = new KetQuaHocTap();
                        kqMoi.setLopHocPhan(lop);
                        kqMoi.setSinhVien(kq.getSinhVien());
                        return ketQuaRepo.save(kqMoi);
                    });
        }

        // Trả về danh sách đã sắp xếp theo tên SV
        return ketQuaRepo.findByLopHocPhanId(idLop);
    }

    // ── Lưu / cập nhật điểm ──────────────────────────────────────────
    @Override
    @Transactional
    public void luuBangDiem(int idLopHP,
            List<Integer> danhSachIdSV,
            List<Float> danhSachCC,
            List<Float> danhSachGK,
            List<Float> danhSachCK) {

        LopHocPhan lop = getLopById(idLopHP);

        for (int i = 0; i < danhSachIdSV.size(); i++) {
            int idSV = danhSachIdSV.get(i);
            Float cc = danhSachCC.get(i);
            Float gk = danhSachGK.get(i);
            Float ck = danhSachCK.get(i);

            // Lấy row có sẵn, không tìm thấy thì báo lỗi
            KetQuaHocTap kq = ketQuaRepo
                    .findByLopHocPhanIdAndSinhVienId(idLopHP, idSV)
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy kết quả học tập của SV: " + idSV));

            // Chỉ ghi đè khi form gửi lên giá trị mới (không xóa điểm đã chốt)
            if (cc != null)
                kq.setDiemChuyenCan(cc);
            if (gk != null)
                kq.setDiemGiuaKy(gk);
            if (ck != null)
                kq.setDiemCuoiKy(ck);

            // Tự tính tổng kết + xếp loại
            tinhDiemTongKet(kq);
            ketQuaRepo.save(kq);
        }
    }

    // ── Helper: tính tổng kết + xếp loại chữ ─────────────────────────
    private void tinhDiemTongKet(KetQuaHocTap kq) {
        if (kq.getDiemChuyenCan() == null
                || kq.getDiemGiuaKy() == null
                || kq.getDiemCuoiKy() == null)
            return; // chưa đủ 3 cột, bỏ qua

        float tong = kq.getDiemChuyenCan() * 0.1f
                + kq.getDiemGiuaKy() * 0.3f
                + kq.getDiemCuoiKy() * 0.6f;

        tong = Math.round(tong * 10) / 10f; // làm tròn 1 chữ số thập phân
        kq.setDiemTongKet(tong);

        if (tong >= 9.0f) {
            kq.setDiemChu("A+");
            kq.setTrangThaiQuaMon(true);
        } else if (tong >= 8.5f) {
            kq.setDiemChu("A");
            kq.setTrangThaiQuaMon(true);
        } else if (tong >= 8.0f) {
            kq.setDiemChu("B+");
            kq.setTrangThaiQuaMon(true);
        } else if (tong >= 7.0f) {
            kq.setDiemChu("B");
            kq.setTrangThaiQuaMon(true);
        } else if (tong >= 6.5f) {
            kq.setDiemChu("C+");
            kq.setTrangThaiQuaMon(true);
        } else if (tong >= 5.5f) {
            kq.setDiemChu("C");
            kq.setTrangThaiQuaMon(true);
        } else if (tong >= 5.0f) {
            kq.setDiemChu("D+");
            kq.setTrangThaiQuaMon(true);
        } else if (tong >= 4.0f) {
            kq.setDiemChu("D");
            kq.setTrangThaiQuaMon(true);
        } else {
            kq.setDiemChu("F");
            kq.setTrangThaiQuaMon(false);
        }
    }

    @Override
    public Nhanvien findByEmail(String email) {
        return nhanvienRepository.findByEmail(email).orElse(null);
    }
}