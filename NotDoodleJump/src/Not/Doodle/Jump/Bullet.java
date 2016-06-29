package Not.Doodle.Jump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Bullet extends GameObject {
	static float initWidh = 10 ;
	static float initHeight = 10 ;
	Bitmap texture ;
	float flyTime = 1.5f ;
	float timer = 0 ;
	public Bullet(float x, float y, float width, float height, ObjectType type) {
		super(x, y, width, height, type);
		// TODO Auto-generated constructor stub
	}
	
	public Bullet(float x, float y,  ObjectType type) {
		super(x, y, 0, 0, type);
		setWidth( this.initWidh ) ;
		setHeight( this.initHeight ) ;
		texture = ResourceManager.getBitmapByID(R.drawable.bullet) ;
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	void update(float deltaTime) {
		// TODO Auto-generated method stub
		this.timer += deltaTime ;
		y+= 400*deltaTime ;
		if ( timer > flyTime ) sleep() ;
	}
	

	@Override
	void awake() {
		// TODO Auto-generated method stub
		isSleeping = false ;
	}

	@Override
	void sleep() {
		// TODO Auto-generated method stub
		timer = 0 ;
		isSleeping = true ;
	}

	@Override
	void render(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(texture, null, new Rect((int)x, (int)(canvas.getHeight()-y) , (int)(width+x), (int)(canvas.getHeight()-y +height)), paint);
	}

	@Override
	void onCollider(GameObject obj) {
		// TODO Auto-generated method stub
		if ( obj.getObjType() == ObjectType.Monster ) {
			(( Monster ) obj).takeDamage() ;
			sleep() ;
		}
	}

	@Override
	void onColliderTop(GameObject obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void onColliderBottom(GameObject obj) {
		// TODO Auto-generated method stub
		
	}

	
	
}
