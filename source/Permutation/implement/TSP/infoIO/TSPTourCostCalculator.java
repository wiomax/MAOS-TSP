/*   
 * @Description: 
 * 
 * @Author         Create/Modi     Note
 * Xiao-Feng Xie   Mar 03, 2014		 http://www.cs.cmu.edu/~xfxie
 */

package implement.TSP.infoIO;

import static smos.ext.util.option.BasicOptionMethods.getCmdStr;
import static smos.share.util.log.SimpleLoggingHandler.logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import Global.system.io.GlobalFile;
import implement.TSP.represent.RealProblemData;
import smos.ext.util.option.BasicOptionMethods;
import smos.ext.util.option.IUseOptionsEngine;
import smos.share.util.log.GradeText;

public class TSPTourCostCalculator implements IUseOptionsEngine {
	public static void main(String[] args) throws Exception {
		TSPTourCostCalculator mainExecutor = new TSPTourCostCalculator();
		CommandLine cmdLine = BasicOptionMethods.initCmdLine(args, mainExecutor);
		if (cmdLine == null) return;
	 	GradeText.LogLevel = BasicOptionMethods.getCmdInt(GradeText.LogLevel, "l", cmdLine);

		try {
	    TSPSolutionIOHandler solHandler = new TSPSolutionIOHandler();
	    String solFile = getCmdStr("s", cmdLine);
	    String contentS = GlobalFile.getStringFromFile(solFile);
	    int[] permutations = solHandler.naiveReadSolution(contentS);
	    for (int i=1; i<permutations.length; i++) {
	    	permutations[i] --;
	    }
	    
	    TSPInstanceLoader insHandler = new TSPInstanceLoader();
	    String insFile = getCmdStr("i", cmdLine);
	    String contentI = GlobalFile.getStringFromFile(insFile);
	    RealProblemData problem = (RealProblemData)insHandler.readProblem(contentI);
	    
	    int[][] costMatrix = problem.getCostMatrix();
	    boolean notClosed = cmdLine.hasOption("n");
	    int totalLength = notClosed?0:costMatrix[permutations[permutations.length-1]][permutations[0]];
	    for (int i=1; i<permutations.length; i++) {
	      totalLength += costMatrix[permutations[i-1]][permutations[i]];
	    }
		  logger.info("Cost:" +totalLength);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void getOptions(Options options) {
		options.addOption("l", true, "Logger Level");
		options.addOption("i", true, "TSP Instance");
		options.addOption("s", true, "TSP Solution");
		options.addOption("n", false, "Not Closed");
	}
}
