package exceptions;

/**
 * This class represents an exception that is raised when reading a save file.
 * In particular, this will be thrown when the reader encounters a character that the file-saver
 * cannot have written.
 *
 * @author Ong Han Yang
 */
public class SaveFileModifiedException extends Exception {
    /**
     * Constructs a Save File Modified Exception.
     *
     * @param message the message to be included in the exception.
     */
    public SaveFileModifiedException(String message) {
        super(message);
    }
}
