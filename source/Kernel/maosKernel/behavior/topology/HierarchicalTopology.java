/**
 * Description: The Hierarchical connection topology
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 17, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * 
 * @REF:
 * [1] S. Janson and M. Middendorf, "A hierarchical particle swarm optimizer 
 * and its adaptive variant," IEEE Transactions on Systems Man and Cybernetics
 * Part B-Cybernetics, vol. 35, pp. 1272-1282, 2005.
 */

package maosKernel.behavior.topology;

import Global.basic.data.collection.*;
import Global.basic.nodes.utilities.*;
import Global.methods.*;
import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.represent.knowledge.*;
import maosKernel.memory.*;

public class HierarchicalTopology extends AbsCycleTopology implements ISetLandscapeInfoEngine, ISetStateSetEngine {
  private int height = 2;

  private int degree = 0;

  // temp for regular-like Hierarchical Tree
  private DynTreeNode[] nodesArray;
  private int rootID = -1;
  private int[] orderIDs;

  // temp for output
  private IArray tempArray = new IArray(1);
  
  private IQualityEvaluateEngine qeEngine = null;
  private IGetEachEncodedStateEngine library = null;

  public HierarchicalTopology() {
  }
  
  public void setRootInfo(AbsLandscape landscape) {
    super.setRootInfo(landscape);
    this.qeEngine = landscape;
  }

  public void setInfo(IGetEachEncodedStateEngine library) {
    this.library = library;
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("height", height));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    height = TypeConverter.toInteger(getValue("height"));

  }

  protected void internalBasicInit(int nodeNumber) {
    nodesArray = new DynTreeNode[nodeNumber];
    degree = getMaxBranchDegree(nodeNumber, height);
    for (int i = 0; i < nodeNumber; i++) {
      nodesArray[i] = new DynTreeNode();
      nodesArray[i].childIDs = new DualIAlienArray(nodeNumber);
    }
    orderIDs = new int[nodeNumber];
  }

  private int getMaxBranchDegree(int nodeNumber, int height) {
    int startD = (int) Math.pow(nodeNumber, 1 / (double) height);
    for (int i = startD; i < nodeNumber; i++) {
      if ((Math.pow(i, height + 1) - 1) / (i - 1) >= nodeNumber) {
        return i;
      }
    }
    return -1;
  }

  public void initTrial() {
    super.initTrial();
    RandomGenerator.randomDistinctSelection(orderIDs);
    rootID = orderIDs[0];
    int nodeNumber = this.getNodeNumber();
    for (int i = 0; i < nodeNumber; i++) {
      nodesArray[i].clear();
    }
 
    initTreeNodes(0, 1);
  }

  private void initTreeNodes(int startID, int endID) {
    int nodeNumber = this.getNodeNumber();
    int csID = endID;
    for (int j = 0; j < degree; j++) {
      for (int i = startID; i < endID; i++) {
        nodesArray[orderIDs[i]].childIDs.addElement(orderIDs[csID]);
        nodesArray[orderIDs[csID]].parentID = orderIDs[i];
        csID++;
        if (csID >= nodeNumber)
          return;
      }
    }
    int childLevelSize = degree * (endID-startID);
    initTreeNodes(endID, endID+childLevelSize);
  }
  
  public IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID) {
    tempArray.clear();
    if (nodesArray[nodeID].parentID != -1) {
      tempArray.addElement(nodesArray[nodeID].parentID);
    } else { // is root node
      tempArray.addElement(nodeID);
    }
    return tempArray;
  }
  
  private void adjust(int parentID){
    IDynamicICollectionEngine childIDs = this.nodesArray[parentID].childIDs;
    int childSize = childIDs.getSize();
    if (childSize==0)return;
    
    int selChildID, bestChildID = childIDs.getElementAt(0);
    EncodedState selState, bestState = library.getSelectedPoint(bestChildID);
    
    for (int i=1; i<childSize; i++) {
      selChildID = childIDs.getElementAt(i);
      selState = library.getSelectedPoint(selChildID);
      if (qeEngine.evaluate(selState.getEncodeInfo(), bestState.getEncodeInfo())) {
        bestState = selState;
        bestChildID = selChildID;
      }
    }
    
     EncodedState parentState = library.getSelectedPoint(parentID);
    
    if (qeEngine.evaluate(bestState.getEncodeInfo(), parentState.getEncodeInfo())) {
      //swap their places within the hierarchy
      if (parentID==rootID) {
        rootID = bestChildID; 
      }
      else {
        //for the parent node of the parentID
        nodesArray[nodesArray[parentID].parentID].childIDs.removeElement(parentID);
        nodesArray[nodesArray[parentID].parentID].childIDs.addElement(bestChildID);
      }
      
      //for the children of the parentID
      for (int i=0; i<childSize; i++) {
        selChildID = childIDs.getElementAt(i);
        nodesArray[selChildID].parentID = bestChildID;
      }
      
      childIDs.removeElement(bestChildID);
      childIDs.addElement(parentID);
      
      DynTreeNode tempNode = nodesArray[parentID];
      nodesArray[parentID] = nodesArray[bestChildID];
      nodesArray[bestChildID] = tempNode;
      
    }
    
    for (int i=0; i<childSize; i++) {
      adjust(childIDs.getElementAt(i));
    }
  }

  public void innerInitTopology() {
    adjust(rootID);
  }
}
