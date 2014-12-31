/**
 * Description: provide a Vector with integer values
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2005    Adapt from JDK1.4 Vector
 *
 */

package Global.basic.data.collection;

public class IVector {
  /**
   * The number of valid components in this <tt>Vector</tt> object.
   * Components <tt>elementData[0]</tt> through
   * <tt>elementData[elementCount-1]</tt> are the actual items.
   *
   * @serial
   */
  public int elementCount;
  /**
   * The array buffer into which the components of the vector are
   * stored. The capacity of the vector is the length of this array buffer,
   * and is at least large enough to contain all the vector's elements.<p>
   *
   * Any array elements following the last element in the Vector are null.
   *
   * @serial
   */
  public int[] elementData;

  /**
   * The amount by which the capacity of the vector is automatically
   * incremented when its size becomes greater than its capacity.  If
   * the capacity increment is less than or equal to zero, the capacity
   * of the vector is doubled each time it needs to grow.
   *
   * @serial
   */
  protected int capacityIncrement;

  /**
   * Constructs an empty vector with the specified initial capacity and
   * capacity increment.
   *
   * @param   initialCapacity     the initial capacity of the vector.
   * @param   capacityIncrement   the amount by which the capacity is
   *                              increased when the vector overflows.
   * @exception IllegalArgumentException if the specified initial capacity
   *               is negative
   */
  public IVector(int initialCapacity, int capacityIncrement) {
      super();
      if (initialCapacity < 0)
          throw new IllegalArgumentException("Illegal Capacity: "+
                                             initialCapacity);
      this.elementData = new int[initialCapacity];
      this.capacityIncrement = capacityIncrement;
  }

  /**
   * Constructs an empty vector with the specified initial capacity and
   * with its capacity increment equal to zero.
   *
   * @param   initialCapacity   the initial capacity of the vector.
   * @exception IllegalArgumentException if the specified initial capacity
   *               is negative
   */
  public IVector(int initialCapacity) {
      this(initialCapacity, 0);
  }

  /**
   * Constructs an empty vector so that its internal data array
   * has size <tt>10</tt> and its standard capacity increment is
   * zero.
   */
  public IVector() {
      this(10);
  }

  /**
   * Removes all of the elements from this Vector.  The Vector will
   * be empty after this call returns (unless it throws an exception).
   *
   * @since 1.2
   */
  public void clear() {
      elementCount = 0;
  }

  /**
   * Deletes the component at the specified index. Each component in
   * this vector with an index greater or equal to the specified
   * <code>index</code> is shifted downward to have an index one
   * smaller than the value it had previously. The size of this vector
   * is decreased by <tt>1</tt>.<p>
   *
   * The index must be a value greater than or equal to <code>0</code>
   * and less than the current size of the vector. <p>
   *
   * This method is identical in functionality to the remove method
   * (which is part of the List interface).  Note that the remove method
   * returns the old value that was stored at the specified position.
   *
   * @param      index   the index of the object to remove.
   * @exception  ArrayIndexOutOfBoundsException  if the index was invalid.
   * @see        #size()
   * @see	   #remove(int)
   * @see	   List
   */
  public synchronized void removeElementAt(int index) {
      modCount++;
      if (index >= elementCount) {
          throw new ArrayIndexOutOfBoundsException(index + " >= " +
                                                   elementCount);
      }
      else if (index < 0) {
          throw new ArrayIndexOutOfBoundsException(index);
      }
      int j = elementCount - index - 1;
      if (j > 0) {
          System.arraycopy(elementData, index + 1, elementData, index, j);
      }
      elementCount--;
//      elementData[elementCount] = null; /* to let gc do its work */
  }

  /**
   * Searches for the first occurence of the given argument, testing
   * for equality using the <code>equals</code> method.
   *
   * @param   elem   an object.
   * @return  the index of the first occurrence of the argument in this
   *          vector, that is, the smallest value <tt>k</tt> such that
   *          <tt>elem.equals(elementData[k])</tt> is <tt>true</tt>;
   *          returns <code>-1</code> if the object is not found.
   * @see     Object#equals(Object)
   */
  public int indexOf(int elem) {
      return indexOf(elem, 0);
  }

  /**
   * Searches for the first occurence of the given argument, beginning
   * the search at <code>index</code>, and testing for equality using
   * the <code>equals</code> method.
   *
   * @param   elem    an object.
   * @param   index   the non-negative index to start searching from.
   * @return  the index of the first occurrence of the object argument in
   *          this vector at position <code>index</code> or later in the
   *          vector, that is, the smallest value <tt>k</tt> such that
   *          <tt>elem.equals(elementData[k]) && (k &gt;= index)</tt> is
   *          <tt>true</tt>; returns <code>-1</code> if the object is not
   *          found. (Returns <code>-1</code> if <tt>index</tt> &gt;= the
   *          current size of this <tt>Vector</tt>.)
   * @exception  IndexOutOfBoundsException  if <tt>index</tt> is negative.
   * @see     Object#equals(Object)
   */
  public synchronized int indexOf(int elem, int index) {
      for (int i = index ; i < elementCount ; i++)
          if (elem ==elementData[i])
              return i;
      return -1;
  }

  /**
   * Copies the components of this vector into the specified array. The
   * item at index <tt>k</tt> in this vector is copied into component
   * <tt>k</tt> of <tt>anArray</tt>. The array must be big enough to hold
   * all the objects in this vector, else an
   * <tt>IndexOutOfBoundsException</tt> is thrown.
   *
   * @param   anArray   the array into which the components get copied.
   * @throws  NullPointerException if the given array is null.
   */
  public synchronized void copyInto(int anArray[]) {
      System.arraycopy(elementData, 0, anArray, 0, elementCount);
  }

  public synchronized void importIVector(IVector iVector) {
    for (int i=0; i<iVector.size(); i++) {
      this.addElement(iVector.elementAt(i));
    }
//    this.elementCount = iVector.elementCount;
//    this.capacityIncrement = iVector.capacityIncrement;
//    this.modCount = iVector.modCount;
//    this.elementData = new int[iVector.elementData.length];
//    this.copyInto(iVector.elementData);
  }

  /**
   * Trims the capacity of this vector to be the vector's current
   * size. If the capacity of this vector is larger than its current
   * size, then the capacity is changed to equal the size by replacing
   * its internal data array, kept in the field <tt>elementData</tt>,
   * with a smaller one. An application can use this operation to
   * minimize the storage of a vector.
   */
  public synchronized void trimToSize() {
      modCount++;
      int oldCapacity = elementData.length;
      if (elementCount < oldCapacity) {
          int oldData[] = elementData;
          elementData = new int[elementCount];
          System.arraycopy(oldData, 0, elementData, 0, elementCount);
      }
  }

  /**
   * Increases the capacity of this vector, if necessary, to ensure
   * that it can hold at least the number of components specified by
   * the minimum capacity argument.
   *
   * <p>If the current capacity of this vector is less than
   * <tt>minCapacity</tt>, then its capacity is increased by replacing its
   * internal data array, kept in the field <tt>elementData</tt>, with a
   * larger one.  The size of the new data array will be the old size plus
   * <tt>capacityIncrement</tt>, unless the value of
   * <tt>capacityIncrement</tt> is less than or equal to zero, in which case
   * the new capacity will be twice the old capacity; but if this new size
   * is still smaller than <tt>minCapacity</tt>, then the new capacity will
   * be <tt>minCapacity</tt>.
   *
   * @param minCapacity the desired minimum capacity.
   */
  public synchronized void ensureCapacity(int minCapacity) {
      modCount++;
      ensureCapacityHelper(minCapacity);
  }

  /**
   * This implements the unsynchronized semantics of ensureCapacity.
   * Synchronized methods in this class can internally call this
   * method for ensuring capacity without incurring the cost of an
   * extra synchronization.
   *
   * @see java.util.Vector#ensureCapacity(int)
   */
  private void ensureCapacityHelper(int minCapacity) {
      int oldCapacity = elementData.length;
      if (minCapacity > oldCapacity) {
          int oldData[] = elementData;
          int newCapacity = (capacityIncrement > 0) ?
              (oldCapacity + capacityIncrement) : (oldCapacity * 2);
          if (newCapacity < minCapacity) {
              newCapacity = minCapacity;
          }
          elementData = new int[newCapacity];
          System.arraycopy(oldData, 0, elementData, 0, elementCount);
      }
  }


  /**
   * Returns the number of components in this vector.
   *
   * @return  the number of components in this vector.
   */
  public synchronized int size() {
      return elementCount;
  }

  /**
   * Tests if this vector has no components.
   *
   * @return  <code>true</code> if and only if this vector has
   *          no components, that is, its size is zero;
   *          <code>false</code> otherwise.
   */
  public synchronized boolean isEmpty() {
      return elementCount == 0;
  }

  /**
   * Returns the component at the specified index.<p>
   *
   * This method is identical in functionality to the get method
   * (which is part of the List interface).
   *
   * @param      index   an index into this vector.
   * @return     the component at the specified index.
   * @exception  ArrayIndexOutOfBoundsException  if the <tt>index</tt>
   *             is negative or not less than the current size of this
   *             <tt>Vector</tt> object.
   *             given.
   * @see	   #get(int)
   * @see	   List
   */
  public synchronized int elementAt(int index) {
      if (index >= elementCount) {
          throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
      }

      return elementData[index];
  }

  /**
   * Sets the component at the specified <code>index</code> of this
   * vector to be the specified object. The previous component at that
   * position is discarded.<p>
   *
   * The index must be a value greater than or equal to <code>0</code>
   * and less than the current size of the vector. <p>
   *
   * This method is identical in functionality to the set method
   * (which is part of the List interface). Note that the set method reverses
   * the order of the parameters, to more closely match array usage.  Note
   * also that the set method returns the old value that was stored at the
   * specified position.
   *
   * @param      obj     what the component is to be set to.
   * @param      index   the specified index.
   * @exception  ArrayIndexOutOfBoundsException  if the index was invalid.
   * @see        #size()
   * @see        List
   * @see	   #set(int, java.lang.int)
   */
  public synchronized void setElementAt(int obj, int index) {
      if (index >= elementCount) {
          throw new ArrayIndexOutOfBoundsException(index + " >= " +
                                                   elementCount);
      }
      elementData[index] = obj;
  }

  /**
   * Inserts the specified object as a component in this vector at the
   * specified <code>index</code>. Each component in this vector with
   * an index greater or equal to the specified <code>index</code> is
   * shifted upward to have an index one greater than the value it had
   * previously. <p>
   *
   * The index must be a value greater than or equal to <code>0</code>
   * and less than or equal to the current size of the vector. (If the
   * index is equal to the current size of the vector, the new element
   * is appended to the Vector.)<p>
   *
   * This method is identical in functionality to the add(int, int) method
   * (which is part of the List interface). Note that the add method reverses
   * the order of the parameters, to more closely match array usage.
   *
   * @param      obj     the component to insert.
   * @param      index   where to insert the new component.
   * @exception  ArrayIndexOutOfBoundsException  if the index was invalid.
   * @see        #size()
   * @see	   #add(int, int)
   * @see	   List
   */
  public synchronized void insertElementAt(int obj, int index) {
      modCount++;
      if (index > elementCount) {
          throw new ArrayIndexOutOfBoundsException(index
                                                   + " > " + elementCount);
      }
      ensureCapacityHelper(elementCount + 1);
      System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
      elementData[index] = obj;
      elementCount++;
  }

  /**
   * Adds the specified component to the end of this vector,
   * increasing its size by one. The capacity of this vector is
   * increased if its size becomes greater than its capacity. <p>
   *
   * This method is identical in functionality to the add(int) method
   * (which is part of the List interface).
   *
   * @param   obj   the component to be added.
   * @see	   #add(int)
   * @see	   List
   */
  public synchronized void addElement(int obj) {
      modCount++;
      ensureCapacityHelper(elementCount + 1);
      elementData[elementCount++] = obj;
  }

  public IVector getClonedIVector() {
    IVector iVector = new IVector();
    for (int i=0; i<this.size(); i++) {
      iVector.addElement(this.elementAt(i));
    }
    return iVector;
  }


  /**
   * Returns an array containing all of the elements in this Vector
   * in the correct order.
   *
   * @since 1.2
   */
  public synchronized int[] toArray() {
      int[] result = new int[elementCount];
      System.arraycopy(elementData, 0, result, 0, elementCount);
      return result;
  }


  // Positional Access Operations

  /**
   * Returns the element at the specified position in this Vector.
   *
   * @param index index of element to return.
   * @return object at the specified index
   * @exception ArrayIndexOutOfBoundsException index is out of range (index
   * 		  &lt; 0 || index &gt;= size()).
   * @since 1.2
   */
  public synchronized int get(int index) {
      if (index >= elementCount)
          throw new ArrayIndexOutOfBoundsException(index);

      return elementData[index];
  }

  /**
   * Replaces the element at the specified position in this Vector with the
   * specified element.
   *
   * @param index index of element to replace.
   * @param element element to be stored at the specified position.
   * @return the element previously at the specified position.
   * @exception ArrayIndexOutOfBoundsException index out of range
   *		  (index &lt; 0 || index &gt;= size()).
   * @since 1.2
   */
  public synchronized int set(int index, int element) {
      if (index >= elementCount)
          throw new ArrayIndexOutOfBoundsException(index);

      int oldValue = elementData[index];
      elementData[index] = element;
      return oldValue;
  }

  /**
   * Appends the specified element to the end of this Vector.
   *
   * @param o element to be appended to this Vector.
   * @return true (as per the general contract of Collection.add).
   * @since 1.2
   */
  public synchronized boolean add(int o) {
      modCount++;
      ensureCapacityHelper(elementCount + 1);
      elementData[elementCount++] = o;
      return true;
  }


  /**
   * Inserts the specified element at the specified position in this Vector.
   * Shifts the element currently at that position (if any) and any
   * subsequent elements to the right (adds one to their indices).
   *
   * @param index index at which the specified element is to be inserted.
   * @param element element to be inserted.
   * @exception ArrayIndexOutOfBoundsException index is out of range
   *		  (index &lt; 0 || index &gt; size()).
   * @since 1.2
   */
  public void add(int index, int element) {
      insertElementAt(element, index);
  }

  public int getFirstIndex(int value) {
    for (int i=0; i<this.size(); i++) {
      if (this.elementAt(i)==value) return i;
    }
    return -1;
  }

  /**
   * The number of times this list has been <i>structurally modified</i>.
   * Structural modifications are those that change the size of the
   * list, or otherwise perturb it in such a fashion that iterations in
   * progress may yield incorrect results.<p>
   *
   * This field is used by the iterator and list iterator implementation
   * returned by the <tt>iterator</tt> and <tt>listIterator</tt> methods.
   * If the value of this field changes unexpectedly, the iterator (or list
   * iterator) will throw a <tt>ConcurrentModificationException</tt> in
   * response to the <tt>next</tt>, <tt>remove</tt>, <tt>previous</tt>,
   * <tt>set</tt> or <tt>add</tt> operations.  This provides
   * <i>fail-fast</i> behavior, rather than non-deterministic behavior in
   * the face of concurrent modification during iteration.<p>
   *
   * <b>Use of this field by subclasses is optional.</b> If a subclass
   * wishes to provide fail-fast iterators (and list iterators), then it
   * merely has to increment this field in its <tt>add(int, Object)</tt> and
   * <tt>remove(int)</tt> methods (and any other methods that it overrides
   * that result in structural modifications to the list).  A single call to
   * <tt>add(int, Object)</tt> or <tt>remove(int)</tt> must add no more than
   * one to this field, or the iterators (and list iterators) will throw
   * bogus <tt>ConcurrentModificationExceptions</tt>.  If an implementation
   * does not wish to provide fail-fast iterators, this field may be
   * ignored.
   */
  protected transient int modCount = 0;
}
