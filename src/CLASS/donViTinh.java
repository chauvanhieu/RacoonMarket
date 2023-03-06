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
public class donViTinh {
    String idDonViTinh ;
    String name ;
    String ghiChu ;
    boolean trangThai ;

    public donViTinh() {
    }

    public donViTinh(String idDonViTinh, String name, String ghiChu, boolean trangThai) {
        this.idDonViTinh = idDonViTinh;
        this.name = name;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public String getIdDonViTinh() {
        return idDonViTinh;
    }

    public void setIdDonViTinh(String idDonViTinh) {
        this.idDonViTinh = idDonViTinh;
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
