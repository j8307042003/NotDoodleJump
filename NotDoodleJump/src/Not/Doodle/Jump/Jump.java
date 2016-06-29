package Not.Doodle.Jump;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;

public class Jump extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.main);
    
    }
}