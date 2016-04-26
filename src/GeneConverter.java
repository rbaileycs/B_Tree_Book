/**
 * This class features functions to convert substrings to binary
 * and binary "strings" to strings.
 *
 * @author Ryan Bailey
 */

import java.util.Arrays;
public class GeneConverter {

    protected static int subLength;

    public GeneConverter(int subLength){
        this.subLength = subLength;
    }
    /**
     * This function takes in a sub string from FileParser
     * and turns it into binary by splitting the sub string
     * into individual letters (ex. 'atg' to 'a' 't' 'g')
     * and turning those letters into binary. It then appends
     * the letters' binary form to smask.
     *
     * @param key sub string from FileParser
     * @return the appended binary long smask
     */
    protected static long toLong(String key){

        long mask = 0x00, smask = 0x00;

        for (int i = 0; i < key.length(); i++){
            char c  = key.toLowerCase().charAt(i);
            switch(c) {
                case 'a':
                    mask = 0x00;
                    break;
                case 'c':
                    mask = 0x01;
                    break;
                case 'g':
                    mask = 0x02;
                    break;
                case 't':
                    mask = 0x03;
                    break;
            }
            smask |= (mask << i*2);
        }
        //System.out.println("SMASK: " + Long.toBinaryString(smask));
        //System.out.println("TO STRING: " + toString(smask));
        return smask;
    }


    /**
     *TODO:Finish this function
     */
    protected static String toString(long key){

        String binStr = Long.toBinaryString(key);
        int length = 256 - binStr.length();
        char[] padArray = new char[length];
        Arrays.fill(padArray, '0');
        String padString = new String(padArray);
        binStr = padString + binStr;

        String rtnStr = "";
        for(int i = 256-(2*subLength-1); i < 256 ; i=i+2) {
            //System.out.println("binStr.charAt(i): " + binStr.charAt(i) + " i: " + i);
            //System.out.println("binStr: " + binStr);
            switch(binStr.charAt(i)) {
                case '0':
                    if(binStr.charAt(i-1) == '0') {
                        rtnStr += "a";
                    }
                    if(binStr.charAt(i-1) == '1') {
                        rtnStr += "c";
                    }
                    break;
                case '1':
                    if(binStr.charAt(i-1) == '0') {
                        rtnStr += "g";
                    }
                    if(binStr.charAt(i-1) == '1') {
                        rtnStr += "t";
                    }
                    break;
                default:
                    rtnStr += "";
                    break;
            }
        }
        return rtnStr;
    }

}
