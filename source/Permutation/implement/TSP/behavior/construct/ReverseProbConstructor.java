/**
 * Description: The description of construction strategy based on reverse probability.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005    For TSP problem
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Oct 08, 2006    Simplified version
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 * [1] Xiao-Feng Xie, Jiming Liu. Multiagent optimization system for solving the traveling 
 *  salesman problem (TSP). IEEE Transactions on Systems, Man, and Cybernetics - Part B, 
 *  2009, 39(2): 489-502 
 */

package implement.TSP.behavior.construct;

import maosKernel.represent.landscape.*;
import maosKernel.memory.*;
import implement.TSP.represent.*;

public class ReverseProbConstructor extends AbsNNConstructor {

  private SequenceTourConstructor tourConstructor;

  public ReverseProbConstructor() {}

  public void reInitialized(){}

  protected void setRootInfo(AbsLandscape landscape) {
    ReverseProbabilityHolder probabilityHolder = new ReverseProbabilityHolder(super.neighborEngine, ((InternalRepresentation)landscape).getIGetProblemDataEngine());
    ProbNNNextNodeSelector probNNNextNodeSelector = new ProbNNNextNodeSelector(neighborEngine, probabilityHolder);
    tourConstructor = new SequenceTourConstructor(neighborEngine.getNodeNumber(), probNNNextNodeSelector);
  }

  public boolean generate(EncodedState trialState) {
    return tourConstructor.generate(trialState.getSearchState());
  }
}
