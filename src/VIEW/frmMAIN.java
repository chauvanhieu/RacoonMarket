package VIEW;

import CLASS.donViTinh;
import CLASS.hoaDonThuNo;
import CLASS.khachHang;
import CLASS.loaiSanPham;
import CLASS.nhaCungCap;
import CLASS.nhanVien;
import CLASS.sanPham;
import HELPER.helper;
import MODEL.MDAccount;
import MODEL.MDBaoCao;
import MODEL.MDCongNo;
import MODEL.MDDonViTinh;
import MODEL.MDHoaDon;
import MODEL.MDKhachHang;
import MODEL.MDLoaiSanPham;
import MODEL.MDNhaCungCap;
import MODEL.MDNhanVien;
import MODEL.MDNhapHang;
import MODEL.MDSanPham;
import MODEL.MDThuChi;
import MODEL.MDTraHang;
import MODEL.MDTrichKho;
import src.CLASS.Account;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.IntConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class frmMAIN extends javax.swing.JFrame {

    public static Account acc;
    public static Component thisFrame;
    private String path = "src/IMAGE/";
    private ArrayList<sanPham> listSanPham = new ArrayList<sanPham>();

    public frmMAIN(Account acount) {

        this.acc = acount;
        thisFrame = this;
        initComponents();
        // set Title cho phần mềm
        this.setTitle("RACOON Market");
        // set logo Favicon
        ImageIcon img = new ImageIcon(getClass().getResource("/ICON/favicon.png"));
        this.setIconImage(img.getImage());

        // hiện nút close tabbed
        this.tabbed.putClientProperty("JTabbedPane.tabClosable", true);

        // hàm tắt tabbed
        showIconCloseTab();

        // set rộng bằng màn hình
        this.setExtendedState(this.MAXIMIZED_BOTH);

        // mở bảng thông kê ( home )
        panelMain.add(new PanelThongKeTongHop(acc));

        // thêm icon và nút clear vào các textField tìm kiếm
        setSearchTextField();

        //setup table sản phẩm
        setModelTableSanPham();

        //Load table các danh mục đối tượng
        loadTableAccount();
        loadTableKhachHang();
        loadTableNhaCungCap();
        loadTableNhanVien();
        loadTableDonViTinh();
        loadTableLoaiSanPham();
        try {
            loadTableSanPham();
        } catch (IOException ex) {
            Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableThuNoKhachHang();
        loadTableDanhSachPhieuTraNoNCC();
        loadTableNhatKyChiTien();
        loadComboboxLoaiSanPham();

        // thiết lập phân quyền theo account
        setModuleForAccount();

        // set table text center
        setTableTextCenter();
        addKeyEsc();

    }

    public void loadTableThuNoKhachHang() {
        DefaultTableModel model = (DefaultTableModel) tableThuNoKhachHang.getModel();
        model.setRowCount(0);
        ArrayList<hoaDonThuNo> data = MDCongNo.dataTableThuNoKhachHang();
        for (hoaDonThuNo item : data) {
            model.addRow(new Object[]{
                item.getThoiGian(),
                item.getNhanVien(),
                item.getKhachHang(),
                helper.SoString(item.getTongTien()),
                item.getGhiChu()
            });

        }
        tableThuNoKhachHang.setModel(model);
    }

    public void loadTableThuNoKhachHang(String keyword) {
//        MDCongNo.dataTableThuNoKhachHang(tableThuNoKhachHang);

        DefaultTableModel model = (DefaultTableModel) tableThuNoKhachHang.getModel();
        model.setRowCount(0);
        ArrayList<hoaDonThuNo> data = MDCongNo.dataTableThuNoKhachHang();
        for (hoaDonThuNo item : data) {
            String rs = item.getNhanVien() + " " + item.getKhachHang();
            if (helper.removeAccent(rs.toLowerCase()).contains(helper.removeAccent(keyword.toLowerCase()))) {

                model.addRow(new Object[]{
                    item.getThoiGian(),
                    item.getNhanVien(),
                    item.getKhachHang(),
                    helper.SoString(item.getTongTien()),
                    item.getGhiChu()
                });
            }
        }
        tableThuNoKhachHang.setModel(model);
    }

    public void traHangNhaCungCap() {
        openTab(new panelTraHangNhaCungCap(acc), "Trả hàng nhà cung cấp :");
    }

    public void addKeyEsc() {
        InputMap inputMap = tabbed.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "VK_ESCAPE");
        tabbed.getActionMap().put("VK_ESCAPE", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                if (tabbed.getTabCount() > 0) {
                    tabbed.remove(tabbed.getSelectedIndex());
                    if (tabbed.getTabCount() == 0) {
                        panelMain.removeAll();
                        panelMain.add(new PanelThongKeTongHop(acc));
                    }
                }
            }
        });
    }

    public void showIconCloseTab() {
        tabbed.putClientProperty("JTabbedPane.tabCloseCallback",
                (IntConsumer) tabIndex -> {
                    this.tabbed.remove(tabIndex);
                    if (tabbed.getTabCount() == 0) {
                        panelMain.removeAll();
                        panelMain.add(new PanelThongKeTongHop(acc));
                    }
                });
    }

    public void loadTableLoaiSanPham() {
        ArrayList<loaiSanPham> data = MDLoaiSanPham.getAll();
        DefaultTableModel model = (DefaultTableModel) tableLoaiSanPham.getModel();
        model.setRowCount(0);
        for (loaiSanPham item : data) {
            model.addRow(new Object[]{
                item.getIdLoaiSanPham(),
                item.getName(),
                item.getGhiChu(),
                item.isTrangThai()
            });
        }
        tableLoaiSanPham.setModel(model);
    }

    public void loadTableDonViTinh() {
        ArrayList<donViTinh> data = MDDonViTinh.getAll();
        DefaultTableModel model = (DefaultTableModel) tableDonViTinh.getModel();
        model.setRowCount(0);
        for (donViTinh item : data) {
            model.addRow(new Object[]{
                item.getIdDonViTinh(),
                item.getName(),
                item.getGhiChu(),
                item.isTrangThai()
            });
        }
        tableDonViTinh.setModel(model);
    }

    public void loadTableNhanVien() {
        ArrayList<nhanVien> data = MDNhanVien.getDataToTable();
        DefaultTableModel model = (DefaultTableModel) tableNhanVien.getModel();
        model.setRowCount(0);
        for (nhanVien item : data) {
            model.addRow(new Object[]{
                item.getIdNhanVien(),
                item.getName(),
                item.isGioiTinh() == true ? "Nam" : "Nữ",
                item.getDiaChi(),
                item.getSoDienthoai(),
                item.getNgaySinh(),
                helper.LongToString(item.getLuong()),
                item.getNgayVaoLam(),
                item.getGhiChu(),
                item.isTrangThai()
            });
        }
        tableNhanVien.setModel(model);
    }

    public void loadTableNhanVien(ArrayList<nhanVien> data) {
        DefaultTableModel model = (DefaultTableModel) tableNhanVien.getModel();
        model.setRowCount(0);
        for (nhanVien item : data) {
            model.addRow(new Object[]{
                item.getIdNhanVien(),
                item.getName(),
                item.isGioiTinh() == true ? "Nam" : "Nữ",
                item.getDiaChi(),
                item.getSoDienthoai(),
                item.getNgaySinh(),
                helper.LongToString(item.getLuong()),
                item.getNgayVaoLam(),
                item.getGhiChu(),
                item.isTrangThai()
            });
        }
        tableNhanVien.setModel(model);
    }

    public void loadTableNhaCungCap() {
        ArrayList<nhaCungCap> data = MDNhaCungCap.getAll();
        DefaultTableModel model = (DefaultTableModel) tableNhaCungCap.getModel();
        model.setRowCount(0);
        for (nhaCungCap item : data) {
            model.addRow(new Object[]{
                item.getIdNhaCungCap(),
                item.getName(),
                item.getDiaChi(),
                item.getSoDienThoai(),
                item.getGhiChu(),
                helper.LongToString(item.getCongNo()),
                item.isTrangThai()
            });
        }
        tableNhaCungCap.setModel(model);
    }

    public void loadTableNhaCungCap(ArrayList<nhaCungCap> data) {
        DefaultTableModel model = (DefaultTableModel) tableNhaCungCap.getModel();
        model.setRowCount(0);
        for (nhaCungCap item : data) {
            model.addRow(new Object[]{
                item.getIdNhaCungCap(),
                item.getName(),
                item.getDiaChi(),
                item.getSoDienThoai(),
                item.getGhiChu(),
                helper.LongToString(item.getCongNo()),
                item.isTrangThai()
            });
        }
        tableNhaCungCap.setModel(model);
    }

    public void loadTableDanhSachHoaDonBanHang() {
        MDHoaDon.getDanhSachHoaDon(tableDanhSachHoaDonBanHang);
    }

    public void loadTableHoaDonNhapHang() {
        MDNhapHang.loadTable(tableHoaDonNhapHang);
    }

    public void loadTableKhachHang() {
        ArrayList<khachHang> data = MDKhachHang.getDataToTable();
        DefaultTableModel model = (DefaultTableModel) tableKhachHang.getModel();
        model.setRowCount(0);
        for (khachHang item : data) {
            model.addRow(new Object[]{
                item.getIdKhachHang(),
                item.getName(),
                item.getDiaChi(),
                item.getSoDienThoai(),
                item.getGhiChu(),
                helper.LongToString(item.getNo()),
                item.isTrangThai()
            });
        }
        tableKhachHang.setModel(model);
    }

    public void loadTableKhachHang(ArrayList<khachHang> data) {
//        ArrayList<khachHang> data = MDKhachHang.getDataToTable();
        DefaultTableModel model = (DefaultTableModel) tableKhachHang.getModel();
        model.setRowCount(0);
        for (khachHang item : data) {
            model.addRow(new Object[]{
                item.getIdKhachHang(),
                item.getName(),
                item.getDiaChi(),
                item.getSoDienThoai(),
                item.getGhiChu(),
                helper.LongToString(item.getNo()),
                item.isTrangThai()
            });
        }
        tableKhachHang.setModel(model);
    }

    public void loadTableSanPham() throws IOException {
        ArrayList<sanPham> data = MDSanPham.getDataToTable();
        listSanPham = data;
        DefaultTableModel model = (DefaultTableModel) tableSanPhamPnlSanPham.getModel();
        model.setRowCount(0);
        for (sanPham item : data) {

            ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));

            model.addRow(new Object[]{
                imageIcon,
                item.getIdSanPham(),
                item.getName(),
                item.getBarcode(),
                item.getIdDonViTinh(),
                item.getIdLoaiSanPham(),
                helper.LongToString(item.getGiaNhap()),
                helper.LongToString(item.getGiaBan()),
                helper.LongToString(item.getGiaSi()),
                item.getSoLuong(),
                item.getSoLuongToiThieu(),
                item.getGhiChu(),
                item.isTrangThai()
            });
        }
        tableSanPhamPnlSanPham.setModel(model);
    }

    public void loadTableAccount() {
        ArrayList<Account> data = MDAccount.getDataToTable();
        DefaultTableModel model = (DefaultTableModel) tableTaiKhoan.getModel();
        model.setRowCount(0);
        for (Account item : data) {
            model.addRow(new Object[]{
                item.getUsername(),
                item.getPassword(),
                item.getIdNhanVien(),
                item.isTrangThai(),
                item.isBanHang(),
                item.isNhapHang(),
                item.isHangHoa(),
                item.isKhachHang(),
                item.isNhaCungCap(),
                item.isPhieuChi(),
                item.isBaoCao()
            });
        }
        tableTaiKhoan.setModel(model);
    }

    public void loadTableAccount(ArrayList<Account> data) {
        DefaultTableModel model = (DefaultTableModel) tableTaiKhoan.getModel();
        model.setRowCount(0);
        for (Account item : data) {
            model.addRow(new Object[]{
                item.getUsername(),
                item.getPassword(),
                item.getIdNhanVien(),
                item.isTrangThai(),
                item.isBanHang(),
                item.isNhapHang(),
                item.isHangHoa(),
                item.isKhachHang(),
                item.isNhaCungCap(),
                item.isPhieuChi(),
                item.isBaoCao()
            });
        }
        tableTaiKhoan.setModel(model);
    }

    public void setTableTextCenter() {
        helper.setTableTextCenterFullColumn(tableCongNoNCC);
        helper.setTableTextCenterFullColumn(tableChiPhiNhapHang);
        helper.setTableTextCenterFullColumn(tableCacKhoanChi);
        helper.setTableTextCenterFullColumn(tableLuongNhanVien);
        helper.setTableTextCenterFullColumn(tableTrichKhoSanPham);
        helper.setTableTextCenterFullColumn(tableCongNoKhachHang);
        helper.setTableTextCenterFullColumn(tableDoanhThuLoiNhuan);
        helper.setTableTextCenterFullColumn(tableTraNoNhaCungCap);
        helper.setTableTextCenterFullColumn(tableTienTraHangNCC);
        // set table text center      
        helper.setTableTextCenter(tableHoaDonNhapHang);
        helper.setTableTextCenter(tableHoaDonTraHang);
        helper.setTableTextCenterFullColumn(tableChiTietTraHang);
        helper.setTableTextCenterFullColumn(tableChiTietNhapHang);
        helper.setTableTextCenterFullColumn(tableHoaDonTrichKho);
        helper.setTableTextCenterFullColumn(tableChiTietTrichKho);
        helper.setTableTextCenterFullColumn(tableDanhSachPhieuChi);
        helper.setTableTextCenterFullColumn(tableChiTietHoaDonBanHang);
        helper.setTableTextCenterFullColumn(tableTraNoNhaCungCap);
        HELPER.helper.setTableTextCenter(tableNhaCungCap);
        HELPER.helper.setTableTextCenter(tableNhanVien);
        HELPER.helper.setTableTextCenter(tableDonViTinh);
        HELPER.helper.setTableTextCenter(tableKhachHang);
        HELPER.helper.setTableTextCenter(tableDanhSachHoaDonBanHang);
        HELPER.helper.setTableTextCenter(tableChiTietHoaDonBanHang);
        HELPER.helper.setTableTextCenter(tableLoaiSanPham);
        HELPER.helper.setTableTextCenterFullColumn(tableThuNoKhachHang);
        // set table text center table tài khoản
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 3; i++) {
            tableTaiKhoan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        helper.setTableTextCenterFullColumn(tableDanhSachHoaDonBanHang);
    }

    public void setModuleForAccount() {
        //bán hàng
        btnDanhSachHoaDonBanHang.setVisible(acc.isBanHang());
        btnTaoHoaDonBanHang.setVisible(acc.isBanHang());
        menuDanhSachHoaDonBanHang.setVisible(acc.isBanHang());
        menuTaoHoaDonBanHang.setVisible(acc.isBanHang());

        //nhập hàng
        btnNhapHang.setVisible(acc.isNhapHang());
        menuNhapHang.setVisible(acc.isNhapHang());
        menuNhatKyNhapHang.setVisible(acc.isNhapHang());
        btnThemSanPham.setVisible(acc.isNhapHang());

        //quản lý các tài khoản
        menuQuanLyTaiKhoan.setVisible(acc.isTaiKhoan());

        // quản lý hàng hóa
        btnNhapHang.setVisible(acc.isNhapHang());
        menuNhapHang.setVisible(acc.isNhapHang());
        menuNhatKyNhapHang.setVisible(acc.isNhapHang());
        btnThemSanPham.setVisible(acc.isNhapHang());

        //quản lý nhân viên
        menuQuanLyNhanVien.setVisible(acc.isNhanVien());

        //quản lý nhà cung cấp
        menuQuanLyNhaCungCap.setVisible(acc.isNhaCungCap());

        //quản lý khách hàng
        menuQuanLyKhachHang.setVisible(acc.isKhachHang());

        //báo cáo
        btnBaoCao.setVisible(acc.isBaoCao());

        //phiếu chi
        menuTaoPhieuChi.setVisible(acc.isPhieuChi());
        menuDanhSachPhieuChi.setVisible(acc.isPhieuChi());
        btnTaoPhieuChi.setVisible(acc.isPhieuChi());

    }

    public void setModelTableSanPham() {

        String[] column = {"Hình ảnh", "Mã", "Sản phẩm", "Mã vạch", "Đơn vị tính", "Nhóm hàng", "Giá nhập", "Giá bán", "Giá sĩ", "Tồn kho", "Số lượng tối thiểu", "Ghi chú", "Còn sử dụng?"};
        Object[][] rows = {};
        DefaultTableModel model = new DefaultTableModel(rows, column) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return ImageIcon.class;

                    case 12:
                        return Boolean.class;
                    default:
                        return Object.class;
                }
            }

            public boolean isCellEditable(int rowIndex,
                    int columnIndex) {
                return false;
            }
        };
        tableSanPhamPnlSanPham.setModel(model);
        tableSanPhamPnlSanPham.setRowHeight(120);
        tableSanPhamPnlSanPham.setRowMargin(7);

        tableSanPhamPnlSanPham.setFont(new Font("Arial", Font.CENTER_BASELINE, 17));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        tableSanPhamPnlSanPham.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(11).setCellRenderer(centerRenderer);

        tableSanPhamPnlSanPham.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(1).setPreferredWidth(70);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(2).setPreferredWidth(200);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(3).setPreferredWidth(150);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(4).setPreferredWidth(60);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(11).setPreferredWidth(110);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(12).setPreferredWidth(90);
        tableSanPhamPnlSanPham.getColumnModel().getColumn(9).setPreferredWidth(60);
    }

    public void setSearchTextField() {
        helper.addIconSearch(txtTimKiemHoaDonTraHang);
        helper.addIconSearch(txtTimKiemHoaDonNhapHang);
        helper.addIconSearch(txtTimKiemHoaDonTrichKho);
        helper.addIconSearch(txtTimKiemHoaDonBanHang);
        helper.addIconSearch(txtTimKiemKhachHangPnlKH);
        helper.addIconSearch(txtTimKiemNhaCungCap);
        helper.addIconSearch(txtTimKiemNhanVien);
        helper.addIconSearch(txtTimKiemSanPhamPnlSanPham);
        helper.addIconSearch(txtTimKiemTaiKhoan);
        helper.addIconSearch(txtTimKiemThuNoKhachHang);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbed = new javax.swing.JTabbedPane();
        panelDanhSachHoaDonBanHang = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiemHoaDonBanHang = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dateFromHoaDon = new com.toedter.calendar.JDateChooser();
        dateToHoaDon = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDanhSachHoaDonBanHang = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableChiTietHoaDonBanHang = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        panelQuanLyNhanVien = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtTimKiemNhanVien = new javax.swing.JTextField();
        btnTimKiemNhanVien = new javax.swing.JButton();
        btnThemNhanVien = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableNhanVien = new javax.swing.JTable();
        panelQuanLyKhachHang = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTimKiemKhachHangPnlKH = new javax.swing.JTextField();
        btnTimKiemKhachHang = new javax.swing.JButton();
        btnThemKhachHang = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableKhachHang = new javax.swing.JTable();
        panelQuanLyNhaCungCap = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtTimKiemNhaCungCap = new javax.swing.JTextField();
        btnThemNhaCungCap = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableNhaCungCap = new javax.swing.JTable();
        panelQuanLyTaiKhoan = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableTaiKhoan = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtTimKiemTaiKhoan = new javax.swing.JTextField();
        btnThemNhaCungCap1 = new javax.swing.JButton();
        panelQuanLyHangHoa = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtTimKiemSanPhamPnlSanPham = new javax.swing.JTextField();
        btnReloadTableSanPham = new javax.swing.JButton();
        btnThemSanPham = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        comboBoxNhomHang = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableSanPhamPnlSanPham = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableDonViTinh = new javax.swing.JTable();
        btnXoaSanPham2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tableLoaiSanPham = new javax.swing.JTable();
        btnXoaSanPham1 = new javax.swing.JButton();
        panelDanhSachPhieuThuNoKhachHang = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableThuNoKhachHang = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        txtTimKiemThuNoKhachHang = new javax.swing.JTextField();
        btnTimKiemNhanVien1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        panelDanhSachPhieuTraNoNCC = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        txtTimKiemTraNoNcc = new javax.swing.JTextField();
        dateToTraNoNcc = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        dateFromTraNoNcc = new com.toedter.calendar.JDateChooser();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableTraNoNhaCungCap = new javax.swing.JTable();
        panelDanhSachPhieuChi = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        txtTimKiemThuNoKhachHang2 = new javax.swing.JTextField();
        btnTimKiemNhanVien3 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        cbFilterThuNo2 = new javax.swing.JComboBox<>();
        jScrollPane12 = new javax.swing.JScrollPane();
        tableDanhSachPhieuChi = new javax.swing.JTable();
        panelNhatKyTrichKho = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTimKiemHoaDonTrichKho = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        dateFromTrichKho = new com.toedter.calendar.JDateChooser();
        dateToTrichKho = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        tableHoaDonTrichKho = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        tableChiTietTrichKho = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        panelDanhSachHoaDonNhapHang = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtTimKiemHoaDonNhapHang = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        dateFromNhapHang = new com.toedter.calendar.JDateChooser();
        dateToNhapHang = new com.toedter.calendar.JDateChooser();
        jLabel22 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        tableHoaDonNhapHang = new javax.swing.JTable();
        jScrollPane16 = new javax.swing.JScrollPane();
        tableChiTietNhapHang = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        panelHoaDonTraHangNCC = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtTimKiemHoaDonTraHang = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        dateFromTraHang = new com.toedter.calendar.JDateChooser();
        dateToTraHang = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jScrollPane17 = new javax.swing.JScrollPane();
        tableHoaDonTraHang = new javax.swing.JTable();
        jScrollPane18 = new javax.swing.JScrollPane();
        tableChiTietTraHang = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        panelBaoCaoTongHop = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        dateFromBaoCao = new com.toedter.calendar.JDateChooser();
        dateToBaoCao = new com.toedter.calendar.JDateChooser();
        jLabel35 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        tabBaoCao = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        tableCongNoNCC = new javax.swing.JTable();
        jPanel34 = new javax.swing.JPanel();
        txtSoTienDaTraNCC = new javax.swing.JTextField();
        txtSoTienNoNCCConLai = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        txtTongTienNhapHang = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtTongTienThanhToanNhapHang = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtTienNoLaiNCC = new javax.swing.JTextField();
        jScrollPane20 = new javax.swing.JScrollPane();
        tableChiPhiNhapHang = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        btnTongTienChiTieu = new javax.swing.JTextField();
        jScrollPane21 = new javax.swing.JScrollPane();
        tableCacKhoanChi = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        txtTongTienLuong = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        tableLuongNhanVien = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        txtTongTienTrichKho = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        tableTrichKhoSanPham = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        txtTongTienNoDaThu = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtSoTienNoConLai = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        tableCongNoKhachHang = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        txtTongDoanhThu = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtTongLoiNhuan = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        tableDoanhThuLoiNhuan = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        txtTongTienNhanLai = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtTongTienTruCongNo = new javax.swing.JTextField();
        jScrollPane26 = new javax.swing.JScrollPane();
        tableTienTraHangNCC = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnTaoHoaDonBanHang = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnDanhSachHoaDonBanHang = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnTaoPhieuThu = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnTaoPhieuChi = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnNhapHang = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton7 = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        btnBaoCao = new javax.swing.JButton();
        panelMain = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        thongtin = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuQuanLyHangHoa = new javax.swing.JMenuItem();
        menuQuanLyNhanVien = new javax.swing.JMenuItem();
        menuQuanLyKhachHang = new javax.swing.JMenuItem();
        menuQuanLyNhaCungCap = new javax.swing.JMenuItem();
        menuQuanLyTaiKhoan = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        menuTaoPhieuChi = new javax.swing.JMenuItem();
        menuDanhSachPhieuChi = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuNhapHang = new javax.swing.JMenuItem();
        menuNhatKyNhapHang = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuTaoHoaDonBanHang = new javax.swing.JMenuItem();
        menuDanhSachHoaDonBanHang = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();

        tabbed.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabbed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabbedKeyPressed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("TÌM HÓA ĐƠN :");

        txtTimKiemHoaDonBanHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiemHoaDonBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemHoaDonBanHangActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("TỪ NGÀY :");

        dateFromHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        dateFromHoaDon.setDate(new Date());
        dateFromHoaDon.setDateFormatString("dd-MM-yyyy");
        dateFromHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateFromHoaDon.setVerifyInputWhenFocusTarget(false);

        dateToHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        dateToHoaDon.setDate(new Date());
        dateToHoaDon.setDateFormatString("dd-MM-yyyy");
        dateToHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateToHoaDon.setVerifyInputWhenFocusTarget(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("ĐẾN NGÀY :");

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/check-hoa-don.png"))); // NOI18N
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dateFromHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateToHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTimKiemHoaDonBanHang))
                .addGap(18, 18, 18)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtTimKiemHoaDonBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(dateFromHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateToHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)))
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("DANH SÁCH HÓA ĐƠN BÁN HÀNG");

        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);

        tableDanhSachHoaDonBanHang.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableDanhSachHoaDonBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Khách hàng", "Nhân viên", "Tổng tiền", "Giảm giá", "Hình thức", "Thời gian"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDanhSachHoaDonBanHang.setRowHeight(40);
        tableDanhSachHoaDonBanHang.setRowMargin(4);
        tableDanhSachHoaDonBanHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableDanhSachHoaDonBanHangFocusLost(evt);
            }
        });
        tableDanhSachHoaDonBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDanhSachHoaDonBanHangMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableDanhSachHoaDonBanHangMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tableDanhSachHoaDonBanHang);
        if (tableDanhSachHoaDonBanHang.getColumnModel().getColumnCount() > 0) {
            tableDanhSachHoaDonBanHang.getColumnModel().getColumn(1).setHeaderValue("Khách hàng");
            tableDanhSachHoaDonBanHang.getColumnModel().getColumn(3).setHeaderValue("Tổng tiền");
            tableDanhSachHoaDonBanHang.getColumnModel().getColumn(4).setHeaderValue("Giảm giá");
        }

        tableChiTietHoaDonBanHang.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableChiTietHoaDonBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Sản phẩm", "Số lượng", "ĐVT", "Giá bán", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableChiTietHoaDonBanHang.setRowHeight(40);
        tableChiTietHoaDonBanHang.setRowMargin(4);
        tableChiTietHoaDonBanHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableChiTietHoaDonBanHangFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(tableChiTietHoaDonBanHang);
        if (tableChiTietHoaDonBanHang.getColumnModel().getColumnCount() > 0) {
            tableChiTietHoaDonBanHang.getColumnModel().getColumn(0).setMinWidth(140);
            tableChiTietHoaDonBanHang.getColumnModel().getColumn(1).setMaxWidth(65);
            tableChiTietHoaDonBanHang.getColumnModel().getColumn(2).setMaxWidth(90);
        }

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("CHI TIẾT HÓA ĐƠN");

        javax.swing.GroupLayout panelDanhSachHoaDonBanHangLayout = new javax.swing.GroupLayout(panelDanhSachHoaDonBanHang);
        panelDanhSachHoaDonBanHang.setLayout(panelDanhSachHoaDonBanHangLayout);
        panelDanhSachHoaDonBanHangLayout.setHorizontalGroup(
            panelDanhSachHoaDonBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelDanhSachHoaDonBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDanhSachHoaDonBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDanhSachHoaDonBanHangLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDanhSachHoaDonBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap())
        );
        panelDanhSachHoaDonBanHangLayout.setVerticalGroup(
            panelDanhSachHoaDonBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDanhSachHoaDonBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelDanhSachHoaDonBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDanhSachHoaDonBanHangLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
                    .addGroup(panelDanhSachHoaDonBanHangLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelDanhSachHoaDonBanHangLayout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(panelDanhSachHoaDonBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDanhSachHoaDonBanHangLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelDanhSachHoaDonBanHangLayout.createSequentialGroup()
                        .addComponent(jSeparator11)
                        .addGap(6, 6, 6))))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setText("Tìm nhân viên :");

        txtTimKiemNhanVien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemNhanVienKeyReleased(evt);
            }
        });

        btnTimKiemNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/reload.png"))); // NOI18N
        btnTimKiemNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemNhanVienActionPerformed(evt);
            }
        });

        btnThemNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemNhanVien.setForeground(new java.awt.Color(0, 153, 255));
        btnThemNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/add-user.png"))); // NOI18N
        btnThemNhanVien.setText("Thêm");
        btnThemNhanVien.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThemNhanVien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThemNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(btnThemNhanVien)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(690, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtTimKiemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemNhanVien)
                    .addComponent(btnThemNhanVien))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Họ tên", "Giới tính", "Địa chỉ", "Số điện thoại", "Ngày sinh", "Lương cơ bản", "Ngày vào làm", "Ghi chú", "Đang làm việc"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableNhanVien.setRowHeight(35);
        tableNhanVien.setRowMargin(3);
        tableNhanVien.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableNhanVienFocusLost(evt);
            }
        });
        tableNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableNhanVienMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tableNhanVien);
        if (tableNhanVien.getColumnModel().getColumnCount() > 0) {
            tableNhanVien.getColumnModel().getColumn(0).setMaxWidth(120);
            tableNhanVien.getColumnModel().getColumn(1).setMinWidth(150);
            tableNhanVien.getColumnModel().getColumn(2).setMaxWidth(80);
            tableNhanVien.getColumnModel().getColumn(3).setMinWidth(150);
            tableNhanVien.getColumnModel().getColumn(4).setMinWidth(120);
            tableNhanVien.getColumnModel().getColumn(4).setMaxWidth(120);
            tableNhanVien.getColumnModel().getColumn(9).setMinWidth(90);
            tableNhanVien.getColumnModel().getColumn(9).setMaxWidth(90);
        }

        javax.swing.GroupLayout panelQuanLyNhanVienLayout = new javax.swing.GroupLayout(panelQuanLyNhanVien);
        panelQuanLyNhanVien.setLayout(panelQuanLyNhanVienLayout);
        panelQuanLyNhanVienLayout.setHorizontalGroup(
            panelQuanLyNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelQuanLyNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        panelQuanLyNhanVienLayout.setVerticalGroup(
            panelQuanLyNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuanLyNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Tìm khách hàng :");

        txtTimKiemKhachHangPnlKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKhachHangPnlKHKeyReleased(evt);
            }
        });

        btnTimKiemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/tim-nhan-vien.png"))); // NOI18N

        btnThemKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemKhachHang.setForeground(new java.awt.Color(0, 153, 255));
        btnThemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/add-user.png"))); // NOI18N
        btnThemKhachHang.setText("Thêm");
        btnThemKhachHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThemKhachHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(btnThemKhachHang)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemKhachHangPnlKH, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(665, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(txtTimKiemKhachHangPnlKH, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemKhachHang)
                    .addComponent(btnThemKhachHang))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Họ tên", "Địa chỉ", "Số điện thoại", "Ghi chú", "Công nợ", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableKhachHang.setRowHeight(35);
        tableKhachHang.setRowMargin(3);
        tableKhachHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableKhachHangFocusLost(evt);
            }
        });
        tableKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableKhachHangMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tableKhachHang);
        if (tableKhachHang.getColumnModel().getColumnCount() > 0) {
            tableKhachHang.getColumnModel().getColumn(0).setMaxWidth(200);
            tableKhachHang.getColumnModel().getColumn(1).setMinWidth(150);
            tableKhachHang.getColumnModel().getColumn(2).setMinWidth(150);
            tableKhachHang.getColumnModel().getColumn(3).setMinWidth(120);
            tableKhachHang.getColumnModel().getColumn(3).setMaxWidth(120);
            tableKhachHang.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        javax.swing.GroupLayout panelQuanLyKhachHangLayout = new javax.swing.GroupLayout(panelQuanLyKhachHang);
        panelQuanLyKhachHang.setLayout(panelQuanLyKhachHangLayout);
        panelQuanLyKhachHangLayout.setHorizontalGroup(
            panelQuanLyKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelQuanLyKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        panelQuanLyKhachHangLayout.setVerticalGroup(
            panelQuanLyKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuanLyKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("Tìm nhà cung cấp :");

        txtTimKiemNhaCungCap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemNhaCungCapKeyReleased(evt);
            }
        });

        btnThemNhaCungCap.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemNhaCungCap.setForeground(new java.awt.Color(0, 153, 255));
        btnThemNhaCungCap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/add-user.png"))); // NOI18N
        btnThemNhaCungCap.setText("Thêm");
        btnThemNhaCungCap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThemNhaCungCap.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThemNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhaCungCapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(btnThemNhaCungCap)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(747, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(txtTimKiemNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemNhaCungCap))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableNhaCungCap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableNhaCungCap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên nhà cung cấp", "Địa chỉ", "Số điện thoại", "Ghi chú", "Công nợ", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableNhaCungCap.setRowHeight(35);
        tableNhaCungCap.setRowMargin(3);
        tableNhaCungCap.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableNhaCungCapFocusLost(evt);
            }
        });
        tableNhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableNhaCungCapMousePressed(evt);
            }
        });
        jScrollPane5.setViewportView(tableNhaCungCap);
        if (tableNhaCungCap.getColumnModel().getColumnCount() > 0) {
            tableNhaCungCap.getColumnModel().getColumn(0).setMaxWidth(200);
            tableNhaCungCap.getColumnModel().getColumn(1).setMinWidth(150);
            tableNhaCungCap.getColumnModel().getColumn(2).setMinWidth(150);
            tableNhaCungCap.getColumnModel().getColumn(3).setMinWidth(120);
            tableNhaCungCap.getColumnModel().getColumn(3).setMaxWidth(120);
            tableNhaCungCap.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        javax.swing.GroupLayout panelQuanLyNhaCungCapLayout = new javax.swing.GroupLayout(panelQuanLyNhaCungCap);
        panelQuanLyNhaCungCap.setLayout(panelQuanLyNhaCungCapLayout);
        panelQuanLyNhaCungCapLayout.setHorizontalGroup(
            panelQuanLyNhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelQuanLyNhaCungCapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        panelQuanLyNhaCungCapLayout.setVerticalGroup(
            panelQuanLyNhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuanLyNhaCungCapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                .addContainerGap())
        );

        tableTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Username", "Password", "Nhân viên", "Còn sử dụng ?", "Bán hàng", "Nhập hàng", "QL hàng hóa", "QL khách hàng", "QL nhà cung cấp", "QL phiếu chi", "Báo cáo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTaiKhoan.setRowHeight(35);
        tableTaiKhoan.setRowMargin(3);
        tableTaiKhoan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableTaiKhoanFocusLost(evt);
            }
        });
        tableTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableTaiKhoanMousePressed(evt);
            }
        });
        jScrollPane6.setViewportView(tableTaiKhoan);
        if (tableTaiKhoan.getColumnModel().getColumnCount() > 0) {
            tableTaiKhoan.getColumnModel().getColumn(0).setMinWidth(150);
            tableTaiKhoan.getColumnModel().getColumn(1).setMinWidth(150);
            tableTaiKhoan.getColumnModel().getColumn(2).setMinWidth(200);
            tableTaiKhoan.getColumnModel().getColumn(3).setMinWidth(150);
        }

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setText("Tìm tài khoản :");

        txtTimKiemTaiKhoan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemTaiKhoanKeyReleased(evt);
            }
        });

        btnThemNhaCungCap1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemNhaCungCap1.setForeground(new java.awt.Color(0, 153, 255));
        btnThemNhaCungCap1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/add-user.png"))); // NOI18N
        btnThemNhaCungCap1.setText("Thêm");
        btnThemNhaCungCap1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThemNhaCungCap1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThemNhaCungCap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhaCungCap1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(btnThemNhaCungCap1)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(791, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(txtTimKiemTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemNhaCungCap1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelQuanLyTaiKhoanLayout = new javax.swing.GroupLayout(panelQuanLyTaiKhoan);
        panelQuanLyTaiKhoan.setLayout(panelQuanLyTaiKhoanLayout);
        panelQuanLyTaiKhoanLayout.setHorizontalGroup(
            panelQuanLyTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelQuanLyTaiKhoanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        panelQuanLyTaiKhoanLayout.setVerticalGroup(
            panelQuanLyTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuanLyTaiKhoanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setText("Tìm sản phẩm :");

        txtTimKiemSanPhamPnlSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemSanPhamPnlSanPhamActionPerformed(evt);
            }
        });
        txtTimKiemSanPhamPnlSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemSanPhamPnlSanPhamKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemSanPhamPnlSanPhamKeyReleased(evt);
            }
        });

        btnReloadTableSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/reload.png"))); // NOI18N
        btnReloadTableSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadTableSanPhamActionPerformed(evt);
            }
        });

        btnThemSanPham.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemSanPham.setForeground(new java.awt.Color(0, 153, 255));
        btnThemSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/new-san-pham.png"))); // NOI18N
        btnThemSanPham.setText("Thêm");
        btnThemSanPham.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThemSanPham.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setText("Nhóm hàng :");

        comboBoxNhomHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxNhomHangItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 119, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(btnThemSanPham)
                .addGap(155, 155, 155)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiemSanPhamPnlSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxNhomHang, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnReloadTableSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(jLabel10)
                                        .addComponent(txtTimKiemSanPhamPnlSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnReloadTableSanPham))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(comboBoxNhomHang, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(btnThemSanPham)))
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        tableSanPhamPnlSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Ảnh", "Mã hàng", "Sản phẩm", "Barcode", "Đơn vị tính", "Giá nhập", "Giá bán", "Tồn kho", "Số lượng tối thiểu", "Nhà cung cấp", "Ghi chú", "Còn sử dụng ?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSanPhamPnlSanPham.setRowHeight(80);
        tableSanPhamPnlSanPham.setRowMargin(3);
        tableSanPhamPnlSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableSanPhamPnlSanPhamMousePressed(evt);
            }
        });
        jScrollPane8.setViewportView(tableSanPhamPnlSanPham);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1274, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel8);

        tableDonViTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableDonViTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã", "Đơn vị tính", "Ghi chú", "Còn sử dụng?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDonViTinh.setRowHeight(30);
        tableDonViTinh.setRowMargin(4);
        tableDonViTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableDonViTinhMousePressed(evt);
            }
        });
        jScrollPane7.setViewportView(tableDonViTinh);
        if (tableDonViTinh.getColumnModel().getColumnCount() > 0) {
            tableDonViTinh.getColumnModel().getColumn(0).setMinWidth(100);
            tableDonViTinh.getColumnModel().getColumn(0).setMaxWidth(100);
            tableDonViTinh.getColumnModel().getColumn(3).setMinWidth(120);
            tableDonViTinh.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        btnXoaSanPham2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaSanPham2.setForeground(new java.awt.Color(0, 153, 255));
        btnXoaSanPham2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/don-vi-tinh.png"))); // NOI18N
        btnXoaSanPham2.setText("Thêm ĐVT");
        btnXoaSanPham2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnXoaSanPham2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoaSanPham2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPham2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaSanPham2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnXoaSanPham2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Đơn vị tính", jPanel9);

        tableLoaiSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableLoaiSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã", "Loại sản phẩm", "Ghi chú", "Còn sử dụng?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableLoaiSanPham.setRowHeight(30);
        tableLoaiSanPham.setRowMargin(4);
        tableLoaiSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableLoaiSanPhamMousePressed(evt);
            }
        });
        jScrollPane9.setViewportView(tableLoaiSanPham);
        if (tableLoaiSanPham.getColumnModel().getColumnCount() > 0) {
            tableLoaiSanPham.getColumnModel().getColumn(0).setMinWidth(100);
            tableLoaiSanPham.getColumnModel().getColumn(0).setMaxWidth(100);
            tableLoaiSanPham.getColumnModel().getColumn(3).setMinWidth(120);
            tableLoaiSanPham.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        btnXoaSanPham1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaSanPham1.setForeground(new java.awt.Color(0, 153, 255));
        btnXoaSanPham1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/nhom-hang.png"))); // NOI18N
        btnXoaSanPham1.setText("Thêm nhóm hàng");
        btnXoaSanPham1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnXoaSanPham1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoaSanPham1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPham1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnXoaSanPham1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Nhóm hàng", jPanel10);

        javax.swing.GroupLayout panelQuanLyHangHoaLayout = new javax.swing.GroupLayout(panelQuanLyHangHoa);
        panelQuanLyHangHoa.setLayout(panelQuanLyHangHoaLayout);
        panelQuanLyHangHoaLayout.setHorizontalGroup(
            panelQuanLyHangHoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelQuanLyHangHoaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        panelQuanLyHangHoaLayout.setVerticalGroup(
            panelQuanLyHangHoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuanLyHangHoaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        tableThuNoKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableThuNoKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Thời gian", "Nhân viên thu tiền", "Khách hàng", "Số tiền thu", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableThuNoKhachHang.setRowHeight(35);
        tableThuNoKhachHang.setRowMargin(3);
        tableThuNoKhachHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableThuNoKhachHangFocusLost(evt);
            }
        });
        tableThuNoKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableThuNoKhachHangMousePressed(evt);
            }
        });
        jScrollPane10.setViewportView(tableThuNoKhachHang);

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTimKiemThuNoKhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemThuNoKhachHangKeyReleased(evt);
            }
        });

        btnTimKiemNhanVien1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/reload.png"))); // NOI18N
        btnTimKiemNhanVien1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemNhanVien1ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel13.setText("Tìm Kiếm :");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemThuNoKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTimKiemNhanVien1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel13)
                    .addComponent(btnTimKiemNhanVien1)
                    .addComponent(txtTimKiemThuNoKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelDanhSachPhieuThuNoKhachHangLayout = new javax.swing.GroupLayout(panelDanhSachPhieuThuNoKhachHang);
        panelDanhSachPhieuThuNoKhachHang.setLayout(panelDanhSachPhieuThuNoKhachHangLayout);
        panelDanhSachPhieuThuNoKhachHangLayout.setHorizontalGroup(
            panelDanhSachPhieuThuNoKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelDanhSachPhieuThuNoKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelDanhSachPhieuThuNoKhachHangLayout.setVerticalGroup(
            panelDanhSachPhieuThuNoKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDanhSachPhieuThuNoKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/check-hoa-don.png"))); // NOI18N
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setText("TÌM HÓA ĐƠN :");

        txtTimKiemTraNoNcc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiemTraNoNcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemTraNoNccActionPerformed(evt);
            }
        });

        dateToTraNoNcc.setForeground(new java.awt.Color(255, 255, 255));
        dateToTraNoNcc.setDate(new Date());
        dateToTraNoNcc.setDateFormatString("dd-MM-yyyy");
        dateToTraNoNcc.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateToTraNoNcc.setVerifyInputWhenFocusTarget(false);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setText("ĐẾN NGÀY :");

        dateFromTraNoNcc.setForeground(new java.awt.Color(255, 255, 255));
        dateFromTraNoNcc.setDate(new Date());
        dateFromTraNoNcc.setDateFormatString("dd-MM-yyyy");
        dateFromTraNoNcc.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateFromTraNoNcc.setVerifyInputWhenFocusTarget(false);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setText("TỪ NGÀY :");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(dateFromTraNoNcc, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateToTraNoNcc, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTimKiemTraNoNcc))
                .addGap(18, 18, 18)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtTimKiemTraNoNcc, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(dateFromTraNoNcc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateToTraNoNcc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)))
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tableTraNoNhaCungCap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableTraNoNhaCungCap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nhà Cung Cấp", "Nhân viên thu tiền", "Thời gian", "Số tiền trả", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTraNoNhaCungCap.setRowHeight(35);
        tableTraNoNhaCungCap.setRowMargin(3);
        tableTraNoNhaCungCap.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableTraNoNhaCungCapFocusLost(evt);
            }
        });
        tableTraNoNhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableTraNoNhaCungCapMousePressed(evt);
            }
        });
        jScrollPane11.setViewportView(tableTraNoNhaCungCap);

        javax.swing.GroupLayout panelDanhSachPhieuTraNoNCCLayout = new javax.swing.GroupLayout(panelDanhSachPhieuTraNoNCC);
        panelDanhSachPhieuTraNoNCC.setLayout(panelDanhSachPhieuTraNoNCCLayout);
        panelDanhSachPhieuTraNoNCCLayout.setHorizontalGroup(
            panelDanhSachPhieuTraNoNCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelDanhSachPhieuTraNoNCCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11)
                .addContainerGap())
        );
        panelDanhSachPhieuTraNoNCCLayout.setVerticalGroup(
            panelDanhSachPhieuTraNoNCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDanhSachPhieuTraNoNCCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTimKiemThuNoKhachHang2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemThuNoKhachHang2KeyReleased(evt);
            }
        });

        btnTimKiemNhanVien3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/reload.png"))); // NOI18N
        btnTimKiemNhanVien3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemNhanVien3ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setText("Tìm theo :");

        cbFilterThuNo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Nhân viên", "Khách hàng" }));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbFilterThuNo2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiemThuNoKhachHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 305, Short.MAX_VALUE)
                .addComponent(btnTimKiemNhanVien3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(btnTimKiemNhanVien3)
                    .addComponent(txtTimKiemThuNoKhachHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterThuNo2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        tableDanhSachPhieuChi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableDanhSachPhieuChi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Thời gian", "Nhân viên chi tiền", "Số tiền chi", "Lý Do"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDanhSachPhieuChi.setRowHeight(35);
        tableDanhSachPhieuChi.setRowMargin(3);
        tableDanhSachPhieuChi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableDanhSachPhieuChiFocusLost(evt);
            }
        });
        tableDanhSachPhieuChi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableDanhSachPhieuChiMousePressed(evt);
            }
        });
        jScrollPane12.setViewportView(tableDanhSachPhieuChi);

        javax.swing.GroupLayout panelDanhSachPhieuChiLayout = new javax.swing.GroupLayout(panelDanhSachPhieuChi);
        panelDanhSachPhieuChi.setLayout(panelDanhSachPhieuChiLayout);
        panelDanhSachPhieuChiLayout.setHorizontalGroup(
            panelDanhSachPhieuChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelDanhSachPhieuChiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12)
                .addContainerGap())
        );
        panelDanhSachPhieuChiLayout.setVerticalGroup(
            panelDanhSachPhieuChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDanhSachPhieuChiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("TÌM HÓA ĐƠN :");

        txtTimKiemHoaDonTrichKho.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiemHoaDonTrichKho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemHoaDonTrichKhoActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("TỪ NGÀY :");

        dateFromTrichKho.setForeground(new java.awt.Color(255, 255, 255));
        dateFromTrichKho.setDate(new Date());
        dateFromTrichKho.setDateFormatString("dd-MM-yyyy");
        dateFromTrichKho.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateFromTrichKho.setVerifyInputWhenFocusTarget(false);

        dateToTrichKho.setForeground(new java.awt.Color(255, 255, 255));
        dateToTrichKho.setDate(new Date());
        dateToTrichKho.setDateFormatString("dd-MM-yyyy");
        dateToTrichKho.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateToTrichKho.setVerifyInputWhenFocusTarget(false);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("ĐẾN NGÀY :");

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/check-hoa-don.png"))); // NOI18N
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(dateFromTrichKho, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateToTrichKho, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTimKiemHoaDonTrichKho))
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtTimKiemHoaDonTrichKho, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(dateFromTrichKho, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateToTrichKho, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)))
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tableHoaDonTrichKho.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableHoaDonTrichKho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã", "Nhân viên", "Thời gian", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableHoaDonTrichKho.setRowHeight(40);
        tableHoaDonTrichKho.setRowMargin(4);
        tableHoaDonTrichKho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableHoaDonTrichKhoFocusLost(evt);
            }
        });
        tableHoaDonTrichKho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHoaDonTrichKhoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableHoaDonTrichKhoMousePressed(evt);
            }
        });
        jScrollPane13.setViewportView(tableHoaDonTrichKho);

        tableChiTietTrichKho.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableChiTietTrichKho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Sản phẩm", "Số lượng", "ĐVT", "Giá nhập", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableChiTietTrichKho.setRowHeight(40);
        tableChiTietTrichKho.setRowMargin(4);
        tableChiTietTrichKho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableChiTietTrichKhoFocusLost(evt);
            }
        });
        jScrollPane14.setViewportView(tableChiTietTrichKho);
        if (tableChiTietTrichKho.getColumnModel().getColumnCount() > 0) {
            tableChiTietTrichKho.getColumnModel().getColumn(0).setMinWidth(140);
            tableChiTietTrichKho.getColumnModel().getColumn(1).setMaxWidth(65);
            tableChiTietTrichKho.getColumnModel().getColumn(2).setMaxWidth(90);
        }

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setText("DANH SÁCH PHIẾU TRÍCH KHO");

        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setText("CHI TIẾT TRÍCH KHO");

        javax.swing.GroupLayout panelNhatKyTrichKhoLayout = new javax.swing.GroupLayout(panelNhatKyTrichKho);
        panelNhatKyTrichKho.setLayout(panelNhatKyTrichKhoLayout);
        panelNhatKyTrichKhoLayout.setHorizontalGroup(
            panelNhatKyTrichKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelNhatKyTrichKhoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNhatKyTrichKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelNhatKyTrichKhoLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelNhatKyTrichKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap())
        );
        panelNhatKyTrichKhoLayout.setVerticalGroup(
            panelNhatKyTrichKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNhatKyTrichKhoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelNhatKyTrichKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelNhatKyTrichKhoLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                    .addGroup(panelNhatKyTrichKhoLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelNhatKyTrichKhoLayout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(panelNhatKyTrichKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelNhatKyTrichKhoLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelNhatKyTrichKhoLayout.createSequentialGroup()
                        .addComponent(jSeparator13)
                        .addGap(6, 6, 6))))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("TÌM HÓA ĐƠN :");

        txtTimKiemHoaDonNhapHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiemHoaDonNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemHoaDonNhapHangActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("TỪ NGÀY :");

        dateFromNhapHang.setForeground(new java.awt.Color(255, 255, 255));
        dateFromNhapHang.setDate(new Date());
        dateFromNhapHang.setDateFormatString("dd-MM-yyyy");
        dateFromNhapHang.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateFromNhapHang.setVerifyInputWhenFocusTarget(false);

        dateToNhapHang.setForeground(new java.awt.Color(255, 255, 255));
        dateToNhapHang.setDate(new Date());
        dateToNhapHang.setDateFormatString("dd-MM-yyyy");
        dateToNhapHang.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateToNhapHang.setVerifyInputWhenFocusTarget(false);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("ĐẾN NGÀY :");

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/check-hoa-don.png"))); // NOI18N
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(dateFromNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateToNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTimKiemHoaDonNhapHang))
                .addGap(18, 18, 18)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtTimKiemHoaDonNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(dateFromNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateToNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21)))
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tableHoaDonNhapHang.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableHoaDonNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Thời gian", "Nhân viên", "Nhà cung cấp", "Tổng tiền", "Số tiền đã trả", "Ghi chú", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableHoaDonNhapHang.setRowHeight(40);
        tableHoaDonNhapHang.setRowMargin(4);
        tableHoaDonNhapHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableHoaDonNhapHangFocusLost(evt);
            }
        });
        tableHoaDonNhapHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHoaDonNhapHangMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableHoaDonNhapHangMousePressed(evt);
            }
        });
        jScrollPane15.setViewportView(tableHoaDonNhapHang);
        if (tableHoaDonNhapHang.getColumnModel().getColumnCount() > 0) {
            tableHoaDonNhapHang.getColumnModel().getColumn(5).setHeaderValue("Số tiền đã trả");
            tableHoaDonNhapHang.getColumnModel().getColumn(7).setMaxWidth(60);
        }

        tableChiTietNhapHang.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableChiTietNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Sản phẩm", "Số lượng", "ĐVT", "Giá nhập", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableChiTietNhapHang.setRowHeight(40);
        tableChiTietNhapHang.setRowMargin(4);
        tableChiTietNhapHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableChiTietNhapHangFocusLost(evt);
            }
        });
        jScrollPane16.setViewportView(tableChiTietNhapHang);
        if (tableChiTietNhapHang.getColumnModel().getColumnCount() > 0) {
            tableChiTietNhapHang.getColumnModel().getColumn(0).setMinWidth(140);
            tableChiTietNhapHang.getColumnModel().getColumn(1).setMaxWidth(65);
            tableChiTietNhapHang.getColumnModel().getColumn(2).setMaxWidth(90);
        }

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel23.setText("DANH SÁCH HÓA ĐƠN NHẬP HÀNG");

        jSeparator14.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setText("CHI TIẾT NHẬP HÀNG");

        javax.swing.GroupLayout panelDanhSachHoaDonNhapHangLayout = new javax.swing.GroupLayout(panelDanhSachHoaDonNhapHang);
        panelDanhSachHoaDonNhapHang.setLayout(panelDanhSachHoaDonNhapHangLayout);
        panelDanhSachHoaDonNhapHangLayout.setHorizontalGroup(
            panelDanhSachHoaDonNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelDanhSachHoaDonNhapHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDanhSachHoaDonNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDanhSachHoaDonNhapHangLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDanhSachHoaDonNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap())
        );
        panelDanhSachHoaDonNhapHangLayout.setVerticalGroup(
            panelDanhSachHoaDonNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDanhSachHoaDonNhapHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelDanhSachHoaDonNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDanhSachHoaDonNhapHangLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
                    .addGroup(panelDanhSachHoaDonNhapHangLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelDanhSachHoaDonNhapHangLayout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(panelDanhSachHoaDonNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDanhSachHoaDonNhapHangLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelDanhSachHoaDonNhapHangLayout.createSequentialGroup()
                        .addComponent(jSeparator14)
                        .addGap(6, 6, 6))))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("TÌM HÓA ĐƠN :");

        txtTimKiemHoaDonTraHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiemHoaDonTraHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemHoaDonTraHangActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("TỪ NGÀY :");

        dateFromTraHang.setForeground(new java.awt.Color(255, 255, 255));
        dateFromTraHang.setDate(new Date());
        dateFromTraHang.setDateFormatString("dd-MM-yyyy");
        dateFromTraHang.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateFromTraHang.setVerifyInputWhenFocusTarget(false);

        dateToTraHang.setForeground(new java.awt.Color(255, 255, 255));
        dateToTraHang.setDate(new Date());
        dateToTraHang.setDateFormatString("dd-MM-yyyy");
        dateToTraHang.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateToTraHang.setVerifyInputWhenFocusTarget(false);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setText("ĐẾN NGÀY :");

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/check-hoa-don.png"))); // NOI18N
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(dateFromTraHang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateToTraHang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTimKiemHoaDonTraHang))
                .addGap(18, 18, 18)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtTimKiemHoaDonTraHang, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(dateFromTraHang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateToTraHang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26)))
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tableHoaDonTraHang.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableHoaDonTraHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Thời gian", "Nhân viên", "Nhà cung cấp", "Tổng tiền", "Hình thức", "Ghi chú", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableHoaDonTraHang.setRowHeight(40);
        tableHoaDonTraHang.setRowMargin(4);
        tableHoaDonTraHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableHoaDonTraHangFocusLost(evt);
            }
        });
        tableHoaDonTraHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHoaDonTraHangMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableHoaDonTraHangMousePressed(evt);
            }
        });
        jScrollPane17.setViewportView(tableHoaDonTraHang);
        if (tableHoaDonTraHang.getColumnModel().getColumnCount() > 0) {
            tableHoaDonTraHang.getColumnModel().getColumn(7).setMaxWidth(60);
        }

        tableChiTietTraHang.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableChiTietTraHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Sản phẩm", "Số lượng", "ĐVT", "Giá nhập", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableChiTietTraHang.setRowHeight(40);
        tableChiTietTraHang.setRowMargin(4);
        tableChiTietTraHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableChiTietTraHangFocusLost(evt);
            }
        });
        jScrollPane18.setViewportView(tableChiTietTraHang);
        if (tableChiTietTraHang.getColumnModel().getColumnCount() > 0) {
            tableChiTietTraHang.getColumnModel().getColumn(0).setMinWidth(140);
            tableChiTietTraHang.getColumnModel().getColumn(1).setMaxWidth(65);
            tableChiTietTraHang.getColumnModel().getColumn(2).setMaxWidth(90);
        }

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setText("DANH SÁCH HÓA ĐƠN TRẢ HÀNG");

        jSeparator15.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setText("CHI TIẾT TRẢ HÀNG");

        javax.swing.GroupLayout panelHoaDonTraHangNCCLayout = new javax.swing.GroupLayout(panelHoaDonTraHangNCC);
        panelHoaDonTraHangNCC.setLayout(panelHoaDonTraHangNCCLayout);
        panelHoaDonTraHangNCCLayout.setHorizontalGroup(
            panelHoaDonTraHangNCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelHoaDonTraHangNCCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHoaDonTraHangNCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHoaDonTraHangNCCLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHoaDonTraHangNCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addContainerGap())
        );
        panelHoaDonTraHangNCCLayout.setVerticalGroup(
            panelHoaDonTraHangNCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHoaDonTraHangNCCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelHoaDonTraHangNCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHoaDonTraHangNCCLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
                    .addGroup(panelHoaDonTraHangNCCLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelHoaDonTraHangNCCLayout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(panelHoaDonTraHangNCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHoaDonTraHangNCCLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelHoaDonTraHangNCCLayout.createSequentialGroup()
                        .addComponent(jSeparator15)
                        .addGap(6, 6, 6))))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setText("TỪ NGÀY :");

        dateFromBaoCao.setBackground(new java.awt.Color(102, 102, 102));
        dateFromBaoCao.setForeground(new java.awt.Color(0, 153, 255));
        dateFromBaoCao.setDate(new Date());
        dateFromBaoCao.setDateFormatString("dd-MM-yyyy");
        dateFromBaoCao.setFocusable(false);
        dateFromBaoCao.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        dateFromBaoCao.setRequestFocusEnabled(false);
        dateFromBaoCao.setVerifyInputWhenFocusTarget(false);

        dateToBaoCao.setBackground(new java.awt.Color(102, 102, 102));
        dateToBaoCao.setForeground(new java.awt.Color(0, 153, 255));
        dateToBaoCao.setDate(new Date());
        dateToBaoCao.setDateFormatString("dd-MM-yyyy");
        dateToBaoCao.setFocusable(false);
        dateToBaoCao.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        dateToBaoCao.setRequestFocusEnabled(false);
        dateToBaoCao.setVerifyInputWhenFocusTarget(false);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setText("ĐẾN NGÀY :");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 153, 255));
        jButton1.setText("Duyệt báo cáo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateFromBaoCao, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(dateToBaoCao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel34)
                            .addComponent(dateFromBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateToBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabBaoCao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabBaoCao.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabBaoCao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabBaoCaoFocusGained(evt);
            }
        });

        tableCongNoNCC.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableCongNoNCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nhà cung cấp", "Số điện thoại", "Địa chỉ", "Tiền còn nợ", "Tiền đã trả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCongNoNCC.setRowMargin(4);
        jScrollPane19.setViewportView(tableCongNoNCC);

        jPanel34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtSoTienDaTraNCC.setEditable(false);
        txtSoTienDaTraNCC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSoTienDaTraNCC.setText("0");
        txtSoTienDaTraNCC.setFocusable(false);

        txtSoTienNoNCCConLai.setEditable(false);
        txtSoTienNoNCCConLai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSoTienNoNCCConLai.setText("0");
        txtSoTienNoNCCConLai.setFocusable(false);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel14.setText("Tổng nợ còn lại :");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel33.setText("Tổng số tiền đã chi trả :");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel33)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSoTienNoNCCConLai, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(txtSoTienDaTraNCC))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoTienDaTraNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(14, 14, 14)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoTienNoNCCConLai, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabBaoCao.addTab("Công nợ NCC", jPanel18);

        jPanel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel36.setText("Tổng chi phí nhập hàng :");

        txtTongTienNhapHang.setEditable(false);
        txtTongTienNhapHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongTienNhapHang.setText("0");
        txtTongTienNhapHang.setFocusable(false);

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel37.setText("Tổng tiền đã thanh toán :");

        txtTongTienThanhToanNhapHang.setEditable(false);
        txtTongTienThanhToanNhapHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongTienThanhToanNhapHang.setText("0");
        txtTongTienThanhToanNhapHang.setFocusable(false);

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel38.setText("Số tiền nợ lại NCC :");

        txtTienNoLaiNCC.setEditable(false);
        txtTienNoLaiNCC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTienNoLaiNCC.setText("0");
        txtTienNoLaiNCC.setFocusable(false);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTongTienThanhToanNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(txtTongTienNhapHang))
                .addGap(28, 28, 28)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienNoLaiNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(259, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtTongTienNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtTienNoLaiNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel27Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel38))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel37)
                    .addComponent(txtTongTienThanhToanNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        tableChiPhiNhapHang.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableChiPhiNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Thời gian", "Mã phiếu nhập hàng", "Nhân viên nhập hàng", "Nhà cung cấp", "Tổng tiền", "Số tiền thanh toán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableChiPhiNhapHang.setRowMargin(4);
        jScrollPane20.setViewportView(tableChiPhiNhapHang);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane20))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabBaoCao.addTab("Chi phí nhập hàng", jPanel19);

        jPanel28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel39.setText("Tổng số tiền đã chi tiêu :");

        btnTongTienChiTieu.setEditable(false);
        btnTongTienChiTieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTongTienChiTieu.setText("0");
        btnTongTienChiTieu.setFocusable(false);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTongTienChiTieu, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(664, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(btnTongTienChiTieu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        tableCacKhoanChi.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableCacKhoanChi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Thời gian", "Nhân viên tạo phiếu", "Số tiền đã chi", "Lý do"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCacKhoanChi.setRowMargin(4);
        jScrollPane21.setViewportView(tableCacKhoanChi);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane21)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabBaoCao.addTab("Các khoản chi", jPanel20);

        jPanel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTongTienLuong.setEditable(false);
        txtTongTienLuong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongTienLuong.setText("0");
        txtTongTienLuong.setFocusable(false);

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel40.setText("Tổng tiền lương trong 1 tháng :");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongTienLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(662, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtTongTienLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        tableLuongNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableLuongNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên nhân viên", "Giới tính", "Số điện thoại", "Địa chỉ", "Ngày vào làm", "Lương cơ bản", "Doanh thu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableLuongNhanVien.setRowMargin(4);
        jScrollPane22.setViewportView(tableLuongNhanVien);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane22)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabBaoCao.addTab("Lương nhân viên", jPanel21);

        jPanel30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTongTienTrichKho.setEditable(false);
        txtTongTienTrichKho.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongTienTrichKho.setText("0");
        txtTongTienTrichKho.setFocusable(false);

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel41.setText("Tổng tiền :");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongTienTrichKho, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(663, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtTongTienTrichKho, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        tableTrichKhoSanPham.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableTrichKhoSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Thời gian", "Mã phiếu trích kho", "Nhân viên trích kho", "Tên sản phẩm", "Giá nhập", "Số lượng trích kho", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTrichKhoSanPham.setRowMargin(4);
        jScrollPane23.setViewportView(tableTrichKhoSanPham);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane23)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabBaoCao.addTab("Tiền trích kho sản phẩm", jPanel22);

        jPanel31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTongTienNoDaThu.setEditable(false);
        txtTongTienNoDaThu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongTienNoDaThu.setText("0");
        txtTongTienNoDaThu.setFocusable(false);

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel42.setText("Tổng tiền đã thu :");

        txtSoTienNoConLai.setEditable(false);
        txtSoTienNoConLai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSoTienNoConLai.setText("0");
        txtSoTienNoConLai.setFocusable(false);

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel43.setText("Số tiền nợ còn lại :");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSoTienNoConLai, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(txtTongTienNoDaThu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtTongTienNoDaThu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtSoTienNoConLai, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tableCongNoKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableCongNoKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Tiền còn nợ", "Tiền đã thu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCongNoKhachHang.setRowMargin(4);
        jScrollPane24.setViewportView(tableCongNoKhachHang);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane24, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane24, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabBaoCao.addTab("Công nợ khách hàng", jPanel23);

        jPanel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTongDoanhThu.setEditable(false);
        txtTongDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongDoanhThu.setText("0");
        txtTongDoanhThu.setFocusable(false);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel44.setText("Tổng doanh thu :");

        txtTongLoiNhuan.setEditable(false);
        txtTongLoiNhuan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongLoiNhuan.setText("0");
        txtTongLoiNhuan.setFocusable(false);

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel45.setText("Tổng lợi nhuận :");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTongDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(txtTongLoiNhuan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongLoiNhuan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tableDoanhThuLoiNhuan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableDoanhThuLoiNhuan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên sản phẩm", "Giá bán", "Giá nhập", "Số lượng bán", "Doanh thu", "Lợi nhuận", "Thời gian"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDoanhThuLoiNhuan.setRowMargin(4);
        jScrollPane25.setViewportView(tableDoanhThuLoiNhuan);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabBaoCao.addTab("Doanh thu lợi nhuận", jPanel24);

        jPanel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jPanel33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel46.setText("Tổng tiền nhận lại :");

        txtTongTienNhanLai.setEditable(false);
        txtTongTienNhanLai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongTienNhanLai.setText("0");
        txtTongTienNhanLai.setFocusable(false);

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel47.setText("Tổng tiền trừ công nợ :");

        txtTongTienTruCongNo.setEditable(false);
        txtTongTienTruCongNo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongTienTruCongNo.setText("0");
        txtTongTienTruCongNo.setFocusable(false);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongTienNhanLai, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongTienTruCongNo, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(669, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtTongTienNhanLai, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongTienTruCongNo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tableTienTraHangNCC.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tableTienTraHangNCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nhà cung cấp", "Mã phiếu trả hàng", "Nhân viên trả hàng", "Thời gian", "Hình thức thanh toán", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTienTraHangNCC.setRowMargin(4);
        jScrollPane26.setViewportView(tableTienTraHangNCC);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane26)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabBaoCao.addTab("Tiền trả lại hàng NCC", jPanel25);

        javax.swing.GroupLayout panelBaoCaoTongHopLayout = new javax.swing.GroupLayout(panelBaoCaoTongHop);
        panelBaoCaoTongHop.setLayout(panelBaoCaoTongHopLayout);
        panelBaoCaoTongHopLayout.setHorizontalGroup(
            panelBaoCaoTongHopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelBaoCaoTongHopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabBaoCao)
                .addContainerGap())
        );
        panelBaoCaoTongHopLayout.setVerticalGroup(
            panelBaoCaoTongHopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBaoCaoTongHopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabBaoCao)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(100, 25));
        jToolBar1.setMinimumSize(new java.awt.Dimension(100, 25));

        btnTaoHoaDonBanHang.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnTaoHoaDonBanHang.setForeground(new java.awt.Color(51, 153, 255));
        btnTaoHoaDonBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/tao-hoa-don.png"))); // NOI18N
        btnTaoHoaDonBanHang.setText("Tạo đơn hàng");
        btnTaoHoaDonBanHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTaoHoaDonBanHang.setFocusable(false);
        btnTaoHoaDonBanHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTaoHoaDonBanHang.setPreferredSize(new java.awt.Dimension(120, 120));
        btnTaoHoaDonBanHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTaoHoaDonBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonBanHangActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTaoHoaDonBanHang);
        jToolBar1.add(jSeparator1);

        btnDanhSachHoaDonBanHang.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnDanhSachHoaDonBanHang.setForeground(new java.awt.Color(51, 153, 255));
        btnDanhSachHoaDonBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/cac-hoa-don.png"))); // NOI18N
        btnDanhSachHoaDonBanHang.setText("Sổ hóa đơn");
        btnDanhSachHoaDonBanHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDanhSachHoaDonBanHang.setFocusable(false);
        btnDanhSachHoaDonBanHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDanhSachHoaDonBanHang.setPreferredSize(new java.awt.Dimension(120, 120));
        btnDanhSachHoaDonBanHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDanhSachHoaDonBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDanhSachHoaDonBanHangActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDanhSachHoaDonBanHang);
        jToolBar1.add(jSeparator2);

        btnTaoPhieuThu.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnTaoPhieuThu.setForeground(new java.awt.Color(51, 153, 255));
        btnTaoPhieuThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/tao-phieu-thu.png"))); // NOI18N
        btnTaoPhieuThu.setText("Tạo phiếu thu");
        btnTaoPhieuThu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTaoPhieuThu.setFocusable(false);
        btnTaoPhieuThu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTaoPhieuThu.setPreferredSize(new java.awt.Dimension(120, 120));
        btnTaoPhieuThu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTaoPhieuThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoPhieuThuActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTaoPhieuThu);
        jToolBar1.add(jSeparator3);

        btnTaoPhieuChi.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnTaoPhieuChi.setForeground(new java.awt.Color(51, 153, 255));
        btnTaoPhieuChi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/tao-phieu-chi.png"))); // NOI18N
        btnTaoPhieuChi.setText("Tạo phiếu chi");
        btnTaoPhieuChi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTaoPhieuChi.setFocusable(false);
        btnTaoPhieuChi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTaoPhieuChi.setPreferredSize(new java.awt.Dimension(120, 120));
        btnTaoPhieuChi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTaoPhieuChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoPhieuChiActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTaoPhieuChi);
        jToolBar1.add(jSeparator4);

        btnNhapHang.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnNhapHang.setForeground(new java.awt.Color(51, 153, 255));
        btnNhapHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/nhap-hang.png"))); // NOI18N
        btnNhapHang.setText("Nhập hàng");
        btnNhapHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNhapHang.setFocusable(false);
        btnNhapHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNhapHang.setMinimumSize(new java.awt.Dimension(140, 103));
        btnNhapHang.setPreferredSize(new java.awt.Dimension(120, 120));
        btnNhapHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapHangActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNhapHang);
        jToolBar1.add(jSeparator6);

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton7.setForeground(new java.awt.Color(51, 153, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/tra-hang-ncc.png"))); // NOI18N
        jButton7.setText("Trả hàng NCC");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setPreferredSize(new java.awt.Dimension(120, 120));
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);
        jToolBar1.add(jSeparator8);

        btnBaoCao.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnBaoCao.setForeground(new java.awt.Color(51, 153, 255));
        btnBaoCao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/bao-cao.png"))); // NOI18N
        btnBaoCao.setText("Báo cáo");
        btnBaoCao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBaoCao.setFocusable(false);
        btnBaoCao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBaoCao.setMaximumSize(new java.awt.Dimension(140, 103));
        btnBaoCao.setMinimumSize(new java.awt.Dimension(140, 103));
        btnBaoCao.setPreferredSize(new java.awt.Dimension(120, 120));
        btnBaoCao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBaoCao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaoCaoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBaoCao);

        panelMain.setLayout(new java.awt.BorderLayout());

        jMenu1.setText("Hệ thống");

        thongtin.setText("Thông tin cửa hàng");
        thongtin.setMargin(new java.awt.Insets(6, 6, 6, 6));
        thongtin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thongtinActionPerformed(evt);
            }
        });
        jMenu1.add(thongtin);

        jMenuItem30.setText("Đổi giao diện");
        jMenuItem30.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem30);

        jMenuItem2.setText("Đổi mật khẩu");
        jMenuItem2.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Đăng xuất");
        jMenuItem3.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Thoát");
        jMenuItem4.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Danh mục");

        menuQuanLyHangHoa.setText("Hàng hóa");
        menuQuanLyHangHoa.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuQuanLyHangHoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQuanLyHangHoaActionPerformed(evt);
            }
        });
        jMenu2.add(menuQuanLyHangHoa);

        menuQuanLyNhanVien.setText("Nhân viên");
        menuQuanLyNhanVien.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuQuanLyNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQuanLyNhanVienActionPerformed(evt);
            }
        });
        jMenu2.add(menuQuanLyNhanVien);

        menuQuanLyKhachHang.setText("Khách hàng");
        menuQuanLyKhachHang.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuQuanLyKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQuanLyKhachHangActionPerformed(evt);
            }
        });
        jMenu2.add(menuQuanLyKhachHang);

        menuQuanLyNhaCungCap.setText("Nhà cung cấp");
        menuQuanLyNhaCungCap.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuQuanLyNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQuanLyNhaCungCapActionPerformed(evt);
            }
        });
        jMenu2.add(menuQuanLyNhaCungCap);

        menuQuanLyTaiKhoan.setText("Tài khoản");
        menuQuanLyTaiKhoan.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuQuanLyTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQuanLyTaiKhoanActionPerformed(evt);
            }
        });
        jMenu2.add(menuQuanLyTaiKhoan);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Thu chi");

        jMenuItem9.setText("Tạo phiếu thu nợ");
        jMenuItem9.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setText("Danh sách phiếu thu nợ");
        jMenuItem10.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);
        jMenu3.add(jSeparator7);

        jMenuItem28.setText("Trả nợ nhà cung cấp");
        jMenuItem28.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem28);

        jMenuItem29.setText("Danh sách phiếu trả nợ NCC");
        jMenuItem29.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem29);
        jMenu3.add(jSeparator12);

        menuTaoPhieuChi.setText("Tạo phiếu chi");
        menuTaoPhieuChi.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuTaoPhieuChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTaoPhieuChiActionPerformed(evt);
            }
        });
        jMenu3.add(menuTaoPhieuChi);

        menuDanhSachPhieuChi.setText("Danh sách phiếu chi");
        menuDanhSachPhieuChi.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuDanhSachPhieuChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDanhSachPhieuChiActionPerformed(evt);
            }
        });
        jMenu3.add(menuDanhSachPhieuChi);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Nhập kho");

        menuNhapHang.setText("Nhập hàng");
        menuNhapHang.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNhapHangActionPerformed(evt);
            }
        });
        jMenu4.add(menuNhapHang);

        menuNhatKyNhapHang.setText("Nhật ký nhập hàng");
        menuNhatKyNhapHang.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuNhatKyNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNhatKyNhapHangActionPerformed(evt);
            }
        });
        jMenu4.add(menuNhatKyNhapHang);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Xuất kho");

        menuTaoHoaDonBanHang.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        menuTaoHoaDonBanHang.setText("Tạo đơn bán hàng");
        menuTaoHoaDonBanHang.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuTaoHoaDonBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTaoHoaDonBanHangActionPerformed(evt);
            }
        });
        jMenu5.add(menuTaoHoaDonBanHang);

        menuDanhSachHoaDonBanHang.setText("Danh sách hóa đơn bán hàng");
        menuDanhSachHoaDonBanHang.setMargin(new java.awt.Insets(6, 6, 6, 6));
        menuDanhSachHoaDonBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDanhSachHoaDonBanHangActionPerformed(evt);
            }
        });
        jMenu5.add(menuDanhSachHoaDonBanHang);
        jMenu5.add(jSeparator9);

        jMenuItem19.setText("Trả hàng lại NCC");
        jMenuItem19.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem19);

        jMenuItem20.setText("Nhật ký trả hàng cho NCC");
        jMenuItem20.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem20);
        jMenu5.add(jSeparator10);

        jMenuItem21.setText("Trích kho hàng hóa");
        jMenuItem21.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem21);

        jMenuItem22.setText("Nhật ký trích kho hàng hóa");
        jMenuItem22.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem22);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1281, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuTaoPhieuChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTaoPhieuChiActionPerformed
        frmPhieuChi panel = new frmPhieuChi(this, true, acc);
        panel.setVisible(true);
        loadTableNhatKyChiTien();
    }//GEN-LAST:event_menuTaoPhieuChiActionPerformed

    private void btnNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapHangActionPerformed
        panelNhapHang frmnhaphang = new panelNhapHang(acc);
        openTab(frmnhaphang, "Phiếu nhập hàng : ");
    }//GEN-LAST:event_btnNhapHangActionPerformed

    private void btnTaoHoaDonBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonBanHangActionPerformed
        taoPhieuBanHang();
    }//GEN-LAST:event_btnTaoHoaDonBanHangActionPerformed
    public void taoPhieuBanHang() {

        popupWaiting popup = new popupWaiting(this, false);
        popup.setVisible(true);
        Timer loadingPage = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int values = popup.prog.getValue();
                if (values < 100) {
                    popup.prog.setValue(values + 7);
                    if (popup.prog.getValue() == 98) {
                        panelTaoHoaDonBanHang panel = new panelTaoHoaDonBanHang(acc);
                        if (tabbed.getTabCount() == 0) {
                            panelMain.removeAll();
                            panelMain.add(tabbed);
                            tabbed.add(panel);
                        } else {
                            tabbed.add(panel);
                            tabbed.setSelectedIndex(tabbed.getTabCount() - 1);
                        }
                        panel.txtBarcode.requestFocus();
                        panel.txtBarcode.requestFocus();
                    }
                } else {
                    popup.setVisible(false);
                    return;
                }
            }
        });

        if (popup.prog.getValue() < popup.prog.getMaximum()) {
            loadingPage.start();
        } else {
            loadingPage.stop();
        }

    }
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Đăng xuất ?") == 0) {
            this.setVisible(false);
            new frmDangNhap().setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Thoát phần mềm ?") == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void btnDanhSachHoaDonBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachHoaDonBanHangActionPerformed
        loadTableDanhSachHoaDonBanHang();
        openTab(panelDanhSachHoaDonBanHang, "Danh sách hóa đơn");
    }//GEN-LAST:event_btnDanhSachHoaDonBanHangActionPerformed

    private void tableDanhSachHoaDonBanHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableDanhSachHoaDonBanHangFocusLost
        tableDanhSachHoaDonBanHang.clearSelection();
    }//GEN-LAST:event_tableDanhSachHoaDonBanHangFocusLost

    private void tableChiTietHoaDonBanHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableChiTietHoaDonBanHangFocusLost
        tableChiTietHoaDonBanHang.clearSelection();
    }//GEN-LAST:event_tableChiTietHoaDonBanHangFocusLost

    private void txtTimKiemHoaDonBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemHoaDonBanHangActionPerformed

    }//GEN-LAST:event_txtTimKiemHoaDonBanHangActionPerformed

    private void tableDanhSachHoaDonBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDanhSachHoaDonBanHangMouseClicked
        try {
            String idHoaDon = tableDanhSachHoaDonBanHang.getValueAt(tableDanhSachHoaDonBanHang.getSelectedRow(), 0) + "";
            MDHoaDon.showChiTietHoaDon(idHoaDon, tableChiTietHoaDonBanHang);
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_tableDanhSachHoaDonBanHangMouseClicked

    private void tableDanhSachHoaDonBanHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDanhSachHoaDonBanHangMousePressed
        if (tableDanhSachHoaDonBanHang.getSelectedRows().length == 1 && evt.getClickCount() == 2) {
            String idhoadon = tableDanhSachHoaDonBanHang.getValueAt(tableDanhSachHoaDonBanHang.getSelectedRow(), 0) + "";
            new frmXemHoaDonBanHang(this, true, acc, idhoadon).setVisible(true);
            try {
                loadTableSanPham();
            } catch (IOException ex) {
                Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTableDanhSachHoaDonBanHang();
            loadTableKhachHang();
        }
    }//GEN-LAST:event_tableDanhSachHoaDonBanHangMousePressed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new frmDoiMatKhau(this, true, acc).setVisible(true);
        loadTableAccount();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void menuQuanLyNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuanLyNhanVienActionPerformed
        loadTableNhanVien();
        openTab(panelQuanLyNhanVien, "Danh sách nhân viên");
    }//GEN-LAST:event_menuQuanLyNhanVienActionPerformed

    private void menuQuanLyKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuanLyKhachHangActionPerformed
        loadTableKhachHang();
        openTab(panelQuanLyKhachHang, "Danh sách khách hàng");
    }//GEN-LAST:event_menuQuanLyKhachHangActionPerformed

    private void menuQuanLyNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuanLyNhaCungCapActionPerformed
        loadTableNhaCungCap();
        openTab(panelQuanLyNhaCungCap, "Danh sách nhà cung cấp");
    }//GEN-LAST:event_menuQuanLyNhaCungCapActionPerformed

    private void menuQuanLyTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuanLyTaiKhoanActionPerformed
        loadTableAccount();
        openTab(panelQuanLyTaiKhoan, "Quản lý tài khoản");
    }//GEN-LAST:event_menuQuanLyTaiKhoanActionPerformed

    private void menuQuanLyHangHoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuanLyHangHoaActionPerformed
        try {
            loadTableSanPham();
        } catch (IOException ex) {
            Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        openTab(panelQuanLyHangHoa, "Kho hàng hóa");
    }//GEN-LAST:event_menuQuanLyHangHoaActionPerformed

    private void tableTaiKhoanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableTaiKhoanFocusLost
        tableTaiKhoan.clearSelection();
    }//GEN-LAST:event_tableTaiKhoanFocusLost

    private void tableNhaCungCapFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableNhaCungCapFocusLost
        tableNhaCungCap.clearSelection();
    }//GEN-LAST:event_tableNhaCungCapFocusLost

    private void tableKhachHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableKhachHangFocusLost
        tableKhachHang.clearSelection();
    }//GEN-LAST:event_tableKhachHangFocusLost

    private void tableNhanVienFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableNhanVienFocusLost
        tableNhanVien.clearSelection();
    }//GEN-LAST:event_tableNhanVienFocusLost

    private void menuTaoHoaDonBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTaoHoaDonBanHangActionPerformed
        taoPhieuBanHang();
    }//GEN-LAST:event_menuTaoHoaDonBanHangActionPerformed

    private void tableNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNhanVienMousePressed

        if (evt.getClickCount() == 2 && tableNhanVien.getSelectedRows().length == 1) {
            int indexRow = tableNhanVien.getSelectedRow();
            String idNhanVien = tableNhanVien.getValueAt(indexRow, 0) + "";
            frmEditorNhanVien frm = new frmEditorNhanVien((JFrame) this.getParent(), true, idNhanVien);
            frm.setVisible(true);
            MDNhanVien.arrayToTable(tableNhanVien);
        }
    }//GEN-LAST:event_tableNhanVienMousePressed

    private void btnThemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanVienActionPerformed
        frmEditorNhanVien frm = new frmEditorNhanVien((JFrame) this.getParent(), true);
        frm.setVisible(true);
        loadTableNhanVien();
    }//GEN-LAST:event_btnThemNhanVienActionPerformed

    private void txtTimKiemNhanVienKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemNhanVienKeyReleased
        String text = txtTimKiemNhanVien.getText();
        ArrayList<nhanVien> data = MDNhanVien.getDataToTable();
        ArrayList<nhanVien> find = new ArrayList<nhanVien>();
        for (nhanVien item : data) {
            if (item.getIdNhanVien().toLowerCase().contains(text.toLowerCase())
                    || item.getSoDienthoai().contains(text)
                    || item.getName().toLowerCase().contains(text.toLowerCase())
                    || helper.removeAccent(item.getIdNhanVien().toLowerCase()).contains(text.toLowerCase())
                    || helper.removeAccent(item.getName().toLowerCase()).contains(text.toLowerCase())) {
                find.add(item);
            }
        }
        if (text != "") {
            loadTableNhanVien(find);
        } else {
            loadTableNhanVien(data);
        }
    }//GEN-LAST:event_txtTimKiemNhanVienKeyReleased

    private void btnTimKiemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemNhanVienActionPerformed
        loadTableNhanVien();
    }//GEN-LAST:event_btnTimKiemNhanVienActionPerformed

    private void btnReloadTableSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadTableSanPhamActionPerformed
        try {
            loadTableSanPham();
        } catch (IOException ex) {
            Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDonViTinh();
        loadTableLoaiSanPham();
    }//GEN-LAST:event_btnReloadTableSanPhamActionPerformed

    private void tabbedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabbedKeyPressed

    }//GEN-LAST:event_tabbedKeyPressed

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        frmEditorSanPham frm = null;
        try {
            frm = new frmEditorSanPham(this, true, "add");
        } catch (IOException ex) {
            Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        frm.setVisible(true);
        try {
            loadTableSanPham();
        } catch (IOException ex) {
            Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void tableSanPhamPnlSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamPnlSanPhamMousePressed
        if (tableSanPhamPnlSanPham.getSelectedRows().length == 1 && evt.getClickCount() == 2) {
            String id = tableSanPhamPnlSanPham.getValueAt(tableSanPhamPnlSanPham.getSelectedRow(), 1) + "";
            frmEditorSanPham frm = null;
            try {
                frm = new frmEditorSanPham(this, true, id);
            } catch (IOException ex) {
                Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
            }
            frm.setVisible(true);
            try {
                loadTableSanPham();
            } catch (IOException ex) {
                Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_tableSanPhamPnlSanPhamMousePressed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        openTab(panelDanhSachPhieuThuNoKhachHang, "Danh sách phiếu thu nợ");
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        frmThuNoKhachHang panel = new frmThuNoKhachHang(this, true, acc);
        panel.setVisible(true);
        loadTableKhachHang();
        loadTableThuNoKhachHang();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void menuNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNhapHangActionPerformed
        panelNhapHang frmnhaphang = new panelNhapHang(acc);
        openTab(frmnhaphang, "Phiếu nhập hàng : ");
    }//GEN-LAST:event_menuNhapHangActionPerformed

    private void tableThuNoKhachHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableThuNoKhachHangFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tableThuNoKhachHangFocusLost

    private void tableThuNoKhachHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableThuNoKhachHangMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tableThuNoKhachHangMousePressed

    private void txtTimKiemThuNoKhachHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemThuNoKhachHangKeyReleased
        loadTableThuNoKhachHang(txtTimKiemThuNoKhachHang.getText().trim());
    }//GEN-LAST:event_txtTimKiemThuNoKhachHangKeyReleased

    private void btnTimKiemNhanVien1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemNhanVien1ActionPerformed
        loadTableThuNoKhachHang();
    }//GEN-LAST:event_btnTimKiemNhanVien1ActionPerformed

    private void btnTaoPhieuThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoPhieuThuActionPerformed
        frmThuNoKhachHang panel = new frmThuNoKhachHang(this, true, acc);
        panel.setVisible(true);
        loadTableKhachHang();
        loadTableThuNoKhachHang();
    }//GEN-LAST:event_btnTaoPhieuThuActionPerformed


    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:
        frmTraNoNhaCungCap panel = new frmTraNoNhaCungCap(this, true, acc);
        panel.setVisible(true);
        loadTableNhaCungCap();
        loadTableDanhSachPhieuTraNoNCC();

    }//GEN-LAST:event_jMenuItem28ActionPerformed
    public void loadTableDanhSachPhieuTraNoNCC() {
        MDCongNo.dataTableTraNoNhaCungCap(tableTraNoNhaCungCap);
    }

    public void loadTableNhatKyChiTien() {
        MDThuChi.dataTableDanhSachPhieuChi(tableDanhSachPhieuChi);
    }
    private void tableTraNoNhaCungCapFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableTraNoNhaCungCapFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tableTraNoNhaCungCapFocusLost

    private void tableTraNoNhaCungCapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTraNoNhaCungCapMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tableTraNoNhaCungCapMousePressed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        // TODO add your handling code here:
        openTab(panelDanhSachPhieuTraNoNCC, "Danh sách phiếu trả nợ NCC");
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void btnXoaSanPham2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPham2ActionPerformed
        frmEditorDonViTinh frm = new frmEditorDonViTinh(this, true, "add");
        frm.setVisible(true);
        loadTableDonViTinh();
    }//GEN-LAST:event_btnXoaSanPham2ActionPerformed

    private void btnXoaSanPham1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPham1ActionPerformed
        frmEditorLoaiSanPham frm = new frmEditorLoaiSanPham(this, true, "add");
        frm.setVisible(true);
        loadTableLoaiSanPham();
    }//GEN-LAST:event_btnXoaSanPham1ActionPerformed

    private void tableDonViTinhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDonViTinhMousePressed
        int index = tableDonViTinh.getSelectedRow();

        if (tableDonViTinh.getSelectedRows().length == 1 && evt.getClickCount() == 2) {
            String id = tableDonViTinh.getValueAt(index, 0) + "";
            frmEditorDonViTinh frm = new frmEditorDonViTinh(this, true, id);
            frm.setVisible(true);
            loadTableDonViTinh();
        }
    }//GEN-LAST:event_tableDonViTinhMousePressed

    private void tableLoaiSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableLoaiSanPhamMousePressed
        int index = tableLoaiSanPham.getSelectedRow();

        if (tableLoaiSanPham.getSelectedRows().length == 1 && evt.getClickCount() == 2) {
            String id = tableLoaiSanPham.getValueAt(index, 0) + "";
            frmEditorLoaiSanPham frm = new frmEditorLoaiSanPham(this, true, id);
            frm.setVisible(true);
            loadTableLoaiSanPham();
        }
    }//GEN-LAST:event_tableLoaiSanPhamMousePressed

    private void btnTaoPhieuChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoPhieuChiActionPerformed
        frmPhieuChi panel = new frmPhieuChi(this, true, acc);
        panel.setVisible(true);
        loadTableNhatKyChiTien();
    }//GEN-LAST:event_btnTaoPhieuChiActionPerformed

    private void txtTimKiemThuNoKhachHang2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemThuNoKhachHang2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemThuNoKhachHang2KeyReleased

    private void btnTimKiemNhanVien3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemNhanVien3ActionPerformed
        // TODO add your handling code here:
        loadTableNhatKyChiTien();
    }//GEN-LAST:event_btnTimKiemNhanVien3ActionPerformed

    private void tableDanhSachPhieuChiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableDanhSachPhieuChiFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tableDanhSachPhieuChiFocusLost

    private void tableDanhSachPhieuChiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDanhSachPhieuChiMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tableDanhSachPhieuChiMousePressed

    private void menuDanhSachPhieuChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDanhSachPhieuChiActionPerformed
        // TODO add your handling code here:
        openTab(panelDanhSachPhieuChi, "Nhật Ký Chi Tiền");

    }//GEN-LAST:event_menuDanhSachPhieuChiActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        new frmEditorGiaoDien(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void btnThemNhaCungCap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhaCungCap1ActionPerformed
        new frmEditorAccount(this, true, "add").setVisible(true);
        loadTableAccount();
    }//GEN-LAST:event_btnThemNhaCungCap1ActionPerformed

    private void menuDanhSachHoaDonBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDanhSachHoaDonBanHangActionPerformed
        loadTableDanhSachHoaDonBanHang();
        openTab(panelDanhSachHoaDonBanHang, "Danh sách hóa đơn");
    }//GEN-LAST:event_menuDanhSachHoaDonBanHangActionPerformed

    private void tableTaiKhoanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTaiKhoanMousePressed
        if (tableTaiKhoan.getSelectedRows().length == 1 && evt.getClickCount() == 2) {
            String username = tableTaiKhoan.getValueAt(tableTaiKhoan.getSelectedRow(), 0) + "";
            new frmEditorAccount(this, true, username).setVisible(true);
            loadTableAccount();
        }
    }//GEN-LAST:event_tableTaiKhoanMousePressed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        traHangNhaCungCap();
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        traHangNhaCungCap();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtTimKiemTaiKhoanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemTaiKhoanKeyReleased
        // TÌM KIẾM TÀI KHOẢN

        String keyword = txtTimKiemTaiKhoan.getText();

        ArrayList<Account> data = MDAccount.getDataToTable();
        ArrayList<Account> find = new ArrayList<>();
        for (Account item : data) {
            if (item.getIdNhanVien().toLowerCase().contains(keyword.toLowerCase())
                    || item.getUsername().toLowerCase().contains(keyword.toLowerCase())
                    || helper.removeAccent(item.getIdNhanVien().toLowerCase()).contains(keyword.toLowerCase())
                    || helper.removeAccent(item.getUsername().toLowerCase()).contains(keyword.toLowerCase())) {

                find.add(item);
            }
        }
        if (keyword != "") {
            loadTableAccount(find);
        } else {
            loadTableAccount(data);
        }
    }//GEN-LAST:event_txtTimKiemTaiKhoanKeyReleased

    private void txtTimKiemNhaCungCapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemNhaCungCapKeyReleased
        String keyword = txtTimKiemNhaCungCap.getText();

        ArrayList<nhaCungCap> data = MDNhaCungCap.getAll();
        ArrayList<nhaCungCap> find = new ArrayList<>();
        for (nhaCungCap item : data) {
            if (item.getIdNhaCungCap().toLowerCase().contains(keyword.toLowerCase())
                    || item.getName().toLowerCase().contains(keyword.toLowerCase())
                    || item.getSoDienThoai().toLowerCase().contains(keyword.toLowerCase())
                    || helper.removeAccent(item.getIdNhaCungCap().toLowerCase()).contains(keyword.toLowerCase())
                    || helper.removeAccent(item.getName().toLowerCase()).contains(keyword.toLowerCase())
                    || helper.removeAccent(item.getSoDienThoai().toLowerCase()).contains(keyword.toLowerCase())) {

                find.add(item);
            }
        }
        if (keyword != "") {
            loadTableNhaCungCap(find);
        } else {
            loadTableNhaCungCap(data);
        }
    }//GEN-LAST:event_txtTimKiemNhaCungCapKeyReleased

    private void txtTimKiemSanPhamPnlSanPhamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSanPhamPnlSanPhamKeyReleased
        try {
            // TODO add your handling code here:
//        loadTableSanPhamKeyReleased(txtTimKiemSanPhamPnlSanPham.getText());
            loadTableLoaiSanPham(comboBoxNhomHang.getSelectedItem() + "", txtTimKiemSanPhamPnlSanPham.getText().trim());
        } catch (IOException ex) {
            Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtTimKiemSanPhamPnlSanPhamKeyReleased

    public void loadTableSanPhamKeyReleased(String keyword) throws IOException {
        ArrayList<sanPham> dataSanPhamTable = MDSanPham.getDataToTableBanHang();
        comboBoxNhomHang.setSelectedIndex(0);
        DefaultTableModel model = (DefaultTableModel) tableSanPhamPnlSanPham.getModel();
        model.setRowCount(0);

        for (sanPham item : dataSanPhamTable) {
            if (item.getIdSanPham().toLowerCase().contains(keyword.toLowerCase())
                    || item.getName().toLowerCase().contains(keyword.toLowerCase())
                    || item.getBarcode().toLowerCase().contains(keyword.toLowerCase())
                    || helper.removeAccent(item.getIdSanPham().toLowerCase()).contains(keyword.toLowerCase())
                    || helper.removeAccent(item.getName().toLowerCase()).contains(keyword.toLowerCase())) {

                ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
                model.addRow(new Object[]{
                    imageIcon,
                    item.getIdSanPham(),
                    item.getName(),
                    item.getBarcode(),
                    item.getIdDonViTinh(),
                    item.getIdLoaiSanPham(),
                    helper.LongToString(item.getGiaNhap()),
                    helper.LongToString(item.getGiaBan()),
                    helper.LongToString(item.getGiaSi()),
                    item.getSoLuong(),
                    item.getSoLuongToiThieu(),
                    item.getGhiChu(),
                    item.isTrangThai()

                });
            }
        }
        tableSanPhamPnlSanPham.setModel(model);
    }
    private void txtTimKiemSanPhamPnlSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemSanPhamPnlSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemSanPhamPnlSanPhamActionPerformed

    private void txtTimKiemKhachHangPnlKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKhachHangPnlKHKeyReleased
        String text = txtTimKiemKhachHangPnlKH.getText();
        ArrayList<khachHang> dataKhachHangTable = MDKhachHang.getDataToTable();
        ArrayList<khachHang> find = new ArrayList<khachHang>();

        for (khachHang item : dataKhachHangTable) {
            if (item.getIdKhachHang().toLowerCase().contains(text.toLowerCase())
                    || item.getName().toLowerCase().contains(text.toLowerCase())
                    || item.getSoDienThoai().contains(text)
                    || helper.removeAccent(item.getName().toLowerCase()).contains(text.toLowerCase())) {
                find.add(item);
            }
        }
        if (text != "") {
            loadTableKhachHang(find);
        } else {
            loadTableKhachHang(dataKhachHangTable);
        }

    }//GEN-LAST:event_txtTimKiemKhachHangPnlKHKeyReleased

    //đọc danh sách loại sản phẩm
    public void loadComboboxLoaiSanPham() {
        comboBoxNhomHang.removeAllItems();
        comboBoxNhomHang.addItem("Tất cả");
        //đọc danh sách Loại sản phẩm
        ArrayList<String> listLoaiSanPham = MDLoaiSanPham.getNames();
        for (String name : listLoaiSanPham) {
            comboBoxNhomHang.addItem(name);
        }
        comboBoxNhomHang.setSelectedIndex(0);
    }

    //Gom nhóm Loại sản phẩm
    public void loadTableSanPham(String loaiSanPham) throws IOException {
        ArrayList<sanPham> dataSanPhamTable = MDSanPham.getDataToTableBanHang();
        DefaultTableModel model = (DefaultTableModel) tableSanPhamPnlSanPham.getModel();
        model.setRowCount(0);
        for (sanPham item : dataSanPhamTable) {

            if (loaiSanPham.equals("Tất cả") || item.getIdLoaiSanPham().equals(loaiSanPham)) {

                ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));

                model.addRow(new Object[]{
                    imageIcon,
                    item.getIdSanPham(),
                    item.getName(),
                    item.getBarcode(),
                    item.getIdDonViTinh(),
                    item.getSoLuong(),
                    helper.LongToString(item.getGiaBan())
                });
            }
        }
        tableSanPhamPnlSanPham.setModel(model);
    }

    private void comboBoxNhomHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxNhomHangItemStateChanged
        // TODO add your handling code here:
        String loaSanPham = comboBoxNhomHang.getSelectedItem() + "";
        try {
            loadTableLoaiSanPham(loaSanPham);
        } catch (IOException ex) {
            Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboBoxNhomHangItemStateChanged

    private void txtTimKiemSanPhamPnlSanPhamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSanPhamPnlSanPhamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemSanPhamPnlSanPhamKeyPressed


    private void thongtinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thongtinActionPerformed
//        openTab(frmEditorThongTinCuaHang, "Thông Tin Cửa Hàng");
        new frmEditorThongTinCuaHang(this, true).setVisible(true);
    }//GEN-LAST:event_thongtinActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String keyword = txtTimKiemHoaDonBanHang.getText().trim();
        String dateFrom = helper.LayNgayString(dateFromHoaDon.getDate(), "yyyy-MM-dd");
        String dateTo = helper.LayNgayString(dateToHoaDon.getDate(), "yyyy-MM-dd");
        MDHoaDon.getDanhSachHoaDon(tableDanhSachHoaDonBanHang, dateFrom, dateTo, keyword);
        DefaultTableModel model = (DefaultTableModel) tableChiTietHoaDonBanHang.getModel();
        model.setRowCount(0);
        tableChiTietHoaDonBanHang.setModel(model);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void tableKhachHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableKhachHangMousePressed
        if (evt.getClickCount() == 2 && tableKhachHang.getSelectedRows().length == 1) {
            String idKhachHang = tableKhachHang.getValueAt(tableKhachHang.getSelectedRow(), 0) + "";
            new frmEditorKhachHang(this, true, idKhachHang).setVisible(true);
            loadTableKhachHang();
        }
    }//GEN-LAST:event_tableKhachHangMousePressed

    private void btnThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhachHangActionPerformed
        new frmEditorKhachHang(this, true).setVisible(true);
        loadTableKhachHang();
    }//GEN-LAST:event_btnThemKhachHangActionPerformed

    private void btnBaoCaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaoCaoActionPerformed
        openTab(panelBaoCaoTongHop, "Báo cáo tổng hợp :");

        clearTable(tableCongNoNCC);
        clearTable(tableChiPhiNhapHang);
        clearTable(tableCacKhoanChi);
        clearTable(tableLuongNhanVien);
        clearTable(tableTrichKhoSanPham);
        clearTable(tableCongNoKhachHang);
        clearTable(tableDoanhThuLoiNhuan);
        clearTable(tableTraNoNhaCungCap);
    }//GEN-LAST:event_btnBaoCaoActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        openTab(new panelTrichKhoSanPham(acc), "Phiếu trích kho :");
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void txtTimKiemHoaDonTrichKhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemHoaDonTrichKhoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemHoaDonTrichKhoActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String keyword = txtTimKiemHoaDonTrichKho.getText().trim();
        String dateFrom = helper.LayNgayString(dateFromTrichKho.getDate(), "yyyy-MM-dd");
        String dateTo = helper.LayNgayString(dateToTrichKho.getDate(), "yyyy-MM-dd");
        MDTrichKho.loadTableHoaDonTrichKho(tableHoaDonTrichKho, keyword, dateFrom, dateTo);
        clearTable(tableChiTietTrichKho);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void tableHoaDonTrichKhoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableHoaDonTrichKhoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tableHoaDonTrichKhoFocusLost

    private void tableHoaDonTrichKhoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoaDonTrichKhoMouseClicked
        try {
            String id = tableHoaDonTrichKho.getValueAt(tableHoaDonTrichKho.getSelectedRow(), 0) + "";
            MDTrichKho.loadChiTietTrichKho(tableChiTietTrichKho, id);
        } catch (Exception e) {
            return;
        }


    }//GEN-LAST:event_tableHoaDonTrichKhoMouseClicked
    public void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        table.setModel(model);
    }


    private void tableHoaDonTrichKhoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoaDonTrichKhoMousePressed
        if (tableHoaDonTrichKho.getSelectedRows().length == 1 && evt.getClickCount() == 2) {
            String id = tableHoaDonTrichKho.getValueAt(tableHoaDonTrichKho.getSelectedRow(), 0) + "";
            new frmXemPhieuTrichKho(this, true, id).setVisible(true);
            loadTableHoaDonTrichKho();
        }
    }//GEN-LAST:event_tableHoaDonTrichKhoMousePressed

    private void tableChiTietTrichKhoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableChiTietTrichKhoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tableChiTietTrichKhoFocusLost

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        openTab(panelNhatKyTrichKho, "Nhật ký trích kho");
        loadTableHoaDonTrichKho();
        clearTable(tableChiTietTrichKho);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void menuNhatKyNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNhatKyNhapHangActionPerformed
        openTab(panelDanhSachHoaDonNhapHang, "Danh sách hóa đơn nhập hàng :");
        loadTableHoaDonNhapHang();
        clearTable(tableChiTietNhapHang);
    }//GEN-LAST:event_menuNhatKyNhapHangActionPerformed

    private void txtTimKiemHoaDonNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemHoaDonNhapHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemHoaDonNhapHangActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        MDNhapHang.loadTable(tableHoaDonNhapHang,
                txtTimKiemHoaDonNhapHang.getText().trim(),
                helper.LayNgayString(dateFromNhapHang.getDate(), "yyyy-MM-dd"),
                helper.LayNgayString(dateToNhapHang.getDate(), "yyyy-MM-dd")
        );
    }//GEN-LAST:event_jButton10ActionPerformed

    private void tableHoaDonNhapHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableHoaDonNhapHangFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tableHoaDonNhapHangFocusLost

    private void tableHoaDonNhapHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoaDonNhapHangMouseClicked
        try {
            String idHoaDon = tableHoaDonNhapHang.getValueAt(tableHoaDonNhapHang.getSelectedRow(), 0) + "";
            MDNhapHang.loadChiTietHoaDon(tableChiTietNhapHang, idHoaDon);
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_tableHoaDonNhapHangMouseClicked

    private void tableHoaDonNhapHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoaDonNhapHangMousePressed
        if (evt.getClickCount() == 2 && tableHoaDonNhapHang.getSelectedRows().length == 1) {
            String id = tableHoaDonNhapHang.getValueAt(tableHoaDonNhapHang.getSelectedRow(), 0) + "";

            new frmXemHoaDonNhapHang(this, true, id).setVisible(true);
            loadTableHoaDonNhapHang();
            try {
                loadTableSanPham();
            } catch (IOException ex) {
                Logger.getLogger(frmMAIN.class.getName()).log(Level.SEVERE, null, ex);
            }
            clearTable(tableChiTietNhapHang);
        }
    }//GEN-LAST:event_tableHoaDonNhapHangMousePressed

    private void tableChiTietNhapHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableChiTietNhapHangFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tableChiTietNhapHangFocusLost

    private void txtTimKiemHoaDonTraHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemHoaDonTraHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemHoaDonTraHangActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void tableHoaDonTraHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableHoaDonTraHangFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tableHoaDonTraHangFocusLost

    private void tableHoaDonTraHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoaDonTraHangMouseClicked
        try {
            String idhoadon = tableHoaDonTraHang.getValueAt(tableHoaDonTraHang.getSelectedRow(), 0) + "";
            MDTraHang.loadChiTietHoaDon(tableChiTietTraHang, idhoadon);
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_tableHoaDonTraHangMouseClicked

    private void tableHoaDonTraHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoaDonTraHangMousePressed
        if (evt.getClickCount() == 2 && tableHoaDonTraHang.getSelectedRows().length == 1) {
            String idhoadon = tableHoaDonTraHang.getValueAt(tableHoaDonTraHang.getSelectedRow(), 0) + "";
            new frmXemHoaDonTraHang(this, true, idhoadon).setVisible(true);
            loadTableHoaDonTraHang();
        }
    }//GEN-LAST:event_tableHoaDonTraHangMousePressed
    public void loadTableHoaDonTraHang() {
        MDTraHang.loadTable(tableHoaDonTraHang);
        clearTable(tableChiTietTraHang);
    }
    private void tableChiTietTraHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableChiTietTraHangFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tableChiTietTraHangFocusLost

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        openTab(panelHoaDonTraHangNCC, "Danh sách hóa đơn trả hàng :");
        loadTableHoaDonTraHang();
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void tableNhaCungCapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNhaCungCapMousePressed
        if (evt.getClickCount() == 2 && tableNhaCungCap.getSelectedRows().length == 1) {
            String id = tableNhaCungCap.getValueAt(tableNhaCungCap.getSelectedRow(), 0) + "";
            new frmEditorNhaCungCap(this, true, id).setVisible(true);
            loadTableNhaCungCap();
        }
    }//GEN-LAST:event_tableNhaCungCapMousePressed

    private void btnThemNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhaCungCapActionPerformed
        new frmEditorNhaCungCap(this, true).setVisible(true);
        loadTableNhaCungCap();
    }//GEN-LAST:event_btnThemNhaCungCapActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        MDCongNo.dataTableTraNoNhaCungCap(tableTraNoNhaCungCap,
                txtTimKiemTraNoNcc.getText().trim(),
                helper.LayNgayString(dateFromTraNoNcc.getDate(), "yyyy-MM-dd"),
                helper.LayNgayString(dateToTraNoNcc.getDate(), "yyyy-MM-dd")
        );
    }//GEN-LAST:event_jButton12ActionPerformed

    private void txtTimKiemTraNoNccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemTraNoNccActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemTraNoNccActionPerformed

    private void tabBaoCaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabBaoCaoFocusGained
        tabBaoCao.requestFocus();
    }//GEN-LAST:event_tabBaoCaoFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String dateFrom = helper.LayNgayString(dateFromBaoCao.getDate(), "yyyy-MM-dd");
        String dateTo = helper.LayNgayString(dateToBaoCao.getDate(), "yyyy-MM-dd");

        MDBaoCao.loadTableCacKhoanChi(tableCacKhoanChi, dateFrom, dateTo, btnTongTienChiTieu);
        MDBaoCao.loadTableChiPhiNhapHang(tableChiPhiNhapHang, dateFrom, dateTo, txtTongTienNhapHang, txtTongTienThanhToanNhapHang, txtTienNoLaiNCC);
        MDBaoCao.loadTableCongNoNhaCungCap(tableCongNoNCC, dateFrom, dateTo, txtSoTienDaTraNCC, txtSoTienNoNCCConLai);
        MDBaoCao.loadTableLuongNhanVien(tableLuongNhanVien, dateFrom, dateTo, txtTongTienLuong);
        MDBaoCao.loadTableTienTrichKho(tableTrichKhoSanPham, dateFrom, dateTo, txtTongTienTrichKho);
        MDBaoCao.loadCongNoKhachHang(tableCongNoKhachHang, dateFrom, dateTo, txtTongTienNoDaThu, txtSoTienNoConLai);
        MDBaoCao.loadTableDoanhThuLoiNhuan(tableDoanhThuLoiNhuan, dateFrom, dateTo, txtTongDoanhThu, txtTongLoiNhuan);
        MDBaoCao.loadTableTienTraLaiNCC(tableTienTraHangNCC, dateFrom, dateTo, txtTongTienNhanLai, txtTongTienTruCongNo);

    }//GEN-LAST:event_jButton1ActionPerformed
    public void loadTableHoaDonTrichKho() {
        MDTrichKho.loadTableHoaDonTrichKho(tableHoaDonTrichKho);
    }

    private void txtTimKiemTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    public void loadTableLoaiSanPham(String loaiSanPham, String keyword) throws IOException {
        ArrayList<sanPham> dataSanPhamTable = listSanPham;
        DefaultTableModel model = (DefaultTableModel) tableSanPhamPnlSanPham.getModel();
        model.setRowCount(0);
        for (sanPham item : dataSanPhamTable) {
            String rs = item.getBarcode() + " " + item.getIdSanPham() + " " + item.getName();
            if ((loaiSanPham.equals("Tất cả") || item.getIdLoaiSanPham().equals(loaiSanPham)) && (helper.removeAccent(rs.toLowerCase()).contains(helper.removeAccent(keyword.toLowerCase())))) {
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));

                model.addRow(new Object[]{
                    imageIcon,
                    item.getIdSanPham(),
                    item.getName(),
                    item.getBarcode(),
                    item.getIdDonViTinh(),
                    item.getIdLoaiSanPham(),
                    helper.LongToString(item.getGiaNhap()),
                    helper.LongToString(item.getGiaBan()),
                    helper.LongToString(item.getGiaSi()),
                    item.getSoLuong(),
                    item.getSoLuongToiThieu(),
                    item.getGhiChu(),
                    item.isTrangThai()

                });
            }
        }
        tableSanPhamPnlSanPham.setModel(model);
    }

    public void loadTableLoaiSanPham(String loaiSanPham) throws IOException {
        ArrayList<sanPham> dataSanPhamTable = listSanPham;
        DefaultTableModel model = (DefaultTableModel) tableSanPhamPnlSanPham.getModel();
        model.setRowCount(0);
        for (sanPham item : dataSanPhamTable) {

            if (loaiSanPham.equals("Tất cả") || item.getIdLoaiSanPham().equals(loaiSanPham)) {

                ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));

                model.addRow(new Object[]{
                    imageIcon,
                    item.getIdSanPham(),
                    item.getName(),
                    item.getBarcode(),
                    item.getIdDonViTinh(),
                    item.getIdLoaiSanPham(),
                    helper.LongToString(item.getGiaNhap()),
                    helper.LongToString(item.getGiaBan()),
                    helper.LongToString(item.getGiaSi()),
                    item.getSoLuong(),
                    item.getSoLuongToiThieu(),
                    item.getGhiChu(),
                    item.isTrangThai()

                });
            }
        }
        tableSanPhamPnlSanPham.setModel(model);
    }

//    public void LoadTableKhachHangKeyReleased(String keyword){
    public void LoadTableKhachHangKeyReleased(String keyword) {
        String text = txtTimKiemKhachHangPnlKH.getText();
        ArrayList<khachHang> dataKhachHangTable = MDKhachHang.getDataToTable();
        ArrayList<khachHang> find = new ArrayList<khachHang>();

        for (khachHang item : dataKhachHangTable) {
            if (item.getIdKhachHang().toLowerCase().contains(text.toLowerCase())
                    || item.getName().toLowerCase().contains(text.toLowerCase())
                    || item.getSoDienThoai().contains(text)
                    || helper.removeAccent(item.getName().toLowerCase()).contains(text.toLowerCase())) {
                find.add(item);
            }
        }
        if (text != "") {
            loadTableKhachHang(find);
        } else {
            loadTableKhachHang(dataKhachHangTable);
        }
    }
//    public void LoadTableKhachHangKeyReleased(String keyword) {
//        ArrayList<khachHang> dataKhachHangTable = MDKhachHang.getDataToTable();
//
//        DefaultTableModel model = (DefaultTableModel) tableKhachHang.getModel();
//        model.setRowCount(0);
//
//        for (khachHang item : dataKhachHangTable) {
//            if (item.getIdKhachHang().toLowerCase().contains(keyword.toLowerCase())
//                    || item.getName().toLowerCase().contains(keyword.toLowerCase())
//                    || item.getSoDienThoai().contains(keyword)) {
//
//                model.addRow(new Object[]{
//                    item.getIdKhachHang(),
//                    item.getName(),
//                    item.getDiaChi(),
//                    item.getSoDienThoai(),
//                    item.getGhiChu(),
//                    item.getNo()
//                });
//
//            }
//        }
//        
//    }

////    private void txtTimKiemNhanVienKeyReleased(java.awt.event.KeyEvent evt) {                                               
////        String text = txtTimKiemNhanVien.getText();
////        ArrayList<nhanVien> data = MDNhanVien.getDataToTable();
////        ArrayList<nhanVien> find = new ArrayList<nhanVien>();
////        for (nhanVien item : data) {
////            if (item.getIdNhanVien().toLowerCase().contains(text.toLowerCase())
////                    || item.getSoDienthoai().contains(text)
////                    || item.getName().toLowerCase().contains(text.toLowerCase())
////                    || helper.removeAccent(item.getIdNhanVien().toLowerCase()).contains(text.toLowerCase())
////                    || helper.removeAccent(item.getName().toLowerCase()).contains(text.toLowerCase())) {
////                find.add(item);
////            }
////        }
////        if (text != "") {
////            loadTableNhanVien(find);
////        } else {
////            loadTableNhanVien(data);
////        }
//    }
    public void openTab(JPanel TypeOfPanel, String name) {
        JPanel tab = TypeOfPanel;
        tab.setName(name);
        if (tabbed.getTabCount() == 0) {
            panelMain.removeAll();
            panelMain.add(tabbed);
            tabbed.setVisible(true);
            tabbed.add(tab);
        } else {
            tabbed.add(tab);
            tabbed.setSelectedIndex(tabbed.getTabCount() - 1);
        }
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMAIN(acc).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaoCao;
    private javax.swing.JButton btnDanhSachHoaDonBanHang;
    private javax.swing.JButton btnNhapHang;
    private javax.swing.JButton btnReloadTableSanPham;
    private javax.swing.JButton btnTaoHoaDonBanHang;
    private javax.swing.JButton btnTaoPhieuChi;
    private javax.swing.JButton btnTaoPhieuThu;
    private javax.swing.JButton btnThemKhachHang;
    private javax.swing.JButton btnThemNhaCungCap;
    private javax.swing.JButton btnThemNhaCungCap1;
    private javax.swing.JButton btnThemNhanVien;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnTimKiemKhachHang;
    private javax.swing.JButton btnTimKiemNhanVien;
    private javax.swing.JButton btnTimKiemNhanVien1;
    private javax.swing.JButton btnTimKiemNhanVien3;
    private javax.swing.JTextField btnTongTienChiTieu;
    private javax.swing.JButton btnXoaSanPham1;
    private javax.swing.JButton btnXoaSanPham2;
    private javax.swing.JComboBox<String> cbFilterThuNo2;
    private javax.swing.JComboBox<String> comboBoxNhomHang;
    private com.toedter.calendar.JDateChooser dateFromBaoCao;
    private com.toedter.calendar.JDateChooser dateFromHoaDon;
    private com.toedter.calendar.JDateChooser dateFromNhapHang;
    private com.toedter.calendar.JDateChooser dateFromTraHang;
    private com.toedter.calendar.JDateChooser dateFromTraNoNcc;
    private com.toedter.calendar.JDateChooser dateFromTrichKho;
    private com.toedter.calendar.JDateChooser dateToBaoCao;
    private com.toedter.calendar.JDateChooser dateToHoaDon;
    private com.toedter.calendar.JDateChooser dateToNhapHang;
    private com.toedter.calendar.JDateChooser dateToTraHang;
    private com.toedter.calendar.JDateChooser dateToTraNoNcc;
    private com.toedter.calendar.JDateChooser dateToTrichKho;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem menuDanhSachHoaDonBanHang;
    private javax.swing.JMenuItem menuDanhSachPhieuChi;
    private javax.swing.JMenuItem menuNhapHang;
    private javax.swing.JMenuItem menuNhatKyNhapHang;
    private javax.swing.JMenuItem menuQuanLyHangHoa;
    private javax.swing.JMenuItem menuQuanLyKhachHang;
    private javax.swing.JMenuItem menuQuanLyNhaCungCap;
    private javax.swing.JMenuItem menuQuanLyNhanVien;
    private javax.swing.JMenuItem menuQuanLyTaiKhoan;
    private javax.swing.JMenuItem menuTaoHoaDonBanHang;
    private javax.swing.JMenuItem menuTaoPhieuChi;
    private javax.swing.JPanel panelBaoCaoTongHop;
    private javax.swing.JPanel panelDanhSachHoaDonBanHang;
    private javax.swing.JPanel panelDanhSachHoaDonNhapHang;
    private javax.swing.JPanel panelDanhSachPhieuChi;
    private javax.swing.JPanel panelDanhSachPhieuThuNoKhachHang;
    private javax.swing.JPanel panelDanhSachPhieuTraNoNCC;
    private javax.swing.JPanel panelHoaDonTraHangNCC;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelNhatKyTrichKho;
    private javax.swing.JPanel panelQuanLyHangHoa;
    private javax.swing.JPanel panelQuanLyKhachHang;
    private javax.swing.JPanel panelQuanLyNhaCungCap;
    private javax.swing.JPanel panelQuanLyNhanVien;
    private javax.swing.JPanel panelQuanLyTaiKhoan;
    private javax.swing.JTabbedPane tabBaoCao;
    private javax.swing.JTabbedPane tabbed;
    private javax.swing.JTable tableCacKhoanChi;
    private javax.swing.JTable tableChiPhiNhapHang;
    private javax.swing.JTable tableChiTietHoaDonBanHang;
    private javax.swing.JTable tableChiTietNhapHang;
    private javax.swing.JTable tableChiTietTraHang;
    private javax.swing.JTable tableChiTietTrichKho;
    private javax.swing.JTable tableCongNoKhachHang;
    private javax.swing.JTable tableCongNoNCC;
    private javax.swing.JTable tableDanhSachHoaDonBanHang;
    private javax.swing.JTable tableDanhSachPhieuChi;
    private javax.swing.JTable tableDoanhThuLoiNhuan;
    private javax.swing.JTable tableDonViTinh;
    private javax.swing.JTable tableHoaDonNhapHang;
    private javax.swing.JTable tableHoaDonTraHang;
    private javax.swing.JTable tableHoaDonTrichKho;
    private javax.swing.JTable tableKhachHang;
    private javax.swing.JTable tableLoaiSanPham;
    private javax.swing.JTable tableLuongNhanVien;
    private javax.swing.JTable tableNhaCungCap;
    private javax.swing.JTable tableNhanVien;
    private javax.swing.JTable tableSanPhamPnlSanPham;
    private javax.swing.JTable tableTaiKhoan;
    private javax.swing.JTable tableThuNoKhachHang;
    private javax.swing.JTable tableTienTraHangNCC;
    private javax.swing.JTable tableTraNoNhaCungCap;
    private javax.swing.JTable tableTrichKhoSanPham;
    private javax.swing.JMenuItem thongtin;
    private javax.swing.JTextField txtSoTienDaTraNCC;
    private javax.swing.JTextField txtSoTienNoConLai;
    private javax.swing.JTextField txtSoTienNoNCCConLai;
    private javax.swing.JTextField txtTienNoLaiNCC;
    private javax.swing.JTextField txtTimKiemHoaDonBanHang;
    private javax.swing.JTextField txtTimKiemHoaDonNhapHang;
    private javax.swing.JTextField txtTimKiemHoaDonTraHang;
    private javax.swing.JTextField txtTimKiemHoaDonTrichKho;
    private javax.swing.JTextField txtTimKiemKhachHangPnlKH;
    private javax.swing.JTextField txtTimKiemNhaCungCap;
    private javax.swing.JTextField txtTimKiemNhanVien;
    private javax.swing.JTextField txtTimKiemSanPhamPnlSanPham;
    private javax.swing.JTextField txtTimKiemTaiKhoan;
    private javax.swing.JTextField txtTimKiemThuNoKhachHang;
    private javax.swing.JTextField txtTimKiemThuNoKhachHang2;
    private javax.swing.JTextField txtTimKiemTraNoNcc;
    private javax.swing.JTextField txtTongDoanhThu;
    private javax.swing.JTextField txtTongLoiNhuan;
    private javax.swing.JTextField txtTongTienLuong;
    private javax.swing.JTextField txtTongTienNhanLai;
    private javax.swing.JTextField txtTongTienNhapHang;
    private javax.swing.JTextField txtTongTienNoDaThu;
    private javax.swing.JTextField txtTongTienThanhToanNhapHang;
    private javax.swing.JTextField txtTongTienTrichKho;
    private javax.swing.JTextField txtTongTienTruCongNo;
    // End of variables declaration//GEN-END:variables
}
