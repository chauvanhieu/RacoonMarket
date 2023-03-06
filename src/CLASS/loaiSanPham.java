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
public class loaiSanPham {
    String idLoaiSanPham ;
    String name ;
    String ghiChu ;
    boolean trangThai ;

    public loaiSanPham(String idLoaiSanPham, String name, String ghiChu, boolean trangThai) {
        this.idLoaiSanPham = idLoaiSanPham;
        this.name = name;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public loaiSanPham() {
    }

    public String getIdLoaiSanPham() {
        return idLoaiSanPham;
    }

    public void setIdLoaiSanPham(String idLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
