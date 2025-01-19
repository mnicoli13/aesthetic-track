import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
// components
import Breadcrumb from "../../components/elements/Breadcrumb";
import PatientForm from "../../components/forms/PatientForm";
// hooks
import useApi from "../../hooks/useApi";
// stores
import useVisitStore from "../../stores/useVisitStore";
//
import { Typography } from "@mui/material";

const CreatePatient = () => {
  const { patientID } = useParams();
  const { getPatient } = useApi();

  const setPatient = useVisitStore((state) => state.setPatient);
  const patient = useVisitStore((state) => state.patient);

  const [loadingData, setLoadingData] = useState(true);

  const breadcrumbPath = [
    {
      path: "/patients/list",
      name: "Pazienti",
    },
    {
      path: null,
      name: "Inserisci nuovo paziente",
    },
  ];

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    if (patientID) {
      await fetchPatientData();
    } else {
      setLoadingData(false);
    }
  };

  /**
   * Retrieve patient data
   */
  const fetchPatientData = async () => {
    try {
      const res = await getPatient(patientID);
      setPatient(res);
      setLoadingData(false);
    } catch (error) {
      console.log("error", error);
    }
  };

  return (
    <>
      <Breadcrumb path={breadcrumbPath} />

      <Typography variant="h1" style={{ mb: 5, textAlign: "center" }}>
        {patient ? "Modifica paziente" : "Inserisci nuovo paziente"}
      </Typography>

      {!loadingData && <PatientForm />}
    </>
  );
};

export default CreatePatient;
