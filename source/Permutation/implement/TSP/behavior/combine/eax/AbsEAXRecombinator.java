/**
 * Description: The Generalized EAX (GEAX) combinator with diffusion control and AB-selecting.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005
 * Xiaofeng Xie    Jun 29, 2005    From diffusion control
 * Xiaofeng Xie    Apr 28, 2006    the AB-selecting interface
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 * [1] Yuichi Nagata. The EAX Algorithm Considering Diversity Loss. PPSN, 2004
 * -> For the original EAX
 * [2] Xiao-Feng Xie, Jiming Liu. How autonomy oriented computing (AOC) tackles a
 *  computationally hard optimization problem, International Joint Conference
 *  on Autonomous Agents & Multiagent Systems (AAMAS), Hakodate, Japan, 2006: 646-653
 *  -> For introducing the parameter Cd
 * [3] Xiao-Feng Xie, Jiming Liu. Multiagent optimization system for solving the traveling 
 *  salesman problem (TSP). IEEE Transactions on Systems, Man, and Cybernetics - Part B, 
 *  2009, 39(2): 489-502 
 *  -> To a generalized EAX version with the AB-selecting interface
 */

package implement.TSP.behavior.combine.eax;

import Global.methods.*;
import Global.basic.nodes.utilities.*;
import Global.basic.data.collection.*;

import maosKernel.memory.EncodedState;
import maosKernel.represent.landscape.*;
import maosKernel.behavior.combine.*;
import implement.TSP.represent.*;
import implement.TSP.behavior.repair.*;
import implement.TSP.behavior.neighbor.*;

public abstract class AbsEAXRecombinator extends AbsRecombinationSearch {
  private double Cd = 1.5;
  private int Nmtm = 20;
  protected int nABCycle = 100; //seldom be changed
  private int joinType = 0; 

  /**************************************************/
  private int[][] costMatrix;
  private AbsNeighborManager neighborEngine;

  /**************************************************/
  private ABCycleLib abCycleLib;
  private CyclesBridger cyclesManager;

  private DChainState aLinkPoint;
  private DChainState bLinkPoint;
  private DChainState kidDLinkPoint;
  /**************************************************/
  private DualIAlienArray activeCycleIDs;
  private boolean[] commonFlagArray;
  
  public AbsEAXRecombinator() {}

  protected void init(int nodeNumber) {
    abCycleLib = new ABCycleLib(nodeNumber);
    abCycleLib.setMaxSize(nABCycle);
    aLinkPoint = new DChainState(nodeNumber);
    bLinkPoint = new DChainState(nodeNumber);
    kidDLinkPoint = new DChainState(nodeNumber);
    activeCycleIDs = new DualIAlienArray(nodeNumber);
    commonFlagArray = new boolean[nodeNumber];
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("Cd", Cd));
    initUtility(new IntegerUtility("Nmtm", Nmtm));
    initUtility(new IntegerUtility("nABCycle", nABCycle));
    initUtility(new BasicUtility("neighborEngine", neighborEngine));
    initUtility(new IntegerUtility("joinType", joinType));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    Cd = TypeConverter.toDouble(getValue("Cd"));
    Nmtm = TypeConverter.toInteger(getValue("Nmtm"));
    nABCycle = TypeConverter.toInteger(getValue("nABCycle"));
    neighborEngine = (AbsNeighborManager)(getValue("neighborEngine"));
    joinType = TypeConverter.toInteger(getValue("joinType"));
  }

  protected void setRootInfo(AbsLandscape landscape) {
    costMatrix = ((InternalRepresentation)landscape).getIGetProblemDataEngine().getCostMatrix();
    cyclesManager = new CyclesBridger(((InternalRepresentation)landscape).getIGetProblemDataEngine());
    init(landscape.getSearchSpace().getNodeNumber());
  }

  //The AB-selecting rule
  protected abstract void designABSet(IDynamicICollectionEngine activeCycleIDs, int Nmtm, ABCycleLib abCycleLib, int[][] costMatrix);
 
  public boolean generate(EncodedState trialState, EncodedState baseState, EncodedState referState) {
    
    aLinkPoint.importPoint(baseState.getSearchState().getIArray());
    bLinkPoint.importPoint(referState.getSearchState().getIArray());
    //Step 1.1: build the AB-Library
    
    for( int i = 0; i < commonFlagArray.length; i++ ) {
      commonFlagArray[i]= (aLinkPoint.isFullRepeat(bLinkPoint, i));
    }

    abCycleLib.setABCycles(aLinkPoint, bLinkPoint, commonFlagArray);

    int size = abCycleLib.getSize();

    if (size==0) {
      return false;
    }

    //Step 1.2: design the AB-set
    activeCycleIDs.clear();
    designABSet(activeCycleIDs, Nmtm, abCycleLib, costMatrix);

    boolean isWorked = false;
    double bestEvalC = Double.MAX_VALUE;
    double currEvalC;

    int idSetSize = activeCycleIDs.getSize();
    int diffV = 0;
    int activeCycleID;
    int deltaCost = 0;
    for (int i=0; i<idSetSize; i++) {
      kidDLinkPoint.importPoint(aLinkPoint);
      activeCycleID = activeCycleIDs.getElementAt(i);
      //Step 2.1: kick the state into a reference structure with disjointed sub-cycles
      changeSolForABCycle(activeCycleID);
      diffV = getCycleCostDifference(activeCycleID, costMatrix);
      //Step 2.2: Repair the reference structure into a valid tour in greedy way
      diffV += cyclesManager.bridgeCycles(kidDLinkPoint, neighborEngine, commonFlagArray, joinType);

      currEvalC = diffV/(Math.pow(getDIS(activeCycleID), Cd));
      //Step 3: choose one state among the candidate child states as the output 
      //by a state-competing rule with both the high-quality and distance criteria
      if (currEvalC<bestEvalC) {
        kidDLinkPoint.toBasicPoint(trialState.getSearchState().getIArray());
        deltaCost = diffV;
        bestEvalC = currEvalC;
        isWorked = true;
      }
    }
    if (isWorked) trialState.setEncodeInfo(baseState.getEncodeInfo()+deltaCost);
    return isWorked;
  }

  //abCycle: the even edges are removed, and the odd edges are added
  //From a single cycle -> multiple disjointed cycles
  public void changeSolForABCycle(int cycleID) {
    int r1,r2, j1,j2;
    int[] abCycle = abCycleLib.getCycleData();
    int startID = abCycleLib.getCycleStartIndex(cycleID);
    int n_cycle = abCycleLib.getCycleSizeAt(cycleID);

    for(j1=0;j1<n_cycle/2;j1++) {
      j2 = 2*j1;
      r1=abCycle[1+j2+startID];r2=abCycle[(2+j2)%n_cycle+startID];

      kidDLinkPoint.changeChainCityIDAt(r1, r2, abCycle[  j2+startID]);
      kidDLinkPoint.changeChainCityIDAt(r2, r1, abCycle[(3+j2)%n_cycle+startID]);
    }
  }

  protected int getCycleCostDifference(int cycleID, int[][] costMatrix) {
    int[] abCycle = abCycleLib.getCycleData();
    int startID = abCycleLib.getCycleStartIndex(cycleID);
    int n_cycle = abCycleLib.getCycleSizeAt(cycleID);

    int realID, diffV = 0;
    for (int i=0; i<n_cycle/2; i++) {
      realID = 2*i+startID;
      diffV += costMatrix[abCycle[realID]][abCycle[realID+1]];
      diffV -= costMatrix[abCycle[realID+1]][abCycle[(2*i+2)%n_cycle+startID]];
    }
    return diffV;
  }

  private int getDIS(int cycleID) {
    return abCycleLib.getCycleSizeAt(cycleID)/2;
  }
}
