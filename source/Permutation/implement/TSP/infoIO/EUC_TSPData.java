/**
 * Description: provide the information for the EUC TSP data
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 6, 2005
 * Xiaofeng Xie    Apr 14, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 * Xiaofeng Xie    Jan 23, 2018    "GEO_M", "GEO_DM", "GEO_MM"
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 *  [1] Program's name: acotsp
 *  Ant Colony Optimization algorithms (AS, ACS, EAS, RAS, MMAS, BWAS) for the
 *  symmetric TSP, Copyright (C) 2004  Thomas Stuetzle
 *  Email: stuetzle no@spam informatik.tu-darmstadt.de
 *  [2] TSPLIB, Gerhard Reinelt, Gerhard.Reinelt{at}informatik.uni-heidelberg.de
 */

package implement.TSP.infoIO;

import Global.methods.*;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.sin;

import Global.math.*;


public class EUC_TSPData extends Abs_TSPData {
  public double[][] tspCOORDData; //[][2]
  private String[] RECOG_EW_TYPE ={"EUC_2D", "CEIL_2D", "GEO", "ATT", "GEO_M", "GEO_DM", "GEO_MM"};
  private int index_EDGE_WEIGHT_TYPE = -1;

  private double[] getCOORDAt(int index) {
    return tspCOORDData[index];
  }

  public boolean setEDGE_WEIGHT_TYPE(String type) {
    index_EDGE_WEIGHT_TYPE = StringSearch.getStringLoc(RECOG_EW_TYPE, type);
    return (index_EDGE_WEIGHT_TYPE!=-1);
  }

  public double[][] getCoordData(int[] tour) {
    double[][] coord = new double[getNodeNumber()][];
    for (int i=0; i<getNodeNumber(); i++) {
      coord[i] = getCOORDAt(tour[i]);
    }
    return coord;
  }

  public int[][] getDistanceMatrix() {
    int nodeNumber = getNodeNumber();
    int [][] distMatrix = new int[nodeNumber][nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      for (int j=0; j<i; j++) {
        distMatrix[i][j] = getValueAt(i, j);
        distMatrix[j][i] = distMatrix[i][j];
      }
    }
    return distMatrix;
  }

  public int getNodeNumber() {
    return tspCOORDData.length;
  }

  public int getValueAt(int i, int j) {
    if (i==j) return 0;
    switch (index_EDGE_WEIGHT_TYPE) {
      case 0:
        return round_distance(tspCOORDData, i, j);
      case 1:
        return ceil_distance(tspCOORDData, i, j);
      case 2:
        return geo_distance(tspCOORDData, i, j);
      case 3:
        return att_distance(tspCOORDData, i, j);
      case 4:
        return (int) (1E3*geo_distance_new(tspCOORDData, i, j));
      case 5:
        return (int) (1E4*geo_distance_new(tspCOORDData, i, j));
      case 6:
        return (int) (1E6*geo_distance_new(tspCOORDData, i, j));
      default:
        return Integer.MAX_VALUE;
    }
  }

  /*
    FUNCTION: compute Euclidean distances between two nodes rounded to next
              integer for TSPLIB instances
    INPUT:    two node indices
    OUTPUT:   distance between the two nodes
    COMMENTS: for the definition of how to compute this distance see TSPLIB
  */
  public static int round_distance (double[][] tspCOORDData, int i, int j) {
    return (int)(ArrayMath.getLength(tspCOORDData[i], tspCOORDData[j])+ 0.5);
  }

  /*
    FUNCTION: compute ceiling distance between two nodes rounded to next
              integer for TSPLIB instances
    INPUT:    two node indices
    OUTPUT:   distance between the two nodes
    COMMENTS: for the definition of how to compute this distance see TSPLIB
  */
  public static int ceil_distance (double[][] tspCOORDData, int i, int j) {
    return (int)Math.ceil(ArrayMath.getLength(tspCOORDData[i], tspCOORDData[j]));
  }

  //For converting coordinate input to longitude and latitude in radian
  // See also TSPLIB: http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/TSPFAQ.html
  private static double getRadian (double v) {
    double deg = (int)(v);
    double min = v - deg;
    return Math.PI* (deg + 5.0 * min / 3.0) / 180.0;
  }

  /*
    FUNCTION: compute geometric distance between two nodes rounded to next
              integer for TSPLIB instances
    INPUT:    two node indices
    OUTPUT:   distance between the two nodes (unit: km)
    COMMENTS: adapted from concorde code
              for the definition of how to compute this distance see TSPLIB
  */
  public static double geo_distance_old (double[][] tspCOORDData, int i, int j) {
    double lati = getRadian(tspCOORDData[i][0]);
    double latj = getRadian(tspCOORDData[j][0]);
    double longi = getRadian(tspCOORDData[i][1]);
    double longj = getRadian(tspCOORDData[j][1]);
    double q1 = Math.cos (longi - longj);
    double q2 = Math.cos (lati - latj);
    double q3 = Math.cos (lati + latj);
    return (EarthRadius * Math.acos (0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
  }
  
  private static final double pk = 180/Math.PI;
  public static final double EarthRadius = 6378.388;
	public static double gps2km(double lat_a, double lng_a, double lat_b, double lng_b) {
   double a1 = lat_a / pk;
   double a2 = lng_a / pk;
   double b1 = lat_b / pk;
   double b2 = lng_b / pk;

   double ca1 = cos(a1);
   double cb1 = cos(b1);
   
   double t1 = ca1*cos(a2)*cb1*cos(b2);
   double t2 = ca1*sin(a2)*cb1*sin(b2);
   double t3 = sin(a1)*sin(b1);
   return EarthRadius * acos(min(1, t1 + t2 + t3));
	}
  
  public static int geo_distance (double[][] tspCOORDData, int i, int j) {
  	return (int) geo_distance_new(tspCOORDData, i, j);
  }

  public static double geo_distance_new (double[][] tspCOORDData, int i, int j) {
    return gps2km(tspCOORDData[i][0],tspCOORDData[i][1], tspCOORDData[j][0], tspCOORDData[j][1]);
  }

  /*
    FUNCTION: compute ATT distance between two nodes rounded to next
              integer for TSPLIB instances
    INPUT:    two node indices
    OUTPUT:   distance between the two nodes
    COMMENTS: for the definition of how to compute this distance see TSPLIB
  */
  public static int att_distance (double [][] tspCOORDData, int i, int j) {
    return (int)Math.ceil(ArrayMath.getLength(tspCOORDData[i], tspCOORDData[j])/Math.sqrt(10.0));
  }
}

