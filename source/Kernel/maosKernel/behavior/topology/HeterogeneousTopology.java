/**
 * Description: The Heterogeneous distance-based topology: only the nodes with distance larger than
 *   ratio*(average distance) are considered.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 06, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * 
 * @REF:
 * [1] H. K. Tsai, J. M. Yang, Y. F. Tsai, and C. Y. Kao, "An evolutionary algorithm for 
 *  large traveling salesman problems," IEEE Transactions on Systems Man and Cybernetics 
 *  - Part B, vol. 34, pp. 1718-1729, 2004.
 */

package maosKernel.behavior.topology;

import Global.basic.data.collection.*;
import Global.basic.nodes.utilities.*;
import Global.math.ArrayMath;
import Global.methods.*;
import maosKernel.represent.landscape.*;
import maosKernel.represent.knowledge.*;
import maosKernel.memory.*;

public class HeterogeneousTopology extends AbsCycleTopology implements ISetLandscapeInfoEngine, ISetStateSetEngine {
  private double ratio = 1;

  // temp for output
  private IArray[] connectedIDsArray;
  
  private ICalcDistanceEngine distanceEngine = null;
  private IGetEachEncodedStateEngine library = null;
  
  private double [][] distanceMatrix;

  public HeterogeneousTopology() {
  }
  
  public void setRootInfo(AbsLandscape landscape) {
    super.setRootInfo(landscape);
    this.distanceEngine = landscape;
  }

  public void setInfo(IGetEachEncodedStateEngine library) {
    this.library = library;
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("ratio", ratio));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    ratio = TypeConverter.toDouble(getValue("ratio"));
  }

  protected void internalBasicInit(int nodeNumber) {
    connectedIDsArray = new IArray[nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      connectedIDsArray[i] = new IArray(nodeNumber);
    }
    distanceMatrix = new double[nodeNumber][nodeNumber];
  }

  public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID) {
    return connectedIDsArray[nodeID];
  }
  
  private void calcDistMatrix() {
    int nodeNumber = library.getLibSize();
    for (int i=0; i<nodeNumber; i++) {
      for (int j=0; j<i; j++) {
        distanceMatrix[i][j] = distanceEngine.getDistance(library.getSelectedPoint(i).getSearchState(), library.getSelectedPoint(j).getSearchState());
        distanceMatrix[j][i] = distanceMatrix[i][j];
      }
    }
  }

  protected void innerInitTopology() {
    calcDistMatrix();
    int nodeNumber = library.getLibSize();
    int connNodeNumber = nodeNumber-1;
    int i, j;
    double vth;
    for (i=0; i<distanceMatrix.length; i++) {
      connectedIDsArray[i].clear();
      vth = ratio * ArrayMath.totalSum(distanceMatrix[i])/connNodeNumber;
      for (j=0; j<nodeNumber; j++) {
        if (distanceMatrix[i][j]>=vth) {
          connectedIDsArray[i].addElement(j);
        }
      }
    }
  }
}
