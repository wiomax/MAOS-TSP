/**
 * Description: provides a loading mode for UtilSetNode.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 21, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.parser;

import Global.basic.nodes.*;
import Global.basic.nodes.utilities.*;
import Global.basic.nodes.loader.*;
import Global.methods.*;

public class BracketUtilSetNodeLoader extends AbsUtilSetNodeLoader {
  public String MAYORLTOKEN = "(";
  public String MAYORRTOKEN = ")";
  public String HEADTOKEN = "|";
  public String PARAMTOKEN = ",";
  public String VALUETOKEN = "=";

  public String saveUtilSetNode(UtilSetNode node) {
    String content = node.getKey();
    if (!GlobalString.isNull(node.getName())) {
      content += HEADTOKEN+node.getName();
    }
    int utilSize = node.getUtilitiesSize();
    if (utilSize>0) {
      content += MAYORLTOKEN;
      for (int i=0; i<utilSize; i++) {
        BasicUtility utility = node.getUtilityAt(i);
        content += utility.getName()+VALUETOKEN+TypeConverter.toString(utility.getValue());
        if (i<utilSize-1) content += PARAMTOKEN;
      }
      content += MAYORRTOKEN;
    }
    return content;
  }

  public void loadUtilSetNode(UtilSetNode node, String content) {
    String[] contents = GlobalString.tokenize(content, MAYORLTOKEN);

    String[] attribStrs = GlobalString.tokenize(contents[0], HEADTOKEN);
    if(attribStrs.length==2) node.setName(attribStrs[1]);
    node.setKey(attribStrs[0]);

    if (contents.length>1) {
      String[] preParamStrs = GlobalString.tokenize(contents[1], MAYORRTOKEN);
      String[] paramStrs = GlobalString.tokenize(preParamStrs[0], PARAMTOKEN);
      for (int i = 0; i < paramStrs.length; i++) {
        String[] paramMap = GlobalString.tokenize(paramStrs[i], VALUETOKEN);
        node.initUtility(new BasicUtility(paramMap[0].trim(), paramMap[1].trim()));
      }
    }
  }
}
