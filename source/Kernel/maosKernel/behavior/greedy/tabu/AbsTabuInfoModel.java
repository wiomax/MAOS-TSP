/**
 * Description: provide the taboo information
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Jun 02, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.tabu;

import java.util.Arrays;

import Global.basic.data.matrix.*;

import maosKernel.behavior.greedy.onemove.AbsBasic1MoveModel;
import maosKernel.represent.knowledge.*;

public abstract class AbsTabuInfoModel extends AbsKnowledgeElement implements ITabuInfoModelEngine {
  //temp for tabu table
  private int[][] tenures;
  private int tabuIter = 0;

  public AbsTabuInfoModel() {}
  
  public void initTabuInfo() {
    for(int i=0; i<tenures.length; i++) {
      Arrays.fill(tenures[i], 0);
    }
    tabuIter = 0;
  }
  public void init1MoveModel(AbsBasic1MoveModel basic1MoveModel) {
    initSpace(basic1MoveModel);
  }
  
  private void initSpace(IIrregular2DEngine spaceInfo) {
    tenures = new int[spaceInfo.getNodeNumber()][];
    for (int i=0; i<tenures.length; i++) {
      tenures[i] = new int[spaceInfo.getElementNumberAt(i)];
    }
  }

  public boolean isNotForbidden(int nodeID, int referV, double deltaQuality) {
    return (tenures[nodeID][referV]<=tabuIter);
  }
  
  public void setForbiddenInfo(int nodeID, int baseV, double deltaQuality) {
    tenures[nodeID][baseV] = tabuIter+getTenureValue(nodeID, baseV);
    tabuIter++;
  }

  abstract protected int getTenureValue(int nodeID, int baseV);
  
}

