package de.vkb.alex.demo.rest;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Timeout;

import de.vkb.alex.demo.SingleConverters;
import io.reactivex.Single;

@ApplicationScoped
@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
public class HelloController {
    @Inject
    @ConfigProperty(name = "name", defaultValue = "World")
    private String name;

    @GET
    public String sayHello() {
        return String.format("Hello %s", name);
    }

    @GET
    @Path("reactive")
    @Timeout(value = 1100, unit = ChronoUnit.MILLIS)
    @Asynchronous
    public CompletionStage<String> sayHelloReactive() {
        return Single.just(name).delay(1000, TimeUnit.MILLISECONDS).map(n -> String.format("Hello %s", n))
                .as(SingleConverters.asCompletionStage());
    }
}
