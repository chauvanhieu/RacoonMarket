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
public class nhaCungCap {

    String idNhaCungCap;
    String name;
    String soDienThoai;
    String diaChi;
    String ghiChu;
    long congNo;
    boolean trangThai;

    public nhaCungCap() {
    }

    public nhaCungCap(
            String idNhaCungCap,
            String name,
            String soDienThoai,
            String diaChi,
            String ghiChu,
            long congNo,
            boolean trangThai) {
        this.idNhaCungCap = idNhaCungCap;
        this.name = name;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ghiChu = ghiChu;
        this.congNo = congNo;
        this.trangThai = trangThai;
    }

    public String getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public void setIdNhaCungCap(String idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public long getCongNo() {
        return congNo;
    }

    public void setCongNo(long congNo) {
        this.congNo = congNo;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

}
