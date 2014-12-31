/**
 * Description: provide operations for changing the Array elements
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 27, 2005
 */

package Global.methods;

public class ArrayOperator {


  //Copy the segment from startID to endID (include endID)
  public static void copySegmentTo(short[] srcData, short[] objData, short srcStartID, short srcEndID, short objStartID) {
    short rLength = (short)(srcEndID+1-srcStartID);
    short sLength = rLength;
    if(rLength<=0) {
      sLength += srcData.length;
    }
    if (rLength>0) {
      System.arraycopy(srcData, srcStartID, objData, objStartID, sLength);
    }
    if(rLength<0) {
      System.arraycopy(srcData, srcStartID, objData, objStartID, srcData.length-srcStartID);
      System.arraycopy(srcData, 0, objData, objStartID+srcData.length-srcStartID, srcEndID+1);
    }
  }

  //Copy the segment from startID to endID (include endID)
  public static void copySegmentTo(int[] srcData, int[] objData, int srcStartID, int srcEndID, int objStartID) {
    int rLength = srcEndID+1-srcStartID;
    int sLength = rLength;
    if(rLength<=0) {
      sLength += srcData.length;
    }
    if (rLength>0) {
      System.arraycopy(srcData, srcStartID, objData, objStartID, sLength);
    }
    if(rLength<0) {
      System.arraycopy(srcData, srcStartID, objData, objStartID, srcData.length-srcStartID);
      System.arraycopy(srcData, 0, objData, objStartID+srcData.length-srcStartID, srcEndID+1);
    }
  }

  /**
   *  Add two 1D array as one array, the sArray0 is in front of sArray1
   */
  public static int[] merge2Array(int[] sArray0, int[] sArray1) {
    int[][] sArrays = {sArray0, sArray1};
    return mergeArrays(sArrays);
  }

  public static void mergePureArrays(int[] addedArray, int[][] sArrays) {
    int totalSize = 0;
    for (int i=0; i<sArrays.length ;i++) {
      System.arraycopy(sArrays[i], 0, addedArray, totalSize, sArrays[i].length);
      totalSize += sArrays[i].length;
    }
  }

  public static int[] mergeArrays(int[][] sArrays) {
    int totalSize = 0;
    for (int i=0; i<sArrays.length ;i++) {
      totalSize += sArrays[i].length;
    }
    int[] addedArray = new int[totalSize];
    mergePureArrays(addedArray, sArrays);
    return(addedArray);
  }

  public static void shiftSelf(double[] ov, boolean isForward) {
    double tempObj;
    if (isForward) {
      tempObj = ov[ov.length-1];
      System.arraycopy(ov, 0, ov, 1, ov.length-1);
      ov[0] = tempObj;
    } else {
      tempObj = ov[0];
      System.arraycopy(ov, 1, ov, 0, ov.length-1);
      ov[ov.length-1] = tempObj;
    }
  }

  public static void shiftSelf(Object[] ov, boolean isForward) {
    Object tempObj;
    if (isForward) {
      tempObj = ov[ov.length-1];
      System.arraycopy(ov, 0, ov, 1, ov.length-1);
      ov[0] = tempObj;
    } else {
      tempObj = ov[0];
      System.arraycopy(ov, 1, ov, 0, ov.length-1);
      ov[ov.length-1] = tempObj;
    }
  }

  //Move one element from startID to endID
  public static void elementMove(int[] data, int startID, int endID) {
    if(endID==startID) return;
    int swapV = data[startID];
    if (endID>startID) {
      for(int i=startID; i<endID; i++) {
        data[i] = data[i+1];
      }
      data[endID] = swapV;
    } else {
      for(int i=startID; i>endID; i--) {
        data[i] = data[i-1];
      }
      data[endID] = swapV;
    }
  }

  public static void shiftSelf(Object[] ov, int[] indices, boolean isForward) {
    Object tempObj;
    if (isForward) {
      tempObj = ov[indices[indices.length-1]];
      for (int i=indices.length-1; i>0; i--) {
        ov[indices[i]] = ov[indices[i-1]];
      }
      ov[indices[0]] = tempObj;
    } else {
      tempObj = ov[indices[0]];
      for (int i=0; i<indices.length-1; i--) {
        ov[indices[i]] = ov[indices[i+1]];
      }
      ov[indices[indices.length-1]] = tempObj;
    }
  }

  public static void shiftSelf(int[] ov, int[] indices, boolean isForward) {
    int tempObj;
    if (isForward) {
      tempObj = ov[indices[indices.length-1]];
      for (int i=indices.length-1; i>0; i--) {
        ov[indices[i]] = ov[indices[i-1]];
      }
      ov[indices[0]] = tempObj;
    } else {
      tempObj = ov[indices[0]];
      for (int i=0; i<indices.length-1; i--) {
        ov[indices[i]] = ov[indices[i+1]];
      }
      ov[indices[indices.length-1]] = tempObj;
    }
  }

  public static void shiftSelf(double[] ov, int[] indices, boolean isForward) {
    double tempObj;
    if (isForward) {
      tempObj = ov[indices[indices.length-1]];
      for (int i=indices.length-1; i>0; i--) {
        ov[indices[i]] = ov[indices[i-1]];
      }
      ov[indices[0]] = tempObj;
    } else {
      tempObj = ov[indices[0]];
      for (int i=0; i<indices.length-1; i--) {
        ov[indices[i]] = ov[indices[i+1]];
      }
      ov[indices[indices.length-1]] = tempObj;
    }
  }

  //Inverse the segment from startID to endID (include endID)
  private static int inverseI, inverseDiff, inverseN;
  public static void inverseSegment(int[] data, int startID, int endID) {
    if (startID==endID) return;
    if (endID>startID) {
      inverseDiff = (endID - startID+1) / 2;
      for (inverseI = 0; inverseI < inverseDiff; inverseI++) {
        swapData(data, startID+inverseI, endID-inverseI);
      }
    } else {
      inverseN = data.length;
      endID += inverseN;
      inverseDiff = (endID - startID+1) / 2;
      for (inverseI = 0; inverseI < inverseDiff; inverseI++) {
        swapData(data, BasicArray.getPeriodID(inverseN, startID+inverseI), BasicArray.getPeriodID(inverseN, endID-inverseI));
      }
    }
  }

  private static int[] tempShiftV;
  public static void shiftSelf(int[] ov, int number, boolean isForward) {
    if (tempShiftV==null||tempShiftV.length<number) {
      tempShiftV = new int[number];
    }
    if (isForward) {
      System.arraycopy(ov, ov.length-1-number, tempShiftV, 0, number);
      System.arraycopy(ov, 0, ov, number, ov.length-1-number);
      System.arraycopy(tempShiftV, 0, ov, 0, number);
    } else {
      System.arraycopy(ov, 0, tempShiftV, 0, number);
      System.arraycopy(ov, number, ov, 0, ov.length-1-number);
      System.arraycopy(tempShiftV, 0, ov, ov.length-1-number, number);
    }
  }

  public static void shiftSelf(int[] ov, boolean isForward) {
    int tempObj;
    if (isForward) {
      tempObj = ov[ov.length-1];
      System.arraycopy(ov, 0, ov, 1, ov.length-1);
      ov[0] = tempObj;
    } else {
      tempObj = ov[0];
      System.arraycopy(ov, 1, ov, 0, ov.length-1);
      ov[ov.length-1] = tempObj;
    }
  }

  private static boolean bSwapV = true;
  public static void swapData(boolean[] data, int id1, int id2) {
    bSwapV = data[id1];
    data[id1] = data[id2];
    data[id2] = bSwapV;
  }

  private static int iSwapV = -1;
  public static void swapData(int[] data, int id1, int id2) {
    iSwapV = data[id1];
    data[id1] = data[id2];
    data[id2] = iSwapV;
  }

  public static void nnSwapData(int[] data, int id) {
    iSwapV = data[id];
    data[id] = data[id+1];
    data[id+1] = iSwapV;
  }

  private static double dSwapV = -1;
  public static void swapData(double[] data, int id1, int id2) {
    dSwapV = data[id1];
    data[id1] = data[id2];
    data[id2] = dSwapV;
  }

  private static Object oSwapV = null;
  public static void swapData(Object[] data, int id1, int id2) {
    oSwapV = data[id1];
    data[id1] = data[id2];
    data[id2] = oSwapV;
  }

  public static void insertTo(int[] data, int fromID, int toID){
    if (fromID>toID) {
      iSwapV = data[fromID];
      System.arraycopy(data, toID, data, toID+1, fromID-toID);
      data[toID] = iSwapV;
    }
    if (fromID<toID) {
      iSwapV = data[fromID];
      System.arraycopy(data, fromID+1, data, fromID, toID-fromID);
      data[toID] = iSwapV;
    }
  }

  public static boolean[] removeElement(boolean[] _1DArray, int removedIndex) {
    boolean[] _newArray = new boolean[_1DArray.length-1];
    System.arraycopy(_1DArray, 0, _newArray, 0, removedIndex);
    System.arraycopy(_1DArray, removedIndex+1, _newArray, removedIndex, _newArray.length-removedIndex);
    return _newArray;
  }

  public static int[] removeElement(int[] _1DArray, int removedIndex) {
    int[] _newArray = new int[_1DArray.length-1];
    System.arraycopy(_1DArray, 0, _newArray, 0, removedIndex);
    System.arraycopy(_1DArray, removedIndex+1, _newArray, removedIndex, _newArray.length-removedIndex);
    return _newArray;
  }

  public static double[] removeRepeatValues(double[] value) {
    double[] imgVal = new double[value.length];
    int loc = 0;
    for(int i=0; i<value.length; i++) {
      boolean isExist = false;
      for(int j=0; j<loc; j++) {
        if(imgVal[j]==value[i]) {
          isExist = true;
          break;
        }
      }
      if(!isExist) {
        imgVal[loc] = value[i];
        loc++;
      }
    }
    double[] ultiVal = new double[loc];
    System.arraycopy(imgVal, 0, ultiVal, 0, loc);
    return ultiVal;
  }

  public static double[][] regulateRepeatedElements(double[] xSeries, double[] ySeries) {
    int size = Math.min(xSeries.length, ySeries.length);
    double[] newXSeries = new double[size];
    double[] newYSeries = new double[size];
    int[] yCounts = new int[size];
    int newCount = 0;
    int tempIndex;
    for(int i=0; i<size; i++) {
      tempIndex = BasicArray.getExactIndex(newXSeries, xSeries[i], 0, newCount);
      if(tempIndex !=-1) {
        newYSeries[tempIndex] += ySeries[i];
        yCounts[tempIndex] ++;
      } else {
        newXSeries[newCount] = xSeries[i];
        newYSeries[newCount] = ySeries[i];
        yCounts[newCount] = 1;
        newCount ++;
      }
    }
    double[][] newSeries = new double[newCount][2];
    for(int i=0; i<newCount; i++) {
      newSeries[i][0] = newXSeries[i];
      newSeries[i][1] = newYSeries[i]/yCounts[i];
    }
    return newSeries;
  }

  public static void reverseArray(boolean[] array) {
    for (int i=0; i<array.length; i++) {
      array[i] = !array[i];
    }
  }

  private static void swap2(int[] v1, int[] v2, int i, int j) {
    swapData(v1, i, j);
    swapData(v2, i, j);
  }
 
  /**    
  FUNCTION:       recursive routine (quicksort) for sorting one array; second 
                  arrays does the same sequence of swaps  
  INPUT:          two arrays, two indices
  OUTPUT:         none
*/
  public static void quickSort2(int[] data, int[] idSet, int left, int right)
  {
    int k, last;

    if (left >= right) 
      return;
    swap2(data, idSet, left, (left + right)/2);
    last = left;
    for (k=left+1; k <= right; k++)
      if (data[k] < data[left])
        swap2(data, idSet, ++last, k);
    swap2(data, idSet, left, last);
    quickSort2(data, idSet, left, last);
    quickSort2(data, idSet, last+1, right);
  }
}
