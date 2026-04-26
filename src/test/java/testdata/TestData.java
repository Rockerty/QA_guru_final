package testdata;

import com.github.javafaker.Faker;
import org.junit.jupiter.params.provider.Arguments;

import java.util.*;
import java.util.stream.Stream;

import static testdata.TestDataHelper.formatPhoneNumber;
import static testdata.TestDataHelper.getMonthNumber;

public class TestData {
    public static Faker fakerRu = new Faker(new Locale("ru"));

    public static String firstName = fakerRu.name().firstName();
    public static String lastName = fakerRu.name().lastName();
    private static String generateRandomEmail(int length) {
        String part1 = fakerRu.regexify("[a-zA-Z0-9]{" + length + "}");
        String part2 = fakerRu.regexify("[a-zA-Z0-9]{" + length + "}");
        return part1 + "@" + part2 + ".com";
    }
    public static String email = generateRandomEmail(9);


    public static String phone = fakerRu.phoneNumber().subscriberNumber(10);
    public static String expectedPhone = formatPhoneNumber(phone);

    public static String[] languages = {"English", "Russian", "Italian", "Chines"};
    public static String language = fakerRu.options().option(languages);

    public static String[] genders = {"Male", "Female", "Other"};
    public static String gender = fakerRu.options().option(genders);

    public static String[] years = {"1998", "1999", "2000", "2001", "2002", "2003", "2004"};
    public static String birthYear = fakerRu.options().option(years);
    public static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};
    public static String birthMonth = fakerRu.options().option(months);
    public static String birthDay = String.valueOf(fakerRu.number().numberBetween(1, 29));
    public static String expectedBirthDate = birthDay + "/" +
            getMonthNumber(birthMonth) + "/" +
            birthYear;

    public static String[] allHobbies = {"Sports", "Reading", "Music"};
    public static List<String> hobbies = generateRandomHobbies();
    private static List<String> generateRandomHobbies() {
        List<String> all = Arrays.asList(allHobbies);
        Collections.shuffle(all);
        int size = fakerRu.number().numberBetween(1, allHobbies.length + 1);
        return all.subList(0, size);
    }

    public static String[] allSubjects = {"Sports", "Music", "Maths", "Accounting", "Arts", "Dance", "Science", "Geometry", "Geography", "English", "Physical"};
    public static List<String> subjects = generateRandomSubjects();
    private static List<String> generateRandomSubjects() {
        List<String> all = Arrays.asList(allSubjects);
        Collections.shuffle(all);
        int size = fakerRu.number().numberBetween(1, allSubjects.length + 1);
        return all.subList(0, size);
    }

    public static Map<String, List<String>> stateCities = new HashMap<>() {{
        put("California", Arrays.asList("San Francisco", "Los Angeles", "San Diego"));
        put("Texas", Arrays.asList("Houston", "Dallas", "San Antonio"));
        put("New York", Arrays.asList("New York", "Buffalo", "Rochester"));
        put("Florida", Arrays.asList("Miami", "Tampa", "Jacksonville"));
    }};
    public static String[] allStates = stateCities.keySet().toArray(new String[0]);
    public static String state = fakerRu.options().option(allStates);
    public static String city = fakerRu.options().option(
            stateCities.get(state).toArray(new String[0])
    );

    public static String address = fakerRu.address().fullAddress();
    public static String shortIncorrectEmail = fakerRu.letterify("??@??.com", false);
    public static String incorrectFormatEmail = fakerRu.lorem().characters(8, false, false);

    public static Stream<String> shortEmailTestWithSourceMethod() {
        return Stream.of(
                "a@test.com",
                "parchi@mail.ru"
        );
    }

    public static Stream<String> invalidEmailTestWithSourceMethod() {
        return Stream.of(
                "mail.ru",
                "gmailcom",
                "@mail.ru"

        );
    }

    public static Stream<Arguments> errorMessageWithIncorrectEmailTest() {
        return Stream.of(
                Arguments.of("mail.ru", "E-mail is invalid"),
                Arguments.of("a@mail.ru", "E-mail must be at least 10 symbols long")
        );
    }

    public static String randomString(int charactersNumber) {
        return fakerRu.lorem().characters(charactersNumber);
    }
    public static String randomRefresh = randomString(10);
}
