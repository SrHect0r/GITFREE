package util;

import java.sql.*;
import logic.Player;

public class SQL {
    private static final String URL = "jdbc:mysql://localhost:3306/traviesodb";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean iniciarsesion(Player player) {
        String sql = "INSERT INTO usuario (nombre) VALUES (?)";

        try (Connection conn = connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, player.getName());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al hacer login: " + e.getMessage());
            return false;
        }
    }


    public static boolean result(Player player) {
        String findUserIdSql = "SELECT id FROM usuario WHERE nombre = ?";
        String insertResultSql = "INSERT INTO puntuacion (usuario_id, puntos) VALUES (?, ?)";

        try (Connection conn = connection();
             PreparedStatement findStmt = conn.prepareStatement(findUserIdSql)) {

            findStmt.setString(1, player.getName());
            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");

                try (PreparedStatement insertStmt = conn.prepareStatement(insertResultSql)) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setInt(2, player.getScore());
                    insertStmt.executeUpdate();
                    return true;
                }

            } else {
                System.out.println("User not found: " + player.getName());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error saving game result: " + e.getMessage());
            return false;
        }
    }

}
