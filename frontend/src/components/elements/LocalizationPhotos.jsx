import { useState, useEffect, useRef } from "react";

import { Grid, Box, Tabs, Tab, Typography } from "@mui/material";
import TabPanel from "./TabPanel";
import { useTheme } from "@mui/material";
import CustomButton from "../elements/CustomButton";

const LocalizationPhotos = ({
  localizationViews,
  localizationImages,
  setLocalizationImages,
  viewMode,
}) => {
  const theme = useTheme();

  const [localizationView, setLocalizationView] = useState(0);

  const handleChangeLocalizationView = (event, newValue) => {
    setLocalizationView(newValue);
  };

  /**
   * Event click trigger for input file
   */
  const handleLoadImageClick = (index) => {
    const input = document.createElement("input");
    input.type = "file";
    input.accept = "image/*";

    input.onchange = (event) => {
      const file = event.target.files[0];

      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          const newImage = {
            localizationImage: e.target.result,
            localizationViewId: localizationViews[index].id,
          };

          // Aggiorna l'array nello stato
          setLocalizationImages((prevImages) =>
            prevImages.map((item, i) => (i === index ? newImage : item))
          );
        };
        reader.readAsDataURL(file);
      }
    };

    input.click();
  };

  function a11yProps(index) {
    return {
      id: `vertical-tab-${index}`,
      "aria-controls": `vertical-tabpanel-${index}`,
    };
  }

  return (
    <Grid item xs={12} sx={{ pl: 2, pt: 2 }}>
      <Box
        sx={{
          border: "1px solid rgba(0, 0, 0, 0.2)",
          borderRadius: "4px",
        }}
      >
        <Box
          sx={{
            borderBottom: "1px solid rgba(0, 0, 0, 0.2)",
            pl: 2,
            py: 1,
          }}
        >
          <Typography variant="h6">Localizzazione</Typography>
        </Box>
        <Box
          sx={{
            flexGrow: 1,
            bgcolor: "background.paper",
            py: 0,
          }}
        >
          <Tabs
            variant="scrollable"
            value={localizationView}
            onChange={handleChangeLocalizationView}
            aria-label="Vertical tabs example"
            TabIndicatorProps={{
              style: {
                backgroundColor: theme.palette.main.primary,
              },
            }}
            sx={{ borderBottom: "1px solid rgba(0, 0, 0, 0.2) !important" }}
          >
            {localizationViews &&
              localizationViews.map((loc, index) => (
                <Tab
                  key={index}
                  label={loc.name}
                  {...a11yProps(index)}
                  sx={{
                    alignItems: "start !important",
                    "&.Mui-selected": {
                      color: theme.palette.main.primary,
                      fontWeight: 600,
                    },
                  }}
                />
              ))}
          </Tabs>

          {localizationViews &&
            localizationViews.map((loc, index) => (
              <TabPanel key={index} value={localizationView} index={index}>
                <Box
                  display="flex"
                  sx={{
                    py: 1,
                    alignItems: "center",
                    flexDirection: "column",
                  }}
                >
                  <Typography variant="h6">{loc.name}</Typography>
                  {localizationImages[index] ? (
                    <Box
                      sx={{
                        width: "250px",
                        mt: 2,
                        mb: 2,
                        boxSizing: "border-box",
                        position: "relative",
                      }}
                    >
                      <img
                        src={localizationImages[index]?.localizationImage}
                        alt={`Localization ${loc.name}`}
                        style={{
                          width: "250px",
                          maxHeight: "300px",
                          objectFit: "contain",
                        }}
                      />
                    </Box>
                  ) : (
                    <Box
                      sx={{
                        width: "250px",
                        height: "300px",
                        mt: 2,
                        mb: 2,
                        backgroundColor: theme.palette.main.lightGrey,
                        boxSizing: "border-box",
                        border: "1px solid",
                        borderColor: theme.palette.main.grey,
                        position: "relative",
                      }}
                    />
                  )}

                  {!viewMode && (
                    <Box
                      display={"flex"}
                      sx={{ justifyContent: "center", pb: 1 }}
                    >
                      <CustomButton
                        label={"carica foto"}
                        secondary
                        notFullWidth
                        notHoverChange
                        onClick={() => handleLoadImageClick(index)}
                      />
                    </Box>
                  )}
                </Box>
              </TabPanel>
            ))}
        </Box>
      </Box>
    </Grid>
  );
};
export default LocalizationPhotos;
