// @mui
import { Box, Typography, Skeleton, Grid } from "@mui/material";
import { useTheme } from "@mui/material/styles";
import useVisitStore from "../../stores/useVisitStore";
import LocalizationPhotosView from "../elements/LocalizationPhotosView";

const VisitData = ({ localizationViews, localizationImagesUrl }) => {
  const theme = useTheme();

  const visit = useVisitStore((state) => state.visit);

  return (
    <Box
      sx={{
        backgroundColor: theme.palette.main.grey,
        mt: 4,
      }}
    >
      <Grid
        container
        sx={{
          display: "flex",
        }}
      >
        <Grid
          item
          xs={5}
          display={"flex"}
          sx={{
            backgroundColor: theme.palette.main.white,
            alignItems: "center",
          }}
        >
          {visit ? (
            <LocalizationPhotosView
              localizationId={visit.localizationId}
              localizationViews={localizationViews}
              localizationImagesUrl={localizationImagesUrl}
              viewMode
            />
          ) : (
            <Skeleton variant="rectangular" width={180} height={200} />
          )}
        </Grid>

        <Grid item xs={7} sx={{ flexGrow: 2, px: 3, display: "flex" }}>
          <Box sx={{ display: "flex", flexDirection: "column" }}>
            {visit && visit.visitType && (
              <Box sx={{ pt: 2 }}>
                <Typography variant="h5">Visita {visit.visitType}</Typography>
              </Box>
            )}
            {visit && visit.id && (
              <Box sx={{ pt: 2 }}>
                <Typography
                  sx={{
                    textTransform: "uppercase",
                  }}
                >
                  ID: {visit.id}
                </Typography>
              </Box>
            )}

            {visit && visit.date && (
              <Box sx={{ pt: 2 }}>
                <Typography>
                  Data visita:{" "}
                  {visit.date[2] + "/" + visit.date[1] + "/" + visit.date[0]}
                </Typography>
              </Box>
            )}
            {visit && visit.localizationName && (
              <Box sx={{ pt: 2 }}>
                <Typography>
                  Localizzazione trattamento: {visit.localizationName}
                </Typography>
              </Box>
            )}
            {visit && visit.notes && (
              <Box sx={{ pt: 2 }}>
                <Typography>Descrizione visita: {visit.notes}</Typography>
              </Box>
            )}

            {visit && visit.products && (
              <Box display={"flex"} sx={{ pt: 2 }}>
                <Typography>
                  Prodotti utilizzati:{" "}
                  {visit.products.map((product) => product.name).join(", ")}
                </Typography>
              </Box>
            )}
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};
export default VisitData;
