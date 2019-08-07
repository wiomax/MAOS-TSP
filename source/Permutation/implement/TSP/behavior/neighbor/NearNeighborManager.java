/**
 * Description: provide the information for the nearest neighborhood 
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 7, 2005     Inside PheromoneHolder
 * Xiaofeng Xie    Apr 15, 2005    neighborhood manager for nearest
 * Xiaofeng Xie    May 22, 2005    general neighborhood manager
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 * Xiaofeng Xie    May 21, 2009    MAOS M01.00.02
 *
 * @version M01.00.02
 * @since M01.00.00
 *
 */

package implement.TSP.behavior.neighbor;

import Global.define.*;
import Global.basic.data.matrix.*;
import Global.basic.nodes.utilities.*;
import Global.methods.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.represent.landscape.*;
import implement.TSP.behavior.misc.*;
import implement.TSP.represent.IGetLocalCostEngine;

public class NearNeighborManager extends AbsNeighborManager {
  //parameters
  private int Knni = 20;   //importance: high level
  private int Knnm = GlobalValue.MAXINTEGER;
  private IGetCostMatrixEngine costMatrixEngine = new CostMatrixHandler();

  //internal
  protected int real_max_nn_nodes = 0;             /* number of maximum stored nearest neighbors */
  protected int real_norm_nn_nodes = 0;            /* number of frequency-used nearest neighbors */
  protected int[][] norm_DistSortedIDMatrix;       // the primary IDs of N_neighbor nodes sorted by cost

  public NearNeighborManager() {}

  public NearNeighborManager(ISquareIMatrixEngine costMatrix, int norm_nn_nodes) {
    this(costMatrix, norm_nn_nodes, costMatrix.getNodeNumber()-1);
  }

  public NearNeighborManager(ISquareIMatrixEngine costMatrix, int norm_nn_nodes, int max_nn_nodes) {
    setNeighborManager(costMatrix, norm_nn_nodes, max_nn_nodes);
  }
  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("Knni", Knni));
    initUtility(new IntegerUtility("Knnm", Knnm));
    initUtility(new BasicUtility("costMatrix", costMatrixEngine));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    Knni = TypeConverter.toInteger(getValue("Knni"));
    Knnm = TypeConverter.toInteger(getValue("Knnm"));
    costMatrixEngine = (IGetCostMatrixEngine)getValue("costMatrix");
  }

  protected void setRootInfo(AbsLandscape landscape) {
    super.setRootInfo(landscape);
    this.setNeighborManager(costMatrixEngine.getCostMatrix(), Knni, Knnm);
  }

  public void setNeighborManager(ISquareIMatrixEngine costMatrix, int norm_nn_nodes, int max_nn_nodes) {
    int nodeNumber = costMatrix.getNodeNumber();
    this.real_max_nn_nodes = Math.min(max_nn_nodes, nodeNumber-1);
    this.real_norm_nn_nodes = Math.min(norm_nn_nodes, real_max_nn_nodes);
    init_Norm_DistMatrix(costMatrix, real_max_nn_nodes);
  }

  private void init_Norm_DistMatrix(ISquareIMatrixEngine dataMatrix, int max_nn_nodes) {
    int nodeNumber = dataMatrix.getNodeNumber();
    norm_DistSortedIDMatrix = new int[nodeNumber][max_nn_nodes];
    int[] distData = new int[nodeNumber];
    int[] indexData = new int[nodeNumber];
    for (int i=0; i<nodeNumber; i++) {
      for (int j=0; j<nodeNumber; j++) {
        indexData[j] = j;
        distData[j] = dataMatrix.getValueAt(i, j);
      }
      distData[i] = -Integer.MAX_VALUE;
      ArrayOperator.quickSort2(distData, indexData, 0, nodeNumber-1);
      System.arraycopy(indexData, 1, norm_DistSortedIDMatrix[i], 0, max_nn_nodes);
    }
  }
  
  
  public int getNodeNumber() {
    return norm_DistSortedIDMatrix.length;
  }

  public int getNNNumberUpLimit() {
    return real_max_nn_nodes;
  }

  public int[] getNNArrayAt(int index) {
    return norm_DistSortedIDMatrix[index];
  }

  public int getElementNumberAt(int index) {
    return real_norm_nn_nodes;
  }

  //For debug
  public int[] getOrder(SearchState point, boolean isForward) {
    int[] nodes = point.getIArray();
    int[] orderIDs = new int[nodes.length];
    if (isForward) {
      for (int i=0; i<nodes.length; i++) {
        orderIDs[i] = getOrder(nodes[i], nodes[(i+1)%nodes.length]);
      }
    } else {
      for (int i=0; i<nodes.length; i++) {
        orderIDs[i] = getOrder(nodes[(i+1)%nodes.length], nodes[i]);
      }
    }
    return orderIDs;
  }

  private int getOrder(int startNode, int endNode) {
    return BasicArray.getExactIndex(norm_DistSortedIDMatrix[startNode], endNode);
  }


  public boolean isCoveredEdge(int startID, int endID) {
    if (isConnectedAt(startID, endID)) {
      return true;
    }
    if (isConnectedAt(endID, startID)) {
      return true;
    }
    return false;
  }

  public boolean isConnectedAt(int nodeID, int connectID) {
    int[] nnArray = this.getNNArrayAt(nodeID);
    int index = BasicArray.getExactIndex(nnArray, connectID, 0, this.getElementNumberAt(nodeID));
    return (index != -1);
  }

  public int getCoverNumber(SearchState point) {
    int number = 0;
    int nodeNumber = point.getNodeNumber();
    for(int i=0; i<nodeNumber; i++) {
      if (isCoveredEdge(point.getValueAt(i), point.getValueAt(BasicArray.getSuccessorID(nodeNumber, i)))) {
        number ++;
      }
    }
    return number;
  }

  public final static int getNearestNeighbor(int actualNode, IGetLocalCostEngine costEngine) {
  	int nearestNode = -1;
  	int nodeNumber = costEngine.getNodeNumber();
  	double minCost = Double.MAX_VALUE;
  	for(int i = 0; i < nodeNumber; ++i) {
  		if(i != actualNode) {
  			double cost = costEngine.getLocalCost(actualNode, i);
  			if(cost < minCost) {
  				nearestNode = i;
  				minCost = cost; 
  			}
  		}
  	}
  	return nearestNode;
  }
}

