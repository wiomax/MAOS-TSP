/**
 * Description: provide a irregular matrix with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 22, 2005
 */

package Global.basic.data.matrix;

public abstract class AbsRegularIMatrix extends AbsIMatrix implements IRegular2DEngine {

  public double getLowTriSD(double averageV) {
    int nodeNumber = getNodeNumber();
    double sum = 0;
    int totalN = nodeNumber*(nodeNumber-1)/2;
    for (int i=0; i<nodeNumber; i++) {
      for (int j=0; j<i; j++) {
        sum += Math.pow(getValueAt(i, j)-averageV, 2);
      }
    }
    return Math.sqrt(sum/totalN);
  }

  public double getLowTriAverage() {
    int nodeNumber = getNodeNumber();
    double sum = 0;
    int totalN = nodeNumber*(nodeNumber-1)/2;
    for (int i=0; i<nodeNumber; i++) {
      for (int j=0; j<i; j++) {
        sum += getValueAt(i, j);
      }
    }
    return sum/totalN;
  }

  public int getElementNumberAt(int index) {
    return getElementNumber();
  }
}
