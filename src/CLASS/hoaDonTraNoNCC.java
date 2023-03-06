/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASS;

/**
 *
 * @author Admin
 */
public class hoaDonTraNoNCC {
    private String nhaCungCap,nhanVien,thoiGian,ghiChu;
    long tien;

    public hoaDonTraNoNCC(String nhaCungCap, String nhanVien, String thoiGian, String ghiChu, long tien) {
        this.nhaCungCap = nhaCungCap;
        this.nhanVien = nhanVien;
        this.thoiGian = thoiGian;
        this.ghiChu = ghiChu;
        this.tien = tien;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public long getTien() {
        return tien;
    }

    public void setTien(long tien) {
        this.tien = tien;
    }
    
}
