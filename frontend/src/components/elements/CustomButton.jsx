// @mui
import { Button } from "@mui/material";
import { useTheme } from "@mui/material/styles";

const CustomButton = ({
  label,
  disabled,
  onClick,
  secondary,
  tertiary,
  type,
  notFullWidth,
}) => {
  const theme = useTheme();

  const commonStyles = {
    padding: "10px 20px",
    fontWeight: 800,
    borderRadius: "0px",
    boxSizing: "border-box",
    "&:focus": {
      outline: "none",
    },
  };

  let styles = {
    color: `${theme.palette.main.white} !important`,
    backgroundColor: theme.palette.main.primary,
    border: "2px solid",
    borderColor: theme.palette.main.primary,
    "&:hover": {
      backgroundColor: theme.palette.main.secondary,
      border: "2px solid",
      borderColor: theme.palette.main.secondary,
    },
  };

  if (secondary) {
    styles = {
      color: `${theme.palette.main.primary} !important`,
      backgroundColor: "transparent",
      border: "2px solid",
      borderColor: theme.palette.main.primary,
      "&:hover": {
        border: "2px solid",
        borderColor: theme.palette.main.secondary,
        color: `${theme.palette.main.secondary} !important`,
      },
    };
  }

  if (tertiary) {
    styles = {
      backgroundColor: "transparent",
      border: "2px solid",
      borderColor: "white",
      color: `white !important`,
      "&:hover": {
        backgroundColor: theme.palette.main.secondary,
        border: "2px solid",
        borderColor: theme.palette.main.secondary,
        color: `white !important`,
      },
    };
  }

  if (disabled) {
    styles = {
      ...styles,
      color: !secondary
        ? `${theme.palette.main.white} !important`
        : `${theme.palette.main.grey} !important`,
      backgroundColor: !secondary && theme.palette.main.grey,
      border: "2px solid",
      borderColor: theme.palette.main.grey,
    };
  }

  return (
    <Button
      variant="cta"
      sx={{
        ...commonStyles,
        ...styles,
      }}
      type={type}
      fullWidth={!notFullWidth ? true : false}
      disabled={disabled}
      onClick={onClick}
    >
      {label}
    </Button>
  );
};

export default CustomButton;
