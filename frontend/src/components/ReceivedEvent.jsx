import { Card, CardContent, Typography } from "@material-ui/core";
import React, { useEffect, useState } from "react";

const ReceivedEvent = ({ sseUri }) => {
  const [eventData, setEventData] = useState("");

  useEffect(() => {
    const sse = new EventSource(sseUri);
    sse.onmessage = (ev) => {
      const newEventData = JSON.parse(ev.data);
      if (newEventData === eventData) return;
      setEventData(JSON.parse(ev.data));
    };
    sse.onerror = (ev) => {
      console.error(ev);
      sse.close();
    };
    return () => {
      sse.close();
    };
  });

  return (
    <Card>
      <CardContent>
        <Typography variant="h5">Last Event Payload</Typography>
        <Typography variant="body1">
          {eventData ? eventData.payload : "No Event received yet"}
        </Typography>
      </CardContent>
    </Card>
  );
};

export default ReceivedEvent;
