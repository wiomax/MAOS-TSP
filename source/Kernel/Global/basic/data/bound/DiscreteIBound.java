/**
 * Description: provide an discrete bound, and the corresponding operations
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb. 08, 2006
 */

package Global.basic.data.bound;

import java.util.*;
import Global.methods.*;

public class DiscreteIBound {
  public int startValue;
  public boolean[] occupyFlags;

  public DiscreteIBound(int startValue, int length) {
    this.initRange(startValue, length);
  }

  public DiscreteIBound() {}

  public int getLength(){
    return occupyFlags.length;
  }

  public int getEndValue() {
    return startValue+getLength();
  }

  public int getFlagNumber(boolean flag) {
    return BasicArray.getElementNumber(occupyFlags, flag);
  }

  public boolean getFlag(int index) {
    return occupyFlags[index];
  }

  public int getStartBias(int newStartV) {
    return newStartV-startValue;
  }

  public void initRange(int startValue, int length) {
    this.startValue = startValue;
    occupyFlags = new boolean[length];
    Arrays.fill(occupyFlags, true);
  }
}
