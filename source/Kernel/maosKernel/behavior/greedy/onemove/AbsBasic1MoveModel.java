/**
 * Description: provide the information for 1-move. Here a 1-move
 *   changes the value of a single node in the incumbent state. 
 *   Normally, 1-moves possess the connectivity property.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Oct 06, 2006 
 * Xiaofeng Xie    Nov 30, 2007
 * Xiaofeng Xie    Jun 12, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.onemove;

import maosKernel.represent.knowledge.*;
import maosKernel.represent.landscape.space.*;

public abstract class AbsBasic1MoveModel extends AbsKnowledgeElement implements IBasic1MoveModelEngine {
  protected SearchState baseState;
  public AbsBasic1MoveModel() {}
  
  public int getNodeNumber() {
    return baseState.getNodeNumber();
  }
  
  public void setState(SearchState baseState) {
    this.baseState = baseState;
  }
}

