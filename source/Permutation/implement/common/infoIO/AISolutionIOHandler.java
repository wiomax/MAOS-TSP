/**
* Description: provide IOHandler for writing/reading solution in
 *  integer array
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 6, 2005     xiaofengxie@tsinghua.org.cn
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.infoIO;

import Global.define.*;
import Global.math.*;
import Global.methods.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.*;
import maosKernel.infoIO.historical.*;

public class AISolutionIOHandler extends AbsSolutionIOHandler {

  public String writeSolution(AbsLandscape virtualLandscape, EncodedState state) throws Exception {
    String colStr = "";
    int[] solution = state.getSearchState().getIArray();
    for (int i=0; i<solution.length; i++) {
      colStr += solution[i]+BasicTag.RETURN_TAG;
    }
    return colStr;
  }

  protected void naiveReadSolution(EncodedState state, String content) throws Exception {
    String[] lines = GlobalString.getMeaningfulLines(content);
    int[] solution = state.getSearchState().getIArray();
    int index = 0;
    for (int i=0; i<lines.length; i++) {
      String[] lineContents = GlobalString.tokenize(lines[i], BasicTag.NULL_SEPERATE_TAG);
      for (int j=0; j<lineContents.length; j++) {
        solution[index] = Integer.parseInt(lineContents[j]);
        index++;
        if (index>=solution.length) {
          return;
        }
      }
    }
  }

  public void readSolution(EncodedState state, String content) throws Exception {
    super.readSolution(state, content);
    removeBias(state.getSearchState().getIArray());
  }

  private void removeBias(int[] sol) {
    //remove bias value
    int biasV = ArrayMath.getExtermal(sol, false);
    if (biasV!=0) {
      for (int i=0; i<sol.length; i++) {
        sol[i] -= biasV;
      }
    }
  }
}

