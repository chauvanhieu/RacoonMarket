package CONTROLLER;

import MODEL.MDAccount;
import src.CLASS.Account;

import javax.swing.JOptionPane;

public class CTRLAccount {
    
    public static void checkAddAccount(Account acc) {
        boolean checkuser = false;
        
        checkuser = HELPER.helper.isUsername(acc.getUsername());
        if (checkuser == false) {
            JOptionPane.showMessageDialog(null, "Tên đăng nhập ít nhất 5 kí tự !");
            return;
        }
        
        if (checkuser == true) {
            MDAccount.add(acc);
            JOptionPane.showMessageDialog(null, "Đã thêm thành công !");
        }
    }
    
    public static boolean isAccount(String username, String pass, String confirmPass) {
        if (HELPER.helper.isUsername(username) == false) {
            JOptionPane.showConfirmDialog(null, "Tên đăng nhập phải ít nhất 5 kí tự và 1 chữ cái viết hoa !");
            return false;
        }
        
        if (!pass.equals(confirmPass)) {
            JOptionPane.showConfirmDialog(null, "Mật khẩu nhập lại không trùng khớp !");
            return false;
        }
        
        return true;
    }
    
    public static void checkUpdate(Account acc) {
        boolean checkuser = false;
        
        checkuser = HELPER.helper.isUsername(acc.getUsername());
        if (checkuser == false) {
            JOptionPane.showMessageDialog(null, "Tên đăng nhập ít nhất 5 kí tự !");
            return;
        }
        
        if (checkuser == true) {
            MDAccount.updateAccount(acc);
            JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
        }
        
    }
}
