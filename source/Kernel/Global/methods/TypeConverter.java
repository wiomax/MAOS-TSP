/**
 * Description: operations for the a text string.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 22, 2001    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.methods;

import Global.define.*;

public class TypeConverter {
  public static final String MINDOUBLE_STR= "MIND";
  public static final String MAXDOUBLE_STR= "MAXD";

  public static final String MININTEGER_STR= "MINI";
  public static final String MAXINTEGER_STR= "MAXI";


  public static final String POS_PI = "PI";
  public static final String NEG_PI = "-PI";


  static public double toDouble(Object oVal) throws Exception {
    if(oVal==null) throw new Exception("Null string");
    if(oVal.toString().equalsIgnoreCase(MINDOUBLE_STR)) return GlobalValue.MINDOUBLE;
    if(oVal.toString().equalsIgnoreCase(MAXDOUBLE_STR)) return GlobalValue.MAXDOUBLE;
//    if(oVal.toString().equalsIgnoreCase(GlobalValue.UNKNOWN_D_STR)) return GlobalValue.UNKNOWNDOUBLE;
    if(oVal.toString().equalsIgnoreCase(POS_PI)) return Math.PI;
    if(oVal.toString().equalsIgnoreCase(NEG_PI)) return -Math.PI;
    return new Double(oVal.toString()).doubleValue();
  }

  public static int getNaturalValue(int currValue, int maxValue) {
    if (currValue==GlobalValue.INVALID_NATURAL_NUMBER||currValue>maxValue) {
      return maxValue;
    }
    return currValue;
  }

  static public String toString(double dVal) {
    if (dVal<=GlobalValue.MINDOUBLE) {
      return MINDOUBLE_STR;
    }
    if (dVal>=GlobalValue.MAXDOUBLE) {
      return MAXDOUBLE_STR;
    }
//    if (dVal==GlobalValue.UNKNOWNDOUBLE) {
//      return GlobalValue.UNKNOWN_D_STR;
//    }
    if (Math.abs(dVal-Math.PI)<1E-5) {
      return NEG_PI;
    }
    if (Math.abs(dVal+Math.PI)<1E-5) {
      return NEG_PI;
    }
    return new Double(dVal).toString();
  }

  static public boolean toBoolean(Object sVal) throws Exception {
    return new Boolean(sVal.toString()).booleanValue();
  }

  static public int toInteger(Object oVal) throws Exception {
    if(oVal==null) throw new Exception("Null string");
    if(oVal.toString().equalsIgnoreCase(MININTEGER_STR)) return GlobalValue.MININTEGER;
    if(oVal.toString().equalsIgnoreCase(MAXINTEGER_STR)) return GlobalValue.MAXINTEGER;
    return new Integer(oVal.toString()).intValue();
  }

  static public String toString(Object oVal) {
    return oVal.toString();
  }

  static public String toString(int iVal) {
    if (iVal<=GlobalValue.MININTEGER) {
      return MININTEGER_STR;
    }
    if (iVal>=GlobalValue.MAXINTEGER) {
      return MAXINTEGER_STR;
    }
    return new Integer(iVal).toString();
  }

  static public double[] parseDoubleArray(String strArrayInfo) throws Exception {
    if (strArrayInfo==null) {
      return null;
    }
    String strInfo = strArrayInfo.trim();
    if (strInfo.length()>2) {
      if (strInfo.charAt(0)=='(' || strInfo.charAt(strInfo.length()-1)==')') {
        strInfo = strInfo.substring(1, strInfo.length()-1);
      }
    }
    double[] dElems;
    String[] elements = GlobalString.tokenize(strInfo.trim(), BasicTag.SPACE_TAG+BasicTag.COMMA_TAG);
    dElems = new double[elements.length];
    for (int i=0; i<elements.length; i++) {
      dElems[i] = toDouble(elements[i]);
    }
    return dElems;
  }
}
