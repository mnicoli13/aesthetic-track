// @mui
import { Box, Typography, Link, IconButton } from "@mui/material";
import { useTheme } from "@mui/material/styles";
import { Home, ArrowRightAlt } from "@mui/icons-material"; // Importa le icone

const Breadcrumb = ({ path }) => {
  const theme = useTheme();

  return (
    <Box sx={{ display: "flex", alignItems: "center", mb: 5 }}>
      <Box component={Link} href="/">
        <IconButton color={theme.palette.main.secondary} aria-label="home">
          <Home />
        </IconButton>
      </Box>

      {path &&
        path.map((el, index) => {
          return (
            <Box sx={{ display: "flex", alignItems: "center" }} key={index}>
              <Box
                component={el.path ? Link : "span"}
                sx={{
                  color: theme.palette.main.primary,
                  textDecoration: "none",
                }}
                href={el.path}
              >
                <Typography
                  variant="bodyXXS"
                  sx={{
                    ml: 2,
                    lineHeight: 1,
                    color: theme.palette.main.primary,
                  }}
                >
                  {el.name}
                </Typography>
              </Box>

              {index !== path.length - 1 && (
                <IconButton color={theme.palette.main.secondary} sx={{ ml: 2 }}>
                  <ArrowRightAlt />
                </IconButton>
              )}
            </Box>
          );
        })}
    </Box>
  );
};

export default Breadcrumb;
