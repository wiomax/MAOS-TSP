/**
 * Description: provide the basic swarm settings
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 02, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.infoIO.setting;

import Global.define.*;
import Global.methods.*;
import Global.basic.nodes.*;
import Global.basic.nodes.loader.*;
import Global.basic.nodes.utilities.*;
import maosKernel.infoIO.parser.*;
import maosKernel.represent.landscape.quality.ICalcGlobalCostEngine;

public class SwarmSettings extends UtilSetNode {
  /**********************algorithm************************/
  //swarm parameters
  public int N = 300;     //importance: high level
  public int Nact = -1;   //importance: high level

  //termination condition parameters
  public int T = 500;     //importance: high level
  public int Tcon = 50;   //importance: medium level

  //topology
  public String topology = "FullConnect";    //importance: high level
  //solver
  public String solver = SystemSettingPath.DefaultSolverName;

  /*********************misc parameters************************/
  public int DUP_TIMES = 1;

  public int Tout = 5;

  public double opt = ICalcGlobalCostEngine.WOSRTVALUE;

  public SwarmSettings() {
    this.setKey("SwarmSettings");
  }

  public void initUtilities() {
    super.initUtilities();

    initUtility(new IntegerUtility("N", N));
    initUtility(new IntegerUtility("Nact", Nact));

    initUtility(new BasicUtility("Topology", topology));

    initUtility(new IntegerUtility("T", T));
    initUtility(new IntegerUtility("Tcon", Tcon));

    initUtility(new IntegerUtility("DUP_TIMES", DUP_TIMES));

    initUtility(new IntegerUtility("Tout", Tout));

    initUtility(new BasicUtility("solver", solver));

    initUtility(new DoubleUtility("opt", opt));
}

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    N = TypeConverter.toInteger(getValue("N"));
    Nact = TypeConverter.toInteger(getValue("Nact"));
    topology = TypeConverter.toString(getValue("Topology"));

    T = TypeConverter.toInteger(getValue("T"));
    Tcon = TypeConverter.toInteger(getValue("Tcon"));

    DUP_TIMES = TypeConverter.toInteger(getValue("DUP_TIMES"));
    Tout = TypeConverter.toInteger(getValue("Tout"));

    solver = TypeConverter.toString(getValue("solver"));

    opt = TypeConverter.toDouble(getValue("opt"));
  }

  protected String getFileNodesString(String[] parameters) {
    return getNodesString(parameters, "=", "_");
  }

  protected String getSummaryNodesString(String[] parameters) {
    return getNodesString(parameters, "=", " ");
  }

  public String getBasicResultDir() {
    String[] swarmParameters = {"N", "Nact", "Topology", "T", "Tcon"};
    AbsUtilSetNodeLoader utilSetNodeLoader = new ModuleUtilSetNodeLoader();
    UtilSetNode utilSetNode = new UtilSetNode();
    utilSetNodeLoader.loadUtilSetNode(utilSetNode, solver);
    utilSetNodeLoader = new BracketUtilSetNodeLoader();
    return getFileNodesString(swarmParameters)+"_solver="+utilSetNodeLoader.saveUtilSetNode(utilSetNode);
  }

  public String getSummary() {
    String sumStr = "";
    sumStr += "Swarm Params: "+getSummaryNodesString(new String[] {"N", "Nact", "Topology", "Solver"}) + BasicTag.RETURN_TAG;
    sumStr += "Terminate Condition: "+getSummaryNodesString(new String[] {"T", "Tcon"}) + BasicTag.RETURN_TAG;
    sumStr += "Misc Params: "+getSummaryNodesString(new String[] {"DUP_TIMES", "Tout"});
    return sumStr;
  }
}

