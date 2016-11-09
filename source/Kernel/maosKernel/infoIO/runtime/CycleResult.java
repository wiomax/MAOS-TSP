/**
 * Description: provide the result settings (saving information)
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 21, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.runtime;

import java.util.*;
import Global.basic.*;
import Global.methods.StringSearch;
import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.quality.*;
import maosKernel.memory.*;

public class CycleResult extends BasicObject {
  public EncodedState BState;
  public double Time;
  public int NCycle;
  private IGetEncodeTypeEngine encodeType;
  
  private Vector<BasicMap> auxData = new Vector<BasicMap>(); //BasicMap

  public CycleResult() {
  }
  
  public void submitData(String name, String val) {
    int index = StringSearch.getSelectedIndex(auxData, name);
    if (index == -1)  {
      BasicMap newData = new BasicMap(name, val);
      auxData.add(newData);
    } else 
    ((BasicMap)auxData.elementAt(index)).value = val;
  }

  public void setInfo(AbsLandscape landscape) {
    BState = new EncodedState(landscape);
    encodeType = landscape;
  }

  public Object clone() {
    try {
      CycleResult obj = (CycleResult)super.clone();
      obj.BState = (EncodedState)BState.clone();
      obj.Time = this.Time;
      obj.NCycle = this.NCycle;
      return obj;
    } catch(Exception e) {
      return null;
    }
  }

  public void importData(CycleResult cycleResult) {
    this.BState.importEncodeState(cycleResult.BState);
    this.Time = cycleResult.Time;
    this.NCycle = cycleResult.NCycle;
    for (int i=0; i<auxData.size(); i++) {
      BasicMap newData = (BasicMap) cycleResult.auxData.elementAt(i);
      this.submitData(newData.name, newData.value);
    }
  }

  public void initTrial() {
    NCycle = 0;
    Time = 0;
    BState.removeEncodeInfo();
    auxData.clear();
  }

  private String getTimeString() {
    return Time+"";
//    return FormatOutput.smartFormatConvert(Time);
  }

  protected Vector<String> getBasicNames() {
    Vector<String> names = new Vector<String>();
    names.add("EvalV");
    names.add("Time");
    return names;
  }
  
  public Vector<String> getAllNames() {
    Vector<String> names = getBasicNames();
    for (int i=0; i<this.auxData.size(); i++) {
      names.add(((BasicMap)auxData.elementAt(i)).name);
    }
    return names;
  }

  public Vector<String> getCycledAllNames() {
    Vector<String> names = getAllNames();
    names.add("NCycle");
    return names;
  }
  
  protected Vector<String> getBasicValues() {
    Vector<String> values = new Vector<String>();
    values.add(EncodedStateHandler.writeEncodedInfo(encodeType, BState.getEncodeInfo()));
    values.add(getTimeString());
    return values;
 }

  public Vector<String> getAllValues() {
    Vector<String> values = getBasicValues();
    for (int i=0; i<this.auxData.size(); i++) {
      values.add(((BasicMap)auxData.elementAt(i)).value);
    }
    return values;
  }

  public Vector<String> getCycledAllValues() {
    Vector<String> values = getAllValues();
    values.add(""+NCycle);
    return values;
  }
  
  public String getCycleInfo() {
    return "EvalV=" + EncodedStateHandler.writeEncodedInfo(encodeType, BState.getEncodeInfo()) + " at NCycle=" + NCycle +" & calcTime=" + getTimeString() +"(s)";
  }
}

