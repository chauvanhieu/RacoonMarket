package MODEL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import src.CLASS.Account;

public class MDAccount {

    public static Account getAccount(String username) {
        String sql = "select *,nhanvien.name as 'tennhanvien' from account"
                + " join nhanvien on nhanvien.id = account.idnhanvien "
                + " where username = ?;";

        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, username);
        Account acc = null; // ta?o ra
        try {
            while (rs.next()) {

                acc = new Account(
                        rs.getString("idnhanvien"),
                        rs.getString("UserName"),
                        rs.getString("Password"),
                        rs.getInt("trangThai") == 1 ? true : false,
                        rs.getInt("banhang") == 1 ? true : false,
                        rs.getInt("nhaphang") == 1 ? true : false,
                        rs.getInt("taikhoan") == 1 ? true : false,
                        rs.getInt("hanghoa") == 1 ? true : false,
                        rs.getInt("nhanvien") == 1 ? true : false,
                        rs.getInt("khachhang") == 1 ? true : false,
                        rs.getInt("nhacungcap") == 1 ? true : false,
                        rs.getInt("baocao") == 1 ? true : false,
                        rs.getInt("phieuchi") == 1 ? true : false
                );
            }
        } catch (Exception e) {
        }

        return acc;
    }

    public static ArrayList<Account> getDataToTable() {

        String sql = "select username,account.password,nhanvien.name as 'tennhanvien',account.TrangThai,banhang,nhaphang,taikhoan,hanghoa,nhanvien,khachhang,account.nhacungcap,baocao,phieuchi from account\n"
                + "join nhanvien on nhanvien.id=account.IDNhanVien"
                + " where username != 'admin' ";
        ArrayList<Account> data = new ArrayList<>();
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                data.add(new Account(
                        rs.getString("tennhanvien"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("trangthai") == 1 ? true : false,
                        rs.getInt("banhang") == 1 ? true : false,
                        rs.getInt("nhaphang") == 1 ? true : false,
                        rs.getInt("taikhoan") == 1 ? true : false,
                        rs.getInt("hanghoa") == 1 ? true : false,
                        rs.getInt("nhanvien") == 1 ? true : false,
                        rs.getInt("khachhang") == 1 ? true : false,
                        rs.getInt("nhacungcap") == 1 ? true : false,
                        rs.getInt("baocao") == 1 ? true : false,
                        rs.getInt("phieuchi") == 1 ? true : false
                ));
            }
        } catch (Exception ex) {
        }
        return data;
    }

    public static ArrayList<Account> getAll() {
        ArrayList<Account> arr = new ArrayList<>();
        String sql = "Select account.*,nhanvien.name as 'tennhanvien' From Account join nhanvien on nhanvien.id=account.idnhanvien";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                Account acc = new Account();

                acc.setUsername(rs.getString("UserName"));
                acc.setPassword(rs.getString("Password"));
                acc.setIdNhanVien(rs.getString("tennhanvien"));
                acc.setTrangThai(rs.getInt("trangThai") == 1 ? true : false);
                acc.setBanHang(rs.getInt("banhang") == 1 ? true : false);
                acc.setBaoCao(rs.getInt("baocao") == 1 ? true : false);
                acc.setKhachHang(rs.getInt("khachhang") == 1 ? true : false);
                acc.setNhaCungCap(rs.getInt("nhacungcap") == 1 ? true : false);
                acc.setNhanVien(rs.getInt("nhanvien") == 1 ? true : false);
                acc.setNhapHang(rs.getInt("nhaphang") == 1 ? true : false);
                acc.setTaiKhoan(rs.getInt("taikhoan") == 1 ? true : false);
                acc.setPhieuChi(rs.getInt("phieuchi") == 1 ? true : false);
                acc.setHangHoa(rs.getInt("hanghoa") == 1 ? true : false);
                arr.add(acc);
            }
        } catch (Exception ex) {
        }
        return arr;
    }

    public static void add(Account acc) {
        String sql = "INSERT INTO Account (Username, Password, IDNhanVien, TrangThai, BanHang, NhapHang, TaiKhoan, HangHoa, NhanVien, "
                + " KhachHang, NhaCungCap, BaoCao, PhieuChi) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        HELPER.SQLhelper.executeUpdate(sql,
                acc.getUsername(),
                acc.getPassword(),
                acc.getIdNhanVien(),
                acc.isTrangThai() == true ? 1 : 0,
                acc.isBanHang() == true ? 1 : 0,
                acc.isNhapHang() == true ? 1 : 0,
                acc.isTaiKhoan() == true ? 1 : 0,
                acc.isHangHoa() == true ? 1 : 0,
                acc.isNhanVien() == true ? 1 : 0,
                acc.isKhachHang() == true ? 1 : 0,
                acc.isNhaCungCap() == true ? 1 : 0,
                acc.isBaoCao() == true ? 1 : 0,
                acc.isPhieuChi() == true ? 1 : 0);
    }

    // return 1 dô´i tuo?ng
    public static Account getAccount(String username, String password) {
        String sql = "select * from account where account.username = ? and account.password = ?";
//        String sql = "select * from account where account.username = '" + username + "' and account.password = '" + password + "'";

        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, username, password);
//         ResultSet rs = HELPER.SQLhelper.executeQuery(sql );
        Account acc = new Account();
        try {
            while (rs.next()) {
                acc.setUsername(rs.getString("UserName"));
                acc.setPassword(rs.getString("Password"));
                acc.setIdNhanVien(rs.getString("IDNhanVien"));
                acc.setTrangThai(rs.getInt("trangThai") == 1 ? true : false);
                acc.setBanHang(rs.getInt("banhang") == 1 ? true : false);
                acc.setNhapHang(rs.getInt("nhaphang") == 1 ? true : false);
                acc.setTaiKhoan(rs.getInt("taikhoan") == 1 ? true : false);
                acc.setHangHoa(rs.getInt("hanghoa") == 1 ? true : false);
                acc.setNhanVien(rs.getInt("nhanvien") == 1 ? true : false);
                acc.setKhachHang(rs.getInt("khachhang") == 1 ? true : false);
                acc.setBaoCao(rs.getInt("baocao") == 1 ? true : false);
                acc.setNhaCungCap(rs.getInt("nhacungcap") == 1 ? true : false);
                acc.setPhieuChi(rs.getInt("phieuchi") == 1 ? true : false);
            }
        } catch (Exception e) {
        }

        return acc;// tra? vê`
    }

    //Hàm xóa
    public static void remove(String Username) {
        // không xóa hoàn toản, đưa trạng thái về 0
        String sql = " Update Account set TrangThai = 0 Where Username = ?";
        HELPER.SQLhelper.executeUpdate(sql, Username);
    }

    //Hàm Update
    public static void updateAccount(Account acc) {
        String sql = "UPDATE Account set idnhanvien=?,  Password = ?, TrangThai = ?, BanHang = ?, NhapHang = ?, TaiKhoan = ?, HangHoa = ?,"
                + " NhanVien = ?, KhachHang = ?, NhaCungCap = ?, BaoCao = ?, PhieuChi = ? Where username = ?";
        HELPER.SQLhelper.executeUpdate(sql,
                acc.getIdNhanVien(),
                acc.getPassword(),
                acc.isTrangThai(),
                acc.isBanHang(),
                acc.isNhapHang(),
                acc.isTaiKhoan(),
                acc.isHangHoa(),
                acc.isNhanVien(),
                acc.isKhachHang(),
                acc.isNhaCungCap(),
                acc.isBaoCao(),
                acc.isPhieuChi(),
                acc.getUsername());
    }

    public static void reStore(String username) {
        String sql = "update account set trangthai=1 where username = ?";
        HELPER.SQLhelper.executeUpdate(sql, username);
    }

    public static String createID() {
        String id = "ACC";
        String date = HELPER.helper.LayNgayString(new Date(), "ddMM");
        Random r = new Random();
        String alphabet = "1234567890";
        String random = "";
        for (int i = 0; i < 2; i++) {
            random += r.nextInt(alphabet.length());
        }
        return id + date + random;
    }
    //Không đùa được đâu :)))
}
