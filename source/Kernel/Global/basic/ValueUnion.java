/**
 * Description: store and convert different data type
 *
 * @ Author        Create/Modi     Note
 * Yinli Fang      Mar 22, 2000
 * Xiaofeng Xie    Sep 19, 2002
 *
 */


package Global.basic;

public class ValueUnion implements Cloneable{
  public static final String CLASS_STRING_NAME = new String().getClass().getName();
  public static final String CLASS_DOUBLE_NAME = new Double(0).getClass().getName();
  public static final String CLASS_INTEGER_NAME = new Integer(0).getClass().getName();
  public static final String CLASS_BOOLEAN_NAME = new Boolean(true).getClass().getName();

  public Object value="";

  public ValueUnion(){}

  public ValueUnion(Integer v){
    value = v;
  }

  public ValueUnion(Double v){
    value = v;
  }

  public ValueUnion(Boolean v){
    value = v;
  }

  public ValueUnion(int v){
    value=new Integer(v);
  }

  public ValueUnion(double v){
    value=new Double(v);
  }

  public ValueUnion(String v){
    value=new String(v);
  }

  public ValueUnion(boolean v){
    value=new Boolean(v);
  }

  public static Object getData(String valueStr, String className){
    Object value =null;
    if(className.equalsIgnoreCase(CLASS_BOOLEAN_NAME)) {
      value = new Boolean(valueStr);
    } else if(className.equalsIgnoreCase(CLASS_DOUBLE_NAME)) {
      value = new Double(valueStr);
    } else if(className.equalsIgnoreCase(CLASS_INTEGER_NAME)) {
      value = new Integer((int)(new Double(valueStr).doubleValue()));
    } else {
      value = valueStr;
    }
    return value;
  }

  public Object clone(){
    ValueUnion newUnion;

    if (value == null){
      newUnion = new ValueUnion();
      newUnion.value = null;
    }
    else{
      if (getClassName().equals(CLASS_INTEGER_NAME))
        newUnion = new ValueUnion(this.ival());
      else if (getClassName().equals(CLASS_BOOLEAN_NAME))
        newUnion = new ValueUnion(this.bval());
      else if (getClassName().equals(CLASS_DOUBLE_NAME))
        newUnion = new ValueUnion(this.dval());
      else
        newUnion = new ValueUnion(this.sval());
     }
    return newUnion;
  }

  //return different type of value
  public int ival(){
    //boolean --> int
    if (getClassName().equals(CLASS_BOOLEAN_NAME)){
      if(((Boolean)value).booleanValue() == true)
        return 1;
      else
        return 0;
    }

    //int --> int
    if (getClassName().equals(CLASS_INTEGER_NAME))
      return ((Integer)value).intValue();

    //double --> int
    if (getClassName().equals(CLASS_DOUBLE_NAME))
      return ((Double)value).intValue();

    //String type --> int
    if (getClassName().equals(CLASS_STRING_NAME)) {
      if(value.toString().length()==0) {
        return 0;
      }
      try {
        return new Integer(value.toString()).intValue();
      } catch (Exception e) {
        return 0;
      }
    }

    //other type --> 0
    return 0;

  }

  public double dval(){
    //double --> double
    if (getClassName().equals(CLASS_DOUBLE_NAME))
      return ((Double)value).doubleValue();

    //int --> double
    if (getClassName().equals(CLASS_INTEGER_NAME))
      return ((Integer)value).doubleValue();

    //boolean --> double
    if (getClassName().equals(CLASS_BOOLEAN_NAME)){
      if(((Boolean)value).booleanValue() == true)
        return 1.0;
      else
        return 0.0;
    }

    if (getClassName().equals(CLASS_STRING_NAME)) {
      try {
        Double d = new Double((String)value);
        return d.doubleValue();
      } catch ( Exception e) {
        return 0.0;
      }
    }

    //other type --> 0.0
    return 0.0;

  }

  public String sval(){
    //string --> string
    if (getClassName().equals(CLASS_STRING_NAME))
      return (String)value;

    //int --> string
    if (getClassName().equals(CLASS_INTEGER_NAME)){
      return ((Integer)value).toString();
    }



    //boolean --> string
    if (getClassName().equals(CLASS_BOOLEAN_NAME)){
      return ((Boolean)value).toString();
    }


    //double --> string
    if (getClassName().equals(CLASS_DOUBLE_NAME)){
      return ((Double)value).toString();
    }


    //other type --> ""
    return new String();
  }

  public boolean bval(){

    //int --> boolean
    if (getClassName().equals(CLASS_INTEGER_NAME)){
      if(((Integer)value).intValue() != 0)
        return true;
      else
        return false;
    }

    //double --> boolean
    if (getClassName().equals(CLASS_DOUBLE_NAME)){
      if(((Double)value).doubleValue() != 0.0)
        return true;
      else
        return false;
    }

    //boolean --> boolean
    if (getClassName().equals(CLASS_BOOLEAN_NAME))
      return ((Boolean)value).booleanValue();

    //string --> boolean
    if (getClassName().equals(CLASS_STRING_NAME)) {
      try {
        return new Boolean(value.toString()).booleanValue();
      } catch(Exception e) {
        return (((String)value).length()==0?false:true);
      }
    }

    //other type --> boolean
    return false;
  }

  public void setData(String valueStr, String className){
    if(className.equalsIgnoreCase(CLASS_BOOLEAN_NAME)) {
      value = new Boolean(valueStr);
    } else if(className.equalsIgnoreCase(CLASS_DOUBLE_NAME)) {
      value = new Double(valueStr);
    } else if(className.equalsIgnoreCase(CLASS_INTEGER_NAME)) {
      value = new Integer(valueStr);
    } else {
      value = valueStr;
    }
  }

  public String getClassName(){
    return value.getClass().getName();
  }
}
