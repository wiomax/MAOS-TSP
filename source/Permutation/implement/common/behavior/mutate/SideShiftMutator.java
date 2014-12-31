/**
 * Description:
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 20, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.mutate;

import Global.methods.*;
import maosKernel.represent.landscape.AbsLandscape;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.incumbent.*;

public class SideShiftMutator extends AbsPerturbationSearch {
  private int[] xPoints = new int[2];
  private int[] tempArray;

  public SideShiftMutator() {
  }
 
  protected void setRootInfo(AbsLandscape landscape) {
    tempArray = new int[landscape.getNodeNumber()];
  }

  public boolean generate(SearchState baseState){
    int nodeNumber = baseState.getNodeNumber();
    RandomGenerator.randomDistinctSelection(nodeNumber, xPoints);
    int[] dataArray = baseState.getIArray();
    
    int length = xPoints[1]-xPoints[0];
    
    if (length<0) { //left corner
      System.arraycopy(dataArray, 0, tempArray, 0, xPoints[0]);
      System.arraycopy(tempArray, 0, dataArray, -length, xPoints[1]);
      System.arraycopy(tempArray, xPoints[1], dataArray, 0, -length);
    } else {
      System.arraycopy(dataArray, xPoints[0], tempArray, 0, nodeNumber-xPoints[0]);
      System.arraycopy(tempArray, 0, dataArray, nodeNumber-length, length);
      System.arraycopy(tempArray, length, dataArray, xPoints[0], nodeNumber-xPoints[1]);
    }
    return true;
  }
}
