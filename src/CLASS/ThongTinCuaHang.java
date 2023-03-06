/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASS;

/**
 *
 * @author Admin
 */
public class ThongTinCuaHang {

    String TenCuaHang;
    String DiaChi;
    String SoDienThoai;
    String Email;
    String FooterContent;

    public String getTenCuaHang() {
        return TenCuaHang;
    }

    public void setTenCuaHang(String TenCuaHang) {
        this.TenCuaHang = TenCuaHang;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getFooterContent() {
        return FooterContent;
    }

    public void setFooterContent(String FooterContent) {
        this.FooterContent = FooterContent;
    }

    public ThongTinCuaHang() {
    }

    public ThongTinCuaHang(
            String TenCuaHang,
            String DiaChi,
            String SoDienThoai,
            String Email,
            String FooterContent) {
        this.TenCuaHang = TenCuaHang;
        this.DiaChi = DiaChi;
        this.SoDienThoai = SoDienThoai;
        this.Email = Email;
        this.FooterContent = FooterContent;
    }

}
