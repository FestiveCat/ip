package tasks;


import java.util.ArrayList;
import java.util.List;

import exceptions.NoSuchTaskException;
import file.management.FileManager;

/**
 * This class encapsulates a taskList.
 *
 * @author Ong Han Yang
 */
public class TaskList {
    /** Name of the saved file */
    private String fileName = "saved-taskList.txt";

    /** Folder path to the saved file */
    private String filePath = "data";

    /** The container for the items, implemented as an ArrayList */
    private final ArrayList<Task> list;

    /**
     * Constructs a taskList.
     */
    public TaskList() {
        this.list = new ArrayList<Task>();
    }

    /**
     * Consturcts a tasklist and sets the file path and file name.
     *
     * @param filePath the file path to the file to save/load from.
     * @param fileName the file name to save/load from.
     */
    public TaskList(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.list = new ArrayList<Task>();
    }

    /**
     * Adds an item into the taskList.
     *
     * @param item the item to be added.
     */
    public void add(Task item) {
        this.list.add(item);
        this.saveToFile();
    }

    /**
     * Adds multiple items at a time to the taskList.
     *
     * @param items all the items to be added.
     */
    public void add(Task... items) {
        this.list.addAll(List.of(items));
        this.saveToFile();
    }

    /**
     * Checks if a given taskIndex is within the indexes in the task list.
     *
     * @param taskIndex the task index to check.
     * @return true if the given index fits into the task list, false if it is not.
     */
    private boolean isWithinIndex(int taskIndex) {
        return taskIndex > -1 && taskIndex < this.list.size();
    }

    /**
     * Removes an item from the list of tasks.
     *
     * @param taskIndex the index of the item to remove.
     * @return the removed task.
     */
    public Task delete(int taskIndex) throws NoSuchTaskException {
        if (!isWithinIndex(taskIndex)) {
            throw new NoSuchTaskException("There is no task with index " + taskIndex);
        }
        Task output = this.list.remove(taskIndex);
        this.saveToFile();
        return output;
    }

    /**
     * Gets the length of the task list.
     *
     * @return an integer representing the length of the list.
     */
    public int length() {
        return this.list.size();
    }

    /**
     * Marks a task as done or undone.
     *
     * @param taskIndex the index of the task to be marked.
     * @param isDone whether the task is to be done or not.
     * @throws NoSuchTaskException when there is no such task with the index taskNum.
     */
    public void markTask(int taskIndex, boolean isDone) throws NoSuchTaskException {
        if (!isWithinIndex(taskIndex)) {
            throw new NoSuchTaskException("There is no task with index " + taskIndex);
        }
        this.list.get(taskIndex).markAs(isDone);
        this.saveToFile();
    }

    /**
     * Represents a stored task as a String.
     *
     * @param taskIndex the index of the task to be displayed.
     * @return String representation of the task.
     * @throws NoSuchTaskException when there is no such task with the index taskNum.
     */
    public String displayTask(int taskIndex) throws NoSuchTaskException {
        if (!isWithinIndex(taskIndex)) {
            throw new NoSuchTaskException("There is no task with index " + taskIndex);
        }
        return this.list.get(taskIndex).toString();
    }

    /**
     * Displays the taskList as a String.
     *
     * @return String representation of the tasks.TaskList.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, list.get(i).toString()));
        }
        return sb.toString();
    }

    /**
     * Saves the current state of the list to the save-file.
     */
    private void saveToFile() {
        FileManager.saveToFile(filePath, fileName, this.toString());
    }

}
