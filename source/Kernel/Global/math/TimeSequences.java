/**
 * Description: Some mathmatic functions and algorithms.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 19, 2003    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.math;


public class TimeSequences {


  static public double getLinMapping(double min, double max, int index, int totalTimes) {
    return (max-min) * index/(totalTimes-1)+min;
  }

  static public double getExpMapping(double min, double max, int index, int totalTimes) {
    double N=1;
    return max*Math.pow(min/max, Math.pow((double)index/(double)totalTimes, N));
  }

  static public double getCosLinMapping(double min, double max, int index, int totalTimes) {
    return getCosLinMapping(min, max, index, totalTimes, 10, 0.5, 4);
  }

  static public double getCosMapping(double min, double max, int index, int totalTimes) {
    return (Math.abs(max)-min) * (Math.cos(index*Math.PI/totalTimes)+1)/2 +min; //cosine variation
  }

  static public double getBasicCosLinMapping(double min, double max, int index, int totalTimes) {
    return getBasicCosLinMapping(min, max, index, totalTimes, 10, 0.5);
  }

  static public double getBasicCosLinMapping(double min, double max, int index, int totalTimes, double k, double loc) {
    double mapV2;
    int changedGen = (int)(totalTimes*loc);
    if (index<changedGen) {
      mapV2 = (Math.abs(max)-k*min) * Math.cos(index*Math.PI/(2*changedGen))+min*k; //cosine variation
    } else {
      mapV2 = getLinMapping(k*min, min, index-changedGen, totalTimes - changedGen);
    }
    return mapV2;
  }

  static public double getCosLinMapping(double min, double max, int index, int totalTimes, double k, double loc, double ratio) {
    double mapV1, mapV2;
    mapV1 = getCosMapping(min, max, index, totalTimes); //cosine variation
    mapV2 = getBasicCosLinMapping(min, max, index, totalTimes, k, loc);
    return (mapV1+mapV2*(ratio-1))/ratio;
  }
}

