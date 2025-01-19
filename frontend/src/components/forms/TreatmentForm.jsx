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

const TreatmentForm = ({ localizations }) => {
  const [isSavingData, setIsSavingData] = useState(false);
  const patient = useVisitStore((state) => state.patient);
  const treatment = useVisitStore((state) => state.treatment);
  const { saveTreatment, updateTreatment } = useApi();

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm();

  useEffect(() => {
    if (treatment) {
      setValue("motivationTitle", treatment.motivationTitle);
      setValue("motivationDesc", treatment.motivationDesc);
      setValue("localizationId", treatment.localizationId);
    }
  }, []);

  // Funzione per gestire l'invio del modulo
  const onSubmit = async (values) => {
    try {
      setIsSavingData(true);

      if (treatment) {
        await handleUpdateTreatment(values);
      } else {
        await handleCreateTreatment(values);
      }

      setTimeout(() => {
        window.location.href = `/patients/view/${patient.id}`;
      }, 1000);
    } catch (error) {
      console.log("error", error);
    } finally {
      setIsSavingData(false);
    }
  };

  const handleCreateTreatment = async (values) => {
    try {
      const data = {
        patientId: patient.id,
        motivationTitle: values.motivationTitle,
        localizationId: values.localizationId,
        motivationDesc: values.motivationDesc,
      };

      await saveTreatment(data);
    } catch (error) {
      throw new Error(error);
    }
  };

  const handleUpdateTreatment = async (values) => {
    try {
      const data = {
        id: treatment.id,
        patientId: patient.id,
        motivationTitle: values.motivationTitle,
        localizationId: values.localizationId,
        motivationDesc: values.motivationDesc,
      };

      await updateTreatment(data);
    } catch (error) {
      throw new Error(error);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box sx={{ mt: 4 }}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Grid container spacing={2}>
            {/* Tipo trattamento */}
            <Grid item xs={12}>
              <TextField
                label="Tipo trattamento"
                fullWidth
                {...register("motivationTitle", {
                  required: "Il tipo di trattamento è obbligatorio",
                })}
                error={!!errors.motivationTitle}
                helperText={errors.motivationTitle?.message}
              />
            </Grid>

            {/* Motivazione intervento */}
            <Grid item xs={12}>
              <TextField
                label="Motivazione Intervento"
                fullWidth
                multiline
                rows={4}
                {...register("motivationDesc")}
              />
            </Grid>

            {/* Localizzazione */}
            {localizations && (
              <Grid item xs={12}>
                <FormControl fullWidth>
                  <InputLabel id="localizationId-label">
                    Localizzazione
                  </InputLabel>
                  <Select
                    labelId="localizationId-label"
                    label="Localizzazione"
                    defaultValue={treatment ? treatment.localizationId : ""}
                    {...register("localizationId", {
                      required: "Il genere è obbligatorio",
                    })}
                    error={!!errors.localizationId}
                  >
                    {localizations.map((loc) => (
                      <MenuItem key={loc.id} value={loc.id}>
                        {loc.name}
                      </MenuItem>
                    ))}
                  </Select>
                  {errors.localizationId && (
                    <FormHelperText error>
                      {errors.localizationId?.message}
                    </FormHelperText>
                  )}
                </FormControl>
              </Grid>
            )}

            {/* SUBMIT BUTTON */}
            <Grid item xs={12} sx={{ mt: 2 }}>
              <CustomButton
                label={
                  treatment
                    ? "Aggiorna trattamento"
                    : "Inserisci nuovo trattamento"
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

export default TreatmentForm;
