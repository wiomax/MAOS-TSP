/**
 * Description: Blocks-Keeping & Common positions avoided One point crossover
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 26, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.common.behavior.combine;

import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.*;
import Global.basic.data.collection.*;
import Global.methods.*;
import implement.common.knowledge.*;

public class BCAOnePointOrderXS extends CAOnePointOrderXS {
  protected DualIAlienArray nbBaseIDArray;

  public BCAOnePointOrderXS() {}
  
  protected void setRootInfo(AbsLandscape landscape) {
    super.setRootInfo(landscape);
    nbBaseIDArray = new DualIAlienArray(landscape.getNodeNumber());
  }
  
  protected void initSelection(SearchState basePoint, SearchState referPoint){
    super.initSelection(basePoint,referPoint);
    SequencesOperation.getSharedBlocks(nbBaseIDArray, basePoint, referPoint, startID+1, endID-1);
  }

   protected boolean initSelectedIDs(int nodeNumber) {
    if (nbBaseIDArray.getSize()==0) return super.initSelectedIDs(nodeNumber);
    int remainSize = endID-startID-2-nbBaseIDArray.getSize();
    if(remainSize<0) return false;
    xPoint = RandomGenerator.intRangeRandom(startID+1, endID-1);
    if (nbBaseIDArray.getElementID(xPoint)!=-1){
      int tempX = RandomGenerator.intRangeRandom(remainSize);
      int count = 0;
      for (int i=startID+1; i<endID-1; i++) {
        if (nbBaseIDArray.getElementID(i)==-1) {
          count++;
          if (count==tempX) {
            xPoint = i;
            break;
          }
        }
      }
    }
    isForward = RandomGenerator.booleanRandom();
    return true;
  }
}
