/**
 * Description: Provide a thread for cleaning the InputStream
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar. 22, 2003
 * Xiaofeng Xie    Apr. 12, 2003    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.system;

import java.io.*;

public class ProcStreamReader extends Thread {
   private InputStream in;
   private StringBuffer data;
   public ProcStreamReader(InputStream argIn) {
        in = argIn;
        data = new StringBuffer();
   }

   public void run() {
     try {
       BufferedReader reader = new BufferedReader(new InputStreamReader(in));
       String line = null;
       while((line = reader.readLine())!= null) {
         data.append(line);
       }
     } catch(IOException ioe) {
        ioe.printStackTrace();
     }
   }
   public String getData() {
      return data.toString();
   }

}
