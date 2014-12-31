/**
 * Description: The goodness landscape.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Aug 21, 2008
 * Xiaofeng Xie    Sep 06, 2008    Add ICalcDistanceEngine
 *
 * @version M01.00.00
 */
package maosKernel.represent.landscape;

import Global.basic.data.collection.*;
import maosKernel.represent.problem.*;
import maosKernel.represent.information.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.represent.landscape.quality.*;

public abstract class AbsLandscape extends AbsInformationElement implements IQualityEvaluateEngine, IGetEncodeTypeEngine, ICalcGlobalCostEngine, INodeNumberEngine, ICalcDistanceEngine {
  private AbsSearchSpace representSpace = null;

  public AbsLandscape(AbsProblemData problemData) {
    this.importBasicAttrib(problemData);
    representSpace = initSearchSpace(problemData);
  }

  abstract protected AbsSearchSpace initSearchSpace(AbsProblemData problemData);

  public boolean isFullSpaceMeasuable() {
    return true;
  }

  public AbsSearchSpace getSearchSpace() {
    return representSpace;
  }
  
  public int getNodeNumber() {
    return representSpace.getNodeNumber();
  }
  
  public double getDistance(SearchState stateA, SearchState stateB) {
    return 0;
  }

  //Quality Measurement: If A is no worse than B, then return true
  public boolean evaluate(double encodedA, double encodedB) {
    return encodedA<=encodedB;
  }
}
