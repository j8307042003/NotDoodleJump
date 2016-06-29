package Not.Doodle.Jump;
import java.lang.Math;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import android.R.string;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.KeyEvent;


public class scene extends SurfaceView implements SurfaceHolder.Callback {

	static final float gravityConst = -120f;
	final long tickRate = 1000/10;
	final int playerSize = 32 ;// width must be 10*playerSize
	public final static int gameWidth = 320; // wall Size
	public final static int gameHeight = 430; // canvas.getHeight
	public static long timeStamp = 0;
	float scale = 1;
	Input input;
	long lastTime ;
	long nowTime;
	long deltaTime;
	
	static boolean gameBreak = false ;
	BlockManager blockMangr;
	ResourceManager resMangr;
	LinkedList<GameObject> gameObjs;
	LinkedList<GameObject> blocks ;
	gameLoopThread gameloop;
	static Player player ;
	static Camera cam ;
	
	int score = 0 ;
	int frameCounter = 0;
	int iFPS = 0;
	Timer t = new Timer();
	
	
	public scene(Context context, AttributeSet attrs) {
		super(context, attrs);
		t.scheduleAtFixedRate(updateFPS, 1000, 1000);
		SurfaceHolder holder = getHolder();
		System.loadLibrary("Jni") ;
		holder.addCallback(this);
		setFocusable(true) ;
		resMangr = new ResourceManager( this.getContext() );
		gameObjs = new LinkedList<GameObject>();
		player = new Player( 40,0,50,50, ObjectType.Player );
		gameObjs = GameObject.gameObjList;
		cam = new Camera( 0,0 ) ;
		cam.setTarget(player);
		player.takeCamera(cam);
		blockMangr = new BlockManager(gameWidth,player,cam) ;
	}
	public void surfaceCreated( SurfaceHolder holder ){
		requestFocus();

		holder.setFixedSize(gameWidth, gameHeight);
		input = new Input() ;
		input.SetContext(this.getContext()) ;
		this.setOnKeyListener( input ) ;
		this.setOnTouchListener(input) ;
		gameloop = new gameLoopThread();
		gameloop.start();

	}
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		boolean retry = true;
		gameloop.pause();
		t.cancel();
		t.purge();
	
		while (retry) {
			try {
				gameloop.join();
				retry = false;
            } catch (InterruptedException e) { }
        }
		
		
	}
	public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ){}
	
	
	TimerTask updateFPS = new TimerTask() {
	    public void run() {
	    	iFPS = frameCounter ;
	        frameCounter = 0;
	        //Log.d("fps", String.format("%d", iFPS)) ;
	    }
	} ;
	
	public class gameLoopThread extends Thread {
		private boolean running = false;
		public void pause() { running = false ;}
		public void run() {
			running = true ;
			
			
			lastTime = System.currentTimeMillis();
			while ( running ){
				if ( gameBreak ){
					if (Input.down|| Input.up)
						restart() ;
				}
				
				nowTime = System.currentTimeMillis();
				deltaTime += nowTime - lastTime ;
				lastTime = nowTime ;
				
				while ( deltaTime >= tickRate ) {
					// physics
					// update method
					portal() ;
					update( (float)tickRate/1000 ) ;
					applyPhysics( (float) tickRate/1000 ) ; 
					
					cam.update((float)tickRate/1000) ;
					deathCheck() ;
					//frameCounter++ ;
					deltaTime -= tickRate ;
					timeStamp += tickRate ;
				}
					// renderer
					//cam.update((float)tickRate/1000) ;
				frameCounter++ ;
				draw( getHolder() );
			
			}		
		}
	}
	
	private void update( float deltaTime ){
		blockMangr.update() ;
		for ( GameObject obj : gameObjs ){
			if ( obj.isSleeping == false )
				obj.update(deltaTime);
		}
	}
	
	private void applyPhysics( float deltaTime ) { // gravity, velocity
		player.setVelY(player.getVelY()+gravityConst*deltaTime);
		
		for (int i = 0 ; i < gameObjs.size() ; i++  ) {
			GameObject obj = gameObjs.get(i) ;
			if ( !obj.isSleeping ) {
				for ( int j = i ; j < gameObjs.size() ; j++ ) {
					GameObject secObj  = gameObjs.get(j);
					if ( secObj != obj && !secObj.isSleeping && ( obj.enRigid || secObj.enRigid ) ){
						if ( intersect(obj.getBound(), secObj.getBound()) ){
							GameObject higher = obj.getY() > secObj.getY() ? obj : secObj ;
							GameObject lower = obj.getY() < secObj.getY() ? obj : secObj ;
							obj.onCollider(secObj);
							secObj.onCollider(obj) ;
							if ( higher.velY < 0 && intersect(higher.getBoundBottom(), lower.getBoundTop())) {
								higher.setVelY(0 ) ;
								higher.setY( lower.y + higher.height ) ;
								higher.onColliderBottom(lower);
								lower.onColliderTop(higher);
							}
						}
					}
				}
			}
		}
		
	
		
		//Log.d("time", String.format("%f", player.getVelY()));
		
		player.applyVelocity(deltaTime);
	}
	
	
	private void draw( SurfaceHolder holder ){
		Canvas canvas = holder.lockCanvas();
		canvas.drawColor( Color.BLACK );
		
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
		canvas.drawBitmap(ResourceManager.getBitmapByID(R.drawable.bg_grid01), null, new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), paint);
		
		canvas.translate(0, cam.getY());

		
		for( GameObject obj : gameObjs ){
			if ( !obj.isSleeping )
				obj.render(canvas);
		}
		
		player.render(canvas);
		canvas.translate(0, -cam.getY());
		paint.setColor(Color.BLACK) ;
		paint.setTextSize(40f) ;
		paint.setAntiAlias(true) ;
		if ( player.getY() > score ) score = (int)player.getY() ;
		canvas.drawText(String.format("%d", (int)score), 0f, 50f, paint);
		
		if ( gameBreak ) {
			paint.setTextSize(30f) ;
			String text ="Game Over!";
			float tHeight = 0 ;
			String text2 = String.format("Score : %d", (int)score) ;
			String text3 = "Press Down to restart" ;
			Rect bounds = new Rect();
			paint.getTextBounds(text, 0, text.length(), bounds);
			canvas.drawText("Game Over!", gameWidth/2-(bounds.width()/2), gameHeight/2, paint) ;
			tHeight = bounds.height() ;
			paint.getTextBounds(text2, 0, text2.length(), bounds);
			canvas.drawText(text2, gameWidth/2-(bounds.width()/2), gameHeight/2+(tHeight/2 +20), paint) ;
			tHeight += bounds.height() +20;
			paint.getTextBounds(text3, 0, text3.length(), bounds);
			canvas.drawText(text3, gameWidth/2-(bounds.width()/2), gameHeight/2+(tHeight/2 +20), paint) ;
		}
		
		
		holder.unlockCanvasAndPost( canvas );	
		
	}
	
	
	
	private void deathCheck() {
		if ( player.y < cam.getY() || player.y < -50 ) {
			cam.stop() ;
			endGame() ;
		}
		
		
	}
	
	static public void endGame() {
		if ( gameBreak ) return ;
		gameBreak = true ;
		ResourceManager.PlaySound(R.raw.fall, 0);
	}
	
	void restart() {
		for( GameObject obj : gameObjs ) {
			obj.sleep();
		}
		lastTime = System.currentTimeMillis();
		scene.timeStamp = 0 ;
		score = 0 ;
		player.awake() ;
		player.setPosition(40,0) ;
		player.setVelY( 0) ;
		cam.restart();
		blockMangr.restart();
		cam.setTarget(player);
		gameBreak = false ;
	}
	
	
	private void portal() {
		if ( player.getX() > this.gameWidth ) {// out of screen
			player.setX(0.1f) ;
		}else if ( player.getX()+player.width < 0 ) { // out of screen
			player.setX(gameWidth-1) ;
		}
	}
	
	
	private boolean intersect( Rect r1, Rect r2 ) {
		/*
		return intersectRange( r1.left, r1.right, r2.left, r2.right) &&
			   intersectRange( r1.bottom, r1.top, r2.bottom, r2.top ) ;
			   */
		return intersectRangeJNI( r1.left, r1.right, r2.left, r2.right) &&
		   intersectRangeJNI( r1.bottom, r1.top, r2.bottom, r2.top ) ;
		
	}
	
	private boolean intersectRange( int ax1, int ax2, int bx1, int bx2 ) {
		return Math.max(ax1, bx1) <= Math.min(ax2, bx2) ;
		
	}
	
	
	public native boolean intersectRangeJNI( int ax1, int ax2, int bx1, int bx2  )  ;
	
}
