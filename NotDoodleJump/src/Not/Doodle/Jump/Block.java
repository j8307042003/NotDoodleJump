package Not.Doodle.Jump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Block extends GameObject{

	
	protected Bitmap texture;
	static protected float blockWidth = 50;
	static protected float blockHeight = 13;
	
	
	
	public Block(float x, float y, float width, float height, ObjectType type) {
		super(x, y, width, height, type);
		this.setWidth(blockWidth);
		this.setHeight(blockHeight);
		texture = ResourceManager.getBitmapByID(R.drawable.p_green01) ;
		// TODO Auto-generated constructor stub
		paint.setColor(Color.YELLOW);
	}
	
	public Block(float x, float y, ObjectType type) {
		super(x, y, 0, 0, type);
		this.setWidth(blockWidth);
		this.setHeight(blockHeight);
		texture = ResourceManager.getBitmapByID(R.drawable.p_green01) ;
		// TODO Auto-generated constructor stub
		paint.setColor(Color.YELLOW);
	}
	
	@Override
	void update(float deltaTime) {
		// TODO Auto-generated method stub
	}

	@Override
	void render(Canvas canvas ) {
		if ( isSleeping ) return ;
		
		//canvas.drawRect(x, canvas.getHeight()-y , width+x, canvas.getHeight()-y +height, paint); // have parent screen size problem
		canvas.drawBitmap(texture, null, new Rect((int)x, (int)(canvas.getHeight()-y) , (int)(width+x), (int)(canvas.getHeight()-y +height)), paint);
		//canvas.drawRect(x, y , width+x, y -height, paint);
		//drawBound(canvas, Color.GRAY );
		// TODO Auto-generated method stub
		
	}

	@Override
	void onCollider(GameObject obj) {
		// TODO Auto-generated method stub
	}

	@Override
	void sleep() {
		// TODO Auto-generated method stub
		isSleeping = true ;
	}

	@Override
	void awake() {
		// TODO Auto-generated method stub
		isSleeping = false ;
		
	}
	
	private boolean intersect( Rect r1, Rect r2 ) {
		return intersectRange( r1.left, r1.right, r2.left, r2.right) &&
			   intersectRange( r1.bottom, r1.top, r2.bottom, r2.top ) ;
		
	}
	
	private boolean intersectRange( int ax1, int ax2, int bx1, int bx2 ) {
		return Math.max(ax1, bx1) <= Math.min(ax2, bx2) ;
		
	}

	@Override
	void onColliderTop(GameObject obj) {
		// TODO Auto-generated method stub
		if ( obj.getObjType() == ObjectType.Player )
			((Player) obj).Jump() ;
		
	}

	@Override
	void onColliderBottom(GameObject obj) {
		// TODO Auto-generated method stub
		
	}

}
