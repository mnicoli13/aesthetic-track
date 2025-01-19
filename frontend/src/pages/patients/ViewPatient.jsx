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
import PatientData from "../../components/patients/PatientData";
//stores
import useVisitStore from "../../stores/useVisitStore";
import { set } from "react-hook-form";
import LocalizationPhotos from "../../components/elements/LocalizationPhotos";
import VisitData from "../../components/patients/VisitData";

const ViewPatient = () => {
  const theme = useTheme();
  const {
    getPatient,
    getTreatmentsByPatientId,
    getLocalizationByTreatmentId,
    getVisitIdsByPatientId,
    getVisit,
    getLocalization,
  } = useApi();
  const setPatient = useVisitStore((state) => state.setPatient);
  const setTreatments = useVisitStore((state) => state.setTreatments);
  const setVisits = useVisitStore((state) => state.setVisits);
  const setVisit = useVisitStore((state) => state.setVisit);

  const visits = useVisitStore((state) => state.visits);
  const treatments = useVisitStore((state) => state.treatments);
  const { patientID } = useParams();

  const breadcrumbPath = [
    {
      path: "/patients/list",
      name: "I miei pazienti",
    },
    {
      path: null,
      name: "Scheda paziente",
    },
  ];

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      // patient
      const patient = await getPatient(patientID);
      setPatient(patient);

      // treatments
      const treatments = await getTreatmentsByPatientId(patient.id);
      setTreatments(treatments);

      const visits = await getVisitIdsByPatientId(patient.id);

      if (visits && visits.length > 0) {
        const visitDetails = await Promise.all(
          visits.map(async (visit) => {
            const visitDetail = await getVisit(visit);
            if (visitDetail) {
              const loc = await getLocalization(visitDetail.localizationId);
              visitDetail.localizationName = loc.name;

              if (visitDetail.photos) {
                const locViews = visitDetail.photos.map(
                  (photo) => photo.localizationView
                );
                visitDetail.localizationViews = locViews;

                const phs = visitDetail.photos.map((photo) => photo.image);
                visitDetail.localizationImagesUrl = phs;
              }
            }
            return visitDetail;
          })
        );
        console.log("visitDetails", visitDetails);

        setVisits(visitDetails);
      }

      // treatment details
      if (treatments && treatments.length > 0) {
        const updatedTreatments = await Promise.all(
          treatments.map(async (treatment) => {
            const localization = await getLocalizationByTreatmentId(
              treatment.id
            );
            return { ...treatment, localization };
          })
        );

        setTreatments(updatedTreatments);
      }
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

  const handleAddTreatmentClick = () => {
    window.location.href = `/treatments/create/${patientID}`;
  };

  const handleAddVisitClick = (treatmentID) => {
    window.location.href = `/visits/create/${treatmentID}`;
  };

  return (
    <>
      <Breadcrumb path={breadcrumbPath} />

      <Box>
        <Box sx={{ display: "flex", justifyContent: "space-between" }}>
          <Typography variant="h1">Scheda paziente</Typography>

          <Box display="flex" sx={{ gap: "20px" }}>
            <Box display="flex" sx={{ alignItems: "end" }}>
              <CustomButton
                notFullWidth
                label={"delete"}
                secondary
                onClick={handleDeletePatientClick}
              />
            </Box>
            <Box display="flex" sx={{ alignItems: "end" }}>
              <CustomButton
                notFullWidth
                label={"edit"}
                secondary
                onClick={handleEditPatientClick}
              />
            </Box>
          </Box>
        </Box>

        <PatientData /*data={treatmentsDetails}*/ />
      </Box>

      <Box sx={{ mt: 5 }}>
        <Box sx={{ display: "flex", justifyContent: "space-between" }}>
          <Typography variant="h1">Trattamenti</Typography>
          <Box display="flex" sx={{ alignItems: "end" }}>
            <CustomButton
              notFullWidth
              label={"Inserisci nuovo trattamento"}
              onClick={handleAddTreatmentClick}
            />
          </Box>
        </Box>

        {treatments && treatments.length > 0 ? (
          <Box display="flex" sx={{ pt: 4, flexDirection: "column" }}>
            {treatments.map((treatment) => (
              <Accordion
                key={treatment.id}
                sx={{ width: "100%" }}
                defaultExpanded
              >
                <AccordionSummary
                  expandIcon={<ExpandMoreIcon />}
                  sx={{
                    backgroundColor: theme.palette.main.grey,
                    minHeight: "60px",
                    py: 1,
                    pl: 4,
                    borderBottom: `1px solid ${theme.palette.main.primary}`,
                  }}
                >
                  <Box
                    display={"flex"}
                    sx={{ width: "100%", justifyContent: "space-between" }}
                  >
                    <Box
                      sx={{
                        display: "flex",
                        flexDirection: "column",
                        gap: 3,
                      }}
                    >
                      <Typography
                        variant="h3"
                        sx={{ color: theme.palette.main.black }}
                      >
                        {treatment.motivationTitle}
                      </Typography>
                    </Box>
                    <Box display={"flex"}>
                      <Box display="flex" sx={{ alignItems: "start" }}>
                        <CustomButton
                          notFullWidth
                          label={"delete"}
                          secondary
                          onClick={handleDeleteTreatmentClick}
                        />
                      </Box>
                      <Box
                        display="flex"
                        sx={{ alignItems: "start", pl: 3, pr: 3 }}
                      >
                        <CustomButton
                          notFullWidth
                          label={"edit"}
                          secondary
                          onClick={() => handleEditTreatmentClick(treatment.id)}
                        />
                      </Box>
                    </Box>
                  </Box>
                </AccordionSummary>
                <AccordionDetails sx={{ p: 0 }}>
                  <Box
                    sx={{
                      display: "flex",
                      flexDirection: "column",
                      gap: 3,
                      py: 3,
                      px: 4,
                      backgroundColor: theme.palette.main.grey,
                      borderBottom: `1px solid ${theme.palette.main.primary}`,
                    }}
                  >
                    <Box display={"flex"} sx={{ flexDirection: "column" }}>
                      <Typography variant="bodyBold">Area trattata:</Typography>
                      <Typography variant="body">
                        {treatment.localization?.name}
                      </Typography>
                    </Box>
                    <Box display={"flex"} sx={{ flexDirection: "column" }}>
                      <Typography variant="bodyBold">
                        Motivazione intervento:
                      </Typography>
                      <Typography variant="body">
                        {treatment.motivationDesc}
                      </Typography>
                    </Box>
                    <Box display={"flex"} sx={{ flexDirection: "row" }}>
                      <Box display="flex" sx={{ alignItems: "end" }}>
                        <CustomButton
                          notFullWidth
                          label={"Inserisci nuova visita"}
                          onClick={() => handleAddVisitClick(treatment.id)}
                        />
                      </Box>
                    </Box>
                  </Box>

                  <Box>
                    {visits &&
                      visits.length > 0 &&
                      visits.map((visit) => (
                        <>
                          {visit.treatmentId === treatment.id && (
                            <VisitData
                              key={visit.id}
                              localizationImagesUrl={
                                visit.localizationImagesUrl
                              }
                              localizationViews={visit.localizationViews}
                              visit={visit}
                            />
                          )}
                        </>
                      ))}
                  </Box>
                </AccordionDetails>
              </Accordion>
            ))}
          </Box>
        ) : (
          <Box display="flex" sx={{ pt: 4 }}>
            <Typography>Nessun trattamento inserito</Typography>
          </Box>
        )}
      </Box>
    </>
  );
};

export default ViewPatient;
