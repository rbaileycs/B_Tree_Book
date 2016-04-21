/**
 * This class features functions to convert substrings to binary
 * and binary "strings" to strings.
 *
 * @author Ryan Bailey
 */
public class GeneConverter {
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
        return smask;
    }


    /**
     *TODO:Finish this function
     */
    protected static String toString(long key, int subLength){
        return "0";
    }

}
