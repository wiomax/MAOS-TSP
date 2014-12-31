/**
 * Description: provide a parent class that implement the interface Clonable
 * and Serializable.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 22, 2000
 */

package Global.basic;

import java.io.*;

public abstract class BasicObject extends Object implements Cloneable, Serializable {

  public BasicObject getSelf() {
    return this;
  }
}
