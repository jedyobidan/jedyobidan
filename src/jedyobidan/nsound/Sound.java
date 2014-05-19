package jedyobidan.nsound;

import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.InputStream;

import jedyobidan.io.IO;

public abstract class Sound {
	public void play(){
		play(1);
	}
	public void loop(){
		play(0);
	}
	public abstract void play(int times);
	public void stop(){
		pause();
		setPositionInMicroseconds(0);
	}
	public void playFromBeginning(int times){
		setPositionInMicroseconds(0);
		play(times);
	}
	public void playFromBeginning(){
		setPositionInMicroseconds(0);
		play();
	}
	public void loopFromBeginning(){
		setPositionInMicroseconds(0);
		loop();
	}
	public abstract void pause();
	public abstract boolean isPlaying();
	public abstract void setPositionInMicroseconds(long position);
	public abstract long getPositionInMicroseconds();
	public abstract long getLengthInMicroseconds();
	public abstract void setTempoFactor(float factor) throws UnsupportedOperationException;
	public abstract void setVolumeFactor(double factor) throws UnsupportedOperationException;
	public abstract void dispose();
	public abstract void onPlaybackEnd(ActionListener a);
	
	public static Sound getSound(Class<? extends Object> c, String filename){
		BufferedInputStream stream = new BufferedInputStream(c.getResourceAsStream(filename));
		String ext = IO.getFileExtension(filename);
		if(ext.startsWith("mid")){
			try {
				return new MidiSound(stream);
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
		} else if (ext.equals("aiff")|| ext.equals("wav") || ext.equals("au")){
			try{
				return new SampledSound(stream);
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
}
