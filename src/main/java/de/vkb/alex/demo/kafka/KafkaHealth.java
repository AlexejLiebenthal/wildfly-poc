package de.vkb.alex.demo.kafka;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.json.bind.Jsonb;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.spi.Connector;

import io.smallrye.reactive.messaging.health.HealthReport;
import io.smallrye.reactive.messaging.kafka.KafkaConnector;

@ApplicationScoped
public class KafkaHealth {
  @Inject
  private Jsonb jsonb;

  @Inject
  @Connector(KafkaConnector.CONNECTOR_NAME)
  KafkaConnector kafkaConnector;

  @Produces
  @Liveness
  public HealthCheck checkKafkaLiveness() {
    return () -> toHealthCheck("kafka-liveness", kafkaConnector.getLiveness());
  }

  @Produces
  @Readiness
  public HealthCheck checkKafkaReadiness() {
    return () -> toHealthCheck("kafka-readiness", kafkaConnector.getReadiness());
  }

  HealthCheckResponse toHealthCheck(String name, HealthReport report) {
    var healthCheckResponseBuilder = HealthCheckResponse.named(name).status(report.isOk());

    report.getChannels().stream()
        .forEach(channel -> healthCheckResponseBuilder.withData(channel.toString(), jsonb.toJson(channel)));

    return healthCheckResponseBuilder.build();
  }
}
