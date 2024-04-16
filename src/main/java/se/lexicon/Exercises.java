package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       TODO:  1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> result = storage.findMany(person -> person.getFirstName().equals("Erik"));
        for (Person person : result) {
            System.out.println(person);
        }
        System.out.println("----------------------");
    }

    /*
        TODO:  2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> result = storage.findMany(person -> person.getGender() == Gender.FEMALE);
        for (Person person : result) {
            System.out.println(person);
        }
        System.out.println("----------------------");
    }

    /*
        TODO:  3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        //Write your code here
        Predicate<Person> filter = person -> person.getBirthDate().isAfter(LocalDate.of(1999, 12, 31));
        List<Person> result = storage.findMany(filter);
        for (Person person : result) {
            System.out.println(person);
        }
        System.out.println("----------------------");
    }

    /*
        TODO: 4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        //Write your code here
        System.out.println(storage.findOne(person -> person.getId() == 123).toString());
        System.out.println("----------------------");

    }

    /*
        TODO:  5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        //Write your code here
        System.out.println(storage.findOneAndMapToString(
                person -> person.getId() == 456,
                Person::toString));
        System.out.println("----------------------");
    }

    /*
        TODO:  6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        //Write your code here
        System.out.println(storage.findOneAndMapToString(
                person -> person.getFirstName().startsWith("E") &&
                        person.getGender() == Gender.MALE,
                Person::toString));
        System.out.println("----------------------");
    }

    /*
        TODO:  7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        //Write your code here
        System.out.println(storage.findOneAndMapToString(
                person -> person.getBirthDate().isAfter(LocalDate.now().minusYears(10)),
                person -> person.getFirstName() + " " + person.getLastName() + " " + (LocalDate.now().getYear() - person.getBirthDate().getYear()) + " years"));
        System.out.println("----------------------");
    }

    /*
        TODO:  8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndDo(
                person -> person.getFirstName().equals("Ulf"),
                person -> System.out.println(person.toString())
        );
        System.out.println("----------------------");
    }

    /*
        TODO:  9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndDo(
                person -> person.getLastName().contains(person.getFirstName()),
                person -> System.out.println(person.toString())
        );
        //it keeps returning nothing, but I guess it's because the names are random everytime the program is run
        // I need to retry until it generates person that fits the above criteria or make a custom entry to the data storage
        System.out.println("----------------------");
    }

    /*
        TODO:  10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndDo(
                person -> new StringBuilder(person.getFirstName()).reverse().toString().equalsIgnoreCase(person.getFirstName()),
                person -> System.out.println(person.getFirstName() + " " + person.getLastName())
        );
        System.out.println("----------------------");
    }

    /*
        TODO:  11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> sortedList = new ArrayList<>(
                storage.findAndSort(
                        person -> person.getFirstName().startsWith("A"),
                        Comparator.comparing(person -> person.getBirthDate()))
        );

        for (Person person : sortedList) {
            System.out.println(person.toString());
        }
        System.out.println("----------------------");
    }

    /*
        TODO:  12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> sortedList = new ArrayList<>(
                storage.findAndSort(
                        person -> person.getBirthDate().isBefore(LocalDate.of(1950, 1, 1)),
                        Comparator.comparing((Person person) -> person.getBirthDate()).reversed()
                )
                //Reminder: Comparator.comparing() parameter requires you to pass in a function itself
                //for reasons not understood I cannot write this statement inside Comparator.comparing()
                //person -> person.getBirthDate()
                //instead I need to specify (Person person) -> person.getBirthDate() for it to work
                //Person::getBirthDate works too
        );

        for (Person person : sortedList) {
            System.out.println(person.toString());
        }
        System.out.println("----------------------");
    }

    /*
        TODO:  13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> sortedList = new ArrayList<>(
        storage.findAndSort(Comparator.comparing((Person person) -> person.getLastName())
                .thenComparing((Person person) -> person.getFirstName())
                .thenComparing((Person person) -> person.getBirthDate())
                //Thi
        )
        //Same problem here, cannot write person -> person.method() instead you have to specify the class inside parentheses
        //(Person person) -> method call

        );

        for (Person person : sortedList) {
            System.out.println(person.toString());
        }
        System.out.println("----------------------");
    }
}
