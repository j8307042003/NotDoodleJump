package Not.Doodle.Jump;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;

public class Player extends GameObject{

	final float speed = 120f ;
	public final static float jumpSpeed = 200f ;
	
	private boolean jumping = true ;
	private boolean falling = false ;
	
	LinkedList<Bullet> bullets = new LinkedList<Bullet>() ;
	
	Bitmap [] bitmapLR = new Bitmap[3]; // 0-> left; 1 -> right 2-> shot;
	Camera cam ;
	float timer = 0 ;
	float shotCD = .25f;
	boolean fire = false ;
	boolean death = false ;
	int preLR = 0 ;
	int iLR =0 ;
	
	
	
	public Player(float x, float y,float width, float height, ObjectType type) {
		super(x, y, width, height, type);
		// TODO Auto-generated constructor stub
		for( int i = 0 ; i < 15 ; i++ ) {
			bullets.add(new Bullet( 0,0, ObjectType.Bullet ));
			bullets.get(i).isSleeping = true ;
		}
		this.bitmapLR[0] = ResourceManager.getBitmapByID(R.drawable.doodlel1) ;
		this.bitmapLR[1] = ResourceManager.getBitmapByID(R.drawable.doodler2) ;
		this.bitmapLR[2] = ResourceManager.getBitmapByID(R.drawable.doodles) ;
		paint.setColor(Color.BLUE);
		enRigid = true ;
	}

	
	void update(float deltaTime) {
		if (death) return ;
		
		//if ( Input.up && this.velY <= 0 ) this.velY += 50 ;
		if ( this.velY > 0 ) this.enRigid = false ;
		else this.enRigid = true ;
		
		
		if ( Input.right ){
			this.x += this.speed * deltaTime ;
			iLR = 1 ;
		}
		else if ( Input.left ) {
			this.x -= this.speed * deltaTime ;
			iLR = 0 ;
		}
		
		if ( Input.up && !fire) {
			Bullet bullet = getBullet() ;
			bullet.isSleeping = false ;
			bullet.setPosition(x + (width/2), y) ;
			ResourceManager.PlaySound(R.raw.shot, 0);
			preLR = iLR ;
			iLR = 2 ;
			fire = true ;
		}
		
		if ( fire ){
			if ( timer < .1f )
				iLR = 2 ;
			else iLR = preLR ;
			timer+=deltaTime;
			if ( timer > shotCD ) {
				timer = 0 ;
				fire = false ;
			}
		}
		
		//Log.d("time", String.format("%f", deltaTime*speed));
	}
	
	public void takeCamera( Camera cam  ) {
		this.cam = cam ;
	}

	public void takeDamage() {
		this.enRigid = false ;
		this.death = true ;
		scene.endGame();
	}
	
	private Bullet getBullet() {
		Bullet tmp = null ;
		for ( int i = 0 ; i < bullets.size() ; i++ ) {
			if( bullets.get(i).isSleeping )
				tmp = bullets.get(i) ;
		}
		
		if ( tmp == null ) {
			tmp = new Bullet( 0,0, ObjectType.Bullet ) ;
			tmp.isSleeping = true ;
			bullets.add(tmp) ;
		}
		
		return tmp ;
		
	}
	
	
	void render(Canvas canvas) {
		//canvas.drawRect(x, canvas.getHeight()-y , width+x, canvas.getHeight()-y +height, paint); // have parent screen size problem
		canvas.drawBitmap(bitmapLR[iLR], null, new Rect((int)x, (int)(canvas.getHeight()-y) , (int)(width+x), (int)(canvas.getHeight()-y +height)), paint);
		//canvas.drawRect(x, y , width+x, y -height, paint);
		//drawBound(canvas, Color.RED  );
		
	}

	void Jump() {
		if ( this.velY <= 0 ) {
			this.velY = jumpSpeed ;
			ResourceManager.PlaySound(R.raw.jump2, 0);
			//ResourceManager.PlaySound(R.raw.dragon_den, 0);
		}
		
	}
	
	void onCollider(GameObject obj) {
		
	}


	@Override
	void sleep() {
		// TODO Auto-generated method stub
		isSleeping = false ;
	}


	@Override
	void awake() {
		// TODO Auto-generated method stub
		this.enRigid = true ;
		this.death = false ;
	}


	@Override
	void onColliderTop(GameObject obj) {
		// TODO Auto-generated method stub
		
	}


	@Override
	void onColliderBottom(GameObject obj) {
		// TODO Auto-generated method stub
		if ( obj.getObjType() == ObjectType.Block ) {
			if ( cam != null ) cam.setTarget(obj) ;
		}
		
	}
	


}
