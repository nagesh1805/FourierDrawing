import java.util.ArrayList;

public class ComplexNumber 
{
	
	private double r_part;
	private double i_part;
	
	public ComplexNumber(double a, double b) {
		r_part=a;
		i_part=b;
	}
	public ComplexNumber(double a) {
		r_part=a;
		i_part=0;
	}
	public ComplexNumber() {
		this(0,0);
	}
	
	public ComplexNumber add(ComplexNumber other) {
		
		return new ComplexNumber(this.r_part + other.r_part, this.i_part + other.i_part);
	}
	
	
	public ComplexNumber mul(ComplexNumber other) {
		
		return new ComplexNumber(this.r_part*other.r_part - this.i_part*other.i_part, this.r_part*other.i_part +  this.i_part*other.r_part);
	}
	public ComplexNumber mul(double r) {
		return new ComplexNumber(this.r_part*r,this.i_part*r);
		
	}
	
	public ComplexNumber sub(ComplexNumber other) {
		
		return new ComplexNumber(this.r_part - other.r_part, this.i_part - other.i_part);
	}
	
	public double get_polar_r() {
		return Math.sqrt(r_part*r_part + i_part*i_part);
	}
	
	public double get_polar_theta() {
		return Math.atan2(i_part,r_part);
	}
	
	public double arg() {
		if (r_part > 0) {
			return Math.atan(i_part/r_part);
		}
		else if (r_part < 0 && i_part >= 0) {
			return Math.PI + Math.atan(i_part/r_part);
		}
		
		else if (r_part < 0 && i_part < 0) {
			return -Math.PI + Math.atan(i_part/r_part);
		}
		
		else if (r_part == 0 && i_part > 0) {
			return Math.PI/2;
		}
		
		else {
			return -Math.PI/2 ;
		}
	}
	
	public ComplexNumber rotate(double t) {
		ComplexNumber z=new ComplexNumber();
		z.r_part=this.get_polar_r()*(Math.cos(this.arg()+t));
		z.i_part=this.get_polar_r()*(Math.sin(this.arg()+t));
		return z;
		
	}
		

	public double getR_part() {
		return r_part;
	}

	public void setR_part(double r_part) {
		this.r_part = r_part;
	}

	public double getI_part() {
		return i_part;
	}

	public void setI_part(double i_part) {
		this.i_part = i_part;
	}
	
	public String toString() {
		String s;
		s=r_part+"+i"+i_part;
		return s;
	}
	
	public static  ComplexNumber cis(double t) {
		ComplexNumber z= new ComplexNumber(Math.cos(t),Math.sin(t));
		return z;
		
	}
	
	public static  ComplexNumber pow(ComplexNumber z,int n) {
		if (n==0) { 
			ComplexNumber zz= new ComplexNumber(1,0);
			return zz;
		}

		else {
			ComplexNumber zz=pow(z,n/2);
			if(n%2==0) {
				zz=zz.mul(zz);
			}
			else {
				zz=zz.mul(zz).mul(z);
			}
			return zz;
		}
		
		
	}
	
	public static Matrix DFT(int n) {
		Matrix D=new Matrix(n,n);
		ComplexNumber wn=cis(-2*Math.PI/n);
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				D.set_ijth(i, j, pow(wn,i*j));
			}
		}
		return D;
	}
	// Fourier coefficients for complex numbers
	public static ArrayList<ComplexNumber> FourierCCoefs(ArrayList<ComplexNumber> f) throws Exception{
		Matrix ff = new Matrix(f);
		Matrix dft=DFT(f.size());

		Matrix fc=dft.mul(ff);
		ArrayList<ComplexNumber> fcs = new ArrayList<ComplexNumber>();
		for(int i=0;i<f.size();i++) {
			fcs.add(fc.get_ijth(i, 0));
		}
		
		return fcs;
				
	}
	// Fourier coefficients for real numbers
	public static ArrayList<ComplexNumber> FourierDCoefs(ArrayList<Double> f) throws Exception{
		ArrayList<ComplexNumber> ff = new ArrayList<ComplexNumber>();
		for(int i=0;i<f.size();i++) {
			ff.add(new ComplexNumber(f.get(i)));
		}
		
		return FourierCCoefs(ff);
				
	}
	
	public boolean compareTo(ComplexNumber other) {
		return this.get_polar_r()> other.get_polar_r();
	}
	

}
