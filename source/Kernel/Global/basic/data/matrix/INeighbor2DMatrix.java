/**
 * Description: provide the information for an irregular 2D matrix with no
 *   repeated elements and same with the node at a node
 *   --Unusally used by neigbhorhood
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 21, 2005    Initial version, trans for VaryNeighborManager
 *
 */

package Global.basic.data.matrix;


public class INeighbor2DMatrix extends IIrregular2DVariedMatrix {

  public INeighbor2DMatrix(int nodeNumber) {
    super(nodeNumber, nodeNumber-1);
  }

  public boolean addNodeTo(int elemID, int nodeID) {
    if (elemID==nodeID) {
      return false;
    }
    return super.addNodeTo(elemID, nodeID);
  }

  public int[] getNNArrayAt(int index) {
    return super.getArrayAt(index);
  }
}

