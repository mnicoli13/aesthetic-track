// @mui
import { createTheme } from "@mui/material/styles";
//
import palette from "./palette";
import typography from "./typography";

let theme = createTheme({
  palette: {
    ...palette,
  },
  typography: {
    ...typography(palette),
  },
});

export default theme;
