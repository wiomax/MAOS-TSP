/**
 * Description: The connection topology: each node has fixed number of connected nodes,
 *   Initially it is a G(n,m) determined by the class EdgeRatioTopology
 *   Then at each dynInterval cycles, dynEdges edges are changed dynamically.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 12, 2008 
 *
 * @version M01.00.02
 */

package maosKernel.behavior.topology;

import java.util.*;

import Global.basic.nodes.utilities.*;
import Global.methods.*;
import Global.basic.data.collection.*;
import maosKernel.memory.*;
import maosKernel.represent.information.*;
import maosKernel.represent.landscape.*;
import maosKernel.behavior.pick.*;

public class TourDDynEdgeRatioTopology extends EdgeRatioTopology implements ICycleInitEngine, ISetStateSetEngine {
  private int dynEdges = 1;
  private int dynInterval = 1;
  private AbsStatePicker statePicker = new TourDistanceStatePicker();
  private boolean isLarger = true;
  private int tao = 1;

  //temp value
  protected DualIAlienArray[] usedIDArrays;
  protected DualIAlienArray[] unusedIDArrays;
  private int dynCycle = 1;
  private boolean[] idFlags;
  private IArray toUseIDArrays;
  private IArray toUnuseIDArrays;
  
  private IGetEachEncodedStateEngine library = null;

  public TourDDynEdgeRatioTopology() {}

  public void initTrial() {
    super.initTrial();
    dynCycle = 1;
  }

  protected void setRootInfo(AbsLandscape landscape) {
    super.setRootInfo(landscape);
    statePicker.setInfo(landscape);
  }
  
  protected void internalBasicInit(int nodeNumber) {
    super.internalBasicInit(nodeNumber);
    usedIDArrays = new DualIAlienArray[nodeNumber];
    unusedIDArrays = new DualIAlienArray[nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      usedIDArrays[i] = new DualIAlienArray(nodeNumber);
      unusedIDArrays[i] = new DualIAlienArray(nodeNumber);
    }
    idFlags = new boolean[nodeNumber];
    toUseIDArrays = new IArray(nodeNumber);
    toUnuseIDArrays = new IArray(nodeNumber);
  }
  
  public void setInfo(IGetEachEncodedStateEngine library) {
    this.library = library;
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("dynEdges", dynEdges));
    initUtility(new IntegerUtility("dynInterval", dynInterval));
    initUtility(new BooleanUtility("isLarger", isLarger));
    initUtility(new IntegerUtility("tao", tao));
    statePicker.initUtilities();
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    dynEdges = TypeConverter.toInteger(getValue("dynEdges"));
    dynInterval = TypeConverter.toInteger(getValue("dynInterval"));
    isLarger = TypeConverter.toBoolean(getValue("isLarger"));
    tao = TypeConverter.toInteger(getValue("tao"));
    statePicker.setValue("isLarger", new Boolean(isLarger));
    statePicker.setValue("tao", new Integer(tao));
    statePicker.shortcutInit();
  }
  
  private void dynInitTopology(int nodeID, int dynEdges) {
    toUseIDArrays.clear();
    toUnuseIDArrays.clear();
    int realDynEdges = Math.min(dynEdges, usedIDArrays[nodeID].getSize());
    realDynEdges = Math.min(realDynEdges, unusedIDArrays[nodeID].getSize());
    statePicker.setBaseState(library.getSelectedPoint(nodeID));
    for (int i=0; i<realDynEdges; i++) {
      int toUnuseID = usedIDArrays[nodeID].getElementAt(selToUnuseID(usedIDArrays[nodeID]));
      usedIDArrays[nodeID].removeElement(toUnuseID);
      toUnuseIDArrays.addElement(toUnuseID);
      int toUseID = unusedIDArrays[nodeID].getElementAt(selToUseID(unusedIDArrays[nodeID]));
      unusedIDArrays[nodeID].removeElement(toUseID);
      toUseIDArrays.addElement(toUseID);
    }
    
    for (int i=0; i<realDynEdges; i++) {
      usedIDArrays[nodeID].addElement(toUseIDArrays.getElementAt(i));
      unusedIDArrays[nodeID].addElement(toUnuseIDArrays.getElementAt(i));
    }
  }
 
  protected int selToUnuseID(final DualIAlienArray usedIDArray) {
    IGetEachEncodedStateEngine referEngine = new IGetEachEncodedStateEngine() {
      public int getLibSize() {
        return usedIDArray.getSize();
      }
      public EncodedState getSelectedPoint(int index) {
        return library.getSelectedPoint(usedIDArray.getElementAt(index));
      }
    };
    statePicker.reverseType();
    int selID = statePicker.pick(referEngine);
    statePicker.reverseType();
    return selID;
  }
  
  protected int selToUseID(final DualIAlienArray unusedIDArray) {
    IGetEachEncodedStateEngine referEngine = new IGetEachEncodedStateEngine() {
      public int getLibSize() {
        return unusedIDArray.getSize();
      }
      public EncodedState getSelectedPoint(int index) {
        return library.getSelectedPoint(unusedIDArray.getElementAt(index));
      }
    };
    return statePicker.pick(referEngine);
  }
  
  public void initCycle() {
    if (dynCycle>0 && dynCycle<dynInterval) {
      dynCycle ++;
      return;
    } else {
      dynCycle = 1;
      for (int i=0; i<this.getNodeNumber(); i++) {
        dynInitTopology(i, dynEdges);
      }
    }
  }
  
  public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID) {
    return usedIDArrays[nodeID];
  }

  protected void innerInitTopology() {
    super.innerInitTopology();
    
    for (int i=0; i<getNodeNumber(); i++) {
      usedIDArrays[i].clear();
      unusedIDArrays[i].clear();
      Arrays.fill(idFlags, false);
      IBasicICollectionEngine ids = super.getConnectedNodeIDsAt(i);
      for (int j=0; j<ids.getSize(); j++) {
        idFlags[ids.getElementAt(j)] = true;
      }
      
      for (int j=0; j<getNodeNumber(); j++) {
        if (idFlags[j]) {
          usedIDArrays[i].addElement(j);
        } else {
          unusedIDArrays[i].addElement(j);
        }
      }
    }
  }
}
