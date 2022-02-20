package dev.westernpine.manager.properties;

import dev.westernpine.lib.properties.PropertiesFile;
import dev.westernpine.lib.properties.Property;
import dev.westernpine.lib.properties.PropertyField;
import dev.westernpine.lib.properties.PropertyFile;
import org.springframework.stereotype.Component;

@Component
public class SystemProperties extends PropertiesFile {

    @PropertyField
    public static final Property IDENTITY = new Property("identity", "WesternPine");

    public SystemProperties() throws Throwable {
        super("system.properties", PropertyFile.getDeclaredProperties(SystemProperties.class));
    }

    @Override
    public PropertyFile reload() throws Throwable {
        super.reload();
        return this;
    }
}
