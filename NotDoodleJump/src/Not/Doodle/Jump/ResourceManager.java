package Not.Doodle.Jump;

import java.util.HashMap;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;


public class ResourceManager {
	
	static Resources res ;
	static HashMap <Integer,Bitmap> textureMap = null ;
	static HashMap <Integer, Integer> spMap = null ; 
	static SoundPool sp = null ;
	
	public  ResourceManager( Context context ) { 
		Resources res = context.getResources() ;
		textureMap = new HashMap() ;
		spMap = new HashMap();
		this.res = res ;
		textureMap.put(Not.Doodle.Jump.R.drawable.doodlel1, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.doodlel1));
		textureMap.put(Not.Doodle.Jump.R.drawable.doodler2, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.doodler2));
		textureMap.put(Not.Doodle.Jump.R.drawable.bg_grid01, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.bg_grid01));
		textureMap.put(Not.Doodle.Jump.R.drawable.p_blue01, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.p_blue01));
		textureMap.put(Not.Doodle.Jump.R.drawable.p_green01, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.p_green01));
		textureMap.put(Not.Doodle.Jump.R.drawable.p_white01, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.p_white01));
		textureMap.put(Not.Doodle.Jump.R.drawable.p_brown_1, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.p_brown_1));
		textureMap.put(Not.Doodle.Jump.R.drawable.p_brown_2, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.p_brown_2));
		textureMap.put(Not.Doodle.Jump.R.drawable.p_brown_3, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.p_brown_3));
		textureMap.put(Not.Doodle.Jump.R.drawable.p_brown_4, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.p_brown_4));
		textureMap.put(Not.Doodle.Jump.R.drawable.p_yellow01, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.p_yellow01));
		textureMap.put(Not.Doodle.Jump.R.drawable.p_red01, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.p_red01));
		textureMap.put(Not.Doodle.Jump.R.drawable.p_black01, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.p_black01));
		textureMap.put(Not.Doodle.Jump.R.drawable.yee01, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.yee01));
		textureMap.put(Not.Doodle.Jump.R.drawable.yee02, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.yee02));
		textureMap.put(Not.Doodle.Jump.R.drawable.bat1, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.bat1));
		textureMap.put(Not.Doodle.Jump.R.drawable.bat2, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.bat2));
		textureMap.put(Not.Doodle.Jump.R.drawable.bat3, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.bat3));
		textureMap.put(Not.Doodle.Jump.R.drawable.bullet, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.bullet));
		textureMap.put(Not.Doodle.Jump.R.drawable.doodles, BitmapFactory.decodeResource(res, Not.Doodle.Jump.R.drawable.doodles));	
		
		sp = new SoundPool( 10, AudioManager.STREAM_MUSIC, 0) ;
		spMap.put(Not.Doodle.Jump.R.raw.dragon_den, sp.load(context, Not.Doodle.Jump.R.raw.dragon_den, 1) );
		
		spMap.put(Not.Doodle.Jump.R.raw.fall, sp.load(context, Not.Doodle.Jump.R.raw.fall, 1) );
		spMap.put(Not.Doodle.Jump.R.raw.jump, sp.load(context, Not.Doodle.Jump.R.raw.jump, 1) );
		spMap.put(Not.Doodle.Jump.R.raw.jump2, sp.load(context, Not.Doodle.Jump.R.raw.jump2, 1) );
		spMap.put(Not.Doodle.Jump.R.raw.shot, sp.load(context, Not.Doodle.Jump.R.raw.shot, 1) );
		spMap.put(Not.Doodle.Jump.R.raw.gumo_hideout, sp.load(context, Not.Doodle.Jump.R.raw.gumo_hideout, 1) );
		
		
	}
	
	public static void StopAllSound() {
		//sp.stop(Not.Doodle.Jump.R.raw.fall) ;
	}
	
	public static void PlaySound( int id, int loop ) { // (0 = no loop, -1 = loop forever)
		sp.play(spMap.get(id), 1, 1, 1, loop, 1) ;
		
	}
	
	public static void StopSound( int id ) {
		sp.stop(spMap.get(id)) ;
	}
	

	
	
	static public Bitmap getBitmapByID(int id) {
		return textureMap.get(id) ;
	}
	
	
	
	
}
