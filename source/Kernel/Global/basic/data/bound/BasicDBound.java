/**
 * Description: provide a double bound, and the corresponding operations
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct. 9, 2002
 */

package Global.basic.data.bound;

import Global.basic.*;
import Global.define.*;
import Global.methods.*;


public class BasicDBound extends BasicObject {
  public static final double N0_UPBOUND = GlobalValue.MAXDOUBLE;
  public static final double N0_LOWBOUND = GlobalValue.MINDOUBLE;

  public double minValue = N0_LOWBOUND;
  public double maxValue = N0_UPBOUND;
  public BasicDBound() {
  }

  public BasicDBound(double min, double max) {
    minValue = Math.min(min, max);
    maxValue = Math.max(min, max);
  }

  public void refresh() {
    minValue = N0_LOWBOUND;
    maxValue = N0_UPBOUND;
  }

  public void importBasicDBound(BasicDBound data) {
    this.minValue = data.minValue;
    this.maxValue = data.maxValue;
  }

  public boolean isBoundness() {
    return (minValue<=N0_LOWBOUND||maxValue>=N0_UPBOUND
            ||maxValue<=N0_LOWBOUND||minValue>=N0_UPBOUND);
  }

  public double getLength() {
    return Math.abs(maxValue-minValue);
  }

  public boolean isSatisfyCondition(double child){
    if(child > maxValue || child < minValue) {
      return(false);
    }
    return(true);
  }

  public static double getImageDiff(double diff, double halfLen) {
    if (diff>halfLen) {
      diff -= halfLen*2;
    } else if(diff<-halfLen) {
     diff += halfLen*2;
    }
    return diff;
  }

  public double boundAdjust(double value){
    if(value > maxValue) {
      value = maxValue;
    } else if (value < minValue) {
      value = minValue;
    }
    return value;
  }
  public int getRegionNumber(double value, int totalCounts) {
    return (int)Math.floor((value-minValue)/(getLength()/totalCounts));
  }

  public double getRandomValueAtRegion(int regionNum, int totalCounts) {
    double step = getLength()/totalCounts;
    return RandomGenerator.doubleRangeRandom(step*regionNum, step*(regionNum+1));
  }

  public double periodAdjust (double value) {
    if(value > maxValue) {
      double extendsLen = (value-maxValue)%getLength();
      value = minValue+extendsLen;
    } else if (value < minValue) {
      double extendsLen = (minValue-value)%getLength();
      value = maxValue-extendsLen;
    }
    return value;
  }
  public static BasicDBound getBound(double[] data) {
    BasicDBound bound = new BasicDBound();
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

  public double randomAdjust () {
    return getRandomValue();
  }

  public double randomAdjust (double value){
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

  public double getRandomValue(){
    return RandomGenerator.doubleRangeRandom(minValue, maxValue);
  }
}
