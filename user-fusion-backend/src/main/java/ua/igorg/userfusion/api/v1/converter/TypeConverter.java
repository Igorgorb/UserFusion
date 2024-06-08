package ua.igorg.userfusion.api.v1.converter;

import java.util.Collection;
import java.util.List;

/**
 * @Author igorg
 * @create 03.06.2024
 */
public interface TypeConverter<S, T> {

	Class<S> getSourceClass();

	Class<T> getTargetClass();

	T convert(S source);

	default List<T> convert(final Collection<S> sourceList) {
		return sourceList
			.stream()
			.map(this::convert)
			.toList();
	}
}