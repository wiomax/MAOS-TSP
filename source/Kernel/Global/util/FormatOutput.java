/**
 * Description: operations for outputting a double value as string in given format.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 22, 2001    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.util;

import java.text.*;
import Global.define.*;

public class FormatOutput {
//  public static final String FORMAT_TAG = "0.###E0";

/**
  * Format output a 8-bit String represent the input String in closed real value.
  * @param      vString      the String to be conversed (a real value).
  * @return  a 8-bit String.
  */
  public static String formatConvert(String vString){
    return(formatConvert(vString,8));
  }

  public static String smartFormatConvert(String strValue){
    return smartFormatConvert(strValue, 3);
  }

  public static String smartFormatConvert(String strValue, int effectDec){
    Double value = null;
    try {
      value = new Double(strValue);
    } catch (Exception e) {return strValue;};
    return smartFormatConvert(value.doubleValue(), effectDec);
  }

  public static String smartFormatConvert(double value) {
    return smartFormatConvert(value, 3);
  }

  public static String smartFormatConvert(double value, int effectDec){
    String format_TAG = "0.";
    for(int i=0; i<effectDec; i++) {
      format_TAG +="#";
    }

    DecimalFormat df = new DecimalFormat();
    double realValue = Math.abs(value);
    double radix = Math.pow(10, effectDec);
    if ((realValue>radix||realValue<1/radix)&&realValue!=0) {
      format_TAG += "E0";

    }
    df = new DecimalFormat(format_TAG);
    return df.format(value);
  }

  public static String formatConvert(int value, int length){
    String sValue = String.valueOf(value);
    int leftLen = length-sValue.length();
    for (int i=0; i<leftLen; i++) {
      sValue = "0"+sValue;
    }
    return sValue;
  }

/**
  * Format out a 8-bit String represent the input String in closed real value.
  * @param      vString      the String to be conversed (a real value).
  * @param      len      the length of formatted string (>3).
  * @return  a formatted String with specified length.
  */
  public static String formatConvert(String vString, int len){
    int eIndex;

    vString = vString.trim();
    boolean negative = false;
    if(vString.indexOf(BasicTag.EXP_TAG)==0){
      negative = true;
      vString = vString.substring(1);
    }
    if((eIndex = vString.indexOf(BasicTag.EXP_TAG))==-1)
      eIndex = vString.indexOf(BasicTag.S_EXP_TAG);
    if(eIndex!=-1){
      String eValue = vString.substring(eIndex+1);
      if(eValue.length()<4){
        String zero = "";
        if(eValue.indexOf(BasicTag.EXP_TAG)!=-1){
          for(int j=0;j<4-eValue.length();j++)
            zero += BasicTag.ZERO_TAG;
          eValue = BasicTag.EXP_TAG + zero + eValue.substring(1);
        }else{
          for(int j=0;j<3-eValue.length();j++)
          zero += BasicTag.ZERO_TAG;
          eValue = BasicTag.INC_TAG + zero + eValue.substring(0);
        }
      }
      if(eIndex>=len)
        vString = vString.substring(0,len) + BasicTag.EXP_TAG + eValue;
      else{
        String zero;
        if(vString.indexOf(BasicTag.INC_TAG)==-1){
          zero = BasicTag.INC_TAG;
          for(int j=0;j<len-1-eIndex;j++)
            zero += BasicTag.ZERO_TAG;
        }else{
          zero = "";
          for(int j=0;j<len-eIndex;j++)
            zero += BasicTag.ZERO_TAG;
        }
        vString = vString.substring(0,eIndex) + zero + BasicTag.EXP_TAG +eValue;
      }
    }else{
      if(vString.indexOf(BasicTag.ZERO_TAG)==0){
        int zeroIndex = 0;
        for(int j=2;j<vString.length();j++){
          if(vString.charAt(j)=='0')
            zeroIndex = j;
          else
            break;
        }
        if(zeroIndex==0){
          String zero = "";
          if(vString.length()>len+1)
            vString = String.valueOf(vString.charAt(2)) + BasicTag.INC_TAG + vString.substring(3,len+1) + "E-001";
          else{
            for(int j=0;j<len+1-vString.length();j++)
              zero += BasicTag.ZERO_TAG;
            vString = String.valueOf(vString.charAt(2)) + BasicTag.INC_TAG + vString.substring(3) + zero + "E-001";
          }
        }else if(zeroIndex<vString.length()-1){
          String zero = "";
          if(vString.length()>len+zeroIndex)
            vString = String.valueOf(vString.charAt(zeroIndex+1)) + BasicTag.INC_TAG + vString.substring(zeroIndex+2,zeroIndex+len) + "E-00" + String.valueOf(zeroIndex);
          else{
            for(int j=0;j<len+zeroIndex-vString.length();j++)
              zero += BasicTag.ZERO_TAG;
            vString = String.valueOf(vString.charAt(zeroIndex+1)) + BasicTag.INC_TAG + vString.substring(zeroIndex+2) + zero + "E-00" + String.valueOf(zeroIndex);
          }
        }else{
          vString = "0.";
          for(int i=0;i<len-2;i++)
            vString += BasicTag.ZERO_TAG;
          vString = vString + "E+000";
        }
      }else{
                                int pointIndex = vString.indexOf(".");
        if(pointIndex!=-1)
          vString = vString.substring(0,pointIndex) + vString.substring(pointIndex+1);
        String zero = "";
        if(vString.length()>len-1)
          vString = String.valueOf(vString.charAt(0)) + BasicTag.INC_TAG + vString.substring(1,len-1) + "E+00" + String.valueOf(pointIndex-1);
        else{
          for(int j=0;j<len-1-vString.length();j++)
            zero += BasicTag.ZERO_TAG;
          vString = String.valueOf(vString.charAt(0)) + BasicTag.INC_TAG + vString.substring(1) + zero + "E+00" + String.valueOf(pointIndex-1);
        }
      }
    }
    if(negative==true)
      vString = BasicTag.EXP_TAG+vString;
    return vString;
  }

//Take the given string and chop it up into a series of strings on whitespace boundries.
  public static String getFormattedString(Double dVal, int maxlen) {
    String mark = dVal.toString();
    if (mark.length()>=maxlen) {
      int eindex = mark.indexOf(BasicTag.S_EXP_TAG);
      if (eindex==-1) {
        mark = mark.substring(0,maxlen);
      } else {
        mark = mark.substring(0,maxlen-(mark.length()-eindex))+mark.substring(eindex,mark.length());
      }
    }
    return(mark);
  }
}
