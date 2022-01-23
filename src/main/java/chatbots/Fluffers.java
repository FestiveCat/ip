package chatbots;

import exceptions.InvalidInputException;
import exceptions.SaveFileModifiedException;
import instructions.ExitInst;
import instructions.Instruction;
import instructions.list.instructions.DisplayListInst;
import tasks.TaskList;

import java.util.Scanner;

/**
 * This class encapsulates the chatbot Fluffers for CS2103T's Individual Project.
 * Fluffers is a chatbot that helps to manage a task list.
 *
 * ASCII art credit: All ASCII art was found on https://www.asciiart.eu/animals/cats .
 *
 *
 * @author Ong Han Yang
 */
public class Fluffers extends TaskManagerChatbot {
    private enum AsciiArt {
        /** ASCII art for when Fluffers just wakes up*/
        AWAKE(
                "    /\\_____/\\\n" +
                "   /  o   o  \\\n" +
                "  ( ==  ^  == )\n" +
                "   )         (\n" +
                "  (           )\n" +
                " ( (  )   (  ) )\n" +
                "(__(__)___(__)__)"
        ),
        /** ASCII art for when Fluffers goes back to sleep*/
        ASLEEP(
                "      |\\      _,,,---,,_\n" +
                "ZZZzz /,`.-'`'    -.  ;-;;,_\n" +
                "     |,4-  ) )-,_. ,\\ (  `'-'\n" +
                "    '---''(_/--'  `-'\\_)"
        ),
        /** ASCII art for when Fluffers is displaying a list */
        LIST_TOP(
                "    |\\__/,|   (`\\\n" +
                "  _.|o o  |_   ) )\n" +
                "-(((---(((--------"
        );

        private final String art;
        private AsciiArt(String art) {
            this.art = art;
        }
    }



    /**
     * Constructs an awake Fluffers.
     *
     * @throws SaveFileModifiedException when the save file contains invalid
     *                                   symbols that could not have been from
     *                                   the string representation of a task,
     *                                   indicating external modification to
     *                                   the file.
     */
    public Fluffers() throws SaveFileModifiedException {
        super(TaskList.loadFromFile());
    }

    /**
     * Asks Fluffers to speak with fancy formatting.
     *
     * @param input the text that Fluffers is asked to speak.
     * @return the formatted String that Fluffers is asked to speak.
     */
    protected String speak(String input) {
        return String.format("Meow! (%s)\n", input);
    }

    /**
     * Greets the user, with fancy formatting.
     *
     * @return String representation of the greeting.
     */
    protected String greet() {
        return "Activating Cat Translator 2000...\n" +
                "Waking Fluffers up...\n\n" +
                "Meow! (Hello!)\n" +
                AsciiArt.AWAKE.art;
    }

    /**
     * Says goodbye to the user, with fancy formatting.
     *
     * @return String representation of the Farewell.
     */
    protected String farewell() {
        return "Meooow~ (Bye bye! See you again next time!)\n" +
                AsciiArt.ASLEEP.art;
    }

    /**
     * Processes a command and gives a reply according to the command.
     *
     * @param input the input String or command to be given to Fluffers.
     * @return the response given with respect to the given input.
     */
    public String feedCommandAndReply(String input) {
        Instruction inst;
        try {
            inst = Instruction.of(input);
        } catch (InvalidInputException e) {
            return this.speak(e.getMessage());
        }
        if (inst instanceof ExitInst) {
            this.isAwake = false;
            return farewell();
        }
        if (inst instanceof DisplayListInst) {
            return String.format("%s\n%s\n------------------"
                    , AsciiArt.LIST_TOP.art
                    , super.instHandler.doInstruction(inst));
        }
        return this.speak(super.instHandler.doInstruction(inst));
    }

    /**
     * Starts the program/wakes Fluffers up
     *
     * @param args input CLI arguments.
     * @throws SaveFileModifiedException when the save file contains invalid
     *                                   symbols that could not have been from
     *                                   the string representation of a task,
     *                                   indicating external modification to
     *                                   the file.
     */
    public static void main(String[] args) throws SaveFileModifiedException {
        Fluffers f = new Fluffers();

        System.out.println(f.greet());

        Scanner sc = new Scanner(System.in);

        while (f.isAwake) {
            String input = sc.nextLine();
            System.out.println(f.feedCommandAndReply(input));
        }
    }
}
