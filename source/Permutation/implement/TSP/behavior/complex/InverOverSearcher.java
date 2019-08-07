/**
* Description: The description of Inver-Over Operator.
*
* @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 02, 2005
 * Xiaofeng Xie    Sep 03, 2006
 * Xiaofeng Xie    Aug 25, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @version 1.0
 *
 * @ Reference:
 * [1]	T. Guo and Z. Michalewicz, "Inver-over operator for the TSP,"
 * International Conference on Parallel Problem Solving from Nature,
 * Amsterdam, the Netherlands, 1998.
 */

package implement.TSP.behavior.complex;

import Global.methods.*;
import Global.basic.nodes.utilities.*;

import maosKernel.represent.landscape.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.space.*;
import maosKernel.behavior.complex.*;
import implement.TSP.represent.*;
import implement.TSP.knowledge.*;

public class InverOverSearcher extends AbsComplexSearch {
  //parameters
  private double pRandom = 0.01;
  private int MaxInverNumber = Integer.MAX_VALUE;

  //global information
  private int[][] distEngine;
  
  //temp
  int nodeNumber = -1;

  public InverOverSearcher() {}

  protected void setRootInfo(AbsLandscape landscape) {
    this.distEngine = ((InternalRepresentation)landscape).getIGetProblemDataEngine().getCostMatrix();
    nodeNumber = landscape.getNodeNumber();
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("pRandom", pRandom));
    initUtility(new IntegerUtility("MaxInverNumber", MaxInverNumber));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    pRandom = TypeConverter.toDouble(getValue("pRandom"));
    MaxInverNumber = TypeConverter.toInteger(getValue("MaxInverNumber"));
  }

  private int selectNextNodeAt(IGetEachEncodedStateEngine infoLibrary, int cityIndex) {
    SearchState consultant = StateSetHandler.getRandomOne(infoLibrary).getSearchState();
    int consultantCurrentCityIndex = BasicArray.getExactIndex(consultant.getIArray(), cityIndex);
    return consultant.getValueAt(BasicArray.getSuccessorID(nodeNumber, consultantCurrentCityIndex));
  }

  public static int get2EdgeChangeDiffValue(int[][] distEngine, int[] tour, int node1AID, int node2AID) {
    int n = tour.length;
    return MutateKnowledge.get2EdgeChangeDiffValue(distEngine,
      tour[node1AID],
      tour[BasicArray.getSuccessorID(n, node1AID)],
      tour[node2AID],
      tour[BasicArray.getSuccessorID(n, node2AID)]);
  }

  /**
   * Do an inver-over crossover.
  */
 public boolean generate(EncodedState trialPoint, EncodedState basePoint, IGetEachEncodedStateEngine msState) {
    int offspringCityPrime         = -1;
    int offspringCityPrimeIndex    = -1;
    int offspringCurrentCity       = -1;
    int offspringCurrentCityIndex  = -1;

    trialPoint.importEncodeState(basePoint);

    int numCities = basePoint.getSearchState().getNodeNumber();

    int startIndex = RandomGenerator.intRangeRandom(numCities);

    offspringCurrentCityIndex = startIndex;
    offspringCurrentCity      = trialPoint.getSearchState().getValueAt(offspringCurrentCityIndex);
    offspringCityPrime        = -1;
    offspringCityPrimeIndex   = -1;

    int pCount = 0;

    int invertFrom, invertTo;
    boolean isWorked = false;
    int invCount = 0;
    while (true) {
    /**
     * To help maintain diversity, there is a small chance that inver-over
     * does a self mutation, the true alternative part of this next conditional.
     * From the remain cities
     */
      if ( Math.random() < pRandom) {
          int pVaried = RandomGenerator.intRangeRandom(numCities - 1)+1;
          offspringCityPrimeIndex = (offspringCurrentCityIndex+pVaried)%numCities;
          offspringCityPrime = trialPoint.getSearchState().getValueAt(offspringCityPrimeIndex );
      } else {
        offspringCityPrime = selectNextNodeAt(msState, offspringCurrentCity);
        offspringCityPrimeIndex =  BasicArray.getExactIndex(trialPoint.getSearchState().getIArray(), offspringCityPrime);
      }

      /**
       ** Check if that the next city is the neighbor of self.
       */
      if(
          (trialPoint.getSearchState().getValueAt(BasicArray.getPrecessorID(nodeNumber, offspringCurrentCityIndex)) ==offspringCityPrime) ||
          (trialPoint.getSearchState().getValueAt(BasicArray.getSuccessorID(nodeNumber, offspringCurrentCityIndex))==offspringCityPrime)
      ) {
        offspringCurrentCityIndex = BasicArray.getSuccessorID(numCities, offspringCurrentCityIndex);
        pCount ++;
      } else {
      /**
       ** If not done, then invert the segment of my genome from beside
       ** my current city of interest to the one I want next to it, which will
       ** accomplish that goal.
       */
        invertFrom = BasicArray.getSuccessorID(numCities, offspringCurrentCityIndex);
        invertTo   = offspringCityPrimeIndex;

        int diff = get2EdgeChangeDiffValue(distEngine, trialPoint.getSearchState().getIArray(), offspringCurrentCityIndex, offspringCityPrimeIndex);
        trialPoint.setDeltaEncodeInfo((double)diff);
        ArrayOperator.inverseSegment(trialPoint.getSearchState().getIArray(), invertFrom, invertTo);

        invCount++;
        isWorked = true;
        /**
         ** Make the city I just moved next to my city of interest, and try again.
         */
        offspringCurrentCityIndex = invertTo;
        pCount += BasicArray.getPeriodDistance(numCities, invertFrom, invertTo);
      }
      if (invCount>this.MaxInverNumber || pCount>=numCities) {
        return isWorked;
      }
      offspringCurrentCity = trialPoint.getSearchState().getValueAt(offspringCurrentCityIndex);
    }
  }
}
