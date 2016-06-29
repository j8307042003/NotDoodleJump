package Not.Doodle.Jump;

import java.util.LinkedList;
import java.lang.Class;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class GameObject {
	protected float x;
	protected float y;
	protected float velX;
	protected float velY;
	protected float width;
	protected float height;
	protected boolean isSleeping = false;
	protected ObjectType type ;
	protected Rect rect ;
	protected Paint paint ;
	protected boolean enRigid = false ;
	static public LinkedList <GameObject> gameObjList = new LinkedList<GameObject>() ; 
	
	public GameObject( float x, float y, float width, float height, ObjectType type ){
		this.x = x ;
		this.y = y ;
		this.velX = 0 ;
		this.velY = 0 ;
		this.type = type ;
		rect = new Rect();
		paint = new Paint();
		paint.setColor(Color.GRAY);
		this.width = width;
		this.height = height ;
		gameObjList.add(this);
	}
	
	public void setPosition( float x, float y ){
		this.x = x ;
		this.y = y ;
	}
	
	
	static public LinkedList<GameObject> getGameObjects() { return gameObjList; }
	static public void removeGameObj( GameObject obj) { gameObjList.remove(obj); }
	
	public void setX(float x ) { this.x = x ; }
	public void setY( float y ) { this.y = y ; } 
	public float getX() { return x ; }
	public float getY(){ return y; }
	public void getPosition() { ;}
	
	public float getHeight(  ) { return this.height ; }
	public float getWidth() { return this.width ; }
	public void setHeight(float height) { this.height = height ;  }
	public void setWidth( float width ) {this.width = width ; }
	
	public void setVelocity( float x, float y ) { this.velX = x ; this.velY = y ; }
	
	public void setVelX( float x ) { this.velX = x ; }
	public void setVelY( float y ) { this.velY = y ; }
	public float getVelX() { return this.velX ;}
	public float getVelY() { return this.velY; }
	public float getVel() { return (float) Math.sqrt(Math.pow(velX, 2)+ Math.pow(velY, 2) ); }
	
	
	public void applyVelocity( float deltaTime ) {
		x += this.velX * deltaTime ;
		y += this.velY * deltaTime ; 
	}
	
	public Rect getBound() { return new Rect((int) x,(int)y,(int)(x+width),(int) (y-height) ); }
	
	public Rect getBoundTop() { return new Rect((int) (x+width*0.05),(int)y,(int)(x+width*0.95),(int)(y - height/2) );}
	public Rect getBoundBottom() { return new Rect( (int) (x+width*0.05),(int)(y - (3*height)/4),(int)(x+width*0.95),(int) (y - height) ) ; }
	public Rect getBoundRight() { return new Rect((int) (x+width*0.8),(int)(y-height*0.1),(int)(x+width),(int) (y - (height)*(0.9)) );}
	public Rect getBoundLeft() { return new Rect( (int) x,(int)(y-height*0.1),(int)(x+width*0.2),(int) (y - (height)*(0.9)) ) ; }
	
	public void drawBound( Canvas canvas, int color  ) {  
		int originColor = paint.getColor();
		paint.setColor(color);
		Rect boundRect = getBoundBottom() ;
		boundRect.top = canvas.getHeight() - boundRect.top ;
		boundRect.bottom = canvas.getHeight() - boundRect.bottom ;
		
		canvas.drawRect(boundRect, paint) ;
		paint.setColor(originColor) ;
	}
	
	public ObjectType getObjType() { return type ; }
	
	abstract void awake();
	abstract void sleep();
	abstract void update(float deltaTime) ;
	abstract void render(Canvas canvas ) ;
	abstract void onCollider( GameObject obj );
	abstract void onColliderTop( GameObject obj ) ;
	abstract void onColliderBottom( GameObject obj ) ;
	
}
