package jedyobidan.nsound;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import jedyobidan.io.IO;

public class SoundPlayer {
	public static Sound play(File f, int times) throws FileNotFoundException{
		String ext = IO.getFileExtension(f.getName());
		return play(ext, new FileInputStream(f), times);
	}
	public static Sound play(URL url, int times) throws IOException{
		String ext = IO.getFileExtension(url.getPath());
		return play(ext, url.openStream(), times);
	}
	public static Sound play(String ext, InputStream stream, int times){
		final Sound s;
		if(ext.startsWith("mid")){
			try {
				s = new MidiSound(stream);
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
		} else if (ext.equals("aiff")|| ext.equals("wav") || ext.equals("au")){
			try{
				s = new SampledSound(stream);
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
		s.onPlaybackEnd(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				s.dispose();
			}
		});
		s.play(times);
		return s;
	}
}
