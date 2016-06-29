package Not.Doodle.Jump;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class WhiteBlock extends Block {

	protected int jumpTime = 0 ;
	
	public WhiteBlock(float x, float y, float width, float height,	ObjectType type) {
		super(x, y, width, height, type);
		// TODO Auto-generated constructor stub
		texture = ResourceManager.getBitmapByID(R.drawable.p_white01);
	    paint.setColor(Color.RED);
	}
	
	public	WhiteBlock(float x, float y,  ObjectType type) {
		super(x, y, type);
		texture = ResourceManager.getBitmapByID(R.drawable.p_white01);
	    paint.setColor(Color.RED);
	}
	
	@Override
	void render(Canvas canvas ) {
		if ( isSleeping ) return ;
		
		//canvas.drawRect(x, canvas.getHeight()-y , width+x, canvas.getHeight()-y +height, paint); // have parent screen size problem
		canvas.drawBitmap(texture, null, new Rect((int)x, (int)(canvas.getHeight()-y) , (int)(width+x), (int)(canvas.getHeight()-y +height)), paint);
		//canvas.drawRect(x, y , width+x, y -height, paint);
		//drawBound(canvas, Color.GRAY );
		
	}
	
	
	@Override
	public void awake() {
		isSleeping = false ;
		jumpTime = 0 ;
		
		
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
