/**
 * Description: Counts the number of same edges for different tours.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 24, 2005
 * Xiaofeng Xie    Apr 29, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.TSP.knowledge;

import java.util.*;
import Global.math.*;
import Global.methods.*;

import maosKernel.represent.landscape.space.*;

public class HomoEdgeCounter {
  private int[][] sameInstCounts;
  private boolean[][] occupyFlags;

  public HomoEdgeCounter(int nodeNumber) {
    this(nodeNumber, nodeNumber);
  }

  public HomoEdgeCounter(int nodeNumber, int popuNumber) {
    occupyFlags = new boolean[nodeNumber][nodeNumber];
    sameInstCounts = new int[popuNumber][popuNumber];
  }

  private void clearFlags() {
    for (int i=0; i<occupyFlags.length; i++) {
      Arrays.fill(occupyFlags[i], false);
    }
  }


  public int getNodeNumber() {
    return occupyFlags.length;
  }

  private void countPointFlag(SearchState point, boolean flag) {
    int nodeNumber = point.getNodeNumber();
    int currCity, nextCity;
    for (int i=0; i<nodeNumber;i++) {
      currCity = point.getValueAt(i);
      nextCity = point.getValueAt(BasicArray.getSuccessorID(nodeNumber, i));
      occupyFlags[currCity][nextCity] = flag;
      occupyFlags[nextCity][currCity] = flag;
    }
  }

  private int getSameEdgeNumber(SearchState point) {
    int count = 0;
    int currCity, nextCity;
    int nodeNumber = point.getNodeNumber();
    for (int i=0; i<nodeNumber;i++) {
      currCity = point.getValueAt(i);
      nextCity = point.getValueAt(BasicArray.getSuccessorID(nodeNumber, i));
      if (occupyFlags[currCity][nextCity]) {
        count++;
      }
    }
    return count;
  }

  public int[][] getEdgeIDs(SearchState[] points) {
    for (int i=0; i<points.length; i++) {
      countPointFlag(points[i], true);
    }
    int nodeNumber = getNodeNumber();
    int[][] ids = new int[nodeNumber][];
    for (int i=0; i<nodeNumber; i++) {
      ids[i] = BasicArray.getFlagIndices(occupyFlags[i], true);
    }
    clearFlags();
    return ids;
  }


  public int getEdgeNumber(SearchState[] points) {
    for (int i=0; i<points.length; i++) {
      countPointFlag(points[i], true);
    }
    int nodeNumber = getNodeNumber();
    int counts = 0;
    for (int i=0; i<nodeNumber; i++) {
      counts += BasicArray.getElementNumber(occupyFlags[i], true);
    }
    clearFlags();
    return counts/2;
  }

  public int getSameEdgeFullNumber(SearchState point, SearchState[] pointLib) {
    int nodeNumber = point.getNodeNumber();
    int totalNumber = 0;
    boolean[] sameFlags = new boolean[nodeNumber];
    for (int i=0; i<pointLib.length; i++) {
      Arrays.fill(sameFlags, false);
      countPointFlag(pointLib[i], true);
      accSameEdgeFlags(sameFlags, point);
      countPointFlag(pointLib[i], false);
      totalNumber += BasicArray.getElementNumber(sameFlags, true);
    }
    return totalNumber;
  }

  public int getSameEdgeNumber(SearchState point, SearchState[] pointLib) {
    int nodeNumber = point.getNodeNumber();
    boolean[] sameFlags = new boolean[nodeNumber];
    for (int i=0; i<pointLib.length; i++) {
      countPointFlag(pointLib[i], true);
      accSameEdgeFlags(sameFlags, point);
      countPointFlag(pointLib[i], false);
    }
    return BasicArray.getElementNumber(sameFlags, true);
  }

  public int getSameEdgeNumber(SearchState pointA, SearchState pointB) {
    countPointFlag(pointA, true);
    int sameEdgeNumber = getSameEdgeNumber(pointB);
    countPointFlag(pointA, false);
    return sameEdgeNumber;
  }

  private void accSameEdgeFlags(boolean[] sameFlags, SearchState point) {
    int currCity, nextCity;
    int nodeNumber = point.getNodeNumber();
    for (int i=0; i<nodeNumber;i++) {
      currCity = point.getValueAt(i);
      nextCity = point.getValueAt(BasicArray.getSuccessorID(nodeNumber, i));
      sameFlags[i] = sameFlags[i] || occupyFlags[currCity][nextCity];
    }
  }

  private void getSameEdgeFlags(boolean[] sameFlags, SearchState point) {
    int currCity, nextCity;
    int nodeNumber = point.getNodeNumber();
    for (int i=0; i<nodeNumber;i++) {
      currCity = point.getValueAt(i);
      nextCity = point.getValueAt(BasicArray.getSuccessorID(nodeNumber, i));
      sameFlags[i] = occupyFlags[currCity][nextCity];
    }
  }

  public void getSameEdgeFlags(boolean[] sameFlags, SearchState basePoint, SearchState referPoint) {
    countPointFlag(referPoint, true);
    getSameEdgeFlags(sameFlags, basePoint);
    countPointFlag(referPoint, false);
  }

  public void countSameEdges(SearchState[] pointArray) {
    for (int i=0; i<pointArray.length;i++) {
      countPointFlag(pointArray[i], true);
      for (int j=0; j<i; j++) {
        sameInstCounts[i][j] = sameInstCounts[j][i] = getSameEdgeNumber(pointArray[j]);
      }
      sameInstCounts[i][i] = -1;
      countPointFlag(pointArray[i], false);
    }
  }

  public int getBelowIndexAt(int index) {
    int[] sortedIDs = BasicArray.getSortedIndices(sameInstCounts[index]);
    return sortedIDs[1];

//    int averageCount = this.getAverageCount(index);
//    for (int i=0; i<sameInstCounts.length;i++) {
//      if (i!=index && sameInstCounts[index][i]<averageCount) {
//        return i;
//      }
//    }
//    return RandomGenerator.intRangeRandom(sameInstCounts.length);
  }


  public int getAverageCount(int index) {
    return ArrayMath.totalSum(sameInstCounts[index])/sameInstCounts.length;
  }

}
