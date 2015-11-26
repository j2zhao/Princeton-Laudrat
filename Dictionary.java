/*****************************************************************************
  * Princeton Laundrat - Dictionary
  *
  * Created: 11/14/15
  * Last Modified: 11/14/15
  * 
  * Compile: javac Dictionary.java
  * Execute: java Dictionary
  * 
  * Description:
  *     This class designs a dictionary of designed as follows:
  *         | String key1 : int data1 |    (each key accesses a particular int)
  *         | String key2 : int data2 |
  *         | String key3 : int data3 |
  *     Can access a data value with its array index OR corresponding key, and vice
  *     versa.
  *
  *     EACH KEY AND DATA VALUE MUST BE UNIQUE; NO REPEATS
  ***************************************************************************/

public class Dictionary {

    /************************************
    * Instance variables
    ************************************/

    // Parallel arrays such that each key corresponds to data with the same index
    private String[] keys;
    private int[] data;


    /************************************
    * Constructor
    ************************************/

    public Dictionary(String[] k, int[] d) {
        int N = k.length;
        int M = d.length;
        if (N != M) throw new RuntimeException("Different number of keys and data values");

        // Saves new arrays (for immutability)
        keys = new String[N];
        for (int i = 0; i < N; i++) {
            keys[i] = k[i];
        }

        data = new int[M];
        for (int i = 0; i < M; i++) {
            data[i] = d[i];
        }
    }


    /************************************
    * Functions
    ************************************/

    // Returns the index of the query element in the array (for ints)
    private static int indexOf(int query, int[] array) {
        // Compares with each element until a match is found
        int N = array.length;
        for (int i = 0; i < N; i++) {
            if (array[i] == query) {
                return i;
            }
        }

        return -1;
    }

    // Returns the index of the query element in the array (for Strings)
    private static int indexOf(String query, String[] array) {
        // Compares with each element until a match is found
        int N = array.length;
        for (int i = 0; i < N; i++) {
            if (array[i] == query) {
                return i;
            }
        }

        return -1;
    }


    /************************************
    * Operations on the Dictionary object
    ************************************/

    // Returns the data value associated with a given key
    public int dataAt(String key) {
        int i = indexOf(key, keys);
        return data[i];
    }

    // Returns the data value at a given array index
    public int dataAtIndex(int i) {
        return data[i];
    }

    // Returns the key associated with a given data value
    public String keyAt(int value) {
        int i = indexOf(value, data);
        return keys[i];
    }

    // Returns the key at a given array index
    public String keyAtIndex(int i) {
        return keys[i];
    }


    /************************************
    * Test client
    ************************************/
    public static void main(String[] args) {
        // Makes a test Dictionary
        Dictionary dict = new Dictionary(
            new String[] { "First thing", "Second thing", "Third thing" },
            new int[]    {       1,              2,              3      });

        System.out.println("The first data value is " + dict.dataAt("First thing"));
        System.out.println("The third key is " + dict.keyAt(3));
    }
}