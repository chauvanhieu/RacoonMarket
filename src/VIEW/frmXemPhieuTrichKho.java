package VIEW;

import CLASS.chiTietHoaDon;
import CLASS.hoaDonTrichKho;
import CLASS.sanPham;
import HELPER.helper;
import MODEL.MDChiTietHoaDon;
import MODEL.MDLoaiSanPham;
import MODEL.MDSanPham;
import MODEL.MDTrichKho;
import java.awt.AWTException;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

public class frmXemPhieuTrichKho extends javax.swing.JDialog {

    private ArrayList<chiTietHoaDon> dataChiTietHoaDon;
    private ArrayList<String> listLoaiSanPham = MDLoaiSanPham.getNames();
    public static String idHoaDon;
    private hoaDonTrichKho hoadon;
    private ArrayList<chiTietHoaDon> dataChiTietHoaDonCu;

    public frmXemPhieuTrichKho(java.awt.Frame parent, boolean modal, String idhoadon) {
        initComponents();
        this.hoadon = MDTrichKho.getHoaDon(idhoadon);
        dataChiTietHoaDon = MDChiTietHoaDon.getChiTietHoaDonTrichKho(idhoadon);
        dataChiTietHoaDonCu = (ArrayList<chiTietHoaDon>) dataChiTietHoaDon.clone();
        UIManager.put("Table.consistentHomeEndKeyBehavior", true);

        helper.addIconSearch(txtTimKiemSanPham);
        // add key listener cho nút quét mã vạch
        setKeyPress();
        //set model table sản phẩm
        setModelTableSanPham();
        loadTableSanPham();
        txtBarcode.requestFocus();

        loadGioHang();
        editMode(false);
    }

    public void themNhanhSanPham() {
        frmThemNhanhSanPham frm = new frmThemNhanhSanPham((Frame) this.getParent().getParent().getParent().getParent().getParent().getParent(), true);

        frm.setVisible(true);
        loadTableSanPham();
    }

    public void loadGioHang() {
        DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
        model.setRowCount(0);
        for (chiTietHoaDon item : dataChiTietHoaDon) {
            model.addRow(new Object[]{
                item.getTenSanPham(),
                item.getDonViTinh(),
                item.getSoLuong(),
                helper.LongToString(item.getGiaNhap()),
                helper.LongToString(item.getThanhTienGiaNhap()),
                item.isTrangThai()
            });
        }
        tableGioHang.setModel(model);
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

    }

    public void editMode(boolean mode) {
        txtBarcode.setEnabled(mode);
        txtGhiChu.setEnabled(mode);
        txtTimKiemSanPham.setEnabled(mode);
        tableGioHang.setEnabled(mode);
        tableSanPham.setEnabled(mode);
        cbLoaiSanPham.setEnabled(mode);
        btnEnter.setEnabled(mode);
        btnLuu.setEnabled(mode);
        btnThemSanPham.setEnabled(mode);
        btnSua.setEnabled(!mode);
        tableSanPham.clearSelection();
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

                ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/IMAGE/" + (item.getHinhAnh().equals("") ? "empty.png" : item.getHinhAnh()))).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        iconBarcode = new javax.swing.JLabel();
        txtBarcode = new javax.swing.JTextField();
        btnThemSanPham = new javax.swing.JButton();
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
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnLuu = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

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

        btnThemSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/new.png"))); // NOI18N
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
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
                        .addGap(0, 247, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(iconBarcode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnEnter)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThemSanPham))
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
                    .addComponent(btnThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("GIỎ HÀNG");

        tableGioHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sản phẩm", "Đơn vị tính", "Số lượng", "Giá nhập", "Thành tiền", "Xóa ?"
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

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("Ghi chú :");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(0, 204, 0));
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
        btnSua.setForeground(new java.awt.Color(255, 0, 51));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addComponent(jScrollPane4))
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

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
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
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

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void iconBarcodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconBarcodeMouseClicked
        txtBarcode.requestFocus();
    }//GEN-LAST:event_iconBarcodeMouseClicked

    private void txtBarcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBarcodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBarcodeKeyReleased

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        themNhanhSanPham();
        loadTableSanPham();
    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
        enterBarcode();
        txtBarcode.setText("");
        txtBarcode.requestFocus();
    }//GEN-LAST:event_btnEnterActionPerformed
    public void taoHoaDon() {
        hoaDonTrichKho hoadon = new hoaDonTrichKho(
                MDTrichKho.createID(),
                this.hoadon.getNhanVien(),
                helper.getDateTime(),
                txtGhiChu.getText().trim()
        );
        MDTrichKho.trichKho(hoadon, dataChiTietHoaDon);
        JOptionPane.showMessageDialog(this, "Đã trích kho sản phẩm !");
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
        } catch (AWTException ex) {
            java.util.logging.Logger.getLogger(panelTrichKhoSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    private void txtTimKiemSanPhamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSanPhamKeyReleased
        loadTableSanPhamKeyReleased(txtTimKiemSanPham.getText());
    }//GEN-LAST:event_txtTimKiemSanPhamKeyReleased

    private void cbLoaiSanPhamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbLoaiSanPhamItemStateChanged
        String loaSanPham = cbLoaiSanPham.getSelectedItem() + "";
        loadTableSanPham(loaSanPham);
    }//GEN-LAST:event_cbLoaiSanPhamItemStateChanged

    private void cbLoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbLoaiSanPhamActionPerformed

    private void tableSanPhamFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableSanPhamFocusGained

    }//GEN-LAST:event_tableSanPhamFocusGained

    private void tableSanPhamFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableSanPhamFocusLost
        tableSanPham.clearSelection();
    }//GEN-LAST:event_tableSanPhamFocusLost

    private void tableSanPhamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamMouseEntered

    }//GEN-LAST:event_tableSanPhamMouseEntered

    private void tableSanPhamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamMouseExited

    }//GEN-LAST:event_tableSanPhamMouseExited

    private void tableSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamMousePressed
        if (tableSanPham.getSelectedRows().length == 1 && evt.getClickCount() == 2) {
            int indexRow = tableSanPham.getSelectedRow();
            String id = tableSanPham.getValueAt(indexRow, 1) + "";
            chiTietHoaDon sp = MDChiTietHoaDon.getSanPhamChiTietHoaDonbyID(id);

            addGioHang(sp);

        }
    }//GEN-LAST:event_tableSanPhamMousePressed

    private void tableGioHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableGioHangFocusLost
        tableGioHang.clearSelection();
    }//GEN-LAST:event_tableGioHangFocusLost

    private void tableGioHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMouseExited

    }//GEN-LAST:event_tableGioHangMouseExited

    private void tableGioHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMousePressed

    }//GEN-LAST:event_tableGioHangMousePressed
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
    private void tableGioHangMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMouseReleased
        deleteGioHang();
    }//GEN-LAST:event_tableGioHangMouseReleased

    private void tableGioHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableGioHangKeyReleased
        int rowCount = tableGioHang.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            dataChiTietHoaDon.get(i).setGiaNhap(helper.SoLong(tableGioHang.getValueAt(i, 3) + ""));
            dataChiTietHoaDon.get(i).setSoLuong(Integer.parseInt(tableGioHang.getValueAt(i, 2) + ""));
            if (dataChiTietHoaDon.get(i).getSoLuong() < 1 || dataChiTietHoaDon.get(i).getGiaNhap() < 0) {
                dataChiTietHoaDon.remove(i);
            }
        }

        loadGioHang();
    }//GEN-LAST:event_tableGioHangKeyReleased

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed

        editMode(false);
        MDTrichKho.updateHoaDonTrichKho(hoadon.getId(), dataChiTietHoaDonCu, dataChiTietHoaDon);
        JOptionPane.showMessageDialog(this, "Cập nhật thành công !");
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnLuuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLuuKeyPressed

    }//GEN-LAST:event_btnLuuKeyPressed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        editMode(true);
        dataChiTietHoaDonCu = MDChiTietHoaDon.getChiTietHoaDonTrichKho(hoadon.getId());
    }//GEN-LAST:event_btnSuaActionPerformed

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
            java.util.logging.Logger.getLogger(frmXemPhieuTrichKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmXemPhieuTrichKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmXemPhieuTrichKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmXemPhieuTrichKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmXemPhieuTrichKho dialog = new frmXemPhieuTrichKho(new javax.swing.JFrame(), true, idHoaDon);
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
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JComboBox<String> cbLoaiSanPham;
    private javax.swing.JLabel iconBarcode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tableGioHang;
    private javax.swing.JTable tableSanPham;
    public javax.swing.JTextField txtBarcode;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtTimKiemSanPham;
    // End of variables declaration//GEN-END:variables
}
