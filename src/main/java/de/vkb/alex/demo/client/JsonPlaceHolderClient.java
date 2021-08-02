package de.vkb.alex.demo.client;

import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import de.vkb.alex.demo.client.dto.User;

@RegisterRestClient
@Produces(MediaType.APPLICATION_JSON)
public interface JsonPlaceHolderClient {
  @GET
  @Path("todos")
  public CompletionStage<Response> getTodosAsync();

  @GET
  @Path("todos/{id}")
  public CompletionStage<Response> getTodoAsync(@PathParam("id") int id);

  @GET
  @Path("comments")
  public CompletionStage<Response> getCommentsAsync();

  @GET
  @Path("comments/{id}")
  public CompletionStage<Response> getCommentAsync(@PathParam("id") int id);

  @GET
  @Path("users")
  public CompletionStage<List<User>> getUsersAsync();

  @GET
  @Path("users/{id}")
  public CompletionStage<User> getUserAsync(@PathParam("id") int id);
}
