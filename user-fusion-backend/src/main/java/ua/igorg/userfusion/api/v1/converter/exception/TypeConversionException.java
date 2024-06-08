package ua.igorg.userfusion.api.v1.converter.exception;

/**
 * @Author igorg
 * @create 03.06.2024
 */

import ua.igorg.userfusion.exception.UserFusionApplicationException;

import static java.lang.String.format;

public class TypeConversionException extends UserFusionApplicationException {

	public TypeConversionException(final String msgTemplate, final Object... args) {
		super(format(msgTemplate, args));
	}

	public TypeConversionException(final Throwable cause, final String msgTemplate, final Object... args) {
		super(format(msgTemplate, args), cause);
	}
}