import { useState, useEffect, useRef } from "react";

import { Grid, Box, Tabs, Tab, Typography } from "@mui/material";
import TabPanel from "./TabPanel";
import { useTheme } from "@mui/material";
import CustomButton from "../elements/CustomButton";
import { get } from "react-hook-form";
import useApi from "../../hooks/useApi";

const LocalizationPhotos = ({ localizationViews, localizationImagesUrl }) => {
  const theme = useTheme();

  const [localizationView, setLocalizationView] = useState(0);
  const [localizationImages, setLocalizationImages] = useState([]);
  const { getPhotoBase64ByUrl } = useApi();

  useEffect(() => {
    if (localizationImagesUrl) {
      fetchImages();
    }
  }, [localizationImagesUrl]);

  const handleChangeLocalizationView = (event, newValue) => {
    setLocalizationView(newValue);
  };

  const fetchImages = async () => {
    const localizationImgs = await Promise.all(
      localizationImagesUrl.map(async (element) => {
        const data = {
          url: element,
        };
        const img = await getPhotoBase64ByUrl(data);
        return img;
      })
    );

    console.log(localizationImgs);
    setLocalizationImages(localizationImgs);
  };

  function a11yProps(index) {
    return {
      id: `vertical-tab-${index}`,
      "aria-controls": `vertical-tabpanel-${index}`,
    };
  }

  return (
    <Grid item xs={12}>
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
          <Typography variant="h6">Localization Views</Typography>
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
                        src={localizationImages[index]}
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
                </Box>
              </TabPanel>
            ))}
        </Box>
      </Box>
    </Grid>
  );
};
export default LocalizationPhotos;
