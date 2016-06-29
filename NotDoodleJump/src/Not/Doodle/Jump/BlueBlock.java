package Not.Doodle.Jump;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BlueBlock extends Block{
	boolean end;
	float speed = 40f ;
	
	public BlueBlock(float x, float y, float width, float height, ObjectType type) {
		super(x, y, width, height, type);
	    paint.setColor(Color.BLUE);
	    texture = ResourceManager.getBitmapByID(R.drawable.p_blue01);
		end = false;
	}
	
	public BlueBlock(float x, float y, ObjectType type) {
		super(x, y, type);
	    paint.setColor(Color.BLUE);
	    texture = ResourceManager.getBitmapByID(R.drawable.p_blue01);
		end = false;
	}
	
	
	@Override
	void update(float deltaTime) {
		// TODO Auto-generated method stub
		if( x <= 0 )
			end = true;
		else if( x+width >= scene.gameWidth)
			end = false;
		
		if( end )
			x += speed * deltaTime;
		else
			x -= speed * deltaTime;
		
		return;
	}
	
	

}