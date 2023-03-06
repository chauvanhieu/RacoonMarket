package CONTROLLER;

import CLASS.sanPham;
import MODEL.MDSanPham;
import javax.swing.JOptionPane;

public class CTRLSanPham {

    public static boolean themNhanhSanPham(String ten, long giaBan, String barcode) {
        boolean check = true;
        if (ten.equals("") || giaBan < 1) {
            JOptionPane.showMessageDialog(null, "Sai thông tin sản phẩm !!!");
            check = false;
        }

        if (check == true) {
            MDSanPham.quickAdd(ten, giaBan, barcode);
        }
        return check;
    }

    public static void checkAddSP(sanPham sp) {

        if (sp.getName().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên sản phẩm không được để trống !");
            return;
        }

        if (sp.getIdDonViTinh().equals("")) {
            JOptionPane.showMessageDialog(null, "Chưa chọn đơn vị tính !");
            return;
        }

        if (sp.getIdLoaiSanPham().equals("")) {
            JOptionPane.showMessageDialog(null, "Chưa chọn loại sản phẩm !");
            return;
        }

        if (sp.getIdNhaCungCap().equals("")) {
            JOptionPane.showMessageDialog(null, "Chưa chọn nhà cung cấp !");
            return;
        }
        if (sp.getSoLuong() < 0) {
            JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0 !");
            return;
        }

        if (sp.getGiaBan() < 0) {
            JOptionPane.showMessageDialog(null, "Giá bán phải lớn hơn 0 !");
            return;
        }
        if (sp.getGiaNhap() < 0) {
            JOptionPane.showMessageDialog(null, "Giá nhập phải lớn hơn 0 !");
            return;
        }
        if (sp.getGiaSi() < 0) {
            JOptionPane.showMessageDialog(null, "Giá sĩ phải lớn hơn 0 !");
            return;
        }

        MDSanPham.add(sp);
        JOptionPane.showMessageDialog(null, "Thêm thành công !");
    }

    public static void checkUpdate(sanPham sp) {
        if (sp.getName().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên sản phẩm không được để trống !");
            return;
        }

        if (sp.getIdDonViTinh().equals("")) {
            JOptionPane.showMessageDialog(null, "Chưa chọn đơn vị tính !");
            return;
        }

        if (sp.getIdLoaiSanPham().equals("")) {
            JOptionPane.showMessageDialog(null, "Chưa chọn loại sản phẩm !");
            return;
        }

        if (sp.getIdNhaCungCap().equals("")) {
            JOptionPane.showMessageDialog(null, "Chưa chọn nhà cung cấp !");
            return;
        }
        if (sp.getSoLuong() < 0) {
            JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0 !");
            return;
        }

        if (sp.getGiaBan() < 0) {
            JOptionPane.showMessageDialog(null, "Giá bán phải lớn hơn 0 !");
            return;
        }
        if (sp.getGiaNhap() < 0) {
            JOptionPane.showMessageDialog(null, "Giá nhập phải lớn hơn 0 !");
            return;
        }
        if (sp.getGiaSi() < 0) {
            JOptionPane.showMessageDialog(null, "Giá sĩ phải lớn hơn 0 !");
            return;
        }

        MDSanPham.update(sp);
        JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
    }

}
