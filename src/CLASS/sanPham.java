package CLASS;

import javax.swing.ImageIcon;

public class sanPham {

    String idSanPham;
    String name;
    String barcode;
    String hinhAnh;
    long giaNhap;
    long giaBan;
    long giaSi;
    int soLuong;
    int soLuongToiThieu;
    String idNhaCungCap;
    String idDonViTinh;
    String idLoaiSanPham;
    String ghiChu;
    boolean trangThai;

    public sanPham() {
    }

    public sanPham(
            String idSanPham,
            String name,
            String barcode,
            String hinhAnh,
            long giaNhap,
            long giaBan,
            long giaSi,
            int soLuong,
            int soLuongToiThieu,
            String idNhaCungCap,
            String idDonViTinh,
            String idLoaiSanPham,
            String ghiChu,
            boolean trangThai) {
        this.idSanPham = idSanPham;
        this.name = name;
        this.hinhAnh = hinhAnh;
        this.barcode = barcode;
        this.giaSi = giaSi;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.soLuongToiThieu = soLuongToiThieu;
        this.idNhaCungCap = idNhaCungCap;
        this.idDonViTinh = idDonViTinh;
        this.idLoaiSanPham = idLoaiSanPham;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public long getGiaSi() {
        return giaSi;
    }

    public void setGiaSi(long giaSi) {
        this.giaSi = giaSi;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public long getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(long giaNhap) {
        this.giaNhap = giaNhap;
    }

    public long getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(long giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoLuongToiThieu() {
        return soLuongToiThieu;
    }

    public void setSoLuongToiThieu(int soLuongToiThieu) {
        this.soLuongToiThieu = soLuongToiThieu;
    }

    public String getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public void setIdNhaCungCap(String idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public String getIdDonViTinh() {
        return idDonViTinh;
    }

    public void setIdDonViTinh(String idDonViTinh) {
        this.idDonViTinh = idDonViTinh;
    }

    public String getIdLoaiSanPham() {
        return idLoaiSanPham;
    }

    public void setIdLoaiSanPham(String idLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
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
