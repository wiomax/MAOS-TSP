/**
 * Description: A set of states.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar  7, 2003
 * Xiaofeng Xie    May  3, 2003
 * Xiaofeng Xie    Apr 28, 2006   
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel.memory;

import Global.methods.*;

public class StateSet implements IGetEachEncodedStateEngine {
  protected EncodedState[] libPoints = new EncodedState[0];

//  public StateSet(int number, AbsSearchSpace initSpace) {
//    EncodedState[] libPoints = new EncodedState[number];
//    for (int i=0; i<number; i++) {
//      libPoints[i] = new EncodedState(initSpace.getRandomState());
//    }
//    initPoints(libPoints);
//  }

  public StateSet(EncodedState[] libPoints) {
    this.libPoints = libPoints;
  }

  protected void initPoints(EncodedState[] points) {
    libPoints = points;
  }

  protected void appendPoints(EncodedState[] points) {
    if(points==null) return;
    this.libPoints = appendPoints(libPoints, points);
  }

  protected static EncodedState[] appendPoints(EncodedState[] points1, EncodedState[] points2) {
    EncodedState[] newPoints = new EncodedState[points1.length+points2.length];
    System.arraycopy(points1, 0, newPoints, 0, points1.length);
    System.arraycopy(points2, 0, newPoints, points1.length, points2.length);
    return newPoints;
  }

  public int getLibSize() {
    return libPoints.length;
  }

  public int getRandomIndex() {
    int num = getLibSize();
    if (num==0) return -1;
    return RandomGenerator.intRangeRandom(num);
  }

  public EncodedState getRandomPoint() {
    int index = getRandomIndex();
    if (index==-1) return null;
    return getSelectedPoint(index);
  }

  public EncodedState getSelectedPoint(int index) {
    return libPoints[index];
  }

  public int getPointIndex(EncodedState point) {
    for(int i=0; i<this.libPoints.length; i++) {
      if(point.equals(libPoints[i])) {
        return i;
      }
    }
    return -1;
  }

  public EncodedState[] getAllPoints(int[] idArray) {
    EncodedState[] points = new EncodedState[idArray.length];
    for(int i=0; i<idArray.length; i++) {
      points[i] = libPoints[idArray[i]];
    }
    return points;
  }

  public EncodedState getStateAt(int index) {
    return libPoints[index];
  }

  public EncodedState[] getAllPoints() {
    return libPoints;
  }
}


