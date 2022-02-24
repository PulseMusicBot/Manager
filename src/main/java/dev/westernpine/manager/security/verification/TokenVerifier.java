package dev.westernpine.manager.security.verification;

import dev.westernpine.lib.mapper.Mapper;
import dev.westernpine.manager.properties.IdentityProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TokenVerifier {

    private Map<String, String> tokenIdentities;

    public TokenVerifier(IdentityProperties identityProperties) {
        this.tokenIdentities = Mapper.fromString(identityProperties.get(IdentityProperties.IDENTITY_TOKENS), ",", "=");
    }

    /*
    This is an extremely basic authentication implementation.
    More professional implementations would usually take place, but this serves the need well enough.
    This project is not intended to be open to the internet.
     */
    public String getIdentity(String token) throws VerificationException {
        if (token == null)
            throw new VerificationException("Verification failed. No token present.");
        String identity = tokenIdentities.get(token);
        if (identity == null)
            throw new VerificationException("Verification failed. Invalid token.");
        return identity;
    }

}
