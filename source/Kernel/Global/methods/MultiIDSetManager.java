/**
 * Description: manage multi IDSet, in order to distinguish repeated IDSet
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 06, 2005
 *
 */

package Global.methods;

import java.util.*;

public class MultiIDSetManager {
  private Vector<int[]> idSetGroup = new Vector<int[]>();

  public int[] getIDSetAt(int index) {
    return (int[])idSetGroup.elementAt(index);
  }

  public int getSize() {
    return idSetGroup.size();
  }

  public void clear() {
    idSetGroup.clear();
  }

  public boolean setElement(int[] idSet) {
    if (idSet==null||idSet.length==0) {
      return false;
    }
    int index = getIndex(idSet);
    if (index==-1) {
      idSetGroup.add(idSet);
      return true;
    }
    return false;
  }

  private int getIndex(int[] idSet) {
    int size = getSize();
    for (int i=0; i<size; i++) {
      if (ArraySet.isDualContain(getIDSetAt(i), idSet)) {
        return i;
      }
    }
    return -1;
  }
}

