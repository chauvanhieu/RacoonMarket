package VIEW;

import CLASS.donViTinh;
import CLASS.loaiSanPham;
import CLASS.nhaCungCap;
import CLASS.sanPham;
import COMPONENT.DetailedComboBox;
import CONTROLLER.CTRLSanPham;
import MODEL.MDDonViTinh;
import MODEL.MDLoaiSanPham;
import MODEL.MDNhaCungCap;
import MODEL.MDSanPham;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class frmEditorSanPham extends javax.swing.JDialog {

    public static String option;
    private DetailedComboBox comboBoxNhaCungCap;
    private DetailedComboBox comboBoxDonViTinh;
    private DetailedComboBox comboBoxNhomHang;
    private ArrayList<nhaCungCap> dataNhaCungCap = MDNhaCungCap.getAll();
    private ArrayList<loaiSanPham> dataLoaiSanPham = MDLoaiSanPham.getAll();
    private ArrayList<donViTinh> dataDonViTinh = MDDonViTinh.getAll();
    private String idNhaCungCap;
    private String idLoaiSanPham;
    private String idDonViTinh;
    private String hinhAnh;
    private Component thisPanel = this;

    public frmEditorSanPham(java.awt.Frame parent, boolean modal, String option) throws IOException {
        super(parent, modal);

        this.option = option;
        initComponents();
        loadComboBox();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/IMAGE/empty.png")).getImage().getScaledInstance(lbHinhAnh.getWidth(), lbHinhAnh.getHeight(), Image.SCALE_SMOOTH));
        hinhAnh = getClass().getResource("/IMAGE/empty.png").toString().replaceAll("jar:file:/", "");
        lbHinhAnh.setIcon(imageIcon);
        if (this.option != "add") {
            loadThongTinSanPham(this.option);
        }
        setUX(this.option);
    }

    public void loadThongTinSanPham(String idSanPham) throws IOException {
        sanPham item = MDSanPham.getSanPham(idSanPham);
        txtTenSanPham.setText(item.getName());
        txtBarcode.setText(item.getBarcode());
        txtGhiChu.setText(item.getGhiChu());
        txtGiaSi.setText(HELPER.helper.LongToString(item.getGiaSi()));
        txtGiaBan.setText(HELPER.helper.LongToString(item.getGiaBan()));
        txtGiaNhap.setText(HELPER.helper.LongToString(item.getGiaNhap()));
        txtSoLuong.setValue(Integer.parseInt(item.getSoLuong() + ""));
        txtSoLuongToiThieu.setValue(Integer.parseInt(item.getSoLuongToiThieu() + ""));

        ImageIcon imageIcon = new ImageIcon(new ImageIcon(item.getHinhAnh()).getImage().getScaledInstance(lbHinhAnh.getWidth(), lbHinhAnh.getHeight(), Image.SCALE_SMOOTH));

        lbHinhAnh.setIcon(imageIcon);
        this.hinhAnh = item.getHinhAnh();
        if (item.isTrangThai() == true) {
            cbTrangThai.setSelectedIndex(0);
        } else {
            cbTrangThai.setSelectedIndex(1);
        }
        for (int i = 0; i < dataDonViTinh.size(); i++) {
            if (dataDonViTinh.get(i).getIdDonViTinh().equals(item.getIdDonViTinh())) {
                comboBoxDonViTinh.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < dataLoaiSanPham.size(); i++) {
            if (dataLoaiSanPham.get(i).getIdLoaiSanPham().equals(item.getIdLoaiSanPham())) {
                comboBoxNhomHang.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < dataNhaCungCap.size(); i++) {
            if (dataNhaCungCap.get(i).getIdNhaCungCap().equals(item.getIdNhaCungCap())) {
                comboBoxNhaCungCap.setSelectedIndex(i);
                break;
            }
        }

    }

    // edit UX
    public void setUX(String option) {

        if (option != "add") {
            btnChonAnh.setEnabled(false);
            btnLuu.setEnabled(false);
            txtBarcode.setEditable(false);
            txtGhiChu.setEditable(false);
            txtGiaBan.setEditable(false);
            txtGiaNhap.setEditable(false);
            txtSoLuong.setEnabled(false);
            txtSoLuongToiThieu.setEnabled(false);
            txtTenSanPham.setEditable(false);
            comboBoxDonViTinh.setEnabled(false);
            comboBoxNhaCungCap.setEnabled(false);
            comboBoxNhomHang.setEnabled(false);
            cbTrangThai.setEnabled(false);
            txtGiaSi.setEditable(false);
            // set actionlistener btn Sữa
            btnSua.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnChonAnh.setEnabled(true);
                    btnLuu.setEnabled(true);
                    cbTrangThai.setEnabled(true);
                    txtBarcode.setEditable(true);
                    txtGhiChu.setEditable(true);
                    txtGiaBan.setEditable(true);
                    txtGiaNhap.setEditable(true);
                    txtSoLuong.setEnabled(true);
                    txtSoLuongToiThieu.setEnabled(true);
                    txtTenSanPham.setEditable(true);
                    comboBoxDonViTinh.setEnabled(true);
                    comboBoxNhaCungCap.setEnabled(true);
                    comboBoxNhomHang.setEnabled(true);
                    btnSua.setEnabled(false);
                }
            });

            btnLuu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String id = option;
                    String name = txtTenSanPham.getText().trim();
                    String barcode = txtBarcode.getText().trim();
                    long giaNhap = HELPER.helper.SoLong(txtGiaNhap.getText());
                    long giaBan = HELPER.helper.SoLong(txtGiaBan.getText());
                    long giaSi = HELPER.helper.SoLong(txtGiaSi.getText());
                    int soLuong = Integer.parseInt(txtSoLuong.getValue() + "");
                    int soLuongToiThieu = Integer.parseInt(txtSoLuongToiThieu.getValue() + "");
                    String ghiChu = txtGhiChu.getText().trim();

                    sanPham spUpdate = new sanPham(
                            id,
                            name,
                            barcode,
                            hinhAnh,
                            giaNhap,
                            giaBan,
                            giaSi,
                            soLuong,
                            soLuongToiThieu,
                            idNhaCungCap,
                            idDonViTinh,
                            idLoaiSanPham,
                            ghiChu,
                            cbTrangThai.getSelectedIndex() == 0 ? true : false);
                    CTRLSanPham.checkUpdate(spUpdate);
                }
            });

        } else if (option == "add") {
            lbTrangThai.setVisible(false);
            cbTrangThai.setVisible(false);
            btnSua.setEnabled(false);
            btnLuu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String id = MDSanPham.createID();
                    String name = txtTenSanPham.getText().trim();
                    String barcode = txtBarcode.getText().trim();
                    long giaNhap = HELPER.helper.SoLong(txtGiaNhap.getText());
                    long giaBan = HELPER.helper.SoLong(txtGiaBan.getText());
                    long giaSi = HELPER.helper.SoLong(txtGiaSi.getText());
                    int soLuong = Integer.parseInt(txtSoLuong.getValue() + "");
                    int soLuongToiThieu = Integer.parseInt(txtSoLuongToiThieu.getValue() + "");
                    String ghiChu = txtGhiChu.getText().trim();

                    sanPham sp = new sanPham(
                            id,
                            name,
                            barcode,
                            hinhAnh,
                            giaNhap,
                            giaBan,
                            giaSi,
                            soLuong,
                            soLuongToiThieu,
                            idNhaCungCap,
                            idDonViTinh,
                            idLoaiSanPham,
                            ghiChu,
                            true);
                    CTRLSanPham.checkAddSP(sp);
                    thisPanel.setVisible(false);
                }
            });
        }

    }

    public void loadComboBox() {
        // comboBox nhà cung cấp
        String[] columns1 = new String[]{"Mã", "Tên", "Điện thoại", "Công nợ"};
        int[] widths1 = new int[]{80, 300, 120, 130};
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
            }
        });
        comboBoxNhaCungCap.setVisible(true);
        pnlNhaCungCap.add(comboBoxNhaCungCap);

        // combo loại sản phẩm
        String[] columns2 = new String[]{"Mã", "Loại"};
        int[] widths2 = new int[]{80, 200};
        this.comboBoxNhomHang = new DetailedComboBox(columns2, widths2, 1);

        List<List<?>> tableDataLoaiSanPham = new ArrayList<List<?>>();
        for (loaiSanPham lsp : dataLoaiSanPham) {
            tableDataLoaiSanPham.add(new ArrayList<>(
                    Arrays.asList(lsp.getIdLoaiSanPham(), lsp.getName())));
        }

        comboBoxNhomHang.setTableData(tableDataLoaiSanPham);
        comboBoxNhomHang.setFont(new Font("Arial", Font.ITALIC, 14));
        comboBoxNhomHang.setSelectedIndex(-1);
        comboBoxNhomHang.setPopupAlignment(DetailedComboBox.Alignment.LEFT);
        comboBoxNhomHang.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<?> rowData = comboBoxNhomHang.getSelectedRow();
                idLoaiSanPham = rowData.get(0) + "";
            }
        });
        comboBoxNhomHang.setVisible(true);
        pnlNhomHang.add(comboBoxNhomHang);

        // combo đơn vị tính
        String[] columns3 = new String[]{"Mã", "ĐVT"};
        int[] widths3 = new int[]{80, 200};
        this.comboBoxDonViTinh = new DetailedComboBox(columns3, widths3, 1);

        List<List<?>> tableDataDonViTinh = new ArrayList<List<?>>();
        for (donViTinh dvt : dataDonViTinh) {
            tableDataDonViTinh.add(new ArrayList<>(
                    Arrays.asList(dvt.getIdDonViTinh(), dvt.getName())));
        }

        comboBoxDonViTinh.setTableData(tableDataDonViTinh);
        comboBoxDonViTinh.setFont(new Font("Arial", Font.ITALIC, 14));
        comboBoxDonViTinh.setSelectedIndex(-1);
        comboBoxDonViTinh.setPopupAlignment(DetailedComboBox.Alignment.LEFT);
        comboBoxDonViTinh.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<?> rowData = comboBoxDonViTinh.getSelectedRow();
                idDonViTinh = rowData.get(0) + "";
            }
        });
        comboBoxDonViTinh.setVisible(true);
        pnlDonViTinh.add(comboBoxDonViTinh);
    }

    public void copyFile(File sourceFile, File destFile) {
        if (!destFile.exists()) {
            try {
                destFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(frmEditorSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } catch (IOException e) {
        } finally {
            if (source != null) {
                try {
                    source.close();
                } catch (IOException ex) {
                    Logger.getLogger(frmEditorSanPham.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (destination != null) {
                try {
                    destination.close();
                } catch (IOException ex) {
                    Logger.getLogger(frmEditorSanPham.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lbHinhAnh = new javax.swing.JLabel();
        btnChonAnh = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnLuu = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtSoLuong = new javax.swing.JSpinner();
        txtSoLuongToiThieu = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        txtTenSanPham = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtBarcode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pnlNhaCungCap = new javax.swing.JPanel();
        pnlDonViTinh = new javax.swing.JPanel();
        pnlNhomHang = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtGiaSi = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbTrangThai = new javax.swing.JComboBox<>();
        lbTrangThai = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THÔNG TIN SẢN PHẨM");

        lbHinhAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbHinhAnh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnChonAnh.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnChonAnh.setText("...");
        btnChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhActionPerformed(evt);
            }
        });

        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(0, 153, 255));
        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/new-san-pham.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLuu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSua.setForeground(new java.awt.Color(0, 153, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/edit-san-pham.png"))); // NOI18N
        btnSua.setText("Sữa");
        btnSua.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSua.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtSoLuongToiThieu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setText("Số lượng tối thiểu :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Tên sản phẩm :");

        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Mã vạch :");

        txtBarcode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Nhà cung cấp :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setText("Đơn vị tính :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setText("Nhóm hàng :");

        pnlNhaCungCap.setMinimumSize(new java.awt.Dimension(64, 43));
        pnlNhaCungCap.setPreferredSize(new java.awt.Dimension(64, 22));
        pnlNhaCungCap.setLayout(new java.awt.BorderLayout());

        pnlDonViTinh.setMinimumSize(new java.awt.Dimension(64, 43));
        pnlDonViTinh.setPreferredSize(new java.awt.Dimension(64, 22));
        pnlDonViTinh.setLayout(new java.awt.BorderLayout());

        pnlNhomHang.setMinimumSize(new java.awt.Dimension(64, 43));
        pnlNhomHang.setPreferredSize(new java.awt.Dimension(64, 22));
        pnlNhomHang.setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Giá nhập :");

        txtGiaNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaNhap.setText("0");
        txtGiaNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaNhapKeyReleased(evt);
            }
        });

        txtGiaBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaBan.setText("0");
        txtGiaBan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaBanKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("Giá bán :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setText("Số lượng :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setText("Ghi chú :");

        txtGiaSi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaSi.setText("0");
        txtGiaSi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaSiKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setText("Giá sĩ :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtGiaSi)
                    .addComponent(pnlNhaCungCap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(pnlDonViTinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlNhomHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuongToiThieu, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGhiChu, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBarcode, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(90, 90, 90))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pnlNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pnlDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pnlNhomHang, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaSi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel9))
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtSoLuongToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        cbTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn sử dụng", "Ngưng sử dụng" }));

        lbTrangThai.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbTrangThai.setText("Trạng thái :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbTrangThai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChonAnh)
                .addGap(106, 106, 106))
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnChonAnh)
                    .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTrangThai))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonAnhActionPerformed
        //tạo bảng chọn file
        JFileChooser chooser = new JFileChooser();
        //chỉ chọn các file ảnh
        FileFilter imageFilter = new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes());

        //set filter chọn file ảnh
        chooser.setFileFilter(imageFilter);

        //Mở bảng chọn
        int rVal = chooser.showOpenDialog(new JPanel());

        // nếu đã chọn thì tạo 1 file mới với path đã chọn bởi chooser.
        // sau đó copy file đó đến folder IMAGE 
        if (rVal == JFileChooser.APPROVE_OPTION) {

            File file = new File(chooser.getSelectedFile().getAbsolutePath());

            this.hinhAnh = file.getPath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(file.getPath()).getImage().getScaledInstance(lbHinhAnh.getWidth(), lbHinhAnh.getHeight(), Image.SCALE_DEFAULT));
            lbHinhAnh.setIcon(imageIcon);
        }
    }//GEN-LAST:event_btnChonAnhActionPerformed

    private void txtGiaNhapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaNhapKeyReleased
        HELPER.helper.setTextFieldMoney(txtGiaNhap);
    }//GEN-LAST:event_txtGiaNhapKeyReleased

    private void txtGiaBanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaBanKeyReleased
        HELPER.helper.setTextFieldMoney(txtGiaBan);
    }//GEN-LAST:event_txtGiaBanKeyReleased

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed

    }//GEN-LAST:event_btnLuuActionPerformed

    private void txtGiaSiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaSiKeyReleased
        HELPER.helper.setTextFieldMoney(txtGiaSi);
    }//GEN-LAST:event_txtGiaSiKeyReleased

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
            java.util.logging.Logger.getLogger(frmEditorSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEditorSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEditorSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEditorSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmEditorSanPham dialog = null;
                try {
                    dialog = new frmEditorSanPham(new javax.swing.JFrame(), true, option);
                } catch (IOException ex) {
                    Logger.getLogger(frmEditorSanPham.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    private javax.swing.JButton btnChonAnh;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JComboBox<String> cbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbHinhAnh;
    private javax.swing.JLabel lbTrangThai;
    private javax.swing.JPanel pnlDonViTinh;
    private javax.swing.JPanel pnlNhaCungCap;
    private javax.swing.JPanel pnlNhomHang;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtGiaSi;
    private javax.swing.JSpinner txtSoLuong;
    private javax.swing.JSpinner txtSoLuongToiThieu;
    private javax.swing.JTextField txtTenSanPham;
    // End of variables declaration//GEN-END:variables
}
