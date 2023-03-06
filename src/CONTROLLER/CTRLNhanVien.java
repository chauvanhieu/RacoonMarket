package CONTROLLER;

import CLASS.nhanVien;
import MODEL.MDNhanVien;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class CTRLNhanVien {

    public static void checkAdd(nhanVien nhanvien) {
        boolean checkName = false;
        boolean checkSoDienThoai = false;
        boolean checkDiaChi = false;
        boolean checkLuong = false;

        checkName = HELPER.helper.isFullname(nhanvien.getName());
        checkSoDienThoai = HELPER.helper.isNumberPhone(nhanvien.getSoDienthoai());
        if (checkName == false) {
            JOptionPane.showMessageDialog(null, "Tên nhân viên sai !");
            return;
        }
        if (checkSoDienThoai == false) {
            JOptionPane.showMessageDialog(null, "Số điện thoại sai !");
            return;
        }
        if (checkName == false) {
            JOptionPane.showMessageDialog(null, "Tên không đúng !");
            return;
        }

        if (checkSoDienThoai == false) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không đúng !");
            return;
        }
        if (nhanvien.getDiaChi().equals("")) {
            JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống !");
            return;
        } else {
            checkDiaChi = true;
        }

        if (nhanvien.getLuong() < 0) {
            JOptionPane.showMessageDialog(null, "Công nợ không được âm !");
            return;
        } else {
            checkLuong = true;
        }
        if (checkDiaChi == true && checkSoDienThoai == true && checkName == true && checkLuong == true) {
            MDNhanVien.add(nhanvien);
            JOptionPane.showMessageDialog(null, "Thêm thành công !");
        }
    }

    public static void checkUpdate(nhanVien nhanvien) {
        boolean checkName = false;
        boolean checkSoDienThoai = false;
        boolean checkDiaChi = false;
        boolean checkLuong = false;

        checkName = HELPER.helper.isFullname(nhanvien.getName());
        checkSoDienThoai = HELPER.helper.isNumberPhone(nhanvien.getSoDienthoai());
        if (checkName == false) {
            JOptionPane.showMessageDialog(null, "Tên nhân viên sai !");
            return;
        }
        if (checkSoDienThoai == false) {
            JOptionPane.showMessageDialog(null, "Số điện thoại sai !");
            return;
        }
        if (nhanvien.getDiaChi().equals("")) {
            JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống !");
            return;
        } else {
            checkDiaChi = true;
        }

        if (nhanvien.getLuong() < 0) {
            JOptionPane.showMessageDialog(null, "Công nợ không được âm !");
            return;
        } else {
            checkLuong = true;
        }
        if (checkDiaChi == true && checkSoDienThoai == true && checkName == true && checkLuong == true) {
            MDNhanVien.update(nhanvien);
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
        }
    }
}
