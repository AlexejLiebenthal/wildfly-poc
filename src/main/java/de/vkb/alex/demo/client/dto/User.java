package de.vkb.alex.demo.client.dto;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

@SuppressWarnings({ "java:S1104" })
public class User {
  public int id;
  @JsonbProperty("name")
  public String fullname;
  public String username;
  public Address address;
  public String email;
  public String phone;
  public String website;
  public Company company;

  public List<Todo> todos;
}
