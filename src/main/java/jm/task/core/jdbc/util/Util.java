package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db_jdbc", "root", "password");
    }

    public static void connection(String sql) {
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
