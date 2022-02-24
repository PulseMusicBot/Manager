package dev.westernpine.manager.properties;

import dev.westernpine.lib.properties.PropertiesFile;
import dev.westernpine.lib.properties.Property;
import dev.westernpine.lib.properties.PropertyField;
import dev.westernpine.lib.properties.PropertyFile;

public class IdentityProperties extends PropertiesFile {

    @PropertyField
    public static final Property IDENTITY_TOKENS = new Property("identity tokens", "h8AVoMwlPj6IkZD20D8D=WesternPine");

    @PropertyField
    public static final Property PREMIUM_MASTER = new Property("premium master", "WesternPine");

    public IdentityProperties(String identity) throws Throwable {
        super(identity + ".properties", PropertyFile.getDeclaredProperties(IdentityProperties.class));
    }

    @Override
    public PropertyFile reload() throws Throwable {
        super.reload();
        return this;
    }
}
