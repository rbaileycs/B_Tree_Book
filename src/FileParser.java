/**
 * FileParser parses through a given file and looks for certain
 * key values. Once those key values are found, FileParser will take
 * the data required from the file and assign it to the string word.
 * Then newWord will replace any unnecessary data with an empty space.
 * longString then appends the information to itself as FileParser moves
 * through the file. Finally, subber splits longString into substrings
 * and passes it to GeneConverter, which converts it to binary, and finally
 * to newTree (a BTree object).
 *
 * @author Ryan Bailey
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileParser {

    private final int k;
    private final File f;
    private final BTree newTree;
    private String longString = "";

    /**
     * Constructor for FileParser.
     *
     * @param newFile file to be read
     * @param subStringSize size of substrings
     * @param order degree of the BTree
     */
    public FileParser(File newFile, int subStringSize, int order){

        this.f = newFile;
        this.k = subStringSize;
        newTree = new BTree(order);
    }

    /**
     * Main logic block for FileParser. See header comment for explanation.
     *
     * @throws FileNotFoundException throws exception just in case file is not found
     */
    public void parseFromOriginToKey() throws FileNotFoundException {

        int i = 0;
        Scanner scan = new Scanner(this.f);

        while(scan.hasNext()){
            if(scan.next().equalsIgnoreCase("ORIGIN")){
                while(scan.hasNext()){
                    String word = scan.next().replaceAll("[\\d]+", "");
                    if (word.equalsIgnoreCase("//")){
                        break;
                    }
                    longString  = longString + word;
                }
                while(i+(this.k-1) <= longString.length()){
                    String subber = longString.substring(i, i + (this.k - 1));
                    if(subber.contains("n")){
                        i++;
                    }else{
                        newTree.insert(newTree, GeneConverter.toLong(subber));
                        i++;
                    }
                }
            }
        }
    }
}
