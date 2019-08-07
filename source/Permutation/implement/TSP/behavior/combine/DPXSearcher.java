/**
 * Description: The description of DPX (Distance preserving crossover).
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 08, 2005
 * Xiaofeng Xie    Apr 29, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 25, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @version 1.0
 *
 * @ Reference:
 * [1] New Genetic Local Search Operators for the Traveling Salesman Problem. B Freisleben, P Merz - PPSN, 890-899, 1996
 */

package implement.TSP.behavior.combine;

import Global.methods.*;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.combine.*;
import implement.TSP.represent.*;
import implement.TSP.knowledge.*;

public class DPXSearcher extends AbsNoQRecombinationSearch {
  private boolean[] sameFlags;
  private int[] segLocIDs;
  private boolean[] segLocIDUsedFlags;
  private int segNumber = 0;

  private int[][] distEngine;
  private HomoEdgeCounter agentInstCounter;

  public DPXSearcher() {}

  private void init(int nodeNumber) {
    segLocIDs = new int[nodeNumber];
    segLocIDUsedFlags = new boolean[nodeNumber];
    sameFlags = new boolean[nodeNumber];
    agentInstCounter = new HomoEdgeCounter(nodeNumber);
  }

  protected void setRootInfo(AbsLandscape landscape) {
    this.distEngine = ((InternalRepresentation)landscape).getIGetProblemDataEngine().getCostMatrix();
    init(landscape.getSearchSpace().getNodeNumber());
  }

  public String getSummary() {
    String str = this.getClass().getName();
    return str;
  }

  public int getNodeNumber() {
    return segLocIDs.length;
  }

  private int getEndCity(int segID, boolean isStart, SearchState basePoint) {
    int cityID = this.getEndCityID(segID, isStart, basePoint.getNodeNumber());
    return basePoint.getValueAt(cityID);
  }

  private int getEndCityID(int segID, boolean isStart, int nodeNumber) {
    if (isStart) {
      return segLocIDs[segID];
    }
    return BasicArray.getPrecessorID(nodeNumber, segLocIDs[BasicArray.getSuccessorID(segNumber, segID)]);
  }

  private void getNextSegment(DPXSegment currentSegment, SearchState basePoint, SearchState referPoint) {
    int city = getEndCity(currentSegment.segmentID, !currentSegment.isForwordDirection, basePoint);

    int nextCity = -1;
    int segID = -1;
    boolean segDir = true;

    int minDistance = Integer.MAX_VALUE;
    int currDistance = 0;
    for (int i=0; i<segNumber; i++) {
      if (!segLocIDUsedFlags[i]) {
        nextCity = getEndCity(i, true, basePoint);
        currDistance = distEngine[city][nextCity];
        if (currDistance < minDistance) {
          minDistance = currDistance;
          segID = i;
          segDir = false;
        }
        nextCity = getEndCity(i, false, basePoint);
        currDistance = distEngine[city][nextCity];
        if (currDistance < minDistance) {
          minDistance = currDistance;
          segID = i;
          segDir = false;
        }
      }
    }
    currentSegment.segmentID = segID;
    currentSegment.isForwordDirection = segDir;
  }

  public void toSegment(SearchState basePoint, SearchState referPoint) {
    agentInstCounter.getSameEdgeFlags(sameFlags, basePoint, referPoint);
    segNumber = 0;
    int nodeNumber = getNodeNumber();

    for (int i=0; i<nodeNumber; i++) {
      if (!sameFlags[BasicArray.getPrecessorID(nodeNumber, i)]) {
        segLocIDs[segNumber] = i;
        segLocIDUsedFlags[segNumber] = false;
        segNumber++;
      }
    }
  }

  private int setSegment(int biasID, DPXSegment currentSegment, SearchState trialPoint, SearchState basePoint) {
    int startID, endID;
    int nodeNumber = this.getNodeNumber();
    startID = getEndCityID(currentSegment.segmentID, true, nodeNumber);
    endID = getEndCityID(currentSegment.segmentID, false, nodeNumber);
    int newBiasID = biasID + BasicArray.getPeriodDistance(nodeNumber, startID, endID);
    ArrayOperator.copySegmentTo(basePoint.getIArray(), trialPoint.getIArray(), startID, endID, biasID);
    if (!currentSegment.isForwordDirection) {
      ArrayOperator.inverseSegment(trialPoint.getIArray(), biasID, newBiasID);
    }
    return newBiasID+1;
  }

  public boolean generate(SearchState trialPoint, SearchState basePoint, SearchState referPoint) {
    toSegment(basePoint, referPoint);
    if (segNumber == 0) return false;
    int biasID = 0;
    DPXSegment currentSegment = new DPXSegment();
    currentSegment.segmentID = RandomGenerator.intRangeRandom(segNumber);
    currentSegment.isForwordDirection = RandomGenerator.booleanRandom();
    for (int i=0; i<segNumber; i++) {
      biasID = setSegment(biasID, currentSegment, trialPoint, basePoint);
      if (i<segNumber-1) {
        segLocIDUsedFlags[currentSegment.segmentID] = true;
        getNextSegment(currentSegment, basePoint, referPoint);
      }
    }
    return true;
  }

  class DPXSegment {
    int segmentID;
    boolean isForwordDirection;
  }
}
