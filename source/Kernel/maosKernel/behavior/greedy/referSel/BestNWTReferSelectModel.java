/**
 * Description: provide the information for obtaining a best as with a 
 *   non-worse-than reference value among the candidates for 1-move
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 10, 2006
 * Xiaofeng Xie    Jun 02, 2008 
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.greedy.referSel;

public class BestNWTReferSelectModel extends BestofAllReferSelectModel {
  
  public BestNWTReferSelectModel() {}
  
  public boolean isAcceptableQuality(double newReferQuality) {
    if (newReferQuality>0) return false;
    return super.isAcceptableQuality(newReferQuality);
  }
}

