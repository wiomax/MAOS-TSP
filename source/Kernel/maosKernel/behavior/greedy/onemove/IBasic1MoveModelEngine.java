/**
 * Description: provide the information for 1-move
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.onemove;

import Global.basic.data.matrix.*;
import maosKernel.represent.landscape.space.*;

public interface IBasic1MoveModelEngine extends IIrregular2DEngine {
  public final static int INVALID_V = -Integer.MAX_VALUE;

  public int getBaseValue(int nodeID);
  public double getRelativeQuality(int nodeID, int referV);
  public void actualMove(int nodeID, int referV);
  public void setState(SearchState baseState);

}

