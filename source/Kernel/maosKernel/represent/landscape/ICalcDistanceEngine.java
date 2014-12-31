/**
 * Description: The interface for get distance between two states
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 06, 2008 
 *
 * @version M01.00.00
 */


package maosKernel.represent.landscape;

import maosKernel.represent.landscape.space.*;

public interface ICalcDistanceEngine {
  public double getDistance(SearchState stateA, SearchState stateB);
}
