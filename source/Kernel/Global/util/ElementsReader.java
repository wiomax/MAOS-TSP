/**
 * Description: Reading elements one by one from a text in multi-lines, seperated by tokenTag
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 12, 2006
 */

package Global.util;

import Global.methods.*;
import Global.define.*;

public class ElementsReader {
  private int lineID = -1;
  private int elemID = -1;
  private String[] lines = null;
  private String[] lineContents=null;
  public String tokenTag = BasicTag.NULL_SEPERATE_TAG+",";

  public ElementsReader() {}

  public ElementsReader(String tokenTag) {this.tokenTag=tokenTag;}

  public void setContent(String[] lines) {
    setContent(lines, 0);
  }

  public void setContent(String[] lines, int startID) {
    this.lines = lines;
    lineID = startID;
    lineContents = null;
    elemID = -1;
  }

  public String readNextElement() {
    if (lines==null) return null;
    if (lines.length==0) return null;
    elemID++;
    if (lineContents==null||elemID>=lineContents.length) {
      if (lineID>=lines.length) return null;
      lineContents = GlobalString.tokenize(lines[lineID], tokenTag);
      lineID++;
      elemID=0;
    }
    return lineContents[elemID];
  }
}
