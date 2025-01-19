import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

// @mui
import {
  Box,
  Typography,
  Accordion,
  AccordionDetails,
  AccordionSummary,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useTheme } from "@mui/material/styles";
import useApi from "../../hooks/useApi";
// components
import CustomButton from "../../components/elements/CustomButton";
import Breadcrumb from "../../components/elements/Breadcrumb";
import VisitData from "../../components/visits/VisitData";
//stores
import useVisitStore from "../../stores/useVisitStore";
import { set } from "react-hook-form";

const ViewPatient = () => {
  const theme = useTheme();
  const { getVisit, getLocalization } = useApi();
  const setPatient = useVisitStore((state) => state.setPatient);
  const setVisit = useVisitStore((state) => state.setVisit);
  const visit = useVisitStore((state) => state.visit);
  const { visitID } = useParams();
  const [localizationViews, setLocalizationViews] = useState([]);
  const [localizationImagesUrl, setLocalizationImagesUrl] = useState([]);

  const breadcrumbPath = [
    {
      path: "/patients/list",
      name: "I miei pazienti",
    },
    {
      path: null,
      name: "Dettaglio visita",
    },
  ];

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      // visit
      const vis = await getVisit(visitID);

      if (vis) {
        const loc = await getLocalization(vis.localizationId);
        vis.localizationName = loc.name;

        if (vis.photos) {
          const locViews = vis.photos.map((photo) => photo.localizationView);
          setLocalizationViews(locViews);

          const phs = vis.photos.map((photo) => photo.image);
          setLocalizationImagesUrl(phs);
        }
      }
      setVisit(vis);
      console.log("vis", vis);
    } catch (error) {
      console.log("error", error);
    }
  };

  const handleEditPatientClick = () => {
    window.location.href = `/patients/edit/${patientID}`;
  };

  const handleEditTreatmentClick = (treatmentID) => {
    window.location.href = `/treatments/edit/${treatmentID}`;
  };

  const handleDeleteTreatmentClick = (treatmentID) => {
    // window.location.href = `/treatemnts/edit/${treatmentID}`;
  };

  const handleDeletePatientClick = async () => {
    // setOpenDeleteDialog(true);
  };

  const handleAddVisitClick = () => {
    window.location.href = `/visits/create/${visit?.treatmentId}`;
  };

  return (
    <>
      <Breadcrumb path={breadcrumbPath} />

      <Box>
        <Box sx={{ display: "flex", justifyContent: "space-between" }}>
          <Typography variant="h1">Riassunto visita</Typography>

          <Box display="flex" sx={{ gap: "20px" }}>
            <Box display="flex" sx={{ alignItems: "end" }}>
              <CustomButton
                notFullWidth
                label={"Inserisci nuova visita"}
                secondary
                onClick={handleAddVisitClick}
              />
            </Box>
          </Box>
        </Box>

        <VisitData
          localizationViews={localizationViews}
          localizationImagesUrl={localizationImagesUrl}
        />
      </Box>

      <Box sx={{ mt: 5 }}></Box>
    </>
  );
};

export default ViewPatient;
