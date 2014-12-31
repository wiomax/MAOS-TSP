/**
 * Description: multiagent optimization system (MAOS) - main framework.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 15, 2004
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel;

import Global.basic.*;
import Global.methods.*;
import maosKernel.represent.landscape.*;
import maosKernel.behavior.terminate.*;
import maosKernel.behavior.topology.*;
import maosKernel.behavior.scratch.*;
import maosKernel.behavior.generate.*;
import maosKernel.behavior.update.*;
import maosKernel.misc.*;
import maosKernel.misc.infopool.*;

public final class MAFramework extends BasicAttrib implements IExecCyclesEngine {

  //compact agents
  protected MiniAgent[] agents;
  private int[] activeIDs; // indicates the active status of agents

  //*************** environment **************************//
  protected AbsLandscape virtualLandscape; //kernel of internal representation (IR)

  protected InteractionCenter interactionCenter;

  protected ModuleInitializer moduleInitializer = new ModuleInitializer(); //for initializing components

  //for depositing best solution in the current cycle
  protected SolutionDepositor cycleSolutionDepositor;
  
  public static CalculationPool COMPPool = new CalculationPool();

  //termination condition
  private TerminationCondition overallCutoffCriterion = new TerminationCondition();

  public MAFramework(AbsLandscape virtualLandscape) {
    this.virtualLandscape = virtualLandscape;
    cycleSolutionDepositor = new SolutionDepositor(virtualLandscape);
  }
  
  public void initSwarmShell(int N, int Nact) {
    initSwarm(N);
    initActiveN(Nact);
  }
  
  protected void initActiveN(int Nact) {
    int realNAct = TypeConverter.getNaturalValue(Nact, agents.length);
    if (realNAct==agents.length) {
      activeIDs = new int[agents.length];
      for (int i=0; i<activeIDs.length; i++) {
        activeIDs[i] = i;
      }
    } else activeIDs = new int[Nact];
  }
  
  public void initTerminationCondition(int T_MAX, int T_CON) {
    overallCutoffCriterion.setInformation(T_MAX, T_CON);
    overallCutoffCriterion.setInfo(virtualLandscape);
    moduleInitializer.setModule(overallCutoffCriterion);
  }
  
  public ITerminateCycleCheckEngine getTerminateCycleCheckEngine() {
    return this.overallCutoffCriterion;
  }

  //initiate the behaviors: R_INI, R_T, and R_G.
  public void initBehaviors(AbsScratchSearch absConstructor, AbsStateUpdater maStateUpdater, AbsMiniGenerator generator) {
    for(int i=0; i<agents.length; i++) {
      agents[i].initAllBehaviors(absConstructor, maStateUpdater, generator);
    }
    moduleInitializer.setModule(absConstructor);
    moduleInitializer.setModule(maStateUpdater);
    moduleInitializer.setModule(generator);
  }
  
  public void initInteractionMode(AbsTopology topologyEngine) {

    moduleInitializer.setModule(topologyEngine);
    interactionCenter = new InteractionCenter(virtualLandscape, agents, topologyEngine);

    for(int i=0; i<agents.length; i++) {
      agents[i].initMSElement(interactionCenter.getSocialAccessModuleAt(i));
    }
  }

  protected void initSwarm(int N) {
    agents = new MiniAgent[N];
    for(int i=0; i<N; i++) {
      agents[i] = new MiniAgent(virtualLandscape);
    }
  }

  public void trialLearningCycle() {
    moduleInitializer.initCycle();
    
    COMPPool.clear();

    //by the Interaction Center
    interactionCenter.adjustStatus();

    //by the agents
    if (activeIDs.length<agents.length) RandomGenerator.randomDistinctSelection(agents.length, activeIDs);

    for (int i=0; i<activeIDs.length; i++) {
      agents[activeIDs[i]].resetWorkStatus();
    }

    for (int i=0; i<LearningClock.AGENTS_STEPS.length; i++) {
      for (int j=0; j<activeIDs.length; j++) {
       agents[activeIDs[j]].centralExecute(LearningClock.AGENTS_STEPS[i]);
      }
    }
    
    //Clock step: LearningClock.C_POST
    cycleSolutionDepositor.clear(); //get best state of a cycle
    for (int i=0; i<activeIDs.length; i++) {
      if (agents[activeIDs[i]].getWorkStatus()) {
        cycleSolutionDepositor.depositPoint(agents[activeIDs[i]].getMGState());
      }
    }
  }

  private void initTrial() {
    moduleInitializer.initTrial();

    cycleSolutionDepositor.clear();
    for(int i=0; i<agents.length; i++) {
      agents[i].initMemory();
      cycleSolutionDepositor.depositPoint(agents[i].getNonprivateState());
    }
  }

  public void execOneRun(ICycleInfoEngine cycleCheckEngine) throws Exception {
    int t = 0;
    cycleCheckEngine.initTrial();
    cycleCheckEngine.setMonitorInfo(interactionCenter);
    initTrial();
    
    while(true) {

      if (cycleCheckEngine.cycleCheck(t, cycleSolutionDepositor.getBestState())) break;
      
      t++;
      trialLearningCycle();
    }
  }
}

