package instructions.listinstructions;

import java.util.stream.Stream;

import exceptions.InvalidInputException;
import instructions.Instruction;
import tasks.TaskList;

/**
 * This class encapsulates a Find Tasks instruction. A Find Task finds all tasks with the given
 * keyword.
 * <br>
 * Format: {@code find < keyword >}
 *
 * @author Ong Han Yang
 */
public class FindInst extends Instruction {
    /** Reusable exception for when no keywords are given */
    private static final InvalidInputException MISSING_KEYWORD_EXCEPTION =
            new InvalidInputException("No keywords "
            + "detected in your input! "
            + "Please use \"find\" with a keyword!");

    /** The keyword to search for */
    private String keywords;

    /**
     * Constructs a Find Task instruction.
     *
     * @param keywords the keyword for the search.
     */
    private FindInst(String keywords) {
        this.keywords = keywords;
    }

    /**
     * Produces a FindInstruction with the given input.
     *
     * @param input the original instruction in the form of "find < x >".
     * @return a Find Task instruction with the keyword found in the input.
     * @throws InvalidInputException when no keywords are specified.
     */
    public static FindInst of(String input) throws InvalidInputException {
        String[] split = input.split(" ", 2);
        if (split.length == 1) {
            throw MISSING_KEYWORD_EXCEPTION;
        }
        String strippedKeywords = split[1].strip();
        if (strippedKeywords.length() == 0) {
            throw MISSING_KEYWORD_EXCEPTION;
        }
        return new FindInst(split[1]);
    }

    /**
     * Performs the search to list out all the instructions that contain the keyword in it. The
     * numbering is kept the same as the {@code list} command.
     *
     * @param taskList the task list for the instruction to perform its action on.
     * @return the String representation of the filtered list, showing only tasks that contain the keyword.
     */
    @Override
    public String doInst(TaskList taskList) {
        String allTasks = taskList.toString();
        Stream<String> filtered = allTasks.lines().filter(line -> line.contains(keywords));
        StringBuilder sb = new StringBuilder();
        filtered.forEach(line -> sb.append(line).append("\n"));
        return sb.toString();
    }
}
