package ru.itis.database1;

import javax.sql.DataSource;
import java.sql.*;

public class ReadExample {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/first_webapp_database";

    public static void main(String[] args) throws SQLException {
        Connection connection = null;

        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        // Получение данных
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from users");

        while (result.next()) {
            System.out.println(
                    result.getInt("id")
                            + " " + result.getString("first_name")
                            + " " + result.getString("course_name"));
        }
    }
}
