/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASS;

/**
 *
 * @author Admin
 */
public class nhanVien {

    String idNhanVien;
    String name;
    String soDienthoai;
    String diaChi;
    String ngaySinh;
    long Luong;
    boolean gioiTinh;
    String ngayVaoLam;
    String ghiChu;
    boolean trangThai;

    public nhanVien() {
    }

    public nhanVien(String idNhanVien, 
            String name,
            String soDienthoai, 
            String diaChi,
            String ngaySinh, 
            long Luong,
            boolean gioiTinh,
            String ngayVaoLam, 
            String ghiChu,
            boolean trangThai) {
        this.idNhanVien = idNhanVien;
        this.name = name;
        this.soDienthoai = soDienthoai;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.Luong = Luong;
        this.gioiTinh = gioiTinh;
        this.ngayVaoLam = ngayVaoLam;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoDienthoai() {
        return soDienthoai;
    }

    public void setSoDienthoai(String soDienthoai) {
        this.soDienthoai = soDienthoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public long getLuong() {
        return Luong;
    }

    public void setLuong(long Luong) {
        this.Luong = Luong;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(String ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

}
