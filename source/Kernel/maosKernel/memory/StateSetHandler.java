/**
 * Description: The goodness landscape.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006   
 * Xiaofeng Xie    Mar 24, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */
package maosKernel.memory;

import Global.methods.*;
import maosKernel.represent.landscape.quality.*;

public abstract class StateSetHandler {

  public static int getRandomIndex(IGetEachEncodedStateEngine stateSet) {
    return RandomGenerator.intRangeRandom(stateSet.getLibSize());
  }

  
  public static EncodedState getRandomOne(IGetEachEncodedStateEngine stateSet) {
    return stateSet.getSelectedPoint(RandomGenerator.intRangeRandom(stateSet.getLibSize()));
  }

  public static int getIndex(EncodedState baseState, IGetEachEncodedStateEngine stateSet) {
    int size = stateSet.getLibSize();
    for (int i=0; i<size; i++) {
      if (baseState.equals(stateSet.getSelectedPoint(i))) {
        return i;
      }
    }
    return -1;
  }

  public static int getExtremeIndex(IQualityEvaluateEngine evaluater, IGetEachEncodedStateEngine stateSet, boolean isBetter) {
    int size = stateSet.getLibSize();
    if (size==0) return -1;
    if (evaluater==null) return getRandomIndex(stateSet);
    int baseStateID = 0;
    for (int i=1; i<size; i++) {
      if (isBetter==EncodedStateHandler.evaluate(evaluater, stateSet.getSelectedPoint(i), stateSet.getSelectedPoint(baseStateID))) {
        baseStateID = i;
      }
    }
    return baseStateID;
  }
  
  public static EncodedState getExtremeOne(IQualityEvaluateEngine evaluater, IGetEachEncodedStateEngine stateSet, boolean isBetter) {
    int extremeID = getExtremeIndex(evaluater, stateSet, isBetter);
    if (extremeID == -1) return null;
    return stateSet.getSelectedPoint(extremeID);
  }
}
