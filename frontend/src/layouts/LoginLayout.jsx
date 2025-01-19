// @mui
import { Box } from "@mui/material";
//
import { Outlet } from "react-router-dom";

export default function LoginLayout() {
  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        width: "100vw",
        height: "100vh",
      }}
    >
      <Outlet />
    </Box>
  );
}
