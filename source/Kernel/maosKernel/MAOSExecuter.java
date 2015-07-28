/**
 * @Description: The main entrance for the MAOS
 *
 * @General_Information
 *  Portal: http://www.wiomax.com/MAOS-TSP/
 *  E-MAIL: info@wiomax.com
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 * Xiaofeng Xie    Dec 01, 2014    MAOS M01.00.03
 *
 * @version: see maosKernel.ProductDefinition.java
 * 
 * @License: See the Creative Commons Non-Commercial License 3.0 for more details.
 *           Please acknowledge the author(s) if you use this code in any way.
 *******************************************************************
 *
 * @Reference
 * [1] Xiao-Feng Xie, Wen-Jun Zhang. SWAF: swarm algorithm framework for
 *     numerical optimization. Genetic and Evolutionary Computation Conference
 *     (GECCO), LNCS 3102, Washington, USA, 2004: 238-250
 * [2] Xiao-Feng Xie, Jiming Liu. A compact multiagent system based on
 *     autonomy oriented computing, IEEE/WIC/ACM International Conference
 *     on Intelligent Agent Technology (IAT), Compiegne, France, 2005: 38-44
 * [3] Xiao-Feng Xie, Jiming Liu. How autonomy oriented computing (AOC) tackles a
 *     computationally hard optimization problem, International Joint Conference
 *     on Autonomous Agents & Multi Agent Systems (AAMAS), Hakodate, Japan, 2006:
 *     646-653
 * [4] Xiao-Feng Xie, Jiming Liu. A mini-swarm for the quadratic knapsack
 *     problem. IEEE Swarm Intelligence Symposium (SIS), Hawaii, USA, 2007.
 * [5] Xiao-Feng Xie, Jiming Liu. Graph coloring by multiagent fusion search. 
 *     Journal of Combinatorial Optimization, In Press.
 * [6] Xiao-Feng Xie, Jiming Liu. Multiagent optimization system for solving the traveling 
 *     salesman problem (TSP). IEEE Transactions on Systems, Man, and Cybernetics - Part B, 
 *     2009, 39(2): 489-502 
 */

package maosKernel;

import java.util.*;

import Global.define.*;
import Global.util.*;
import Global.system.*;
import Global.methods.*;
import maosKernel.infoIO.*;
import maosKernel.infoIO.runtime.*;
import maosKernel.infoIO.setting.*;
import maosKernel.infoIO.screen.*;
import maosKernel.infoIO.historical.*;
import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.ICalcGlobalCostEngine;
import maosKernel.represent.problem.*;
import maosKernel.behavior.topology.*;

public final class MAOSExecuter {

  public static void main(String[] args) {
    try {
      ProductDefinition.printMAOSHead();
      GradedOut.showEXTREMEMessage("Started at "+Calendar.getInstance().getTime().toString());
      new MAOSExecuter(args);
    }
    catch (Exception e) {
      System.out.println(MessageTags.MSGTAG_ERROR + e.toString());
      System.out.println("@The system has failed.");
      System.exit(-1);
    }
  }
  
  /**
   * To start application from given arguments.
   * @param      args    the arguments input by users.
   */
  public MAOSExecuter(String[] args) throws Exception {

    if (args.length<1) {
      GradedOut.showEXTREMEMessage(MessageTags.MSGTAG_PLAIN+"Usage: "+this.getClass().getName()+" $PROBTYPE:[NAME=VALUE] N=* Nact=* T=* Tcon=* solver=*:[NAME=VALUE]"+BasicTag.RETURN_TAG);
      return;
    }

    GlobalTools.CPUTimeCostCounter.setStart("initTime");

    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_SECT+"[Initialization Information]"+BasicTag.RETURN_TAG);
    
    GradedOut.showNORMALMessage("$PRJ_PATH="+System.getProperty("user.dir"));
    SystemSettingPath.initRootSettingPath();
    GradedOut.showNORMALMessage("$SET_PATH="+SystemSettingPath.RootSettingPath);

    BasicParamHolder paramHolder = new BasicParamHolder();

    CMDLineProblemSettings problemSettings = paramHolder.getProblemSettings();
    problemSettings.loadContent(args[0]);

    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Load initializer for problem type $PROBTYPE: "+ problemSettings.getKey());
    AbsMAOSInitializer maosInitializer = loadProblemType(problemSettings.getKey());
    
    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Load default project I/O (in \""+SystemSettingPath.DefaultPrjIOFile+"\") and common setting parameters from files in $SET_PATH");
    paramHolder.loadDefaultFiles(problemSettings.getKey());
    GradedOut.showNORMALMessage("$TASKPATH="+paramHolder.prjIOPath.getProjectPath());

    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Load problem instance \"" +args[0]+"\"");
    //initialize the problem task
    AbsProblemData problemData = maosInitializer.initProblemData(problemSettings, paramHolder.prjIOPath);

    GradedOut.showNORMALMessage("Type="+problemSettings.getKey()+" description=\""+problemData.getDescription()+"\"");
    String problemInfo = "Instance: "+problemData.getSummary();
    GradedOut.showNORMALMessage(problemInfo);
    String initInfo = problemInfo+BasicTag.RETURN_TAG;

    //initialize internal representation (IR): primary landscape and auxiliary knowledge
//    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Initialize internal representation (landscape)");
    AbsLandscape virtualLandscape = maosInitializer.initLandscape(problemData);

    if (args.length>1) {
      GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Parse common command-line parameters: [NAME=VALUE]");
      paramHolder.parseSwarmSettings(ObjectMatrix.removeElementAt(args, 0));
    }

    GradedOut.showNORMALMessage(MessageTags.MSGTAG_PLAIN+"Common Setting information in the command line & \""+SystemSettingPath.DefaultSwarmFile+"\"");

    //show input (including default) parameters
    SwarmSettings swarmSettings = paramHolder.getSwarmSettings();
    String swarmInfo = swarmSettings.getSummary();
    GradedOut.showNORMALMessage(swarmInfo);
    initInfo += swarmInfo;

    // *** for the swarm topology
    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Initialize the topology information (TOPO) in \""+SystemSettingPath.DefaultTopologyFile+"\"");
    TopologyHandler topologyHandler = new TopologyHandler();
    AbsTopology topologyEngine = topologyHandler.initTopology(swarmSettings.topology);
    String initTopologyInfo = "Topology: " + topologyEngine.getSummary();
    GradedOut.showNORMALMessage(initTopologyInfo);

    // *** for the swarm behaviors
    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Initialize the components (R_INI, R_G, and R_T) in the behavioral toolbox \""+swarmSettings.solver+"\"");
    BehaviorBoxHandler behaviorHolder = new BehaviorBoxHandler(problemSettings.getKey(), virtualLandscape);
    behaviorHolder.parseBehaviorScript(swarmSettings.solver);
//    SystemSettingPath.submitUsedFile("Solver", behaviorFileName);
//    GradedOut.showNORMALMessage(MessageTags.MSGTAG_PLAIN+"Information for real behaviors in the solver toolbox \""+swarmSettings.solver+"\"");
    String initBehaviorInfo = behaviorHolder.getBehaviorsString();
//    GradedOut.showNORMALMessage(initBehaviorInfo);

    // *** initialize the multi-agent optimization framework
    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Initialize the multiagent optimization framework (MAOS)");
    MAFramework maSolver = new MAFramework(virtualLandscape);
    maSolver.initSwarmShell(swarmSettings.N, swarmSettings.Nact);
    maSolver.initInteractionMode(topologyEngine);
    maSolver.initBehaviors(behaviorHolder.absConstructor, behaviorHolder.absStateUpdater, behaviorHolder.absGenerator);
    maSolver.initTerminationCondition(swarmSettings.T, swarmSettings.Tcon);

    //Retrieve history solution information
    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Retrieve history solution information in $SOL_PATH");
    HistoricalStateHandler historicalStateHandler = new HistoricalStateHandler(virtualLandscape, maosInitializer.getSolutionIOHandler(), paramHolder.prjIOPath);
    historicalStateHandler.setLowerBound(maosInitializer.getLowerBound());
    historicalStateHandler.setOptimalBound(swarmSettings.opt);
    GradedOut.showNORMALMessage(historicalStateHandler.printKnownSolutionInfo());
    
    //For output result in runs
    ResultOutputHandler resultOutputHandler = new ResultOutputHandler();
    String outfileName = resultOutputHandler.setResultFile(paramHolder.getResultFileDir(problemSettings.getKey()), paramHolder.getResultFileName());
    SystemSettingPath.submitUsedFile("$RES_FILE", outfileName);

    CycleResult cycleResult = maosInitializer.getCycleResult();
    cycleResult.setInfo(virtualLandscape);

    OutputIntervalChecker normalScreenOutputHandler = new OutputIntervalChecker();
    normalScreenOutputHandler.setOutputInterval(swarmSettings.Tout);
    
    //initialize the termination condition
    
    //initialize CycleInfoManager for outputting to screen and result file
    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Initialize CycleInfoManager for outputing screen information, saving results, and specifying termination conditions");
    CycleInfoManager cycleChecker = new CycleInfoManager(virtualLandscape, cycleResult, historicalStateHandler, normalScreenOutputHandler, resultOutputHandler, maSolver.getTerminateCycleCheckEngine());
    
    //initialize cycle analyzer
//    AbsCycleAnalysizer cycleAnalysizer = maosInitializer.getCycleAnalysizer();
//    if (cycleAnalysizer!=null) cycleAnalysizer.setInfo(virtualLandscape);
//    cycleChecker.setCycleAnalysizer(cycleAnalysizer);

    //show involved input/output files and paths
    GradedOut.showNORMALMessage(MessageTags.MSGTAG_PLAIN+"Involved input/output files and paths ...");
    GradedOut.showNORMALMessage(SystemSettingPath.getAllUsedFilesString());

    //show the preprocessing time
    GlobalTools.CPUTimeCostCounter.setEnd("initTime");
    GradedOut.showIMPORTANTMessage(MessageTags.MSGTAG_PLAIN+"Total preprocessing time: " + GlobalTools.CPUTimeCostCounter.getTotalTimeInSeconds("initTime") + "(s)");
    GlobalTools.CPUTimeCostCounter.removeCounter("initTime");

    //write head information to result file
    resultOutputHandler.writeCommentString("[Initialization Information]"+BasicTag.RETURN_TAG+initInfo+BasicTag.RETURN_TAG+BasicTag.RETURN_TAG+initTopologyInfo+BasicTag.RETURN_TAG+initBehaviorInfo+BasicTag.RETURN_TAG);

    //run MAOS
    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_SECT+"[Runtime Information]");
    resultOutputHandler.writeCommentString(BasicTag.RETURN_TAG+"[Runtime Information]");
    
    runMAOS(maSolver, cycleChecker, swarmSettings.DUP_TIMES);

    //output final statistics information
    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_SECT+"[Statistics Information]"+BasicTag.RETURN_TAG);
    String finalStats = cycleChecker.finalStats();
    GradedOut.showNORMALMessage(finalStats);
    resultOutputHandler.writeCommentString(BasicTag.RETURN_TAG+"[Statistics Information]"+BasicTag.RETURN_TAG+finalStats);

//    GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_SECT+"The system was terminated successfully.");
//    GlobalTools.CPUTimeCostCounter.printSelf();
  }

  private void runMAOS(IExecCyclesEngine maSolver, CycleInfoManager cycleChecker, int DUP_TIMES) throws Exception {
    cycleChecker.initRun();
    for (int iRun=1; iRun<=DUP_TIMES; iRun++) {
      if (DUP_TIMES>1) GradedOut.showNORMALMessage(MessageTags.MSGTAG_ACTION_NORM+"Trial No. "+ iRun);
      //start solving
      cycleChecker.setStart();
      maSolver.execOneRun(cycleChecker);
      cycleChecker.setEnd();
    }
  }

  private AbsMAOSInitializer loadProblemType(String problemType) throws Exception {
    String initializerName = SystemSettingPath.getInitializerName(problemType);
    try {
      Class cls = Class.forName(initializerName);
      return (AbsMAOSInitializer)cls.newInstance();
    } catch (Exception e) {
      throw new Exception("Problem type \""+problemType+"\" specified by the first parameter could not be found: "+e.getMessage());
    }
  }

}
