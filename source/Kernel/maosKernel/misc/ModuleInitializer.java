/**
 * Description: For initializing all modules
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 26, 2007    Created
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel.misc;

import java.util.*;

import maosKernel.represent.information.*;

public class ModuleInitializer {
  private Vector<ICycleInitEngine> cycleModules = new Vector<ICycleInitEngine>();
  private Vector<ITrialInitEngine> trialModules = new Vector<ITrialInitEngine>();

  public ModuleInitializer() {
  }

  public boolean isElementExist(AbsInformationElement element, Vector<?> modules) {
    AbsInformationElement elementAtI = null;
    for (int i=0; i<modules.size(); i++) {
      elementAtI = (AbsInformationElement) modules.elementAt(i);
      if (elementAtI.equals(element)) return true;
    }
    return false;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
	protected boolean addElement(AbsInformationElement element, Vector modules) {
    if (!isElementExist(element, modules)) {
      modules.addElement(element);
      return true;
    }
    return false;
  }

  public void setModule(AbsInformationElement element) {
    if (element instanceof ITrialInitEngine) {
      addElement(element, trialModules);
    }
    if (element instanceof ICycleInitEngine) {
      addElement(element, cycleModules);
    }
  }

  public void initTrial() {
    for (int i=0; i<trialModules.size(); i++) {
      ((ITrialInitEngine) trialModules.elementAt(i)).initTrial();
    }
  }

  public void initCycle() {
    for (int i=0; i<cycleModules.size(); i++) {
      ((ICycleInitEngine) cycleModules.elementAt(i)).initCycle();
    }
  }

}
