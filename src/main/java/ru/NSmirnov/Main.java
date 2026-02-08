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
    }
}