/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import CLASS.chiTietHoaDon;
import CLASS.hoaDonNhapHang;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class MDNhapHang {

    public static void update(hoaDonNhapHang hoadon, hoaDonNhapHang hoadoncu,
            ArrayList<chiTietHoaDon> dataCu, ArrayList<chiTietHoaDon> dataMoi
    ) {
        /*
        reset tồn kho
        reset công nợ nhà cung cấp
        xóa chi tiết cũ
        
        
        Thêm lại chi tiết hóa đơn mới
        cộng tồn kho sản phẩm
        Sửa hóa đơn
        cập nhật nợ nhà cung cấp
         */
        String sqlXoaChiTiet = "delete from chitiethoadon where idhoadon = ?";
        String sqlResetCongNoNhaCungCap = "update nhacungcap set congno = congno - ? where id = ?";
        String sqlResetTonKho = "update sanpham set soluong = soluong - ? where id = ? ";
        String sqlCapNhatCongNo = " update nhacungcap set congno = congno + ? where id = ? ";
        String sqlTaoChiTietHoaDon = "insert into chitiethoadon(idhoadon,idsanpham,soluong,chitiethoadon.giaban,trangthai) values(?,?,?,?,3)";
        String sqlThemSoluongSanPham = "update sanpham set soluong = soluong + ? where id = ?";
        String sqlCapNhatHoaDon = "update nhatkynhaphang set idnhacungcap = ?,tongtien=?,thanhtoan=?,ghichu=?,trangthai=? where id = ?";

        // xóa chi tiết hóa đơn cũ
        HELPER.SQLhelper.executeUpdate(sqlXoaChiTiet, hoadon.getId());

        for (chiTietHoaDon item : dataCu) {
            //reset tồn kho về ban đầu
            HELPER.SQLhelper.executeUpdate(sqlResetTonKho, item.getSoLuongNhapHang(), item.getIdSanPham());
        }

        HELPER.SQLhelper.executeUpdate(sqlResetCongNoNhaCungCap,
                hoadoncu.getTongTien() - hoadoncu.getThanhToan(),
                hoadoncu.getIdNhaCungCap());

        HELPER.SQLhelper.executeUpdate(sqlCapNhatHoaDon,
                hoadon.getIdNhaCungCap(),
                hoadon.getTongTien(),
                hoadon.getThanhToan(),
                hoadon.getGhiChu(),
                hoadon.isTrangThai() == true ? 1 : 0,
                hoadoncu.getId()
        );
        HELPER.SQLhelper.executeUpdate(sqlCapNhatCongNo,
                hoadon.getTongTien() - hoadon.getThanhToan(),
                hoadon.getIdNhaCungCap()
        );

        for (chiTietHoaDon item : dataMoi) {
            HELPER.SQLhelper.executeUpdate(sqlTaoChiTietHoaDon,
                    hoadon.getId(),
                    item.getIdSanPham(),
                    item.getSoLuongNhapHang(),
                    item.getGiaNhap()
            );

            HELPER.SQLhelper.executeUpdate(sqlThemSoluongSanPham,
                    item.getSoLuongNhapHang(),
                    item.getIdSanPham()
            );
        }
    }

    public static hoaDonNhapHang getHoaDon(String id) {
        String sql = "select * from nhatkynhaphang where id = ?";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, id);
        hoaDonNhapHang hoadon = null;

        try {
            while (rs.next()) {
                hoadon = new hoaDonNhapHang(
                        rs.getString("id"),
                        rs.getString("thoigian"),
                        rs.getString("idnhanvien"),
                        rs.getString("idnhacungcap"),
                        rs.getString("ghichu"),
                        rs.getLong("tongtien"),
                        rs.getLong("thanhtoan"),
                        rs.getInt("trangthai") == 1 ? true : false
                );
            }
        } catch (Exception e) {
        }
        return hoadon;
    }

    public static void loadTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        String sql = "select nhatkynhaphang.thoigian as 'thoigian', nhatkynhaphang.ID as 'id' ,nhanvien.name as 'tennhanvien' ,nhacungcap.name as 'nhacungcap',\n"
                + "nhatkynhaphang.TongTien as 'tongtien', nhatkynhaphang.ThanhToan as 'thanhtoan', nhatkynhaphang.GhiChu as 'ghichu',\n"
                + "nhatkynhaphang.TrangThai as 'trangthai'\n"
                + "from nhatkynhaphang\n"
                + "join nhanvien on nhanvien.id = nhatkynhaphang.IDnhanvien\n"
                + "join nhacungcap on nhacungcap.id = nhatkynhaphang.IDNhaCungCap\n"
                //                + "where nhatkynhaphang.TrangThai=1\n"
                + " order by nhatkynhaphang.thoigian desc "
                + "limit 50";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("thoigian"),
                    rs.getString("tennhanvien"),
                    rs.getString("nhacungcap"),
                    HELPER.helper.SoString(rs.getLong("tongtien")),
                    HELPER.helper.SoString(rs.getLong("thanhtoan")),
                    rs.getString("ghichu"),
                    rs.getInt("trangthai") == 1 ? true : false
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
    }

    public static void loadTable(JTable table, String keyword, String dateFrom, String dateTo) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        String sql = "select nhatkynhaphang.thoigian as 'thoigian', nhatkynhaphang.ID as 'id' ,nhanvien.name as 'tennhanvien' ,nhacungcap.name as 'nhacungcap',\n"
                + "               nhatkynhaphang.TongTien as 'tongtien', nhatkynhaphang.ThanhToan as 'thanhtoan', nhatkynhaphang.GhiChu as 'ghichu',\n"
                + "               nhatkynhaphang.TrangThai as 'trangthai'\n"
                + "               from nhatkynhaphang\n"
                + "                join nhanvien on nhanvien.id = nhatkynhaphang.IDnhanvien\n"
                + "                join nhacungcap on nhacungcap.id = nhatkynhaphang.IDNhaCungCap\n"
                + "                where \n"
                + "               ( date(nhatkynhaphang.thoigian) between ?  and ? ) and \n"
                + "                ( nhatkynhaphang.id like '%" + keyword + "%' or \n"
                + "                nhatkynhaphang.idnhanvien like '%" + keyword + "%'  or \n"
                + "                 nhanvien.name like '%" + keyword + "%' or \n"
                + "                nhacungcap.name like '%" + keyword + "%' or\n"
                + "               nhatkynhaphang.idnhacungcap like '%" + keyword + "%'   \n"
                + "                 ) \n"
                + " order by nhatkynhaphang.thoigian desc "
                + "               limit 50";
        if (keyword.trim().equals("")) {
            sql = "select nhatkynhaphang.thoigian as 'thoigian', nhatkynhaphang.ID as 'id' ,nhanvien.name as 'tennhanvien' ,nhacungcap.name as 'nhacungcap',\n"
                    + "               nhatkynhaphang.TongTien as 'tongtien', nhatkynhaphang.ThanhToan as 'thanhtoan', nhatkynhaphang.GhiChu as 'ghichu',\n"
                    + "               nhatkynhaphang.TrangThai as 'trangthai'\n"
                    + "               from nhatkynhaphang\n"
                    + "                join nhanvien on nhanvien.id = nhatkynhaphang.IDnhanvien\n"
                    + "                join nhacungcap on nhacungcap.id = nhatkynhaphang.IDNhaCungCap\n"
                    + "                where \n"
                    + "                date(nhatkynhaphang.thoigian) between ?  and ?  "
                    + " order by nhatkynhaphang.thoigian desc "
                    + "               limit 50";
        }
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, dateFrom, dateTo);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("thoigian"),
                    rs.getString("tennhanvien"),
                    rs.getString("nhacungcap"),
                    HELPER.helper.SoString(rs.getLong("tongtien")),
                    HELPER.helper.SoString(rs.getLong("thanhtoan")),
                    rs.getString("ghichu"),
                    rs.getInt("trangthai") == 1 ? true : false
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
    }

    public static void showHoaDonNhapHangTheoNhaCungCap(JTable table, String idNhaCungCap) {
        String sql = "select nhatkynhaphang.ID as 'id' , nhanvien.name as 'nhanvien' , \n"
                + " nhatkynhaphang.TongTien as 'tongtien', nhatkynhaphang.ThanhToan as 'thanhtoan',\n"
                + " nhatkynhaphang.ThoiGian as 'thoigian', nhatkynhaphang.GhiChu as 'ghichu' from nhatkynhaphang\n"
                + " join nhanvien on nhanvien.id = nhatkynhaphang.IDnhanvien\n"
                + " where nhatkynhaphang.IDNhaCungCap = ? \n"
                + " order by nhatkynhaphang.ThoiGian desc";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, idNhaCungCap);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("nhanvien"),
                    HELPER.helper.SoString(rs.getLong("tongtien")),
                    HELPER.helper.SoString(rs.getLong("thanhtoan")),
                    rs.getString("thoigian"),
                    rs.getString("ghichu")
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
    }

    public static void loadChiTietHoaDon(JTable table, String idHoaDon) {
        String sql = "select chitiethoadon.soluong as 'soluong',(chitiethoadon.soluong*sanpham.GiaNhap) as 'thanhtien', "
                + "sanpham.name as 'tensanpham',sanpham.GiaNhap as 'gianhap',donvitinh.Name as 'donvitinh' from chitiethoadon "
                + "join sanpham on sanpham.ID=chitiethoadon.idsanpham "
                + "join donvitinh on donvitinh.id=sanpham.IDDonViTinh "
                + " "
                + "where chitiethoadon.idhoadon = ?";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, idHoaDon);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("tensanpham"),
                    rs.getInt("soluong"),
                    rs.getString("donvitinh"),
                    HELPER.helper.SoString(rs.getLong("gianhap")),
                    HELPER.helper.SoString(rs.getLong("thanhtien"))
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
    }

    public static String createID() {
        String id = "NH";
        String date = HELPER.helper.LayNgayString(new Date(), "ddMM");
        Random r = new Random();
        String alphabet = "1234567890";
        String random = "";
        for (int i = 0; i < 4; i++) {
            random += r.nextInt(alphabet.length());
        }
        return id + date + random;
    }

    public static void nhapHang(hoaDonNhapHang hoadon, ArrayList<chiTietHoaDon> data) {
        /*
        B1: tạo mới hóa đơn
        B2: tạo các chi tiết hóa đơn ( trạng thái : 3 )
        B3: Cập nhật số lượng tồn kho
         */
        String sqlTaoHoaDon = "insert into nhatkynhaphang values(?,?,?,?,?,?,?,?)";
        String sqlTaoChiTietHoaDon = "insert into chitiethoadon(idhoadon,idsanpham,soluong,chitiethoadon.giaban,trangthai) values(?,?,?,?,3)";
        String sqlCapNhatSanPham = "update sanpham set soluong = soluong + ? where id = ?";
        String sqlCongNoNhaCungCap = "update nhacungcap set congno = congno + ? where id = ?";
        HELPER.SQLhelper.executeUpdate(sqlTaoHoaDon,
                hoadon.getId(),
                hoadon.getThoiGian(),
                hoadon.getIdNhanVien(),
                hoadon.getIdNhaCungCap(),
                hoadon.getTongTien(),
                hoadon.getThanhToan(),
                hoadon.getGhiChu(),
                true
        );

        for (chiTietHoaDon item : data) {
            HELPER.SQLhelper.executeUpdate(sqlTaoChiTietHoaDon,
                    hoadon.getId(),
                    item.getIdSanPham(),
                    item.getSoLuongNhapHang(),
                    item.getGiaNhap()
            );
            HELPER.SQLhelper.executeUpdate(sqlCapNhatSanPham, item.getSoLuongNhapHang(), item.getIdSanPham());
        }

        HELPER.SQLhelper.executeUpdate(sqlCongNoNhaCungCap,
                hoadon.getTongTien() - hoadon.getThanhToan(),
                hoadon.getIdNhaCungCap());
    }
}
