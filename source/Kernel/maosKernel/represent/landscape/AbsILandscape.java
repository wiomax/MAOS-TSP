/**
 * Description: The goodness landscape with integer fitness value.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel.represent.landscape;

import maosKernel.represent.problem.*;

public abstract class AbsILandscape extends AbsLandscape {
  public boolean isDiscrete() {
    return true;
  }

  public AbsILandscape(AbsProblemData problemData) {
    super(problemData);
  }
}
