package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22();
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 10 && animal.getAge() < 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.stream()
                                .collect(Collectors.groupingBy(animal -> list.indexOf(animal) / 7))
                                .getOrDefault(2, List.of())
                ))
                .forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getOrigin().equals("Japanese"))
                .map(a -> a.getGender().equals("Female") ? a.getBread() : a.getBread().toUpperCase())
                .forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30 && animal.getOrigin().startsWith("A"))
                .map(Animal::getOrigin)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> animal.getGender().equals("Female"))
                .count());
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> animal.getOrigin().equals("Hungarian")));
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .allMatch(animal -> animal.getGender().equals("Female") || animal.getGender().equals("Male")));
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .noneMatch(animal -> animal.getOrigin().equals("Oceania")));

    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .mapToInt(Animal::getAge)
                .max()
                .ifPresent(System.out::println);
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .mapToInt(arr -> arr.length)
                .min()
                .ifPresent(System.out::println);
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .mapToInt(Animal::getAge)
                .sum());
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge)
                .average()
                .ifPresentOrElse(System.out::println, () -> System.out.println(0.0));
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream()
                .filter(person -> person.getGender().equals("Male"))
                .filter(person -> calculateAge(person.getDateOfBirth()) >= 18 && calculateAge(person.getDateOfBirth()) <= 27)
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    public static void task13() {
        final int RETIREMENT_AGE = 58;
        List<House> houses = Util.getHouses();

        Stream<Person> hospital = houses.stream()
                .filter(house -> house.getBuildingType().equals("Hospital"))
                .flatMap(house -> house.getPersonList().stream());

        Stream<Person> childrenAndElder = houses.stream()
                .filter(house -> !house.getBuildingType().equals("Hospital"))
                .flatMap(house -> house.getPersonList().stream())
                .filter(person -> calculateAge(person.getDateOfBirth()) < 18 || calculateAge(person.getDateOfBirth()) >= RETIREMENT_AGE);

        Stream<Person> others = houses.stream()
                .filter(house -> !house.getBuildingType().equals("Hospital"))
                .flatMap(house -> house.getPersonList().stream())
                .filter(person -> calculateAge(person.getDateOfBirth()) < RETIREMENT_AGE && calculateAge(person.getDateOfBirth()) >= 18);

        Stream.concat(hospital, Stream.concat(
                                childrenAndElder,
                                others
                        )
                )
                .limit(500)
                .forEach(System.out::println);
    }

    public static void task14() {
        List<Car> cars = Util.getCars();
        final double COST_PER_TON = 7.14;

        Map<String, Double> countryCosts = cars.stream()
                .collect(Collectors.groupingBy(car -> {
                            if (car.getCarMake().equals("Jaguar") || car.getColor().equals("White")) {
                                return "Turkmenistan";
                            } else if (car.getMass() <= 1500 ||
                                    car.getCarMake().equals("BMW") ||
                                    car.getCarMake().equals("Lexus") ||
                                    car.getCarMake().equals("Chrysler") ||
                                    car.getCarMake().equals("Toyota")) {
                                return "Uzbekistan";
                            } else if ((car.getColor().equals("Black") && car.getMass() > 4000) ||
                                    car.getCarMake().equals("GMC") ||
                                    car.getCarMake().equals("Dodge")) {
                                return "Kazakhstan";
                            } else if (car.getReleaseYear() <= 1982 ||
                                    car.getCarModel().equals("Civic") ||
                                    car.getCarModel().equals("Cherokee")) {
                                return "Kyrgyzstan";
                            } else if (!(car.getColor().equals("Yellow") ||
                                    car.getColor().equals("Red") ||
                                    car.getColor().equals("Green") ||
                                    car.getColor().equals("Blue")) ||
                                    car.getPrice() > 40000) {
                                return "Russia";
                            } else if (car.getVin().contains("59")) {
                                return "Mongolia";
                            }
                            return "Discard";
                        },
                        Collectors.summingDouble(car -> car.getMass() / 1000.0 * COST_PER_TON)
                ));
        countryCosts.remove("Discard");

        countryCosts.forEach((country, cost) -> System.out.printf("Transportation costs for %s: $%.2f%n", country, cost));

        double totalRevenue = countryCosts.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.printf("Total revenue for the logistics company: $%.2f%n", totalRevenue);
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        System.out.println(flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparingInt(Flower::getPrice)
                        .thenComparing(Flower::getWaterConsumptionPerDay).reversed())
                .filter(flower -> {
                    String name = flower.getCommonName().toUpperCase();
                    return name.compareTo("C") >= 0 && name.compareTo("S") <= 0;
                })
                .filter(flower -> flower.isShadePreferred() &&
                        (flower.getFlowerVaseMaterial().contains("Aluminum") ||
                                flower.getFlowerVaseMaterial().contains("Glass") ||
                                flower.getFlowerVaseMaterial().contains("Steel")))
                .mapToDouble(flower -> {
                    double waterCostForFiveYears = flower.getWaterConsumptionPerDay() * 365 * 1.39 * 5;
                    return flower.getPrice() + waterCostForFiveYears;
                })
                .sum());
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
        students.stream()
                .filter(student -> student.getAge() < 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(student -> System.out.println(student.getSurname() + " " + student.getAge()));
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        students.stream()
                .map(Student::getGroup)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();

        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty, Collectors.averagingInt(Student::getAge)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input group number: ");
        String group = scanner.nextLine();

        students.stream()
                .filter(student -> student.getGroup().equals(group))
                .filter(student -> examinations.stream()
                        .anyMatch(exam -> exam.getExam3() >= 4 && exam.getStudentId() == student.getId()))
                .forEach(System.out::println);

    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();

        students.stream()
                .collect(Collectors.groupingBy(
                        Student::getFaculty,
                        Collectors.averagingDouble(student -> examinations.stream()
                                .filter(exam -> exam.getStudentId() == student.getId())
                                .findFirst()
                                .map(Examination::getExam1)
                                .orElse(0))
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .ifPresent(System.out::println);
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, Collectors.counting()))
                .forEach((key, value) -> System.out.println(key + " - " + value));
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty, Collectors.minBy(Comparator.comparing(Student::getAge))))
                .forEach((key, value) ->
                        System.out.println(key + " - " + value.map(Student::getAge).orElse(null)));
    }

    private static int calculateAge(LocalDate birthdate) {
        Period period = Period.between(birthdate, LocalDate.now());
        return period.getYears();
    }
}