package jedyobidan.ui.swing;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;

/**
 * 
 * @author adapted from Bart Kiers (Stack Overflow)
 *
 */
public class HintTextField extends JTextField implements FocusListener {
	private static final long serialVersionUID = 1L;
	private final String hint;

    public HintTextField(final String hint) {
        super(hint);
        this.hint = hint;
        super.setForeground(Color.gray);
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText("");
        }
        super.setForeground(Color.black);
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty()) {
        	super.setForeground(Color.gray);
            super.setText(hint);
        }
    }

    @Override
    public String getText() {
        return getForeground().equals(Color.gray) ? "" : super.getText();
    }
}