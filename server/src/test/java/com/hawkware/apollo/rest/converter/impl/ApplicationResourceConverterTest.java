package com.hawkware.apollo.rest.converter.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hawkware.apollo.model.Application;
import com.hawkware.apollo.model.builder.impl.ApplicationBuilder;
import com.hawkware.apollo.model.builder.impl.PropertyBuilder;
import com.hawkware.apollo.rest.resources.ApplicationResource;

public class ApplicationResourceConverterTest {

	private ApplicationResourceConverter applicationResourceConverter;

	private PropertyResourceConverter propertyResourceConverter;

	@Before
	public void setUp() throws Exception {
		applicationResourceConverter = new ApplicationResourceConverter();
		propertyResourceConverter = new PropertyResourceConverter();
		applicationResourceConverter.setPropertyResourceConverter(propertyResourceConverter);
	}

	@Test
	public void testFromApplication() {

		Application application = new ApplicationBuilder().name("test-app")
				.property(new PropertyBuilder().name("test-prop").value("dev", "dev-value").build()).build();

		ApplicationResource appResource = applicationResourceConverter.from(application);

		assertEquals(application.getName(), appResource.getName());
		assertEquals(application.getProperties().size(), appResource.getProperties().size());

	}

	@Test
	public void testToApplicationResource() {
		Application expected = new ApplicationBuilder().name("test-app")
				.property(new PropertyBuilder().name("test-prop").value("dev", "dev-value").build()).build();

		ApplicationResource appResource = applicationResourceConverter.from(expected);

		assertEquals(expected.getName(), appResource.getName());
		assertEquals(expected.getProperties().size(), appResource.getProperties().size());

		Application actual = applicationResourceConverter.to(appResource);

		assertEquals(expected, actual);

	}

}
