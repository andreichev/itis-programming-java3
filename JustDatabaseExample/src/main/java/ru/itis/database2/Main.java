package ru.itis.database2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/drivers";

    public static void main(String[] args) throws Exception {
        Connection connection =
                DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        CrudRepository<Driver, Long> driversDao = new UsersRepositoryDBImpl(connection);
        Optional<Driver> optionalDriver = driversDao.findById(0L);

        if (optionalDriver.isPresent()) {
            Driver driver = optionalDriver.get();
            System.out.println("Driver found! Name: " + driver.getFirstName());
        } else {
            System.out.println("No drivers found!");
        }

        Driver driver = Driver.builder()
                .firstName("Ivan")
                .lastName("Kulikov")
                .age(50)
                .build();

        Driver savedDriver = driversDao.save(driver);
        System.out.println("Created driver with id = " + savedDriver.getId());
    }
}
