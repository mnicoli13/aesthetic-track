// @mui
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
} from "@mui/material";
import { useTheme } from "@mui/material/styles";
import { useEffect } from "react";
import CustomButton from "../elements/CustomButton";

const PatientsTable = ({ patients }) => {
  useEffect(() => {
    console.log(patients);
  }, [patients]);
  const theme = useTheme();
  return (
    <TableContainer
      component={Paper}
      sx={{
        mt: 4,
        boxShadow: 3,
        borderRadius: 2,
        overflow: "hidden",
      }}
    >
      <Typography
        variant="h5"
        align="center"
        gutterBottom
        sx={{
          mt: 2,
          fontWeight: "bold",
          color: theme.palette.main.primary,
          backgroundColor: theme.palette.main.secondary,
          padding: 2,
          borderRadius: "4px 4px 0 0",
        }}
      >
        Lista Pazienti
      </Typography>
      <Table>
        <TableHead>
          <TableRow sx={{ backgroundColor: theme.palette.main.primary }}>
            <TableCell sx={{ color: "#FFFFFF", fontWeight: "bold" }}>
              Nome
            </TableCell>
            <TableCell sx={{ color: "#FFFFFF", fontWeight: "bold" }}>
              Cognome
            </TableCell>
            <TableCell sx={{ color: "#FFFFFF", fontWeight: "bold" }}>
              Email
            </TableCell>
            <TableCell sx={{ color: "#FFFFFF", fontWeight: "bold" }}>
              Genere
            </TableCell>
            <TableCell sx={{ color: "#FFFFFF", fontWeight: "bold" }}>
              Data di Nascita
            </TableCell>
            <TableCell sx={{ color: "#FFFFFF", fontWeight: "bold" }}>
              Etnia
            </TableCell>
            <TableCell sx={{ color: "#FFFFFF", fontWeight: "bold" }}>
              Storia Precedente
            </TableCell>
            <TableCell sx={{ color: "#FFFFFF", fontWeight: "bold" }}>
              Modifica
            </TableCell>
            <TableCell sx={{ color: "#FFFFFF", fontWeight: "bold" }}>
              Visite
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {patients &&
            patients.map((patient) => (
              <TableRow
                key={patient.id}
                sx={{
                  backgroundColor: theme.palette.main.grey,
                  "&:nth-of-type(even)": {
                    backgroundColor: theme.palette.main.primary,
                  },
                }}
              >
                <TableCell sx={{ color: "white" }}>
                  {patient.firstName}
                </TableCell>
                <TableCell sx={{ color: "white" }}>
                  {patient.lastName}
                </TableCell>
                <TableCell sx={{ color: "white" }}>{patient.email}</TableCell>
                <TableCell sx={{ color: "white" }}>{patient.gender}</TableCell>
                <TableCell sx={{ color: "white" }}>
                  {patient.dateOfBirth}
                </TableCell>
                <TableCell sx={{ color: "white" }}>
                  {patient.ethnicity}
                </TableCell>
                <TableCell sx={{ color: "white" }}>
                  {patient.previousHistory}
                </TableCell>
                <TableCell sx={{ color: "white" }}>
                  <CustomButton
                    label={"Modifica"}
                    tertiary
                    onClick={() => {
                      window.location.href = `/patients/edit/${patient.id}`;
                    }}
                  />
                </TableCell>
                <TableCell sx={{ color: "white" }}>
                  <CustomButton
                    label={"Visite"}
                    tertiary
                    onClick={() => {
                      window.location.href = `/patients/view/${patient.id}`;
                    }}
                  />
                </TableCell>
              </TableRow>
            ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default PatientsTable;
