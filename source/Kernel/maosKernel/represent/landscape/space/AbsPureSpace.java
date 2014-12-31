/**
 * Description: provide the information for homogeneous space (with SearchState in a certain data structure)
 *   Types: SPACE_SEARCH, (SPACE_FULL), SPACE_SUPER, SPACE_PARTIAL, SPACE_NULL
 * 
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.represent.landscape.space;

public abstract class AbsPureSpace {
  
  abstract public boolean isValid(SearchState point);

}

