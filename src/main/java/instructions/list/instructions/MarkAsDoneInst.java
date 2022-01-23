package instructions.list.instructions;

import exceptions.NoSuchTaskException;
import tasks.TaskList;

/**
 * This class represents a Mark Task as Done Instruction.
 * Format: "mark x", where x is the task number to be marked done.
 *
 * @author Ong Han Yang
 */
public class MarkAsDoneInst extends ModifyListedTaskInst {
    /**
     * Constructs a Mark Task as Done Instruction.
     *
     * @param taskNum the task number to mark as done.
     */
    private MarkAsDoneInst(int taskNum) {
        super(taskNum);
    }

    /**
     * Produces a Mark Task as Done Instruction.
     *
     * @param taskNum the task number to mark as done.
     * @return the Mark Task as Done Instruction.
     */
    protected static MarkAsDoneInst of(int taskNum) {
        return new MarkAsDoneInst(taskNum);
    }

    /**
     * Marks a task from the taskList as done.
     *
     * @param taskList the taskList to modify.
     * @return the feedback message after performing this instruction.
     * @throws NoSuchTaskException when there does not exist a task with the given index.
     */
    @Override
    public String doInst(TaskList taskList) throws NoSuchTaskException {
        taskList.markTask(super.getTaskNum() - 1, true);
        return String.format("Okay, this task is done:\n%s"
                , taskList.displayTask(super.getTaskNum() - 1));
    }
}
