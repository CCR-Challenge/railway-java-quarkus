package challenge.futurefocus.infrastructure;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class MyApiKeyFilter implements ContainerRequestFilter {

    @Inject
    @org.eclipse.microprofile.config.inject.ConfigProperty(name = "api.key")
    String apiKey;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String apiKeyRequest = requestContext.getHeaderString("X-API-Key");

        if (apiKeyRequest == null || !apiKeyRequest.equals(apiKey)) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("API key is missing or invalid")
                            .build()
            );
        }
    }
}
