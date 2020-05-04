package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    static File file = new File("tasks.csv");
    static Path pathTaskFile = Paths.get("tasks.csv");
    static String[][] tasksArray = new String[0][3];

    public static void main(String[] args) throws FileNotFoundException {
        loadingFile();
        while (true) {
            options();
            selectOption();
        }
    }

    public static void options() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        String listOptions[] = {"add", "remove", "list", "exit"};
        for (String option : listOptions) {
            System.out.println(ConsoleColors.RESET + option);
        }
    }

    public static void selectOption() {
        Scanner scan = new Scanner(System.in);
        while (!scan.hasNext("add") && !scan.hasNext("remove") && !scan.hasNext("list") && !scan.hasNext("exit")) {
            System.out.println(ConsoleColors.RED_BOLD + "Incorrect choice. Try again." + ConsoleColors.RESET);
            scan.next();
        }
        switch (scan.next().toLowerCase()) {
            case "add":
                addTool();
                break;
            case "remove":
                removeTool();
                break;
            case "list":
                listTool();
                break;
            case "exit":
                exitTool();
                break;
        }
    }

    public static void loadingFile() throws FileNotFoundException {
        if (!Files.exists(pathTaskFile)) {
            try {
                System.out.println("At this moment you do not have any saved tasks.\nDo you want create new tasks sheet?" + "(" + ConsoleColors.BLACK_BOLD + "Yes/No" + ConsoleColors.RESET + ")");
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextInt()) {
                    System.out.println(ConsoleColors.RESET + "If you want create new sheet, type" + ConsoleColors.BLACK_BOLD + " Yes" + ConsoleColors.RESET + ", if not, type" + ConsoleColors.BLACK_BOLD + " No" + ".");
                    scanner.next();
                }
                if (scanner.next().toLowerCase().equals("yes")) {
                    Files.createFile(pathTaskFile);
                } else {
                    System.exit(1);
                }
            } catch (IOException e) {
                System.out.println("File can not be created.");
            }
        } else {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String task[] = scan.nextLine().split(", ");
                tasksArray = ArrayUtils.add(tasksArray, task);
            }
        }
    }

    public static void addTool() {
        String[] newTask = new String[3];
        Scanner scan = new Scanner(System.in);
        System.out.println("Please add task description");
        newTask[0] = scan.nextLine();
        System.out.println("Please add task due date (YYYY-MM-DD)");
        newTask[1] = scan.nextLine();
        System.out.println("Is your task important: true/false");
        while (!scan.hasNext("true") && !scan.hasNext("false")) {
            System.out.println(ConsoleColors.RED_BOLD + "Incorrect value. Try again." + ConsoleColors.RESET);
            scan.next();
        }
        newTask[2] = scan.next();
        tasksArray = ArrayUtils.add(tasksArray, newTask);
    }

    public static void removeTool() throws IndexOutOfBoundsException {
        System.out.println("Please select number to remove.");
        Scanner scan = new Scanner(System.in);
        while (!scan.hasNextInt()) {
            System.out.println(ConsoleColors.RED_BOLD + "Incorrect value. Try again" + ConsoleColors.RESET);
            scan.next();
        }
        int numericValue = scan.nextInt();
        boolean value = false;
        while (!value)
            if (numericValue > (tasksArray.length - 1) || numericValue < 0) {
                System.out.println(ConsoleColors.RED_BOLD + "Typed number out of range. Choose available tasks number from list." + ConsoleColors.RED_BOLD);
                while (!scan.hasNextInt()) {
                    System.out.println(ConsoleColors.RED_BOLD + "Incorrect value. Try again" + ConsoleColors.RESET);
                    scan.next();
                }
                numericValue = scan.nextInt();
            } else {
                tasksArray = ArrayUtils.remove(tasksArray, numericValue);
                value = true;
            }
    }

    public static void listTool() {
        for (int i = 0; i < tasksArray.length; i++) {
            System.out.println(i + " : " + StringUtils.join(tasksArray[i], ", "));
        }
    }

    public static void exitTool() {
        System.out.println(ConsoleColors.RED_BOLD + "Bye bye");
        try (FileWriter fileWriter = new FileWriter("tasks.csv")) {
            for (int i = 0; i < tasksArray.length; i++) {
                for(String data : tasksArray[i]){
                    fileWriter.append(data + " ");
                }
                fileWriter.append("\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        System.exit(1);
    }
}


