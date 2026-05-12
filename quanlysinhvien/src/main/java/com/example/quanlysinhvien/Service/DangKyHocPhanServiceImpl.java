package com.example.quanlysinhvien.Service;

import com.example.quanlysinhvien.Model.KetQuaHocTap;
import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.repository.KetQuaHocTapRepository;
import com.example.quanlysinhvien.repository.LopHocPhanRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DangKyHocPhanServiceImpl implements DangKyHocPhanService {

    private final LopHocPhanRepository lopHocPhanRepository;
    private final KetQuaHocTapRepository ketQuaHocTapRepository;
    private final SinhVienIFService sinhVienService;

    public DangKyHocPhanServiceImpl(
            LopHocPhanRepository lopHocPhanRepository,
            KetQuaHocTapRepository ketQuaHocTapRepository,
            SinhVienIFService sinhVienService) {
        this.lopHocPhanRepository = lopHocPhanRepository;
        this.ketQuaHocTapRepository = ketQuaHocTapRepository;
        this.sinhVienService = sinhVienService;
    }

    @Override
    public List<LopHocPhan> getDanhSachLop(Integer hocKy, String namHoc) {
        // ✅ Dùng query fetch JOIN để load đầy đủ hocPhan, giangVien, danhSachKetQua
        List<LopHocPhan> list = lopHocPhanRepository
                .findByHocKyAndNamHocWithDetails(hocKy, namHoc);

        // ✅ Đảm bảo danhSachKetQua không null
        for (LopHocPhan lop : list) {
            if (lop.getDanhSachKetQua() == null) {
                lop.setDanhSachKetQua(new java.util.ArrayList<>());
            }
        }
        return list;
    }

    @Override
    public Set<Integer> getIdLopDaDangKy(Integer hocKy, String namHoc) {
        SinhVien sv = sinhVienService.getCurrentSinhVien();
        List<KetQuaHocTap> daDangKy = ketQuaHocTapRepository
                .findBySinhVien_IdAndLopHocPhan_HocKyAndLopHocPhan_NamHoc(
                        sv.getId(), hocKy, namHoc);

        Set<Integer> idSet = new HashSet<>();
        for (KetQuaHocTap kq : daDangKy) {
            idSet.add(kq.getLopHocPhan().getId());
        }
        return idSet;
    }

    @Override
    public int getSiSoHienTai(int lopId) {
        return lopHocPhanRepository.demSoSinhVienDangKy(lopId);
    }

    @Override
    @Transactional
    public void dangKy(int lopHocPhanId) {
        SinhVien sv = sinhVienService.getCurrentSinhVien();

        LopHocPhan lop = lopHocPhanRepository.findById(lopHocPhanId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học phần!"));

        // Kiểm tra đã đăng ký chưa
        Optional<KetQuaHocTap> daDangKy = ketQuaHocTapRepository
                .findBySinhVien_IdAndLopHocPhan_Id(sv.getId(), lopHocPhanId);
        if (daDangKy.isPresent()) {
            throw new RuntimeException("Bạn đã đăng ký lớp này rồi!");
        }

        // Kiểm tra còn chỗ không
        int siSoHienTai = lopHocPhanRepository.demSoSinhVienDangKy(lopHocPhanId);
        if (lop.getSiSoToiDa() != null && siSoHienTai >= lop.getSiSoToiDa()) {
            throw new RuntimeException("Lớp học phần đã đầy, không thể đăng ký!");
        }

        KetQuaHocTap ketQua = new KetQuaHocTap();
        ketQua.setSinhVien(sv);
        ketQua.setLopHocPhan(lop);
        ketQua.setDiemChuyenCan(null);
        ketQua.setDiemGiuaKy(null);
        ketQua.setDiemCuoiKy(null);
        ketQua.setDiemTongKet(null);
        ketQua.setDiemChu(null);
        ketQua.setTrangThaiQuaMon(null);

        ketQuaHocTapRepository.save(ketQua);
    }

    @Override
    @Transactional
    public void huyDangKy(int lopHocPhanId) {
        SinhVien sv = sinhVienService.getCurrentSinhVien();

        KetQuaHocTap ketQua = ketQuaHocTapRepository
                .findBySinhVien_IdAndLopHocPhan_Id(sv.getId(), lopHocPhanId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa đăng ký lớp này!"));

        if (ketQua.getDiemTongKet() != null) {
            throw new RuntimeException("Không thể hủy lớp đã có điểm!");
        }

        ketQuaHocTapRepository.delete(ketQua);
    }

    @Override
    public int getTongTinChi(Integer hocKy, String namHoc) {
        SinhVien sv = sinhVienService.getCurrentSinhVien();
        List<KetQuaHocTap> daDangKy = ketQuaHocTapRepository
                .findBySinhVien_IdAndLopHocPhan_HocKyAndLopHocPhan_NamHoc(
                        sv.getId(), hocKy, namHoc);

        int tongTinChi = 0;
        for (KetQuaHocTap kq : daDangKy) {
            if (kq.getLopHocPhan() != null
                    && kq.getLopHocPhan().getHocPhan() != null
                    && kq.getLopHocPhan().getHocPhan().getSoTinChi() != null) {
                tongTinChi += kq.getLopHocPhan().getHocPhan().getSoTinChi();
            }
        }
        return tongTinChi;
    }
}