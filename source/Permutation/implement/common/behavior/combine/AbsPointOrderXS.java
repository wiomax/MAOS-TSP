/**
 * Description: the abstract order-based crossover for two sequences (order & position)
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 29, 2006
 * Xiaofeng Xie    Dec 04, 2007    To an abstract "order" version
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 */

package implement.common.behavior.combine;

import Global.methods.*;

import Global.basic.nodes.utilities.*;

public abstract class AbsPointOrderXS extends AbsOrderXS {

  //Flags for tacking unassigned nodes
  private final int MODE_R = -1;  //first nothing, then all at random
  private final int MODE_PR = 0;  //default (first position-based from the refer state, then at random)
  private final int MODE_PP = 1;  //first position-based from the refer state, them partial mapped
  private final int MODE_PO = 3;  //first position-based from the refer state, them order-based
  private final int MODE_O = 2;   //first nothing, then all in order from the refer state

  private int postmode = MODE_PR;  //mode for tacking unassigned nodes

  public AbsPointOrderXS() {}

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("postmode", postmode));
    initUtility(new IntegerUtility("gBaseMode", gBaseMode));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    gBaseMode = TypeConverter.toInteger(getValue("gBaseMode"));
    postmode = TypeConverter.toInteger(getValue("postmode"));
    switch (postmode) {
      case MODE_R:
        referMode = REFER_NONE;
        remainMode = REMAIN_RND;
        break;
      case MODE_PP:
        referMode = REFER_POSITION;
        remainMode = REMAIN_PM;
        break;
      case MODE_PO:
        referMode = REFER_POSITION;
        remainMode = REMAIN_ORDER;
        break;
      case MODE_O:
        referMode = REFER_NONE;
        remainMode = REMAIN_ORDER;
        break;
      default: //MODE_PR
        referMode = REFER_POSITION;
        remainMode = REMAIN_RND;
        break;
    }
  }
}
