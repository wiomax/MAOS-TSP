/**
 * Description: common point crossover
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 29, 2006
 * Xiaofeng Xie    Dec 09, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.combine;

import maosKernel.represent.landscape.space.*;

public class CommonOrderXS extends AbsOrderXS {
  SearchState basePoint, referPoint;

  public CommonOrderXS() {
    super.gBaseMode = GBASE_COMMONNV;
    super.referMode = REFER_NONE;
    super.remainMode = REMAIN_RND;
  }

  protected void initSelection(SearchState basePoint, SearchState referPoint){
    this.basePoint = basePoint;
    this.referPoint = referPoint;
   }

}
