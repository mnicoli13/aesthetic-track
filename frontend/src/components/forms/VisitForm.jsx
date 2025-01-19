import { useState, useEffect, useRef } from "react";
import { useForm, useWatch } from "react-hook-form";
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
  Chip,
} from "@mui/material";
import { Container } from "@mui/system";
import CustomButton from "../elements/CustomButton";
// stores
import useVisitStore from "../../stores/useVisitStore";
import LocalizationPhotos from "../elements/LocalizationPhotos";

const VisitForm = ({
  visitTypes,
  localizations,
  products,
  localizationViews,
}) => {
  const [isSavingData, setIsSavingData] = useState(false);
  const [localizationImages, setLocalizationImages] = useState([]);
  const [preselectedProductIds, setPreselectedProductIds] = useState([]);

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
    control,
  } = useForm();

  const selectedProducts =
    useWatch({ name: "products", control }) || preselectedProductIds;

  const patient = useVisitStore((state) => state.patient);
  const treatment = useVisitStore((state) => state.treatment);
  const visit = useVisitStore((state) => state.visit);

  const { saveVisit, updateVisit, saveLocalizationPhoto } = useApi();

  useEffect(() => {
    if (visit) {
      if (visit.date) {
        const [year, month, day] = visit.date;
        const formatDate = `${year}-${month}-${day}`;
        setValue("date", formatDate);
      }

      if (visit.products) {
        const productIds = visit.products.map((product) => product.id);
        setPreselectedProductIds(productIds);
      }
      setValue("notes", visit.notes);
    }
  }, []);

  useEffect(() => {
    if (localizationViews) {
      initLocalizationImages();
    }
  }, [localizationViews]);

  // Funzione per gestire l'invio del modulo
  const onSubmit = async (values) => {
    try {
      setIsSavingData(true);

      let newVisit = null;
      if (visit) {
        newVisit = await handleUpdateVisit(values);
      } else {
        newVisit = await handleCreateVisit(values);
      }

      if (newVisit && localizationImages) {
        console.log("localizationImages", localizationImages);
        localizationImages.map((localizationImage) => {
          console.log("localizationImage", localizationImage);
          if (localizationImage) {
            handleSaveLocalizationPhoto(localizationImage, newVisit.id);
          }
        });
      }

      setTimeout(() => {
        window.location.href = `/visits/view/${newVisit.id}`;
      }, 1000);
    } catch (error) {
      console.log("error", error);
    } finally {
      setIsSavingData(false);
    }
  };

  const initLocalizationImages = () => {
    const initArray = Array.from(
      { length: localizationViews.length },
      () => null
    );

    setLocalizationImages(initArray);
  };

  const handleCreateVisit = async (values) => {
    try {
      const data = {
        treatmentId: treatment.id,
        date: values.date,
        visitType: values.visitType,
        notes: values.notes,
        productIds: values.products,
      };

      return await saveVisit(data);
    } catch (error) {
      throw new Error(error);
    }
  };

  const handleUpdateVisit = async (values) => {
    try {
      const data = {
        id: visit.id,
        treatmentId: treatment.id,
        date: values.date,
        visitType: values.visitType,
        notes: values.notes,
        productIds: values.products,
      };

      return await updateVisit(data);
    } catch (error) {
      throw new Error(error);
    }
  };

  const handleSaveLocalizationPhoto = async (localizationImage, visitId) => {
    try {
      const formData = new FormData();

      formData.append("visitId", visitId);
      formData.append(
        "localizationViewId",
        localizationImage.localizationViewId
      );
      formData.append("photo", localizationImage.localizationImage);

      console.log("formData", formData);

      await saveLocalizationPhoto(formData);
    } catch (error) {
      throw new Error(error);
    }
  };
  return (
    <Container maxWidth="sm">
      <Box sx={{ mt: 4 }}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Grid container spacing={2}>
            {/* Visit date */}
            <Grid item xs={12}>
              <TextField
                label="Data visita"
                type="date"
                fullWidth
                InputLabelProps={{
                  shrink: true,
                }}
                {...register("date", {
                  required: "La data della visita è obbligatoria",
                })}
                error={!!errors.date}
                helperText={errors.date?.message}
              />
            </Grid>

            {/* VisitType */}
            {visitTypes && (
              <Grid item xs={12}>
                <FormControl fullWidth>
                  <InputLabel id="visitType-label">Tipo di visita</InputLabel>
                  <Select
                    labelId="visitType-label"
                    label="Tipo di visita"
                    defaultValue={visit ? visit.visitType : ""}
                    {...register("visitType", {
                      required: "Il tipo di visita è obbligatorio",
                    })}
                    error={!!errors.visitType}
                  >
                    {visitTypes.map((visitType, index) => (
                      <MenuItem key={index} value={visitType}>
                        {visitType}
                      </MenuItem>
                    ))}
                  </Select>
                  {errors.visitType && (
                    <FormHelperText error>
                      {errors.visitType?.message}
                    </FormHelperText>
                  )}
                </FormControl>
              </Grid>
            )}

            {/* Products */}
            {products && (
              <Grid item xs={12}>
                <FormControl fullWidth>
                  <InputLabel id="products-label">
                    Prodotti utilizzati
                  </InputLabel>
                  <Select
                    labelId="products-label"
                    label="Localizzazione"
                    defaultValue={preselectedProductIds}
                    multiple
                    {...register("products", {
                      required: "Il prodotti sono obbligatorii",
                    })}
                    error={!!errors.products}
                    MenuProps={{
                      PaperProps: {
                        style: {
                          maxHeight: 200, // Altezza massima del menu
                          overflowY: "auto", // Abilita lo scroll
                        },
                      },
                    }}
                    renderValue={(selected) => (
                      <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
                        {selected.map((id) => {
                          const product = products.find((p) => p.id === id);
                          return (
                            <Chip
                              key={id}
                              label={product?.name || ""}
                              sx={{ borderRadius: "16px" }} // Chip ovale
                            />
                          );
                        })}
                      </Box>
                    )}
                  >
                    {products.map((product) => (
                      <MenuItem key={product.id} value={product.id}>
                        {product.name}
                      </MenuItem>
                    ))}
                  </Select>
                  {errors.products && (
                    <FormHelperText error>
                      {errors.products?.message}
                    </FormHelperText>
                  )}
                </FormControl>
              </Grid>
            )}

            {/* Descrizione Visita */}
            <Grid item xs={12}>
              <TextField
                label="Descrizione Visita"
                fullWidth
                multiline
                rows={4}
                {...register("notes")}
              />
            </Grid>

            {/* Localization views */}
            {localizationViews && (
              <LocalizationPhotos
                localizationViews={localizationViews}
                localizationImages={localizationImages}
                setLocalizationImages={setLocalizationImages}
              />
            )}

            {/* SUBMIT BUTTON */}
            <Grid item xs={12} sx={{ mt: 2 }}>
              <CustomButton
                label={"Inserisci nuova visita"}
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

export default VisitForm;
