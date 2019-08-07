/**
 * Description: provide an edge for two nodes
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 7, 2005
 */

package Global.basic.data;

public class BasicEdge {
  public int startIndex = -1;
  public int endIndex = -1;

  public BasicEdge(){};

  public BasicEdge(int startI, int endI) {
    this.startIndex = startI;
    this.endIndex = endI;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }
}

