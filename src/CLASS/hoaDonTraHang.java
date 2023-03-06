package CLASS;

public class hoaDonTraHang {

    String id, thoiGian, idNhanVien, idNhaCungCap, ghiChu;
    int hinhThuc;
    long tongTien;
    boolean trangThai;

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

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public hoaDonTraHang() {
    }

    public int getHinhThuc() {
        return hinhThuc;
    }

    public void setHinhThuc(int hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public hoaDonTraHang(
            String id,
            String thoiGian,
            String idNhanVien,
            String idNhaCungCap,
            String ghiChu,
            long tongTien,
            int hinhThuc,
            boolean trangThai) {
        this.id = id;
        this.thoiGian = thoiGian;
        this.idNhanVien = idNhanVien;
        this.idNhaCungCap = idNhaCungCap;
        this.ghiChu = ghiChu;
        this.hinhThuc = hinhThuc;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }
}
