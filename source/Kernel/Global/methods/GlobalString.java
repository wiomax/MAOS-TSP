/**
 * Description: operations for the a text string.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 22, 2001    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.methods;

import java.io.*;
import java.util.*;
import Global.basic.*;
import Global.define.*;

public class GlobalString {
  public static final String NEGLECT_TAG = "#$@";

  public static boolean isNull(String name) {
    if(name==null) return true;
    if(name.trim().length()==0) return true;
    return false;
  }
  public static String addHeadToAllLines(String content, String headString) throws Exception {
    String[] lines = getAllLines(content);
    addHeadToAll(lines, headString);
    return serinize(lines, BasicTag.RETURN_TAG);
  }

  public static void addHeadToAll(String[] lines, String headString) {
    for (int i=0; i<lines.length; i++) {
      lines[i] = headString+lines[i];
    }
  }

  public static String[] getMeaningfulLines(String srcStr) throws Exception {
    return getMeaningfulLines(srcStr, NEGLECT_TAG);
  }

  public static String getFirstStringWithHead(String[] lines, String headStr, String tokenKey) {
    int index = StringSearch.getHeadStringIndex(lines, headStr);
    if (index!=-1) {
      String subString = lines[index].substring(headStr.length());
      String[] lineInfos = GlobalString.tokenize(subString, tokenKey);
      if (lineInfos.length>0) {
        return lineInfos[lineInfos.length-1].trim();
      }
    }
    return null;
  }
  public static String[] getAllLines(String srcStr) throws Exception {
    StringReader outStringReader = new StringReader(srcStr);
    BufferedReader outReader = new BufferedReader(outStringReader);
    Vector<String> origData = new Vector<String>();
    String str = null;
    while(true) {
      str = outReader.readLine();
      if (str==null) {
        break;
      }
      origData.add(str);
    }
    return ObjectMatrix.convert1DVectorToStringArray(origData);
  }

  public static String[] getMeaningfulLines(String srcStr, String neglectFirstChars) throws Exception {
    StringReader outStringReader = new StringReader(srcStr);
    BufferedReader outReader = new BufferedReader(outStringReader);
    Vector<String> origData = new Vector<String>();
    String str = null;
    while(true) {
      str = getMeaningfulLine(outReader, neglectFirstChars);
      if (str==null) {
        break;
      }
      origData.add(str);
    }
    return ObjectMatrix.convert1DVectorToStringArray(origData);
  }

  public static String[] getAcceptMeaningfulLines(String srcStr, String acceptFirstChars) throws Exception {
    StringReader outStringReader = new StringReader(srcStr);
    BufferedReader outReader = new BufferedReader(outStringReader);
    Vector<String> origData = new Vector<String>();
    String str = null;
    while(true) {
      str = getMeaningfulLine(outReader, "");
      if (str==null) {
        break;
      }
      if(StringSearch.getFirstCharExist(str, acceptFirstChars)) {
        origData.add(str.substring(1));
      }
    }
    return ObjectMatrix.convert1DVectorToStringArray(origData);
  }

  public static String getMeaningfulLine(BufferedReader outReader) throws Exception {
    return getMeaningfulLine(outReader, NEGLECT_TAG);
  }

  public static String getMeaningfulLine(BufferedReader outReader, String neglectFirstChars) throws Exception {
    String str;
    boolean isNeglect = true;
    do {
      str = outReader.readLine();
      if (str==null) {
        return null;
      }
      str = trim(str, " \t");
      if(str.length()>0) {
        isNeglect = StringSearch.getFirstCharExist(str, neglectFirstChars);
      }
    } while (isNeglect);
    return str;
  }

/**
  * Make the initial character to the upper case.
  * @param      oldString      the String to be conversed.
  * @return  a String with capital to the upper case.
  */
  public static String initialCapital(String oldString) {
    return(oldString.substring(0,1).toUpperCase()+oldString.substring(1,oldString.length()));
  }

  public static String serinize(int[] data, String seriKey) {
    String str = "";
    for (int i=0; i<data.length; i++) {
      if(i!=0) {
        str+=seriKey;
      }
      str+=data[i];
    }
    return str;
  }

  public static String serinize(double[] data, String seriKey) {
    String str = "";
    for (int i=0; i<data.length; i++) {
      if(i!=0) {
        str+=seriKey;
      }
      str+=data[i];
    }
    return str;
  }

  public static String serinize(IGetNameEngine[] data, String seriKey) {
    String str = "";
    for (int i=0; i<data.length; i++) {
      if(i!=0) {
        str+=seriKey;
      }
      str+=data[i].getName();
    }
    return str;
  }

  public static String serinize(Object[] data, String seriKey, int startIndex, int endIndex) {
    String str = "";
    for (int i=startIndex; i<endIndex; i++) {
      if(i!=0) {
        str+=seriKey;
      }
      str+=data[i].toString();
    }
    return str;
  }

  public static String serinize(Object[] data, String seriKey) {
    String str = "";
    for (int i=0; i<data.length; i++) {
      if(i!=0) {
        str+=seriKey;
      }
      str+=data[i].toString();
    }
    return str;
  }

  public static String serinize(Vector<? extends Object> data, String seriKey) {
    String str = "";
    for (int i=0; i<data.size(); i++) {
      if(i!=0) {
        str+=seriKey;
      }
      str+=data.elementAt(i).toString();
    }
    return str;
  }

  public static String replace(String input, String tokenOld, String tokenNew) {
    String[] strs = GlobalString.tokenize(input, tokenOld);
    return GlobalString.serinize(strs, tokenNew);
  }
  
  public static String[] splitFirst(String input, String tokenKey) {
    String[] realArray = new String[2];
    String[] lineArray = GlobalString.tokenize(input, tokenKey);
    int index = input.indexOf(lineArray[1], lineArray[0].length());
    realArray[0] = lineArray[0];
    realArray[1] = input.substring(index);
    return realArray;
  }

  public static String[] tokenize(String input, String tokenKey) {
    return tokenize(input, tokenKey, input.length());
  }
  
  /**
   * Tokenize a String with given key.
   * @param      input      the String to be tokenized.
   * @param      tokenKey   the delimiters.
   * @param      maxSize    the maximal number of elements.
   * @return  a String array that include the elements of input string that
   * divided by the tokenKey.
   */
  public static String[] tokenize(String input , String tokenKey, int maxSize) {
    Vector<Object> v = new Vector<Object>();
    StringTokenizer t = new StringTokenizer(input, tokenKey);
    String cmd[];
    int size = 0;
    while (t.hasMoreTokens() && size<maxSize) {
      v.addElement(t.nextToken());
      size ++;
    }
    cmd = new String[v.size()];
    for (int i = 0; i < cmd.length; i++)
      cmd[i] = (String) v.elementAt(i);
    return cmd;
  }

  public static String getObjString(Object nObj) {
    if(nObj instanceof String) return (String)nObj;
    if(nObj instanceof BasicAttrib) return ((BasicAttrib)nObj).name;
    return nObj.toString();
  }

  public static String[] tokenize(String input, String normalTokenKey, String importantTokenKey) {
    String str = input.trim();
    int index1, index2;
    Vector<String> v = new Vector<String>();
    do {
      index1 = StringSearch.getTokenLoc(str, importantTokenKey);
      if(index1==-1||index1==str.length()) {
        v.add(str);
        break;
      } else {
        v.add(str.substring(0, index1));
        str = str.substring(index1);
        str.trim();
        if(str.length()<1) break;
        index2 = StringSearch.getTokenLoc(str.substring(1), importantTokenKey);
        if(index2==-1||index2==str.length()) {
          v.add(str);
          break;
        } else {
          if(index2==0)  {
            v.add("");
          } else {
            v.add(str.substring(0, index2+2));
          }
          str = str.substring(index2+2);
          str.trim();
          if(str.length()<1) break;
        }
      }
    } while (true);

    Vector<String> realV = new Vector<String>();
    for(int i=0; i<v.size(); i++) {
      String subElem = v.elementAt(i).toString();
      if (subElem.equalsIgnoreCase("")) {
        realV.add(subElem);
      }else if(StringSearch.getTokenLoc(subElem, importantTokenKey)!=-1) {
        subElem = trim(subElem, importantTokenKey);
        realV.add(subElem);
      } else {
        String[] subData = tokenize(subElem, normalTokenKey);
        for(int j=0; j<subData.length; j++) {
          realV.add(subData[j]);
        }
      }
    }
    return ObjectMatrix.convert1DVectorToStringArray(realV);
  }

  public static String trim(String origStr, String discardStr) {
    String str = origStr;
    do {
      if(str.length()==0) return str;
      if(StringSearch.getCharLoc(str.charAt(0), discardStr)!=-1) str = str.substring(1);
      else if(StringSearch.getCharLoc(str.charAt(str.length()-1), discardStr)!=-1) str = str.substring(0, str.length()-1);
      else {return str;}
    } while(true);
  }

/**
  * if the length of a line is large than specified, then chop it up into
  * many lines of strings on whitespace boundries.
  * @param      oldLine   to be checked string.
  * @param      maxLineLength   maximum length of a string.
  * @return  many lines of strings, the length of each line is short than
  * specified length.
  */
  public static String trimLine (String oldLine, int maxLineLength) {
    String newLine = "";
    int STEP = 10;
    int currentLength = maxLineLength;
    int oldLineLength = oldLine.length();
    if (oldLineLength <= maxLineLength) {
      newLine= oldLine;
    } else {
      int seperateIndex = 0;
      do {
        currentLength -= STEP;
      } while ((currentLength>0)&&((seperateIndex = oldLine.indexOf(" ",currentLength))==-1));
      if (currentLength>0) {
        String restString = oldLine.substring(seperateIndex,oldLineLength);
        if (restString.length()>maxLineLength) {
          restString = trimLine(restString,maxLineLength);
        }
        newLine = oldLine.substring(0, seperateIndex)+"\n+"+restString;
      }

    }
    return(newLine);
  }

  public static String toSummary(String[] names, double[] data) {
    return toSummary(names, data, BasicTag.EQUAL_TAG);
  }

  public static String toSummary(String[] names, double[] data, String sepFlag) {
    if(names==null||data==null) return "";
    if(names.length!=data.length) return "";
    String sumStr = "";
    for(int i=0; i<names.length; i++) {
      sumStr += names[i]+sepFlag+data[i]+BasicTag.RETURN_TAG;
    }
    return sumStr;
  }

}
