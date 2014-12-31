/**
 * Description: The interface for select next node in order to construct a tour
 *  in sequence according to given probability and neighborhood information
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 24, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 * @ Reference:
 *  [1] Program's name: acotsp
 *  Ant Colony Optimization algorithms (AS, ACS, EAS, RAS, MMAS, BWAS) for the
 *  symmetric TSP, Copyright (C) 2004  Thomas Stuetzle
 *  Email: stuetzle no@spam informatik.tu-darmstadt.de
 */


package implement.TSP.behavior.construct;

import Global.methods.*;
import implement.TSP.behavior.neighbor.*;


public class ProbNNNextNodeSelector extends AbsNNNextNodeSelector {
  private IGetProbabiityEngine probabiityEngine;
  //temp values
  private double[] tempNeighborPheroArray;     //temp total phero of the neighborhood of a node

  public ProbNNNextNodeSelector(INeighborEngine neighborManager, IGetProbabiityEngine probabiityEngine) {
    super(neighborManager);
    this.probabiityEngine = probabiityEngine;
    //temp memory for N_neighbor nodes
    tempNeighborPheroArray = new double[neighborManager.getNodeNumber()];
  }

  private int getCandidateID(int index, int[] candidateIDs, boolean isDirectMode) {
   if (isDirectMode) return index;
   return candidateIDs[index];
  }
  /*
    FUNCTION:      Choose for an ant probabilistically a next city among all
                   unvisited cities in the current city's candidate list.
                   If this is not possible, choose the closest next
    INPUT:         pointer to ant the construction step "phase"
    OUTPUT:        the index of the chosen city for the moving of agent
  */
 protected int selectNextNodeAt(int cityID, boolean[] occupyFlags, INeighborEngine neighborManager) {
    int nNeighbor = 0;
    int currentID;
    int[] nnNodes = neighborManager.getNNArrayAt(cityID);
    int neighborNumber = neighborManager.getElementNumberAt(cityID);
    double[] neighborPheros = probabiityEngine.getProbabilityArrayAt(cityID);
    boolean isDirectMode = probabiityEngine.isPartialMode();
    for (int i = 0; i < neighborNumber; i++) {
      currentID = nnNodes[i];
      if (!occupyFlags[currentID]) {
        tempNeighborPheroArray[nNeighbor] = neighborPheros[getCandidateID(i, nnNodes, isDirectMode)];
        tempNeighborIDs[nNeighbor] = currentID;
        nNeighbor++;
      }
    }

    if (nNeighbor != 0) {
    /* if at least one neighbor is eligible, then chose one according
     * to the selection probabilities
     */
      int selID = BasicArray.getProbSelectIndex(tempNeighborPheroArray, false, 0, nNeighbor);
      return tempNeighborIDs[selID];
    } else {
        /* All nodes from the candidate set are selected */
        // chooses a node as the next city with the maximal value of
        // heuristic information 
      for (int i = neighborNumber; i < nnNodes.length; i++) {
        if (!occupyFlags[nnNodes[i]]) {
          return nnNodes[i];
        }
      }
      return -1;
    }
  }
}
