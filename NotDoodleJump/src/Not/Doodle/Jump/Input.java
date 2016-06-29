package Not.Doodle.Jump;

import android.app.Activity;
import android.content.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class Input implements View.OnKeyListener, View.OnTouchListener, SensorEventListener {
	public static boolean menu = false ;
	public static boolean down =false ;
	public static boolean up = false ;
	public static boolean left = false ;
	public static boolean right = false ;
	public static boolean zero = false ;

	public Input() {;}

	SensorManager sm = null;
	Sensor GSensor;
	Context context ;
	
	public void SetContext(Context context) {
		this.context = context ;
		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		GSensor = sm.getDefaultSensor( Sensor.TYPE_ACCELEROMETER );
		sm.registerListener(this, GSensor, SensorManager.SENSOR_DELAY_UI);
	}
	
	public void handleKeyDown(int keyCode,KeyEvent event) {
		
		switch( keyCode ) {
			
			case KeyEvent.KEYCODE_DPAD_UP:	
				this.up = true  ;break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				this.down = true ; break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				this.left = true ; break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				this.right = true ; break;
			case KeyEvent.KEYCODE_SPACE: break;
			case KeyEvent.KEYCODE_DPAD_CENTER:
				;
				break;
			case KeyEvent.KEYCODE_MENU:
			    this.menu = true ; break ;
		
		}
		
		
	}
	
	public void handleKeyUp( int keyCode, KeyEvent event ) {
		switch( keyCode ) {
		case KeyEvent.KEYCODE_DPAD_UP:	
			this.up = false   ;break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			this.down = false ; break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			this.left = false ; break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			this.right = false ; break;
		case KeyEvent.KEYCODE_SPACE: break;
		case KeyEvent.KEYCODE_DPAD_CENTER:
			;
			break;
		case KeyEvent.KEYCODE_MENU:
		    this.menu = false ; break ;
	
	}
	
		
	}


	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ( event.getAction() == KeyEvent.ACTION_DOWN ) 
			handleKeyDown( keyCode, event ) ;
		else if( event.getAction() == KeyEvent.ACTION_UP )
			handleKeyUp(keyCode, event) ;
	
		return false ;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch( event.getAction() ) {
		case MotionEvent.ACTION_DOWN:
			up = true ;
			break;
		case MotionEvent.ACTION_UP : 
			up = false ;
		
			break ;
			
			
		}
		
			
		return true ;
	}

	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			if ( event.values[0] > 1.5 ) { left = true ; right = false ;}
			else if ( event.values[0] < -1.5 ) {right = true ;left = false ;}
			else {
				right = false ;
				left = false ;
			}
		}
	}





}
