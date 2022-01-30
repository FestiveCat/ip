package instructions.taskinstructions;

import exceptions.InvalidInputException;
import instructions.Instruction;

/**
 * This class represents a New Task Instruction, to create a new task to be
 * added in the taskList.
 * These instructions include these subclasses/instructions:
 * - To Do
 * - Deadline
 * - Event
 *
 * @author Ong Han Yang
 */
public abstract class NewTaskInst extends Instruction {
    /** Reusable Invalid Input Exception for when there is no provided task number */
    protected static final InvalidInputException MISSING_TASK_DETAILS_EXCEPTION =
            new InvalidInputException("There are missing task details!");

    /**
     * Reusable Invalid Input Exception for when too many arguments are specified. Happens
     * with multiple " /at "s or " /by "s in the input. Mainly used by subclasses.
     */
    protected static final InvalidInputException TOO_MANY_ARGUMENTS_EXCEPTION =
            new InvalidInputException("Too many arguments are supplied. "
            + "Check your input format.");

    /** Reusable Invalid Input Exception for when the input command has missing spaces */
    protected static final InvalidInputException MISSING_SPACES_EXCEPTION =
            new InvalidInputException("There are missing spaces in the command!");

    /** The provided description for the task to be added */
    private String taskDesc;

    /**
     * Constructs a New Task Instruction.
     *
     * @param taskDesc the description to be included in the new Task.
     */
    protected NewTaskInst(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     /**
     * Produces a New Task Instruction, according to the specified instruction type.
     *
     * @param input the original command called.
     * @return the Modify Listed Task Instruction with the specified task number to delete.
     * @throws InvalidInputException when no details are provided, or the format of the input
     *          is wrong.
     */
    public static NewTaskInst of(String input) throws InvalidInputException {
        //to do tasks are not shown to prevent improper highlighting of comments
        String[] split = input.split(" ", 2);

        if (split.length == 1) { // happens when: "deadline", "event"
            throw MISSING_TASK_DETAILS_EXCEPTION;
        }

        String taskDetails = split[1].strip();
        if (taskDetails.length() == 0) { // happens when: "deadline ", "event "
            throw MISSING_TASK_DETAILS_EXCEPTION;
        }

        // At this point, the instruction must be: "deadline <x>" with x being
        // not completely full of spaces.
        String instType = split[0];
        switch (instType) {
        case "todo":
            return TodoInst.of(taskDetails);
        case "deadline":
            return DeadlineInst.of(taskDetails);
        case "event":
            return EventInst.of(taskDetails);
        default:
            // will not reach here, as Instruction.of controls the main flow
            // of instructions
            return null;
        }
    }

    /**
     * Gets the task details of this New Task Instruction.
     *
     * @return the task details.
     */
    public String getTaskDesc() {
        return taskDesc;
    }
}
