package de.vkb.alex.demo.kafka;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.streams.operators.ProcessorBuilder;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Publisher;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.functions.BiConsumer;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;

@ApplicationScoped
public class KafkaController {
  private static final Logger LOGGER = Logger.getLogger(KafkaController.class.getName());

  @Outgoing("number-gen")
  @SuppressWarnings("java:S1905")
  public Publisher<Integer> generate() {
    return Flowable
        .generate(() -> new Random(42), (BiConsumer<Random, Emitter<Integer>>) (r, e) -> e.onNext(r.nextInt(10)))
        .zipWith(Flowable.interval(1, TimeUnit.SECONDS), (number, interval) -> number);
  }

  @Incoming("number-gen")
  @Outgoing("even-numbers")
  public ProcessorBuilder<Integer, Integer> evenNumbersFilter() {
    return ReactiveStreams.<Integer>builder().filter(i -> i % 2 == 0);
  }

  @Incoming("even-numbers")
  @Outgoing("to-kafka")
  public ProcessorBuilder<Message<Integer>, KafkaRecord<String, Integer>> toKafka() {
    return ReactiveStreams.<Message<Integer>>builder().map(i -> KafkaRecord.of("my-key", i.getPayload()));
  }

  @Incoming("from-kafka")
  @Outgoing("consumerRecord")
  public Publisher<String> fromKafka(ConsumerRecord<Object, Integer> consumerRecord) {
    return ReactiveStreams.of(consumerRecord).map(ConsumerRecord::toString).buildRs();
  }

  @Incoming("consumerRecord")
  public CompletionStage<Void> log(String consumerRecord) {
    return CompletableFuture.runAsync(() -> LOGGER.info(consumerRecord));
  }
}
