package jedyobidan.io;

import java.io.*;

/**
 * An implementation of the {@link ResourceFile} interface for resources that
 * are located in the local file system.
 * 
 * @author Young
 * 
 */
public class LocalResource extends ResourceFile {
	File file;

	/**
	 * Creates a new <code>LocalResource</code>. This constructor also attempts
	 * to convert the file to its absolute form.
	 * 
	 * @param f the file of this <code>LocalResource</code>
	 */
	public LocalResource(File f) {	
		try {
			file = f.getAbsoluteFile();
		} catch (SecurityException e) {
			file = f;
		}
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public InputStream openInStream() throws IOException {
		if (file.canRead()) {
			return new FileInputStream(file);
		} else {
			return null;
		}
	}

	@Override
	public String getFileName() {
		return file.getName();
	}

	@Override
	public ResourceFile getParent() {
		return new LocalResource(file.getParentFile());
	}

	/**
	 * Returns the file of this <code>LocalResource</code>.
	 * @return the file of this <code>LocalResource</code>
	 */
	public File getFile() {
		return file;
	}

	@Override
	public boolean equals(ResourceFile r) {
		if (r instanceof LocalResource)
			return ((LocalResource) r).getFile().equals(file);
		return false;
	}

	@Override
	public OutputStream openOutStream() throws IOException {
		if(file.canWrite()){
			return new FileOutputStream(file);
		} else {
			return null;
		}
	}

	@Override
	public String getFullName() {
		return file.getPath();
	}

}
