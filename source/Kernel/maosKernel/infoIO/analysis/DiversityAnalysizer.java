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

import maosKernel.memory.*;
import maosKernel.represent.landscape.*;

public class DiversityAnalysizer extends AbsCycleAnalysizer {
  private ICalcDistanceEngine distanceEngine = null;

  public DiversityAnalysizer(){
    this.setName("Diversity");
  }

  public void setInfo(AbsLandscape landscape) {
    this.distanceEngine = landscape;
  }
  
  public double analysis(IGetEachEncodedStateEngine stateSet) {
    int nodeNumber = stateSet.getLibSize();
    double distSum = 0;
    int i, j;
    for (i=0; i<nodeNumber; i++) {
      for (j=0; j<i; j++) {
        distSum += distanceEngine.getDistance(stateSet.getSelectedPoint(i).getSearchState(), stateSet.getSelectedPoint(j).getSearchState());
      }
    }
    return distSum / ((nodeNumber-1)*nodeNumber/2);
  }
}

