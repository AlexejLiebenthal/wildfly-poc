import { yupResolver } from "@hookform/resolvers/yup";
import {
  Button,
  Card,
  CardActions,
  CardContent,
  TextField,
} from "@material-ui/core";
import React from "react";
import { useForm } from "react-hook-form";
import { object, string } from "yup";

const formSchema = object({
  payload: string().required(),
});

const EventForm = ({ sseUri }) => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({ resolver: yupResolver(formSchema) });

  const onSubmit = ({ payload }) => {
    if (!payload) return;

    fetch(sseUri, {
      method: "POST",
      headers: {
        "Content-Type": "text/plain",
      },
      body: payload,
    }).catch(console.error);
    reset();
  };
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Card>
        <CardContent>
          <TextField
            label="Payload"
            placeholder="Some Payload..."
            fullWidth
            {...register("payload")}
            error={!!errors.payload}
            helperText={errors.payload?.message}
          />
        </CardContent>
        <CardActions>
          <Button type="submit" variant="text" color="primary">
            Send Event
          </Button>
        </CardActions>
      </Card>
    </form>
  );
};

export default EventForm;
