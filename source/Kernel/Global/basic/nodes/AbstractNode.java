/**
 * Description: The description of an node, with nodeID.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jan 30, 2004
 * Xiaofeng Xie    Feb 11, 2004
 *
 * @version 1.0
 */

package Global.basic.nodes;

import Global.basic.*;

public abstract class AbstractNode extends BasicAttrib {
  public int nodeID = -1;

  public AbstractNode(){
  }

  public AbstractNode(String outName) {
    super(outName);
  }

  public Object clone(){
    try {
      AbstractNode no = (AbstractNode)super.clone();
      no.nodeID = nodeID;
      return no;
    } catch(Exception e) {
      return null;
    }
  }

  public void importNode(AbstractNode node) {
    importBasicAttrib(node);
    this.nodeID = node.nodeID;
  }

}
