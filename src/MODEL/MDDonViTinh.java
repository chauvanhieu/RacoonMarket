package MODEL;

import CLASS.donViTinh;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MDDonViTinh {

    public static String createID() {
        String id = "DVT";
        String date = HELPER.helper.LayNgayString(new Date(), "ddMM");
        Random r = new Random();
        String alphabet = "1234567890";
        String random = "";
        for (int i = 0; i < 2; i++) {
            random += r.nextInt(alphabet.length());
        }
        return id + date + random;
    }

    public static void add(donViTinh item) {
        String sql = "insert into donvitinh values(?,?,?,?)";
        HELPER.SQLhelper.executeUpdate(sql, item.getIdDonViTinh(), item.getName(), item.getGhiChu(), item.isTrangThai() == true ? 1 : 0);
    }

    public static void remove(String id) {
        String sql = "update donvitinh set trangthai = 0 where id=?";
        HELPER.SQLhelper.executeUpdate(sql, id);
    }

    public static void reStore(String id) {
        String sql = "update donvitinh set trangthai=1 where id=?";
        HELPER.SQLhelper.executeUpdate(sql, id);
    }

    public static void update(donViTinh item) {
        String sql = "update donvitinh set name=?,ghichu=?,trangthai=? where id=?";
        HELPER.SQLhelper.executeUpdate(sql,
                item.getName(),
                item.getGhiChu(),
                item.isTrangThai() == true ? 1 : 0,
                item.getIdDonViTinh());
    }

    public static donViTinh getDonViTinh(String id) {
        donViTinh item = null;
        String sql = "select * from donvitinh where id = ?";

        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, id);
        try {
            while (rs.next()) {
                item = new donViTinh(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("ghichu"),
                        rs.getInt("trangthai") == 1 ? true : false
                );
            }
        } catch (Exception e) {
        }
        return item;
    }

    public static ArrayList<donViTinh> getAll() {
        ArrayList<donViTinh> data = new ArrayList<>();
        String sql = "select * from donvitinh";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                data.add(new donViTinh(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("ghichu"),
                        rs.getInt("trangthai") == 1 ? true : false));
            }
        } catch (Exception ex) {
        }
        return data;
    }

    public static void arrayToTable(JTable table) {
        String sql = "select * from donvitinh";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("ghichu"),
                    rs.getInt("trangthai") == 1 ? true : false,});
            }
        } catch (Exception ex) {
        }
        table.setModel(model);
    }
}
