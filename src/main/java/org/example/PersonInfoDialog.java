package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;

public class PersonInfoDialog {
    private static final Scanner in = new Scanner(System.in);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private String surname;
    private String name;
    private String patronymic;

    private Date birthday = null;

    private String greetingLine;

    public void start() {
        do {
            startNewSession();
        } while (askToTryAgain());
    }

    private boolean askToTryAgain() {
        System.out.print("Хотите попробывать еще раз? [д/н] ");
        var ans = in.nextLine();

        return Objects.equals(ans, "д");
    }

    private void startNewSession() {
        clear();

        getName();
        getBirthday();

        createGreetingLine();

        greetUser();
    }

    private void clear() {
        name = surname = patronymic = null;
        birthday = null;
    }

    private void getName() {
        surname = getLineUntilEmpty("Введите фамилию");
        name = getLineUntilEmpty("Введите имя");
        patronymic = getLineUntilEmpty("Введите отчество");
    }

    private void getBirthday() {
        while (true) {
            try {
                String rawDate = getLineUntilEmpty("Введите день рождения (пр. 03.10.1993)");
                birthday = DATE_FORMAT.parse(rawDate);
                return;
            } catch (ParseException e) {
                System.out.println("Дата должна соответствовать формату 'дд.мм.гггг'!");
            }
        }
    }

    private void createGreetingLine() {
        StringBuilder line = new StringBuilder();
        LocalDate localBirthday = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        line.append(">>>>> Здравствуйте, ")
                .append(surname)
                .append(' ')
                .append(Character.toUpperCase(name.charAt(0)))
                .append('.')
                .append(Character.toUpperCase(patronymic.charAt(0)))
                .append(", ")
                .append(isMale() ? "мужчина" : "женщина")
                .append(", ")
                .append(isMale() ? "рожденный в " : "рожденная в ")
                .append(getMonthName(localBirthday.getMonth().getValue()))
                .append(' ')
                .append(localBirthday.getDayOfMonth())
                .append(", ")
                .append(localBirthday.getYear())
                .append('.');

        greetingLine = line.toString();
    }

    private void greetUser() {
        System.out.println(greetingLine);
    }

    private String getMonthName(int month) {
        return switch (month) {
            case 1 -> "Январе";
            case 2 -> "Феврале";
            case 3 -> "Марте";
            case 4 -> "Апреле";
            case 5 -> "Мае";
            case 6 -> "Июне";
            case 7 -> "Июле";
            case 8 -> "Августе";
            case 9 -> "Сентября";
            case 10 -> "Октябре";
            case 11 -> "Ноябре";
            case 12 -> "Декабре";
            default -> "";
        };
    }

    private String getLineUntilEmpty(String message) {
        System.out.print(message + " > ");
        String line = in.nextLine();

        while (line == null || line.isEmpty() || line.isBlank()) {
            System.out.println("Ввод не может быть пустым!");
            System.out.print(message + " > ");
            line = in.nextLine();
        }

        return line.trim();
    }

    private boolean isMale() {
        return patronymic.toLowerCase().endsWith("вич");
    }

}
