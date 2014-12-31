/**
 * Description: For moving one node, and return the delta quality value
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 16, 2006
 * Xiaofeng Xie    May 26, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.nodeLevel;

import maosKernel.represent.landscape.space.*;

public interface ILSLevelNodeEngine {

  //return the delta cost value, return WORSTVALUE if failed
  public double moveNode(int nodeID); 

  public void setState(SearchState baseState);
}
