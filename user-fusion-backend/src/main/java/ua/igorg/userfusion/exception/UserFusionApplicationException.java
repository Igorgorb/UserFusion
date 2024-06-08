package ua.igorg.userfusion.exception;

/**
 * @Author igorg
 * @create 03.06.2024
 */
public class UserFusionApplicationException  extends RuntimeException {

	public UserFusionApplicationException(final String message) {
		super(message);
	}

	public UserFusionApplicationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UserFusionApplicationException(final Throwable cause) {
		super(cause);
	}
}