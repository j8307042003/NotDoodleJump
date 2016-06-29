#include <jni.h>
#include <stdio.h>
#include <android/log.h>
		
	
jboolean Java_Not_Doodle_Jump_scene_intersectRangeJNI(
	JNIEnv* env,
	jobject obj,
	jint ax1, 
	jint ax2,
	jint bx1,
	jint bx2
	
){
	
	return (ax1>bx1 ? ax1 : bx1) <= (ax2>bx2 ? bx2:ax2) ;


}

void Java_Not_Doodle_Jump_Camera_SpringJNI( 
	JNIEnv* env,
	jobject obj,
	jfloat targetY,
	jfloat h,
	jfloat omega,
	jfloat zeta ) {
	

	 jclass cls = (*env)->GetObjectClass(env, obj);
	 jfieldID yid = (*env)->GetFieldID( env,cls,"y","F" );
	 jfloat y = (*env)->GetFloatField( env,obj, yid );
	 jfieldID velYid = (*env)->GetFieldID( env,cls,"velY","F" );
	 jfloat velY = (*env)->GetFloatField( env,obj, velYid );


	  const double f = 1.0f + 2.0f * h * zeta * omega;
	  const double oo = omega * omega;
	  const double hoo = h * oo;
	  const double hhoo = h * hoo;
	  const double detInv = 1.0f / (f + hhoo);
	  const double detY = f * y + h * velY + hhoo * targetY;
	  const double detV = velY + hoo * (targetY - y);
	  y = (float)(detY * detInv);
	  velY = (float)(detV * detInv);

	(*env)->SetFloatField(env,obj, yid, y);
	(*env)->SetFloatField(env,obj, velYid, velY);


}


