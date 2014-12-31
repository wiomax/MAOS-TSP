/**
 * Description: provide a set operations for Arrays
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 27, 2005
 */

package Global.methods;

public class ArraySet {
  public static boolean isIntersect(int[] v1, int[] v2) {
    for (int i=0; i<v1.length; i++) {
      if (BasicArray.getExactIndex(v2, v1[i])!=-1) {
        return true;
      }
    }
    return false;
  }

  //if v1 contains v2 && v2 contains v1, then return true
  public static boolean isDualContain(int[] v1, int[] v2) {
    return (isContain(v1, v2) && isContain(v2,v1));
  }

  //if v1 contains v2, then return true
  public static boolean isContain(int[] v1, int[] v2) {
    for (int i=0; i<v2.length; i++) {
      if (BasicArray.getExactIndex(v1, v2[i])==-1) {
        return false;
      }
    }
    return true;
  }
}
