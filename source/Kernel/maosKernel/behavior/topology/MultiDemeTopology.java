/**
 * Description: The multi-deme topology
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 19, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.behavior.topology;

import java.util.*;
import Global.basic.data.collection.*;
import Global.methods.*;
import Global.basic.nodes.utilities.*;

public class MultiDemeTopology extends AbsCycleTopology {
  private int[] demeNArray = new int[1];
  private double chaosRatio = 0;

  private IArray[] demeArrays;
  private IArray fullArray;

  //temp values
  private DualIAlienArray tempArray;
  private int[] locationArray;

  public MultiDemeTopology() {}

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("demeNumber", demeNArray.length));
    initUtility(new DoubleUtility("chaosRatio", chaosRatio));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    demeNArray = new int[TypeConverter.toInteger(getValue("demeNumber"))];
    chaosRatio = TypeConverter.toDouble(getValue("chaosRatio"));
  }

  protected void internalBasicInit(int nodeNumber) {
    fullArray = new IArray(nodeNumber);
    for (int i=0; i<nodeNumber; i++) {
      fullArray.addElement(i);
    }

    locationArray = new int[nodeNumber];
    int demeNumber = demeNArray.length;
    demeArrays = new IArray[demeNumber];
    for (int i=0; i<demeNumber; i++) {
      demeArrays[i] = new IArray(nodeNumber);
    }

    demeNArray = new int[demeNumber];
    tempArray = new DualIAlienArray(nodeNumber);
  }

  public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID) {
    if (Math.random()<chaosRatio) {
      return fullArray;
    } else {
      return demeArrays[locationArray[nodeID]];
    }
  }

  private void allocateProximateNumbers(int[] resultArray, int N) {
    int baseN = N/resultArray.length;
    Arrays.fill(resultArray, baseN);
    int residualN = N-baseN*resultArray.length;
    if (residualN==0) return;
    int[] indices = RandomGenerator.randomDistinctSelection(resultArray.length, residualN);
    for (int i=0; i<residualN; i++) {
      resultArray[indices[i]] ++;
    }
  }

  protected void innerInitTopology() {
    int nodeNumber = getNodeNumber();
    allocateProximateNumbers(demeNArray, nodeNumber);
    for (int i=0; i<demeNArray.length; i++) {
      demeArrays[i].clear();
    }
    tempArray.clear();
    for (int i=0; i<getNodeNumber(); i++) {
      tempArray.addElement(i);
    }
    int selID = -1;
    for (int i=0; i<demeNArray.length; i++) {
      for (int j=0; j<demeNArray[i]; j++) {
        selID = BasicCollection.getRandomElement(tempArray);
        demeArrays[i].addElement(selID);
        locationArray[selID] = i;
        tempArray.removeElement(selID);
      }
    }
  }
}
