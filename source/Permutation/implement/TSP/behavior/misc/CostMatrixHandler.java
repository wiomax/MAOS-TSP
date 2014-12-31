/**
 * Description: Return a Cost Matrix
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 12, 2006
 * Xiaofeng Xie    Mar 21, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.TSP.behavior.misc;

import Global.basic.*;
import Global.define.*;
import Global.methods.*;
import Global.basic.data.matrix.*;
import Global.system.io.*;

import maosKernel.represent.landscape.*;
import maosKernel.infoIO.setting.*;
import maosKernel.behavior.*;

import implement.TSP.represent.*;
import implement.TSP.knowledge.*;
import implement.TSP.infoIO.*;
import Global.basic.nodes.utilities.*;

public final class CostMatrixHandler extends AbsBehavior implements IGetCostMatrixEngine {
  private boolean isAlpha = false;
  private boolean isPI = false;

  ISquareIMatrixEngine costMatrix;
  BasicAttrib basicAttrib;

  public CostMatrixHandler() {}

  protected void setRootInfo(AbsLandscape virtualLandscape) {
    basicAttrib = virtualLandscape;
    costMatrix = new FullSquareIMatrix(((InternalRepresentation)virtualLandscape).getIGetProblemDataEngine().getCostMatrix());
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BooleanUtility("isAlpha", isAlpha));
    initUtility(new BooleanUtility("isPI", isPI));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    isAlpha = TypeConverter.toBoolean(getValue("isAlpha"));
    isPI = TypeConverter.toBoolean(getValue("isPI"));
  }

  public ISquareIMatrixEngine getCostMatrix() {
    if (isAlpha||isPI) {
      return this.getCostMatrix(costMatrix);
    } else {
      return costMatrix;
    }
  }

  //1. init candidate set: if PI_FILES exists, then in alpha-nearness measure, else in nearest neighbor
  //2. estimate Held-Karp bound
  private ISquareIMatrixEngine getCostMatrix(ISquareIMatrixEngine costMatrix) {
    PiCostMatrix piCostMatrix = new PiCostMatrix(costMatrix);
    if (isPI) {
      String piFileName = ProjectIOPath.getSpecProjectPath(basicAttrib.getKey())+BasicTag.FILE_SEP_TAG+"PI_FILES"+BasicTag.FILE_SEP_TAG+basicAttrib.getName()+".pi";
      System.out.print("Penalty values: ");
      try {
        String content = GlobalFile.getStringFromFile(piFileName);
        piCostMatrix.setPI(LKH_PI_Reader.readResult(content), LKH_PI_Reader.precision);
        System.out.println("loaded from file");
        SystemSettingPath.submitUsedFile("$PIV_FILE", piFileName);
      } catch (Exception e) {
        System.out.println("none");
      }
    }

    if (isAlpha) {
      PenaltyHandler piHandler = new PenaltyHandler(piCostMatrix);
      piHandler.creatMinimum1Tree();
      return piHandler.getAlphaMatrix();
    } else {
      return piCostMatrix.getCostMatrix();
    }

  }
}
