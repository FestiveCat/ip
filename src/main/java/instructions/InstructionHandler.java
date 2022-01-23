package instructions;

import Exceptions.NoSuchTaskException;
import instructions.list.instructions.*;
import tasks.TaskList;

/**
 * This class encapsulates an handler for instructions. It allows instructions
 * to perform their action on a given input.
 *
 * @author Ong Han Yang
 */
public class InstructionHandler {
    /** The task list that the handler is modifying */
    private TaskList taskList;

    /**
     * Constructs an Instruction Handler.
     * @param taskList the tasklist to initialise the handler with.
     */
    private InstructionHandler(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Produces an Instruction Handler.
     * @param taskList the tasklist to initialise the handler with.
     * @return the Instruction Handler.
     */
    public static InstructionHandler of(TaskList taskList) {
        return new InstructionHandler(taskList);
    }

    /**
     * Allows the instruction to perform its function. Returns a feedback
     * message about the status of the action.
     *
     * @param inst the given instruction to perform.
     * @return the feedback message about the status of the action.
     */
    public String doInstruction(Instruction inst) {
        try {
            return inst.doInst(taskList);
        } catch (NoSuchTaskException e) {
            // This is okay as only methods that need to modify the tasks in
            // the tasklist, would throw a NoSuchTaskException.
            ModifyListedTaskInst castedInst = (ModifyListedTaskInst) inst;
            return String.format("There is no task %d!", castedInst.getTaskNum());
        }
    }
}
