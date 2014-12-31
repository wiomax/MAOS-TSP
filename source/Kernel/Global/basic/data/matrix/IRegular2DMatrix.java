/**
 * Description: provide the information for an regular 2D matrix
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 12, 2005
 */

package Global.basic.data.matrix;

public class IRegular2DMatrix extends IIrregular2DMatrix implements IRegularNumberEngine {
  public IRegular2DMatrix(int row, int col) {
    super(new int[row][col]);
  }

  public int getElementNumber() {
    if (this.getNodeNumber() == 0) {
      return 0;
    }
    else {
      return dataMatrix[0].length;
    }
  }

}
