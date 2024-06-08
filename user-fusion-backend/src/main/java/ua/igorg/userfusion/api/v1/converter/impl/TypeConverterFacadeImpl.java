package ua.igorg.userfusion.api.v1.converter.impl;

import jakarta.annotation.PostConstruct;
import ua.igorg.userfusion.api.v1.converter.CommonTypeConverter;
import ua.igorg.userfusion.api.v1.converter.TypeConverter;
import ua.igorg.userfusion.api.v1.converter.TypeConverterFacade;
import ua.igorg.userfusion.api.v1.converter.exception.TypeConversionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author igorg
 * @create 03.06.2024
 */
public class TypeConverterFacadeImpl implements TypeConverterFacade {
	private final List<TypeConverter<?, ?>> converters;

	private final Map<ConversionDescriptor, TypeConverter<?, ?>> converterRegistry = new HashMap<>();

	private final CommonTypeConverter fallbackConverter;

	public TypeConverterFacadeImpl(
		final List<TypeConverter<?, ?>> converters,
		final CommonTypeConverter fallbackConverter
	) {
		this.converters = converters;
		this.fallbackConverter = fallbackConverter;
	}

	@PostConstruct
	protected void populateConverterRegistry() {
		for (final TypeConverter<?, ?> converter : converters) {
			final ConversionDescriptor
				descriptor =
				new ConversionDescriptor(converter.getSourceClass(), converter.getTargetClass());
			final TypeConverter<?, ?> alreadyRegistered = converterRegistry.put(descriptor, converter);

			if (alreadyRegistered != null) {
				throw new TypeConversionException(
					"Duplicate type converter found:[%s]->[%s], already registered:[%s], register candidate:[%s]",
					descriptor.srcClz, descriptor.targetClz, alreadyRegistered.getClass().getSimpleName(),
					converter.getClass().getSimpleName()
				);
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <S, T> T convert(final S source, final Class<T> targetClz) {
		assertNotNull(source, targetClz);

		final Class<?> sourceClz = source.getClass();

		final Optional<TypeConverter<?, ?>> targetConverter = getConverterFor(sourceClz, targetClz);

		if (targetConverter.isPresent()) {
			final TypeConverter<S, T> converter = (TypeConverter<S, T>) targetConverter.get();
			return converter.convert(source);
		} else if (fallbackConverter.support(sourceClz, targetClz)) {
			return fallbackConverter.convert(source, targetClz);
		}

		throw new TypeConversionException("Failed to find type converter for:[%s]->[%s] conversion",
			sourceClz, targetClz
		);
	}

	private Optional<TypeConverter<?, ?>> getConverterFor(final Class<?> sourceClz, final Class<?> targetClz) {
		return Optional.ofNullable(converterRegistry.get(new ConversionDescriptor(sourceClz, targetClz)));
	}

	private void assertNotNull(final Object source, final Class<?> targetClz) {
		if (source == null) {
			throw new TypeConversionException("Fail to convert value: source object can not be null");
		}
		if (targetClz == null) {
			throw new TypeConversionException("Fail to convert value: target class can not be null");
		}
	}

	static class ConversionDescriptor {

		private final Class<?> srcClz;

		private final Class<?> targetClz;

		ConversionDescriptor(final Class<?> srcClz, final Class<?> targetClz) {
			this.srcClz = srcClz;
			this.targetClz = targetClz;
		}

		public static ConversionDescriptor forPair(final Class<?> srcClz, final Class<?> targetClz) {
			return new ConversionDescriptor(srcClz, targetClz);
		}

		@Override
		public boolean equals(final Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			final ConversionDescriptor that = (ConversionDescriptor) o;

			return Objects.equals(srcClz, that.srcClz)
				&& Objects.equals(targetClz, that.targetClz);
		}

		@Override
		public int hashCode() {
			return Objects.hash(srcClz, targetClz);
		}
	}
}