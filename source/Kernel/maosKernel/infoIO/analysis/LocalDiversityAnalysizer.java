/**
 * Description: provide the capability for analyzing local diversity information obtained in one cycle
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 12, 2008 
 *
 * @version M01.00.02
*/

package maosKernel.infoIO.analysis;

import Global.basic.data.collection.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.*;
import maosKernel.behavior.topology.*;

public class LocalDiversityAnalysizer extends AbsCycleAnalysizer {
  private ICalcDistanceEngine distanceEngine = null;
  private AbsTopology topology;

  public LocalDiversityAnalysizer(){
    this.setName("LocalDiversity");
  }

  public void setInfo(AbsLandscape landscape) {
    this.distanceEngine = landscape;
  }
  
  public boolean isDecentralized() {
    return !(topology.isCentralized());
  }
  
  
  public void setTopology(AbsTopology topology) {
    this.topology = topology;
  }
  
  public double analysis(IGetEachEncodedStateEngine stateSet) {
    return analysis(stateSet, topology);
  }

  public double analysis(IGetEachEncodedStateEngine stateSet, AbsTopology topology) {
    int nodeNumber = stateSet.getLibSize();
    double subDist, distSum = 0;
    int i, j;
    for (i=0; i<nodeNumber; i++) {
      subDist = 0;
      IBasicICollectionEngine ids = topology.getConnectedNodeIDsAt(i);
      for (j=0; j<ids.getSize(); j++) {
        subDist += distanceEngine.getDistance(stateSet.getSelectedPoint(i).getSearchState(), stateSet.getSelectedPoint(ids.getElementAt(j)).getSearchState());
      }
      distSum += subDist/ids.getSize();
    }
    return distSum/nodeNumber;
  }
}

