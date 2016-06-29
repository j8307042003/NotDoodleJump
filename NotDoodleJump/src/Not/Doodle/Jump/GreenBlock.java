package Not.Doodle.Jump;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GreenBlock extends Block{
	
	public GreenBlock(float x, float y, float width, float height, ObjectType type) {
		super(x, y, width, height, type);
	    paint.setColor(Color.GREEN);
	}
	public GreenBlock(float x, float y,  ObjectType type) {
		super(x, y, type);
	    paint.setColor(Color.GREEN);
	}
	
	@Override
	void update(float deltaTime) {
		// TODO Auto-generated method stub
		return;
	}

}