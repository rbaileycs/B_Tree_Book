import java.io.File;
import java.io.FileNotFoundException;

public class GeneBankCreateBTree {

    public static void main(String[] args) throws FileNotFoundException {

        int degree = 0;
        int sequenceLength;

        if (args.length <3 || args.length >4) {

            //Prints error message if args are incorrect
            UsagePrinter.errorMessage(1);
            System.exit(1);
        }
        else if(Integer.valueOf(args[2]) > 31 || Integer.valueOf(args[2]) < 0){

            //Prints if substring length is not correct
            UsagePrinter.errorMessage(2);
            System.exit(1);
        }
        else if(Integer.valueOf(args[0]) < 2){

            //Prints is degree is less than 2
            UsagePrinter.errorMessage(3);
            System.exit(1);
        }

        File file = new File(args[1]);

        if(!file.exists()){

            //Prints if the file being passed in cannot be found
            UsagePrinter.errorMessage(4);
            System.exit(1);
        }

        if(Integer.valueOf(args[0]) == 0){

            degree = 7; //Optimum degree for disk block size of 4096

        } else{

            degree = Integer.valueOf(args[0]);
        }

        sequenceLength = Integer.valueOf(args[2]);

        FileParser parse = new FileParser(degree, file, sequenceLength);
        parse.parseFromOriginToKey();

    }

}
