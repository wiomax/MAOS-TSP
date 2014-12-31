/**
 * Description: For picking one state in tournament selection
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.pick;

import Global.methods.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.*;
import Global.basic.nodes.utilities.*;
import maosKernel.represent.landscape.quality.*;

public class TourStatePicker extends AbsStatePicker {
  private boolean isBetter = true;
  private int taoB = 1;
  private IQualityEvaluateEngine evaluateEngine;

  public TourStatePicker(){}

  protected void setRootInfo(AbsLandscape landscape) {
    evaluateEngine = landscape;
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BooleanUtility("isBetter", isBetter));
    initUtility(new IntegerUtility("taoB", taoB));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    isBetter = TypeConverter.toBoolean(getValue("isBetter"));
    taoB = TypeConverter.toInteger(getValue("taoB"));
  }

  public void reverseType() {
    isBetter = !isBetter;
  }

  public int pick(IGetEachEncodedStateEngine referEngine) {
    if (evaluateEngine==null ||taoB<2) return StateSetHandler.getRandomIndex(referEngine);

    int selStateID, baseStateID = StateSetHandler.getRandomIndex(referEngine);
    int maxSize= Math.min(taoB, referEngine.getLibSize());
    for (int i=1; i<maxSize; i++) {
      selStateID = StateSetHandler.getRandomIndex(referEngine);
      if (isBetter==EncodedStateHandler.evaluate(evaluateEngine, referEngine.getSelectedPoint(selStateID), referEngine.getSelectedPoint(baseStateID))) {
        baseStateID = selStateID;
      }
    }
    return baseStateID;
  }
}
