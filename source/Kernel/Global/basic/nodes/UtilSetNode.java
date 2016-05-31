/**
 * Description: The description of an UtilSetNode: for representing a tree with BasicUtility leafs.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jan 30, 2004
 * Xiaofeng Xie    Oct 13, 2004
 *
 * @version 1.0
 */

package Global.basic.nodes;

import java.util.*;

import Global.methods.*;

import Global.basic.nodes.utilities.*;

public class UtilSetNode extends AbstractNode implements IShortcutInitEngine, IGetObjectbyNameEngine {

  protected Vector<BasicUtility> utilites = new Vector<BasicUtility>(); //BasicUtility[]

  public UtilSetNode() {
  }

  public UtilSetNode(String outName) {
    super(outName);
  }

  public UtilSetNode(String outName, String outKey) {
    super(outName);
    this.key = outKey;
  }

  public String getNodeName() {
    String superName = super.getName();
    if (GlobalString.isNull(superName)) {
      String simpleName = this.getClass().getName();
      return simpleName.substring(simpleName.lastIndexOf(".")+1);
    }
    return superName;
  }

  public String getNodeKey() {
    String superKey = super.getKey();
    if (GlobalString.isNull(superKey)) return this.getClass().getName();
    return superKey;
  }

  public UtilSetNode(UtilSetNode os) {
    this.importCopiedOperStructure(os);
  }

  public int getUtilitiesSize() {
    return utilites.size();
  }

  public Vector<BasicUtility> getUtilites() {
    return utilites;
  }

  protected Vector<BasicUtility> getCopiedUtilities() {
    Vector<BasicUtility> newNodes = new Vector<BasicUtility>();
    for(int i=0; i<utilites.size(); i++) {
      BasicUtility util = this.getUtilityAt(i);
      newNodes.add(util.getCopiedUtility());
    }
    return newNodes;
  }

  public void initUtilities(Vector<BasicUtility> utils) {
    for(int i=0; i<utils.size(); i++) {
      initUtility((BasicUtility)utils.elementAt(i));
    }
  }

  protected void initUtilityAt(BasicUtility util, int index) {
    if(index<0 ||index>=utilites.size()) {
      utilites.add(util);
    } else {
      this.setValueAt(util.getValue(), index);
    }
  }

  public void initUtility(BasicUtility util) {
    int index = getUtilityIndex(util.getName());
    initUtilityAt(util, index);
  }

  public static void toStringableNode(UtilSetNode os) {
    for(int i=os.getUtilSize()-1; i>=0; i--) {
      BasicUtility util = os.getUtilityAt(i);
      if(!(util instanceof StringableUtility)) {
        os.removeUtilityAt(i);
      }
    }
  }

  public void importCopiedOperStructure(UtilSetNode os) {
    if(!isSameNameAndKey(os.getName(), os.getKey())) {
      this.removeUtilities();
    }
    importNode(os);
    for(int i=0; i<os.getUtilSize(); i++) {
      BasicUtility util = os.getUtilityAt(i);
      int index = getUtilityIndex(util.getName());
      if(index !=-1) {
        setValueAt(util.getClonedValue(), index);
      } else {
        utilites.add(util.getCopiedUtility());
      }
    }
  }
  public void importUtilities(UtilSetNode os) {
    for (int i=0; i<os.getUtilitiesSize(); i++) {
      BasicUtility utility = os.getUtilityAt(i);
      this.setValue(utility.getName(),utility.getValue());
    }
  }

  public void importUtilSetNode(UtilSetNode os) {
    super.importNode(os);
    importUtilities(os);
  }

  public void importRealOperStructure(UtilSetNode os) {
    importNode(os);
    this.utilites = os.getUtilites();
  }

  public int getUtilityIndex(String name, String key) {
    for(int i=0; i<utilites.size(); i++) {
      if(getUtilityAt(i).isSameNameAndKey(name, key)) {
        return i;
      }
    }
    return -1;
  }

  public int getUtilityIndex(String name) {
    return StringSearch.getSelectedIndex(utilites, name);
  }

  public BasicUtility getUtility(String name) {
    int index = getUtilityIndex(name);
    if(index==-1) return null;
    return getUtilityAt(index);
  }

  public Object getValue(String name) {
    int index = getUtilityIndex(name);
    if(index==-1) return null;
    return getValueAt(index);
  }

  public Object getValueAt(int utilIndex) {
    return getUtilityAt(utilIndex).getValue();
  }

  public boolean setValueAt(Object value, int utilIndex) {
    if(utilIndex==-1) return false;
    return getUtilityAt(utilIndex).setValue(value);
  }

  public boolean setValue(String name, Object value) {
    int index = getUtilityIndex(name);
    return setValueAt(value, index);
  }

//  public String getSummary() {
//    String props = name;
//    String subprops = "";
//    for(int i=0; i<getUtilSize(); i++) {
//      BasicUtility util = getUtilityAt(i);
//      if(util instanceof StringableUtility) {
//        if(subprops.length()!=0) {
//          subprops+=",";
//        }
//        subprops+=util.getSummary();
//      }
//    }
//    if(subprops.length()!=0) {
//      props+="("+subprops+")";
//    }
//    return props;
//  }

  public int getUtilSize() {
    return utilites.size();
  }

  public void setUtilities(Vector<BasicUtility> utils) {
    for(int i=0; i<utils.size(); i++) {
      BasicUtility util = getUtilityAt(utils, i);
      initUtility(util);
    }
  }

  public void removeUtilityAt(int index) {
    utilites.remove(index);
  }

  public void removeUtilities() {
    utilites.clear();
  }

  protected boolean isCovariantValid() throws Exception {
    return true;
  }

  public boolean isValid() throws Exception {
    for(int i=0; i<this.getUtilSize(); i++) {
      BasicUtility util = this.getUtilityAt(i);
      if (!util.isSatisfyConstraints()) {

        return false;
      }
    }
    return isCovariantValid();
  }

  private static BasicUtility getUtilityAt(Vector<BasicUtility> utils, int index) {
    return (BasicUtility)utils.elementAt(index);
  }

  public BasicUtility getUtilityAt(int index) {
    return getUtilityAt(utilites, index);
  }

  public void initUtilities() {}

  public void shortcutInit() throws Exception {}

  public String[] getAllNames() {
    String[] names = new String[this.getUtilitiesSize()];
    for (int i=0; i<names.length; i++) {
      names[i] = this.getUtilityAt(i).getName();
    }
    return names;
  }

  public String getNodesString(String[] nodeNames, String innerSepString, String mediumSepString) {
    String str = "";
    for (int i=0; i<nodeNames.length; i++) {
      str += nodeNames[i]+innerSepString+this.getUtility(nodeNames[i]).getValueString();
      if (i<nodeNames.length-1) str += mediumSepString;
    }
    return str;
  }

  public String getSummary() {
    String summary = this.getNodeKey();
    if (!GlobalString.isNull(this.getName())) summary += "|"+this.getNodeName();
    String specSummary = getSpecialSummary();
    if (specSummary.length()>0) {
      return summary+"("+specSummary+")";
    }
    return summary;
  }

  private String getSpecialSummary() {
    String paramStr = "";
    for (int i=0; i<this.getUtilitiesSize(); i++) {
      BasicUtility utility = this.getUtilityAt(i);
      paramStr += utility.getSummary();
      if (i<this.getUtilitiesSize()-1) {
        paramStr += ",";
      }
    }
    return paramStr;
  }
}
