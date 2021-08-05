import { Typography } from "@material-ui/core";
import React, { useEffect, useState } from "react";

const Header = ({ title, helloUri }) => {
  const [helloMessage, setHelloMessage] = useState("");

  useEffect(() => {
    fetch(helloUri)
      .then((response) => response.text())
      .then(setHelloMessage)
      .catch(console.error);
  }, [helloUri]);
  return (
    <>
      <Typography variant="h1" style={{ textAlign: "center" }}>
        {title}
      </Typography>
      <Typography variant="subtitle1" style={{ textAlign: "center" }}>
        {helloMessage}
      </Typography>
    </>
  );
};

export default Header;
