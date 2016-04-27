/**
 * This class searches the BTree file created by BTree
 * and the query class for any specified duplicates and
 * their frequencies.
 *
 * @author Ryan Bailey
 */
import java.io.File;
import java.io.FileNotFoundException;

public class GeneBankSearch {
    /**
     * Main class for GeneBankSearch
     * @param args args passed from command line
     */
    public static void classer (String[] args) throws FileNotFoundException{
        /**
         * Proper usage for this class:
         *
         * java GeneBankSearch <btree file> <query file> [<debug level>]
         */

        if(args.length < 1 || args.length > 2){
            UsagePrinter.errorMessage(5);
        }

        File bTreeFile = new File(args[0]);
        File queryFile = new File(args[1]);

        if(!bTreeFile.exists()){
           UsagePrinter.errorMessage(6);
        }
        else if(!queryFile.exists()){
            UsagePrinter.errorMessage(7);
        }
    }
}
