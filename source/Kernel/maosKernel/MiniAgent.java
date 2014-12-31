/**
 * Description: The description of compact agent.
 *
 * @Information source: a) its own memory (Ma); b) social sharing memory (Ms)
 * ->More specifically, a) EncodedState baseState; b) StateSet referEngine
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 11, 2003
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel;

import maosKernel.represent.landscape.*;
import maosKernel.memory.*;
import maosKernel.behavior.generate.*;
import maosKernel.behavior.update.*;
import maosKernel.behavior.scratch.*;

public class MiniAgent {
  // the virtual landscape
  private AbsLandscape virtualLandscape;

  /****************memory elements***************/
  //elements in its personal memory (M_A), only one element for simplicity
  protected EncodedState baseState;

  //elements in equivalent social memory (M_S)
  protected IGetEachEncodedStateEngine referEngine;

  //elements in buffer memory (M_G), only one element for simplicity
  private EncodedState trialState;

  /*************** behaviors*******************/
  private AbsScratchSearch maConstructor;

  private AbsMiniGenerator absGenerator;
  private AbsStateUpdater maStateUpdater;

  //indicates the status of the agent
  private boolean isWorked = true;

  public MiniAgent(AbsLandscape virtualLandscape) {
    this.virtualLandscape = virtualLandscape;
    trialState = new EncodedState(virtualLandscape);
    baseState = new EncodedState(virtualLandscape);
  }

  //non-private state from M_A
  public EncodedState getNonprivateState() {
    return this.baseState;
  }

  public void initGenerator(AbsMiniGenerator generator) {
    this.absGenerator = generator;
  }

  public void initAllBehaviors(AbsScratchSearch socialConstructor, AbsStateUpdater maStateUpdater, AbsMiniGenerator generator) {
    initMAStateUpdater(maStateUpdater);
    initGenerator(generator);
    initMAConstructor(socialConstructor);
  }

  public void initMAConstructor(AbsScratchSearch constructor) {
    this.maConstructor = constructor;
  }

  public void initMAStateUpdater(AbsStateUpdater updator) {
    this.maStateUpdater = updator;
  }

  public void initMSElement(IGetEachEncodedStateEngine referEngine) {
    this.referEngine = referEngine;
  }

  public void resetWorkStatus() {
    isWorked = true;
  }

  public boolean getWorkStatus() {
    return isWorked;
  }

  //for t=0
  public void initMemory() {
    baseState.removeEncodeInfo();
    maConstructor.generate(baseState);
    EncodedStateHandler.encodeGlobalCost_AsNotEncoded(virtualLandscape, baseState);
  }

  //for t>0
  public void centralExecute(int clockStep) {
    if (!isWorked) return;
    switch(clockStep) {
      case LearningClock.C_RUN_G:
        trialState.removeEncodeInfo();
        isWorked = absGenerator.generateBehavior(trialState, baseState, referEngine);
        if (isWorked) EncodedStateHandler.encodeGlobalCost_AsNotEncoded(virtualLandscape, trialState);
        return;
      case LearningClock.C_RUN_T:
        maStateUpdater.updateBehavior(baseState, trialState);
        break;
    }
  }

  public EncodedState getMGState() {
    return trialState;
  }
}

