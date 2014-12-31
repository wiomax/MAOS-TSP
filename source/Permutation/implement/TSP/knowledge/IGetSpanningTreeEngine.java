/**
 * Description:
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 08, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */


package implement.TSP.knowledge;

import Global.basic.data.matrix.*;


public interface IGetSpanningTreeEngine {
  public double getSpanningTree(SpanningTree spanningTree, ISquareIMatrixEngine absDMatrix);
}
