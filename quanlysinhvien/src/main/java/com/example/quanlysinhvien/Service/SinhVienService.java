package com.example.quanlysinhvien.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quanlysinhvien.Model.Khoa;
import com.example.quanlysinhvien.Model.KhoaPhanTramDTO;
import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.repository.NhanvienRepository;
import com.example.quanlysinhvien.repository.SinhVienRepository;

@Service
public class SinhVienService {

    private final SinhVienRepository sinhVienRepository;
    private final PasswordEncoder passwordEncoder;

    public SinhVienService(SinhVienRepository sinhVienRepository, PasswordEncoder passwordEncoder) {
        this.sinhVienRepository = sinhVienRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public long countTotalSinhVien() {
        return this.sinhVienRepository.count();
    }

    public Long countSinhvienByTrangthai(String trangThai) {
        return this.sinhVienRepository.countByTrangThai(trangThai);
    }

    public List<SinhVien> fetchSinhVien(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return this.sinhVienRepository.searchByNameOrId(keyword.trim());
        }
        return this.sinhVienRepository.findAll();
    }
    // private final StudentRepository studentRepository;
    // private final NhanvienRepository nhanvienRepository;

    // public StudentService(StudentRepository studentRepository, NhanvienRepository
    // nhanvienRepository) {
    // this.studentRepository = studentRepository;
    // this.nhanvienRepository = nhanvienRepository;

    // }

    public List<SinhVien> fetchStudents() {
        List<SinhVien> studentList = this.sinhVienRepository.findAll();

        return studentList;
    }

    // public long countStudents() {
    // return this.studentRepository.count();
    // }

    public void createStudent(SinhVien sinhvien) {
        sinhvien.setPassword(passwordEncoder.encode(sinhvien.getPassword()));
        this.sinhVienRepository.save(sinhvien);
    }

    public boolean checkMaSVExists(String maSV) {
        return this.sinhVienRepository.existsByMaSV(maSV);
    }

    public void deletesinhvienById(int id) {
        this.sinhVienRepository.deleteById(id);
    }

    public SinhVien findSinhVienById(int id) {
        Optional<SinhVien> updatesinhvien = this.sinhVienRepository.findById(id);
        return updatesinhvien.get();
    }

    public void updateSinhVien(SinhVien inputSinhVien) {
        // 1. Tìm sinh viên hiện tại trong DB bằng Optional cho đúng chuẩn
        Optional<SinhVien> svOptional = this.sinhVienRepository.findById(inputSinhVien.getId());

        if (svOptional.isPresent()) {
            SinhVien currentSV = svOptional.get();

            // 2. Cập nhật các thông tin (Thường không cập nhật maSV vì nó là duy nhất và cố
            // định)
            currentSV.setHoTen(inputSinhVien.getHoTen());
            currentSV.setNgaySinh(inputSinhVien.getNgaySinh());
            currentSV.setGioiTinh(inputSinhVien.getGioiTinh());
            currentSV.setSdt(inputSinhVien.getSdt());
            currentSV.setEmail(inputSinhVien.getEmail());
            currentSV.setDiaChi(inputSinhVien.getDiaChi());
            currentSV.setTrangThai(inputSinhVien.getTrangThai());
            if (inputSinhVien.getPassword() != null && !inputSinhVien.getPassword().isEmpty()) {
                currentSV.setPassword(passwordEncoder.encode(inputSinhVien.getPassword()));
            }

            currentSV.setLopHanhChinh(inputSinhVien.getLopHanhChinh());

            this.sinhVienRepository.save(currentSV);
        }
    }

    public long countSinhVienByKhoa(int khoaId) {
        return this.sinhVienRepository.countByKhoaId(khoaId);
    }

    // public long countSinhVienByTrangThai(String trangThai) {

    // return this.studentRepository.countByTrangThai(trangThai);
    // }

    // public List<Student> findSinhVienByname(String hoTen) {

    // return this.studentRepository.findByTenSVContainingIgnoreCase(hoTen);
    // }
    public List<KhoaPhanTramDTO> getPhanTramTheoKhoa() {
        List<KhoaPhanTramDTO> list = sinhVienRepository.demSinhVienTheoKhoa();

        // Tính tổng SV toàn trường
        long tongSV = list.stream()
                .mapToLong(KhoaPhanTramDTO::getSoSinhVien)
                .sum();

        if (tongSV == 0)
            return list;

        // Tính % từng khoa, làm tròn 1 chữ số thập phân
        list.forEach(k -> {
            double phanTram = (k.getSoSinhVien() * 100.0) / tongSV;
            k.setPhanTram(Math.round(phanTram * 10.0) / 10.0);
        });

        return list;
    }

    public Double getTyLeTotNghiep() {
        long tongSV = sinhVienRepository.count();
        if (tongSV == 0)
            return 0.0;

        long soTotNghiep = sinhVienRepository.countByTrangThai("Tốt nghiệp");
        double tyLe = (soTotNghiep * 100.0) / tongSV;
        return Math.round(tyLe * 10.0) / 10.0; // Làm tròn 1 chữ số thập phân
    }

}
