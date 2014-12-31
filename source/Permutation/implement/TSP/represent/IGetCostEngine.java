/**
 * Description: The interface for get global cost and local cost between two nodes
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */


package implement.TSP.represent;

import maosKernel.represent.landscape.quality.*;

public interface IGetCostEngine extends IGetLocalCostEngine, ICalcGlobalCostEngine {
}
