package MODEL;

import CLASS.nhanVien;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MDNhanVien {

    public static void search(JTable table, String keyword) {
        String sql = "SELECT *,DATE_FORMAT(date(NgaySinh), \"%d/%l/%Y\") as 'sinhnhat',DATE_FORMAT(date(NgayVaoLam), \"%d/%l/%Y\") as 'ngaylam' FROM nhanvien "
                + " where id = '%" + keyword + "%' or name = '%" + keyword + "%' or sodienthoai = '%" + keyword + "%' "
                + "";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("gioitinh") == 1 ? "Nam" : "Nữ",
                    rs.getString("diachi"),
                    rs.getString("sodienthoai"),
                    rs.getString("sinhnhat"),
                    HELPER.helper.LongToString(rs.getLong("luong")),
                    rs.getString("ngaylam"),
                    rs.getString("ghichu"),
                    rs.getInt("trangthai") == 1 ? true : false,});
            }
        } catch (Exception ex) {
        }
        table.setModel(model);
    }

    public static ArrayList<nhanVien> getDataToTable() {
        ArrayList<nhanVien> data = new ArrayList<>();
        String sql = "SELECT *,DATE_FORMAT(date(NgaySinh), \"%d/%l/%Y\") as 'sinhnhat',DATE_FORMAT(date(NgayVaoLam), \"%d/%l/%Y\") as 'ngaylam' FROM nhanvien ORDER BY TrangThai desc";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                data.add(new nhanVien(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("sodienthoai"),
                        rs.getString("diachi"),
                        rs.getString("sinhnhat"),
                        rs.getLong("luong"),
                        rs.getInt("gioitinh") == 1 ? true : false,
                        rs.getString("ngaylam"),
                        rs.getString("ghichu"),
                        rs.getInt("trangthai") == 1 ? true : false));
            }
        } catch (Exception ex) {
        }
        return data;
    }

    public static void arrayToTable(JTable table) {
        String sql = "SELECT *,DATE_FORMAT(date(NgaySinh), \"%d/%l/%Y\") as 'sinhnhat',DATE_FORMAT(date(NgayVaoLam), \"%d/%l/%Y\") as 'ngaylam' FROM nhanvien ORDER BY TrangThai desc";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("gioitinh") == 1 ? "Nam" : "Nữ",
                    rs.getString("diachi"),
                    rs.getString("sodienthoai"),
                    rs.getString("sinhnhat"),
                    HELPER.helper.LongToString(rs.getLong("luong")),
                    rs.getString("ngaylam"),
                    rs.getString("ghichu"),
                    rs.getInt("trangthai") == 1 ? true : false,});
            }
        } catch (Exception ex) {
        }
        table.setModel(model);
    }

    public static ArrayList<nhanVien> getAll() {
        ArrayList<nhanVien> data = new ArrayList<>();
        String sql = "select * from nhanvien where trangthai = 1";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                data.add(new nhanVien(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("sodienthoai"),
                        rs.getString("diachi"),
                        rs.getString("ngaySinh"),
                        rs.getLong("luong"),
                        rs.getInt("gioiTinh") == 1 ? true : false,
                        rs.getString("ngaylam"),
                        rs.getString("ghichu"),
                        rs.getInt("trangthai") == 1 ? true : false
                ));
            }
        } catch (Exception e) {
        }

        return data;
    }

    public static ArrayList<nhanVien> getData() {
        String sql = "Select * from nhanvien where nhanvien.trangthai = 1";
        ArrayList<nhanVien> data = new ArrayList<>();
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                data.add(new nhanVien(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("sodienthoai"),
                        rs.getString("diachi"),
                        rs.getString("ngaySinh"),
                        rs.getLong("luong"),
                        rs.getInt("gioiTinh") == 1 ? true : false,
                        rs.getString("ngayvaolam"),
                        rs.getString("ghichu"),
                        rs.getInt("trangthai") == 1 ? true : false
                ));
            }
        } catch (Exception e) {
        }
        return data;
    }

    public static nhanVien getNhanVien(String id) {
        String sql = "SELECT *,date(NgaySinh) as 'sinhnhat',date(NgayVaoLam) as 'ngaylam' FROM nhanvien where id = ? ";
        nhanVien nhanvien = new nhanVien();
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, id);
        try {
            while (rs.next()) {

                nhanvien.setIdNhanVien(rs.getString("id"));
                nhanvien.setName(rs.getString("name"));
                nhanvien.setSoDienthoai(rs.getString("sodienthoai"));
                nhanvien.setDiaChi(rs.getString("diachi"));
                nhanvien.setNgaySinh(rs.getString("sinhnhat"));
                nhanvien.setLuong(rs.getLong("luong"));
                nhanvien.setGioiTinh(rs.getInt("gioitinh") == 1 ? true : false);
                nhanvien.setNgayVaoLam(rs.getString("ngaylam"));
                nhanvien.setGhiChu(rs.getString("ghichu"));
                nhanvien.setTrangThai(rs.getInt("trangthai") == 1 ? true : false);

            }
        } catch (Exception e) {
        }

        return nhanvien;
    }

    public static void add(nhanVien nhanvien) {
        String sql = "insert into nhanvien values(?,?,?,?,?,?,?,?,?,?)";
        HELPER.SQLhelper.executeUpdate(sql,
                nhanvien.getIdNhanVien(),
                nhanvien.getName(),
                nhanvien.getSoDienthoai(),
                nhanvien.getDiaChi(),
                nhanvien.getNgaySinh(),
                nhanvien.getLuong(),
                nhanvien.isGioiTinh() == true ? 1 : 0,
                nhanvien.getNgayVaoLam(),
                nhanvien.getGhiChu(),
                nhanvien.isTrangThai() == true ? 1 : 0
        );
    }

    public static void remove(String id) {
        String sql = "update nhanvien set trangthai=0 where id=?";
        HELPER.SQLhelper.executeUpdate(sql, id);
    }

    public static void update(nhanVien nv) {
        String sql = "update nhanvien set name=?,sodienthoai=?,diachi=?,ngaySinh=?,luong=?,ngayvaolam=?,gioitinh=?,ghichu=?,trangthai=? where id=?";
        HELPER.SQLhelper.executeUpdate(sql,
                nv.getName(),
                nv.getSoDienthoai(),
                nv.getDiaChi(),
                nv.getNgaySinh(),
                nv.getLuong(),
                nv.getNgayVaoLam(),
                nv.isGioiTinh() == true ? 1 : 0,
                nv.getGhiChu(),
                nv.isTrangThai() == true ? 1 : 0,
                nv.getIdNhanVien());
    }

    public static void reStore(String id) {
        String sql = "update nhanvien set trangthai=1 where id = ? ";
        HELPER.SQLhelper.executeUpdate(sql, id);
    }

    //hàm auto create ID cho nhân viên
    public static String createID() {
        String id = "NV";
        String date = HELPER.helper.LayNgayString(new Date(), "ddMM");
        Random r = new Random();
        String alphabet = "1234567890";
        String random = "";
        for (int i = 0; i < 2; i++) {
            random += r.nextInt(alphabet.length());
        }
        return id + date + random;
    }
}
