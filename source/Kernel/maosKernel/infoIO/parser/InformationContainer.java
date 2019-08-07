/**
 * Description: For any knowledge element
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 11, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.parser;

import java.util.*;
import maosKernel.represent.information.*;

public class InformationContainer {
  private Hashtable<String, AbsInformationElement> knowledgeTable = new Hashtable<String, AbsInformationElement>();

  public AbsInformationElement getElement(String nodeName){
    return (AbsInformationElement)knowledgeTable.get(nodeName);
  }

  public void putElement(AbsInformationElement knowledgeNode) {
    putElement(knowledgeNode.getNodeName(), knowledgeNode);
  }
  
  public void putElement(String name, AbsInformationElement knowledgeNode) {
    knowledgeTable.put(name, knowledgeNode);
  }
}
