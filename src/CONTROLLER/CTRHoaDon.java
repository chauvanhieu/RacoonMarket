/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import CLASS.hoaDon;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class CTRHoaDon {
    public static  void  checkAddSP(hoaDon hoadon){
    boolean checkIdKhachHang = false;
//    boolean checkHinhThucThanhToan = false ;
    boolean checkTongTien = false;
    
        if (hoadon.getIdKhachHang() == " ") {
            JOptionPane.showMessageDialog(null, "Khách Hàng không được bỏ trống !");
            
            return;
        }else{
            checkIdKhachHang = true;
        }
//        hình thức thanh toán đg để int t sợ bỏ zô nó xung đột mà hình thức thanh toán cx là 1 cái CBB nên nếu cần thì nhét zô
//        if (hoadon.getHinhThucThanhToan()<0) {
//            JOptionPane.showMessageDialog(null, " Chưa có hình thức thanh toán !");
//            
//            return;
//        }else{
//            checkHinhThucThanhToan = true;
//        }
        if (hoadon.getTongTien() <= 0) {
            JOptionPane.showMessageDialog(null, "Chưa có sản phẩm nào !");
            
            return;
        }else{
            checkTongTien = true;
        }
        
        if (checkIdKhachHang == false || checkTongTien == false) {
            return;
        }else{
            JOptionPane.showMessageDialog(null, "Đã thanh toán thành công .");
        }
    }
    
    
}
