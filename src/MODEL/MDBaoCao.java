package MODEL;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import javax.swing.JTextField;

public class MDBaoCao {

    public static void loadTableCongNoNhaCungCap(JTable table,
            String dateFrom,
            String dateTo,
            JTextField txtSoTienChiTra,
            JTextField txtTongNoConLai) {
        String sql = "SELECT nhacungcap.name as 'name',nhacungcap.SoDienThoai as 'sodienthoai' ,\n"
                + " nhacungcap.DiaChi as 'diachi',nhacungcap.congno as 'tienconno',\n"
                + " (select sum(phieutranoncc.SoTienTra) from phieutranoncc\n"
                + " where phieutranoncc.IDNcc = nhacungcap.id and date(phieutranoncc.ThoiGian) BETWEEN ?  and ? ) as 'tiendatra'\n"
                + " from nhacungcap where nhacungcap.trangthai = 1\n"
                + " order by nhacungcap.congno desc";

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, dateFrom, dateTo);
        long soTienChiTra = 0;
        long tongNoConLai = 0;
        try {
            while (rs.next()) {
                soTienChiTra += rs.getLong("tiendatra");
                tongNoConLai += rs.getLong("tienconno");
                model.addRow(new Object[]{
                    rs.getString("name"),
                    rs.getString("sodienthoai"),
                    rs.getString("diachi"),
                    HELPER.helper.SoString(rs.getLong("tienconno")),
                    HELPER.helper.SoString(rs.getLong("tiendatra"))
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
        txtSoTienChiTra.setText(HELPER.helper.SoString(soTienChiTra));
        txtTongNoConLai.setText(HELPER.helper.SoString(tongNoConLai));
    }

    public static void loadTableChiPhiNhapHang(JTable table,
            String dateFrom,
            String dateTo,
            JTextField txtTongChiPhiNhapHang,
            JTextField txtTongTienDaThanhToan,
            JTextField txtSoTienNoLaiNCC
    ) {
        String sql = "SELECT nhatkynhaphang.ThoiGian as 'thoigian',nhatkynhaphang.ID as 'maphieu', nhanvien.Name as 'nhanvien', nhacungcap.name as 'nhacungcap',\n"
                + " nhatkynhaphang.TongTien as 'tongtien' , nhatkynhaphang.ThanhToan as 'thanhtoan'\n"
                + " from nhatkynhaphang\n"
                + " join nhanvien on nhanvien.id = nhatkynhaphang.IDnhanvien\n"
                + " join nhacungcap on nhacungcap.ID=nhatkynhaphang.IDNhaCungCap\n"
                + " where date(nhatkynhaphang.ThoiGian) BETWEEN ? and ?";

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, dateFrom, dateTo);
        long tongChiPhiNhapHang = 0;
        long tongTienThanhToan = 0;
        long soTienNoLaiNCC = 0;
        try {
            while (rs.next()) {
                tongTienThanhToan += rs.getLong("thanhtoan");
                tongChiPhiNhapHang += rs.getLong("tongtien");
                soTienNoLaiNCC += rs.getLong("tongtien") - rs.getLong("thanhtoan");
                model.addRow(new Object[]{
                    rs.getString("thoigian"),
                    rs.getString("maphieu"),
                    rs.getString("nhanvien"),
                    rs.getString("nhacungcap"),
                    HELPER.helper.SoString(rs.getLong("tongtien")),
                    HELPER.helper.SoString(rs.getLong("thanhtoan"))
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
        txtSoTienNoLaiNCC.setText(HELPER.helper.SoString(soTienNoLaiNCC));
        txtTongChiPhiNhapHang.setText(HELPER.helper.SoString(tongChiPhiNhapHang));
        txtTongTienDaThanhToan.setText(HELPER.helper.SoString(tongTienThanhToan));
    }

    public static void loadTableCacKhoanChi(JTable table,
            String dateFrom,
            String dateTo,
            JTextField txtTongTienChiTieu) {
        String sql = "SELECT phieuchi.*,nhanvien.name as 'nhanvien'\n"
                + "from phieuchi\n"
                + "join nhanvien on nhanvien.id=phieuchi.IDnhanvien\n"
                + "where date(phieuchi.ThoiGian) BETWEEN ? and ?  ";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, dateFrom, dateTo);
        long tongTienChiTieu = 0;

        try {
            while (rs.next()) {
                tongTienChiTieu += rs.getLong("tienchi");
                model.addRow(new Object[]{
                    rs.getString("thoigian"),
                    rs.getString("nhanvien"),
                    HELPER.helper.SoString(rs.getLong("tienchi")),
                    rs.getString("ghichu")
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
        txtTongTienChiTieu.setText(HELPER.helper.SoString(tongTienChiTieu));
    }

    public static void loadTableLuongNhanVien(JTable table,
            String dateFrom,
            String dateTo,
            JTextField txtTongTienLuong) {
        String sql = "SELECT nhanvien.*,\n"
                + " (SELECT sum(hoadon.TongTienThanhToan) from hoadon\n"
                + " where hoadon.IDnhanvien=nhanvien.id and date(hoadon.ThoiGian) BETWEEN ? and ?) as 'doanhthu'\n"
                + " from nhanvien where nhanvien.trangthai = 1";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, dateFrom, dateTo);
        long tongTienLuong = 0;

        try {
            while (rs.next()) {
                tongTienLuong += rs.getLong("luong");
                model.addRow(new Object[]{
                    rs.getString("name"),
                    rs.getInt("gioiTinh") == 1 ? "Nam" : "Nữ",
                    rs.getString("sodienthoai"),
                    rs.getString("diachi"),
                    rs.getString("ngayvaolam"),
                    HELPER.helper.SoString(rs.getLong("luong")),
                    HELPER.helper.SoString(rs.getLong("doanhthu"))
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
        txtTongTienLuong.setText(HELPER.helper.SoString(tongTienLuong));
    }

    public static void loadTableTienTrichKho(JTable table,
            String dateFrom,
            String dateTo,
            JTextField txtTongTienTrichKho) {
        String sql = "select trichkho.id as 'maphieu', trichkho.ThoiGian as 'thoigian',nhanvien.name as 'nhanvien',"
                + " sanpham.name as 'sanpham',sanpham.GiaNhap as 'gianhap',"
                + " chitiethoadon.soluong as 'soluong',(chitiethoadon.soluong*sanpham.GiaNhap) as 'thanhtien'"
                + " from chitiethoadon\n"
                + " join sanpham on sanpham.ID=chitiethoadon.idsanpham\n"
                + " join trichkho on trichkho.id=chitiethoadon.idhoadon\n"
                + " join nhanvien on nhanvien.id=trichkho.IDNhanVien\n"
                + " where chitiethoadon.trangThai = 2 and date(trichkho.ThoiGian) BETWEEN ? and ? \n"
                + " order by trichkho.ThoiGian desc";

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, dateFrom, dateTo);
        long tongTienTrichKho = 0;

        try {
            while (rs.next()) {
                tongTienTrichKho += rs.getLong("thanhtien");
                model.addRow(new Object[]{
                    rs.getString("thoigian"),
                    rs.getString("maphieu"),
                    rs.getString("nhanvien"),
                    rs.getString("sanpham"),
                    HELPER.helper.SoString(rs.getLong("gianhap")),
                    rs.getInt("soluong"),
                    HELPER.helper.SoString(rs.getLong("thanhtien"))
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
        txtTongTienTrichKho.setText(HELPER.helper.SoString(tongTienTrichKho));
    }

    public static void loadCongNoKhachHang(JTable table,
            String dateFrom,
            String dateTo,
            JTextField txtTongTienDaThu,
            JTextField txtSoTienNoConLai) {
        String sql = "SELECT khachhang.name as 'khachhang', khachhang.SoDienThoai as 'sodienthoai' ,\n"
                + " khachhang.DiaChi as 'diachi',\n"
                + " khachhang.congno 'congno',\n"
                + " (SELECT sum(thutienkhachhang.SoTien) from thutienkhachhang\n"
                + " where date(thutienkhachhang.ThoiGian) BETWEEN ? and ?  and thutienkhachhang.IDKhachHang=khachhang.id) as 'tiendathu'\n"
                + " from khachhang where khachhang.TrangThai = 1 and khachhang.id != 'KH01'\n"
                + " order by khachhang.congno desc ";

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, dateFrom, dateTo);
        long tongTienDaThu = 0;
        long soTienNoConLai = 0;
        try {
            while (rs.next()) {
                tongTienDaThu += rs.getLong("tiendathu");
                soTienNoConLai += rs.getLong("congno");
                model.addRow(new Object[]{
                    rs.getString("khachhang"),
                    rs.getString("sodienthoai"),
                    rs.getString("diachi"),
                    HELPER.helper.SoString(rs.getLong("congno")),
                    HELPER.helper.SoString(rs.getLong("tiendathu"))
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
        txtTongTienDaThu.setText(HELPER.helper.SoString(tongTienDaThu));
        txtSoTienNoConLai.setText(HELPER.helper.SoString(soTienNoConLai));
    }

    public static void loadTableDoanhThuLoiNhuan(JTable table,
            String dateFrom,
            String dateTo,
            JTextField txtDoanhThu,
            JTextField txtLoiNhuan) {
        String sql = "SELECT sanpham.name as 'sanpham', sanpham.giaban as 'giaban', sanpham.GiaNhap as 'gianhap',chitiethoadon.soluong as 'soluong',\n"
                + " (chitiethoadon.soluong*sanpham.GiaBan) as 'doanhthu',\n"
                + " ((chitiethoadon.soluong*sanpham.GiaBan)- (chitiethoadon.soluong*sanpham.GiaNhap) ) as 'loinhuan',\n"
                + " hoadon.ThoiGian as 'thoigian'\n"
                + " from chitiethoadon\n"
                + " join sanpham on chitiethoadon.idsanpham=sanpham.id\n"
                + " join hoadon on chitiethoadon.idhoadon=hoadon.id\n"
                + " where chitiethoadon.trangThai = 1 and date(hoadon.ThoiGian) BETWEEN ? and ? \n"
                + " order by hoadon.ThoiGian";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, dateFrom, dateTo);
        long doanhThu = 0;
        long loiNhuan = 0;
        try {
            while (rs.next()) {
                doanhThu += rs.getLong("doanhthu");
                loiNhuan += rs.getLong("loinhuan");
                model.addRow(new Object[]{
                    rs.getString("sanpham"),
                    HELPER.helper.SoString(rs.getLong("giaban")),
                    HELPER.helper.SoString(rs.getLong("gianhap")),
                    rs.getInt("soluong"),
                    HELPER.helper.SoString(rs.getLong("doanhthu")),
                    HELPER.helper.SoString(rs.getLong("loinhuan")),
                    rs.getString("thoigian")
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
        txtDoanhThu.setText(HELPER.helper.SoString(doanhThu));
        txtLoiNhuan.setText(HELPER.helper.SoString(loiNhuan));
    }

    public static void loadTableTienTraLaiNCC(JTable table,
            String dateFrom,
            String dateTo,
            JTextField txtTongTienNhanLai,
            JTextField txtTongTienTruVaoCongNo) {
        String sql = "SELECT nhacungcap.name as  'nhacungcap' , nhatkytrahangncc.id as 'id', nhanvien.name as 'nhanvien', nhatkytrahangncc.ThoiGian as 'thoigian' , nhatkytrahangncc.hinhthuc as 'hinhthuc', nhatkytrahangncc.TongTien as 'tongtien'  from nhatkytrahangncc \n"
                + " join nhanvien on nhanvien.id = nhatkytrahangncc.IDnhanvien\n"
                + " join nhacungcap on nhacungcap.id = nhatkytrahangncc.IDncc\n"
                + " where date(nhatkytrahangncc.ThoiGian) BETWEEN ? and ? \n"
                + " order by nhatkytrahangncc.thoigian desc";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql, dateFrom, dateTo);
        long tongTienNhanLai = 0;
        long tongTienTruVaoCongNo = 0;
        try {
            while (rs.next()) {
                if (rs.getInt("hinhthuc") == 0) {
                    tongTienNhanLai += rs.getLong("tongtien");
                } else {
                    tongTienTruVaoCongNo += rs.getLong("tongtien");
                }

                model.addRow(new Object[]{
                    rs.getString("nhacungcap"),
                    rs.getString("id"),
                    rs.getString("nhanvien"),
                    rs.getString("thoigian"),
                    rs.getInt("hinhthuc") == 0 ? "Nhận tiền mặt" : "Trừ vào công nợ",
                    HELPER.helper.SoString(rs.getLong("tongtien"))
                });
            }
        } catch (Exception e) {
        }
        table.setModel(model);
        txtTongTienNhanLai.setText(HELPER.helper.SoString(tongTienNhanLai));
        txtTongTienTruVaoCongNo.setText(HELPER.helper.SoString(tongTienTruVaoCongNo));
    }
}
