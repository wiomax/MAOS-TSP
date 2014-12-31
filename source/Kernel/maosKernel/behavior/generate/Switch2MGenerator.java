/**
 * Description: The description of generating operator for achieve an trial state in choose one from
 * two generators in given probability.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 09, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.behavior.generate;

import maosKernel.represent.landscape.*;
import maosKernel.memory.*;
import Global.methods.*;
import Global.basic.nodes.utilities.*;

public class Switch2MGenerator extends AbsMiniGenerator {
  private AbsMiniGenerator baseGeneratorA, baseGeneratorB;

  private double nScaleA = 1;
  private double nScaleB = 1;
  //tempV
  private double rateA = 0.5;

  public void initUtilities() {
    super.initUtilities();
    initUtility(new DoubleUtility("nScaleA", nScaleA));
    initUtility(new DoubleUtility("nScaleB", nScaleB));
    initUtility(new BasicUtility("GeneratorA", baseGeneratorA));
    initUtility(new BasicUtility("GeneratorB", baseGeneratorB));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    nScaleA = TypeConverter.toDouble(getValue("nScaleA"));
    nScaleB = TypeConverter.toDouble(getValue("nScaleB"));
    baseGeneratorA = (AbsMiniGenerator)(getValue("GeneratorA"));
    baseGeneratorB = (AbsMiniGenerator)getValue("GeneratorB");
  }

  protected void setRootInfo(AbsLandscape landscape){
    super.setRootInfo(landscape);
    rateA = nScaleA/(nScaleA+nScaleB);
  }

  private AbsMiniGenerator getSubGenerator() {
    if (Math.random()<rateA) return this.baseGeneratorA;
    else return this.baseGeneratorB;
  }

  public boolean generateBehavior(EncodedState trialState, EncodedState baseState, IGetEachEncodedStateEngine referEngine) {
    return getSubGenerator().generateBehavior(trialState, baseState, referEngine);
  }

}
