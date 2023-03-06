/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
//import static org.openxmlformats.schemas.spreadsheetml.x2006.main.STPhoneticType.Enum.table;

/**
 *
 * @author Admin
 */
public class MDThuChi {
    public static void chiTien(String idNhanVien, String ghiChu, long soTienChi){
        // truyền vào 4 tham số là id nhân viên, thời gian, số tiền chi, lý do
        String sql = "Insert into PhieuChi(ThoiGian, idNhanVien, TienChi, GhiChu) values (?, ?, ?, ?) ";
        HELPER.SQLhelper.executeUpdate(sql, 
                    HELPER.helper.LayNgayString(new Date(), "yyyy-MM-dd hh:mm:ss"),
                    idNhanVien,
                    soTienChi,
                    ghiChu
                );
    }
    
    public static void dataTableDanhSachPhieuChi(JTable table){
        // Viết câu lệnh SQL để select ra những gì cần cho vào bảng
        String sql = "Select PhieuChi.*, NhanVien.name as 'TenNhanVien' From PhieuChi " +
                     " join nhanvien on  nhanvien.id =phieuchi.IDnhanvien" ;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("thoigian"),
                    rs.getString("TenNhanVien"),
                    HELPER.helper.LongToString(rs.getLong("TienChi")),
                    rs.getString("ghichu")
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
    }
}
