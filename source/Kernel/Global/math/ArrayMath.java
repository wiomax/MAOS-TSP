/**
 * Description: Global package for the math operations on arrays.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 15, 2002
 * Xiaofeng Xie    Sep 19, 2004
 *
 * @version 1.0
 */


package Global.math;

import Global.basic.*;

public class ArrayMath {
  public static boolean isEqual(int[] m1, int[] m2) {
    if (m1.length != m2.length) return false;
    for (int i=0; i<m1.length; i++) {
      if (m1[i]!=m2[i]) {
        return false;
      }
    }
    return true;
  }
  public static boolean isEqual(int[][] m1, int[][] m2) {
    if (m1.length != m2.length) return false;
    for (int i=0; i<m1.length; i++) {
      if (!isEqual(m1[i], m2[i])) {
        return false;
      }
    }
    return true;
  }

  public static boolean isEqual(int[][][] m1, int[][][] m2) {
    if (m1.length != m2.length) return false;
    for (int i=0; i<m1.length; i++) {
      if (!isEqual(m1[i], m2[i])) {
        return false;
      }
    }
    return true;
  }

  public static double getLinearPolynomialValue(double[] coefficients, double x) {
    double sumV = 0;
    double sPower = 1;
    for (int i=0; i<coefficients.length; i++) {
      sumV += coefficients[i]*sPower;
      sPower*=x;
    }
    return sumV;
  }

  static public int totalSum(int[] v1) {
    int totalSum = 0;
    for (int i=0; i<v1.length; i++){
      totalSum += v1[i];
    }
    return totalSum;
  }

  static public double totalSum(double[] v1) {
    double totalSum = 0;
    for (int i=0; i<v1.length; i++){
      totalSum += v1[i];
    }
    return totalSum;
  }

  static public int[] getBoundArray(int[] arrayA, int[] arrayB, boolean isUpper) {
    int[] boundArray = new int[arrayA.length];
    if (isUpper) {
      for (int i = 0; i < boundArray.length; i++) {
        boundArray[i] = Math.max(arrayA[i], arrayB[i]);
      }
    } else {
      for (int i = 0; i < boundArray.length; i++) {
        boundArray[i] = Math.min(arrayA[i], arrayB[i]);
      }
    }
    return boundArray;
  }


  static public double[] getBoundArray(double[] arrayA, double[] arrayB, boolean isUpper) {
    double[] boundArray = new double[arrayA.length];
    if (isUpper) {
      for (int i = 0; i < boundArray.length; i++) {
        boundArray[i] = Math.max(arrayA[i], arrayB[i]);
      }
    } else {
      for (int i = 0; i < boundArray.length; i++) {
        boundArray[i] = Math.min(arrayA[i], arrayB[i]);
      }
    }
    return boundArray;
  }

  static public void getExtermal(IndexedIValue indexedValue, int[] values, boolean isMax) {
    indexedValue.index = -1;
    indexedValue.value = Integer.MAX_VALUE;
    if (isMax) indexedValue.value *= -1;
    for (int i=0; i<values.length;i++) {
      if(isMax==(values[i]>indexedValue.value)) {
        indexedValue.value = values[i];
        indexedValue.index = i;
      }
    }
  }

  static public int getMinValue(int[] values) {
    int slectedValue = Integer.MAX_VALUE;
    for (int i=0; i<values.length;i++) {
      if(values[i]<slectedValue) {
        slectedValue = values[i];
      }
    }
    return slectedValue;
  }

  static public int getMaxValue(int[] values) {
    int slectedValue = -Integer.MAX_VALUE;
    for (int i=0; i<values.length;i++) {
      if(values[i]>slectedValue) {
        slectedValue = values[i];
      }
    }
    return slectedValue;
  }

  static public int getExtermal(int[] values, boolean isMax) {
    int slectedValue = values[0];
    for (int i=1; i<values.length;i++) {
      if(isMax==(values[i]>slectedValue)) {
        slectedValue = values[i];
      }
    }
    return slectedValue;
  }

  static public double getExtermal(double[] values, boolean isMax) {
    double slectedValue = values[0];
    for (int i=1; i<values.length;i++) {
      if(isMax==values[i]>slectedValue) {
        slectedValue = values[i];
      }
    }
    return slectedValue;
  }

  static public double getLength(double[] v1,double[] v2, int[] indices) {
    int length = Math.min(v1.length, v2.length);
    double[] elemLength = new double[indices.length];
    for (int i=0; i<indices.length; i++){
      if (indices[i]<0||indices[i]>length-1) {
        return -1;
      }
      elemLength[i] = v2[indices[i]]-v1[indices[i]];
    }
    return Math.sqrt(squareSum(elemLength));
  }

  static public double getLength(double[] v1,double[] v2) {
    double v = 0;
    for (int i=0; i<v1.length; i++){
      v += (v2[i]-v1[i])*(v2[i]-v1[i]);
    }
    return Math.sqrt(v);
  }

  static public double getLength(int[] v1,int[] v2) {
    double v = 0;
    for (int i=0; i<v1.length; i++){
      v += (v2[i]-v1[i])*(v2[i]-v1[i]);
    }
    return Math.sqrt(v);
  }


  static public int squareSum(int[] v1) {
    int totalSum = 0;
    for (int i=0; i<v1.length; i++){
      totalSum += v1[i]*v1[i];
    }
    return totalSum;
  }

  static public double squareSum(double[] v1) {
    double totalSum = 0;
    for (int i=0; i<v1.length; i++){
      totalSum += v1[i]*v1[i];
    }
    return totalSum;
  }

/* Get the standard deviation of a set of data
 * Sqrt[SUM((data[i]-mean)^2)/n]
 **/
  public static double getStdDev(double[] data, double mean) {
    double diversity = 0;
    for (int i=0; i<data.length; i++) {
      diversity += (data[i]-mean)*(data[i]-mean);
    }
    return Math.sqrt(diversity/data.length);
  }
  
  public static double getStdDev(int[] data, double mean) {
    double diversity = 0;
    for (int i=0; i<data.length; i++) {
      diversity += (data[i]-mean)*(data[i]-mean);
    }
    return Math.sqrt(diversity/data.length);
  }

///* Get the diversity of a set of data
// * Sqrt[SUM((data[i]-mean)^2)]/n
// **/
//  public static double TotalDiversity(double[] data) {
//    double total = 0;
//    for (int i=0; i<data.length; i++) {
//      total += data[i];
//    }
//    double mean = total/data.length;
//    double diversity = 0;
//    double[] errors = new double[data.length];
//    for (int i=0; i<data.length; i++) {
//      errors[i] = data[i]-mean;
//    }
//    diversity = Math.sqrt(squareSum(errors));
//    return diversity;
//  }
//
///* get the diversity of a set of individuals
// **/
//  public static double[] TotalDiversity(double[][] individuals) {
//    double[] diversities = new double[individuals.length];
//    for (int i=0; i<individuals.length; i++) {
//      diversities[i] = TotalDiversity(individuals[i]);
//    }
//    return diversities;
//  }

  static public void calcVectorMultiplyValues(double[] vr, double v[], double factor) {
    for (int i=0; i<v.length; i++) {
      vr[i] = v[i] * factor;
    }
  }

  static public double[] calcVectorMultiplyValues(double v[], double factor) {
    double[] vr = new double[v.length];
    calcVectorMultiplyValues(vr, v, factor);
    return vr;
  }

  static public void calcVectorAddValues(double[] vr, double v1[], double v2[], boolean isAdd) {
    for (int i=0; i<vr.length; i++) {
      if (isAdd) {
        vr[i] = v1[i]+v2[i];
      } else {
        vr[i] = v1[i]-v2[i];
      }
    }
  }

  static public void calcVectorAddValues(double v1[], double v2[], boolean isAdd) {
    calcVectorAddValues(v1, v1, v2, isAdd);
  }

  static public void calcVectorAddValues(int[] vr, int v1[], int v2[], boolean isAdd) {
    for (int i=0; i<vr.length; i++) {
      if (isAdd) {
        vr[i] = v1[i]+v2[i];
      } else {
        vr[i] = v1[i]-v2[i];
      }
    }
  }
  static public void calcVectorAddValues(int v1[], int v2[], boolean isAdd) {
    calcVectorAddValues(v1, v1, v2, isAdd);
  }

  static public double meanValue(double v1, double v2) {
    return (v1+v2)/2.;
  }

  static public double[] meanValues(double v1[], double v2[]) {
    if (v1.length!=v2.length) {
      return null;
    }
    double[] meanValues = new double[v1.length];
    for (int i=0; i<meanValues.length; i++) {
      meanValues[i] = meanValue(v1[i],v2[i]);
    }
    return meanValues;
  }

  public static double meanValue(double[] no1) {
    return totalSum(no1)/no1.length;
  }

  static public double[] getRowMean(double[][] data) {
    if(data==null) return null;
    double[] meanValue = new double[data[0].length];
    for(int i=0; i<meanValue.length; i++) {
      for(int j=0; j<data.length; j++) {
        meanValue[i]+=data[j][i];
      }
      meanValue[i] /= data.length;
    }
    return meanValue;
  }
}
