package ru.NSmirnov;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String toBePrint = "Hello Culture!";
        int myInt = 4;
        double myDouble = 1.5;
        char myChar = 'c';
        boolean myBoolean = true;
        String name = "Nick";
        int age = 37;
        //boolean result = 3 < 5;
        boolean result = name.equals("Nicks") && age == 37;
        System.out.println(result);

        intArifmetiqOperation();
        intAndDoubleArifmetiqOperation();
        logicalOperation();
        overflowOperation();
    }

    public static void intArifmetiqOperation () {
        int firstVal = 13;
        int secondVal = 98;

        int result11 = firstVal + secondVal;
        int result12 = firstVal - secondVal;
        int result13 = firstVal * secondVal;
        int result14 = firstVal / secondVal;
        System.out.println("Result11 = " + result11);
        System.out.println("Result12 = " + result12);
        System.out.println("Result13 = " + result13);
        System.out.println("Result14 = " + result14);
    }

    public static void intAndDoubleArifmetiqOperation () {
        int firstVal = 13;
        double secondVal = 99.99;

        double result21 = firstVal + secondVal;
        double result22 = firstVal * secondVal;
        System.out.println("Result21 = " + result21);
        System.out.println("Result22 = " + result22);
    }

    public static void logicalOperation () {
        int firstVal = 13;
        double secondVal = 99.99;

        boolean result31 = firstVal * 3 > secondVal;
        boolean result32 = firstVal > secondVal/10;
        System.out.println("Result31 = " + result31);
        System.out.println("Result32 = " + result32);
    }

    public static void overflowOperation () {

        double d = Double.MAX_VALUE;
        double result41 = d * 2;

        System.out.println("Result41 = " + result41);
        System.out.println("float max: " + Float.MAX_VALUE);
    }
}