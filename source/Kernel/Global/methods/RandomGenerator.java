/**
 * Description: For generating random numbers.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 22, 2001    xiaofengxie@tsinghua.org.cn
 *
 */

package Global.methods;

import java.util.*;
import Global.basic.data.collection.*;

public class RandomGenerator {

  /**This function returns a random integer number between the lowLimit and upLimit.
   * @param lowLimit lower limits
   *        upLimit The upper limits (between which the random number is to be generated)
   * @return int return value
   * Example: for find [0,1,2]
   */
  public static int intRangeRandom(int lowLimit, int upLimit) {
    return lowLimit + intRangeRandom(upLimit - lowLimit + 1);
  }

  public static int intRangeRandom(int size) {
    return (int) Math.floor(doubleRangeRandom(0, size - 1E-10));
  }

  public static int intRangeRandomWithExceptID(int size, int exceptID) {
    if (size == 1 || exceptID < 0 || exceptID >= size) {
      return -1;
    }
    int rndV = intRangeRandom(size - 1);
    if (rndV >= exceptID) {
      rndV++;
    }
    return rndV;
  }

  /**This function returns a random float number between the lowLimit and upLimit.
   * @param lowLimit lower limits
   *        upLimit The upper limits (between which the random number is to be generated)
   * @return double return value
   */
  public static double doubleRangeRandom(double lowLimit, double upLimit) {
    return lowLimit + Math.random() * (upLimit - lowLimit);
  }

  public static double doubleRangeRandom() {
    return Math.random();
  }

  public static int getIntProbV(double rValue) {
    if (rValue <= 0)return 0;
    int iValue = (int) rValue;
    if (Math.random() < rValue - iValue) {
      iValue++;
    }
    return iValue;
  }

  public static int getAllUniformLength(int size){
    int len = 0;
    for (int i=0; i<size; i++) {
      if (booleanRandom()) len ++;
    }
    return len;
  }

  /**This function returns true or false with a random probability.
   */
  public static boolean booleanRandom(){
    if (Math.random()<0.5) return false;
    return true;
  }

  public static void randomBlindSelection(int[] storeArray, int maxNum) {
    int upper = maxNum-1;
    for(int i=0; i<storeArray.length; i++) {
      storeArray[i] = intRangeRandom(0, upper);
    }
  }

  public static int[] randomBlindSelection(int maxNum, int times) {
    int[] storeArray = new int[times];
    randomBlindSelection(storeArray, maxNum);
    return storeArray;
  }

  private static DualIAlienArray idArray = null;
  private static void randomDistinctSelection(int[] indicesSaver, int times) {
    int selID;
    for (int i=0; i<times; i++) {
      selID = idArray.getRandomElement();
      indicesSaver[i] = selID;
      idArray.removeElement(selID);
    }
  }

  private static void resetIDArray(int maxNum) {
    if (idArray==null||idArray.getMaxSize()<maxNum) {
      idArray = new DualIAlienArray(maxNum);
    } else {
      idArray.clear();
    }
  }

  private static void initIDArray(int maxNum) {
    resetIDArray(maxNum);
    for (int i=0; i<maxNum; i++) {
      idArray.addElement(i);
    }
  }

  public static int intRangeRandom(boolean[] flags, boolean selFlag) {
    initIDArray(flags, selFlag);
    return idArray.getRandomElement();
  }

  private static void initIDArray(boolean[] flags, boolean selFlag) {
    resetIDArray(flags.length);
    for (int i=0; i<flags.length; i++) {
      if (flags[i]==selFlag) {
        idArray.addElement(i);
      }
    }
  }

  public static void randomDistinctSelection(int[] indicesSaver, int maxNum, int times) {
    initIDArray(maxNum);
    randomDistinctSelection(indicesSaver, times);
  }

  public static void randomDistinctSelection(int maxNum, int[] indicesSaver) {
    randomDistinctSelection(indicesSaver, maxNum, indicesSaver.length);
  }

  public static void randomDistinctSelection(int[] indicesSaver) {
    randomDistinctSelection(indicesSaver.length, indicesSaver);
  }

  public static int[] randomDistinctSelection(int maxNum) {
    return randomDistinctSelection(maxNum, maxNum);
  }

  public static int[] randomDistinctSelection(int maxNum, int times) {
    int[] indicesSaver = new int[times];
    randomDistinctSelection(maxNum, indicesSaver);
    return indicesSaver;
  }

  // For 0 - small times or large times - maxNum
  private static IVector tempV = new IVector();
  public static int[] randomNoadjecentSelection(int maxNum, int times) {
    int[] indicesSaver = new int[times];
    randomNoadjecentSelection(indicesSaver, maxNum);
    return indicesSaver;
  }

  public static int[] randomNoadjecentSelection(int[] indicesSaver, int maxNum) {
    tempV.clear();
    for (int i=0; i<maxNum; i++) {
      tempV.add(i);
    }
    randomNoadjecentSelection(indicesSaver, tempV);
    return indicesSaver;
  }

  //For full
  private static void randomNoadjecentSelection(int[] indicesSaver, IVector selectableIDs) {
    int number = indicesSaver.length;
    int selectIndex = -1;
    int currSize = -1;
    for (int i=0; i<number; i++) {
      currSize = selectableIDs.size();
      selectIndex = intRangeRandom(selectableIDs.size());
      indicesSaver[i] = selectableIDs.elementAt(selectIndex);
      if (selectIndex==0) {
        selectableIDs.removeElementAt(currSize-1);
        selectableIDs.removeElementAt(0);
        selectableIDs.removeElementAt(0);
      } else if (selectIndex==currSize-1) {
        selectableIDs.removeElementAt(currSize-1);
        selectableIDs.removeElementAt(currSize-2);
        selectableIDs.removeElementAt(0);
      } else {
        selectableIDs.removeElementAt(selectIndex);
        selectableIDs.removeElementAt(selectIndex);
        selectableIDs.removeElementAt(selectIndex-1);
      }
    }
  }

  public static int[] randomOrderedDistinctSelection(int low, int up, int times){
    int[] totalIndices = new int[up-low];
    for (int i=low; i<up; i++) {
      totalIndices[i] = i;
    }
    return randomOrderedDistinctSelection(totalIndices, times);
  }

  public static int[] randomOrderedDistinctSelection(int[] totalIndices, int times) {
    if (times>=totalIndices.length) {
      return totalIndices;
    }
    int[] indices = randomOrderedDistinctSelection(totalIndices.length, times);
    for(int i=0; i<indices.length; i++) {
      indices[i] = totalIndices[indices[i]];
    }
    return indices;
  }

  // For 0 - small times or large times - maxNum
  public static void randomOrderedDistinctSelection(boolean[] flags, int[] indices, int maxNum, int realTimes) {
    boolean isBelowHalf = realTimes<maxNum*0.5;
    int virtualTimes = realTimes;
    if(!isBelowHalf) {
      virtualTimes = maxNum-realTimes;
    }
    int i = 0;

    while(i<virtualTimes) {
      indices[i] = intRangeRandom(maxNum);
      if(!flags[indices[i]]) {
        flags[indices[i]] = true;
        i++;
      }
    }
    if(!isBelowHalf) {
      int j=0;
      for(i=0; i<maxNum; i++) {
        if(flags[i]==isBelowHalf) {
          indices[j] = i;
          j++;
          if(j==realTimes) break;
        }
      }
    }
  }

  // For 0 - small times or large times - maxNum, return ordered data
  public static int[] randomOrderedDistinctSelection(int maxNum, int times) {
    if(times<=0) return new int[0];
    int realTimes = Math.min(maxNum, times);
    boolean[] flags = new boolean[maxNum];
    Arrays.fill(flags, false);
    boolean isBelowHalf = times<maxNum*0.5;
    int virtualTimes = realTimes;
    if(!isBelowHalf) {
      virtualTimes = maxNum-realTimes;
    }
    int i = 0;
    int[] indices = new int[realTimes];

    while(i<virtualTimes) {
      indices[i] = intRangeRandom(maxNum);
      if(!flags[indices[i]]) {
        flags[indices[i]] = true;
        i++;
      }
    }
    if(!isBelowHalf) {
      int j=0;
      for(i=0; i<maxNum; i++) {
        if(flags[i]==isBelowHalf) {
          indices[j] = i;
          j++;
          if(j==realTimes) break;
        }
      }
    }
    return indices;
  }

}
