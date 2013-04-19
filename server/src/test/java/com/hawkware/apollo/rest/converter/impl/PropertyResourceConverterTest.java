package com.hawkware.apollo.rest.converter.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hawkware.apollo.model.Property;
import com.hawkware.apollo.model.builder.impl.PropertyBuilder;
import com.hawkware.apollo.rest.resources.PropertyResource;
import com.hawkware.apollo.rest.resources.PropertyValueResource;

public class PropertyResourceConverterTest {

    PropertyResourceConverter converter;

    @Before
    public void setUp() throws Exception {
	converter = new PropertyResourceConverter();
    }

    @Test
    public void testFromPropertyString() {
	PropertyBuilder pb = new PropertyBuilder();
	Property prop = pb.name("test.prop").timeToLive(100).value("qa", "qa.value").value("dev", "dev.value").build();

	PropertyResource qaResource = converter.from(prop, "qa");

	assertEquals("test.prop", qaResource.getName());
	assertEquals(Long.valueOf(100), qaResource.getTimeToLive());
	assertEquals("qa", qaResource.getValue("qa").getEnvironment());
	assertEquals("qa.value", qaResource.getValue("qa").getValue());

	PropertyResource devResource = converter.from(prop, "dev");
	assertEquals("test.prop", devResource.getName());
	assertEquals(Long.valueOf(100), qaResource.getTimeToLive());
	assertEquals("dev", devResource.getValue("dev").getEnvironment());
	assertEquals("dev.value", devResource.getValue("dev").getValue());

    }

    @Test
    public void testToPropertyResource() {
	PropertyResource resource = new PropertyResource();
	resource.setName("test.prop");
	resource.addValue(new PropertyValueResource("live", "test.value.live"));
	resource.setTimeToLive(120L);

	Property property = converter.to(resource);
	assertEquals("test.prop", property.getName());
	assertEquals("test.value.live", property.getValue("live"));
	assertEquals(120, property.getTimeToLive());

    }

    @Test
    public void testFromCollectionOfPropertyString() {
	List<Property> properties = new ArrayList<Property>();

	PropertyBuilder pb1 = new PropertyBuilder();
	Property prop1 = pb1.name("prop.1t").timeToLive(1234).value("dev", "dev.value").value("qa", "qa.value")
		.value("live", "live.value").build();
	properties.add(prop1);

	PropertyBuilder pb2 = new PropertyBuilder();
	Property prop2 = pb2.name("prop.2e").timeToLive(1234).value("live", "live.value").build();
	properties.add(prop2);

	List<PropertyResource> resources = converter.from(properties);
	assertEquals(2, resources.size());
    }

    @Test
    public void testToCollectionOfPropertyResource() {
	List<PropertyResource> resources = new ArrayList<PropertyResource>();

	PropertyResource res1 = new PropertyResource("prop.1", 1200);
	PropertyValueResource pvr1 = new PropertyValueResource("live", "live.value");
	res1.addValue(pvr1);
	resources.add(res1);

	PropertyResource res2 = new PropertyResource("prop.1", 1300);
	PropertyValueResource pvr2 = new PropertyValueResource("qa", "qa.value");
	res2.addValue(pvr2);
	resources.add(res2);

	PropertyResource res3 = new PropertyResource("prop.2", 1400);
	PropertyValueResource pvr3 = new PropertyValueResource("live", "live.value");
	res2.addValue(pvr3);
	resources.add(res3);

	Collection<Property> properties = converter.to(resources);
	assertEquals(2, properties.size());
    }

}
