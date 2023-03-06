/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASS;

public class hoaDon {

    String id, idNhanVien, idKhachHang, thoiGian, ghiChu;
    long giamGia, tongTien, soTienNhanDuoc;
    int hinhThucThanhToan;
    int loaiGia;
    boolean trangThai;

    public long getSoTienNhanDuoc() {
        return soTienNhanDuoc;
    }

    public void setSoTienNhanDuoc(long soTienNhanDuoc) {
        this.soTienNhanDuoc = soTienNhanDuoc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(int hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public long getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(long giamGia) {
        this.giamGia = giamGia;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public hoaDon(
            String id,
            String idNhanVien,
            String idKhachHang,
            String thoiGian,
            int hinhThucThanhToan,
            long soTienNhanDuoc,
            String ghiChu,
            long giamGia,
            long tongTien,
            int loaiGia,
            boolean trangThai) {
        this.loaiGia = loaiGia;
        this.id = id;
        this.soTienNhanDuoc = soTienNhanDuoc;
        this.idNhanVien = idNhanVien;
        this.idKhachHang = idKhachHang;
        this.thoiGian = thoiGian;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.ghiChu = ghiChu;
        this.giamGia = giamGia;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public int getLoaiGia() {
        return loaiGia;
    }

    public void setLoaiGia(int loaiGia) {
        this.loaiGia = loaiGia;
    }

    public hoaDon() {
    }

}
