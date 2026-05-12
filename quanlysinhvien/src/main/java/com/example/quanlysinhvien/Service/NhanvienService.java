package com.example.quanlysinhvien.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.Model.UpdateProfileDTO;
import com.example.quanlysinhvien.repository.NhanvienRepository;

import jakarta.transaction.Transactional;

@Service
public class NhanvienService {
    // private final NhanvienRepository nhanvienRepository;

    // public NhanvienService(NhanvienRepository nhanvienRepository) {
    // this.nhanvienRepository = nhanvienRepository;
    // }

    // public List<Nhanvien> fetchNhanvien() {
    // List<Nhanvien> nhanvienList = this.nhanvienRepository.findAll();
    // return nhanvienList;
    // }

    // @Transactional
    // public void creatNhanvien(Nhanvien nhanvien) {
    // this.nhanvienRepository.save(nhanvien);
    // }

    // public Nhanvien findNhanvienByID(int id) {
    // Optional<Nhanvien> updatenhanvien = this.nhanvienRepository.findById(id);
    // return updatenhanvien.get();
    // }

    // @Transactional
    // public void updateNhanvien(Nhanvien inputNhanvien) {
    // Nhanvien currentNhanvienInDB =
    // this.nhanvienRepository.findById(inputNhanvien.getId()).orElse(null);

    // if (currentNhanvienInDB != null) {
    // currentNhanvienInDB.setMaNV(inputNhanvien.getMaNV());
    // currentNhanvienInDB.setTenNV(inputNhanvien.getTenNV());
    // currentNhanvienInDB.setNgaySinh(inputNhanvien.getNgaySinh());
    // currentNhanvienInDB.setGioiTinh(inputNhanvien.getGioiTinh());
    // currentNhanvienInDB.setSdt(inputNhanvien.getSdt());
    // currentNhanvienInDB.setEmail(inputNhanvien.getEmail());
    // currentNhanvienInDB.setDiaChi(inputNhanvien.getDiaChi());
    // currentNhanvienInDB.setHocVi(inputNhanvien.getHocVi());
    // currentNhanvienInDB.setChucVu(inputNhanvien.getChucVu());
    // currentNhanvienInDB.setTrangThai(inputNhanvien.getTrangThai());

    // if (inputNhanvien.getPassword() != null &&
    // !inputNhanvien.getPassword().isEmpty()) {
    // currentNhanvienInDB.setPassword(inputNhanvien.getPassword());
    // }

    // this.nhanvienRepository.save(currentNhanvienInDB);
    // }
    // }

    // public void deleteById(int id) {
    // this.nhanvienRepository.deleteById(id);
    // }

    // public long countAll() {
    // return nhanvienRepository.count();
    // }

    // // public long countActive() {
    // // return nhanvienRepository.countByTrangThai("Đang công tác");
    // // }

    // // public long countRetired() {
    // // return nhanvienRepository.countByTrangThai("Nghỉ hưu");
    // // }

    // public long countHighDegree() {
    // List<String> expertDegrees = Arrays.asList("Tiến sĩ", "Phó Giáo sư", "Giáo
    // sư");
    // return nhanvienRepository.countByHocViIn(expertDegrees);
    // }

    // public List<Nhanvien> findNhanVienByname(String tenNV) {

    // return this.nhanvienRepository.findByTenNVContainingIgnoreCase(tenNV);
    // }
    private final NhanvienRepository nhanvienRepository;
    private final PasswordEncoder passwordEncoder;

    public NhanvienService(NhanvienRepository nhanvienRepository, PasswordEncoder passwordEncoder) {
        this.nhanvienRepository = nhanvienRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Nhanvien> fetcNhanvien(String keyword) {

        if (keyword != null && !keyword.trim().isEmpty()) {

            return this.nhanvienRepository
                    .searchNhanVien(keyword.trim());
        }

        return this.nhanvienRepository.findAll();
    }

    public Long countNhanVienByTrangThai(String trangThai) {
        return this.nhanvienRepository.countByTrangThai(trangThai);
    }

    public long countNhanvien() {
        return this.nhanvienRepository.count();

    }

    public void createnhanvien(Nhanvien nhanvien) {
        nhanvien.setPassword(passwordEncoder.encode(nhanvien.getPassword()));
        this.nhanvienRepository.save(nhanvien);
    }

    public boolean checkMaNVExists(String maNV) {
        return this.nhanvienRepository.existsByMaNV(maNV);
    }

    public boolean checkEmailExists(String email) {
        return this.nhanvienRepository.existsByEmail(email);
    }

    public void updatenhanvien(Nhanvien inputnhanvien) {
        // 1. Tìm nhân viên hiện tại trong DB bằng Optional cho đúng chuẩn
        Optional<Nhanvien> nvOptional = this.nhanvienRepository.findById(inputnhanvien.getId());

        if (nvOptional.isPresent()) {
            Nhanvien currentNV = nvOptional.get();

            // 2. Cập nhật các thông tin (Thường không cập nhật maNV vì nó là duy nhất và cố
            // định)
            currentNV.setMaNV(inputnhanvien.getMaNV());

            // Bổ sung các thông tin còn lại
            currentNV.setTenNV(inputnhanvien.getTenNV());
            currentNV.setNgaySinh(inputnhanvien.getNgaySinh());
            currentNV.setGioiTinh(inputnhanvien.getGioiTinh());
            currentNV.setDiaChi(inputnhanvien.getDiaChi());
            currentNV.setSdt(inputnhanvien.getSdt());
            currentNV.setEmail(inputnhanvien.getEmail());
            currentNV.setHocVi(inputnhanvien.getHocVi());
            currentNV.setChucVu(inputnhanvien.getChucVu());
            currentNV.setTrangThai(inputnhanvien.getTrangThai());

            // Xử lý riêng mật khẩu: Chỉ cập nhật nếu người dùng có nhập mật khẩu mới ở form
            // Update
            if (inputnhanvien.getPassword() != null && !inputnhanvien.getPassword().isEmpty()) {
                currentNV.setPassword(passwordEncoder.encode(inputnhanvien.getPassword()));
            }

            // 3. Đừng quên cập nhật cả mối quan hệ Khoa
            currentNV.setKhoa(inputnhanvien.getKhoa());

            // 4. Lưu lại (chỉ dùng 1 lần nhanvienRepository thôi nhé)
            this.nhanvienRepository.save(currentNV);
        }
    }

    public Nhanvien findNhanViennById(int id) {
        Optional<Nhanvien> updatenhanvien = this.nhanvienRepository.findById(id);
        return updatenhanvien.get();
    }

    public void deletenhanvienById(int id) {
        this.nhanvienRepository.deleteById(id);
    }

    // dành cho nhân viên cập nhập hô sơ

    public Nhanvien getNhanvienByEmail(String email) {
        return nhanvienRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên: " + email));
    }

    public void capNhatThongTin(String email, UpdateProfileDTO dto) {
        Nhanvien nv = getNhanvienByEmail(email);
        nv.setSdt(dto.getSdt());
        nv.setDiaChi(dto.getDiaChi());
        nhanvienRepository.save(nv);
    }
}
