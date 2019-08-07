/**
 * Description: provide a  temp Array
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 27, 2005
 */

package Global.util;

import java.util.*;

public class TempArrayManager {
  private static Hashtable<String, Object> tempArrayTable = new Hashtable<String, Object>();

  public static int[] getTempIntArray(int size) {
    int[] iArray = (int[])tempArrayTable.get("i"+new Integer(size).toString());
    if(iArray==null) {
      iArray = new int[size];
      tempArrayTable.put("i"+new Integer(size).toString(), iArray);
    }
    return iArray;
  }

  public static double[] getTempDoubleArray(int size) {
    double[] dArray = (double[])tempArrayTable.get("d"+new Integer(size).toString());
    if(dArray==null) {
      dArray = new double[size];
      tempArrayTable.put("d"+new Integer(size).toString(), dArray);
    }
    return dArray;
  }

  public static boolean[] getTempBoolArray(int size) {
    boolean[] bArray = (boolean[])tempArrayTable.get("b"+new Integer(size).toString());
    if(bArray==null) {
      bArray = new boolean[size];
      tempArrayTable.put("b"+new Integer(size).toString(), bArray);
    }
    return bArray;
  }
}
