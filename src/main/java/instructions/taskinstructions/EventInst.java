package instructions.taskinstructions;

import java.time.LocalDateTime;

import date.time.DateTimeParser;
import exceptions.InvalidInputException;
import tasks.EventTask;
import tasks.TaskList;

/**
 * This class represents an instruction for a new Event task.
 * Format: "event P /at Q /until R",
 *         where P is the task description,
 *         Q is the start time, in yyyy-mm-dd hh:mm,
 *         and R is the end time, in yyyy-mm-dd hh:mm.
 *
 * @author Ong Han Yang
 */
public class EventInst extends NewTaskInst {
    /** The start time. */
    private LocalDateTime startTime;

    /** The end time. */
    private LocalDateTime endTime;

    /**
     * Constructs an Event Task Instruction.
     *
     * @param taskDesc description for the task.
     * @param startTime the starting time of the event.
     * @param endTime the ending time of the event.
     */
    private EventInst(String taskDesc, LocalDateTime startTime, LocalDateTime endTime) {
        super(taskDesc);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Produces an Event Instruction.
     *
     * @param taskDetails the details of the instruction. This includes both
     *                    the description and the timing.
     * @return the Event Instruction.
     * @throws InvalidInputException when no details are provided, the wrong
     *                               number of details provided, when either
     *                               the timing or description is omitted, or
     *                               when the given time/date format is wrong.
     */
    public static EventInst of(String taskDetails) throws InvalidInputException {
        String[] split = taskDetails.split(" /at ");
        // a correct format will produce a String[2].

        // check for the " /at " and figure out where the problem is if it is missing
        if (split.length == 1) {
            //happens in "event /at ", event /at b", "event a /atb" etc
            split = taskDetails.split("/at");
            if (split.length == 2 && split[0].length() != 0) {
                //happens in "event a/at b", "event a /atb", "event a/atb"
                throw MISSING_SPACES_EXCEPTION;
            }
            throw MISSING_TASK_DETAILS_EXCEPTION;
        }
        if (split.length >= 3) { // happens with multiple " /at "s
            throw TOO_MANY_ARGUMENTS_EXCEPTION;
        }


        String[] timings = split[1].split(" /until ");
        // a correct format will produce a String[2].

        // check for the " /until " and figure out where the problem is if it is missing
        if (timings.length == 1) {
            //happens in "/at /until", "/at a /until " or similar
            split = split[1].split("/until");
            if (split.length == 2 && split[0].length() != 0) {
                // happens in "/at a/until b" or similar
                throw MISSING_SPACES_EXCEPTION;
            }
            throw MISSING_TASK_DETAILS_EXCEPTION;
        }
        if (timings.length >= 3) {
            throw TOO_MANY_ARGUMENTS_EXCEPTION;
        }

        // if either of the timings are invalid, an exception will be thrown
        DateTimeParser.checkValidFormat(timings[0]);
        DateTimeParser.checkValidFormat(timings[1]);

        LocalDateTime startTime = DateTimeParser.parse(timings[0]);
        LocalDateTime endTime = DateTimeParser.parse(timings[1]);

        return new EventInst(split[0], startTime, endTime);
    }

    /**
     * Adds an event task to the given taskList.
     *
     * @param taskList the taskList to have an event task added to.
     * @return the feedback message after performing this instruction.
     */
    @Override
    public String doInst(TaskList taskList) {
        EventTask task = new EventTask(super.getTaskDesc(), this.startTime, this.endTime);
        taskList.add(task);
        return String.format("Okay, added this task:\n%s\nThere are %d tasks in the list now.",
                task, taskList.length());
    }
}
