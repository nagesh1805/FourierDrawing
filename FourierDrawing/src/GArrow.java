import java.awt.Color;
import java.util.ArrayList;
import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GMath;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;

public class GArrow extends GCompound {
	
	public GArrow(double x1,double y1, double x2, double y2,double stroke) {
		makeArrow(x1,y1,x2,y2,stroke);
		is_filled=false;
	}
	public GArrow(double x1,double y1, double x2, double y2) {
		makeArrow(x1,y1,x2,y2,1);  //default stroke is 1
		is_filled=false;
	}
		
	private void makeArrow(double x1,double y1, double x2, double y2,double stroke) {
		arrow=new GPolygon();
		Color clr1=Color.BLACK;	
		start= new GPoint(x1,y1);
		end= new GPoint(x2,y2);
		double us=stroke;
		if(stroke<=2) us=2;
		else if(stroke<=4) us=(3.0/4)*stroke;
		else if (stroke<=6) us=(3.0/5)*stroke;
		else us=stroke;
		
		double d = GMath.distance(x1, y1, x2, y2);
		double arrow_head_len=0.1*d;
		double shaft_len=d-arrow_head_len;
		
		double t=Math.atan2(y1-y2,x1-x2);
		
		double xx1=x1+(0.5)*stroke*Math.cos(t+Math.PI/2);
		double yy1=y1+(0.5)*stroke*Math.sin(t+Math.PI/2);
		
		double xx2=xx1+shaft_len*Math.cos(t+Math.PI);  
		double yy2=yy1+shaft_len*Math.sin(t+Math.PI);
		
		double xx3=xx2+us*Math.cos(t+Math.PI/2);
		double yy3=yy2+us*Math.sin(t+Math.PI/2);
		
		double xx4=x2;
		double yy4=y2;
		
		double xx5=xx2-(stroke+us)*Math.cos(t+Math.PI/2);
		double yy5=yy2-(stroke+us)*Math.sin(t+Math.PI/2);;
		
		double xx6=xx2-stroke*Math.cos(t+Math.PI/2);
		double yy6=yy2-stroke*Math.sin(t+Math.PI/2);
		
		double xx7=xx6+shaft_len*Math.cos(t);
		double yy7=yy6+shaft_len*Math.sin(t);	
		
		arrow.addVertex(xx1, yy1);
		arrow.addVertex(xx2, yy2);
		arrow.addVertex(xx3, yy3);
		arrow.addVertex(xx4, yy4);
		arrow.addVertex(xx5, yy5);
		arrow.addVertex(xx6, yy6);
		arrow.addVertex(xx7, yy7);
		
		add(arrow);
		
		this.stroke=stroke;

		arrow.setColor(color);

		if(is_filled) {
			this.setFilled(true);
			arrow.setColor(clr1);
			arrow.setFillColor(fill_color);
			color=clr1;
		}
		
	}
	
	public GPoint getStartPoint() {
		return start;
	}
	public GPoint getEndPoint() {
		return end;
	}
	public double getStroke() {
		return stroke;
	}
	
	public Color getColor() {
		return color;
	}
	public Color getFillColor() {
		return fill_color;
	}

	public void setStroke(double stroke){
	
		double x1,y1,x2,y2;
		Color clr1=arrow.getColor();
		Color clr2=arrow.getFillColor();
		x1=start.getX();
		y1=start.getY();
		x2=end.getX();
		y2=end.getY();
		this.remove(arrow);
		makeArrow(x1,y1,x2,y2,stroke);	
		this.setColor(clr1);
		this.setFillColor(clr2);	
	}
	
	public void setStartPoint(double x1, double y1) {
		double x2,y2;
		Color clr1=arrow.getColor();
		Color clr2=arrow.getFillColor();
		x2=end.getX();
		y2=end.getY();
		this.remove(arrow);
		makeArrow(x1,y1,x2,y2,stroke);	
		this.setColor(clr1);
		this.setFillColor(clr2);

	}
	
	public void setEndPoint(double x2, double y2) {
		double x1,y1;
		Color clr1=this.getColor();
		Color clr2=this.getFillColor();
		x1=start.getX();
		y1=start.getY();
		this.remove(arrow);
		makeArrow(x1,y1,x2,y2,stroke);
		this.setColor(clr1);
		this.setFillColor(clr2);
		
	}
	
	public void setFilled(boolean flag) {
		arrow.setFilled(flag);
		is_filled=flag;
	}
	public void setFillColor(Color clr) {
		if(is_filled) {
			arrow.setFillColor(clr);
			this.is_filled=true;
			this.fill_color=clr;
		}

	}
	public void setColor(Color clr) {
		arrow.setColor(clr);
		this.color=clr;
	}
	public void setFullColor(Color clr) {
		arrow.setColor(clr);
		arrow.setFillColor(clr);
		this.color=clr;
		this.fill_color=clr;
		this.is_filled=true;
	}
	
	private GPolygon arrow;
	private GPoint start, end;
	private double stroke;
	private Color color,fill_color;
	private boolean  is_filled;

			
}
