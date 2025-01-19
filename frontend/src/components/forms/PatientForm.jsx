import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import useApi from "../../hooks/useApi";
import {
  TextField,
  Grid,
  Box,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  FormHelperText,
} from "@mui/material";
import { Container } from "@mui/system";
import CustomButton from "../elements/CustomButton";
// stores
import useVisitStore from "../../stores/useVisitStore";

const PatientForm = () => {
  const [isSavingData, setIsSavingData] = useState(false);
  const patient = useVisitStore((state) => state.patient);
  const { savePatient, updatePatient } = useApi();

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm();

  useEffect(() => {
    if (patient) {
      setValue("firstName", patient.firstName);
      setValue("lastName", patient.lastName);
      setValue("email", patient.email);

      setValue("dateOfBirth", patient.dateOfBirth);

      // setValue("gender", patient.gender);
      setValue("ethnicity", patient.ethnicity);
      setValue("previousHistory", patient.previousHistory);
    }
  }, []);

  // Funzione per gestire l'invio del modulo
  const onSubmit = async (values) => {
    try {
      setIsSavingData(true);

      let pat = null;
      if (patient) {
        pat = await handleUpdatePatient(values);
      } else {
        pat = await handleCreatePatient(values);
      }

      setTimeout(() => {
        window.location.href = `/patients/view/${pat.id}`;
      }, 1000);
    } catch (error) {
      console.log("error", error);
    } finally {
      setIsSavingData(false);
    }
  };

  const handleCreatePatient = async (values) => {
    try {
      const data = {
        createdBy: localStorage.getItem("userId"),
        firstName: values.firstName,
        lastName: values.lastName,
        email: values.email,
        password: values.password,
        dateOfBirth: values.dateOfBirth,
        gender: values.gender,
        ethnicity: values.ethnicity,
        previousHistory: values.previousHistory,
      };

      const pat = await savePatient(data);

      return pat;
    } catch (error) {
      throw new Error(error);
    }
  };

  const handleUpdatePatient = async (values) => {
    try {
      const data = {
        id: patient.id,
        createdBy: localStorage.getItem("userId"),
        firstName: values.firstName,
        lastName: values.lastName,
        email: values.email,
        password: values.password,
        dateOfBirth: values.dateOfBirth,
        gender: values.gender,
        ethnicity: values.ethnicity,
        previousHistory: values.previousHistory,
      };

      const pat = await updatePatient(data);

      return pat;
    } catch (error) {
      throw new Error(error);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box sx={{ mt: 4 }}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Grid container spacing={2}>
            {/* FIRST_NAME */}
            <Grid item xs={12}>
              <TextField
                label="Nome"
                fullWidth
                {...register("firstName", {
                  required: "Il nome è obbligatorio",
                })}
                error={!!errors.firstName}
                helperText={errors.firstName?.message}
              />
            </Grid>

            {/* LAST_NAME */}
            <Grid item xs={12}>
              <TextField
                label="Cognome"
                fullWidth
                {...register("lastName", {
                  required: "Il cognome è obbligatorio",
                })}
                error={!!errors.lastName}
                helperText={errors.lastName?.message}
              />
            </Grid>

            {/* EMAIL */}
            <Grid item xs={12}>
              <TextField
                label="Email"
                type="email"
                fullWidth
                {...register("email", {
                  required: "L'email è obbligatoria",
                  pattern: {
                    value: /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/,
                    message: "Email non valida",
                  },
                })}
                error={!!errors.email}
                helperText={errors.email?.message}
              />
            </Grid>

            {/* GENDER */}
            <Grid item xs={12}>
              <FormControl fullWidth>
                <InputLabel id="gender-label">Genere</InputLabel>
                <Select
                  labelId="gender-label"
                  label="Genere"
                  defaultValue={patient ? patient.gender : ""}
                  {...register("gender", {
                    required: "Il genere è obbligatorio",
                  })}
                  error={!!errors.gender}
                >
                  <MenuItem value="m">Maschio</MenuItem>
                  <MenuItem value="f">Femmina</MenuItem>
                </Select>
                {errors.gender && (
                  <FormHelperText error>
                    {errors.gender?.message}
                  </FormHelperText>
                )}
              </FormControl>
            </Grid>

            {/* DATE_OF_BIRTH */}
            <Grid item xs={12}>
              <TextField
                label="Data di Nascita"
                type="date"
                fullWidth
                // defaultValue={patient ? formatDate : ""}
                InputLabelProps={{
                  shrink: true,
                }}
                {...register("dateOfBirth", {
                  required: "La data di nascita è obbligatoria",
                })}
                error={!!errors.dateOfBirth}
                helperText={errors.dateOfBirth?.message}
              />
            </Grid>

            {/* ETHNICITY */}
            <Grid item xs={12}>
              <TextField
                label="Etnia"
                fullWidth
                {...register("ethnicity")}
                error={!!errors.ethnicity}
                helperText={errors.ethnicity?.message}
              />
            </Grid>

            {/* PREVIOUS_HISTORY */}
            <Grid item xs={12}>
              <TextField
                label="Storia Precedente"
                fullWidth
                multiline
                rows={4}
                {...register("previousHistory")}
              />
            </Grid>

            {/* PASSWORD */}
            {!patient && (
              <Grid item xs={12}>
                <TextField
                  label="Password"
                  type="password"
                  fullWidth
                  {...register("password", {
                    required: "La password è obbligatoria",
                  })}
                  error={!!errors.password}
                  helperText={errors.password?.message}
                />
              </Grid>
            )}

            {/* SUBMIT BUTTON */}
            <Grid item xs={12} sx={{ mt: 2 }}>
              <CustomButton
                label={
                  patient ? "Aggiorna paziente" : "Inserisci nuovo paziente"
                }
                type="submit"
                disabled={isSavingData}
                isLoading={isSavingData}
              />
            </Grid>
          </Grid>
        </form>
      </Box>
    </Container>
  );
};

export default PatientForm;
