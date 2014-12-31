/**
 * Description: Problem Preprocessor
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 05, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel.misc.preprocess;

import maosKernel.represent.problem.*;
import maosKernel.represent.landscape.space.*;

public abstract class AbsProblemTransformer {
  public AbsProblemData transformProblem(AbsProblemData externProblem) {
    return null;
  }
  
  public SearchState restoreState(SearchState internState) {
    return null;
  }
}
