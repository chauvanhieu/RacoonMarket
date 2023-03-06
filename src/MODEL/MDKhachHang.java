package MODEL;

import CLASS.khachHang;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class MDKhachHang {

    public static void truNo(String idKhachHang, long tien) {
        String sql = "update khachhang set congno = congno-? where id= ?";

        HELPER.SQLhelper.executeUpdate(sql, tien, idKhachHang);
    }

    public static ArrayList<khachHang> getDataToComboBox() {
        ArrayList<khachHang> data = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE id != 'KH01' and trangthai = 1";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                data.add(new khachHang(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("sodienthoai"),
                        rs.getString("diachi"),
                        rs.getString("ghichu"),
                        rs.getLong("CongNo"),
                        rs.getInt("trangthai") == 1 ? true : false
                ));
            }
        } catch (Exception e) {
        }
        return data;
    }

    public static ArrayList<khachHang> getDataToTable() {
        ArrayList<khachHang> data = new ArrayList<>();
        String sql = "select * from khachhang where khachhang.id != 'KH01' order by congno desc";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                data.add(new khachHang(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("sodienthoai").equals("") ? "Chưa cập nhật" : rs.getString("sodienthoai"),
                        rs.getString("diachi").equals("") ? "Chưa cập nhật" : rs.getString("diachi"),
                        rs.getString("ghichu"),
                        rs.getLong("CongNo"),
                        rs.getInt("trangthai") == 1 ? true : false
                ));
            }
        } catch (Exception e) {
        }
        return data;
    }

    public static void arrayToTable(JTable table) {
        String sql = "select * from khachhang";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("diachi"),
                    rs.getString("sodienthoai"),
                    rs.getString("ghichu"),
                    HELPER.helper.LongToString(rs.getLong("congno")),
                    rs.getInt("trangthai") == 1 ? true : false,});
            }
        } catch (Exception ex) {
        }
        table.setModel(model);
    }

    public static ArrayList<khachHang> getAll() {
        ArrayList<khachHang> data = new ArrayList<>();
        String sql = "select * from khachhang";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                data.add(new khachHang(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("sodienthoai"),
                        rs.getString("diachi"),
                        rs.getString("ghichu"),
                        rs.getLong("CongNo"),
                        rs.getInt("trangthai") == 1 ? true : false
                ));
            }
        } catch (Exception e) {
        }

        return data;
    }

    public static khachHang getKhachHang(String id) {
        String sql = "select * from khachHang where id = ?";
        khachHang kh = new khachHang();
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, id);
        try {
            while (rs.next()) {
                kh = new khachHang(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("sodienthoai"),
                        rs.getString("diachi"),
                        rs.getString("ghichu"),
                        rs.getLong("CongNo"),
                        rs.getInt("trangthai") == 1 ? true : false
                );
            }
        } catch (Exception e) {
        }

        return kh;
    }

    public static void add(khachHang kh) {
        String sql = "insert into khachHang values(?,?,?,?,?,?,?,?)";
        HELPER.SQLhelper.executeUpdate(sql,
                kh.getIdKhachHang(),
                kh.getName(),
                kh.getSoDienThoai(),
                kh.getDiaChi(),
                kh.getGhiChu(),
                kh.getNo(),
                kh.isTrangThai() == true ? 1 : 0,
                HELPER.helper.getDateTime()
        );
    }

    public static void remove(String id) {
        String sql = "update KhachHang set trangthai=0 where id=?";
        HELPER.SQLhelper.executeUpdate(sql, id);
    }

    public static void update(khachHang kh) {
        String sql = "update khachHang set name=?,sodienthoai=?,diachi=?,ghichu=?,congno=?,trangthai=? where id=?";
        HELPER.SQLhelper.executeUpdate(sql,
                kh.getName(),
                kh.getSoDienThoai(),
                kh.getDiaChi(),
                kh.getGhiChu(),
                kh.getNo(),
                kh.isTrangThai() == true ? 1 : 0,
                kh.getIdKhachHang()
        );
    }

    public static void reStore(String id) {
        String sql = "update KhachHang set trangthai=1 where id = ? ";
        HELPER.SQLhelper.executeUpdate(sql, id);
    }

    public static String createID() {
        String id = "KH";
        String date = HELPER.helper.LayNgayString(new Date(), "ddMM");
        Random r = new Random();
        String alphabet = "1234567890";
        String random = "";
        for (int i = 0; i < 4; i++) {
            random += r.nextInt(alphabet.length());
        }
        return id + date + random;
    }

    public static void quickAdd(String id, String name, String sdt, String diachi) {
        String sql = "insert into khachhang(id,name,SoDienThoai,DiaChi,thoigian) values(?,?,?,?,?);";
        HELPER.SQLhelper.executeUpdate(sql, id, name, sdt, diachi, HELPER.helper.getDateTime());
    }
}
