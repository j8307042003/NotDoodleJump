package Not.Doodle.Jump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class RedBlock extends WhiteBlock{
	
	Bitmap [] redBm = new Bitmap[2] ;
	
	
	public RedBlock(float x, float y, float width, float height, ObjectType type) {
		super(x, y, width, height, type);
		redBm[1] = ResourceManager.getBitmapByID(R.drawable.p_red01);
		redBm[0] = ResourceManager.getBitmapByID(R.drawable.p_white01);
	    paint.setColor(Color.RED);
		jumpTime = 1;
	}

	public RedBlock(float x, float y,  ObjectType type) {
		super(x, y, type);
		redBm[1] = ResourceManager.getBitmapByID(R.drawable.p_red01);
		redBm[0] = ResourceManager.getBitmapByID(R.drawable.p_white01);
	    paint.setColor(Color.RED);
		jumpTime = 1;
	}
	
	@Override
	void update(float deltaTime) {
		// TODO Auto-generated method st
		
		return;
	}

	@Override
	void render(Canvas canvas ) {
		if ( isSleeping ) return ;
		
		//canvas.drawRect(x, canvas.getHeight()-y , width+x, canvas.getHeight()-y +height, paint); // have parent screen size problem
		canvas.drawBitmap(redBm[jumpTime], null, new Rect((int)x, (int)(canvas.getHeight()-y) , (int)(width+x), (int)(canvas.getHeight()-y +height)), paint);
		//canvas.drawRect(x, y , width+x, y -height, paint);
		//drawBound(canvas, Color.GRAY );
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void awake() {
		isSleeping = false ;
		jumpTime = 1 ;
		
		
	}
	
	@Override
	void onCollider(GameObject obj) {
		// TODO Auto-generated method stub
			
	}
	@Override
	void onColliderTop(GameObject obj) {
		// TODO Auto-generated method stub
		
		if ( obj.getObjType() == ObjectType.Player )
			((Player) obj).Jump() ;

		if( jumpTime == 0 ) {
			this.isSleeping = true;
			
		}
		else
			jumpTime = jumpTime - 1;
		
	}
	
	

}