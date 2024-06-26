package ua.igorg.userfusion.config.datasources;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author igorg
 * @create 02.06.2024
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(final String name, final EncodedResource encodedResource)
		throws IOException {
		final YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
		factory.setResources(encodedResource.getResource());

		final Properties properties = factory.getObject();

		return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
	}
}
