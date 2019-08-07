/**
 * Description: For generating random number in quasi-mode.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    NOv 01, 2005
 *
 */

package Global.util;

import java.util.*;
import Global.methods.*;

public class QuasiRandomIndicesGenerator {
  private Vector<int[]> indicesArray = new Vector<int[]>();

  public QuasiRandomIndicesGenerator(int maxNum, int times) {
    this(maxNum, times, 100);
  }

  public QuasiRandomIndicesGenerator(int maxNum, int times, int quasiN) {
    init(maxNum, times, quasiN);
  }

  public void init(int maxNum, int times, int quasiN){
    indicesArray.clear();
    for (int i=0; i<quasiN; i++) {
      indicesArray.add(RandomGenerator.randomDistinctSelection(maxNum, times));
    }
  }

  public int[] randomDistinctSelection() {
    return indicesArray.elementAt(RandomGenerator.intRangeRandom(indicesArray.size()));
  }

}
