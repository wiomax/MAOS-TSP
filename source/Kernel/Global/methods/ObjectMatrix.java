/**
 * Description: operations for the simple object Matrix
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 22, 2001    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.methods;

import java.util.*;


public class ObjectMatrix {

  public static String[] removeElementAt(String[] data, int removedIndex) {
    String[] newData = new String[data.length - 1];
    System.arraycopy(data, 0, newData, 0, removedIndex);
    System.arraycopy(data, removedIndex+1, newData, removedIndex, newData.length-removedIndex);
    return newData;
  }

  public static Vector<Object> getValidSet(Vector<Object> elementSet, Vector<Object> freqSet) {
    Vector<Object> validElementSet = new Vector<Object>();
    for(int i=0; i<elementSet.size(); i++) {
      int freq = ((Integer)freqSet.get(i)).intValue();
      if(freq>0) {
        validElementSet.add(elementSet.elementAt(i));
      }
    }
    return validElementSet;
  }

  public static void transferTo(Vector<Object> acceptor, Vector<Object> donator) {
    acceptor.clear();
    acceptor.addAll(donator);
  }

  public static int getMaxColNumber(int[][] matrix) {
    int n = -1;
    for (int i=0; i<matrix.length; i++) {
      if (matrix[i].length>n) {
        n = matrix[i].length;
      }
    }
    return n;
  }

  public static boolean[][] removeCol(boolean[][] _2DArray, int removedIndex) {
    boolean[][] newData = new boolean[_2DArray.length][];
    for(int i=0; i<_2DArray.length; i++) {
      newData[i] = ArrayOperator.removeElement(_2DArray[i], removedIndex);
    }
    return newData;
  }

  public static int[][] removeCol(int[][] _2DArray, int removedIndex) {
    int[][] newData = new int[_2DArray.length][];
    for(int i=0; i<_2DArray.length; i++) {
      newData[i] = ArrayOperator.removeElement(_2DArray[i], removedIndex);
    }
    return newData;
  }

  public static Object[][] removeRow(Object[][] _2DArray, int removedIndex) {
    Object[][] newData = new Object[_2DArray.length-1][];
    int count = 0;
    for(int i=0; i<_2DArray.length; i++) {
      if (i!=removedIndex) {
        newData[count] = _2DArray[i];
        count ++;
      }
    }
    return newData;
  }

  public static boolean[][] removeRow(boolean[][] _2DArray, int removedIndex) {
    boolean[][] newData = new boolean[_2DArray.length-1][];
    int count = 0;
    for(int i=0; i<_2DArray.length; i++) {
      if (i!=removedIndex) {
        newData[count] = _2DArray[i];
        count ++;
      }
    }
    return newData;
  }

  public static int[][] removeRow(int[][] _2DArray, int removedIndex) {
    int[][] newData = new int[_2DArray.length-1][];
    int count = 0;
    for(int i=0; i<_2DArray.length; i++) {
      if (i!=removedIndex) {
        newData[count] = _2DArray[i];
        count ++;
      }
    }
    return newData;
  }

  public static Object[] getColumnAt(Object[][] _2DArray, int index) {
    Object[] colData = new Object[_2DArray.length];
    for(int i=0; i<colData.length; i++) {
      colData[i] = _2DArray[i][index];
    }
    return colData;
  }

  public static double[] getColumnAt(double[][] _2DArray, int index) {
    double[] colData = new double[_2DArray.length];
    for(int i=0; i<colData.length; i++) {
      colData[i] = _2DArray[i][index];
    }
    return colData;
  }

  public static int[] getColumnAt(int[][] _2DArray, int index) {
    int[] colData = new int[_2DArray.length];
    for(int i=0; i<colData.length; i++) {
      colData[i] = _2DArray[i][index];
    }
    return colData;
  }

  public static Object[][] trans1DTo2D(Object[] _1DArray,boolean isCol) {
    Object[][] _2DArray;
    if (isCol) {
      _2DArray = new Object[_1DArray.length][1];
      for (int i=0; i<_1DArray.length; i++) {
        _2DArray[i][0] = _1DArray[i];
      }
    }
    else {
      _2DArray = new Object[1][_1DArray.length];
      for (int i=0; i<_1DArray.length; i++) {
        _2DArray[0][i] = _1DArray[i];
      }
    }
    return(_2DArray);
  }

/**
 *  if iscol=true, then as a col add
 */
  public static Object[][] add2Matrix(Object[][] matrix0, Object[][] matrix1, boolean isCol) {
    if(matrix0==null&&matrix1==null) {
      return null;
    } else if (matrix0!=null&&matrix1==null) {
      return matrix0;
    } else if (matrix0==null&&matrix1!=null) {
      return matrix1;
    } else {

      Object[][] newMatrix;
      if (isCol) {
        int rowSize = Math.min(matrix0.length,matrix1.length);
        newMatrix = new Object[rowSize][matrix0[0].length+matrix1[0].length];
        for( int i=0;i<newMatrix.length;i++) {
          for (int j=0; j<matrix0[0].length;j++) {
            newMatrix[i][j] = matrix0[i][j];
          }
          for (int j=0; j<matrix1[0].length;j++) {
            newMatrix[i][j+matrix0[0].length] = matrix1[i][j];
          }
        }
      }
      else {
        int colSize = Math.min(matrix0[0].length,matrix1[0].length);
        newMatrix = new Object[matrix0.length+matrix1.length][colSize];
        for( int j=0;j<newMatrix[0].length;j++) {
          for (int i=0; i<matrix0.length;i++) {
            newMatrix[i][j] = matrix0[i][j];
          }
          for (int i=0; i<matrix1.length;i++) {
            newMatrix[i+matrix0.length][j] = matrix1[i][j];
          }
        }
      }
      return(newMatrix);
   }
 }

/**
 *  Add two 1D array as one array, the sArray0 is in front of sArray1
 */
  public static Object[] add2OneDArray(Object[] sArray0, Object[] sArray1) {
    Object[] addedArray = new Object[sArray0.length+sArray1.length];
    for (int i=0;i<sArray0.length;i++) {
      addedArray[i] = sArray0[i];
    }
    for (int i=0;i<sArray1.length;i++) {
      addedArray[i+sArray0.length] = sArray1[i];
    }
    return(addedArray);
  }

/** transpose a 2D Matrix
  */
  public static Object[][] transposeMatrix(Object[][] tobetansMatrix) {
    if (tobetansMatrix==null) {
      return null;
    }
    Object[][] newMatrix = new Object[tobetansMatrix[0].length][tobetansMatrix.length];
    for (int i=0; i<tobetansMatrix.length; i++) {
      for (int j=0; j<tobetansMatrix[0].length; j++) {
        newMatrix[i][j] = tobetansMatrix[j][i];
      }
    }
    return(newMatrix);
  }

/**
 * append str1 to the end of the array sArray0
 */
  public static String[] addStringToStrArray(String[] sArray0, String str1) {
    if (str1==null) {
      return null;
    }
    String[] sArray1 = new String[1];
    sArray1[0] = str1;
    return add2StringArray(sArray0,sArray1);
  }

/**
 *  Add two 1D array as one array, the sArray0 is in front of sArray1
 */
  public static String[] add2StringArray(String[] sArray0, String[] sArray1) {
    if (sArray0==null&&sArray1==null) {
      return(null);
    } else if (sArray0==null&&sArray1!=null) {
      return(sArray1);
    } else if (sArray0!=null&&sArray1==null) {
      return(sArray0);
    } else{
      String[] stringArray = new String[sArray0.length+sArray1.length];
      for (int i=0;i<sArray0.length;i++) {
        stringArray[i] = sArray0[i];
      }
      for (int i=0;i<sArray1.length;i++) {
        stringArray[i+sArray0.length] = sArray1[i];
      }
      return(stringArray);
    }
  }

/**
 * convert 1D array to vector
 */
  public static Vector<Object> convertArrayTo1DVector(Object[] toToConvert) {
    if (toToConvert==null) return null;
    Vector<Object> vec = new Vector<Object>();
    for (int i=0; i<toToConvert.length; i++) {
      vec.addElement(toToConvert[i]);
    }
    return(vec);
  }

/**
 * convert vector to 1D array
 */
  public static Object[] convert1DVectorToArray(Vector<Object> toToConvert) {
    if (toToConvert==null) return null;
    Object[] objs = new Object[toToConvert.size()];
    for (int i=0; i<toToConvert.size(); i++) {
      objs[i] = toToConvert.elementAt(i);
    }
    return(objs);
  }

/**
 * convert vector to 1D String array
 */
  public static String[] convert1DVectorToStringArray(Vector<? extends Object> toToConvert) {
    if (toToConvert==null) return null;
    String[] objs = new String[toToConvert.size()];
    for (int i=0; i<toToConvert.size(); i++) {
      objs[i] =GlobalString.getObjString(toToConvert.elementAt(i));
    }
    return(objs);
  }
}


