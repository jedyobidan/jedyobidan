package jedyobidan.ui.swing;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;

public class ImageComboBox extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private ImageIcon[] images;
	private JComboBox<String> comboBox;
	private JLabel label;
	
	public ImageComboBox(String[] options, ImageIcon[] images){
		this(options, images, SwingConstants.CENTER, SwingConstants.CENTER);
	}

	public ImageComboBox(String[] options, ImageIcon[] images, int hAlign, int vAlign) {
		this.images = images;
		this.comboBox = new JComboBox<String>(options);
		comboBox.addActionListener(this);
		label = new JLabel(images[0]);
		label.setVerticalAlignment(vAlign);
		label.setHorizontalAlignment(hAlign);

		setLayout(new BorderLayout());
		JPanel cont = new JPanel();
		cont.setLayout(new BorderLayout());
		cont.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		cont.add(comboBox);
		add(cont, BorderLayout.NORTH);
		add(label);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		label.setIcon(images[((JComboBox<String>) (e.getSource()))
				.getSelectedIndex()]);
	}

	public String getSelection() {
		return (String) comboBox.getSelectedItem();
	}
	
	public int getSelectedIndex(){
		return comboBox.getSelectedIndex();
	}
	
	public void requestFocus(){
		comboBox.requestFocusInWindow();
	}
}
