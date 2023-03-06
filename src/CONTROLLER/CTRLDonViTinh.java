/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import CLASS.donViTinh;
import MODEL.MDDonViTinh;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class CTRLDonViTinh {

    public static void checkAdd(donViTinh item) {
        if (item.getName().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên đơn vị tính không được để trống !");
        } else {
            MDDonViTinh.add(item);
            JOptionPane.showMessageDialog(null, "Thêm thành công !");
        }
    }

    public static void checkUpdate(donViTinh item) {
        if (item.getName().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên đơn vị tính không được để trống !");
        } else {
            MDDonViTinh.update(item);
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
        }
    }
}
