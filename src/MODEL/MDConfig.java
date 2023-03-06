/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import CLASS.config;
import java.sql.ResultSet;

/**
 *
 * @author Admin
 */
public class MDConfig {
    
    public static config getConfig() {
        config config = null;
        String sql = "select * from config";
        ResultSet rs = HELPER.SQLhelper.executeQuery(sql);
        try {
            while (rs.next()) {
                config = new config(rs.getString("username"), rs.getString("pass"), rs.getInt("theme"));
            }
        } catch (Exception e) {
            return null;
        }
        return config;
    }
    
    public static void setConfig(config conf) {
        String sql = "update config set username=?,pass=?,theme=?";
        HELPER.SQLhelper.executeUpdate(sql, conf.getUsername(), conf.getPassword(), conf.getTheme());
    }
}
