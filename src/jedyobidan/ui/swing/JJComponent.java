package jedyobidan.ui.swing;

import java.awt.event.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * An facade of the {@link JComponent} class that adds additional or simpler
 * functionality while still giving low-level access.
 * 
 * @author Young
 * 
 */
public abstract class JJComponent extends JComponent {
	private static final long serialVersionUID = 1L;

	/**
	 * Adds a key binding to this component. Key bindings are used to execute
	 * the action when a certain {@link KeyStroke} is typed.
	 * 
	 * 
	 * @param k
	 *            the keystroke
	 * @param condition
	 *            when this key binding should be used; one of
	 *            WHEN_IN_FOCUSED_WINDOW, WHEN_FOCUSED,
	 *            WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
	 * @param ls
	 *            the listeners to dispatch a {@link KeyEvent} to
	 */
	public void addKeyBinding(KeyStroke k, int condition, final KeyListener[] ls) {
		final KeyEvent event = new KeyEvent(this, k.getKeyEventType(),
				System.currentTimeMillis(), k.getModifiers(), k.getKeyCode(),
				k.getKeyChar());
		Action a = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				for (KeyListener l : ls) {
					switch (event.getID()) {
					case KeyEvent.KEY_TYPED:
						l.keyTyped(event);
						break;
					case KeyEvent.KEY_PRESSED:
						l.keyPressed(event);
						break;
					case KeyEvent.KEY_RELEASED:
						l.keyReleased(event);
						break;
					}
				}

			}

		};
		String name = k.getKeyCode() + "@" + k.hashCode();
		getInputMap(condition).put(k, name);
		getActionMap().put(name, a);
	}
}
