package CLASS;

public class hoaDonTrichKho {

    private String id, nhanVien, thoiGian, ghiChu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public hoaDonTrichKho(String id, String nhanVien, String thoiGian, String ghiChu) {
        this.id = id;
        this.nhanVien = nhanVien;
        this.thoiGian = thoiGian;
        this.ghiChu = ghiChu;
    }

    public hoaDonTrichKho() {
    }

}
