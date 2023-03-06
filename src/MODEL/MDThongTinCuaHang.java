package MODEL;

import CLASS.ThongTinCuaHang;
import java.sql.ResultSet;

public class MDThongTinCuaHang {

    public static ThongTinCuaHang getThongTin() {
        String sql = "select  * from ThongTinCuaHang";
        ThongTinCuaHang Admin = new ThongTinCuaHang();
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                Admin = new ThongTinCuaHang(
                        rs.getString("TenCuaHang"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("FooterContent")
                );
            }
        } catch (Exception e) {
        }
        return Admin;
    }

    public static void updateThongTin(ThongTinCuaHang Admin) {
        String sql = "Update ThongTinCuaHang set TenCuaHang=?, DiaChi=?, SoDienThoai=?, Email=?, Footercontent=?";
        HELPER.SQLhelper.executeUpdate(sql,
                Admin.getTenCuaHang(),
                Admin.getDiaChi(),
                Admin.getSoDienThoai(),
                Admin.getEmail(),
                Admin.getFooterContent()
        );
    }
}
