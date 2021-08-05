package de.vkb.alex.demo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class JsonbProducer {

  @Produces
  Jsonb jsonbProducer() {
    return JsonbBuilder.create();
  }
}
