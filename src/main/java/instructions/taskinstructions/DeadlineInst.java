package instructions.taskinstructions;

import java.time.LocalDateTime;

import date.time.DateTimeParser;
import exceptions.InvalidInputException;
import tasks.DeadlineTask;
import tasks.TaskList;


/**
 * This class represents an instruction for a new Deadline task.
 * Format: "deadline P /by Q",
 *         where P is the task description, and Q is the deadline, in yyyy-mm-dd hh:mm.
 *
 * @author Ong Han Yang
 */
public class DeadlineInst extends NewTaskInst {
    /** The deadline for the task. */
    private LocalDateTime deadline;

    /**
     * Constructs a Deadline Instruction for a Deadline Task.
     *
     * @param taskDesc the task description.
     * @param deadline the deadline for the task.
     */
    private DeadlineInst(String taskDesc, LocalDateTime deadline) {
        super(taskDesc);
        this.deadline = deadline;
    }

    /**
     * Produces a Deadline Instruction.
     *
     * @param taskDetails the details of the instruction. This includes both
     *                    the description and the deadline.
     * @return the Deadline Instruction.
     * @throws InvalidInputException when no details are provided, the wrong number of details
     *          provided, when either the timing or description is omitted, or when the given
     *          time/date format is wrong.
     */
    public static DeadlineInst of(String taskDetails) throws InvalidInputException {
        String[] split = taskDetails.split(" /by ");
        // a correct format will produce a String[2].

        if (split.length == 1) {
            //happens in "deadline /by ", deadline  /by b", "deadline a /byb" etc
            split = taskDetails.split("/by");
            if (split.length == 2 && split[0].length() != 0) {
                //happens in "deadline a/at b", "deadline a /atb" etc
                throw MISSING_SPACES_EXCEPTION;
            }
            throw MISSING_TASK_DETAILS_EXCEPTION;
        }
        if (split.length >= 3) { // happens with multiple " /by "s
            throw TOO_MANY_ARGUMENTS_EXCEPTION;
        }

        DateTimeParser.checkValidFormat(split[1]); // if invalid, an exception will be thrown.

        LocalDateTime deadline = DateTimeParser.parse(split[1]);

        return new DeadlineInst(split[0], deadline);
    }

    /**
     * Gets the deadline for this task.
     *
     * @return the deadline for this task.
     */
    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    /**
     * Adds a deadline task to the given taskList.
     *
     * @param taskList the taskList to have a deadline task added to.
     * @return the feedback message after performing this instruction.
     */
    @Override
    public String doInst(TaskList taskList) {
        DeadlineTask task = DeadlineTask.of(super.getTaskDesc(), this.deadline);
        taskList.add(task);
        return String.format("Okay, added this task:\n%s\nThere are %d tasks in the list now.",
                task, taskList.length());
    }
}
