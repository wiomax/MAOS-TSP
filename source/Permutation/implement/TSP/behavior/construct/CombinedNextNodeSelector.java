/**
 * Description:
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 03, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */


package implement.TSP.behavior.construct;

import java.util.*;
import Global.methods.*;


public class CombinedNextNodeSelector implements INextNodeSelectEngine {
  Vector<INextNodeSelectEngine> nexNodeSelectors = new Vector<INextNodeSelectEngine>();
  double[] selRatios = new double[0];

  public CombinedNextNodeSelector() {
  }

  public void addElement(INextNodeSelectEngine selector, double selRatio) {
    double[] newSelRatios = new double[selRatios.length+1];
    System.arraycopy(selRatios, 0, newSelRatios, 0, selRatios.length);
    newSelRatios[selRatios.length] = selRatio;
    selRatios = newSelRatios;
    nexNodeSelectors.addElement(selector);
  }

  public int selectNextNodeAt(int cityID, boolean[] occupyFlags) {
    if (nexNodeSelectors.size()==0) return -1;
    int selID = BasicArray.getProbSelectIndex(selRatios, true);
    return ((INextNodeSelectEngine)nexNodeSelectors.elementAt(selID)).selectNextNodeAt(cityID, occupyFlags);
  }
}
