/**
 * Description: Defined the termination conditions
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.terminate;

import Global.util.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.AbsLandscape;
import maosKernel.represent.landscape.quality.IQualityEvaluateEngine;
import maosKernel.behavior.*;

public final class TerminationCondition extends AbsBehavior implements ITerminateCycleCheckEngine {
  private int T_MAX = Integer.MAX_VALUE;
  private int T_CON = Integer.MAX_VALUE;

  private FalseCounter falseCounter = new FalseCounter();
  
  //temp values
  private EncodedState baseState = null;
  private IQualityEvaluateEngine evalEngine;

  public TerminationCondition() {
  }
  
  protected void setRootInfo(AbsLandscape landscape){
    baseState = new EncodedState(landscape);
    evalEngine = landscape;
  }

  public void setInformation(int t_MAX, int t_CON) {
    T_CON = t_CON;
    if (T_CON<0) T_CON = Integer.MAX_VALUE;
    T_MAX = t_MAX;
  }
  
  public void initTrial() {
    baseState.removeEncodeInfo();
    falseCounter.clear();
  }

  public boolean isCycleTerminated(int currentNCycle, EncodedState cycleBestState) {
    boolean isImproved = !EncodedStateHandler.evaluate(evalEngine, baseState, cycleBestState);
    falseCounter.submitValue(isImproved);
    if (isImproved) baseState.importEncodeState(cycleBestState);
    return falseCounter.getNumber() >= T_CON || currentNCycle>=T_MAX;
  }
}
