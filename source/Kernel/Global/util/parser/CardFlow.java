/**
 * Description: esigned as a vector that consist of a set of Cards.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep. 13, 2002    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.util.parser;

import  java.util.*;
import Global.methods.*;
import Global.basic.*;

public class CardFlow extends BasicAttrib {
  private Vector<Card> subParams = new Vector<Card>();

  public CardFlow(){};

  public int getSize() {
    return subParams.size();
  }

  public int getParamIndex(String cardName) {
    return StringSearch.getSelectedIndex(subParams, cardName);
  }

  public Card getParam(String paramName) {
    int index = getParamIndex(paramName);
    if(index==-1) return null;
    return this.getParamAt(index);
  }

  public Card[] getParams(String paramName) {
    Vector<Card> cards = new Vector<Card>();
    for (int i=0; i<subParams.size(); i++) {
      Card param = (Card)subParams.elementAt(i);
      if (param.getName().equalsIgnoreCase(paramName)) {
        cards.addElement(param);
      }
    }
    Card[] cardArray = new Card[cards.size()];
    for (int i=0; i<cards.size(); i++) {
      cardArray[i] = (Card)cards.elementAt(i);
    }
    return cardArray;
  }

  public Card getParamAt(int index) {
    if (index<0||index>=subParams.size()) {
      return null;
    } else {
      return (Card)subParams.elementAt(index);
    }
  }

  public void addParam(Card outCard) {
    if (outCard==null) {
      return;
    }
    subParams.addElement(outCard);
//    int index = getParamIndex(outCard.getName());
//    if(index==-1) {
//      subParams.addElement(outCard);
//    } else {
//      subParams.setElementAt(outCard, index);
//    }
  }

  public void setParams(Card[] outParams) {
    if (outParams==null) {
      return;
    }
    for (int i=0; i<outParams.length; i++) {
      addParam(outParams[i]);
    }
  }
}
