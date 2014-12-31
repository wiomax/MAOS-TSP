/**
 * Description: The interface for select next node in order to construct a tour
 *  in sequence
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 03, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */


package implement.TSP.behavior.construct;

import Global.methods.*;
import maosKernel.represent.landscape.space.*;

public class BaseNextNodeSelector implements INextNodeSelectEngine {
  private SearchState baseState;
  private int nodeNumber = -1;

  public BaseNextNodeSelector() {
  }

  public void setInfo(SearchState baseState) {
    this.baseState = baseState;
    nodeNumber = baseState.getNodeNumber();
  }

  public int selectNextNodeAt(int cityID, boolean[] occupyFlags) {
    if (baseState==null) return -1;
    int tourID = baseState.getValueIndex(cityID);
    
    int preCityID = baseState.getValueAt(BasicArray.getPrecessorID(nodeNumber, tourID));
    int postCityID = baseState.getValueAt(BasicArray.getSuccessorID(nodeNumber, tourID));
    if (occupyFlags[preCityID] && occupyFlags[postCityID]) {
      return -1;
    } else if (!occupyFlags[preCityID] && occupyFlags[postCityID]) {
      return preCityID;
    } else if (occupyFlags[preCityID] && !occupyFlags[postCityID]) {
      return postCityID;
    } else {
      if (RandomGenerator.booleanRandom()) return preCityID;
      else return postCityID;
    }
  }
}
