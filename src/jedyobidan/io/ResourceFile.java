package jedyobidan.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a resource file on a file system.
 * 
 * @author Young
 * 
 */
public abstract class ResourceFile {
	/**
	 * Returns an InputStream to read this resource.
	 * 
	 * @return an InputStream to read this resource
	 * @throws IOException
	 *             if a generic I/O error occurs.
	 */
	public abstract InputStream openInStream() throws IOException;

	/**
	 * Returns an OutputStream to write to this resource.
	 * 
	 * @return an OutputStream to write to this resource.
	 * @throws IOException
	 */
	public abstract OutputStream openOutStream() throws IOException;

	/**
	 * Returns the file extension (not including the preceding dot) of the file
	 * this <code>ResourceFile</code> points to.
	 * 
	 * @return the file extension of this <code>ResourceFile</code>, or an empty
	 *         string of the file extension does not exist.
	 */
	public String getExtension() {
		return IO.getFileExtension(getFileName());
	}
	
	/**
	 * Returns the name of the file this <code>ResourceFile</code> points to,
	 * without including its location.
	 * <p>
	 * For example, if <code>ResourceFile r</code> points to
	 * <code>C:\path\to\the\file.txt</code>, then <code>r.getFileName()</code>
	 * returns <code>"file.txt"</code>.
	 * 
	 * </code>
	 * 
	 * @return the name of this file
	 */
	public abstract String getFileName();

	/**
	 * Returns a <code>ResourceFile</code> that points to the parent of the file
	 * this <code>ResourceFile</code> points to.
	 * 
	 * @return the parent of this resource file
	 */
	public abstract ResourceFile getParent();
	
	/**
	 * Returns the full name of the <code>ResourceFile</code>
	 * @return the full name of the <code>ResourceFile</code>
	 */
	public abstract String getFullName();

	/**
	 * Returns a String representation of this <code>ResourceFile</code>.
	 * 
	 * @return a String representation of this <code>ResourceFile</code>
	 * 
	 * @see Object#toString()
	 */
	@Override
	public String toString(){
		return getFullName();
	}

	/**
	 * Returns <code>true</code> if two <code>ResourceFile</code>s are
	 * considered equal. Two <code>ResourceFile</code> are considered equal if
	 * they point to the same file.
	 * 
	 * @see #equals(Object)
	 */

	public abstract boolean equals(ResourceFile r);

	/**
	 * Returns whether this ResourceFile is equal to another object. If the
	 * other object is <code>null</code> or is not a <code>ResourceFile</code>,
	 * then this method automatically returns <code>false</code>. If the other
	 * object is a reference to this object, this method automatically returns
	 * <code>true</code>. Otherwise, this method delegates to
	 * {@link #equals(ResourceFile)}.
	 * 
	 * @param o
	 *            the object to be compared to this object
	 * @return <code>true</code> if this object equals the other object;
	 *         <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof ResourceFile))
			return false;
		return equals((ResourceFile) o);
	}
}
