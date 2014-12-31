/**
 * Description: The description of memory.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 25, 2004
 *
 
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * Please acknowledge the author(s) if you use this code in any way.
 *
 * @version 1.0
 * @Since MAOS1.0
 */

package Global.basic.nodes.utilities;

public abstract class StringableUtility extends BasicUtility {

  protected StringableUtility() {
  }

  protected StringableUtility(String outName) {
    super(outName);
  }

  public boolean setValue(Object outValue) {
    try {
      Object data = getRealObject(outValue.toString());
      return super.setValue(data);
    } catch (Exception e) {
      return false;
    }
  }

  protected Object getRealObject(String sVal) throws Exception {
    return sVal;
  }

  public String getSettingProperty() {
    String propStr = name;
    if(value!=null) {
      propStr += " value=["+value+"]";
    }
    return propStr;
  }

}
