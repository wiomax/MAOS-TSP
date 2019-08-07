/**
 * Description: The description of multiple parent complex search
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 19, 2008
 *
 * @version M01.00.02
 * @since M01.00.02
 */

package implement.common.behavior.complex;

import java.util.Arrays;

import Global.basic.nodes.utilities.*;

import maosKernel.memory.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.complex.*;
import maosKernel.behavior.pick.*;
import maosKernel.misc.infopool.*;

public class MultiComplexSearcher extends AbsComplexSearch {
  private double ratio = 0.5;
  private int[][] desireMatrix;
  
  private AbsStatePicker statePicker = new RandStatePicker();
  
  AbsDirectCalculater extremeCalculator = new AbsDirectCalculater() {
    public Object calculate(Object[] inputParams) {
      return statePicker.pickBehavior((IGetEachEncodedStateEngine)inputParams[0]);
    }
  };

  public MultiComplexSearcher() { }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("ratio", ratio));
    initUtility(new BasicUtility("statePicker", statePicker));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    ratio = ((Double) getValue("ratio")).doubleValue();
    statePicker = (AbsStatePicker) getValue("statePicker");
  }
  
  public void calcDesireMatrix(int[][] desireMatrix, IGetEachEncodedStateEngine referEngine) {
    int nodeNumber = desireMatrix.length;
    for (int i=0; i<nodeNumber; i++) {
      Arrays.fill(desireMatrix[i], 0);
    }
    for (int i=0; i<referEngine.getLibSize(); i++) {
      SearchState referState = referEngine.getSelectedPoint(i).getSearchState();
      for (int j=0; j<nodeNumber; j++) {
        desireMatrix[j][referState.getValueAt(j)] ++;
      }
    }
  }

  
  //unfinished
  public boolean generate(EncodedState trialState, EncodedState baseState, IGetEachEncodedStateEngine referEngine) {
    int number = trialState.getSearchState().getNodeNumber();
    if (desireMatrix == null) desireMatrix = new int[number][number];
    calcDesireMatrix(desireMatrix, referEngine);
    
//    int selID = statePicker.pick(referEngine);
//    EncodedState referState = referEngine.getSelectedPoint(selID);
    
    return true;
  }

}
