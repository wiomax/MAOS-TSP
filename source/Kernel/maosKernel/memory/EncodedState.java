/**
 * Description: provide the location information of a point with goodness value
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 6, 2005
 * Xiaofeng Xie    Apr 20, 2005
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.memory;

import Global.basic.BasicObject;
import maosKernel.represent.landscape.space.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.represent.landscape.*;

public class EncodedState extends BasicObject {
  private SearchState searchState;
  private double encodeValue;

  public EncodedState(AbsLandscape landscape) {
    setSearchState(landscape.getSearchSpace().getNullState());
    removeEncodeInfo();
  }

  public EncodedState(SearchState state) {
    this.setSearchState(state);
  }

  public Object clone() {
    try {
      EncodedState obj = (EncodedState)super.clone();
      obj.searchState = (SearchState)(searchState.clone());
      obj.encodeValue = this.encodeValue;
      return obj;
    } catch(Exception e) {
      return null;
    }
  }

  public void setSearchState(SearchState state) {
    this.searchState = state;
  }

  public void setDeltaEncodeInfo(double deltaV) {
	this.encodeValue += deltaV;
  }

  public void setEncodeInfo(double encodeValue) {
    this.encodeValue = encodeValue;
  }

  public void importSearchState(SearchState searchState) {
    this.searchState.importSearchState(searchState);
  }

  public void importEncodeState(EncodedState point) {
    importEncodeState(point.getSearchState(), point.encodeValue);
  }

  public void importEncodeState(SearchState searchState, double encodeValue) {
    importSearchState(searchState);
    setEncodeInfo(encodeValue);
  }

  public SearchState getSearchState() {
    return searchState;
  }

  public double getEncodeInfo() {
    return encodeValue;
  }

  public void removeEncodeInfo() {
    encodeValue = ICalcGlobalCostEngine.WOSRTVALUE;
  }

  public boolean isEncoded() {
    return encodeValue != ICalcGlobalCostEngine.WOSRTVALUE;
  }
}
