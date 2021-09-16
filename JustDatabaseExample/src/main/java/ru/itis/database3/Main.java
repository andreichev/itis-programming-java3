package ru.itis.database3;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Optional;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/drivers";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        CrudRepository<Driver, Long> driversDao = new DriverRepository(dataSource);
        Optional<Driver> optionalDriver = driversDao.findById(0L);

        if (optionalDriver.isPresent()) {
            Driver driver = optionalDriver.get();
            System.out.println("Driver found! Name: " + driver.getFirstName());
        } else {
            System.out.println("No drivers found!");
        }

        Driver driver = Driver.builder()
                .firstName("Ivan")
                .lastName("Antonov")
                .age(50)
                .build();

        Driver savedDriver = driversDao.save(driver);
        System.out.println("Created driver with id = " + savedDriver.getId());
    }
}
