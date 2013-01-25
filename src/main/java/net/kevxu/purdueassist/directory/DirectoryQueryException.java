/*
 * Author: Kaiwen Xu
 */

package net.kevxu.purdueassist.directory;

public class DirectoryQueryException extends Exception {

	private static final long serialVersionUID = -109046445227574491L;

	public DirectoryQueryException() {
	}

	public DirectoryQueryException(String detailMessage) {
		super(detailMessage);
	}

	public DirectoryQueryException(Throwable throwable) {
		super(throwable);
	}

	public DirectoryQueryException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
