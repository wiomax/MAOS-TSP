/**
 * Description: The description of multiple macro operator for achieve an trail state in parallel runs
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 05, 2007
 *
 * @version M01.00.02
 */

package maosKernel.behavior.combine;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.memory.*;
import Global.methods.*;
import Global.basic.nodes.utilities.*;
import maosKernel.behavior.pick.*;

public class MultipleMRecombinationSearch extends AbsRecombinationSearch {
  private AbsRecombinationSearch baseSearcher;
  private AbsStatePicker statePicker;

  protected int runNumber = 1;
  private EncodedState[] trailStates = new EncodedState[1];
  private boolean[] generatorFlags;

  private double nScale = 1;
  private ICalcGlobalCostEngine encoder;
  
  public MultipleMRecombinationSearch() {}

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("nScale", nScale));
    initUtility(new BasicUtility("baseSearcher", baseSearcher));
    initUtility(new BasicUtility("statePicker", statePicker));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    nScale = TypeConverter.toDouble(getValue("nScale"));
    baseSearcher = (AbsRecombinationSearch)(getValue("baseSearcher"));
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

  public boolean generate(EncodedState trialState, EncodedState baseState, EncodedState referState) {
    int validNumber = 0;
    for (int i=0; i<runNumber; i++) {
       trailStates[i].removeEncodeInfo();
       generatorFlags[i]=baseSearcher.generate(trailStates[i], baseState, referState);
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
