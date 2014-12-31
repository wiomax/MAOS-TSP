/**
 * Description: operations for matrix calculation.
 *
 * @ Author        Create/Modi     Note
 * Li Zhao         Nov 15, 2000
 * Xiaofeng Xie    Feb 22, 2001
 *
 * @version 1.0
 */

package Global.math;


public class MatrixOperation {

    public static double eps = 1e-5;//The minimum allowable error

    public static boolean isMatrixSymmetric(int[][] distMatrix) {
      for (int i=0; i<distMatrix.length; i++) {
        for (int j = i + 1; j < distMatrix.length; j++) {
          if (distMatrix[i][j] != distMatrix[j][i]) {
            return false;
          }
        }
      }
      return true;
    }

/**
  * Choleski decomposition algorithm to solve the linear equations.
  * The matrix A must be positively defined.
  * Improvement: The storing method of matrix A can be improved.
  */
  public static void choleski(double[][] A,double[] b, double[] x){
    int index,n;
    double sum;
    double[] L;

    n = b.length;
    L = new double[n*(n+1)/2];

    //Calculate the lower triangle matrix L to make A=LL'.
    for(int j=0;j<n;j++){
      index=(j+1)*j/2+j;
      sum = 0.0;
      for(int k=0;k<j;k++)  sum += L[(j+1)*j/2+k]*L[(j+1)*j/2+k];
      sum = A[j][j]-sum;
      if(sum<=0.0){
        x[0]=Double.POSITIVE_INFINITY;//Non-solution
        return;
      }
      L[index]=Math.sqrt(sum);
      for(int i=j+1;i<n;i++){
        sum=0;
        for(int k=0;k<j;k++) sum += L[(i+1)*i/2+k]*L[(j+1)*j/2+k];
        L[(i+1)*i/2+j] = (A[i][j]-sum)/L[index];
      }
    }

    x[0] = b[0]/L[0];
    for(int i=1;i<n;i++){
      sum = 0.0;
      for(int k=0;k<i;k++)
        sum += L[(i+1)*i/2+k]*x[k];
      x[i] = (b[i]-sum)/L[(i+1)*i/2+i];
    }

    x[n-1] = x[n-1]/L[n*(n-1)/2+n-1];
    for(int i=n-2;i>=0;i--){
      sum=0.0;
      for(int k=i+1;k<n;k++)  sum += L[(k+1)*k/2+i]*x[k];
      x[i] = (x[i]-sum)/L[(i+1)*i/2+i];
    }
  }

/**
  * Gauss elimination algorithm to solve the linear equations.
  * The main element for elimination is selected from the present column.
  */
  public static void gauss(double[][] A0,double[] b0,double[] x){
    double[][] A;
    double[] b;
    double a;
    int i,j,k,l,n;

    n = b0.length;
    A = new double[n][n];
    b = new double[n];
    System.arraycopy(b0,0,b,0,n);
    for(i=0;i<n;i++)
      System.arraycopy(A0[i],0,A[i],0,n);

      k=0;
      while(k<n-1){
        a=A[k][k];
        l=k;
        i=k+1;
        while(i<n){
          if(Math.abs(A[i][k])>Math.abs(a)){
            a=A[i][k];
            l=i;
          }
          i++;
        }
        if(a==0){//Improvement: <eps
          x[0]=Double.POSITIVE_INFINITY;//Non-solution
        return;
      }else{
        if(l!=k){
          for(j=k;j<n;j++){
            a=A[l][j];
            A[l][j]=A[k][j];
            A[k][j]=a;
          }
          a=b[l];
          b[l]=b[k];
          b[k]=a;
        }
      }
      for(j=k+1;j<n;j++)
        A[k][j]=A[k][j]/A[k][k];
      b[k]=b[k]/A[k][k];
      for(j=k+1;j<n;j++)
        for(i=k+1;i<n;i++)
      A[i][j]=A[i][j]-A[i][k]*A[k][j];
      for(i=k+1;i<n;i++)
        b[i]=b[i]-A[i][k]*b[k];
      k++;
    }
    if(A[n-1][n-1]==0){//Improvement: <eps
      x[0]=Double.POSITIVE_INFINITY;//Non-solution
      return;
    }
    x[n-1]=b[n-1]/A[n-1][n-1];
    for(i=n-2;i>=0;i--){
      a=0;
      for(j=i+1;j<n;j++)
        a=a+A[i][j]*x[j];
      x[i]=b[i]-a;
    }
  }

/**
  * Gauss-Jordon invertible algorithm.
  * The main element for elimination is selected from rest columns and rows.
  */
  public static double[][] inv(double[][] a0){
    double[][] a;
    double max;
    int[] is,js;
    int n;

    n = a0.length;
    a=new double[n][n];
    is=new int[n];
    js=new int[n];
    for(int i=0;i<n;i++)
      System.arraycopy(a0[i],0,a[i],0,n);

    for(int k=0;k<n;k++){
      max=0;
      for(int i=k;i<n;i++) {
        for(int j=k;j<n;j++){
          if(Math.abs(a[i][j])>Math.abs(max)){
            max=a[i][j];
            is[k]=i;
            js[k]=j;
          }
        }
      }
      if(max==0){//Improvement: <eps
        a[0][0]=Double.POSITIVE_INFINITY;//Non-solution
        return a;
      }
      if(is[k]!=k){
        for(int j=0;j<n;j++){
          double h=a[k][j];
          a[k][j]=a[is[k]][j];
          a[is[k]][j]=h;
        }
      }
      if(js[k]!=k){
        for(int i=0;i<n;i++){
          double h=a[i][js[k]];
          a[i][js[k]]=a[i][k];
          a[i][k]=h;
        }
      }
      a[k][k]=1/a[k][k];
      for(int i=0;i<n;i++){
        if(i!=k) a[i][k]=-a[i][k]*a[k][k];
      }
      for(int i=0;i<n;i++){
        if(i!=k){
          for(int j=0;j<n;j++){
            if(j!=k) a[i][j]=a[i][j]+a[i][k]*a[k][j];
          }
        }
      }
      for(int j=0;j<n;j++)
        if(j!=k) a[k][j]=a[k][j]*a[k][k];
      }
      for(int k=n-1;k>=0;k--){
        for(int j=0;j<n;j++){
          if(js[k]!=k){
            double h=a[k][j];
            a[k][j]=a[js[k]][j];
            a[js[k]][j]=h;
          }
        }
        for(int i=0;i<n;i++){
          if(is[k]!=k){
            double h=a[i][k];
            a[i][k]=a[i][is[k]];
            a[i][is[k]]=h;
          }
        }
      }
      return a;
    }

/**
  * Jaccobi algorithm to get the  eigenvalues and eigenvetors of Matrix.
  * This algorithm is applied to the Symmetry matrix.
  * Improvement: The storing method of matrix a0 can be improved.
  */
  public static double[][] jaccobi(double[][] a0,double[] u){
    int n;
    double max,p,q,w;
    double[][] a;
    double[][] s;
    double[][] r;

    n = a0.length;
    a = new double[n][n];
    s = new double[n][n];
    r = new double[n][n];
    for(int i=0;i<n;i++)
    System.arraycopy(a0[i],0,a[i],0,n);

    for(int k=0;k<n;k++){
      for(int j=0;j<n;j++){
        if(k==j){
          s[k][j]=1;
          r[k][j]=1;
        }else{
          s[k][j]=0;
          r[k][j]=0;
        }
      }
    }
    while(true){
      max=a[0][0];
      int i=0;
      int j=0;
      for(int k=1;k<n;k++){
        for(int l=0;l<k;l++){
          if(Math.abs(a[k][l])>Math.abs(max)){
            max=a[k][l];
            i=k;
            j=l;
          }
        }
      }
      if(max<eps) break;

      p=-a[i][j];
      q=(a[j][j]-a[i][i])/2;
      w=sign(q)*p/Math.sqrt(p*p+q*q);
      p=w/Math.sqrt(2*(1+Math.sqrt(1-w*w)));
      q=Math.sqrt(1-p*p);

      for(int k=0;k<n;k++){
        if(k!=i&&k!=j){
          double x = a[i][k];
          a[i][k] = x*q+a[j][k]*p;
          a[j][k] = -x*p+a[j][k]*q;
          a[k][i] = a[i][k];
          a[k][j] = a[j][k];
        }
      }
      double x = a[i][i];
      double y = a[j][j];
      a[i][i] = x*q*q+y*p*p+a[i][j]*w;
      a[j][j] = x*p*p+y*q*q-a[i][j]*w;
      a[i][j] = (y-x)/2*w+a[i][j]*(2*q*q-1);
      a[j][i] = a[i][j];

      r[i][i]=q;
      r[i][j]=-p;
      r[j][i]=p;
      r[j][j]=q;
      s=matrixMultiMatrix(s,r);
      r[i][i]=1;
      r[i][j]=0;
      r[j][i]=0;
      r[j][j]=1;
    }
    for(int k=0;k<n;k++)
      u[k]=a[k][k];
    return s;
  }

  //Return the flag of x.
  private static double sign(double x){
    if(x>=0)
      return 1;
    else
      return -1;
  }

/**
  * Calculate internal product of two matrixes: s(NxM).r(NxM).
  */
  public static double matrixInterProduct(double[][] s,double[][] r){
          int n,m;
          double sum;

    n = s.length;
    m = s[0].length;
    if(n!=r.length){
          sum = Double.POSITIVE_INFINITY;//Non-solution
         return sum;
    }
          if(m!=r[0].length){
          sum = Double.POSITIVE_INFINITY;//Non-solution
         return sum;
    }

    sum = 0;
    for(int i=0;i<n;i++){
         for(int j=0;j<m;j++)
                  sum += s[i][j]*r[i][j];
    }
    return sum;
  }

/**
  * Calculate the result of matrix multiply : s(NxM)*r(MxL).
  */
  public static double[][] matrixMultiMatrix(double[][] s,double[][] r){
    int n,m,l;
    double[][] t;

    n = s.length;
    m = s[0].length;
    l = r[0].length;
    t = new double[n][l];
    if(m!=r.length){
      t[0][0] = Double.POSITIVE_INFINITY;//Non-solution
      return t;
    }
    for(int i=0;i<n;i++){
      for(int j=0;j<l;j++){
        t[i][j]=0;
        for(int k=0;k<m;k++)
          t[i][j] += s[i][k]*r[k][j];
      }
    }
    return t;
  }

/**
  * Calculate the result of matrix multiplied by vector : s(NxM)*r(M).
  */
  public static double[] matrixMultiVector(double[][] s,double[] r){
    int n,m;
    double[] t;

    n = s.length;
    m = s[0].length;
    t = new double[n];
    if(m!=r.length){
      t[0] = Double.POSITIVE_INFINITY;//Non-solution
      return t;
    }
    for(int i=0;i<n;i++){
      t[i]=0;
      for(int k=0;k<m;k++)
        t[i] += s[i][k]*r[k];
    }
    return t;
  }

/**
  * Calculate the internal product of two vectors: a.b.
  */
 public static double vectorInterProduct(double [] a,double [] b){
    double sum;
    int n;

    n = a.length;
    if (n!=b.length){
       sum = Double.POSITIVE_INFINITY;//Non-solution
       return sum;
    }
    sum=0.0;
    for(int i=0;i<n;i++) sum += a[i]*b[i];
    return sum;
 }

/**
  * Return the transposed matrix.
  */
  public static double[][] transPose(double[][] a){
    double[][] b;
    int n,m;

    n = a.length;
    m = a[0].length;
    b = new double[m][n];
    for(int i=0;i<n;i++){
      for(int j=0;j<m;j++)
        b[j][i] = a[i][j];
    }
    return b;
 }

/**
  * Return the 2-normal number of the matrix a.
  */
  public static double normal(double[][] a){
    double[] s;

    s = singular(a);
    return s[0];
 }

/**
  * Return the condition number of the input matrix.
  */
  public static double condition(double[][] a){
    double[] s;
    int n,m;

    s = singular(a);
    n = s.length;
    m = n-1;
    for(int i=0;i<n;i++){
       if(s[i]<1e-100){//Improvement: <eps
          m = i-1;
          break;
       }
    }
    if(m>0)
      return s[0]/s[m];
    else
      return 1;
  }

/**
  * Calculate the singular values of symmetry matrix.
  */
  public static double[] singular(double[][] a){
    double[][] b;
    double[] s;
    int n;

    b = transPose(a);
    b = matrixMultiMatrix(b,a);
    n = b.length;
    s = new double[n];
    b = jaccobi(b,s);

    int m = n-1;
    while(m>0){
       int l = 0;
       for(int j=1;j<=m;j++){
          if(s[j]>s[j-1]){
                  double x = s[j];
                  s[j] = s[j-1];
                  s[j-1] = x;
                  l = j-1;
          }
       }
       m = l;
     }
     for(int i=0;i<n;i++)
       s[i] = Math.sqrt(s[i]);
     return s;
  }
}

