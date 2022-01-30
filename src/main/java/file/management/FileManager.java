package file.management;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.function.Supplier;

import date.time.DateTimeParser;
import exceptions.SaveFileModifiedException;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;
import tasks.TaskList;
import tasks.ToDoTask;

/**
 * This class encapsulates a File Manager, where it saves, loads and parses from a saved file.
 *
 * @author Ong Han Yang
 */
public final class FileManager {
    /**
     * Saves a String to the designated file at the filePath and fileName.
     *
     * @param filePath the file path to the file to save in.
     * @param fileName the file name to save in.
     * @param infoToSave the info to save into the file.
     */
    public static void saveToFile(String filePath, String fileName, String infoToSave) {
        try {
            String combinedFilePath = filePath + "/" + fileName;
            // creates the file if not there
            File f = new File(filePath);
            f.mkdir();
            File file = new File(combinedFilePath);
            file.createNewFile();
            // writes to the file the entirety of the toString().
            FileWriter fileWriter = new FileWriter(combinedFilePath);
            fileWriter.write(infoToSave);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Loads from the designated file to obtain a TaskList that is identical to one that
     * produced the file.
     *
     * @param filePath the file path to the file to load from.
     * @param fileName the file name to load from.
     * @return an identical TaskList to one that produced the saved file.
     * @throws SaveFileModifiedException when the save file contains invalid symbols that could not
     *          have been from the string representation of a task, indicating external modification
     *          to the file.
     */
    public static TaskList loadTaskListFromFile(String filePath, String fileName)
            throws SaveFileModifiedException {
        TaskList output = new TaskList(filePath, fileName);
        try {
            File file = new File(filePath + "/" + fileName);
            Scanner sc = new Scanner(file);
            // parses each line
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                Task task = parseLine(data);
                output.add(task);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // do nothing
        }
        return output;
    }

    /**
     * Parses a String representation of a task to get back the original Task's details.
     *
     *  1. [T][X] sample task
     *  2. [D][ ] task (by: yyyy-mm-dd hh:mm)
     *  3. [E][ ] event (at: yyyy-mm-dd hh:mm, until: yyyy-mm-dd hh:mm)
     *
     *  An assumption is made where the time values are not modified to be invalid.
     *  TODO: FIX THIS ASSUMPTION, maybe make this method cleaner
     *
     * @param input the String form of the Task.
     * @return the Task that corresponds to the input String.
     * @throws SaveFileModifiedException when the save file contains invalid symbols that could not
     *          have been from the string representation of a task, indicating external modification
     *          to the file.
     */
    private static Task parseLine(String input) throws SaveFileModifiedException {
        Task output;
        String desc;
        int indexAftNumber = input.indexOf("[");
        int indexAftDesc = input.lastIndexOf("(");

        // so that the function does not call immediately because of indexAftDesc
        Supplier<LocalDateTime> deadlineSupplier = () -> {
            String s = input.substring(indexAftDesc + 5, input.length() - 1);
            return DateTimeParser.parse(s);
        };

        // so that the function does not call immediately because of indexAftDesc
        Supplier<String> descSupplier = () -> input.substring
                (indexAftNumber + 7, indexAftDesc - 1);

        switch (input.charAt(indexAftNumber + 1)) { // the type of tasks
        case 'T': // To Do task
            desc = input.substring(indexAftNumber + 7);
            output = ToDoTask.of(desc);
            break;
        case 'D': // Deadline task
            output = DeadlineTask.of(descSupplier.get(), deadlineSupplier.get());
            break;
        case 'E': // Event task
            int indexAtUntil = input.indexOf(", until:");
            // start time
            String start = input.substring(indexAftDesc + 5, indexAtUntil);
            LocalDateTime startTime = DateTimeParser.parse(start);
            // end time
            String end = input.substring(indexAtUntil + 9, input.length() - 1);
            LocalDateTime endTime;

            // if the format is the shortened form of hh:mm
            boolean isShortenedForm = end.length() == 5;
            if (isShortenedForm) {
                endTime = LocalDateTime.of(startTime.toLocalDate(), DateTimeParser.parseTime(end));
            } else {
                endTime = DateTimeParser.parse(end);
            }

            output = EventTask.of(descSupplier.get(), startTime, endTime);
            break;
        default: //unknown task
            throw new SaveFileModifiedException("Unknown task type detected!");
        }
        switch (input.charAt(indexAftNumber + 4)) { // check if task is done
        case 'X':
            output.markAs(true);
            break;
        case ' ':
            output.markAs(false);
            break;
        default: //unknown state
            throw new SaveFileModifiedException("Unknown task state detected!");
        } // the Task output
        return output;
    }
}
