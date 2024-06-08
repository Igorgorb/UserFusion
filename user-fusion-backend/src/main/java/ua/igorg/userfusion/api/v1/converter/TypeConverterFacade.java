package ua.igorg.userfusion.api.v1.converter;

import ua.igorg.userfusion.api.v1.converter.exception.TypeConversionException;

import java.util.List;

/**
 * @Author igorg
 * @create 03.06.2024
 */
public interface TypeConverterFacade {

	<S, T> T convert(S source, Class<T> targetClz);

	default <S, T> List<T> convertList(final List<S> sourceList, final Class<T> targetClz) {
		assertNotNull(sourceList, targetClz);

		return sourceList
			.stream()
			.map(s -> convert(s, targetClz))
			.toList();
	}

	private void assertNotNull(final List<?> list, final Class<?> targetClz) {
		if (list == null) {
			throw new TypeConversionException("Fail to convert list of values: source list can not be null");
		}
		if (targetClz == null) {
			throw new TypeConversionException("Fail to convert list of values: target class can not be null");
		}
	}
}