/**
 * Description: The description of a utility: an AbstractNode with value in Discrete type.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jan 30, 2004    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.basic.nodes.utilities;

import java.util.*;

import Global.methods.*;
import Global.define.*;

public class DiscreteUtility extends StringableUtility {
  protected Vector<Object> elements = new Vector<Object>();

  protected DiscreteUtility() {}

  protected DiscreteUtility(String outName) {
    super(outName);
    this.description = "Discrete";
  }

  public DiscreteUtility(String outName, Vector<Object> outElems){
    this(outName);
    elements = outElems;
    if (outElems.size()>0) {
      setValue(outElems.elementAt(0));
    }
  }

  public DiscreteUtility(String outName, String[] outElems) {
    this(outName, ObjectMatrix.convertArrayTo1DVector(outElems));
  }

  public DiscreteUtility(String outName, String currentValue, String[] outElems) {
    this(outName, outElems);
    this.setValue(currentValue);
  }

  public String getDefaultValue() {
    return value.toString();
  }

  public Object getClonedValue(Object value) {
    if(value==null) return null;
    return value.toString();
  }

  public int getElementIndex(Object value){
    return StringSearch.getSelectedIndex(getAllElements(), value);
  }

  public Vector<Object> getAllElements(){
    return elements;
  }

  public boolean isSatisfyConstraints(Object data) {
    if(!super.isSatisfyConstraints(data)) return false;
    int index = getElementIndex(data);
    return index!=GlobalValue.INVALID_NATURAL_NUMBER;
  }

  public Object getRandomElement() {
    int index = RandomGenerator.intRangeRandom(0, elements.size()-1);
    return elements.get(index).toString();
  }
}
