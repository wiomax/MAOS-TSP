/**
 * Description: provide a named class
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 4, 2003
 */

package Global.basic;

public class BasicName extends BasicObject implements IGetNameEngine, IGetMapStringEngine, IGetSummaryEngine {
  public String name = "";

  public BasicName() {}

  public BasicName(String name) {
    this.name = name;
  }

  public Object clone(){
    try {
      BasicName bn = (BasicName)super.clone();
      bn.name = name;
      return bn;
    } catch(Exception e) {
      return null;
    }
  }

  public String getName() {
    return name;
  }

  public boolean isSameName(String outName) {
    return name.equalsIgnoreCase(outName)||name.equalsIgnoreCase(ANY_NAME)||outName.equalsIgnoreCase(ANY_NAME);
  }

  public String toString() {
    return name;
  }

  public String getSummary() {
    return name;
  }

  public String getMapString() {
    return "name="+name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
