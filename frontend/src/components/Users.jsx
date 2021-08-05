import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Avatar,
  Card,
  CardContent,
  Grid,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Skeleton,
  Typography,
} from "@material-ui/core";
import {
  Assignment,
  AssignmentTurnedIn,
  Home,
  List as ListIcon,
  Mail,
  Person,
  Phone,
  Public,
} from "@material-ui/icons";
import React, { useEffect, useState } from "react";

const ReceivedEvent = ({ usersUri }) => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetch(usersUri)
      .then((response) => response.json())
      .then(setUsers)
      .catch(console.error);
  }, [usersUri]);

  const renderUserItem = (icon, text) => (
    <ListItem>
      <ListItemAvatar>
        <Avatar>{icon}</Avatar>
      </ListItemAvatar>
      <ListItemText primary={text} />
    </ListItem>
  );

  const renderTodoItem = (todo) => (
    <ListItem key={todo.id}>
      <ListItemAvatar>
        <Avatar>
          {todo.completed ? (
            <AssignmentTurnedIn color="disabled" />
          ) : (
            <Assignment color="primary" />
          )}
        </Avatar>
      </ListItemAvatar>

      <ListItemText
        style={todo.completed ? { textDecoration: "line-through" } : {}}
        primary={todo.title}
      />
    </ListItem>
  );

  const renderUser = (user) => {
    return (
      <Grid key={user.id} item xs={12}>
        <Card>
          <CardContent>
            <Accordion>
              <AccordionSummary>
                {renderUserItem(<Person />, user.name)}
              </AccordionSummary>
              <AccordionDetails>
                <List>
                  {renderUserItem(<Mail />, user.email)}
                  {renderUserItem(<Phone />, user.phone)}
                  {renderUserItem(<Home />, user.address.city)}
                  {renderUserItem(<Public />, user.website)}
                </List>
              </AccordionDetails>
            </Accordion>
            <Accordion>
              <AccordionSummary>
                {renderUserItem(<ListIcon />, "Todos")}
              </AccordionSummary>
              <AccordionDetails>
                <List>{user.todos.map((todo) => renderTodoItem(todo))}</List>
              </AccordionDetails>
            </Accordion>
          </CardContent>
        </Card>
      </Grid>
    );
  };

  const renderUsers = () => {
    return <>{users.map((user) => renderUser(user))}</>;
  };

  const renderSkeleton = (count) =>
    Array.from({ length: count }).map((_, index) => (
      <Grid key={index} item xs={12}>
        <Card>
          <CardContent>
            {[0, 1].map((key) => (
              <Accordion key={key}>
                <AccordionSummary>
                  {renderUserItem(
                    <Skeleton width={32} height={32} variant="circle" />,
                    <Skeleton width={`${40 / (key + 1)}%`} variant="text" />
                  )}
                </AccordionSummary>
              </Accordion>
            ))}
          </CardContent>
        </Card>
      </Grid>
    ));

  return (
    <Card>
      <CardContent>
        <Typography variant="h5">Users with Todos</Typography>
        <Grid container spacing={2}>
          {!users.length ? renderSkeleton(3) : renderUsers()}
        </Grid>
      </CardContent>
    </Card>
  );
};

export default ReceivedEvent;
