package MODEL;

import CLASS.chiTietHoaDon;
import CLASS.hoaDonTrichKho;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class MDTrichKho {

    public static hoaDonTrichKho getHoaDon(String id) {
        hoaDonTrichKho hoadon = null;
        String sql = "select * from trichkho where id = ?";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, id);
        try {
            while (rs.next()) {
                hoadon = new hoaDonTrichKho(
                        rs.getString("id"),
                        rs.getString("idnhanvien"),
                        rs.getString("thoigian"),
                        rs.getString("ghichu")
                );
            }
        } catch (Exception e) {
        }

        return hoadon;
    }

    public static void loadTableHoaDonTrichKho(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String sql = " select trichkho.*,nhanvien.name as 'tennhanvien' from trichkho "
                + "join nhanvien on nhanvien.id=trichkho.IDNhanVien"
                + " order by trichkho.thoigian desc";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        model.setRowCount(0);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("tennhanvien"),
                    rs.getString("thoigian"),
                    rs.getString("ghichu"),});
            }
        } catch (Exception e) {
        }
        table.setModel(model);
    }

    public static void loadTableHoaDonTrichKho(JTable table, String keyword, String dateFrom, String dateTo) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String sql = "select trichkho.*,nhanvien.name as 'tennhanvien' from trichkho "
                + "join nhanvien on nhanvien.id=trichkho.IDNhanVien "
                + "where "
                + "(trichkho.IDNhanVien like '%" + keyword + "%' or "
                + "trichkho.ID like '%" + keyword + "%' or "
                + "nhanvien.name like '%" + keyword + "%')  "
                + "and "
                + "(date(trichkho.ThoiGian) BETWEEN ? and ?) "
                + "order by trichkho.thoigian desc";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, dateFrom, dateTo);
        model.setRowCount(0);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("tennhanvien"),
                    rs.getString("thoigian"),
                    rs.getString("ghichu"),});
            }
        } catch (Exception e) {
        }
        table.setModel(model);
    }

    public static void updateHoaDonTrichKho(String idhoadon, ArrayList<chiTietHoaDon> dataCu, ArrayList<chiTietHoaDon> dataMoi) {
        String sqlResetSoLuong = " update sanpham set soluong = soluong + ? where id = ?";
        String sqlXoaChiTietHoaDonCu = "delete from chitiethoadon where idhoadon = ? ";
        String sqlCapNhatChiTietHoaDon = "insert into chitiethoadon(idhoadon,idsanpham,soluong,chitiethoadon.giaban,trangthai) values(?,?,?,?,2)";
        String sqlCapNhatTonKho = "update sanpham set soluong = soluong - ? where id = ?";

        for (chiTietHoaDon item : dataCu) {
            HELPER.SQLhelper.executeUpdate(sqlResetSoLuong, item.getSoLuong(), item.getIdSanPham());
        }
        HELPER.SQLhelper.executeUpdate(sqlXoaChiTietHoaDonCu, idhoadon);
        for (chiTietHoaDon item : dataMoi) {
            HELPER.SQLhelper.executeUpdate(sqlResetSoLuong, item.getSoLuong(), item.getIdSanPham());
        }

        for (chiTietHoaDon item : dataMoi) {
            HELPER.SQLhelper.executeUpdate(sqlCapNhatChiTietHoaDon,
                    idhoadon,
                    item.getIdSanPham(),
                    item.getSoLuong(),
                    item.getGiaNhap()
            );
            HELPER.SQLhelper.executeUpdate(sqlCapNhatTonKho, item.getSoLuong(), item.getIdSanPham());
        }
    }

    public static void loadChiTietTrichKho(JTable table, String id) {
        String sql = "select chitiethoadon.soluong as 'soluong',(chitiethoadon.soluong*sanpham.GiaNhap) as 'thanhtien', "
                + "sanpham.name as 'tensanpham',sanpham.GiaNhap as 'gianhap',donvitinh.Name as 'donvitinh' from chitiethoadon "
                + "join sanpham on sanpham.ID=chitiethoadon.idsanpham "
                + "join donvitinh on donvitinh.id=sanpham.IDDonViTinh "
                + " "
                + "where chitiethoadon.idhoadon = ?";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, id);
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

    public static void trichKho(hoaDonTrichKho hoadon, ArrayList<chiTietHoaDon> data) {
        // tạo hóa đơn mới
        String sqlTaoHoaDon = "insert into trichkho values(?,?,?,?)";
        String sqlChiTietHoaDon = "insert into chitiethoadon(idhoadon,idsanpham,soluong,chitiethoadon.giaban,trangthai) values(?,?,?,?,2)";
        String sqlCapNhatTonKho = "update sanpham set soluong = soluong - ? where id = ?";
        HELPER.SQLhelper.executeUpdate(sqlTaoHoaDon,
                hoadon.getId(),
                hoadon.getNhanVien(),
                hoadon.getThoiGian(),
                hoadon.getGhiChu()
        );

        for (chiTietHoaDon item : data) {

            HELPER.SQLhelper.executeUpdate(sqlChiTietHoaDon,
                    hoadon.getId(),
                    item.getIdSanPham(),
                    item.getSoLuong(),
                    item.getGiaNhap()
            );

            HELPER.SQLhelper.executeUpdate(sqlCapNhatTonKho, item.getSoLuong(), item.getIdSanPham());

        }
    }

    public static String createID() {
        String id = "TK";
        String date = HELPER.helper.LayNgayString(new Date(), "ddMM");
        Random r = new Random();
        String alphabet = "1234567890";
        String random = "";
        for (int i = 0; i < 4; i++) {
            random += r.nextInt(alphabet.length());
        }
        return id + date + random;
    }
}
