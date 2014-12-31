/**
 * Description: Parser elements
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep. 19, 2002
 * Xiaofeng Xie    Mar. 20, 2003    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */


package Global.util.parser;

import Global.basic.data.bound.*;

public class ElementParser {
  public String START_TAG = "<";
  public String END_TAG = ">";
  public String NORMAL_TAG = "@";

  public String replaceElement(String template, String replacedContent, String name) {
    BasicIBound bib = null;
    do {
      bib = findElement(template, name);
      if(bib==null) break;
      template = template.substring(0, bib.minValue)+replacedContent+template.substring(bib.maxValue);
    } while (true);
    return template;
  }

  public String getTaggedName(String name) {
    return START_TAG+NORMAL_TAG+name+NORMAL_TAG+END_TAG;
  }

  public BasicIBound findElement(String template, String name) {
    String taggedName = getTaggedName(name);
    int startIndex = template.indexOf(taggedName);
    if(startIndex==-1) return null;
    return new BasicIBound(startIndex, startIndex+taggedName.length());
  }

//  public BasicIBound findFirstElement(String content) {
//    int startIndex = content.indexOf(START_TAG);
//    int stopIndex = content.indexOf(END_TAG);
//    return new BasicIBound(startIndex, stopIndex);
//  }
//
//  public String getElementName(String content, BasicIBound bound) {
//    try {
//      return content.substring(bound.minValue, bound.maxValue);
//    } catch(Exception e) {
//      return "";
//    }
//  }
}
