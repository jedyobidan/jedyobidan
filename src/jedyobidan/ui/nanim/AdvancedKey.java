package jedyobidan.ui.nanim;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class AdvancedKey implements Serializable {
	private static final long serialVersionUID = 1L;
	public final int keyCode;
	public final int keyLocation;

	public AdvancedKey(int keyCode, int keyLocation) {
		this.keyCode = keyCode;
		this.keyLocation = keyLocation;
	}
	
	public AdvancedKey(int keyCode){
		this(keyCode, KeyEvent.KEY_LOCATION_STANDARD);
	}

	public AdvancedKey(KeyEvent e) {
		this(e.getKeyCode(), e.getKeyLocation());
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof AdvancedKey))
			return false;
		AdvancedKey k = (AdvancedKey) o;
		return (k.normalize() == this.normalize() && (k.keyLocation == this.keyLocation
				|| k.keyLocation == KeyEvent.KEY_LOCATION_STANDARD || this.keyLocation == KeyEvent.KEY_LOCATION_STANDARD));
	}

	private int normalize() {
		if (keyCode == KeyEvent.VK_NUMPAD0)
			return KeyEvent.VK_INSERT;
		if (keyCode == KeyEvent.VK_NUMPAD1)
			return KeyEvent.VK_END;
		if (keyCode == KeyEvent.VK_NUMPAD3)
			return KeyEvent.VK_PAGE_DOWN;
		if (keyCode == KeyEvent.VK_NUMPAD7)
			return KeyEvent.VK_HOME;
		if (keyCode == KeyEvent.VK_NUMPAD9)
			return KeyEvent.VK_PAGE_UP;
		return keyCode;
	}

	public int hashCode() {
		return normalize();
	}

	public String toString() {
		String loc = "";
		if (keyLocation == KeyEvent.KEY_LOCATION_LEFT) {
			loc = "Left ";
		} else if (keyLocation == KeyEvent.KEY_LOCATION_RIGHT) {
			loc = "Right ";
		}
		return loc + KeyEvent.getKeyText(keyCode);
	}
}
