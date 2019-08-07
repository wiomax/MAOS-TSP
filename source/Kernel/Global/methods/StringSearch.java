/**
 * Description: operations for the a text string.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 22, 2001    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.methods;

import java.util.*;
import Global.basic.*;
import Global.define.*;

public class StringSearch {

  public static int getHeadStringIndex(String[] lines, String headStr) {
    for(int i=0; i<lines.length; i++) {
      if(lines[i].trim().toLowerCase().startsWith(headStr.toLowerCase())) {
        return i;
      }
    }
    return -1;
  }

  public static boolean getFirstCharExist(String str, String chars) throws Exception {
    int neglectFirstCharLength = chars.length();
    for(int i=0; i<neglectFirstCharLength; i++) {
      if(str.startsWith(chars.substring(i, i+1))) {
        return true;
      }
    }
    return false;
  }

  public static int getCharLoc(char data, String str) {
    for(int i=0; i<str.length(); i++) {
      if(str.charAt(i)==data) return i;
    }
    return -1;
  }

  public static boolean isContain(Object[] set1, Object[] set2) {
    if(set2==null) return true;
    if(set1==null) return false;
    for(int i=0; i<set2.length; i++) {
      int index = getSelectedIndex(set1, set2[i]);
      if (index == -1) return false;
    }
    return true;
  }

  public static int[] getSelectedIndices(Object[] nSrc, Object[] nObj) {
    if(nSrc==null||nObj==null) return new int[0];
    int[] indices = new int[nObj.length];
    for(int i=0; i<nObj.length; i++) {
      indices[i] = getSelectedIndex(nSrc, nObj[i]);
    }
    return indices;
  }

  public static int[] getFullOrderIndices(Vector<?> names, Vector<?> orderNames) {
    int[] indices = new int[orderNames.size()];
    for(int i=0; i<orderNames.size(); i++) {
      indices[i] = getSelectedIndex(names, orderNames.elementAt(i));
    }
    return indices;
  }

  public static int[] getValidOrderIndices(Vector<?> names, Vector<?> orderNames) {
    int validSize = 0;
    int[] indices = new int[orderNames.size()];
    for(int i=0; i<orderNames.size(); i++) {
      int index = getSelectedIndex(names, orderNames.elementAt(i));
      if(index!=-1) {
        indices[validSize] = index;
        validSize++;
      }
    }
    int[] realIndices = new int[validSize];
    System.arraycopy(indices, 0, realIndices, 0, validSize);
    return realIndices;
  }

  public static String getObjString(Object nObj) {
    if(nObj instanceof String) return (String)nObj;
    if(nObj instanceof BasicName) return ((BasicName)nObj).getName();
    return nObj.toString();
  }

  public static Vector<Object> getObjStringSet(Vector<?> strObjSet) {
    Vector<Object> strSet = new Vector<Object>();
    for(int i=0; i<strObjSet.size(); i++) {
      strSet.add(getObjString(strObjSet.elementAt(i)));
    }
    return strSet;
  }

  public static Vector<Object> removeObjStringSet(Vector<?> origStringObjSet, Vector<?> tobeRemovedSet) {
    Vector<Object> strSet = new Vector<Object>();
    strSet.addAll(origStringObjSet);
    for(int i=0; i<tobeRemovedSet.size(); i++) {
      String name = getObjString(tobeRemovedSet.elementAt(i));
      int index = getSelectedIndex(strSet, name);
      if(index!=-1) strSet.remove(index);
    }
    return strSet;
  }

  public static int getSelectedIndex(Object[] nSrc, Object nObj) {
    if(nSrc==null||nObj==null) return -1;
    for(int i=0; i<nSrc.length; i++) {
      if(getObjString(nObj).equalsIgnoreCase(getObjString(nSrc[i]))) return i;
    }
    return GlobalValue.INVALID_NATURAL_NUMBER;
  }

  public static boolean isSameString(Object nSrc, Object nObj) {
    return (getObjString(nSrc).equalsIgnoreCase(getObjString(nObj)));
  }

  public static int getSelectedIndex(Vector<?> nSrc, Object nObj) {
    if(nSrc==null||nObj==null) return -1;
    for(int i=0; i<nSrc.size(); i++) {
      if(getObjString(nSrc.elementAt(i)).equalsIgnoreCase(getObjString(nObj))) return i;
    }
    return GlobalValue.INVALID_NATURAL_NUMBER;
  }

  public static int getTokenLoc(String input, String tokenKey) {
    for(int i=0; i<input.length(); i++) {
      if(getCharLoc(input.charAt(i), tokenKey) !=-1) return i;
    }
    return GlobalValue.INVALID_NATURAL_NUMBER;
  }

  public static int getStringLoc(String[] data, String str) {
    if(str==null) return -1;
    for(int i=0; i<data.length; i++) {
      if(str.equalsIgnoreCase(data[i])) return i;
    }
    return -1;
  }

  public static Vector<Object> getElementsByKey(String key,  Enumeration<?> enumData) {
    Vector<Object> activeElements = new Vector<Object>();
    while (enumData.hasMoreElements()) {
      BasicAttrib oper = (BasicAttrib)enumData.nextElement();
      if(oper.isSameKey(key)) {
        activeElements.add(oper);
      }
    }
    return activeElements;
  }

  public static Vector<String> getSameKeys(IGetKeyEngine[] targets) {
    Vector<String> keys = new Vector<String>();
    for(int i=0; i<targets.length; i++) {
      IGetKeyEngine no = targets[i];
      int index = getSelectedIndex(keys, no.getKey());
      if(index==-1) keys.add(no.getKey());
    }
    return keys;
  }
}
