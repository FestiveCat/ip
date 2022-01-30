package chatbots;

import java.util.Scanner;

import exceptions.InvalidInputException;
import exceptions.SaveFileModifiedException;
import file.management.FileManager;
import instructions.ExitInst;
import instructions.Instruction;
import instructions.listinstructions.DisplayListInst;
import instructions.listinstructions.FindInst;

/**
 * This class encapsulates the chatbot Fluffers for CS2103T's Individual Project.
 * Fluffers is a chatbot that helps to manage a task list.
 *
 * ASCII art credit: All ASCII art was found on https://www.asciiart.eu/animals/cats .
 *
 * @author Ong Han Yang
 */
public class Fluffers extends TaskManagerChatbot {
    private enum AsciiArt {
        /** ASCII art for when Fluffers just wakes up */
        AWAKE("    /\\_____/\\\n"
                + "   /  o   o  \\\n"
                + "  ( ==  ^  == )\n"
                + "   )         (\n"
                + "  (           )\n"
                + " ( (  )   (  ) )\n"
                + "(__(__)___(__)__)"
        ),
        /** ASCII art for when Fluffers goes back to sleep */
        ASLEEP("      |\\      _,,,---,,_\n"
                + "ZZZzz /,`.-'`'    -.  ;-;;,_\n"
                + "     |,4-  ) )-,_. ,\\ (  `'-'\n"
                + "    '---''(_/--'  `-'\\_)"
        ),
        /** ASCII art for when Fluffers is displaying a list */
        LIST_TOP("    |\\__/,|   (`\\\n"
                + "  _.|o o  |_   ) )\n"
                + "-(((---(((--------"
        );

        private final String art;
        private AsciiArt(String art) {
            this.art = art;
        }
    }

    /** Name of the saved file */
    private static String fileName = "saved-taskList.txt";

    /** Folder path to the saved file */
    private static String filePath = "data";

    /**
     * Constructs an awake Fluffers.
     *
     * @throws SaveFileModifiedException when the save file contains invalid symbols that could
     *          not have been from the string representation of a task, indicating external
     *          modification to the file.
     */
    public Fluffers() throws SaveFileModifiedException {
        super(FileManager.loadTaskListFromFile(filePath, fileName));
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
        return "Activating Cat Translator 2000...\nWaking Fluffers up...\n\nMeow! (Hello!)\n"
                + AsciiArt.AWAKE.art;
    }

    /**
     * Says goodbye to the user, with fancy formatting.
     *
     * @return String representation of the Farewell.
     */
    protected String farewell() {
        return "Meooow~ (Bye bye! See you again next time!)\n"
                + AsciiArt.ASLEEP.art;
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
        // check for special inst
        if (inst instanceof ExitInst) {
            this.isAwake = false;
            return farewell();
        }
        if (inst instanceof DisplayListInst || inst instanceof FindInst) {
            String output = String.format("%s\n%s------------------", AsciiArt.LIST_TOP.art,
                    super.instHandler.doInstruction(inst));
            if (inst instanceof FindInst) {
                output = this.speak("Here's what I found! (the numbering is the kept the "
                        + "same as the \"list\" command") + output;
            }
            return output;
        }

        return this.speak(super.instHandler.doInstruction(inst));
    }

    /**
     * Starts the program/wakes Fluffers up.
     *
     * @param args input CLI arguments.
     * @throws SaveFileModifiedException when the save file contains invalid symbols that could
     *          not have been from the string representation of a task, indicating external
     *          modification to the file.
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
