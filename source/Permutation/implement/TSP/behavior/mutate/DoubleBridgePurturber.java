/**
 * Description: The description of Double-Bridge move.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 12, 2005     For TSP problem
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @version 1.0
 * @ Reference:
 *  [1] S. Lin and B. W. Kernighan, "An effective heuristic algorithm for the
 *  traveling-salesman problem," Operations Research, vol. 21, pp. 498-516, 1973.
 */

package implement.TSP.behavior.mutate;

import java.util.*;
import Global.methods.*;

import maosKernel.behavior.incumbent.*;
import maosKernel.represent.landscape.space.*;

public class DoubleBridgePurturber extends AbsPerturbationSearch {
  private int[] v4 = new int[4];
  
  public DoubleBridgePurturber() {}

  public void blindNonsequentialMove(int[] tour) {
    RandomGenerator.randomNoadjecentSelection(v4, tour.length);
    Arrays.sort(v4);
    onceBridgeExchange(tour, v4);
  }

  protected boolean generate(SearchState baseState) {
    blindNonsequentialMove(baseState.getIArray());
    return true;
  }
  
  public void onceBridgeExchange(int[] tour, int[] v4) {
    int n = tour.length;
    int cityX1A = v4[0];
    int cityX1B = BasicArray.getSuccessorID(n, cityX1A);
    int cityX2A = v4[1];
    int cityX2B = BasicArray.getSuccessorID(n, cityX2A);
    int cityX3A = v4[2];
    int cityX3B = BasicArray.getSuccessorID(n, cityX3A);
    int cityX4A = v4[3];
    int cityX4B = BasicArray.getSuccessorID(n, cityX4A);
    interalBridgeExchange(tour, cityX1A, cityX1B, cityX2A, cityX2B, cityX3A, cityX3B, cityX4A, cityX4B);
  }

  private void interalBridgeExchange(int[] tour, int cityX1A, int cityX1B, int cityX2A, int cityX2B, int cityX3A, int cityX3B, int cityX4A, int cityX4B) {
    ArrayOperator.inverseSegment(tour, cityX1B, cityX2A);
    ArrayOperator.inverseSegment(tour, cityX2B, cityX3A);
    ArrayOperator.inverseSegment(tour, cityX3B, cityX4A);
    ArrayOperator.inverseSegment(tour, cityX4B, cityX1A);
  }
}
