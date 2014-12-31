/**
 * Description: The execution of a local search rule in multiple rounds
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 14, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.meta;

import Global.basic.nodes.utilities.*;

import maosKernel.represent.landscape.quality.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.greedy.*;

public abstract class AbsRepeatLocalSearch extends AbsImplicitLocalSearch implements IGetGlobalCostEngine {
  private AbsImplicitLocalSearch kernelLS;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("kernelLS", kernelLS));
  }
  
  public void shortcutInit() throws Exception {
    super.shortcutInit();
    kernelLS = (AbsImplicitLocalSearch)(getValue("kernelLS"));
  }

  public double encodeGlobal() {
    if (kernelLS instanceof IGetGlobalCostEngine) {
      return ((IGetGlobalCostEngine)kernelLS).encodeGlobal();
    } else {
      return ICalcGlobalCostEngine.WOSRTVALUE;
    }
  }

  public void setState(SearchState baseState) {
    kernelLS.setState(baseState);
  }

  abstract protected boolean repeatedLS(AbsImplicitLocalSearch kernelLS);
  
  public boolean localSearch() {
    return repeatedLS(kernelLS);
  }
}
