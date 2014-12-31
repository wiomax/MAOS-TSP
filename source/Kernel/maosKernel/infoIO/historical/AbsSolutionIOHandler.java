/**
 * Description: provide the information for solution IO handler
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 12, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.historical;

import maosKernel.memory.*;
import maosKernel.represent.landscape.*;

public abstract class AbsSolutionIOHandler {
  public AbsSolutionIOHandler() {

  }

  public void readSolution(EncodedState state, String content) throws Exception {
    this.naiveReadSolution(state, content);
  }

  public String writeSolution(EncodedState state) throws Exception {
    throw new UnsupportedOperationException();
  }

  public String writeSolution(AbsLandscape virtualLandscape, EncodedState state) throws Exception {
    return writeSolution(state);
  }

  protected void naiveReadSolution(EncodedState state, String content) throws Exception {
    throw new UnsupportedOperationException();
  }
}

