package ua.igorg.userfusion.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @Author igorg
 * @create 07.06.2024
 */
public class WebUtil {

	private WebUtil() {
	}

	public static String getFullRequestUri() {
		final ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri();
		return decode(uriBuilder.toUriString());
	}

	private static String decode(final String uriString) {
		try {
			return URLDecoder.decode(uriString, StandardCharsets.UTF_8.toString());
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Error decoding URI string:[" + uriString + "]", e);
		}
	}
}