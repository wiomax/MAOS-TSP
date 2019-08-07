/**
 * Description: A tree which stores parameters of the command card.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 26, 2000
 * Xiaofeng Xie    Sep. 13, 2002    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.util.parser;

import java.util.*;


import Global.methods.*;
import Global.basic.*;
import Global.define.*;

public class Card extends BasicAttrib {
  private Vector<CardParam> subParams = new Vector<CardParam>();

  public Card(){};

  public Card(String newName){setName(newName);};

  public int getSize() {
    return subParams.size();
  }

  public CardParam getParam(String paramName) {
    int index = getParamIndex(paramName);
    return getParamAt(index);
  }

  public void removeParamAt(int index) {
    if (index<0||index>=subParams.size()) {
      return;
    }
    subParams.remove(index);
  }

  public int getParamIndex(String paramName) {
    return StringSearch.getSelectedIndex(subParams, paramName);
  }

  public CardParam getParamAt(int index) {
    if (index<0||index>=subParams.size()) {
      return null;
    } else {
      return (CardParam)subParams.elementAt(index);
    }
  }

  public void addParam(CardParam outParam) {
    if (outParam==null) {
      return;
    }
    int index = getParamIndex(outParam.getName());
    if(index==-1) {
      subParams.addElement(outParam);
    } else {
      subParams.setElementAt(outParam, index);
    }
  }


  public void setParams(CardParam[] outParams) {
    if (outParams==null) {
      return;
    }
    for (int i=0; i<outParams.length; i++) {
      addParam(outParams[i]);
    }
  }

  public String saveObject() {
    String str = this.getName();
    for(int i=0; i<this.getSize(); i++) {
      str += " "+this.getParamAt(i).saveObject();
    }
    str += BasicTag.RETURN_TAG;
    return str;
  }
}
