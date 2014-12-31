/**
 * Description: Some nonuniform sampling functions.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 19, 2003
 *
 * @version 1.0
 */

package Global.math;

import Global.methods.*;

public class NonuniformSampling {

/*!
 *  Generate a floating-point number following a Gaussian distribution.
 *  \param inMu Mean value.
 *  \param inSigma Standard-deviation.
 */
  public final static double normrand(double inMu, double inSigma) {
    double lFact,lRSQ,lV1,lV2;
    do {
      lV1  = RandomGenerator.doubleRangeRandom(-1.0, 1.0);
      lV2  = RandomGenerator.doubleRangeRandom(-1.0, 1.0);
      lRSQ = (lV1*lV1) + (lV2*lV2);
    } while(lRSQ >= 1.0 || lRSQ == 0.0);
    lFact = Math.sqrt(-2.0 * Math.log(lRSQ) / lRSQ);
    return lV2*lFact*inSigma + inMu;
  }

  static public double cauchyFunction(double x, double t) {
    return t/(Math.PI*(t*t+x*x));
  }

  static public double arcCauchyDistribution(double y, double t) {
    return Math.tan(Math.PI*(y-0.5))*t;
  }

  static public double cauchyDistribution(double x, double t) {
    return 0.5+Math.atan(x/t)/Math.PI;
  }

  //from y=[0, 1], return=[-0.5, 0.5];
  static public double triangleDistribution(double y) {
    if(y>=0.5) {
      return 0.5-Math.sqrt((1-y)*0.5);
    }
    return Math.sqrt(y*0.5)-0.5;
  }
}

