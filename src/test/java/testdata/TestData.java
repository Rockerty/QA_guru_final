package testdata;

import com.github.javafaker.Faker;
import java.util.*;
import static components.RandomUtils.*;

public class TestData {
    public static Faker fakerRu = new Faker(new Locale("ru"));
    public static Faker faker = new Faker();

    public static String firstName = fakerRu.name().firstName();
    public static String lastName = fakerRu.name().lastName();
    public static String email = getRandomEmail(8);

    public static String phone = getRandomPhoneByTemplate();
    public static String language = getRandomLanguage();

    public static String[] genders = {"Male", "Female", "Other"};
    public static String gender = getRandomItemFromArray(genders);

    public static String[] years = {"1998", "1999", "2000", "2001", "2002", "2003", "2004"};
    public static String birthYear = getRandomItemFromArray(years);
    public static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};
    public static String birthMonth = getRandomItemFromArray(months);
    public static String birthDay = getRandomInt(1, 28) + "";
    public static String expectedBirthDate = birthDay + "/" +
            TestDataHelper.getMonthNumber(birthMonth) + "/" +
            birthYear;

    public static String[] allHobbies = {"Sports", "Reading", "Music"};
    public static List<String> hobbies = getRandomSubset(allHobbies);

    public static String[] allSubjects = {"Sports", "Music", "Maths", "Accounting", "Arts", "Dance", "Science", "Geometry", "Geography", "English", "Physical"};
    public static List<String> subjects = getRandomSubset(allSubjects);

    public static Map<String, List<String>> stateCities = new HashMap<>() {{
        put("California", Arrays.asList("San Francisco", "Los Angeles", "San Diego"));
        put("Texas", Arrays.asList("Houston", "Dallas", "San Antonio"));
        put("New York", Arrays.asList("New York", "Buffalo", "Rochester"));
        put("Florida", Arrays.asList("Miami", "Tampa", "Jacksonville"));
    }};
    public static String[] allStates = stateCities.keySet().toArray(new String[0]);
    public static String state = getRandomItemFromArray(allStates);
    public static String city = getRandomItemFromArray(
            stateCities.get(state).toArray(new String[0])
    );

    public static String address = faker.address().fullAddress();
    public static String shortIncorrectEmail = getRandomEmail(2);;
    public static String incorrectFormatEmail = getRandomString(8);
}
