/**
 * Description: The description of ant construction strategy.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005     For TSP problem
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.TSP.behavior.construct;

import maosKernel.represent.landscape.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.space.*;
import implement.TSP.represent.*;

public class AntConstructor extends AbsNNConstructor {

  private SequenceTourConstructor tourConstructor;

  public AntConstructor() {}

  public void reInitialized(){}

  protected void setRootInfo(AbsLandscape landscape) {
    AbsSearchSpace searchSpace = landscape.getSearchSpace();
    int nodeNumber = searchSpace.getNodeNumber();
    PheromoneHolder pheromoneHolder = new PheromoneHolder((InternalRepresentation)landscape, ((InternalRepresentation)landscape).getIGetProblemDataEngine(), nodeNumber);
    pheromoneHolder.initPheromoneHolder(searchSpace.getRandomState());
    ProbNNNextNodeSelector antNNNextNodeSelector = new ProbNNNextNodeSelector(neighborEngine, pheromoneHolder);
    tourConstructor = new SequenceTourConstructor(pheromoneHolder.getNodeNumber(), antNNNextNodeSelector);
  }

  public boolean generate(EncodedState trialState) {
    return tourConstructor.generate(trialState.getSearchState());
  }
}
