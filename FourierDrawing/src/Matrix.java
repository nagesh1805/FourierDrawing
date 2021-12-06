import java.util.ArrayList;

public class Matrix {
	
	public Matrix(int m,int n) {
		nrow=m;
		ncol=n;
		arr=new ComplexNumber[m][n];
	}
	public Matrix(ArrayList<ComplexNumber> f) {
		nrow=f.size();
		ncol=1;
		arr=new ComplexNumber[nrow][1];
		for(int i=0;i<f.size();i++) {
			arr[i][0]=f.get(i);
		}
	}
	public ComplexNumber get_ijth(int i,int j) {
		return arr[i][j];
	}
	public void  set_ijth(int i, int j, ComplexNumber z) {
		arr[i][j]=z;
	}
	public int getNrows() {
		return nrow;
	}
	public int getNcols() {
		return ncol;
	}
	
	public String toString() {
		String s="";
		for(int i=0;i<this.nrow;i++) {
			for(int j=0;j<this.ncol;j++) {
				s=s+this.get_ijth(i, j)+" , ";
			}
			s=s+"\n";
		}
		return s;
	}
	public Matrix scalar_mul(ComplexNumber c)  {
		Matrix C= new Matrix(nrow,ncol);
		for(int i=0;i<nrow;i++) {
			for(int j=0;j<ncol;j++) {
				C.set_ijth(i, j, this.get_ijth(i, j).mul(c));
			}
		}
		return C;
	}
	public Matrix add(Matrix B) throws Exception {
		if(this.nrow!=B.getNrows() || this.ncol != B.getNcols()) throw new Exception("Not Compatable for addition");
		Matrix C= new Matrix(nrow,ncol);
		for(int i=0;i<nrow;i++) {
			for(int j=0;j<ncol;j++) {
				C.set_ijth(i, j, this.get_ijth(i, j).add(B.get_ijth(i, j)));
			}
		}
		return C;
	}
	
	public Matrix mul(Matrix B) throws Exception {
		if(this.ncol!=B.getNrows()) throw new Exception("Not Compatable for multiplication");
		Matrix C= new Matrix(nrow,B.getNcols());
		for(int i=0;i<nrow;i++) {
			for(int j=0;j<B.getNcols();j++) {
				ComplexNumber s=new ComplexNumber(0,0);
				for(int k=0;k<B.getNrows();k++) {
					s=s.add(this.get_ijth(i, k).mul(B.get_ijth(k, j)));
				}
				C.set_ijth(i, j, s);
			}
		}
		return C;
	}
	
	
	
	private int nrow,ncol;
	private ComplexNumber[][] arr;

}
