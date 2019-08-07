/**
 * Description: For managing the results in each cycle
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 10, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel;

import java.util.*;
import Global.define.*;
import Global.math.ArrayMath;
import Global.methods.GlobalString;
import Global.methods.TypeConverter;
import Global.system.*;
import Global.util.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.represent.information.*;
import maosKernel.behavior.terminate.*;
import maosKernel.infoIO.*;
import maosKernel.infoIO.runtime.*;
//import maosKernel.infoIO.analysis.*;
import maosKernel.infoIO.historical.*;
import maosKernel.infoIO.screen.*;

public final class CycleInfoManager implements ICycleInfoEngine, ITrialInitEngine {
  private final String RUN_TIME_FLAG = "runTime";

  //for evaluating runtime state quality
  private IQualityEvaluateEngine evalEngine;

  private CycleResult currCycleResult;   // current CycleResult
  private CycleResult bestCycleResult;   //the best CycleResult

  //OUTPUT INFORMATION
  private OutputIntervalChecker normalScreenOutputHandler;
  private IHistoricalHandleEngine historicalStateHandler;
  private ResultOutputHandler resultOutputHandler;
  
  private Vector<CycleResult> allBestCycleResults = new Vector<CycleResult>();

  //ANALYZE INFORMATION
//  private DiversityAnalysizer cycleAnalysizer = new DiversityAnalysizer();
//  private LocalDiversityAnalysizer localAnalysizer = new LocalDiversityAnalysizer();
//  private ClusteringAnalysizer clusteringAnalysizer = new ClusteringAnalysizer();
//  private GiniIndexAnalysizer giniIndexAnalysizer = new GiniIndexAnalysizer();

  //termination condition
  private ITerminateCycleCheckEngine overallCutoffCriterion;
  
  //monitor knowledge
  InteractionCenter monitorInfo;
  
  public CycleInfoManager(AbsLandscape landscape, CycleResult cycleResult, IHistoricalHandleEngine historicalStateHandler, OutputIntervalChecker normalScreenOutputHandler, ResultOutputHandler resultOutputHandler, ITerminateCycleCheckEngine overallCutoffCriterion) {
    this.evalEngine = landscape;
    this.currCycleResult = cycleResult;
    bestCycleResult = (CycleResult)cycleResult.clone();

    this.historicalStateHandler = historicalStateHandler;
    this.resultOutputHandler = resultOutputHandler;
    this.normalScreenOutputHandler = normalScreenOutputHandler;
    this.overallCutoffCriterion = overallCutoffCriterion;
    
//    cycleAnalysizer.setInfo(landscape);
//    localAnalysizer.setInfo(landscape);
//    clusteringAnalysizer.setInfo(landscape);
//    giniIndexAnalysizer.setInfo(landscape);
//    currCycleResult.submitData(cycleAnalysizer.getName(), "N/A");
//    currCycleResult.submitData(localAnalysizer.getName(), "N/A");
//    currCycleResult.submitData(clusteringAnalysizer.getName(), "N/A");
//    currCycleResult.submitData(giniIndexAnalysizer.getName(), "N/A");
  }
  
  public void setStart() {
    GlobalTools.CPUTimeCostCounter.reset(RUN_TIME_FLAG);
    GlobalTools.CPUTimeCostCounter.setStart(RUN_TIME_FLAG);
    GradedOut.showNORMALMessage(GlobalString.serinize(currCycleResult.getCycledAllNames(), BasicTag.NULL_SEPERATE_TAG));
  }

  public void setEnd() {
    GlobalTools.CPUTimeCostCounter.setEnd(RUN_TIME_FLAG);
  }

  public CycleResult getCurrentCycleResult() {
    return currCycleResult;
  }

  public void initRun() throws Exception {
    resultOutputHandler.writeResultHeadInfo(currCycleResult);
  }

  public void initTrial() {
    currCycleResult.initTrial();
    bestCycleResult.initTrial();
  }
  
  public void setMonitorInfo(InteractionCenter monitorInfo) {
    this.monitorInfo = monitorInfo;
//    localAnalysizer.setTopology(monitorInfo.getTopology());
//    clusteringAnalysizer.setTopology(monitorInfo.getTopology());
//    giniIndexAnalysizer.setTopology(monitorInfo.getTopology());
  }
  
  public boolean cycleCheck(int cycleNumber, EncodedState cycleBestState) throws Exception {
    GlobalTools.CPUTimeCostCounter.setEnd(RUN_TIME_FLAG);
    boolean isImproved = !EncodedStateHandler.evaluate(evalEngine, currCycleResult.BState, cycleBestState);
    //fill in the current results
    currCycleResult.NCycle = cycleNumber;
    currCycleResult.Time = GlobalTools.CPUTimeCostCounter.getTotalTimeInSeconds(RUN_TIME_FLAG);

//    currCycleResult.submitData(cycleAnalysizer.getName(), cycleAnalysizer.analysis(monitorInfo.getSocialInfo()) +"");
//    
//    if (localAnalysizer.isDecentralized())  {
//      currCycleResult.submitData(localAnalysizer.getName(), localAnalysizer.analysis(monitorInfo.getSocialInfo()) +"");
//      currCycleResult.submitData(clusteringAnalysizer.getName(), clusteringAnalysizer.analysis(monitorInfo.getSocialInfo()) +"");
//      currCycleResult.submitData(giniIndexAnalysizer.getName(), giniIndexAnalysizer.analysis(monitorInfo.getSocialInfo()) +"");
//    }

    if (isImproved) {
      currCycleResult.BState.importEncodeState(cycleBestState);
      bestCycleResult.importData(currCycleResult);
    }

    //check termination conditions
    boolean isTerminated = overallCutoffCriterion.isCycleTerminated(cycleNumber, cycleBestState) || historicalStateHandler.isOptimalState(cycleBestState);

    boolean hasRecordState = historicalStateHandler.updateHistoricalState(cycleBestState);
    boolean isNormalOutput = normalScreenOutputHandler.isCycleNormalOutput(cycleNumber);

    if (isTerminated||isNormalOutput||hasRecordState) { //print screen information
      String msg = GlobalString.serinize(currCycleResult.getCycledAllValues(),  BasicTag.NULL_SEPERATE_TAG);
      GradedOut.showNORMALMessage(msg);
    }

    //output runtime results
    if (isNormalOutput) {
      resultOutputHandler.writeRunInfo(currCycleResult);
    }

    if (isTerminated) {
      //output final information
      resultOutputHandler.writeLastInfo(bestCycleResult);
      historicalStateHandler.outputHistoryState();
      GradedOut.showIMPORTANTMessage(MessageTags.MSGTAG_PLAIN+"Trial summary: "+bestCycleResult.getCycleInfo());
      allBestCycleResults.add((CycleResult)bestCycleResult.clone());
      return true;
    }
    GlobalTools.CPUTimeCostCounter.setStart(RUN_TIME_FLAG);
    return false;
  }

  public String finalStats() throws Exception {
    int trialSize = allBestCycleResults.size();
    if (trialSize==0) return "@ No Information Available";
    
    Vector<String> allNames = ((CycleResult)allBestCycleResults.get(0)).getCycledAllNames();
    int nameSize = allNames.size();
    double[][] data = new double[nameSize][trialSize];
    for (int i=0; i<trialSize; i++) {
      Vector<String> allValues = ((CycleResult)allBestCycleResults.get(i)).getCycledAllValues();
      for (int j=0; j<nameSize; j++) {
        data[j][i] = TypeConverter.toDouble(allValues.elementAt(j));
      }
    }
    
    String finalStats = "";
    finalStats += "@"+BasicTag.NULL_SEPERATE_TAG+"minV"+BasicTag.NULL_SEPERATE_TAG+"maxV"+BasicTag.NULL_SEPERATE_TAG+"mean"+BasicTag.NULL_SEPERATE_TAG+"dev"+BasicTag.RETURN_TAG;
    GlobalString.serinize(allNames, BasicTag.NULL_SEPERATE_TAG);
    for (int i=0; i<nameSize; i++) {
      double minV = ArrayMath.getExtermal(data[i], false);
      double maxV = ArrayMath.getExtermal(data[i], true);
      double mean = ArrayMath.meanValue(data[i]);
      double devV = ArrayMath.getStdDev(data[i], mean);
      finalStats += allNames.get(i)+BasicTag.NULL_SEPERATE_TAG+minV+BasicTag.NULL_SEPERATE_TAG+maxV+BasicTag.NULL_SEPERATE_TAG+mean+BasicTag.NULL_SEPERATE_TAG+devV+BasicTag.RETURN_TAG;
    }
    return finalStats;
  }
}
