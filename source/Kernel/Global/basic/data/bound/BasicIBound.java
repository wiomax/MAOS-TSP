/**
 * Description: provide an integer bound, and the corresponding operations
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr. 12, 2003
 */

package Global.basic.data.bound;

import Global.basic.*;
import Global.define.*;
import Global.methods.*;

public class BasicIBound extends BasicObject {
  public static final int N0_UPBOUND = GlobalValue.MAXINTEGER;
  public static final int N0_LOWBOUND = GlobalValue.MININTEGER;

  public int minValue = N0_LOWBOUND;
  public int maxValue = N0_UPBOUND;
  public BasicIBound() {
  }

  public BasicIBound(int val) {
    this.setFixBound(val);
  }

  public BasicIBound(int min, int max) {
    minValue = Math.min(min, max);
    maxValue = Math.max(min, max);
  }

  public int getLength() {
    return Math.abs(maxValue-minValue);
  }

  public boolean isSatisfyCondition(int child){
    if(child > maxValue || child < minValue) {
      return(false);
    }
    return(true);
  }

  public void moveValue(int value) {
    minValue += value;
    maxValue += value;
  }

  public void setFixBound(int val) {
    minValue = val;
    maxValue = val;
  }

//  public static int boundAdjust(int realV, int minV, int maxV, String warnTitle, Object warnObj) {
//    if (realV>maxV) {
//      try {
//        new GradedInfo(warnObj, GradedInfo.WARNING, warnTitle+": "+realV+", set to maximum value "+maxV).showMessage(warnObj.toString()+warnTitle+"Max");
//      } catch (Exception e) {}
//      realV = maxV;
//    }
//    if (realV<minV) {
//      try {
//        new GradedInfo(warnObj, GradedInfo.WARNING, warnTitle+": "+realV+", set to minimum value" +minV).showMessage(warnObj.toString()+warnTitle+"Min");
//      } catch (Exception e) {}
//      realV = minV;
//    }
//    return realV;
//  }

  public int boundAdjust(int value){
    if(value > maxValue) {
      value = maxValue;
    } else if (value < minValue) {
      value = minValue;
    }
    return value;
  }

  public int periodAdjust (int value) {
    if(value > maxValue) {
      int extendsLen = (value-maxValue)%getLength();
      value = minValue+extendsLen;
    } else if (value < minValue) {
      int extendsLen = (minValue-value)%getLength();
      value = maxValue-extendsLen;
    }
    return value;
  }

  public static BasicIBound getBound(int[] data) {
    BasicIBound bound = new BasicIBound();
    if(data!=null) {
      if(data.length>0) {
        bound.minValue = data[0];
        bound.maxValue = data[0];
        for(int i=1; i<data.length; i++) {
          bound.minValue = Math.min(bound.minValue, data[i]);
          bound.maxValue = Math.max(bound.maxValue, data[i]);
        }

      }
    }
    return bound;
  }

  public int randomAdjust (int value){
    if(value > maxValue || value < minValue) {
      value =  getRandomValue();
    }
    return value;
  }
  public String toRangeString(){
    return "["+TypeConverter.toString(minValue)+","+TypeConverter.toString(maxValue)+"]";
  }

  public String toDESCString(){
    return "MIN="+TypeConverter.toString(minValue)+" MAX="+TypeConverter.toString(maxValue);
  }

  public int getRandomValue(){
    return RandomGenerator.intRangeRandom(minValue, maxValue);
  }
}
