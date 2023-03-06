package VIEW;

import CLASS.ThongTinCuaHang;
import MODEL.MDChiTietHoaDon;
import CLASS.chiTietHoaDon;
import CLASS.hoaDon;
import CLASS.khachHang;
import CLASS.sanPham;
import COMPONENT.DetailedComboBox;
import HELPER.helper;
import MODEL.MDHoaDon;
import MODEL.MDKhachHang;
import MODEL.MDLoaiSanPham;
import MODEL.MDSanPham;
import MODEL.MDThongTinCuaHang;
import src.CLASS.Account;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
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
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class panelTaoHoaDonBanHang extends javax.swing.JPanel {

    private Connection con = HELPER.SQLhelper.getConnection();
    private ThongTinCuaHang admin = MDThongTinCuaHang.getThongTin();
    private DetailedComboBox comboboxKhachHang;
    public static Account acc;
    private ArrayList<chiTietHoaDon> dataChiTietHoaDon = new ArrayList<>();
    private ArrayList<String> listLoaiSanPham;
    private String idKhachHang = "KH01";
    private long soTienGoc;
    private String idHoaDonBanHang = "";

    public panelTaoHoaDonBanHang(Account account) {
        initComponents();
        this.acc = account;
        this.setName("Phiếu bán hàng");
        listLoaiSanPham = MDLoaiSanPham.getNames();
        // load combobox thông tin khách hàng
        loadComboboxKhachHang();

        UIManager.put("Table.consistentHomeEndKeyBehavior", true);
        // load comboBox loại sản phẩm
        loadComboboxLoaiSanPham();
        // add icon search 
        helper.addIconSearch(txtTimKiemSanPham);
        // add key listener cho nút quét mã vạch
        setKeyPress();
        //set model table sản phẩm
        setModelTableSanPham();
        loadTableSanPham();
        txtBarcode.requestFocus();
        lbtienkhachdua.setVisible(false);
        txtTienKhachDua.setVisible(false);
        dataChiTietHoaDon.clear();
        loadGioHang();
    }

    public void themNhanhSanPham() {
        frmThemNhanhSanPham frm = new frmThemNhanhSanPham((Frame) this.getParent().getParent().getParent().getParent().getParent().getParent(), true);

        frm.setVisible(true);
        loadTableSanPham();
    }

    public void loadComboboxLoaiSanPham() {
        cbLoaiSanPham.removeAllItems();
        cbLoaiSanPham.addItem("Tất cả");
        for (String name : listLoaiSanPham) {
            cbLoaiSanPham.addItem(name);
        }
        cbLoaiSanPham.setSelectedIndex(0);
    }

    public void setKeyPress() {
        // nút enter
        InputMap inputMap = btnEnter.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "KEY_ENTER");
        btnEnter.getActionMap().put("KEY_ENTER", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {

                if (txtBarcode.isFocusable()) {
                    btnEnter.doClick();
                }
            }
        });

        // nút F9
        InputMap f9 = btnThanhToan.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        f9.put(KeyStroke.getKeyStroke("F9"), "VK_F9");
        btnThanhToan.getActionMap().put("VK_F9", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {

                btnThanhToan.doClick();

            }
        });

        //nút F10
        InputMap f10 = btnLuuIn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        f10.put(KeyStroke.getKeyStroke("F10"), "VK_F10");
        btnLuuIn.getActionMap().put("VK_F10", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {

                btnLuuIn.doClick();

            }
        });
    }

    public void loadTableSanPhamKeyReleased(String keyword) {
        ArrayList<sanPham> dataSanPhamTable = MDSanPham.getDataToTableBanHang();
        cbLoaiSanPham.setSelectedIndex(0);
        DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
        model.setRowCount(0);

        for (sanPham item : dataSanPhamTable) {
            if (item.getIdSanPham().toLowerCase().contains(keyword.toLowerCase())
                    || item.getName().toLowerCase().contains(keyword.toLowerCase())
                    || item.getBarcode().toLowerCase().contains(keyword.toLowerCase())
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

    public void loadTableSanPham() {
        ArrayList<sanPham> dataSanPhamTable = MDSanPham.getDataToTableBanHang();
        DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
        model.setRowCount(0);
        for (sanPham item : dataSanPhamTable) {
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
        tableSanPham.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableSanPham.getColumnModel().getColumn(4).setPreferredWidth(30);
        tableSanPham.getColumnModel().getColumn(5).setPreferredWidth(30);
        tableSanPham.getColumnModel().getColumn(6).setPreferredWidth(60);

    }

    public void loadComboboxKhachHang() {
        String[] columns = new String[]{"Mã khách hàng", "Tên", "Điện thoại", "Địa chỉ", "Công nợ"};
        int[] widths = new int[]{80, 300, 120, 360, 150};
        this.comboboxKhachHang = new DetailedComboBox(columns, widths, 1);

        List<List<?>> tableData = new ArrayList<List<?>>();
        tableData.add(new ArrayList<>(
                Arrays.asList("KH01", "KHÁCH MỚI", "", "", "")));
        ArrayList<khachHang> dataKhachHang = MDKhachHang.getDataToComboBox();
        for (khachHang kh : dataKhachHang) {

            tableData.add(new ArrayList<>(
                    Arrays.asList(kh.getIdKhachHang(), kh.getName(), kh.getSoDienThoai(), kh.getDiaChi(), helper.LongToString(kh.getNo()))));
        }
        comboboxKhachHang.setTableData(tableData);
        comboboxKhachHang.setFont(new Font("Arial", Font.ITALIC, 16));
        comboboxKhachHang.setSelectedIndex(0);
        comboboxKhachHang.setPopupAlignment(DetailedComboBox.Alignment.LEFT);
        comboboxKhachHang.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<?> rowData = comboboxKhachHang.getSelectedRow();
                idKhachHang = rowData.get(0) + "";
            }
        });
        comboboxKhachHang.setVisible(true);
        panelComBoKhachHang.add(comboboxKhachHang);
    }

    public void reloadComboKhachHang() {

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        iconBarcode = new javax.swing.JLabel();
        txtBarcode = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnEnter = new javax.swing.JButton();
        txtTimKiemSanPham = new javax.swing.JTextField();
        cbLoaiSanPham = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSanPham = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGioHang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panelComBoKhachHang = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cbHinhThucThanhToan = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        btnLuuIn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        cbTuyChonGiamGia = new javax.swing.JComboBox<>();
        txtGiaTriGiam = new javax.swing.JTextField();
        lbtienkhachdua = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        cbChonGia = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        iconBarcode.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        iconBarcode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/scan-barcode.png"))); // NOI18N
        iconBarcode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconBarcodeMouseClicked(evt);
            }
        });

        txtBarcode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtBarcode.setActionCommand("<Not Set>");
        txtBarcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBarcodeKeyReleased(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/new.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
        cbLoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiSanPhamActionPerformed(evt);
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
        tableSanPham.setFocusable(false);
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
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 210, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(iconBarcode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnEnter)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(cbLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconBarcode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("GIỎ HÀNG");

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
        tableGioHang.setRowHeight(30);
        tableGioHang.setRowMargin(3);
        tableGioHang.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableGioHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableGioHangFocusLost(evt);
            }
        });
        tableGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tableGioHangMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableGioHangMousePressed(evt);
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
            tableGioHang.getColumnModel().getColumn(0).setMinWidth(200);
            tableGioHang.getColumnModel().getColumn(0).setMaxWidth(200);
            tableGioHang.getColumnModel().getColumn(1).setMaxWidth(120);
            tableGioHang.getColumnModel().getColumn(2).setMaxWidth(80);
            tableGioHang.getColumnModel().getColumn(5).setMaxWidth(50);
        }

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Khách hàng :");

        panelComBoKhachHang.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Hình thức :");

        cbHinhThucThanhToan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
        txtTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 102, 0));
        txtTongTien.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTongTien.setFocusable(false);

        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(0, 204, 0));
        btnThanhToan.setText("Thanh toán (F9)");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        btnThanhToan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnThanhToanKeyPressed(evt);
            }
        });

        btnLuuIn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnLuuIn.setForeground(new java.awt.Color(0, 102, 204));
        btnLuuIn.setText("Lưu & in (F10)");
        btnLuuIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuInActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 0, 51));
        jButton6.setText("Hủy (ESC)");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel10.setText("Tùy chọn giảm :");

        cbTuyChonGiamGia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbTuyChonGiamGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm số tiền", "Giảm phần trăm" }));
        cbTuyChonGiamGia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTuyChonGiamGiaItemStateChanged(evt);
            }
        });

        txtGiaTriGiam.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtGiaTriGiam.setText("0");
        txtGiaTriGiam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaTriGiamKeyReleased(evt);
            }
        });

        lbtienkhachdua.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lbtienkhachdua.setText("Tiền khách đưa :");

        txtTienKhachDua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThanhToan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLuuIn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addComponent(jScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(panelComBoKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(58, 58, 58)
                                .addComponent(jLabel5))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTongTien)
                                    .addComponent(cbTuyChonGiamGia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(lbtienkhachdua))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbHinhThucThanhToan, 0, 191, Short.MAX_VALUE)
                            .addComponent(txtGiaTriGiam)
                            .addComponent(txtTienKhachDua))))
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(panelComBoKhachHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(cbTuyChonGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbtienkhachdua)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnLuuIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        cbChonGia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbChonGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giá lẻ", "Giá sĩ" }));
        cbChonGia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbChonGiaItemStateChanged(evt);
            }
        });
        cbChonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChonGiaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("Tùy chọn giá :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbChonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jLabel9)
                    .addComponent(cbChonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        themNhanhSanPham();
        loadTableSanPham();
    }//GEN-LAST:event_jButton1ActionPerformed
    public void loadGioHang() {
        if (cbChonGia.getSelectedIndex() == 0) {
            loadTableGioHangGiaBan();
        } else {
            loadTableGioHangGiaSi();
        }
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

    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
        enterBarcode();
        txtBarcode.setText("");
        txtBarcode.requestFocus();
    }//GEN-LAST:event_btnEnterActionPerformed

    private void tableSanPhamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamMouseExited

    }//GEN-LAST:event_tableSanPhamMouseExited

    private void tableGioHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMouseExited

    }//GEN-LAST:event_tableGioHangMouseExited

    private void tableSanPhamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamMouseEntered

    }//GEN-LAST:event_tableSanPhamMouseEntered

    private void tableSanPhamFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableSanPhamFocusGained

    }//GEN-LAST:event_tableSanPhamFocusGained

    private void tableSanPhamFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableSanPhamFocusLost
        tableSanPham.clearSelection();
    }//GEN-LAST:event_tableSanPhamFocusLost

    private void tableGioHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableGioHangFocusLost
        tableGioHang.clearSelection();
    }//GEN-LAST:event_tableGioHangFocusLost

    private void btnThanhToanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnThanhToanKeyPressed

    }//GEN-LAST:event_btnThanhToanKeyPressed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        if (dataChiTietHoaDon.size() != 0) {
            if (comboboxKhachHang.getSelectedIndex() == 0) {
                idKhachHang = "KH01";
            }
            thanhToan();
        } else {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm !");
        }

    }//GEN-LAST:event_btnThanhToanActionPerformed
    public void setIDKhachHang(String id) {
        this.idKhachHang = id;
    }

    public void thanhToan() {
        if (cbHinhThucThanhToan.getSelectedIndex() == 2 && idKhachHang == "KH01") {
            JOptionPane.showMessageDialog(this, "Thanh toán nợ cần có thông tin khách hàng !");
            return;
        }
        String idNhanVien = acc.getIdNhanVien();
        String idKhachHang = this.idKhachHang;
        String thoiGian = helper.getDateTime();
        int hinhThucThanhToan = cbHinhThucThanhToan.getSelectedIndex() + 1;
        /*
        1.tiền mặt
        2.chuyển khoản
        3.nợ
         */
        String ghiChu = txtGhiChu.getText().trim();
        long giamGia = soTienGoc - helper.SoLong(txtTongTien.getText());
        long tongtien = helper.SoLong(txtTongTien.getText());
        long tienKhachDua = txtTienKhachDua.getText() == "" ? 0 : helper.SoLong(txtTienKhachDua.getText());
        hoaDon hoadon = new hoaDon(
                MDHoaDon.craeteID(),
                idNhanVien,
                idKhachHang,
                thoiGian,
                hinhThucThanhToan,
                0,
                ghiChu,
                giamGia,
                tongtien,
                cbChonGia.getSelectedIndex(),
                true);
        long soTienNhanDuoc = tongtien;
        if (cbHinhThucThanhToan.getSelectedIndex() == 2) {
            soTienNhanDuoc = helper.SoLong(txtTienKhachDua.getText());
        }
        hoadon.setSoTienNhanDuoc(soTienNhanDuoc);
        MDHoaDon.taoHoaDon(
                hoadon,
                tienKhachDua,
                tableGioHang,
                dataChiTietHoaDon);
        idHoaDonBanHang = hoadon.getId();
        JOptionPane.showMessageDialog(this, "THANH TOÁN THÀNH CÔNG !");
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
        } catch (AWTException ex) {
            Logger.getLogger(panelTaoHoaDonBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
        } catch (AWTException ex) {
            Logger.getLogger(panelTaoHoaDonBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void iconBarcodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconBarcodeMouseClicked
        txtBarcode.requestFocus();
    }//GEN-LAST:event_iconBarcodeMouseClicked
    public void deleteGioHang() {
        DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
        for (int i = 0; i < tableGioHang.getRowCount(); i++) {
            boolean check = (boolean) tableGioHang.getValueAt(i, 5);
            if (check == false) {
//                model.removeRow(i);
                dataChiTietHoaDon.remove(i);
            }
        }
        loadGioHang();
    }
    private void tableGioHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMousePressed
//        deleteGioHang();

    }//GEN-LAST:event_tableGioHangMousePressed

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
    public void loadTableSanPham(String loaiSanPham) {
        ArrayList<sanPham> dataSanPhamTable = MDSanPham.getDataToTableBanHang();
        DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
        model.setRowCount(0);
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

    private void cbChonGiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbChonGiaItemStateChanged
        loadGioHang();
    }//GEN-LAST:event_cbChonGiaItemStateChanged

    private void cbChonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbChonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbChonGiaActionPerformed

    private void txtGiaTriGiamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaTriGiamKeyReleased
        if (cbTuyChonGiamGia.getSelectedIndex() == 0) {
            helper.setTextFieldMoney(txtGiaTriGiam);
        }
        loadGioHang();
    }//GEN-LAST:event_txtGiaTriGiamKeyReleased

    private void cbTuyChonGiamGiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTuyChonGiamGiaItemStateChanged
        txtGiaTriGiam.setText("0");
        loadGioHang();
    }//GEN-LAST:event_cbTuyChonGiamGiaItemStateChanged

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        helper.setTextFieldMoney(txtTienKhachDua);
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void cbHinhThucThanhToanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbHinhThucThanhToanItemStateChanged
        if (cbHinhThucThanhToan.getSelectedIndex() == 2) {
            lbtienkhachdua.setVisible(true);
            txtTienKhachDua.setVisible(true);
        } else {
            lbtienkhachdua.setVisible(false);
            txtTienKhachDua.setVisible(false);
        }
    }//GEN-LAST:event_cbHinhThucThanhToanItemStateChanged

    private void btnLuuInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuInActionPerformed
        if (dataChiTietHoaDon.size() != 0) {
            if (comboboxKhachHang.getSelectedIndex() == 0) {
                idKhachHang = "KH01";
            }
            luuIn();

        } else {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm !");
        }

//        JOptionPane.showMessageDialog(this, "Chức năng in hóa đơn đang hoàn thiện...");

    }//GEN-LAST:event_btnLuuInActionPerformed
    public void luuIn() {
        if (cbHinhThucThanhToan.getSelectedIndex() == 2 && idKhachHang == "KH01") {
            JOptionPane.showMessageDialog(this, "Thanh toán nợ cần có thông tin khách hàng !");
            return;
        }
        String idNhanVien = acc.getIdNhanVien();
        String idKhachHang = this.idKhachHang;
        String thoiGian = helper.getDateTime();
        int hinhThucThanhToan = cbHinhThucThanhToan.getSelectedIndex() + 1;
        /*
        1.tiền mặt
        2.chuyển khoản
        3.nợ
         */
        String ghiChu = txtGhiChu.getText().trim();
        long giamGia = soTienGoc - helper.SoLong(txtTongTien.getText());
        long tongtien = helper.SoLong(txtTongTien.getText());
        long tienKhachDua = txtTienKhachDua.getText() == "" ? 0 : helper.SoLong(txtTienKhachDua.getText());
        hoaDon hoadon = new hoaDon(
                MDHoaDon.craeteID(),
                idNhanVien,
                idKhachHang,
                thoiGian,
                hinhThucThanhToan,
                0,
                ghiChu,
                giamGia,
                tongtien,
                cbChonGia.getSelectedIndex(),
                true);
        long soTienNhanDuoc = tongtien;
        if (cbHinhThucThanhToan.getSelectedIndex() == 2) {
            soTienNhanDuoc = helper.SoLong(txtTienKhachDua.getText());
        }
        hoadon.setSoTienNhanDuoc(soTienNhanDuoc);
        MDHoaDon.taoHoaDon(
                hoadon,
                tienKhachDua,
                tableGioHang,
                dataChiTietHoaDon);
        idHoaDonBanHang = hoadon.getId();
        JOptionPane.showMessageDialog(this, "THANH TOÁN THÀNH CÔNG !");
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
        } catch (AWTException ex) {
            Logger.getLogger(panelTaoHoaDonBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
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

            JasperPrint printer = JasperFillManager.fillReport(jasper, map, con);

            JasperViewer.viewReport(printer, false);
        } catch (Exception e) {
            return;
        }

    }
    private void txtBarcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBarcodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBarcodeKeyReleased

    private void cbLoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbLoaiSanPhamActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnter;
    private javax.swing.JButton btnLuuIn;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JComboBox<String> cbChonGia;
    private javax.swing.JComboBox<String> cbHinhThucThanhToan;
    private javax.swing.JComboBox<String> cbLoaiSanPham;
    private javax.swing.JComboBox<String> cbTuyChonGiamGia;
    private javax.swing.JLabel iconBarcode;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel lbtienkhachdua;
    private javax.swing.JPanel panelComBoKhachHang;
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
