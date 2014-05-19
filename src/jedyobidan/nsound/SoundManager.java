package jedyobidan.nsound;

import java.util.HashMap;

public class SoundManager extends HashMap<String, Sound>{
	private static final long serialVersionUID = 1L;
	public void clear(){
		for(Sound s: values()){
			s.dispose();
		}
		super.clear();
	}
	public void dispose(String s){
		this.remove(s).dispose();
	}
	public Sound load(Class<? extends Object> c, String folder, String name, String ext){
		Sound s = Sound.getSound(c, folder + "/" + name + "." + ext);
		put(name, s);
		return s;
	}
}
