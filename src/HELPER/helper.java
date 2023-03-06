/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HELPER;

import CLASS.config;
import MODEL.MDConfig;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialOceanicContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialPalenightContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMonokaiProContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatSolarizedLightContrastIJTheme;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class helper {

    public static Image resize(Image img, int width, int height) {
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public static byte[] toByteArray(Image img, String type) throws IOException {
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bimage, type, baos);
        return baos.toByteArray();
    }

    public static byte[] toByteArray(ImageIcon icon) throws IOException {
        Image img = icon.getImage();
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bimage, "jpg", baos);
        return baos.toByteArray();
    }

    public static Image getImage(byte[] b) {
        return new ImageIcon(b).getImage();
    }

    public static Image craeteImage(byte[] data, String type) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        return bImage2.getScaledInstance(bImage2.getWidth(), bImage2.getHeight(), Image.SCALE_SMOOTH);
    }

    public static void setConfig(config item) {
        MDConfig.setConfig(item);
    }

    public static boolean isPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);

        return m.matches();
    }

    public static boolean isEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static boolean isUsername(final String username) {
        String USERNAME_PATTERN
                = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static String getDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static boolean isBarcode(String username) {
        String USERNAME_PATTERN
                = "\\d+";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static void setTextFieldMoney(JTextField txt) {
        String txtGiaBan = txt.getText();
        long tienLuong = SoLong(txtGiaBan);
        txt.setText(SoString(tienLuong));
    }

    public static boolean isFullname(String str) {
        String input = removeAccent(str);
        String regx = "^([a-vxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđĐ]+)((\\s{1}[a-vxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđ]+){1,})$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static config getConfig() {
        return MDConfig.getConfig();
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static void setTableTextCenter(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount() - 1; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public static void setTableTextCenterFullColumn(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public static boolean isNumberPhone(String str) {
        // Bieu thuc chinh quy mo ta dinh dang so dien thoai
        String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";

        // Kiem tra dinh dang
        boolean kt = str.matches(reg);
        try {
            if (kt == false) {
                return false;
            } else {

                return true;
            }
        } catch (Exception e) {
        }
        return true;
    }

    public static String LayNgayString(Date date, String Format) {
        DateFormat dateFormat = new SimpleDateFormat(Format);
        return dateFormat.format(date);
    }

    //Hàm chuyển String -> Date
    public static Date LayNgayDate(String ngay, String Format) {
        DateFormat dateFormat = new SimpleDateFormat(Format);
        try {
            return dateFormat.parse(ngay);
        } catch (ParseException ex) {
            return null;
        }
    }

    //Chuyển đổi số Double <-> String 10.000.000
    //Hàm chuyển Double -> String
    public static String LongToString(double so) {
        return NumberFormat.getNumberInstance().format(so);
    }

    //Hàm chuyển String -> Double
    public static double StringToLong(String so) {
        try {
            return NumberFormat.getNumberInstance().parse(so).doubleValue();
        } catch (ParseException ex) {
            return 0;
        }
    }

    public static String SoString(long so) {
        return NumberFormat.getNumberInstance().format(so);
    }

    //Hàm chuyển String -> Double
    public static long SoLong(String so) {
        try {
            return NumberFormat.getNumberInstance().parse(so).longValue();
        } catch (ParseException ex) {
            return 0;
        }
    }

    public static void updateTheme(int num) {
        config config = helper.getConfig();
        config.setTheme(num);
        helper.setConfig(config);

        JOptionPane.showMessageDialog(null, "Khởi động lại phần mềm để áp dụng giao diện mới");
    }

    public static void setupTheme(int userTheme) {
        switch (userTheme) {
            case 1:
                break;
            case 2:
                FlatHighContrastIJTheme.setup();
                break;

            case 3:
                FlatCarbonIJTheme.setup();
                break;
            case 4:
                FlatMonokaiProContrastIJTheme.setup();
                break;
            case 5:
                FlatArcDarkContrastIJTheme.setup();
                break;
            case 6:
                FlatSolarizedLightContrastIJTheme.setup();
                break;
            case 7:
                FlatArcOrangeIJTheme.setup();
                break;
            case 8:
                FlatMaterialOceanicContrastIJTheme.setup();
                break;
            case 9:
                FlatMaterialPalenightContrastIJTheme.setup();//
                break;
            case 10:
                FlatGitHubContrastIJTheme.setup();
                break;
            case 11:
                FlatAtomOneLightIJTheme.setup();//
                break;
            default:
                FlatLightLaf.setup();
                break;
        }

        UIManager.put("PasswordField.showRevealButton", true);
        UIManager.put("PasswordField.showCapsLock", true);
    }

    public static void showClearButton(JTextField txt) {
        txt.putClientProperty("JTextField.showClearButton", true);
        txt.putClientProperty("JTextField.clearCallback",
                (Runnable) () -> {
                    txt.setText("");
                });
    }

    public static void addIconSearch(JTextField txt) {
////        getClass().getResource("/ICON/search.png")
//        Icon icon = new ImageIcon(new ImageIcon(getClass().getResource("/ICON/search.png"))).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
////        Icon icon = new ImageIcon("src/ICON/search.png");
//        txt.putClientProperty("JTextField.leadingIcon", icon);
        txt.putClientProperty("JTextField.showClearButton", true);
        txt.putClientProperty("JTextField.clearCallback",
                (Runnable) () -> {
                    txt.setText("");
                });
    }
}
