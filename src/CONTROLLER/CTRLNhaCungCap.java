package CONTROLLER;

import CLASS.nhaCungCap;
import MODEL.MDNhaCungCap;
import javax.swing.JOptionPane;

public class CTRLNhaCungCap {

    public static void checkAdd(nhaCungCap ncc) {
        boolean checkName = false;
        boolean checkSoDienThoai = false;
        boolean checkDiaChi = false;
        boolean congNo = false;
        if (ncc.getName().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên nhà cung cấp không được để trống !");
            return;
        } else {
            checkName = true;
        }

        checkSoDienThoai = HELPER.helper.isNumberPhone(ncc.getSoDienThoai());
        if (checkSoDienThoai == false) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không đúng !");
            return;
        }
        if (ncc.getDiaChi().equals("")) {
            JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống !");
            return;
        } else {
            checkDiaChi = true;
        }

        if (ncc.getCongNo() < 0) {
            JOptionPane.showMessageDialog(null, "Công nợ không được âm !");
            return;
        } else {
            congNo = true;
        }
        if (checkDiaChi == true && checkName == true && checkSoDienThoai == true && congNo == true) {
            MDNhaCungCap.add(ncc);
            JOptionPane.showMessageDialog(null, "Thêm thành công !");
        }
    }

    public static void checkUpdate(nhaCungCap ncc) {
        boolean checkName = false;
        boolean checkSoDienThoai = false;
        boolean checkDiaChi = false;
        boolean congNo = false;
        if (ncc.getName().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên nhà cung cấp không được để trống !");
            return;
        } else {
            checkName = true;
        }

        checkSoDienThoai = HELPER.helper.isNumberPhone(ncc.getSoDienThoai());
        if (checkSoDienThoai == false) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không đúng !");
            return;
        }
        if (ncc.getDiaChi().equals("")) {
            JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống !");
            return;
        } else {
            checkDiaChi = true;
        }

        if (ncc.getCongNo() < 0) {
            JOptionPane.showMessageDialog(null, "Công nợ không được âm !");
            return;
        } else {
            congNo = true;
        }
        if (checkDiaChi == true && checkName == true && checkSoDienThoai == true && congNo == true) {
            MDNhaCungCap.update(ncc);
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
        }
    }
}
