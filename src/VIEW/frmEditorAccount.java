package VIEW;

import CLASS.nhanVien;
import COMPONENT.DetailedComboBox;
import CONTROLLER.CTRLAccount;
import HELPER.helper;
import MODEL.MDAccount;
import MODEL.MDNhanVien;
import src.CLASS.Account;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

public class frmEditorAccount extends javax.swing.JDialog {

    private DetailedComboBox comboBoxNhanvien;
    private static String option;
    private String idNhanVien;
    private boolean checkUser = false;
    private boolean checkPass = false;
    private boolean checkConfirmPass = false;

    public frmEditorAccount(java.awt.Frame parent, boolean modal, String option) {
        super(parent, modal);
        frmEditorAccount.option = option;
        initComponents();
        loadComboBoxNhanVien();
        if (option != "add") {
            loadThongTin();
        } else {
            lb.setVisible(false);
            cbTrangThai.setVisible(false);
        }
    }

    public void loadThongTin() {
        Account acc = MDAccount.getAccount(option);
        txtUsername.setText(acc.getUsername());
        txtPassword.setText(acc.getPassword());
        txtConfirmPassword.setText(acc.getPassword());
        int index = 0;
        ArrayList<nhanVien> data = MDNhanVien.getData();
        for (int i = 0; i < data.size(); i++) {

            if (acc.getIdNhanVien().equals(data.get(i).getIdNhanVien())) {
                index = i;
                comboBoxNhanvien.setSelectedIndex(i);
                break;
            }
        }
        cbTrangThai.setSelectedIndex(acc.isTrangThai() == true ? 0 : 1);
        comboBoxNhanvien.setSelectedIndex(index);
        checkboxBanHang.setSelected(acc.isBanHang());
        checkboxHangHoa.setSelected(acc.isHangHoa());
        checkboxKhachHang.setSelected(acc.isKhachHang());
        checkboxNhaCungCap.setSelected(acc.isNhaCungCap());
        checkboxNhapHang.setSelected(acc.isNhapHang());
        checkboxBaoCao.setSelected(acc.isBaoCao());
        checkboxThuChi.setSelected(acc.isPhieuChi());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelNhanVien = new javax.swing.JPanel();
        lb = new javax.swing.JLabel();
        cbTrangThai = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtConfirmPassword = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        btnHuy = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        checkboxBanHang = new javax.swing.JCheckBox();
        checkboxNhapHang = new javax.swing.JCheckBox();
        checkboxHangHoa = new javax.swing.JCheckBox();
        checkboxKhachHang = new javax.swing.JCheckBox();
        checkboxNhaCungCap = new javax.swing.JCheckBox();
        checkboxThuChi = new javax.swing.JCheckBox();
        checkboxBaoCao = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUÁN LÝ TÀI KHOẢN");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel3.setText("Mật khẩu :");

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsernameKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel2.setText("Tên đăng nhập :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel4.setText("Nhân viên :");

        panelNhanVien.setLayout(new java.awt.BorderLayout());

        lb.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lb.setText("Trạng thái :");

        cbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn sử dụng", "Ngưng sử dụng" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel5.setText("Nhập lại mật khẩu :");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });

        txtConfirmPassword.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtConfirmPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtConfirmPasswordKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtConfirmPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbTrangThai, 0, 227, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(panelNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(lb)
                    .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnHuy.setForeground(new java.awt.Color(0, 153, 255));
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(0, 153, 255));
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        checkboxBanHang.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        checkboxBanHang.setText("Bán hàng");

        checkboxNhapHang.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        checkboxNhapHang.setText("Nhập hàng");

        checkboxHangHoa.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        checkboxHangHoa.setText("QL hàng hóa");

        checkboxKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        checkboxKhachHang.setText("QL khách hàng");

        checkboxNhaCungCap.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        checkboxNhaCungCap.setText("QL nhà cung cấp");

        checkboxThuChi.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        checkboxThuChi.setText("QL thu chi");

        checkboxBaoCao.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        checkboxBaoCao.setText("QL báo cáo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkboxBanHang)
                    .addComponent(checkboxKhachHang))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(checkboxNhaCungCap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(checkboxNhapHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkboxThuChi)
                        .addGap(68, 68, 68)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkboxHangHoa)
                    .addComponent(checkboxBaoCao))
                .addGap(71, 71, 71))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxBanHang)
                    .addComponent(checkboxNhapHang)
                    .addComponent(checkboxHangHoa)
                    .addComponent(checkboxThuChi))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(checkboxNhaCungCap)
                    .addComponent(checkboxKhachHang)
                    .addComponent(checkboxBaoCao))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void loadComboBoxNhanVien() {
        ArrayList<nhanVien> data = MDNhanVien.getData();
        String[] columns = new String[]{"Mã nhân viên", "Tên", "Điện thoại", "Địa chỉ"};
        int[] widths = new int[]{80, 300, 120, 360};
        this.comboBoxNhanvien = new DetailedComboBox(columns, widths, 1);

        List<List<?>> tableData = new ArrayList<List<?>>();

        for (nhanVien nv : data) {
            tableData.add(new ArrayList<>(
                    Arrays.asList(nv.getIdNhanVien(), nv.getName(), nv.getSoDienthoai(), nv.getDiaChi())));
        }
        comboBoxNhanvien.setTableData(tableData);
        comboBoxNhanvien.setFont(new Font("Arial", Font.ITALIC, 16));
        comboBoxNhanvien.setSelectedIndex(-1);
        comboBoxNhanvien.setPopupAlignment(DetailedComboBox.Alignment.LEFT);
        comboBoxNhanvien.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<?> rowData = comboBoxNhanvien.getSelectedRow();
                idNhanVien = rowData.get(0) + "";
            }
        });
        comboBoxNhanvien.setVisible(true);
        panelNhanVien.add(comboBoxNhanvien);
    }
    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        if (CTRLAccount.isAccount(txtUsername.getText().trim(),
                new String(txtPassword.getPassword()).trim(),
                new String(txtConfirmPassword.getPassword()).trim()) == false) {
            return;
        }

        if (option.equals("add")) {
            if (comboBoxNhanvien.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên !!");
                return;
            }
            Account acc = new Account(
                    idNhanVien,
                    txtUsername.getText(),
                    new String(txtPassword.getPassword()),
                    true,
                    checkboxBanHang.isSelected(),
                    checkboxNhapHang.isSelected(),
                    false,
                    checkboxHangHoa.isSelected(),
                    false,
                    checkboxKhachHang.isSelected(),
                    checkboxNhaCungCap.isSelected(),
                    checkboxBaoCao.isSelected(),
                    checkboxThuChi.isSelected());
            CTRLAccount.checkAddAccount(acc);
        } else {
            if (idNhanVien == "") {
                JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên !!");
                return;
            }
            Account acc = new Account(
                    idNhanVien,
                    txtUsername.getText().trim(),
                    new String(txtPassword.getPassword()).trim(),
                    cbTrangThai.getSelectedIndex() == 0 ? true : false,
                    checkboxBanHang.isSelected(),
                    checkboxNhapHang.isSelected(),
                    false,
                    checkboxHangHoa.isSelected(),
                    false,
                    checkboxKhachHang.isSelected(),
                    checkboxNhaCungCap.isSelected(),
                    checkboxBaoCao.isSelected(),
                    checkboxThuChi.isSelected());
            CTRLAccount.checkUpdate(acc);
        }

    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnHuyActionPerformed

    private void txtUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyReleased
        String username = txtUsername.getText();
        if (helper.isUsername(username) == true) {
            txtUsername.setBorder(new LineBorder(Color.green, 1));
            checkUser = true;
        } else {
            txtUsername.setBorder(new LineBorder(Color.red, 1));
            checkUser = false;
        }
    }//GEN-LAST:event_txtUsernameKeyReleased


    private void txtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyReleased

    }//GEN-LAST:event_txtPasswordKeyReleased

    private void txtConfirmPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfirmPasswordKeyReleased

    }//GEN-LAST:event_txtConfirmPasswordKeyReleased

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
            java.util.logging.Logger.getLogger(frmEditorAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEditorAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEditorAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEditorAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmEditorAccount dialog = new frmEditorAccount(new javax.swing.JFrame(), true, option);
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
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLuu;
    private javax.swing.JComboBox<String> cbTrangThai;
    private javax.swing.JCheckBox checkboxBanHang;
    private javax.swing.JCheckBox checkboxBaoCao;
    private javax.swing.JCheckBox checkboxHangHoa;
    private javax.swing.JCheckBox checkboxKhachHang;
    private javax.swing.JCheckBox checkboxNhaCungCap;
    private javax.swing.JCheckBox checkboxNhapHang;
    private javax.swing.JCheckBox checkboxThuChi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
