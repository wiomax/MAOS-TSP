/**
 * Description: For picking one state in tournament selection based on distance
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 29, 2008
 *
 * @version M01.00.02
 * @since M01.00.02
 */

package maosKernel.behavior.pick;

import Global.methods.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.*;
import Global.basic.nodes.utilities.*;

public class TourDistanceStatePicker extends AbsStatePicker {
  private boolean isLarger = true;
  private int tao = 1;
  private ICalcDistanceEngine evaluateEngine;
  
  private EncodedState baseState;

  public TourDistanceStatePicker(){}

  protected void setRootInfo(AbsLandscape landscape) {
    evaluateEngine = landscape;
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BooleanUtility("isLarger", isLarger));
    initUtility(new IntegerUtility("tao", tao));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    isLarger = TypeConverter.toBoolean(getValue("isLarger"));
    tao = TypeConverter.toInteger(getValue("tao"));
  }
  
  public void setBaseState(EncodedState baseState) {
    this.baseState = baseState;
  }
  
  public void reverseType() {
    isLarger = !isLarger;
  }

  public int pick(IGetEachEncodedStateEngine referEngine) {
    if (evaluateEngine==null ||tao<2) return StateSetHandler.getRandomIndex(referEngine);

    int selStateID, ultiStateID = StateSetHandler.getRandomIndex(referEngine);
    double newDist, ultiDist = evaluateEngine.getDistance(baseState.getSearchState(), referEngine.getSelectedPoint(ultiStateID).getSearchState());
    for (int i=1; i<tao; i++) {
      selStateID = StateSetHandler.getRandomIndex(referEngine);
      newDist=evaluateEngine.getDistance(baseState.getSearchState(), referEngine.getSelectedPoint(selStateID).getSearchState());
      if (newDist>ultiDist==isLarger) {
        ultiDist = newDist;
        ultiStateID = selStateID;
      }
    }
    return ultiStateID;
  }
}
