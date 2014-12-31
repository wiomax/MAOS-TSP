/**
 * Description: The interface for select next node in order to construct a tour
 *  in sequrence
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 24, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */


package implement.TSP.behavior.construct;

import implement.TSP.behavior.neighbor.*;


public abstract class AbsNNNextNodeSelector implements INextNodeSelectEngine {
  private INeighborEngine neighborManager;
  //temp values
  protected int[] tempNeighborIDs;     //temp total phero of the neighborhood of a node

  public AbsNNNextNodeSelector(INeighborEngine neighborManager) {
    this.neighborManager = neighborManager;
    //temp memory for N_neighbor nodes
    tempNeighborIDs =  new int[neighborManager.getNodeNumber()];
  }
  abstract protected int selectNextNodeAt(int cityID, boolean[] occupyFlags, INeighborEngine neighborManager);

  public int selectNextNodeAt(int cityID, boolean[] occupyFlags) {
    return selectNextNodeAt(cityID, occupyFlags, neighborManager);
  }
}
