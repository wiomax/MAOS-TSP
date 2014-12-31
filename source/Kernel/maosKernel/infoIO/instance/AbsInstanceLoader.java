/**
 * Description: for loading an instance of problem according to ProblemSettings
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 12, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.instance;

import maosKernel.represent.problem.*;

public abstract class AbsInstanceLoader {
  public AbsInstanceLoader() {};

  public AbsProblemData loadProblem(InternelProblemSettings problemSettings) throws Exception {
    AbsProblemData problemData = this.loadPrimaryProblem(problemSettings);
    problemData.setName(problemSettings.getRepresentName());
    problemData.setKey(problemSettings.getKey());
    problemData.importUtilities(problemSettings);
    problemData.shortcutInit();
    return problemData;
  }

  protected abstract AbsProblemData loadPrimaryProblem(InternelProblemSettings problemSettings) throws Exception;
}

