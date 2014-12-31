/**
 * Description: The description of multiple macro operator for achieve an trail state in parallel runs
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 05, 2007
 *
 * @version M01.00.02
 */

package maosKernel.behavior.greedy.meta;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.memory.*;
import Global.methods.*;
import Global.basic.nodes.utilities.*;
import maosKernel.behavior.pick.*;
import maosKernel.behavior.greedy.*;

public class MultipleMLocalSearch extends AbsExplicitLocalSearch {
  private AbsExplicitLocalSearch baseSearcher;
  private AbsStatePicker statePicker;

  protected int runNumber = 1;
  private EncodedState[] trailStates = new EncodedState[1];
  private boolean[] generatorFlags;

  private double nScale = 1;
  private ICalcGlobalCostEngine encoder;
  
  public MultipleMLocalSearch() {}

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("nScale", nScale));
    initUtility(new BasicUtility("baseSearcher", baseSearcher));
    initUtility(new BasicUtility("statePicker", statePicker));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    nScale = TypeConverter.toDouble(getValue("nScale"));
    baseSearcher = (AbsExplicitLocalSearch)(getValue("baseSearcher"));
    statePicker = (AbsStatePicker)getValue("statePicker");
  }

  protected void setRootInfo(AbsLandscape landscape){
    super.setRootInfo(landscape);
    this.encoder = landscape;
    runNumber = Math.max((int)Math.rint(this.nScale*landscape.getNodeNumber()),1);
    trailStates = new EncodedState[runNumber];
    generatorFlags = new boolean[runNumber];
    for (int i=0; i<runNumber; i++) {
      trailStates[i] = new EncodedState(landscape);
    }
  }
  protected boolean generate(SearchState baseState) { return true; }

  public boolean generate(EncodedState trialState) {
    int validNumber = 0;
    for (int i=0; i<runNumber; i++) {
       trailStates[i].removeEncodeInfo();
       generatorFlags[i]=baseSearcher.generate(trailStates[i]);
       if (generatorFlags[i]) {
         EncodedStateHandler.encodeGlobalCost_AsNotEncoded(encoder, trailStates[i]);
         validNumber ++;
       }
     }
     if (validNumber==0) return false;
     EncodedState[] validStates = trailStates;
     if (validNumber<runNumber) {
       validStates = new EncodedState[validNumber];
       int index = 0;
       for (int i=0; i<runNumber; i++) {
         if (generatorFlags[i]) {
           validStates[index] = trailStates[i];
           index ++;
         }
       }
     }

     trialState.importEncodeState(statePicker.pickBehavior(new StateSet(validStates)));
     return true;
  }

}
