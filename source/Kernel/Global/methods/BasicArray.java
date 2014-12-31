/**
 * Description: provide basic operations for Arrays
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 27, 2005
 */

package Global.methods;

import Global.basic.*;
import java.util.*;

public class BasicArray {

  //all positive value for indicating probability
  static public int getProbSelectIndex(double[] p, boolean isPFixed) {
    return getProbSelectIndex(p, isPFixed, 0, p.length);
  }

  //all positive value for indicating probability
  static public int getProbSelectIndex(double[] p, boolean isPFixed, int startIndex, int endIndex) {
    double[] pv = p;
    if (isPFixed) {
      pv = (double[])p.clone();
    }
    for (int i=startIndex+1; i<endIndex; i++) {
      pv[i] += pv[i-1];
    }
    double randV = Math.random()*pv[endIndex-1];
    for (int i=startIndex; i<endIndex; i++) {
      if (randV<=pv[i]) {
        return i;
      }
    }
    return -1;
  }

//  //finding a particular value in a linear array
//  static public int binaryBetweenSearch(int[] values, int value, int startID, int endID) {
//    if (value<=values[startID]) return startID;
//    if (value>=values[endID]) return endID+1;
//
//    int mid = -1;
//    while(startID<endID) {
//      mid = (int)(endID-startID)/2;
//      if (value > values[mid]) {
//        startID = mid+1;
//      } else if (value < values[mid-1]) {
//        endID = mid-1;
//      } else {
//        break;
//      }
//    }
//    return mid;
//  }
//
  //finding a particular value in a linear array
  static public int binarySearch(int[] values, int value, int startID, int endID) {
    int mid = -1;
    while(startID<=endID) {
      mid = (int)(endID+startID)/2;
      if (value > values[mid]) {
        startID = mid+1;
      } else if (value < values[mid]) {
        endID = mid-1;
      } else {
        break;
      }
    }
    return mid;
  }

  static public int[] getSortedIndices(int[] values) {
    int[] sortedIDs = new int[values.length];
    getSortedIndices(sortedIDs, values);
    return sortedIDs;
  }

  static public void getSortedIndices(int[] sortedIDs, int[] values) {
    getSortedIndices(sortedIDs, values, 0, values.length);
  }

  static public int[] getSortedIndices(int[] values, int startID, int endID) {
    int[] sortedIDs = new int[endID - startID];
    getSortedIndices(sortedIDs, values, startID, endID);
    return sortedIDs;
  }

  private static IndexedIValue[] iiArray;
  static public void getSortedIndices(int[] sortedIDs, int[] values, int startID, int endID) {
    if (iiArray == null || iiArray.length < (endID - startID)) {
      iiArray = new IndexedIValue[endID - startID];
    }
    for (int i = 0; i < endID - startID; i++) {
      iiArray[i] = new IndexedIValue(startID + i, values[startID + i]);
    }
    Arrays.sort(iiArray, 0, endID - startID);
    for (int i = 0; i < sortedIDs.length; i++) {
      sortedIDs[i] = iiArray[i].index;
    }
  }

  static public int[] getSortedIndices(double[] values) {
    int[] sortedIDs = new int[values.length];
    getSortedIndices(sortedIDs, values);
    return sortedIDs;
  }

  static public void getSortedIndices(int[] sortedIDs, double[] values) {
    getSortedIndices(sortedIDs, values, 0, values.length);
  }

  static public int[] getSortedIndices(double[] values, int startID, int endID) {
    int[] sortedIDs = new int[endID - startID];
    getSortedIndices(sortedIDs, values, startID, endID);
    return sortedIDs;
  }

  private static IndexedDValue[] idArray;
  static public void getSortedIndices(int[] sortedIDs, double[] values, int startID, int endID) {
    if (idArray == null || idArray.length < (endID - startID)) {
      idArray = new IndexedDValue[endID - startID];
    }
    for (int i = 0; i < endID - startID; i++) {
      idArray[i] = new IndexedDValue(startID + i, values[startID + i]);
    }
    Arrays.sort(idArray, 0, endID - startID);
    for (int i = 0; i < sortedIDs.length; i++) {
      sortedIDs[i] = idArray[i].index;
    }
  }

  public static int getPrecessorID(int maxID, int ID) {
    return (ID < 1 ? maxID - 1 : ID - 1);
  }

  public static int getSuccessorID(int maxID, int ID) {
    return (ID + 1 >= maxID ? 0 : ID + 1);
  }

  public static int getPeriodDistance(int maxID, int startID, int endID) {
    int diff = endID-startID;
    if (diff<0) diff = maxID+diff;
    return diff;
  }

  public static int getElementNumber(boolean[] elements, boolean specElement) {
    int counts = 0;
    for(int i=0; i<elements.length; i++) {
      if (elements[i]==specElement) {
        counts ++;
      }
    }
    return counts;
  }

  public static int getElementNumber(int[] elements, int specElement) {
    int counts = 0;
    for(int i=0; i<elements.length; i++) {
      if (elements[i]==specElement) {
        counts ++;
      }
    }
    return counts;
  }

  public static int[] getFlagIndices(boolean[] types, boolean flag) {
    int validNum = 0;
    for(int i=0; i<types.length; i++) {
      if(types[i]==flag) {
        validNum++;
      }
    }
    int[] totalIndices = new int[validNum];
    validNum = 0;
    for(int i=0; i<types.length; i++) {
      if(types[i]==flag) {
        totalIndices[validNum] = i;
        validNum++;
        if(validNum==totalIndices.length) break;
      }
    }
    return totalIndices;
  }

  public static int[] getFullIDArray(int size) {
    int[] v = new int[size];
    for (int i=0; i<size; i++) {
      v[i] = i;
    }
    return v;
  }


  static public int getExactIndex(Object[] values, Object value) {
    if (values==null) return -1;
    for (int i=0; i<values.length;i++) {
      if(value==values[i]) {
        return i;
      }
    }
    return -1;
  }

  static public int getRandExactIndex(int[] values, int value) {
    int[] tempVs = new int[values.length];
    return getRandExactIndex(values, tempVs, value);
  }

  static public int getRandExactIndex(int[] values, int[] resultIndices, int value) {
    int size = getExactIndices(values, resultIndices, value);
    if (size > 0) {
      return resultIndices[RandomGenerator.intRangeRandom(size)];
    }
    return -1;
  }

  static public int getExactIndices(int[] values, int[] resultIndices, int value) {
    return getExactIndices(values, resultIndices, -1, value);
  }

  static public int getExactIndices(int[] values, int[] resultIndices, int unconsideredIndex, int value) {
    int size = 0;
    for (int i = 0; i < values.length; i++) {
      if (value == values[i] && i!=unconsideredIndex) {
        resultIndices[size] = i;
        size++;
      }
    }
    return size;
  }

  static public int getExactIndex(int[] values, int value) {
    if (values==null) return -1;
    return getExactIndex(values, value, 0, values.length);
  }

  static public int getExactIndex(int[] values, int value, int startIndex, int endIndex) {
    if (values==null) return -1;
    for (int i=startIndex; i<endIndex;i++) {
      if(value==values[i]) {
        return i;
      }
    }
    return -1;
  }

  static public int getExactIndex(double[] values, double value, int startIndex, int endIndex) {
    if (values==null) return -1;
    int realStart = Math.min(startIndex, endIndex);
    int realEnd = Math.max(startIndex, endIndex);
    for (int i=realStart; i<realEnd;i++) {
      if(value==values[i]) {
        return i;
      }
    }
    return -1;
  }

  public static int[] getRelativeIndices(int[] fullArray, int[] subArray) {
    int[] indices = new int[subArray.length];
    for (int i=0; i<subArray.length; i++) {
      indices[i] = getExactIndex(fullArray, subArray[i]);
    }
    return indices;
  }

  public static int[] getPostArray(int[] objArray, int[] dataArray, int[] idArray) {
    for (int i=0; i<idArray.length; i++) {
      objArray[i] = dataArray[idArray[i]];
    }
    return objArray;
  }

  public static int[] getPostArray(int[] dataArray, int[] idArray) {
    int[] objArray = new int[idArray.length];
    return getPostArray(objArray, dataArray, idArray);
  }

  public static boolean isCycleSame(int[] cycleA, int[] cycleB) {
    if (cycleA.length!=cycleB.length) return false;
    int indexA = 0;
    int indexB = BasicArray.getExactIndex(cycleB, cycleA[indexA]);

    for (int i=1; i<cycleA.length; i++) {
      if (cycleA[i]!=cycleB[getPeriodID(cycleA.length, indexB+i)] && cycleA[i]!=cycleB[getPeriodID(cycleA.length, indexB+cycleA.length-i)]) return false;
    }
    return true;
  }

  public static int getPeriodID(int dataSize, int id) {
    if (id<0||id>=dataSize) return id%dataSize;
    else return id;
  }

  public static int getExtremalElementIndices(double[] srcArray, int[] idArray, boolean isMax) {
    return getExtremalElementIndices(srcArray, 0, srcArray.length, idArray, isMax);
  }

  public static int getExtremalElementIndices(double[] srcArray, int startIndex, int endIndex, int[] idArray, boolean isMax) {
    double maxV = -Double.MAX_VALUE;
    if (!isMax) maxV = Double.MAX_VALUE;
    int number = 0;
    for (int i=startIndex; i<endIndex; i++) {
      double degree = srcArray[i];
      if ((maxV < degree)==isMax && maxV!=degree) {
        maxV = degree;
        idArray[0] = i;
        number = 1;
      } else if (maxV==degree) {
        idArray[number]= i;
        number ++;
      }
    }
    return number;
  }

  public static int getExtremalElementIndices(int[] srcArray, int[] idArray, boolean isMax) {
    return getExtremalElementIndices(srcArray, 0, srcArray.length, idArray, isMax);
  }

  public static int getExtremalElementIndices(int[] srcArray, int startIndex, int endIndex, int[] idArray, boolean isMax) {
    int maxV = -Integer.MAX_VALUE;
    if (!isMax) maxV = Integer.MAX_VALUE;
    int number = 0;
    for (int i=startIndex; i<endIndex; i++) {
      int degree = srcArray[i];
      if ((maxV < degree)==isMax && maxV!=degree) {
        maxV = degree;
        idArray[0] = i;
        number = 1;
      } else if (maxV==degree) {
        idArray[number]= i;
        number ++;
      }
    }
    return number;
  }
}
