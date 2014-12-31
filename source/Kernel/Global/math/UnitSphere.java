/**
 * Description: provide the locationinformation in a sphere
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 5, 2003     xiaofengxie@tsinghua.org.cn
 *
 
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * Please acknowledge the author(s) if you use this code in any way.
 */

package Global.math;

public class UnitSphere implements Cloneable {
  public static double[] tempPoint = new double[3];
  public static double[] tempAngleSet = new double[2];

  //theta 0->PI, phai, -PI->PI
  public static void getPoint(double theta, double phai, double[] point) {
    point[0] = Math.sin(theta)*Math.cos(phai);
    point[1] = Math.sin(theta)*Math.sin(phai);
    point[2] = Math.cos(theta);
  }

  public static void getPoint(double[] angleSet, double[] point) {
    getPoint(angleSet[0], angleSet[1], point);
  }

  public static void getPoint(double radius, double[] angleSet, double[] point) {
    getPoint(angleSet, point);
    toRadiusPoint(point, radius);
  }

  public static void getAngle(double[] point, double[] angleSet) {
    getAngle(point[0], point[1], point[2], angleSet);
  }

  public static void getAngle(double x, double y, double z, double[] angleSet) {
    angleSet[0] = Math.acos(z);
    angleSet[1] = Math.acos(x/Math.sin(angleSet[0]));
    if(y<0) angleSet[1] *= -1;
  }

  public static void getPoints(double[] points, double[] angles) {
    int N = angles.length/2;
    for(int i=0; i<N; i++) {
      UnitSphere.getPoint(angles[2*i], angles[2*i+1], tempPoint);
      System.arraycopy(tempPoint, 0, points, 3*i, 3);
    }
  }

  public static void getAngles(double[] angles, double[] points) {
    int N = points.length/3;
    for(int i=0; i<N; i++) {
      UnitSphere.getAngle(points[3*i], points[3*i+1], points[3*i+2], tempAngleSet);
      System.arraycopy(tempAngleSet, 0, angles, 2*i, 2);
    }
  }

  public static void toRadiusPoint(double[] point, double radius) {
    for(int i=0; i<point.length; i++) {
      point[i] *= radius;
    }
  }

  public static void toUnit(double[] point) {
    double radius = getRadius(point);
    toRadiusPoint(point, 1/radius);
  }

  public static double getRadius(double[] point) {
    return Math.sqrt(ArrayMath.squareSum(point));
  }

  public static double getLength(double[] angleSet1, double[] angleSet2) {
    return getLength(angleSet1[0], angleSet1[1], angleSet2[0], angleSet2[1]);
  }

  public static double getLength(double theta1, double phai1, double theta2, double phai2) {
    return Math.sqrt(2*(1-Math.sin(theta1)*Math.sin(theta2)*Math.cos(phai1-phai2)-Math.cos(theta1)*Math.cos(theta2)));
  }

}
