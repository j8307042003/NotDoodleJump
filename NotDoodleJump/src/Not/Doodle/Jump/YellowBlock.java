package Not.Doodle.Jump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class YellowBlock extends Block {

	Bitmap [] ybm = new Bitmap[2];
	int index = 0 ;
	boolean trigger = false ;
	float timer = 0 ;
	float chTimer = 0 ;
	float exploTime = ((float)Math.random() * 5) + 2 ; // 2 + 0~n seconds 
	final float triggerDistance =(float)((Math.pow(Player.jumpSpeed, 2)/ (-2*scene.gravityConst) ) * (Math.random()-0.4f) ) ; 
	
	
	public YellowBlock(float x, float y, float width, float height,	ObjectType type) {
		super(x, y, width, height, type);
		ybm[1] = ResourceManager.getBitmapByID(R.drawable.p_red01);
		ybm[0] = ResourceManager.getBitmapByID(R.drawable.p_yellow01);
	    paint.setColor(Color.YELLOW);
		// TODO Auto-generated constructor stub
	}
	public YellowBlock(float x, float y,  ObjectType type) {
		super(x, y, type);
		ybm[1] = ResourceManager.getBitmapByID(R.drawable.p_red01);
		ybm[0] = ResourceManager.getBitmapByID(R.drawable.p_yellow01);
	    paint.setColor(Color.YELLOW);
	}
	

	@Override
	void update(float deltaTime) {
		// TODO Auto-generated method st
		if ( scene.player.velY < 0 && y - scene.player.getY() < triggerDistance ) 
			trigger = true ;
		if ( trigger ) {
			timer+= deltaTime ;
			chTimer += deltaTime ;
			if ( timer > exploTime ) {
				sleep() ;
			}
			if ( chTimer > .02f) {
				index = index == 0 ? 1 : 0;
				chTimer = 0 ;
			} 
			
		}
		
		
		return;
	}

	@Override
	void render(Canvas canvas ) {
		if ( isSleeping ) return ;
		
		//canvas.drawRect(x, canvas.getHeight()-y , width+x, canvas.getHeight()-y +height, paint); // have parent screen size problem
		canvas.drawBitmap(ybm[index], null, new Rect((int)x, (int)(canvas.getHeight()-y) , (int)(width+x), (int)(canvas.getHeight()-y +height)), paint);
		//canvas.drawRect(x, y , width+x, y -height, paint);
		//drawBound(canvas, Color.GRAY );
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void awake() {
		isSleeping = false ;
		index = 0 ;
		trigger = false ;
		chTimer = 0 ;
		timer = 0 ;
	}
	
	

}
