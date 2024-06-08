package ua.igorg.userfusion.api.v1.converter;

/**
 * Type converter that can potentially support type conversion for several type pairs
 *
 * @Author igorg
 * @create 03.06.2024
 */
public interface CommonTypeConverter {

	<S, T> boolean support(Class<S> sourceClz, Class<T> targetClz);

	<S, T> T convert(S source, Class<T> targetClz);
}