package VIEW;

import CLASS.ThongTinCuaHang;
import CLASS.chiTietHoaDon;
import CLASS.hoaDon;
import CLASS.khachHang;
import CLASS.sanPham;
import COMPONENT.DetailedComboBox;
import HELPER.helper;
import MODEL.MDChiTietHoaDon;
import MODEL.MDHoaDon;
import MODEL.MDKhachHang;
import MODEL.MDLoaiSanPham;
import MODEL.MDSanPham;
import MODEL.MDThongTinCuaHang;
import src.CLASS.Account;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class frmXemHoaDonBanHang extends javax.swing.JDialog {
    
    public static Account acc;
    private ArrayList<chiTietHoaDon> dataChiTietHoaDon;
    private ArrayList<chiTietHoaDon> dataChiTietHoaDonMoi;
    private ArrayList<chiTietHoaDon> dataChiTietHoaDonCu;
    private ArrayList<String> listLoaiSanPham = MDLoaiSanPham.getNames();
    private ArrayList<khachHang> dataKhachHang = MDKhachHang.getDataToComboBox();
    private static hoaDon hoadon;
    private DetailedComboBox comboboxKhachHang;
    public static String idKhachHang;
    private long soTienGoc;
    private ThongTinCuaHang admin = MDThongTinCuaHang.getThongTin();
    
    public frmXemHoaDonBanHang(java.awt.Frame parent, boolean modal, Account account, String idhoaDon) {
        
        this.acc = account;
        this.hoadon = MDHoaDon.getHoaDon(idhoaDon);
        this.dataChiTietHoaDon = MDChiTietHoaDon.getChiTietHoaDon(idhoaDon);
        this.dataChiTietHoaDonMoi = this.dataChiTietHoaDon;
        this.dataChiTietHoaDonCu = (ArrayList<chiTietHoaDon>) this.dataChiTietHoaDon.clone();
        this.idKhachHang = hoadon.getIdKhachHang();
        initComponents();

        //load combobox giá sĩ giá lẽ
        if (hoadon.getLoaiGia() == 0) {
            cbChonGia.setSelectedIndex(0);
        } else {
            cbChonGia.setSelectedIndex(1);
        }
        
        loadComboboxKhachHang();
        cbHinhThucThanhToan.setSelectedIndex(hoadon.getHinhThucThanhToan() - 1);
        if (cbHinhThucThanhToan.getSelectedIndex() == 2) {
            lb.setVisible(true);
            txtTienKhachDua.setVisible(true);
            txtTienKhachDua.setText(helper.LongToString(hoadon.getSoTienNhanDuoc()));
        }
        txtGiaTriGiam.setText(helper.LongToString(hoadon.getGiamGia()));
        this.setTitle("Phiếu bán hàng : " + hoadon.getId());
        ImageIcon img = new ImageIcon(getClass().getResource("/ICON/favicon.png"));
        this.setIconImage(img.getImage());

        // load combobox thông tin khách hàng
        UIManager.put("Table.consistentHomeEndKeyBehavior", true);

        // load comboBox loại sản phẩm
        loadComboboxLoaiSanPham();

        // add icon search 
        helper.addIconSearch(txtTimKiemSanPham);

        // add key listener cho nút quét mã vạch
        addKeyEnter();

        //set model table sản phẩm
        setModelTableSanPham();
        loadTableSanPham();
        editMode(false);
        loadGioHang();
        lb.setVisible(false);
        txtTienKhachDua.setVisible(false);
        if (cbHinhThucThanhToan.getSelectedIndex() == 2) {
            lb.setVisible(true);
            txtTienKhachDua.setVisible(true);
            txtTienKhachDua.setText(helper.LongToString(hoadon.getSoTienNhanDuoc()));
        }
    }
    
    public void loadComboboxKhachHang() {
        String[] columns = new String[]{"Mã khách hàng", "Tên", "Điện thoại", "Địa chỉ", "Công nợ"};
        int[] widths = new int[]{80, 300, 120, 360, 150};
        this.comboboxKhachHang = new DetailedComboBox(columns, widths, 1);
        
        List<List<?>> tableData = new ArrayList<List<?>>();
        tableData.add(new ArrayList<>(
                Arrays.asList("KH01", "KHÁCH MỚI", "", "", "")));
        int index = 0;
        int i = 0;
        for (khachHang kh : dataKhachHang) {
            if (kh.getIdKhachHang().equals(hoadon.getIdKhachHang())) {
                index = i;
            }
            i++;
            tableData.add(new ArrayList<>(
                    Arrays.asList(kh.getIdKhachHang(), kh.getName(), kh.getSoDienThoai(), kh.getDiaChi(), helper.LongToString(kh.getNo()))));
        }
        comboboxKhachHang.setTableData(tableData);
        comboboxKhachHang.setFont(new Font("Arial", Font.ITALIC, 16));
        comboboxKhachHang.setSelectedIndex(index + 1);
        if (hoadon.getIdKhachHang().equals("KH01")) {
            comboboxKhachHang.setSelectedIndex(0);
        }
        comboboxKhachHang.setPopupAlignment(DetailedComboBox.Alignment.LEFT);
        comboboxKhachHang.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<?> rowData = comboboxKhachHang.getSelectedRow();
                hoadon.setIdKhachHang(rowData.get(0) + "");
                idKhachHang = rowData.get(0) + "";
            }
        });
        comboboxKhachHang.setVisible(true);
        panelComboKhachHang.add(comboboxKhachHang);
    }

    //set enable
    public void editMode(boolean mode) {
        // true : bật
        // false : tắt
        comboboxKhachHang.setEnabled(mode);
        txtGiaTriGiam.setEnabled(mode);
        txtBarcode.setEnabled(mode);
        txtTimKiemSanPham.setEnabled(mode);
        tableSanPham.setEnabled(mode);
        tableGioHang.setEnabled(mode);
        cbHinhThucThanhToan.setEnabled(mode);
        cbTuyChonGiamGia.setEnabled(mode);
        cbLoaiSanPham.setEnabled(mode);
        txtGhiChu.setEnabled(mode);
        btnEnter.setEnabled(mode);
        btnThemNhanh.setEnabled(mode);
        btnLuu.setEnabled(mode);
        btnSua.setEnabled(!mode);
        cbChonGia.setEnabled(mode);
        if (cbHinhThucThanhToan.getSelectedIndex() == 2) {
            txtTienKhachDua.setEnabled(mode);
        }
    }
    
    public void addKeyEnter() {
        InputMap inputMap = btnEnter.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "KEY_ENTER");
        btnEnter.getActionMap().put("KEY_ENTER", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                
                btnEnter.doClick();
                
            }
        });
    }
    
    public void addGioHang(chiTietHoaDon sp) {
        boolean isTonTai = true;
        
        if (sp == null) {
            if (JOptionPane.showConfirmDialog(null, "Sản phẩm chưa có. Thêm mới sản phẩm ?") == 0) {
                themNhanhSanPham();
                loadTableSanPham();
            }
            txtBarcode.setText("");
            txtBarcode.requestFocus();
        }
        if (dataChiTietHoaDon.size() == 0) {
            dataChiTietHoaDon.add(sp);
            loadGioHang();
            return;
        } else {
            for (chiTietHoaDon item : dataChiTietHoaDon) {
                if (item.getIdSanPham().equals(sp.getIdSanPham())) {
                    // đã tồn tại
                    isTonTai = true;
                    break;
                } else {
                    //chưa tồn tại
                    isTonTai = false;
                    
                }
            }
        }
        
        if (isTonTai == true) {
            for (chiTietHoaDon item : dataChiTietHoaDon) {
                if (item.getIdSanPham().equals(sp.getIdSanPham())) {
                    item.setSoLuong(item.getSoLuong() + 1);
                    break;
                }
            }
        } else {
            dataChiTietHoaDon.add(sp);
        }
        loadGioHang();
        loadGioHang();
    }
    
    public void loadGioHang() {
        if (cbChonGia.getSelectedIndex() == 0) {
            loadTableGioHangGiaBan();
        } else {
            loadTableGioHangGiaSi();
        }
    }
    
    public void enterBarcode() {
        if (txtBarcode.isFocusable() == false) {
            return;
        }
        String barcode = txtBarcode.getText();
        if (barcode.length() < 7) {
            return;
        }
        chiTietHoaDon sp = MDChiTietHoaDon.getSanPhamChiTietHoaDon(barcode);
        
        if (sp == null) {
            if (JOptionPane.showConfirmDialog(null, "Sản phẩm chưa có. Thêm mới sản phẩm ?") == 0) {
                themNhanhSanPham();
            }
            txtBarcode.setText("");
            txtBarcode.requestFocus();
            return;
        } else {
            if (sp.getTonKho() == 0) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng !");
                return;
            }
        }
        addGioHang(sp);
        txtBarcode.requestFocus();
    }
    
    public void loadTableGioHangGiaBan() {
        DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
        model.setRowCount(0);
        long thanhTienBanDau = 0;
        for (chiTietHoaDon item : dataChiTietHoaDon) {
            thanhTienBanDau += item.getThanhTien();
            model.addRow(new Object[]{
                item.getTenSanPham(),
                item.getDonViTinh(),
                item.getSoLuong(),
                helper.LongToString(item.getDonGia()),
                helper.LongToString(item.getThanhTien()),
                item.isTrangThai()
            });
        }
        
        tableGioHang.setModel(model);
        long tongTienFinal = thanhTienBanDau;
        if (!txtGiaTriGiam.getText().equals("") || !(helper.SoLong(txtGiaTriGiam.getText()) == 0)) {
            if (cbTuyChonGiamGia.getSelectedIndex() == 0) { // Giảm số tiền

                long giaTriGiam = helper.SoLong(txtGiaTriGiam.getText());
                tongTienFinal = thanhTienBanDau - giaTriGiam;
                
            } else {   // Giảm số phần trăm
                long giaTriPhanTram = helper.SoLong(txtGiaTriGiam.getText());
                long soTienGiam = thanhTienBanDau * giaTriPhanTram / 100;
                tongTienFinal = thanhTienBanDau - soTienGiam;
            }
        }
        soTienGoc = thanhTienBanDau;
        
        txtTongTien.setText(helper.LongToString(tongTienFinal));
    }
    
    public void loadTableGioHangGiaSi() {
        
        DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
        model.setRowCount(0);
        long thanhTienBanDau = 0;
        for (chiTietHoaDon item : dataChiTietHoaDon) {
            thanhTienBanDau += item.getThanhTienGiaSi();
            model.addRow(new Object[]{
                item.getTenSanPham(),
                item.getDonViTinh(),
                item.getSoLuong(),
                helper.LongToString(item.getGiaSi()),
                helper.LongToString(item.getThanhTienGiaSi()),
                item.isTrangThai()
            });
        }
        
        tableGioHang.setModel(model);
        long tongTienFinal = thanhTienBanDau;
        if (!txtGiaTriGiam.getText().equals("")) {
            if (cbTuyChonGiamGia.getSelectedIndex() == 0) { // Giảm số tiền

                long giaTriGiam = helper.SoLong(txtGiaTriGiam.getText());
                tongTienFinal = thanhTienBanDau - giaTriGiam;
                
            } else {   // Giảm số phần trăm
                long giaTriPhanTram = helper.SoLong(txtGiaTriGiam.getText());
                long soTienGiam = thanhTienBanDau * giaTriPhanTram / 100;
                tongTienFinal = thanhTienBanDau - soTienGiam;
            }
        }
        soTienGoc = thanhTienBanDau;
        
        txtTongTien.setText(helper.LongToString(tongTienFinal));
    }
    
    public void loadTableSanPham() {
        ArrayList<sanPham> data = MDSanPham.getDataToTableBanHang();
        DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
        model.setRowCount(0);
        for (sanPham item : data) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
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
        tableSanPham.setModel(model);
    }
    
    public void deleteGioHang() {
        DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
        for (int i = 0; i < tableGioHang.getRowCount(); i++) {
            boolean check = (boolean) tableGioHang.getValueAt(i, 5);
            if (check == false) {
                dataChiTietHoaDon.remove(i);
            }
        }
        loadGioHang();
    }
    
    public void setModelTableSanPham() {
        DefaultTableCellRenderer centerRendere = new DefaultTableCellRenderer();
        centerRendere.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableGioHang.getColumnCount() - 1; i++) {
            tableGioHang.getColumnModel().getColumn(i).setCellRenderer(centerRendere);
        }
        tableGioHang.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
        tableGioHang.setRowHeight(40);
        
        String[] column = {"Hình ảnh", "Mã", "Sản phẩm", "Mã vạch", "ĐVT", "Tồn kho", "Giá"};
        Object[][] rows = {};
        DefaultTableModel model = new DefaultTableModel(rows, column) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return ImageIcon.class;
                    
                    default:
                        return Object.class;
                }
            }
            
            public boolean isCellEditable(int rowIndex,
                    int columnIndex) {
                return false;
            }
        };
        tableSanPham.setModel(model);
        tableSanPham.setRowHeight(120);
        tableSanPham.setRowMargin(7);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableSanPham.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableSanPham.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableSanPham.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableSanPham.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableSanPham.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tableSanPham.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        tableSanPham.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
        tableSanPham.getColumnModel().getColumn(0).setPreferredWidth(90);
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(90);
        tableSanPham.getColumnModel().getColumn(2).setPreferredWidth(230);
        tableSanPham.getColumnModel().getColumn(3).setPreferredWidth(120);
        tableSanPham.getColumnModel().getColumn(4).setPreferredWidth(20);
        tableSanPham.getColumnModel().getColumn(5).setPreferredWidth(20);
        tableSanPham.getColumnModel().getColumn(6).setPreferredWidth(60);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBarcode = new javax.swing.JTextField();
        btnThemNhanh = new javax.swing.JButton();
        btnEnter = new javax.swing.JButton();
        txtTimKiemSanPham = new javax.swing.JTextField();
        cbLoaiSanPham = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSanPham = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbHinhThucThanhToan = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        btnLuu = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        cbTuyChonGiamGia = new javax.swing.JComboBox<>();
        txtGiaTriGiam = new javax.swing.JTextField();
        lb = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        panelComboKhachHang = new javax.swing.JPanel();
        btnSua1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGioHang = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        cbChonGia = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/scan-barcode.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        txtBarcode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtBarcode.setActionCommand("<Not Set>");

        btnThemNhanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/new.png"))); // NOI18N
        btnThemNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanhActionPerformed(evt);
            }
        });

        btnEnter.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnEnter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/new-item.png"))); // NOI18N
        btnEnter.setText("Enter");
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });

        txtTimKiemSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiemSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemSanPhamKeyReleased(evt);
            }
        });

        cbLoaiSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbLoaiSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Bia", "Nước ngọt", "Bánh", "Sữa", "Gia vị", "Đồ gia dụng" }));
        cbLoaiSanPham.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbLoaiSanPhamItemStateChanged(evt);
            }
        });

        tableSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Hình ảnh", "Mã", "Sản phẩm", "Đơn vị tính", "Tồn kho", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSanPham.setRowHeight(80);
        tableSanPham.setRowMargin(3);
        tableSanPham.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tableSanPhamFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableSanPhamFocusLost(evt);
            }
        });
        tableSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tableSanPhamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tableSanPhamMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableSanPhamMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tableSanPham);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 226, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnEnter)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThemNhanh)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnThemNhanh)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbLoaiSanPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("GIỎ HÀNG");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Khách hàng :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Hình thức :");

        cbHinhThucThanhToan.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        cbHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản", "Nợ" }));
        cbHinhThucThanhToan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbHinhThucThanhToanItemStateChanged(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Giá trị giảm :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("Ghi chú :");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Tổng tiền :");

        txtTongTien.setEditable(false);
        txtTongTien.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 102, 0));
        txtTongTien.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTongTien.setFocusable(false);

        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(102, 255, 51));
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });
        btnLuu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLuuKeyPressed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnSua.setForeground(new java.awt.Color(0, 153, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("Tùy chọn giảm giá :");

        cbTuyChonGiamGia.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        cbTuyChonGiamGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm số tiền", "Giảm phần trăm" }));
        cbTuyChonGiamGia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTuyChonGiamGiaItemStateChanged(evt);
            }
        });

        txtGiaTriGiam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaTriGiamKeyReleased(evt);
            }
        });

        lb.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lb.setText("Khách đưa :");

        txtTienKhachDua.setText("0");
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        panelComboKhachHang.setLayout(new java.awt.BorderLayout());

        btnSua1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnSua1.setForeground(new java.awt.Color(0, 153, 255));
        btnSua1.setText("In");
        btnSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel9))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(15, 15, 15)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(31, 31, 31)
                                    .addComponent(jLabel8))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbTuyChonGiamGia, 0, 177, Short.MAX_VALUE)
                                    .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                    .addComponent(panelComboKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(lb))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbHinhThucThanhToan, javax.swing.GroupLayout.Alignment.LEADING, 0, 172, Short.MAX_VALUE)
                                    .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTienKhachDua)))
                            .addComponent(jScrollPane4)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(panelComboKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(cbTuyChonGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(lb)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tableGioHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sản phẩm", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền", "Xóa ?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableGioHang.setFocusCycleRoot(true);
        tableGioHang.setFocusTraversalPolicyProvider(true);
        tableGioHang.setRowHeight(30);
        tableGioHang.setRowMargin(3);
        tableGioHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableGioHangFocusLost(evt);
            }
        });
        tableGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tableGioHangMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableGioHangMouseReleased(evt);
            }
        });
        tableGioHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableGioHangKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableGioHang);
        if (tableGioHang.getColumnModel().getColumnCount() > 0) {
            tableGioHang.getColumnModel().getColumn(0).setMinWidth(160);
            tableGioHang.getColumnModel().getColumn(0).setMaxWidth(160);
            tableGioHang.getColumnModel().getColumn(5).setMaxWidth(50);
        }

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel10.setText("Tùy chọn giá :");

        cbChonGia.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        cbChonGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giá lẻ", "Giá sĩ" }));
        cbChonGia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbChonGiaItemStateChanged(evt);
            }
        });
        cbChonGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbChonGiaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbChonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(cbChonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 785, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleParent(null);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        txtBarcode.requestFocus();
    }//GEN-LAST:event_jLabel2MouseClicked
    public void themNhanhSanPham() {
        frmThemNhanhSanPham frm = new frmThemNhanhSanPham(null, true);
        frm.setVisible(true);
        
    }
    private void btnThemNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanhActionPerformed
        themNhanhSanPham();
        loadTableSanPham();
    }//GEN-LAST:event_btnThemNhanhActionPerformed

    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
        enterBarcode();
        txtBarcode.setText("");
        txtBarcode.requestFocus();
    }//GEN-LAST:event_btnEnterActionPerformed

    private void tableSanPhamFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableSanPhamFocusGained

    }//GEN-LAST:event_tableSanPhamFocusGained

    private void tableSanPhamFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableSanPhamFocusLost
        tableSanPham.clearSelection();
    }//GEN-LAST:event_tableSanPhamFocusLost

    private void tableSanPhamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamMouseEntered

    }//GEN-LAST:event_tableSanPhamMouseEntered

    private void tableSanPhamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamMouseExited

    }//GEN-LAST:event_tableSanPhamMouseExited
    
    @SuppressWarnings("unchecked")
    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        tableSanPham.clearSelection();
        
        if (cbHinhThucThanhToan.getSelectedIndex() == 2 && comboboxKhachHang.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Thanh toán nợ cần có thông tin khách hàng !");
            return;
        }
        editMode(false);
        
        int hinhThucThanhToanCu = hoadon.getHinhThucThanhToan();
        long tienNoCu = hoadon.getTongTien() - hoadon.getSoTienNhanDuoc();
        String idKhachHangCu = hoadon.getIdKhachHang();

        // tạo hóa đơn mới
        if (cbHinhThucThanhToan.getSelectedIndex() == 2 && idKhachHang == "KH01") {
            JOptionPane.showMessageDialog(this, "Thanh toán nợ cần có thông tin khách hàng !");
            return;
        }
        String idNhanVien = hoadon.getIdNhanVien();
        String idKhachHang = this.idKhachHang;
        String thoiGian = hoadon.getThoiGian();
        int hinhThucThanhToan = cbHinhThucThanhToan.getSelectedIndex() + 1;
        /*
        1.tiền mặt
        2.chuyển khoản
        3.nợ
         */
        String ghiChu = txtGhiChu.getText().trim();
        long giamGia = soTienGoc - helper.SoLong(txtTongTien.getText());
        long tongtien = helper.SoLong(txtTongTien.getText());
        long tienKhachDua = 0;
//        if (txtTienKhachDua.getText() != "" || !txtTienKhachDua.getText().equals("")) {
//            tienKhachDua = helper.SoLong(txtTienKhachDua.getText());
//        }
        if (cbHinhThucThanhToan.getSelectedIndex() == 2) {
            tienKhachDua = helper.SoLong(txtTienKhachDua.getText());
        }
        hoaDon hoadon = new hoaDon(
                frmXemHoaDonBanHang.hoadon.getId(),
                idNhanVien,
                idKhachHang,
                thoiGian,
                hinhThucThanhToan,
                tienKhachDua,
                ghiChu,
                giamGia,
                tongtien,
                cbChonGia.getSelectedIndex(),
                frmXemHoaDonBanHang.hoadon.isTrangThai());
        MDHoaDon.capNhatHoaDon(hoadon,
                tienKhachDua,
                tableGioHang,
                dataChiTietHoaDonCu,
                dataChiTietHoaDonMoi,
                hinhThucThanhToanCu,
                tienNoCu,
                idKhachHangCu);
        JOptionPane.showMessageDialog(this, "CẬP NHẬT THÀNH CÔNG !");
        this.dispose();
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnLuuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLuuKeyPressed

    }//GEN-LAST:event_btnLuuKeyPressed

    private void tableGioHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableGioHangFocusLost
        tableGioHang.clearSelection();
    }//GEN-LAST:event_tableGioHangFocusLost

    private void tableGioHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMouseExited

    }//GEN-LAST:event_tableGioHangMouseExited

    private void tableGioHangMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMouseReleased
        deleteGioHang();
    }//GEN-LAST:event_tableGioHangMouseReleased

    private void tableGioHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableGioHangKeyReleased
        int rowCount = tableGioHang.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (cbChonGia.getSelectedIndex() == 0) { // giá lẻ
                dataChiTietHoaDon.get(i).setDonGia(helper.SoLong(tableGioHang.getValueAt(i, 3) + ""));
                dataChiTietHoaDon.get(i).setSoLuong(Integer.parseInt(tableGioHang.getValueAt(i, 2) + ""));
                if (dataChiTietHoaDon.get(i).getSoLuong() < 1 || dataChiTietHoaDon.get(i).getDonGia() < 0) {
                    dataChiTietHoaDon.remove(i);
                }
            } else {
                dataChiTietHoaDon.get(i).setGiaSi(helper.SoLong(tableGioHang.getValueAt(i, 3) + ""));
                dataChiTietHoaDon.get(i).setSoLuong(Integer.parseInt(tableGioHang.getValueAt(i, 2) + ""));
                if (dataChiTietHoaDon.get(i).getSoLuong() < 1 || dataChiTietHoaDon.get(i).getDonGia() < 0) {
                    dataChiTietHoaDon.remove(i);
                }
            }
            
        }
        
        loadGioHang();
    }//GEN-LAST:event_tableGioHangKeyReleased

    private void tableSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamMousePressed
        if (tableSanPham.getSelectedRows().length == 1 && evt.getClickCount() == 2) {
            int indexRow = tableSanPham.getSelectedRow();
            String id = tableSanPham.getValueAt(indexRow, 1) + "";
            chiTietHoaDon sp = MDChiTietHoaDon.getSanPhamChiTietHoaDonbyID(id);
//            if (sp.getTonKho() - sp.getSoLuong() == 0) {
//                JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng !");
//            } else {
            addGioHang(sp);
//            }
        }
    }//GEN-LAST:event_tableSanPhamMousePressed
    public void loadComboboxLoaiSanPham() {
        cbLoaiSanPham.removeAllItems();
        cbLoaiSanPham.addItem("Tất cả");
        for (String name : listLoaiSanPham) {
            cbLoaiSanPham.addItem(name);
        }
        cbLoaiSanPham.setSelectedIndex(0);
    }
    
    public void loadTableSanPhamKeyReleased(String keyword) {
        cbLoaiSanPham.setSelectedIndex(0);
        DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
        model.setRowCount(0);
        ArrayList<sanPham> dataSanPhamTable = MDSanPham.getDataToTableBanHang();
        for (sanPham item : dataSanPhamTable) {
            if (item.getIdSanPham().toLowerCase().contains(keyword.toLowerCase())
                    || item.getName().toLowerCase().contains(keyword.toLowerCase())
                    || helper.removeAccent(item.getIdSanPham().toLowerCase()).contains(keyword.toLowerCase())
                    || helper.removeAccent(item.getName().toLowerCase()).contains(keyword.toLowerCase())) {
                
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
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
        tableSanPham.setModel(model);
    }
    
    public void loadTableSanPham(String loaiSanPham) {
        DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
        model.setRowCount(0);
        ArrayList<sanPham> dataSanPhamTable = MDSanPham.getDataToTableBanHang();
        for (sanPham item : dataSanPhamTable) {
            if (loaiSanPham.equals("Tất cả") || item.getIdLoaiSanPham().equals(loaiSanPham)) {
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
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
        tableSanPham.setModel(model);
    }

    private void cbLoaiSanPhamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbLoaiSanPhamItemStateChanged
        String loaSanPham = cbLoaiSanPham.getSelectedItem() + "";
        loadTableSanPham(loaSanPham);
    }//GEN-LAST:event_cbLoaiSanPhamItemStateChanged

    private void txtTimKiemSanPhamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSanPhamKeyReleased
        loadTableSanPhamKeyReleased(txtTimKiemSanPham.getText());
    }//GEN-LAST:event_txtTimKiemSanPhamKeyReleased

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        editMode(true);
        txtBarcode.requestFocus();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtGiaTriGiamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaTriGiamKeyReleased
        if (cbTuyChonGiamGia.getSelectedIndex() == 0) {
            helper.setTextFieldMoney(txtGiaTriGiam);
        }
        if (cbChonGia.getSelectedIndex() == 0) {
            loadTableGioHangGiaBan();
        } else {
            loadTableGioHangGiaSi();
        }
    }//GEN-LAST:event_txtGiaTriGiamKeyReleased

    private void cbChonGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbChonGiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbChonGiaKeyReleased

    private void cbChonGiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbChonGiaItemStateChanged
        if (cbChonGia.getSelectedIndex() == 0) {
            loadTableGioHangGiaBan();
        } else {
            loadTableGioHangGiaSi();
        }
    }//GEN-LAST:event_cbChonGiaItemStateChanged

    private void cbTuyChonGiamGiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTuyChonGiamGiaItemStateChanged
        txtGiaTriGiam.setText("0");
        if (cbChonGia.getSelectedIndex() == 0) {
            loadTableGioHangGiaBan();
        } else {
            loadTableGioHangGiaSi();
        }
    }//GEN-LAST:event_cbTuyChonGiamGiaItemStateChanged
    
    @SuppressWarnings("unchecked")
    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        try {
            Hashtable map = new Hashtable();
            JasperReport jasper = JasperCompileManager.compileReport("D:\\gocHocTap\\app\\DU_AN_1\\build\\classes\\REPORT\\hoaDonBanHang.jrxml");
            map.put("tenCuaHang", admin.getTenCuaHang());
            map.put("diaChiCuaHang", admin.getDiaChi());
            map.put("idHoaDon", hoadon.getId());
            map.put("soDienThoaiCuaHang", admin.getSoDienThoai());
            map.put("soTienGiamGia", HELPER.helper.SoString(hoadon.getGiamGia()) + " đ");
            map.put("soTienThanhToan", HELPER.helper.SoString(hoadon.getTongTien()) + " đ");
            map.put("tenNhanVien", hoadon.getIdNhanVien());
            map.put("tenKhachHang", hoadon.getIdKhachHang());
            map.put("thoiGian", hoadon.getThoiGian());
            Connection con = HELPER.SQLhelper.getConnection();
            JasperPrint printer = JasperFillManager.fillReport(jasper, map, con);
            JasperViewer.viewReport(printer, false);
        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(frmXemHoaDonBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        JOptionPane.showMessageDialog(this, "Chức năng in hóa đơn đang hoàn thiện...");
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void cbHinhThucThanhToanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbHinhThucThanhToanItemStateChanged
        if (cbHinhThucThanhToan.getSelectedIndex() == 2) {
            txtTienKhachDua.setVisible(true);
            lb.setVisible(true);
            
        } else {
            txtTienKhachDua.setVisible(false);
            lb.setVisible(false);
        }
    }//GEN-LAST:event_cbHinhThucThanhToanItemStateChanged

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        helper.setTextFieldMoney(txtTienKhachDua);

    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                    
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmXemHoaDonBanHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmXemHoaDonBanHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmXemHoaDonBanHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmXemHoaDonBanHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmXemHoaDonBanHang dialog = new frmXemHoaDonBanHang(new javax.swing.JFrame(), true, acc, hoadon.getId());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnter;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnThemNhanh;
    private javax.swing.JComboBox<String> cbChonGia;
    private javax.swing.JComboBox<String> cbHinhThucThanhToan;
    private javax.swing.JComboBox<String> cbLoaiSanPham;
    private javax.swing.JComboBox<String> cbTuyChonGiamGia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb;
    private javax.swing.JPanel panelComboKhachHang;
    private javax.swing.JTable tableGioHang;
    private javax.swing.JTable tableSanPham;
    public javax.swing.JTextField txtBarcode;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGiaTriGiam;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTimKiemSanPham;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
