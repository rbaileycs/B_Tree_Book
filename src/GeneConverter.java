/**
 * This class features functions to convert substrings to binary
 * and binary "strings" to strings.
 *
 * @author Ryan Bailey
 */

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
        return smask;
    }


    /**
     * This function takes in a key value and turns it
     * back into a gene sequence as it is in the test files.
     *
     * @param key the key value to be converted
     */
    protected static String toString(long key) {

        long sBits;
        String rtnStr = "";

        for(int i = 0; i < subLength-1; i++){

            sBits = key;
            sBits = sBits >> 2*i;
            sBits = sBits & 0x003;

            switch((int)sBits) {
                case 0:
                    rtnStr += "a";
                    break;

                case 1:
                    rtnStr += "c";
                    break;

                case 2:
                    rtnStr += "g";
                    break;

                case 3:
                    rtnStr += "t";
                    break;

                default:
                    rtnStr += "";
                    break;
            }
        }
        return rtnStr;
    }
}

