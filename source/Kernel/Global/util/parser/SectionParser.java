/**
 * Description: Parser sections
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep. 19, 2002
 * Xiaofeng Xie    Mar. 20, 2003    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */


package Global.util.parser;

import java.io.*;
import java.util.*;

import Global.basic.*;
import Global.methods.*;
import Global.define.BasicTag;

public class SectionParser {
  public static String lFlag = BasicTag.LEFT_BRACKET_TAG;
  public static String rFlag = BasicTag.RIGHT_BRACKET_TAG;

  public static boolean isLineSections(String lineStr) {
    String realStr= GlobalString.trim(lineStr, " \t");
    return realStr.startsWith(BasicTag.LEFT_BRACKET_TAG)&&realStr.endsWith(BasicTag.RIGHT_BRACKET_TAG);
  }

  public static String toSectionLine(String title) {
    return lFlag+title+rFlag;
  }

  public static String getSectionContent(String sectionLineStr) {
    return GlobalString.trim(sectionLineStr, lFlag+rFlag);
  }

  public static BasicMap[] parseSections(String iniSrc) throws Exception {
    Vector<BasicMap> data = new Vector<BasicMap>();
    StringReader outStringReader = new StringReader(iniSrc);
    BufferedReader outReader = new BufferedReader(outStringReader);
    String str;
    BasicMap no = null;
    while (true) {
      str = outReader.readLine();
      if (str==null) {
        break;
      }
      if(isLineSections(str.trim())) {
        no = new BasicMap();
        no.name = getSectionContent(str);
        data.add(no);
      } else {
        if (no!=null) no.value += str+BasicTag.RETURN_TAG;
      }
    }
    BasicMap[] nos = new BasicMap[data.size()];
    for(int i=0; i<nos.length; i++) {
      nos[i] = (BasicMap)data.elementAt(i);
    }
    return nos;
  }
}
