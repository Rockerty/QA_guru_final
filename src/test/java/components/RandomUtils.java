package components;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.String.format;

public class RandomUtils {
    private static final Random RANDOM = new Random();
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    public static String getRandomString(int length) {
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARS.length());
            result.append(CHARS.charAt(index));
        }
        return result.toString();
    }

    public static String getRandomEmail(int length) {
        return format("%s@%s.com", getRandomString(length), getRandomString(length));
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static long getRandomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    public static String getRandomPhone() {
        String phoneTemplate = "+%s";
        return String.format(phoneTemplate, getRandomLong(1111111111L, 9999999999L));
    }

    public static String getRandomPhoneByTemplate() {
        String phoneTemplate = "%s %s %s";
        return String.format(phoneTemplate, getRandomInt(111, 999), getRandomInt(111, 999), getRandomInt(1111, 9994));
    }

    public static String getRandomGender() {
        String[] genders = {"Male", "Female", "Other"};
        int randomIndex = getRandomInt(0, 2);
        return genders[randomIndex];
    }

    public static String getRandomItemFromArray(String[] array) {
        int randomIndex = getRandomInt(0, array.length - 1);
        return array[randomIndex];
    }

    public static String getRandomLanguage() {
        String[] languages = {"English", "Russian", "Italian", "Chines"};
        int randomIndex = getRandomInt(0, 3);
        return languages[randomIndex];
    }

    public static List<String> getRandomSubset(String[] array) {
        List<String> list = new ArrayList<>(Arrays.asList(array));
        Collections.shuffle(list);

        int numberOfItems = getRandomInt(1, array.length);
        return list.subList(0, numberOfItems);
    }
}
