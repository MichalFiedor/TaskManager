package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) throws FileNotFoundException {
        selectOptions();
        loadingFile();
    }

    public static void selectOptions() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        String listOptions[] = {"add", "remove", "exit"};
        for (String option : listOptions) {
            System.out.println(ConsoleColors.RESET + option);
        }
        Scanner scan = new Scanner(System.in);
        switch (scan.next().toLowerCase()) {
            case "add":
//                    addTask();
                scan.next();
                break;
            case "remove":
//                    removeTask();
                scan.next();
                break;
            case "exit":
                System.out.println(ConsoleColors.RED_BOLD + "Bye bye");
                System.exit(1);
                break;
        }
    }

    public static void loadingFile() throws FileNotFoundException {
        Path pathTaskFile = Paths.get("tasks.csv");
        if (!Files.exists(pathTaskFile)) {
            try {
                System.out.println("At this moment you do not have any saved tasks.\nDo you want create new task sheet?" + "(" + ConsoleColors.BLACK_BOLD + "Yes/No" + ConsoleColors.RESET + ")");
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
            File file = new File("tasks.csv");
            Scanner scan = new Scanner(file);
            String[][] tasksArray = new String[0][3];
            while (scan.hasNextLine()) {
                String task[] = scan.nextLine().split(", ");
                tasksArray = ArrayUtils.add(tasksArray, task);
            }
        }
    }

    public static void add() {

    }
}
