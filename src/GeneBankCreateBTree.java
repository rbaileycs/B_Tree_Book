import java.io.File;
import java.io.FileNotFoundException;

public class GeneBankCreateBTree {

    public static void main(String[] args) throws FileNotFoundException {

        if (args.length <3 || args.length >4)
        {
            UsagePrinter.errorMessage(1); //Prints error message if args are incorrect
        }

        File file = new File(args[0]);
        FileParser parse = new FileParser(file, 3, 3);
        parse.parseFromOriginToKey();

    }

}
