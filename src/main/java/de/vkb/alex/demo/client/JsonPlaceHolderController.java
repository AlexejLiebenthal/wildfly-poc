package de.vkb.alex.demo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import de.vkb.alex.demo.client.dto.Comment;
import de.vkb.alex.demo.client.dto.Todo;
import de.vkb.alex.demo.client.dto.User;

@ApplicationScoped
@Path("/jph")
@Produces(MediaType.APPLICATION_JSON)
@SuppressWarnings("java:S1135")
public class JsonPlaceHolderController {
  @RestClient
  @Inject
  JsonPlaceHolderClient jsonPlaceHolderClient;

  @GET
  @Path("comments")
  // Response to List of Comment via JaxRS Generic Type
  public CompletionStage<List<Comment>> getCommentsAsync() {
    return jsonPlaceHolderClient.getCommentsAsync().thenApply(response -> response.readEntity(new GenericType<>() {
    }));
  }

  @GET
  @Path("comments/{id}")
  // Response to Single Comment via JaxRS Class Type
  public CompletionStage<Comment> getCommentAsync(@PathParam("id") int id) {
    return jsonPlaceHolderClient.getCommentAsync(id).thenApply(response -> response.readEntity(Comment.class));
  }

  @GET
  @Path("todos")
  @SuppressWarnings("java:S2133")
  // Response to List of Todo via Json-B
  public CompletionStage<List<Todo>> getTodosAsync() {
    return jsonPlaceHolderClient.getTodosAsync()
        .thenApply(response -> JsonbBuilder.create().fromJson(response.readEntity(String.class), new ArrayList<Todo>() {
        }.getClass().getGenericSuperclass()));
  }

  @GET
  @Path("todos/{id}")
  // Response to Single Todo via Json-B
  public CompletionStage<Todo> getTodoAsync(@PathParam("id") int id) {
    return jsonPlaceHolderClient.getTodoAsync(id)
        .thenApply(response -> JsonbBuilder.create().fromJson(response.readEntity(String.class), Todo.class));
  }

  @GET
  @Path("users")
  // List of User to List of User
  public CompletionStage<List<User>> getUsersAsync() {
    return jsonPlaceHolderClient.getUsersAsync();
  }

  @GET
  @Path("users/{id}")
  // Single User to Response
  public CompletionStage<Response> getUserAsync(@PathParam("id") int id) {
    return jsonPlaceHolderClient.getUserAsync(id).thenApply(user -> Response.ok(user).build());
  }

  @GET
  @Path("users/todos")
  // Merge Users with Todos
  public CompletionStage<Response> getUsersWithTodosAsync() {
    var usersTask = getUsersAsync();
    var todosTask = getTodosAsync()
        .thenApply(todos -> todos.stream().collect(Collectors.groupingBy(todo -> todo.userId)));

    return usersTask.thenCombine(todosTask, (users, todos) -> users.stream().map(user -> {
      user.todos = todos.get(user.id);
      return user;
    }).collect(Collectors.toList())).thenApply(Response::ok).thenApply(ResponseBuilder::build);
  }
}
