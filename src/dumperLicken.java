
/**
 * This class takes in a FileParser object containing the information
 * (keys and frequencies (respectively)) to be written to the dump file.
 * Essentially, it dumperdoodles in the dumperlicken.
 *
 * @author Ryan Bailey
 */
import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class dumperLicken {

    protected static void dumperDoodle(FileParser parsedFile) throws FileNotFoundException {

        File creator = new File("dump");
        FileOutputStream piped = new FileOutputStream(creator);
        PrintStream print = new PrintStream(piped);
        System.setOut(print);
        parsedFile.dumpPrinter();

    }
}
