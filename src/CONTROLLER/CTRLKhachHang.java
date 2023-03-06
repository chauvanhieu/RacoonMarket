/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import CLASS.khachHang;
import MODEL.MDKhachHang;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class CTRLKhachHang {

    public static void checkAdd(khachHang kh) {
        boolean checkName = false;
        boolean checkSoDienThoai = false;
        boolean checkDiaChi = false;
        boolean congNo = false;
        if (kh.getName().equals("") || HELPER.helper.isFullname(kh.getName()) == false) {
            JOptionPane.showMessageDialog(null, "Tên khách hàng không đúng !");
            return;
        } else {
            checkName = true;
        }
        checkSoDienThoai = HELPER.helper.isNumberPhone(kh.getSoDienThoai());
        if (checkSoDienThoai == false) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không đúng !");
            return;
        }
        if (kh.getDiaChi().equals("")) {
            JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống !");
            return;
        } else {
            checkDiaChi = true;
        }

        if (kh.getNo() < 0) {
            JOptionPane.showMessageDialog(null, "Công nợ không được âm !");
            return;
        } else {
            congNo = true;
        }
        if (checkDiaChi == true && checkName == true && checkSoDienThoai == true && congNo == true) {
            MDKhachHang.add(kh);
            JOptionPane.showMessageDialog(null, "Thêm thành công !");
        }
    }

    public static void checkUpdate(khachHang kh) {
        boolean checkName = false;
        boolean checkSoDienThoai = false;
        boolean checkDiaChi = false;
        boolean congNo = false;
        if (kh.getName().equals("") || HELPER.helper.isFullname(kh.getName()) == false) {
            JOptionPane.showMessageDialog(null, "Tên khách hàng không đúng !");
            return;
        } else {
            checkName = true;
        }
        checkSoDienThoai = HELPER.helper.isNumberPhone(kh.getSoDienThoai());
        if (checkSoDienThoai == false) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không đúng !");
            return;
        }
        if (kh.getDiaChi().equals("")) {
            JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống !");
            return;
        } else {
            checkDiaChi = true;
        }

        if (kh.getNo() < 0) {
            JOptionPane.showMessageDialog(null, "Công nợ không được âm !");
            return;
        } else {
            congNo = true;
        }
        if (checkDiaChi == true && checkName == true && checkSoDienThoai == true && congNo == true) {
            MDKhachHang.update(kh);
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
        }
    }
}
