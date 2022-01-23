package instructions.list.instructions;

import Exceptions.NoSuchTaskException;
import tasks.Task;
import tasks.TaskList;

/**
 * This class represents a Delete Task Instruction.
 * Format: "Delete x", where x is an integer.
 *
 * @author Ong Han Yang
 */
public class DeleteInst extends ModifyListedTaskInst {
    /**
     * Constructs a Delete Task Instruction.
     *
     * @param taskNum the task number to delete.
     */
    private DeleteInst(int taskNum) {
        super(taskNum);
    }

    /**
     * Produces a Delete Task Instruction.
     *
     * @param taskNum the task number to delete.
     * @return the Delete Task Instruction.
     */
    protected static DeleteInst of(int taskNum) {
        return new DeleteInst(taskNum);
    }

    /**
     * Deletes a task from the taskList.
     *
     * @param taskList the taskList to modify.
     * @return the feedback message after performing this instruction.
     * @throws NoSuchTaskException when there does not exist a task with the
     *                             given index.
     */
    @Override
    public String doInst(TaskList taskList) throws NoSuchTaskException {
        Task deleted = taskList.delete(super.getTaskNum() - 1);
        return String.format("Okay, I've removed this task:\n%s\nThere are " +
                "%d tasks left in the list!"
                , deleted.toString(), taskList.length());
    }
}
