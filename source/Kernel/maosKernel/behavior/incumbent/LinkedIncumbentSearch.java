/**
 * Description: The description of linked incumbent search 
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Jul 14, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.incumbent;

import maosKernel.memory.*;
import Global.basic.nodes.utilities.*;

public class LinkedIncumbentSearch extends AbsIncumbentSearch {
  private AbsIncumbentSearch[] searchers = new AbsIncumbentSearch[2];

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("searcherA", searchers[0]));
    initUtility(new BasicUtility("searcherB", searchers[1]));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    searchers[0] = (AbsIncumbentSearch)(getValue("searcherA"));
    searchers[1] = (AbsIncumbentSearch)getValue("searcherB");
  }

  public boolean generate(EncodedState baseState) {
    boolean isValid = false;
    for (int i=0; i<searchers.length; i++) {
      if (searchers[i].generate(baseState)) {
        isValid = true;
      }
    }
    return isValid;
  }
}
