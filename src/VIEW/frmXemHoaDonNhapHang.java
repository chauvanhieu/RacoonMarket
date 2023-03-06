/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package VIEW;

import CLASS.chiTietHoaDon;
import CLASS.hoaDonNhapHang;
import CLASS.nhaCungCap;
import CLASS.sanPham;
import COMPONENT.DetailedComboBox;
import HELPER.helper;
import MODEL.MDChiTietHoaDon;
import MODEL.MDNhaCungCap;
import MODEL.MDNhapHang;
import MODEL.MDSanPham;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class frmXemHoaDonNhapHang extends javax.swing.JDialog {

    private ArrayList<chiTietHoaDon> dataChiTietHoaDon = null;
    public static ArrayList<chiTietHoaDon> dataChiTietHoaDonCu = null;
    private ArrayList<sanPham> dataSanPhamTable = MDSanPham.getDataToTableBanHang();
    private ArrayList<sanPham> dataSanPhamTimKiem = new ArrayList<sanPham>();
    private String idNhaCungCap = "";
    private DetailedComboBox comboBoxNhaCungCap;
    private ArrayList<nhaCungCap> dataNhaCungCap = MDNhaCungCap.getAll();
    private hoaDonNhapHang hoadon;
    private hoaDonNhapHang hoadonCu;
    public static String idhoadon;

    public frmXemHoaDonNhapHang(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        frmXemHoaDonTraHang.idhoadon = id;
        this.hoadon = MDNhapHang.getHoaDon(id);
        hoadonCu = hoadon;
        dataChiTietHoaDon = MDChiTietHoaDon.getChiTietHoaDonNhapHang(hoadon.getId());
        dataChiTietHoaDonCu = dataChiTietHoaDon;
        initComponents();
        setModelTableSanPham();
        loadComboBoxNhaCungCap();
        setKeyPress();
        helper.addIconSearch(txtTimKiem);
        loadDuLieu();
        editMode(false);
    }

    public void loadDuLieu() {
        txtTongTien.setText(helper.SoString(hoadonCu.getTongTien()));
        txtTienThanhToan.setText(helper.SoString(hoadonCu.getThanhToan()));
        txtConLai.setText(helper.SoString(hoadonCu.getTongTien() - hoadonCu.getThanhToan()));

        int index = 0;
        for (int i = 0; i < dataNhaCungCap.size(); i++) {
            if (hoadon.getIdNhaCungCap().equals(dataNhaCungCap.get(i).getIdNhaCungCap())) {
                index = i;
            }
        }
        comboBoxNhaCungCap.setSelectedIndex(index);
        dataChiTietHoaDon = dataChiTietHoaDonCu;
        loadGioHang(dataChiTietHoaDon);
    }

    public void editMode(boolean mode) {
        txtBarcode.setEnabled(mode);
        txtGhiChu.setEnabled(mode);
        txtTienThanhToan.setEnabled(mode);
        txtTimKiem.setEnabled(mode);
        tableGioHang.setEnabled(mode);
        tableSanPham.setEnabled(mode);
        btnEnter.setEnabled(mode);
        btnLuu.setEnabled(mode);
        btnSua.setEnabled(!mode);
        tableSanPham.clearSelection();

    }

    public void loadGioHang(ArrayList<chiTietHoaDon> data) {
        DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
        model.setRowCount(0);
        long thanhTienBanDau = 0;
        for (chiTietHoaDon item : data) {
            thanhTienBanDau += item.getGiaNhap() * item.getSoLuongNhapHang();

            model.addRow(new Object[]{
                item.getTenSanPham(),
                item.getDonViTinh(),
                item.getSoLuongNhapHang(),
                helper.LongToString(item.getGiaNhap()),
                helper.LongToString(item.getGiaNhap() * item.getSoLuongNhapHang()),
                item.isTrangThai()
            });
        }

        tableGioHang.setModel(model);

        txtTongTien.setText(helper.LongToString(thanhTienBanDau));

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

    public void loadComboBoxNhaCungCap() {
        String[] columns1 = new String[]{"Mã", "Tên", "Điện thoại", "Công nợ"};
        int[] widths1 = new int[]{80, 380, 120, 130};
        this.comboBoxNhaCungCap = new DetailedComboBox(columns1, widths1, 1);

        List<List<?>> tableDataNhaCungCap = new ArrayList<List<?>>();
        for (nhaCungCap ncc : dataNhaCungCap) {
            tableDataNhaCungCap.add(new ArrayList<>(
                    Arrays.asList(ncc.getIdNhaCungCap(), ncc.getName(), ncc.getSoDienThoai(), HELPER.helper.LongToString(ncc.getCongNo()))));
        }

        comboBoxNhaCungCap.setTableData(tableDataNhaCungCap);
        comboBoxNhaCungCap.setFont(new Font("Arial", Font.ITALIC, 14));
        comboBoxNhaCungCap.setSelectedIndex(-1);
        comboBoxNhaCungCap.setPopupAlignment(DetailedComboBox.Alignment.LEFT);
        comboBoxNhaCungCap.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<?> rowData = comboBoxNhaCungCap.getSelectedRow();
                idNhaCungCap = rowData.get(0) + "";
                loadTableSanPham(idNhaCungCap);
                dataChiTietHoaDon = new ArrayList<chiTietHoaDon>();
                loadGioHang();
            }
        });
        comboBoxNhaCungCap.setVisible(true);
        panelComboBoxNhaCungCap.add(comboBoxNhaCungCap);
    }

    public void loadGioHang() {
        DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
        model.setRowCount(0);
        long thanhTienBanDau = 0;
        for (chiTietHoaDon item : dataChiTietHoaDon) {
            thanhTienBanDau += item.getGiaNhap() * item.getSoLuongNhapHang();

            model.addRow(new Object[]{
                item.getTenSanPham(),
                item.getDonViTinh(),
                item.getSoLuongNhapHang(),
                helper.LongToString(item.getGiaNhap()),
                helper.LongToString(item.getGiaNhap() * item.getSoLuongNhapHang()),
                item.isTrangThai()
            });
        }

        tableGioHang.setModel(model);

        txtTongTien.setText(helper.LongToString(thanhTienBanDau));
        txtConLai.setText(helper.LongToString(helper.SoLong(txtTongTien.getText()) - helper.SoLong(txtTienThanhToan.getText())));
        if (helper.SoLong(txtTienThanhToan.getText()) > helper.SoLong(txtTongTien.getText())) {
            txtTienThanhToan.setText(txtTongTien.getText());
            txtConLai.setText(helper.LongToString(helper.SoLong(txtTongTien.getText()) - helper.SoLong(txtTienThanhToan.getText())));
        }
    }

    public void addGioHang(chiTietHoaDon sp) {

        boolean isTonTai = true;

        if (sp == null) {
            JOptionPane.showMessageDialog(this, "CHƯA CÓ SẢN PHẨM NÀY");
            txtBarcode.setText("");
            txtBarcode.requestFocus();
            return;
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
                    item.setSoLuongNhapHang(item.getSoLuongNhapHang() + 1);
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
        if (idNhaCungCap.equals("")) {
            return;
        }
        String barcode = txtBarcode.getText();
        if (barcode.length() < 7) {
            return;
        }

        chiTietHoaDon sp = MDChiTietHoaDon.getSanPhamChiTietHoaDon(barcode, idNhaCungCap);

        if (sp == null) {
            JOptionPane.showMessageDialog(this, "CHƯA CÓ SẢN PHẨM NÀY");

            txtBarcode.setText("");
            txtBarcode.requestFocus();
            return;
        }

        addGioHang(sp);
        txtBarcode.requestFocus();
    }

    public void loadTableSanPham(String idNhaCungCap) {

        DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
        model.setRowCount(0);
        dataSanPhamTimKiem = new ArrayList<sanPham>();
        for (sanPham item : dataSanPhamTable) {
            if (item.getIdNhaCungCap().equals(idNhaCungCap)) {
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                model.addRow(new Object[]{
                    imageIcon,
                    item.getIdSanPham(),
                    item.getName(),
                    item.getBarcode(),
                    item.getIdDonViTinh(),
                    item.getSoLuong(),
                    helper.LongToString(item.getGiaNhap())
                });
                dataSanPhamTimKiem.add(item);
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

    public void loadResultSanPham(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
        model.setRowCount(0);
        for (sanPham item : dataSanPhamTimKiem) {
            String rs = item.getBarcode() + " " + item.getIdSanPham() + " " + item.getName();

            if (rs.contains(keyword)
                    || rs.toLowerCase().contains(keyword.toLowerCase())
                    || helper.removeAccent(rs.toLowerCase()).contains(helper.removeAccent(keyword.toLowerCase()))) {
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                model.addRow(new Object[]{
                    imageIcon,
                    item.getIdSanPham(),
                    item.getName(),
                    item.getBarcode(),
                    item.getIdDonViTinh(),
                    item.getSoLuong(),
                    helper.LongToString(item.getGiaNhap())
                });
            }
        }
        tableSanPham.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGioHang = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnLuu = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        txtConLai = new javax.swing.JTextField();
        txtTienThanhToan = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtBarcode = new javax.swing.JTextField();
        btnEnter = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSanPham = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        panelComboBoxNhaCungCap = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("GIỎ HÀNG");

        tableGioHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sản phẩm", "Đơn vị tính", "Số lượng", "Giá nhập", "Thành tiền", "Xóa ?"
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

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Tổng tiền :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel10.setText("Thanh toán :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel11.setText("Còn lại :");

        txtTongTien.setEditable(false);
        txtTongTien.setFocusable(false);

        txtConLai.setEditable(false);
        txtConLai.setFocusable(false);

        txtTienThanhToan.setText("0");
        txtTienThanhToan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienThanhToanKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTienThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtConLai, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtConLai, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLuu, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/scan-barcode.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        txtBarcode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtBarcode.setActionCommand("<Not Set>");

        btnEnter.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnEnter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/new-item.png"))); // NOI18N
        btnEnter.setText("Enter");
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Nhà cung cấp :");

        panelComboBoxNhaCungCap.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelComboBoxNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEnter)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(panelComboBoxNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableGioHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableGioHangFocusLost
        tableGioHang.clearSelection();
    }//GEN-LAST:event_tableGioHangFocusLost

    private void tableGioHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMouseExited

    }//GEN-LAST:event_tableGioHangMouseExited

    private void tableGioHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMousePressed

    }//GEN-LAST:event_tableGioHangMousePressed

    private void tableGioHangMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMouseReleased
        deleteGioHang();
    }//GEN-LAST:event_tableGioHangMouseReleased

    private void tableGioHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableGioHangKeyReleased
        int rowCount = tableGioHang.getRowCount();

        for (int i = 0; i < rowCount; i++) {

            dataChiTietHoaDon.get(i).setSoLuongNhapHang(Integer.parseInt(tableGioHang.getValueAt(i, 2) + ""));
            if (dataChiTietHoaDon.get(i).getSoLuongNhapHang() < 1) {
                dataChiTietHoaDon.remove(i);
            }

        }

        loadGioHang();
    }//GEN-LAST:event_tableGioHangKeyReleased

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        editMode(false);
        // thanh toán
        hoaDonNhapHang hoadon = new hoaDonNhapHang(
                hoadonCu.getId(),
                hoadonCu.getThoiGian(),
                hoadonCu.getIdNhanVien(),
                idNhaCungCap,
                txtGhiChu.getText().trim(),
                helper.SoLong(txtTongTien.getText()),
                helper.SoLong(txtTienThanhToan.getText()),
                hoadonCu.isTrangThai());
        MDNhapHang.update(hoadon, hoadonCu, dataChiTietHoaDonCu, dataChiTietHoaDon);
        JOptionPane.showMessageDialog(this, "Cập nhật thành công !");
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnLuuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLuuKeyPressed

    }//GEN-LAST:event_btnLuuKeyPressed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        editMode(true);
        dataChiTietHoaDonCu = MDChiTietHoaDon.getChiTietHoaDonNhapHang(hoadon.getId());
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtTienThanhToanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienThanhToanKeyReleased
        helper.setTextFieldMoney(txtTienThanhToan);

        txtConLai.setText(helper.LongToString(helper.SoLong(txtTongTien.getText()) - helper.SoLong(txtTienThanhToan.getText())));
        if (helper.SoLong(txtTienThanhToan.getText()) > helper.SoLong(txtTongTien.getText())) {
            txtTienThanhToan.setText(txtTongTien.getText());
            txtConLai.setText(helper.LongToString(helper.SoLong(txtTongTien.getText()) - helper.SoLong(txtTienThanhToan.getText())));
        }
    }//GEN-LAST:event_txtTienThanhToanKeyReleased

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        txtBarcode.requestFocus();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
        enterBarcode();
        txtBarcode.setText("");
        txtBarcode.requestFocus();
    }//GEN-LAST:event_btnEnterActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        loadResultSanPham(txtTimKiem.getText());
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void tableSanPhamFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableSanPhamFocusGained

    }//GEN-LAST:event_tableSanPhamFocusGained

    private void tableSanPhamFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableSanPhamFocusLost

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
            java.util.logging.Logger.getLogger(frmXemHoaDonNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmXemHoaDonNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmXemHoaDonNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmXemHoaDonNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmXemHoaDonNhapHang dialog = new frmXemHoaDonNhapHang(new javax.swing.JFrame(), true, idhoadon);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel panelComboBoxNhaCungCap;
    private javax.swing.JTable tableGioHang;
    private javax.swing.JTable tableSanPham;
    public javax.swing.JTextField txtBarcode;
    private javax.swing.JTextField txtConLai;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtTienThanhToan;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
