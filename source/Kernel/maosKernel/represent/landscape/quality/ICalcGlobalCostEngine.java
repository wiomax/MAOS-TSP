/**
 * Description: The interface for get global cost
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */


package maosKernel.represent.landscape.quality;

import maosKernel.represent.landscape.space.*;

public interface ICalcGlobalCostEngine {
  public static final double WOSRTVALUE = Double.MAX_VALUE;
  public static final double WOSRTDVALUE = Double.MAX_VALUE;
  public static final int WOSRTIVALUE = Integer.MAX_VALUE;
  public double getGlobalCost(SearchState state);
}
