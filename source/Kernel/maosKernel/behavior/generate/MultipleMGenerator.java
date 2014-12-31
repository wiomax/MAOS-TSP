/**
 * Description: The description of generating operator for achieve an trail state in parallel runs
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 05, 2007
 *
 * @version M01.00.02
 */

package maosKernel.behavior.generate;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.memory.*;
import Global.methods.*;
import Global.basic.nodes.utilities.*;
import maosKernel.behavior.pick.*;

public class MultipleMGenerator extends AbsMiniGenerator {
  private AbsMiniGenerator baseGenerator;
  private AbsStatePicker statePicker;

  protected int runNumber = 1;
  private EncodedState[] trailStates = new EncodedState[1];
  private boolean[] generatorFlags;

  private double nScale = 1;
  private ICalcGlobalCostEngine encoder;
  
  public MultipleMGenerator() {}

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("nScale", nScale));
    initUtility(new BasicUtility("baseGenerator", baseGenerator));
    initUtility(new BasicUtility("statePicker", statePicker));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    nScale = TypeConverter.toDouble(getValue("nScale"));
    baseGenerator = (AbsMiniGenerator)(getValue("baseGenerator"));
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

  public AbsMiniGenerator getSubGenerator() {
    return this.baseGenerator;
  }

  public boolean generateBehavior(EncodedState trialState, EncodedState baseState, IGetEachEncodedStateEngine referEngine) {
    int validNumber = 0;
    for (int i=0; i<runNumber; i++) {
       trailStates[i].removeEncodeInfo();
       generatorFlags[i]=baseGenerator.generateBehavior(trailStates[i], baseState, referEngine);
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
