package de.vkb.alex.demo.sse;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

@Path("sse")
@ApplicationScoped
public class SseController {
  @Inject
  @Channel("emitter")
  Emitter<EventData> emitter;

  @Inject
  @Channel("emitter")
  private Publisher<EventData> publisher;

  @POST
  @Consumes(MediaType.TEXT_PLAIN)
  public CompletionStage<Void> post(String payload) {
    return emitter.send(new EventData(payload));
  }

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseElementType(MediaType.APPLICATION_JSON)
  public Publisher<EventData> eventStream() {
    return publisher;
  }

  @SuppressWarnings("java:S1104")
  public static class EventData {
    public String payload;

    public EventData(String payload) {
      this.payload = payload;
    }
  }
}
