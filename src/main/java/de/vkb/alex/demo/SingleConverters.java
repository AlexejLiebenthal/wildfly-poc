package de.vkb.alex.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import io.reactivex.SingleConverter;

public class SingleConverters {
  private SingleConverters() {
  }

  public static <T> SingleConverter<T, CompletionStage<T>> asCompletionStage() {
    return single -> {
      var cf = new CompletableFuture<T>();
      single.subscribe(cf::complete, cf::completeExceptionally);
      return cf;
    };
  }
}
