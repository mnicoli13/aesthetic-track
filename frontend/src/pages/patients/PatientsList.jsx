import { useState, useEffect } from "react";
// @mui
import { Box, Typography } from "@mui/material";
import { useTheme } from "@mui/material/styles";

// components
// import CustomTitle from "../../components/elements/CustomTitle";
import CustomButton from "../../components/elements/CustomButton";
// import NoPatients from "./components/NoPatients";
import PatientsTable from "../../components/patients/PatientsTable.jsx";
// hooks
import useApi from "../../hooks/useApi";

const PatientsList = () => {
  const theme = useTheme();

  const { getPatientsByDoctor } = useApi();

  const [loading, setLoading] = useState(true);
  const [patients, setPatients] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    const userId = localStorage.getItem("userId");
    const r = await getPatientsByDoctor(userId);
    console.log("r", r);
    setLoading(false);
    setPatients(r);
    return;
  };

  const handleAddPatientClick = () => {
    window.location.href = "/patients/create";
  };

  return (
    <>
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <Box>
          <Typography variant="h1">I miei pazienti</Typography>
        </Box>

        <Box>
          <CustomButton
            label={"Inserisci nuovo paziente"}
            onClick={handleAddPatientClick}
          />
        </Box>
      </Box>
      {patients && !loading && patients.length === 0 ? (
        <Box
          display={"flex"}
          sx={{
            mt: 5,
            p: 5,
            justifyContent: "center",
          }}
        >
          <Typography
            variant="Bold"
            sx={{ color: theme.palette.main.grey, textAlign: "center" }}
          >
            Non ci sono pazienti da visualizzare, inserisci un nuovo paziente
          </Typography>
        </Box>
      ) : (
        <PatientsTable patients={patients} />
      )}
    </>
  );
};

export default PatientsList;
