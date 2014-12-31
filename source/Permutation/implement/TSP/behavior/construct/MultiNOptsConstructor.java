/**
 * Description: The initial generator
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 03, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package implement.TSP.behavior.construct;


import Global.methods.*;
import maosKernel.represent.landscape.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.space.*;
import Global.basic.nodes.utilities.*;
import maosKernel.behavior.incumbent.*;
import maosKernel.behavior.greedy.*;

public class MultiNOptsConstructor extends AbsNNConstructor {
  private int baseSize = 10;
  public int mutTimes = 2;
  private EncodedState[] libStates;
  private AbsPerturbationSearch perturbationSearch;
  private AbsExplicitLocalSearch localSearch;

  public MultiNOptsConstructor() {
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new IntegerUtility("baseSize", baseSize));
    initUtility(new IntegerUtility("mutTimes", mutTimes));
    initUtility(new BasicUtility("perturbationSearch", perturbationSearch));
    initUtility(new BasicUtility("localSearch", localSearch));
 }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    baseSize = TypeConverter.toInteger(getValue("baseSize"));
    mutTimes = TypeConverter.toInteger(getValue("mutTimes"));
    localSearch = (AbsExplicitLocalSearch) getValue("localSearch");
  }


  protected void setRootInfo(AbsLandscape landscape) {
    initLibStates(landscape.getSearchSpace());
  }

  private void initLibStates(AbsSearchSpace searchSpace) {
    libStates = new EncodedState[baseSize];
    for (int i=0; i<baseSize; i++) {
      libStates[i] = new EncodedState(searchSpace.getRandomState());
       
      //improved by 3-Opt
      localSearch.generate(libStates[i]);
    }
  }

  private void multi4Opts(EncodedState baseState) {
    for (int i=0; i<mutTimes; i++) {
      perturbationSearch.generate(baseState);
    }
  }

  public boolean generate(EncodedState trialState) {
    EncodedState selState = libStates[RandomGenerator.intRangeRandom(libStates.length)];
    trialState.importEncodeState(selState);
    multi4Opts(trialState);
    return true;
  }
}
