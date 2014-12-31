/**
 * Description: The intialization engine for TSP.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 12, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */
package implement.TSP;

import maosKernel.*;
import maosKernel.represent.problem.*;
import maosKernel.represent.landscape.*;
import maosKernel.infoIO.historical.*;
import maosKernel.infoIO.instance.*;
import implement.TSP.infoIO.*;
import implement.TSP.represent.*;

public class RealMAOSInitializer extends AbsMAOSInitializer {

  public RealMAOSInitializer() {
   }

   public AbsInstanceLoader getInstanceLoader() {
     return new TSPInstanceLoader();
   }

   public AbsSolutionIOHandler getSolutionIOHandler() {
     return new TSPSolutionIOHandler();
   }

  public AbsLandscape initLandscape(AbsProblemData absProblemData) throws Exception {
    return new InternalRepresentation((RealProblemData)absProblemData);
  }
}
