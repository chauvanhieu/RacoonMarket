package CLASS;

public class chiTietHoaDon {

    String idSanPham;
    String tenSanPham;
    String donViTinh;
    int soLuong;
    int tonKho;
    int soLuongNhapHang;
    int soLuongTraHang;
    long donGia;
    long giaSi;
    long giaNhap;
    boolean trangThai;

    public long getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(long giaNhap) {
        this.giaNhap = giaNhap;
    }

    public chiTietHoaDon(
            String idSanPham,
            String tenSanPham,
            String donViTinh,
            int soLuong,
            int tonKho,
            int soLuongNhapHang,
            int soLuongTraHang,
            long donGia,
            long giaSi,
            long giaNhap,
            boolean trangThai) {
        this.giaNhap = giaNhap;
        this.idSanPham = idSanPham;
        this.tonKho = tonKho;
        this.soLuongNhapHang = soLuongNhapHang;
        this.tenSanPham = tenSanPham;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.soLuongTraHang = soLuongTraHang;
        this.trangThai = trangThai;
        this.giaSi = giaSi;
    }

    public int getSoLuongTraHang() {
//        if (soLuongTraHang > tonKho) {
//            return tonKho;
//        }

        return soLuongTraHang;
    }

    public void setSoLuongTraHang(int soLuongTraHang) {
        this.soLuongTraHang = soLuongTraHang;
    }

    public int getSoLuongNhapHang() {
        return soLuongNhapHang;
    }

    public void setSoLuongNhapHang(int soLuongNhapHang) {
        this.soLuongNhapHang = soLuongNhapHang;
    }

    public int getTonKho() {
        return tonKho;
    }

    public void setTonKho(int tonKho) {
        this.tonKho = tonKho;
    }

    public long getThanhTienGiaSi() {
        return soLuong * giaSi;
    }

    public long getGiaSi() {
        return giaSi;
    }

    public void setGiaSi(long giaSi) {
        this.giaSi = giaSi;
    }

    public long getThanhTien() {
        return soLuong * donGia;
    }

    public long getThanhTienGiaNhap() {
        return soLuong * giaNhap;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuong() {
//        if (soLuong > tonKho) {
//            setSoLuong(tonKho);
//            return tonKho;
//        }
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

}
