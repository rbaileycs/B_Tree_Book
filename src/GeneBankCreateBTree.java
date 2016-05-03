/**
 * The GeneBankCreateBTree class contains the main function and initial
 * logic for creating a BTree. It first checks for the correctness of
 * the arguments being passed in. If they are not correct, error messages will be
 * displayed (handled in the UsageError class). Otherwise, it creates a
 * FileParser object to pass the file being parsed into the parsing class for
 * data manipulation.
 *
 * @author Ryan Bailey
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GeneBankCreateBTree {

    /**
     * The main class for creating BTree
     *
     * @param args command line arguments being passed in
     * @throws FileNotFoundException exception for files not found
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

        /**
         * Proper usage for this program:
         *
         * java GeneBankCreateBTree <degree> <gbk file> <sequence length> [<debug level>]
         */

        int degree; //degree of BTree. Should be 2 or greater.
        int sequenceLength; //Size of sub-sequences. Should be between 1 and 31.

        /**
         * This section checks the args[] array for correctness.
         * All error handling should be done here.
         */
        if (args.length <3 || args.length >4) {

            //Prints error message if args are incorrect
            UsagePrinter.errorMessage(1);
            System.exit(1);
        }
        else if(Integer.valueOf(args[2]) > 31 || Integer.valueOf(args[2]) < 1){

            //Prints if substring length is not correct
            UsagePrinter.errorMessage(2);
            System.exit(1);
        }
        else if(Integer.valueOf(args[0]) < 2){

            //Prints is degree is less than 2
            UsagePrinter.errorMessage(3);
            System.exit(1);
        }

        //File to be passed into the FileParser
        File file = new File(args[1]);

        if(!file.exists()){

            //Prints if the file being passed in cannot be found
            UsagePrinter.errorMessage(4);
            System.exit(1);
        }

        /**
         * Determines the value of degree. If degree is zero, an optimal size
         * based on disk block size 4096 is assigned.
         */
        if(Integer.valueOf(args[0]) == 0){

            degree = 7; //Optimum degree for disk block size of 4096

        } else{

            degree = Integer.valueOf(args[0]);
        }

        sequenceLength = Integer.valueOf(args[2]);

        FileParser parse = new FileParser(degree, file, sequenceLength);
        parse.parseFromOriginToKey();

        if(Integer.valueOf(args[3]) == 1){
            dumperLicken.dumperDoodle(parse);
        }
    }
}
