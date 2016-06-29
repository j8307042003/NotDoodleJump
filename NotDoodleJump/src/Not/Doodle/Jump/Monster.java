package Not.Doodle.Jump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Monster extends GameObject{
	static float monsterWidth = 64 ;
	static float monsterHeight =64 ;
	Bitmap [] bitmaps = new Bitmap[3]; 
	int bmIndex = 0 ;
	int hp = 1 ;
	float timer = 0 ;
	
	
	public Monster(float x, float y, float width, float height, ObjectType type) {
		super(x, y, width, height, type);
		
		// TODO Auto-generated constructor stub
	}

	public Monster(float x, float y,  ObjectType type) {
		super(x, y, 0, 0, type);
		// TODO Auto-generated constructor stub
		setWidth( this.monsterWidth ) ;
		setHeight( this.monsterHeight) ;
		getTexture() ;
		this.enRigid = true ;
		
	}
	
	public void takeDamage() {
		hp-- ;
		if ( hp <= 0 ) {
			sleep() ;
		}
	}
	
	@Override
	void sleep() {
		// TODO Auto-generated method stub
		isSleeping = true ;
	}

	@Override
	void update(float deltaTime) {
		// TODO Auto-generated method stub
		if ( timer/0.1f > 2 ) timer = 0 ;
		
		bmIndex = (int) (timer / 0.07f );
		timer += deltaTime ;
		
		
	}

	@Override
	void render(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(bitmaps[bmIndex], null, new Rect((int)x, (int)(canvas.getHeight()-y) , (int)(width+x), (int)(canvas.getHeight()-y +height)), paint);
	}

	
	
	private void getTexture() {
		bitmaps[0] = ResourceManager.getBitmapByID(R.drawable.bat1) ;
		bitmaps[1] = ResourceManager.getBitmapByID(R.drawable.bat2) ;
		bitmaps[2] = ResourceManager.getBitmapByID(R.drawable.bat3) ;
	}
	
	@Override
	void onCollider(GameObject obj) {
		// TODO Auto-generated method stub
		if ( obj.getObjType() == ObjectType.Player ) {
			((Player) obj).takeDamage();
			enRigid = false ;
		}
	}

	@Override
	void awake() {
		// TODO Auto-generated method stub
		isSleeping = false ;
		enRigid = true ;
		ResourceManager.PlaySound(R.raw.gumo_hideout, 0) ;
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
