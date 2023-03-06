package VIEW;

import CLASS.nhanVien;
import CONTROLLER.CTRLNhanVien;
import MODEL.MDHoaDon;
import MODEL.MDNhanVien;
import static VIEW.PanelThongKeTongHop.acc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class frmEditorNhanVien extends javax.swing.JDialog {
    
    private nhanVien nv;
    public static String idNhanVien;
    
    public frmEditorNhanVien(java.awt.Frame parent, boolean modal, String idNhanVien) {
        // Sữa nhân viên

        super(parent, modal);
        this.idNhanVien = idNhanVien;
        nv = MDNhanVien.getNhanVien(idNhanVien);
        initComponents();
        String option = "edit";
        setThongTinNhanVien();
        btnLuu.setEnabled(false);
        setBtnLuu(option);
        MDHoaDon.showHoaDonCuaNhanVien(idNhanVien, tableDanhSachHoaDonBanHang);
        HELPER.helper.setTableTextCenterFullColumn(tableDanhSachHoaDonBanHang);
    }
    
    public frmEditorNhanVien(java.awt.Frame parent, boolean modal) {
        // Thêm nhân viên

        super(parent, modal);
        initComponents();
        String option = "add";
        btnLuu.setVisible(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        txtIDNhanVien.setText(MDNhanVien.createID());
        showHide(true);
        dateNgaySinh.setDate(HELPER.helper.LayNgayDate("1-1-2000", "dd-MM-yyyy"));
        dateNgayVaoLam.setDate(new Date());
        setBtnLuu(option);
    }
    
    public void setBtnLuu(String option) {
        if (option == "edit") {
            btnLuu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showHide(false);
                    suaNhanVien();
                }
            });
        } else if (option == "add") {
            btnLuu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showHide(false);
                    themNhanVien();
                }
            });
        }
    }
    
    public void themNhanVien() {
        String id = txtIDNhanVien.getText();
        String name = txtTenNhanVien.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String ghiChu = txtGhiChu.getText().trim();
        String ngaySinh = HELPER.helper.LayNgayString(dateNgaySinh.getDate(), "yyyy-MM-dd");
        String ngayVaoLam = HELPER.helper.LayNgayString(dateNgayVaoLam.getDate(), "yyyy-MM-dd");
        boolean gioiTinh;
        boolean trangThai;
        long Luong = HELPER.helper.SoLong(txtLuong.getText());
        if (cbGioiTinh.getSelectedIndex() == 0) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        if (cbTrangThai.getSelectedIndex() == 0) {
            trangThai = true;
        } else {
            trangThai = false;
        }
        
        CTRLNhanVien.checkAdd(new nhanVien(id, name, soDienThoai, diaChi, ngaySinh, Luong, gioiTinh, ngayVaoLam, ghiChu, trangThai));
    }
    
    public void suaNhanVien() {
        String id = txtIDNhanVien.getText();
        String name = txtTenNhanVien.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String ghiChu = txtGhiChu.getText().trim();
        String ngaySinh = HELPER.helper.LayNgayString(dateNgaySinh.getDate(), "yyyy-MM-dd");
        String ngayVaoLam = HELPER.helper.LayNgayString(dateNgayVaoLam.getDate(), "yyyy-MM-dd");
        boolean gioiTinh;
        boolean trangThai;
        long Luong = HELPER.helper.SoLong(txtLuong.getText());
        if (cbGioiTinh.getSelectedIndex() == 0) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        if (cbTrangThai.getSelectedIndex() == 0) {
            trangThai = true;
        } else {
            trangThai = false;
        }
        
        CTRLNhanVien.checkUpdate(new nhanVien(id, name, soDienThoai, diaChi, ngaySinh, Luong, gioiTinh, ngayVaoLam, ghiChu, trangThai));
    }
    
    public void setThongTinNhanVien() {
        txtIDNhanVien.setText(nv.getIdNhanVien());
        txtDiaChi.setText(nv.getDiaChi());
        txtGhiChu.setText(nv.getGhiChu());
        txtTenNhanVien.setText(nv.getName());
        txtSoDienThoai.setText(nv.getSoDienthoai());
        txtLuong.setText(HELPER.helper.LongToString(nv.getLuong()));
        
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateNgaySinh.setDate(dateFormat.parse(nv.getNgaySinh()));
            dateNgayVaoLam.setDate(dateFormat.parse(nv.getNgaySinh()));
        } catch (Exception e) {
        }
        
        if (nv.isGioiTinh() == true) {
            cbGioiTinh.setSelectedIndex(0);
        } else {
            cbGioiTinh.setSelectedIndex(1);
        }
        if (nv.isTrangThai() == true) {
            cbTrangThai.setSelectedIndex(0);
        } else {
            cbTrangThai.setSelectedIndex(1);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIDNhanVien = new javax.swing.JTextField();
        txtTenNhanVien = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbGioiTinh = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        dateNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dateNgayVaoLam = new com.toedter.calendar.JDateChooser();
        txtLuong = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbTrangThai = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDanhSachHoaDonBanHang = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(null);
        setModal(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Mã nhân viên :");

        txtIDNhanVien.setEditable(false);
        txtIDNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIDNhanVien.setFocusable(false);

        txtTenNhanVien.setEditable(false);
        txtTenNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tên nhân viên :");

        txtSoDienThoai.setEditable(false);
        txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Số điện thoại :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Địa chỉ :");

        txtDiaChi.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Giới tính :");

        cbGioiTinh.setEditable(true);
        cbGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cbGioiTinh.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Ngày sinh :");

        dateNgaySinh.setDateFormatString("dd-MM-yyyy");
        dateNgaySinh.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Ngày vào làm :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Lương :");

        dateNgayVaoLam.setDateFormatString("dd-MM-yyyy");
        dateNgayVaoLam.setEnabled(false);

        txtLuong.setEditable(false);
        txtLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtLuong.setText("0");
        txtLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLuongKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Trạng thái :");

        cbTrangThai.setEditable(true);
        cbTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang làm", "Đã nghĩ" }));
        cbTrangThai.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Ghi chú :");

        txtGhiChu.setEditable(false);

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(0, 153, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/remove-user.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnXoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(0, 153, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/edit-user.png"))); // NOI18N
        btnSua.setText("Sữa");
        btnSua.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSua.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(51, 153, 255));
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(dateNgayVaoLam, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(txtIDNhanVien)
                    .addComponent(txtSoDienThoai))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                    .addComponent(txtTenNhanVien, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(cbGioiTinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGhiChu))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtIDNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(dateNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(6, 6, 6))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSua))
                .addContainerGap())
        );

        tableDanhSachHoaDonBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Khách hàng", "Nhân viên", "Tổng tiền", "Giảm giá", "Hình thức", "Thời gian", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDanhSachHoaDonBanHang.setRowHeight(35);
        tableDanhSachHoaDonBanHang.setRowMargin(3);
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

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Hóa đơn đã bán :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel11)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1385, Short.MAX_VALUE)
                            .addComponent(jSeparator1))
                        .addContainerGap())))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableDanhSachHoaDonBanHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableDanhSachHoaDonBanHangFocusLost
        tableDanhSachHoaDonBanHang.clearSelection();
    }//GEN-LAST:event_tableDanhSachHoaDonBanHangFocusLost

    private void tableDanhSachHoaDonBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDanhSachHoaDonBanHangMouseClicked

    }//GEN-LAST:event_tableDanhSachHoaDonBanHangMouseClicked

    private void tableDanhSachHoaDonBanHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDanhSachHoaDonBanHangMousePressed
        
        if (tableDanhSachHoaDonBanHang.getSelectedRows().length == 1 && evt.getClickCount() == 2) {
            String idhoadon = tableDanhSachHoaDonBanHang.getValueAt(tableDanhSachHoaDonBanHang.getSelectedRow(), 0) + "";
            this.setVisible(false);
            new frmXemHoaDonBanHang(null, true, acc, idhoadon).setVisible(true);
            MDHoaDon.showHoaDonCuaNhanVien(idNhanVien, tableDanhSachHoaDonBanHang);
        }
    }//GEN-LAST:event_tableDanhSachHoaDonBanHangMousePressed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        
        showHide(true);
    }//GEN-LAST:event_btnSuaActionPerformed
    public void showHide(boolean bool) {
        txtDiaChi.setEditable(bool);
        txtGhiChu.setEditable(bool);
        txtLuong.setEditable(bool);
        txtSoDienThoai.setEditable(bool);
        txtTenNhanVien.setEditable(bool);
        dateNgaySinh.setEnabled(bool);
        dateNgayVaoLam.setEnabled(bool);
        cbGioiTinh.setEnabled(bool);
        cbTrangThai.setEnabled(bool);
        btnLuu.setEnabled(bool);
        btnSua.setEnabled(!bool);
        btnXoa.setEnabled(!bool);
    }
    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed

    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (txtIDNhanVien.getText().trim().equals("admin")) {
            JOptionPane.showMessageDialog(this, "KHÔNG ĐƯỢC XÓA TÀI KHOẢN ĐIỀU HÀNH !!");
            return;
        }
        
        if (JOptionPane.showConfirmDialog(null, "Xóa nhân viên ?") == 0) {
            MDNhanVien.remove(txtIDNhanVien.getText());
        }
        this.dispose();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLuongKeyReleased
        HELPER.helper.setTextFieldMoney(txtLuong);
    }//GEN-LAST:event_txtLuongKeyReleased

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
            java.util.logging.Logger.getLogger(frmEditorNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEditorNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEditorNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEditorNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmEditorNhanVien dialog = new frmEditorNhanVien(new javax.swing.JFrame(), true, idNhanVien);
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
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbGioiTinh;
    private javax.swing.JComboBox<String> cbTrangThai;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private com.toedter.calendar.JDateChooser dateNgayVaoLam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tableDanhSachHoaDonBanHang;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtIDNhanVien;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenNhanVien;
    // End of variables declaration//GEN-END:variables
}
