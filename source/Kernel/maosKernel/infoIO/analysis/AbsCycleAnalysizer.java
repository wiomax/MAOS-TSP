/**
 * Description: provide the capability for analyzing information obtained in one cycle
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 07, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
*/

package maosKernel.infoIO.analysis;

import maosKernel.represent.information.*;
import maosKernel.represent.landscape.*;

public abstract class AbsCycleAnalysizer extends AbsInformationElement implements ICycleAnalysisEngine {

  public AbsCycleAnalysizer(){
  }

  abstract public void setInfo(AbsLandscape landscape);

}

