import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QueryFilter{

    private File file;
    private char seqLen;
    private String fileName;
    private int subLength;
    protected static ArrayList <Long> queries;

    public static ArrayList<Long> makeFilter() throws FileNotFoundException {
        File file = new File("../queries/query3");
        Scanner scan = new Scanner(file);
        queries = new ArrayList <>();
        while(scan.hasNext()){
            String word = scan.next();
            queries.add(GeneConverter.toLong(word));
        }
        return queries;
    }

    public static void main(String[] args) throws FileNotFoundException{

       makeFilter();
    }
}