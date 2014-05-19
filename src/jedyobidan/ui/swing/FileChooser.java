package jedyobidan.ui.swing;

import java.io.File;

import javax.swing.*;

public class FileChooser extends JFileChooser{
	private static final long serialVersionUID = 1L;
	public FileChooser(File dir){
		super(dir);
	}
	public File open(JFrame frame){
		return getFile(frame, "Open", "Open");
	}
	public File save(JFrame frame){
		return getFile(frame, "Save", "Save");
	}
	public File getFile(JFrame frame, String title, String acceptText){
		setDialogTitle(title);
		int ret = showDialog(frame, acceptText);
		if(ret == JFileChooser.APPROVE_OPTION){
			return getSelectedFile();
		} else {
			return null;
		}
	}
}
