/**
 * Description: The initialization engine, which initializing
 *  1. AbsSolutionIOHandler, AbsInstanceLoader
 *  2. representation (virtual landscape)
 *  3. LowerBound, if possible
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 13, 2006    To be independent module
 * Xiaofeng Xie    Mar 21, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel;

import Global.basic.*;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.represent.problem.*;
import maosKernel.infoIO.historical.*;
import maosKernel.infoIO.instance.*;
import maosKernel.infoIO.runtime.*;
import maosKernel.infoIO.setting.*;
import maosKernel.infoIO.analysis.*;

public abstract class AbsMAOSInitializer extends BasicAttrib {

  public AbsMAOSInitializer() {
  }

  protected abstract AbsInstanceLoader getInstanceLoader();

  public abstract AbsSolutionIOHandler getSolutionIOHandler();

  public AbsCycleAnalysizer getCycleAnalysizer() {
    return null;
  }
  
  protected InternelProblemSettings getInternelProblemSettings() {
    return new FileInternelProblemSettings();
  }

  public CycleResult getCycleResult() {
    return new CycleResult();
  }

  public double getLowerBound() {
    return ICalcGlobalCostEngine.WOSRTVALUE;
  }

  public AbsProblemData initProblemData(CMDLineProblemSettings problemSettings, IInstanceInputEngine instanceInputEngine) throws Exception {
    InternelProblemSettings internelProblemSettings = getInternelProblemSettings();
    internelProblemSettings.importBasicAttrib(problemSettings);
    internelProblemSettings.initUtilities();
    internelProblemSettings.importUtilities(problemSettings);
    internelProblemSettings.shortcutInit();
    AbsInstanceLoader instanceLoader = getInstanceLoader();
    if (instanceLoader instanceof AbsFileInstanceLoader) {
      ((AbsFileInstanceLoader)instanceLoader).setInstanceInputEngine(instanceInputEngine);
    }
    return instanceLoader.loadProblem(internelProblemSettings);
  }

  abstract public AbsLandscape initLandscape(AbsProblemData absProblemData) throws Exception;
}
