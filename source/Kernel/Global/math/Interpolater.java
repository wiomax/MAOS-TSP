/**
 * Description: Some mathmatic functions and algorithms.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 19, 2003    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.math;


public class Interpolater {

  public static double interpolate(double x1, double x2, double y1, double y2, double xValue) {
    return y1+(y2-y1)*(xValue-x1)/(x2-x1);
  }

  public static double dimidiate(double[]x , double[] y, double yAt, int flag) {
  // if flag = 0, this is a successively degressive function
  // if flag = 1, this is a successively ascensive function
    double xMax, xMin, xMid, yMid, deltaY;

    xMin=x[0];
    xMax=x[x.length-1];
    do {
      xMid = (xMin + xMax)/2.0;
      yMid = lagran(x, y, xMid);
      deltaY = yMid - yAt;
      if (flag == 0) {
        if (deltaY < 0)
          xMax = xMid;
        else
          xMin = xMid;
      }
      else {
        if (deltaY < 0)
          xMin = xMid;
        else
          xMax = xMid;
      }
    }while((Math.abs(deltaY/yAt) > 1.0e-2)&&(Math.abs((xMax-xMin)/xMid)>1.0e-4));
    return xMid;
}

  // lagran method, the values in array x must be equidistant, by rj
  public static double lagran(double[] x, double[] y, double xAt) {
    int i,j;
    int left, right;
    double s;
    double yAt;

    yAt=0.0;
    if(x.length < 0){System.out.println("there are zero element in the array"); return 0.0;}
    if(x.length == 1) {yAt = y[0]; return(yAt);}
    if(x.length == 2){
      yAt = (y[0]*(xAt - x[1]) - y[1] * (xAt - x[0]))/(x[0]-x[1]);
      return yAt;
    }
    //select 8 points around xAt, 4 on the left and 4 on the right
    i = 0;
    while ((x[i]<xAt) && (i<x.length)) i++;
    left = i - 4;
    if(left < 0) left = 0;
    right = i + 3;
    if(right > x.length-1) right = x.length - 1;
    for( i = left; i <= right; i++){
      s = 1.0;
      for( j = left; j <= right; j++)
        if (j != i) s = s*(xAt-x[j])/(x[i]-x[j]);
      yAt += s*y[i];
   }
   return yAt;
  }

  static public double getRatioLocation(double start, double end, double ratio) {
    return (end-start)*ratio+start;
  }
}

