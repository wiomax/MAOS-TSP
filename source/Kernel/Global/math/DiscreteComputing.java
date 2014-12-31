/**
 * Description: Some combinatorial functions and algorithms.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 19, 2003    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.math;


public class DiscreteComputing {

/* perform pow calculation for integer value
 **/
  static public int intPow(int value, int exp) {
    int totalNum = 1;
    for (int i=0; i<exp; i++) {
      totalNum *= value;
    }
    return totalNum;
  }

  static public int factorial(int totalBits) {
    int result = 1;
    for (int i=1; i<=totalBits; i++) {
      result *= i;
    }
    return result;
  }

/* perform permutation calculation
 **/
  static public int permutation(int totalBits, int combineBits) {
    int result = 1;
    for (int i=totalBits-combineBits+1; i<=totalBits; i++) {
      result *= i;
    }
    return result;
  }

/* perform combination calculation
 **/
  static public int combination(int totalBits, int combineBits) {
    return permutation(totalBits,combineBits)/factorial(combineBits);
  }

/* get combinations
 **/
  static public int[][] getCombinations(int totalBits, int combineBits) {
    int[][] results = new int[combination(totalBits, combineBits)][combineBits];

    for (int i=0; i<combineBits; i++){
      results[0][i] = i;
    }
    for (int i=1; i<results.length;i++) {
      results[i] = (int[])results[i-1].clone();
      results[i][combineBits-1]++;
      int j=combineBits-1;
      while (results[i][j]>totalBits-combineBits+j) {
        results[i][j-1]++;
        for (int k=j; k<combineBits; k++){
          results[i][k] = results[i][k-1]+1;
        }
        j--;
      }
    }
    return results;
  }

  static public int ceil(int value, int divN) {
    return (int)(Math.ceil(value/(double)divN));
  }

  static public int floor(int value, int divN) {
    return (int)(Math.floor(value/(double)divN));
  }
  
  static public int getIntValue(boolean[] bArray) {
    int value = 0;
    int mod = 1;
    for (int i=bArray.length-1; i>=0; i--) {
      if (bArray[i]) {
        value += mod;
      }
      mod *= 2;
    }
    return value;
  }

  static public long getLongValue(boolean[] bArray) {
    long value = 0;
    int mod = 1;
    for (int i=bArray.length-1; i>=0; i--) {
      if (bArray[i]) {
        value += mod;
      }
      mod *= 2;
    }
    return value;
  }
}

