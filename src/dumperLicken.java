
/**
 * Created by ryan on 4/27/16.
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
