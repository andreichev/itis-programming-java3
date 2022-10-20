package ru.itis.database1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;

public class WriteExample {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123123";
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/first_lesson_database";

    public static void main(String[] args) throws Exception {
        Connection connection = null;

        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

	    // Запись данных в бд
	    Scanner scanner = new Scanner(System.in);
        System.out.print("FIRST NAME: ");
	    String firstName = scanner.nextLine();
        System.out.print("LAST NAME: ");
	    String lastName = scanner.nextLine();
        System.out.print("COURSE NAME: ");
	    String courseName = scanner.nextLine();
        System.out.print("AGE: ");
	    int age = scanner.nextInt();

        // Плохой вариант формирования запроса т. к. возможна SQL иньекция
        {
            String sqlInsertUser = "insert into users(first_name, last_name, course_name, age) values ('" +
                    firstName + "','" + lastName + "', '" + courseName + "', " + age + ");";
            System.out.println(sqlInsertUser);

            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(sqlInsertUser);
            System.out.println("Было добавлено " + affectedRows + " строк");
        }

        // Правильный вариант формирования запроса
        {
            String sqlInsertUser = "insert into users(first_name, last_name, course_name, age) " +
                    "values (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, courseName);
            preparedStatement.setInt(4, age);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Было добавлено " + affectedRows + " строк");
        }
    }
}
