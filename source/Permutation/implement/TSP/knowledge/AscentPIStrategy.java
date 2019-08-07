/**
 * Description: attempts to find penalties (pi-values).
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 22, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 * [1] K. Helsgaun,"An Effective Implementation of the Lin-Kernighan Traveling
 *  Salesman Heuristic", European J Operational Research 126 (1), 106-130 (2000).
 * [2] See HLK 1.3 code
 */

package implement.TSP.knowledge;

import Global.basic.data.*;
import Global.basic.data.matrix.*;
//import implement.TSP.behavior.neighbor.*;

public class AscentPIStrategy {
  private PiCostMatrix piCostMatrix;
  private SpanningTree spanningTree;

  private AlphaMatrixManager alphaMatrixManager;
  private Minimum1TreeHandler min1TreeHandler;

  //****************************
  private double[] bestPiArray;
  private int[] currDegree;
  private int[] lastDegree;
  //****************************
  
  private PrimeMSTSolver mstSolver;

  public AscentPIStrategy(PiCostMatrix piCostMatrix) {
    this.piCostMatrix = piCostMatrix;

    int nodeNumber = piCostMatrix.getNodeNumber();
    bestPiArray = new double[nodeNumber];
    currDegree = new int[nodeNumber];
    lastDegree = new int[nodeNumber];
    spanningTree = new SpanningTree(nodeNumber);
    alphaMatrixManager = new AlphaMatrixManager(nodeNumber);
    min1TreeHandler = new Minimum1TreeHandler(nodeNumber);
    mstSolver = new PrimeMSTSolver(nodeNumber);
  }

  public FullSquareIMatrix getAlphaMatrix() {
    alphaMatrixManager.toAlphaMatrix(spanningTree, piCostMatrix);
    return alphaMatrixManager.getAlphaMatrix();
  }

  private void get1TreeDegree(int[] degrees) {
    int nodeNumber = spanningTree.getNodeNumber();
    for (int i = 0; i < nodeNumber; i++) {
      degrees[i] =  spanningTree.getNodeAt(i).getDegree();
    }
    BasicEdge basicEdge = min1TreeHandler.getMax1TreeEdge();
    degrees[basicEdge.startIndex] ++;
    degrees[basicEdge.endIndex] ++;
  }

  public int getPrivateW() {
    return spanningTree.getTotalCost(piCostMatrix)
           + min1TreeHandler.getMax1TreeEdgeCost()
           - piCostMatrix.getPenaltyValue();
  }

  private void adjustMinimum1TreeHandler() {
    spanningTree.clear();
    mstSolver.getSpanningTree(spanningTree, piCostMatrix);
    min1TreeHandler.initMinimum1TreeFromSpanningTree(spanningTree, piCostMatrix);
  }

//  private void adjustMinimum1TreeHandler(INeighborEngine neighborManager) {
//    spanningTree.clear();
//    mstSolver.getSpanningTree(spanningTree, piCostMatrix, neighborManager);
//    min1TreeHandler.initMinimum1TreeFromSpanningTree(spanningTree, piCostMatrix);
//  }

  /*
     The Ascent function computes a lower bound on the optimal tour length using
     subgradient optimization. The function also transforms the original problem
     into a problem in which the alpha-values reflect the likelihood of edges being
     optimal.

     The function attempts to find penalties (pi-values) that maximizes the lower
     bound L(T(Pi)) - 2*PiSum, where L(T(Pi)) denotes the length of the minimum
     spanning 1-tree computed from the transformed distances, and PiSum denotes the
     sum of pi-values. If C(i,j) denotes the length of and edge (i,j) then the
     transformed distance D(i,j) of an edge is C(i,j) + Pi(i) + Pi(j).
  */

 public void ascentPiArray(double[] basePIArray) {
   int nodeNumber = basePIArray.length;

   int Period = 0, P = 0;
   int minInitPeriod=10, InitialPeriod = (int)Math.sqrt(nodeNumber);
   if (InitialPeriod < minInitPeriod) InitialPeriod = minInitPeriod;

   double stepT = 1.0;
   double currRatio = 0.7;
   double precisionT = 1.0/piCostMatrix.getPrecision();
   //int nnSize = 50;

   piCostMatrix.setPIArray(basePIArray);
   this.adjustMinimum1TreeHandler();
   get1TreeDegree(lastDegree);
   int BestW = getPrivateW();

   //to sparse graph
   //NearNeighborManager neighborManager = new NearNeighborManager(getAlphaMatrix(), nnSize);

   /* Perform subgradient optimization with decreasing period length and decreasing step size */
   boolean InitialPhase = true;
   for (Period = InitialPeriod; Period > 0 && stepT > precisionT; Period /= 2, stepT /= 2) {      /* Period and step size are halved at each iteration */
     for (P = 1; stepT>precisionT && P <= Period; P++) {
       /* Adjust the Pi-values */
       get1TreeDegree(currDegree);
       for (int i=0; i<nodeNumber; i++) {
         if (currDegree[i]!=2) {
           basePIArray[i] += stepT * (currRatio * (currDegree[i]-2) + (1-currRatio) * (lastDegree[i]-2));
         }
       }
       System.arraycopy(currDegree, 0, lastDegree, 0, nodeNumber);
       piCostMatrix.setPIArray(basePIArray);
       /* Compute a minimum 1-tree in the sparse graph */
       this.adjustMinimum1TreeHandler(); //??? should use sparse mode in future
       //this.adjustMinimum1TreeHandler(neighborManager); //??? should use sparse mode in future
       int W = getPrivateW();
       if (W > BestW) {
         BestW = W;
         /* Update the BestPi-values */
         System.arraycopy(basePIArray, 0, bestPiArray, 0, nodeNumber);
         /* If in the initial phase, the step size is doubled */
         if (InitialPhase) stepT *= 2;
         /* If the improvement was found at the last iteration of the current
            period, then double the period */
         if (P == Period && (Period *= 2) > InitialPeriod)
             Period = InitialPeriod;
       } else if (InitialPhase && P > Period / 2) {
         /* Conclude the initial phase */
         InitialPhase = false;
         P = 0;
         stepT *= 0.75;
       }
     }
   }
   /* Save the Pi-values */
   System.arraycopy(bestPiArray, 0, basePIArray, 0, nodeNumber);
 }
}

//public void ascentPiArray() {
//  int nodeNumber = this.getNodeNumber();
//  int[] currDegree = new int[nodeNumber];
//  double[] currPIArray = new double[nodeNumber];
//
//  double T = 1.0;
//  double precisionT = 1.0/piCostMatrix.getPrecision();
//  double ratio = 0.618;
//
//  piCostMatrix.setPIArray(basePIArray);
//  this.adjustMinimum1TreeHandler();
//  get1TreeDegree(currDegree);
//  int BestW = getPrivateW();
//
//  BasicNeighborManager neighborManager = new BasicNeighborManager(getAlphaMatrix(), 50);
//
//  /* Perform subgradient optimization with decreasing period length and decreasing step size */
//  boolean InitialPhase = true;
//  while(T>precisionT) {
//
////     GlobalTools.CPUTimeCostCounter.setStart("setPIArray");
//    for (int i=0; i<nodeNumber; i++) {
//      if (currDegree[i]!=2) {
//        currPIArray[i] = basePIArray[i] + T * (currDegree[i]-2);
//      }
//    }
//    piCostMatrix.setPIArray(currPIArray);
////     GlobalTools.CPUTimeCostCounter.setEnd("setPIArray");
////     GlobalTools.CPUTimeCostCounter.setStart("adjustMinimum1TreeHandler");
//    /* Compute a minimum 1-tree in the sparse graph */
//    this.adjustMinimum1TreeHandler(neighborManager); //??? should use sparse mode in future
////     GlobalTools.CPUTimeCostCounter.setEnd("adjustMinimum1TreeHandler");
//    int W = getPrivateW();
//    if (W > BestW) {
//      BestW = W;
//      /* Update the BestPi-values */
//      System.arraycopy(currPIArray, 0, basePIArray, 0, nodeNumber);
//      get1TreeDegree(currDegree);
//      /* If in the initial phase, the step size is increased */
//      if (InitialPhase) T /= ratio;
//    } else {
//      /* Conclude the initial phase */
//      InitialPhase = false;
//      T *= ratio;
//    }
//  }
//
////   GlobalTools.CPUTimeCostCounter.printSelf();
//}
