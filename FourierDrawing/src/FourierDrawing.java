import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import acm.graphics.GLine;
import acm.graphics.GMath;
import acm.graphics.GOval;
import acm.graphics.GPen;
import acm.graphics.GPoint;
import acm.graphics.GTurtle;
import acm.program.GraphicsProgram;

public class FourierDrawing extends GraphicsProgram {
	
	public void init() {
		setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
		setBackground(Color.BLACK);

	}
	
	public void run() {
		infile=new File("/Users/nagi/eclipse-workspace/FourierDrawing/res/srinivas_ramanujan.txt");
		
		ArrayList<ComplexNumber> f = new ArrayList<ComplexNumber>();

		try {
			scn = new Scanner(infile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("bad..");
		}
		int count=0;

		while(scn.hasNext()) {

			String ln=scn.nextLine();
			String w1,w2;
			w1=ln.substring(0,ln.indexOf(','));
			w2=ln.substring(ln.indexOf(',')+1); 
			double rp=Double.parseDouble(w1);
			double ip=Double.parseDouble(w2);
			if(count%1==0)
				f.add(new ComplexNumber(rp,ip));
			count++;
		}
		
			
		try {
			f=ComplexNumber.FourierCCoefs(f);
			ComplexNumber _1byN=new ComplexNumber(1.0/f.size(),0);
			for(int i=0;i<f.size();i++) {
				f.set(i, f.get(i).mul(_1byN));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<ComplexNumber, Integer> cmap=new HashMap<ComplexNumber,Integer>();
		for(int i=0;i<f.size();i++) {
			cmap.put(f.get(i), i);
		}
	    f=ComplexSort(f,0,f.size());
		arrows = new ArrayList<GArrow>();
	    circles = new ArrayList<GOval>();
		int N=f.size();
		ComplexNumber v=new ComplexNumber(getWidth()/2,getHeight()/2);
		for (int i=0;i<N;i++) {	 	
			GArrow arrow = new GArrow(v.getR_part(),v.getI_part(),v.getR_part()+f.get(i).getR_part(),v.getI_part()+f.get(i).getI_part(),0.5);
			GOval circle = new GOval(v.getR_part()-f.get(i).get_polar_r(),v.getI_part()-f.get(i).get_polar_r(),2*f.get(i).get_polar_r(),2*f.get(i).get_polar_r());
			arrow.setFilled(true);
			arrow.setFullColor(Color.ORANGE);
			circle.setColor(Color.DARK_GRAY);
			arrows.add(arrow);
			circles.add(circle);
            v=v.add(f.get(i));   	
			add(circle);
			add(arrow);
		}
	    GPen pen = new GPen();
	    pen.setColor(Color.WHITE);
	    pen.setSpeed(0.8);
	    pen.showPen();
	    add(pen,v.getR_part(),v.getI_part());
	    pause(1000);
		for(int t=0;t<N;t++) {
		ComplexNumber vv=new ComplexNumber(getWidth()/2,getHeight()/2);
			for (int i=0;i<N;i++) {
	    		vv=vv.add(f.get(i).rotate((2*Math.PI*cmap.get(f.get(i))*t)/N));
	    		arrows.get(i).setEndPoint(vv.getR_part(),vv.getI_part());
	    		if(i<(N-1)) {
			        arrows.get(i+1).setStartPoint(vv.getR_part(),vv.getI_part());
			    	circles.get(i+1).setLocation(vv.getR_part()-f.get(i+1).get_polar_r(),vv.getI_part()-f.get(i+1).get_polar_r());
	    		}
			}	

			pen.drawLine(vv.getR_part()-v.getR_part(),vv.getI_part()-v.getI_part());
    		v=vv;
		}
		//pause(10);
		for(int i=0;i<N;i++) {
			remove(arrows.get(i));
			remove(circles.get(i));
		}
		pen.setSpeed(1);
		pen.hidePen();
		
	}


	public ArrayList<ComplexNumber> ComplexSort(ArrayList<ComplexNumber> f, int start,int end){
		if((end-start)<=1) {
			ArrayList<ComplexNumber> f0= new ArrayList<ComplexNumber>();
			if((end-start)==1) {
				f0.add(f.get(start));
			}
			return f0;
		}
		else {
			int mid=start+(end-start)/2;
			ArrayList<ComplexNumber> f1=ComplexSort(f,start,mid);
			ArrayList<ComplexNumber> f2=ComplexSort(f,mid,end);
			ArrayList<ComplexNumber> ff= new ArrayList<ComplexNumber>();
			int lp=0,rp=0;
			while(lp<f1.size() && rp<f2.size()) {
				if(f1.get(lp).get_polar_r()>f2.get(rp).get_polar_r()) {
					ff.add(f1.get(lp));
					lp++;
				}
				else {
					ff.add(f2.get(rp));
					rp++;
				}
			}
			while(lp<f1.size()) {
				ff.add(f1.get(lp));
				lp++;
			}
			while(rp<f2.size()) {
				ff.add(f2.get(rp));
				rp++;
			}
			return ff;
		}
		
	}
	ArrayList<GArrow> arrows;
	ArrayList<GOval> circles ;
	File infile,outfile;
	Scanner scn;
	public static final double PI=3.14159;
	private static final int APPLICATION_WIDTH=1920;
	private static final int APPLICATION_HEIGHT=1080;

}
