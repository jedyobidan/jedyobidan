package jedyobidan.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An implementation of the {@link ResourceFile} interface for resources that
 * are located via URL.
 * 
 * @author Young
 * 
 */
public class URLResource extends ResourceFile {
	URL url;
	
	/**
	 * Constructs a new <code>URLResource</code>
	 * @param u the url of this resource
	 */
	public URLResource(URL u) {
		url = u;
	}

	@Override
	public InputStream openInStream() throws IOException {
		return url.openStream();
	}

	@Override
	public ResourceFile getParent() {
		String fullUrl = url.toExternalForm();
		try {
			return new URLResource(new URL(fullUrl.substring(0,
					fullUrl.lastIndexOf("/"))));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getFileName() {
		String path = url.getPath();
		return path.substring((path.lastIndexOf("/")+1));
	}
	
	/**
	 * Returns the external form of this <code>URLResource</code>'s url
	 */
	@Override
	public String getFullName(){
		return url.toExternalForm();
	}
	/**
	 * Returns the URL of this <code>URLResource</code>.
	 * @return the URL of this <code>URLResource</code>
	 */
	public URL getURL(){
		return url;
	}

	@Override
	public boolean equals(ResourceFile r) {
		if(r instanceof URLResource) return ((URLResource) r).getURL().equals(url);
		return false;
	}

	@Override
	public OutputStream openOutStream() throws IOException {
		throw new UnsupportedOperationException("Cannot write to a URL");
	}

}
