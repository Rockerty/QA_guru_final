package testdata;

public class TestData {
    public static String firstName = "Nick";
    public static String lastName = "Smirnov";
    public static String email = "smirnov@mail.ru";
    public static String phone = "999 999 9999";
    public static String language = "Russian";
    public static String birthYear = "2000";
    public static String birthMonth = "Feb";
    public static String birthDay = "25";
    public static String expectedBirthDate = birthDay + "/" +
            TestDataHelper.getMonthNumber(birthMonth) + "/" +
            birthYear;
    public static String gender = "Male";
    public static String[] hobbies = {"Sports", "Reading", "Music"};
    public static String[] subjects = {"Maths", "Arts", "Dance", "Physical"};
    public static String state = "California";
    public static String city = "San Francisco";
    public static String address = "My lovely address";
    public static String shortIncorrectEmail = "sm@mail.ru";
    public static String incorrectFormatEmail = "smirnovsmir";
}
