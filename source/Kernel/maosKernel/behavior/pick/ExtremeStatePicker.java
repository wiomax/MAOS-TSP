/**
 * Description: For picking the extremal state
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

public class ExtremeStatePicker extends AbsStatePicker {
  private boolean isBetter = true;
  private IQualityEvaluateEngine evaluateEngine;

  public ExtremeStatePicker(){}

  protected void setRootInfo(AbsLandscape landscape) {
    evaluateEngine = landscape;
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BooleanUtility("isBetter", isBetter));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    isBetter = TypeConverter.toBoolean(getValue("isBetter"));
  }

  public void reverseType() {
    isBetter = !isBetter;
  }

  public int pick(IGetEachEncodedStateEngine referEngine) {
    return StateSetHandler.getExtremeIndex(evaluateEngine, referEngine, isBetter);
  }
}
