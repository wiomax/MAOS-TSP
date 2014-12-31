/**
 * Description: 
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Mar 24, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 *
 */
package maosKernel.memory;

import maosKernel.represent.landscape.quality.*;

public abstract class EncodedStateHandler {

  public static void encodeGlobalCost_AsNotEncoded(ICalcGlobalCostEngine encoder, EncodedState encodedState) {
    if (!encodedState.isEncoded()) {
      encodeGlobalCost(encoder, encodedState);
    }
  }

  public static void encodeGlobalCost(ICalcGlobalCostEngine encoder, EncodedState encodedState) {
	encodedState.setEncodeInfo(encoder.getGlobalCost(encodedState.getSearchState()));
  }

  //If stateA is no worse than stateB, then return true
  public static boolean evaluate(IQualityEvaluateEngine edEvalEngine, EncodedState stateA, EncodedState StateB) {
    return edEvalEngine.evaluate(stateA.getEncodeInfo(), StateB.getEncodeInfo());
  }
  
  public static String writeEncodedInfo(IGetEncodeTypeEngine encoder, double encodeValue) {
	  if (encoder.isDiscrete()) return ""+(int)Math.rint(encodeValue);
	  else return ""+encodeValue;
  }
}
