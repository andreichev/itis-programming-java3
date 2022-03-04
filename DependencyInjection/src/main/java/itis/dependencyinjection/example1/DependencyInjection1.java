package itis.dependencyinjection.example1;

public class DependencyInjection1 {
    public static void main(String[] args) {
        System.out.println("Выйди из дома");
        Taxi taxi = new Taxi();
        taxi.bookTaxi();
        taxi.driveToSchoolByTaxi();
    }
}
