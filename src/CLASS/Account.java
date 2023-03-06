
package src.CLASS;

/**
 *
 * @author Admin
 */
public class Account {

    String IdNhanVien;
    String username;
    String password;
    boolean trangThai;
    boolean banHang;
    boolean nhapHang;
    boolean taiKhoan;
    boolean hangHoa;
    boolean nhanVien;
    boolean khachHang;
    boolean nhaCungCap;
    boolean baoCao;
    boolean phieuChi;

    public Account() {
    }

    public Account(String IDNhanVien,String username, String password,boolean trangThai,boolean banHang,boolean nhapHang,
            boolean taiKhoan,
            boolean hangHoa,
            boolean nhanVien,
            boolean khachHang,
            boolean nhaCungCap,
            boolean baoCao,
            boolean phieuChi) {
        this.IdNhanVien = IDNhanVien;
        this.username = username;
        this.password = password;
        this.trangThai = trangThai;
        this.banHang = banHang;
        this.nhapHang = nhapHang;
        this.taiKhoan = taiKhoan;
        this.hangHoa = hangHoa;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.nhaCungCap = nhaCungCap;
        this.baoCao = baoCao;
        this.phieuChi = phieuChi;
    }

    public String getIdNhanVien() {
        return IdNhanVien;
    }

    public void setIdNhanVien(String IDNhanVien) {
        this.IdNhanVien = IDNhanVien;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean isBanHang() {
        return banHang;
    }

    public void setBanHang(boolean banHang) {
        this.banHang = banHang;
    }

    public boolean isNhapHang() {
        return nhapHang;
    }

    public void setNhapHang(boolean nhapHang) {
        this.nhapHang = nhapHang;
    }

    public boolean isTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(boolean taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public boolean isHangHoa() {
        return hangHoa;
    }

    public void setHangHoa(boolean hangHoa) {
        this.hangHoa = hangHoa;
    }

    public boolean isNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(boolean nhanVien) {
        this.nhanVien = nhanVien;
    }

    public boolean isKhachHang() {
        return khachHang;
    }

    public void setKhachHang(boolean khachHang) {
        this.khachHang = khachHang;
    }

    public boolean isNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(boolean nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public boolean isBaoCao() {
        return baoCao;
    }

    public void setBaoCao(boolean baoCao) {
        this.baoCao = baoCao;
    }

    public boolean isPhieuChi() {
        return phieuChi;
    }

    public void setPhieuChi(boolean phieuChi) {
        this.phieuChi = phieuChi;
    }

}
