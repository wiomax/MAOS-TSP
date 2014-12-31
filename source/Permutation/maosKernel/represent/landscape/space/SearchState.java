/**
 * Description: provide the location information of a state in integer array
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr  6, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.represent.landscape.space;

import Global.methods.*;
import Global.basic.data.point.*;

public class SearchState extends StableIPoint {

  public SearchState(int nodeNumber) {
    super(nodeNumber);
  }

  public SearchState(int[] content) {
    super(content);
  }

  public int getValueIndex(int value) {
    return BasicArray.getExactIndex(location, value);
  }

  public void importSearchState(SearchState point) {
    System.arraycopy(point.location, 0, location, 0, location.length);
  }

  public Object clone() {
    try {
      SearchState obj = (SearchState)super.clone();
      return obj;
    } catch(Exception e) {
      return null;
    }
  }
}
