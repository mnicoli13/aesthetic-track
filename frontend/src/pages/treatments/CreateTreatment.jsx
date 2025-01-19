import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
// components
import Breadcrumb from "../../components/elements/Breadcrumb";
import TreatmentForm from "../../components/forms/TreatmentForm";
// hooks
import useApi from "../../hooks/useApi";
// stores
import useVisitStore from "../../stores/useVisitStore";
//
import { Typography } from "@mui/material";

const CreateTreatment = () => {
  const { patientID, treatmentID } = useParams();
  const { getTreatment, getLocalizationsByGender, getPatient } = useApi();

  const setTreatment = useVisitStore((state) => state.setTreatment);
  const treatment = useVisitStore((state) => state.treatment);

  const setPatient = useVisitStore((state) => state.setPatient);
  const patient = useVisitStore((state) => state.patient);

  const [loadingData, setLoadingData] = useState(true);
  const [localizations, setLocalizations] = useState([]);

  const breadcrumbPath = [
    {
      path: "/patients/list",
      name: "Pazienti",
    },
    {
      path: `/patients/view/${patient ? patient.id : patientID}`,
      name: "Paziente",
    },
    {
      path: null,
      name: "Inserisci nuovo trattamento",
    },
  ];

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    fetchLocalizations();
  }, [patient]);

  const fetchData = async () => {
    if (treatmentID) {
      await fetchTreatmentData();
      setLoadingData(false);
    } else {
      await fetchPatientData();
      setLoadingData(false);
    }
  };

  const fetchLocalizations = async () => {
    if (patient) {
      const res = await getLocalizationsByGender(patient.gender);
      setLocalizations(res);
    }
  };

  /**
   * Retrieve treatment data
   */
  const fetchTreatmentData = async () => {
    try {
      const treat = await getTreatment(treatmentID);
      setTreatment(treat);
      if (treat) {
        const pat = await getPatient(treat.patientId);
        setPatient(pat);
        await fetchLocalizations();
      }
    } catch (error) {
      console.log("error", error);
    }
  };

  /**
   * Retrieve patient data
   */
  const fetchPatientData = async () => {
    try {
      const pat = await getPatient(patientID);
      setPatient(pat);
      await fetchLocalizations();
    } catch (error) {
      console.log("error", error);
    }
  };

  return (
    <>
      <Breadcrumb path={breadcrumbPath} />

      <Typography variant="h1" style={{ mb: 5, textAlign: "center" }}>
        {treatment ? "Modifica trattamento" : "Inserisci nuovo trattamento"}
      </Typography>

      {!loadingData && <TreatmentForm localizations={localizations} />}
    </>
  );
};

export default CreateTreatment;
