/**
 * Description: The goodness landscape.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 12, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */
package implement.TSP.represent;

import maosKernel.represent.problem.*;
import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;
import implement.common.represent.*;
import implement.common.knowledge.distance.*;

public final class InternalRepresentation extends AbsILandscape {
  protected RealProblemData dataStorager = null;

  public InternalRepresentation(RealProblemData problemData) {
    super(problemData);
    this.dataStorager = problemData;
  }

  public IGetProblemDataEngine getIGetProblemDataEngine() {
    return dataStorager;
  }

  protected AbsSearchSpace initSearchSpace(AbsProblemData problemData) {
    return new SeqSearchSpace(problemData.getNodeNumber());
  }

  public double getGlobalCost(SearchState state) {
  	return dataStorager.getGlobalCost(state.getIArray());
  }
  
  public double getLocalCost(int nodeA, int nodeB) {
  	return dataStorager.getLocalCost(nodeA, nodeB);
  }

  ICalcDistanceEngine distanceCalculator = new AdjacencyDistanceCalculator();
  public double getDistance(SearchState stateA, SearchState stateB) {
    return distanceCalculator.getDistance(stateA, stateB);
  }
}
