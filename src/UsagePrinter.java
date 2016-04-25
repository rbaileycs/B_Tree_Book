/**
 * This class is meant to contain error messages for instances when the
 * command line arguments aren't correct.
 * ALL ERROR MESSAGES ARE TO BE WRITTEN HERE
 *
 * @author Ryan Bailey
 */
public class UsagePrinter {
    /**
     * This method displays error messages for the different classes. This approach is
     * being used to cut down on unnecessary code in the main classes.
     * @param n determines which code to display
     */
    static void errorMessage(int n) {
        switch (n) {
            case 1: //Main error message display for GeneBankCreateBTree.java
                System.out.println("To use this program, please enter the following arguments:\n\n"
                        + "\tjava GeneBankCreateBTree <degree> <gbk file> <sequence length> [<debug level>]\n\n"
                        + "degree: The degree for the BTree\n"
                        + "gbk file: a gene bank file\n"
                        + "sequence length: an integer between 1 and 31\n"
                        + "debug level*: an integer 0 or 1\n"
                        + "\n*debug level is optional. The input for debug level is:\n"
                        + "\t0: Any diagnostic messages, help and status messages must be be printed on standard error stream.\n"
                        + "\t1: The program writes a text file named dump, that has the following line format:\n"
                        + "\t\t<frequency> <DNA string>.\n"
                        + "\t   The dump file contains frequency and DNA string (corresponding to the key stored) in an inorder traversal.");

            case 2: //Error message if substring length is not correct
                System.out.println("Substring length must be between 0 and 31.");

            case 3: //Error message if degree is less than 2
                System.out.println("The degree value must be greater than 2");

            case 4: //Error message if file is not found
                System.out.println("The file cannot be found.");
        }
    }
}