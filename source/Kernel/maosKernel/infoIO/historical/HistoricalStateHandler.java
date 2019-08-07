/**
 * Description: provide the capability for handling historical best state
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 19, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.historical;

import java.io.*;

import Global.system.*;
import Global.system.io.*;
import maosKernel.represent.landscape.*;
import maosKernel.memory.*;
import maosKernel.infoIO.setting.*;
import maosKernel.represent.landscape.quality.*;

public class HistoricalStateHandler implements IDefineLowerBoundEngine, IHistoricalHandleEngine {
  private boolean isOpt = false;
  private EncodedState historyState;
  private double lowBoundEncodedData = ICalcGlobalCostEngine.WOSRTVALUE;
  private boolean isBrokenRecord = false;
  /**********************************************/
  private ISolutionIOEngine solutionIOEngine;
  /**********************************************/
  private AbsLandscape virtualLandscape;
  private AbsSolutionIOHandler solutionIOHandler;

  public HistoricalStateHandler(AbsLandscape virtualLandscape, AbsSolutionIOHandler solutionIOHandler, ISolutionIOEngine solutionIOEngine) {
    historyState = new EncodedState(virtualLandscape);
    this.solutionIOHandler = solutionIOHandler;
    this.virtualLandscape = virtualLandscape;
    this.solutionIOEngine = solutionIOEngine;
    isOpt = initHistoryState(historyState, virtualLandscape);
  }

  private String getOptFile(String problemName) throws Exception {
    return solutionIOEngine.getOptimalSolutionFileName(virtualLandscape.getKey(), problemName, false);
  }

  public void setOptimalBound(double lowBoundEncodedData) {
  	if (isOpt || lowBoundEncodedData==ICalcGlobalCostEngine.WOSRTVALUE) return;
  	historyState.setEncodeInfo(lowBoundEncodedData);
  	this.isOpt = true;
  }

  public void setLowerBound(double lowBoundEncodedData) {
    this.lowBoundEncodedData = lowBoundEncodedData;
  }

  public boolean updateHistoricalState(EncodedState state) {
    if (isNewHistoricalState(state)) {
      historyState.importEncodeState(state);
      isBrokenRecord = true;
      this.isOpt = false;
      return true;
    }
    return false;
  }

  public EncodedState getHistoricalState() {
    return historyState;
  }

  public boolean isLowerBoundState(EncodedState state) {
    return (lowBoundEncodedData!=ICalcGlobalCostEngine.WOSRTVALUE&&virtualLandscape.evaluate(state.getEncodeInfo(), lowBoundEncodedData));
  }

  public boolean isFullHistoricalState(EncodedState state) {
    return isOptimalState(state)||isNewHistoricalState(state);
  }

  public boolean isNewHistoricalState(EncodedState state) {
    return (!EncodedStateHandler.evaluate(virtualLandscape, historyState, state));
  }

  public boolean isOptimalState(EncodedState state) {
    return (isOpt && EncodedStateHandler.evaluate(virtualLandscape, state, historyState)) || isLowerBoundState(state);
  }

  public boolean outputHistoryState() throws Exception {
    if (isBrokenRecord) {
      String fileName;
      String instanceName = virtualLandscape.getName();
      String problemType = virtualLandscape.getKey();
      if (!isLowerBoundState(historyState)) {
        fileName = solutionIOEngine.getNormalSolutionFileName(problemType, instanceName, EncodedStateHandler.writeEncodedInfo(virtualLandscape, historyState.getEncodeInfo()));
      } else {
        fileName = solutionIOEngine.getOptimalSolutionFileName(problemType, instanceName, true);
      }
      try {
        GlobalFile.saveStringToFile(solutionIOHandler.writeSolution(virtualLandscape, historyState), fileName);
        GradedOut.showNORMALMessage("@ Output new record solution to file: "+ fileName);
      } catch (Exception e) {
        GradedOut.showNORMALMessage(e.getMessage());
      }
      isBrokenRecord = false;
      return true;
    }
    return false;
  }

  private boolean initHistoryState(EncodedState historyState, AbsLandscape virtualLandscape) {
    historyState.removeEncodeInfo();
    final String problemName = virtualLandscape.getName();
    String fileName = null, content;
    boolean isOpt = false;
    try {
      fileName = getOptFile(problemName);
      content = GlobalFile.getStringFromFile(fileName);
      solutionIOHandler.readSolution(historyState, content);
      EncodedStateHandler.encodeGlobalCost_AsNotEncoded(virtualLandscape, historyState);
      isOpt = true;
    } catch(Exception e) {
      File file = new File(solutionIOEngine.getSolutionPath(virtualLandscape.getKey()));
      File[] files = file.listFiles(solutionIOEngine.getSolutionFilter(problemName));
      if (files==null) return false;
      EncodedState tempState = new EncodedState(virtualLandscape);
      for (int i=0; i<files.length; i++) {
        try {
          content = GlobalFile.getStringFromFile(files[i].getAbsolutePath());
          tempState.removeEncodeInfo();
          solutionIOHandler.readSolution(tempState, content);
          EncodedStateHandler.encodeGlobalCost_AsNotEncoded(virtualLandscape, tempState);
          if (EncodedStateHandler.evaluate(virtualLandscape, tempState, historyState)) {
            historyState.importEncodeState(tempState);
            fileName = files[i].getAbsolutePath();
          }
        } catch (Exception ex) {}
      }
    }
    SystemSettingPath.submitUsedFile("$SOL_FILE", fileName);
    return isOpt;
  }

  //private known solution info
  public String printKnownSolutionInfo() {
    String msg = "Known solution cost: ";
    if (!historyState.isEncoded()) {
      msg += "none";
    }
    else {
      if (isLowerBoundState(this.historyState)) {
        isOpt = true;
      }
      msg += EncodedStateHandler.writeEncodedInfo(virtualLandscape, historyState.getEncodeInfo());
      if (isOpt) {
        msg += "(Optimum)";
        return msg;
      }
    }
    if (this.lowBoundEncodedData!=ICalcGlobalCostEngine.WOSRTVALUE && !isOpt) {
      msg += ", lower bound is "+lowBoundEncodedData;
    }
    return msg;
  }
}

