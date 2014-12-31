/**
 * Description: provide a node for a spanning tree
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 21, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 * [1] K. Helsgaun,"An Effective Implementation of the Lin-Kernighan Traveling
 *  Salesman Heuristic", European J Operational Research 126 (1), 106-130 (2000).
 */

package implement.TSP.knowledge;

import Global.basic.data.collection.*;

public class SpanningTreeNode {
  public static final int NULL_NODE = -1;
  protected int parentID = NULL_NODE;
  protected IVector childIDs = new IVector();

  public SpanningTreeNode(){ }

  public void clear() {
    parentID = -1;
    childIDs.clear();
  }
  public int getParentID() {
    return parentID;
  }

  public void setParentID(int id) {
    this.parentID = id;
  }

  public int getChildSize() {
    return childIDs.size();
  }

  public int get_V_Value() {
    return getDegree()-2;
  }

  public int getDegree() {
    int degree = getChildSize();
    if (this.parentID != NULL_NODE) {
      degree ++;
    }
    return degree;
  }

  public int getChildIDAt(int index) {
    return childIDs.elementAt(index);
  }

  public void addChild(int childID) {
    childIDs.add(childID);
  }

  public void removeChildbyIndex(int index) {
    childIDs.removeElementAt(index);
  }
}
