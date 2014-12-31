/**
 * Description: provide a irregular matrix with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 22, 2005
 */

package Global.basic.data.matrix;

public abstract class AbsVMatrix implements IIrregular2DEngine {

  public int getElementNumber() {
    return getNodeNumber();
  }

  public int getMaxCardinalityID() {
    int mcID = -1;
    int mcValue = -1;
    for (int i=0; i<this.getNodeNumber(); i++) {
      if (mcValue<getElementNumberAt(i)) {
        mcValue = getElementNumberAt(i);
        mcID = i;
      }
    }
    return mcID;
  }

  public int getMaxCardinality() {
    return this.getElementNumberAt(getMaxCardinalityID());
  }

}
