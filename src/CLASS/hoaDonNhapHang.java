/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASS;

/**
 *
 * @author Admin
 */
public class hoaDonNhapHang {

    private String id, thoiGian, idNhanVien, idNhaCungCap, ghiChu;
    private long tongTien, thanhToan;
    private boolean trangThai;

    public hoaDonNhapHang(String id, String thoiGian, String idNhanVien, String idNhaCungCap, String ghiChu, long tongTien, long thanhToan, boolean trangThai) {
        this.id = id;
        this.thoiGian = thoiGian;
        this.idNhanVien = idNhanVien;
        this.idNhaCungCap = idNhaCungCap;
        this.ghiChu = ghiChu;
        this.tongTien = tongTien;
        this.thanhToan = thanhToan;
        this.trangThai = trangThai;
    }

    public hoaDonNhapHang clone() {
        return this;
    }

    public hoaDonNhapHang() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public void setIdNhaCungCap(String idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public long getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(long thanhToan) {
        this.thanhToan = thanhToan;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

}
