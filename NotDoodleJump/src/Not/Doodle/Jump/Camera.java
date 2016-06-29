package Not.Doodle.Jump;

import android.graphics.Canvas;
import android.util.Log;

public class Camera {
	
	public int x ;
	float y ;
	private float velY = 0;
	public int width = scene.gameWidth;
	public int height= scene.gameHeight;
	boolean running = true ;
	final float  hz = 2f ;
	final float rate = 0.999999f;
	final float omega = (float)(2 * hz *Math.PI);
	final float zeta = (float )( Math.log(1-rate)/( -omega*(1/hz)) );
	
	final float maxSpeed =0;
	
	GameObject target = null ;
	boolean sw = false ;
	
	public Camera( int x, int y ) {
		this.x = x ;
		this.y = y ;
		
	}
	
	public int getY() { return ( int )this.y; }
	public void updateWidth( int width ) { this.width = width ; }
	public void updateHeight( int height ) { this.height = height ; }
	public void setTarget( GameObject obj ) { 
		velY = 0 ;
		if ( this.target == null ) this.target = obj ;
		else if ( this.target.getObjType() != ObjectType.Player ){
			if ( this.target.getY() < obj.getY() ){
				this.target = obj ;
			}
		}
		else this.target = obj ;
	}
	public void stop() { running = false ;  }
	public void restart() { running = true ;
		
		x= 0 ;
		y = 0 ;
		velY = 0 ;
		target = null ;
	
	}
	
	public void update( float deltaTime) {
		if ( this.target == null || !running){
			
			return ;
		}
		
		if ( target.getObjType() == ObjectType.Player ){
			y = (int) (target.getY() -(float) height/2) ;
		}else if ( true ){ // higher than previous Y 
			//Spring( (target.getY() -(float) (height)/4), (float)scene.timeStamp/10000 );
			SpringJNI( (target.getY() -(float) (height)/4), deltaTime, omega, zeta);
		}
		
	}
	
	/*
	  y			- value             (input/output)
	  velY		- velocity          (input/output)
	  targetY	- target value      (input)
	  zeta		- damping ratio     (input)
	  omega		- angular frequency (input)
	  h			- time step         (input)
	*/
	void Spring
	(
	   float targetY, 
	   float h
	)
	{
	
	  final double f = 1.0f + 2.0f * h * zeta * omega;
	  final double oo = omega * omega;
	  final double hoo = h * oo;
	  final double hhoo = h * hoo;
	  final double detInv = 1.0f / (f + hhoo);
	  final double detY = f * y + h * velY + hhoo * targetY;
	  final double detV = velY + hoo * (targetY - y);
	  y = (float)(detY * detInv);
	  velY = (float)(detV * detInv);
	}
	
	
	public native void SpringJNI( float targetY, float h, float omega, float zeta) ; 
	
	

}
