import { Container, Grid } from "@material-ui/core";
import React from "react";
import EventForm from "./components/EventForm";
import Header from "./components/Header";
import ReceivedEvent from "./components/ReceivedEvent";
import Users from "./components/Users";

const App = () => {
  const backendApiUri =
    process.env.BACKEND_API_URI || "http://localhost:8080/api";
  const sseUri = `${backendApiUri}/sse`;
  const usersUri = `${backendApiUri}/jph/users/todos`;
  const helloUri = `${backendApiUri}/hello`;
  const title = "Demo App";

  return (
    <Container maxWidth="sm">
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Header {...{ title, helloUri }} />
        </Grid>
        <Grid item xs={12}>
          <EventForm {...{ sseUri }} />
        </Grid>
        <Grid item xs={6}>
          <ReceivedEvent {...{ sseUri }} />
        </Grid>
        <Grid item xs={6}>
          <ReceivedEvent {...{ sseUri }} />
        </Grid>
        <Grid item xs={12}>
          <Users {...{ usersUri }} />
        </Grid>
      </Grid>
    </Container>
  );
};

export default App;
