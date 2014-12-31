/**
 * Description: transform for factorial problem.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep. 19, 2002
 *
 * @version 1.0
 */
package Global.math;

public class DimTransformer {
  static public int getTotalNum(int[] realD, int index) {
    int totalNum = 1;
    for (int i=0; i<index; i++) {
      totalNum *= (realD[i]);
    }
    return totalNum;
  }

  static public int getTotalNum(int[] realD) {
    return getTotalNum(realD, realD.length);
  }

  static public int multiDToInt(int[] coodinates, int[] realD) {
    int value = 0;

    for(int i=0; i<coodinates.length; i++) {
      value += coodinates[i]*getTotalNum(realD, i);

    }
    return value;
  }

  static public int[] compressedDToRealD(int[][] compressedDRadix, int[] data) {
    int radixLen = 0;
    for (int i=0; i<compressedDRadix.length; i++) {
      radixLen += compressedDRadix[i].length;
    }
    int[] realD = new int[radixLen];
    for (int k=0; k<data.length; k++) {
      int[] subCoor = intToMutiD(data[k], compressedDRadix[k]);
      for (int l=0; l<subCoor.length; l++) {
        realD[compressedDRadix[k][l]-2] = subCoor[l];
      }
    }
    return realD;
  }

  static public int[] intToMutiD(int value, int[] realD) {
    int[] perCoeffs = new int[realD.length];
    for (int i=0; i<perCoeffs.length; i++) {
      perCoeffs[i] = 0;
    }
    int currentValue=value;
    for (int i=perCoeffs.length-1; i>=0; i--) {
      perCoeffs[i] = currentValue/getTotalNum(realD, i);
      currentValue = currentValue%getTotalNum(realD, i);
    }
    return perCoeffs;
  }


}
