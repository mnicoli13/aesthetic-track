// @mui
import { Box, Typography, Skeleton } from "@mui/material";
import { useTheme } from "@mui/material/styles";
import useVisitStore from "../../stores/useVisitStore";

const PatientData = ({ data }) => {
  const theme = useTheme();

  const patient = useVisitStore((state) => state.patient);

  return (
    <Box
      sx={{
        backgroundColor: theme.palette.main.grey,
        mt: 4,
      }}
    >
      <Box
        sx={{
          display: "flex",
        }}
      >
        <Box
          display={"flex"}
          sx={{
            backgroundColor: theme.palette.main.tertiary,
            alignItems: "center",
          }}
        >
          {patient ? (
            <Box
              component="img"
              src={
                patient.gender === "m"
                  ? "/images/patients/m1.svg"
                  : "/images/patients/f1.svg"
              }
              sx={{ width: "180px", px: 5 }}
            />
          ) : (
            <Skeleton variant="rectangular" width={180} height={200} />
          )}
        </Box>

        <Box sx={{ flexGrow: 2, p: 3 }}>
          <Box sx={{ display: "flex", flexDirection: "column" }}>
            {patient && patient.id && (
              <Box>
                <Typography
                  sx={{
                    textTransform: "uppercase",
                  }}
                >
                  ID: {patient.id}
                </Typography>
              </Box>
            )}

            {patient && patient.firstName && (
              <Box sx={{ pt: 2 }}>
                <Typography sx={{ textTransform: "capitalize" }}>
                  Nome {patient.firstName}
                </Typography>
              </Box>
            )}
            {patient && patient.lastName && (
              <Box sx={{ pt: 2 }}>
                <Typography sx={{ textTransform: "capitalize" }}>
                  Cognome: {patient.lastName}
                </Typography>
              </Box>
            )}
            {patient && patient.email && (
              <Box sx={{ pt: 2 }}>
                <Typography>Email: {patient.email}</Typography>
              </Box>
            )}

            {patient && patient.gender && (
              <Box display={"flex"} sx={{ pt: 2 }}>
                <Typography>Genere: {patient.gender}</Typography>
              </Box>
            )}

            {patient && patient.dateOfBirth && (
              <Box sx={{ pt: 2 }}>
                <Typography>Data di nascita: {patient.dateOfBirth}</Typography>
              </Box>
            )}

            {patient && patient.ethnicity && (
              <Box sx={{ pt: 2 }}>
                <Typography sx={{ textTransform: "capitalize" }}>
                  Etnia: {patient.ethnicity}
                </Typography>
              </Box>
            )}
          </Box>

          <Box>
            <Typography
              variant="h6"
              sx={{
                pt: 4,
                pb: 1,
                textTransform: "uppercase",
              }}
            >
              Storia precedente
            </Typography>

            {patient ? (
              <Typography>
                {patient.previousHistory
                  ? patient.previousHistory
                  : "Nessuna storia precedente"}
              </Typography>
            ) : (
              <Skeleton variant="rectangular" width={500} height={50} />
            )}
          </Box>

          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              flexGrow: 1,
              mt: 4,
            }}
          >
            {/* {data ? (
              <PatientDataVisitList data={data} patient={patient} />
            ) : (
              <PatientDataVisitLoading />
            )} */}
          </Box>
        </Box>
      </Box>
    </Box>
  );
};
export default PatientData;
