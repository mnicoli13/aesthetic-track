import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
// components
import Breadcrumb from "../../components/elements/Breadcrumb";
import VisitForm from "../../components/forms/VisitForm";
// hooks
import useApi from "../../hooks/useApi";
// stores
import useVisitStore from "../../stores/useVisitStore";
//
import { Typography } from "@mui/material";

const CreateVisit = () => {
  const { visitID, treatmentID } = useParams();
  const {
    getTreatment,
    getVisit,
    getLocalizationsByGender,
    getPatient,
    getVisitTypes,
    getProducts,
    getLocalizationViews,
  } = useApi();

  const setTreatment = useVisitStore((state) => state.setTreatment);
  const treatment = useVisitStore((state) => state.treatment);

  const setPatient = useVisitStore((state) => state.setPatient);
  const patient = useVisitStore((state) => state.patient);

  const setVisit = useVisitStore((state) => state.setVisit);
  const visit = useVisitStore((state) => state.visit);

  const [loadingData, setLoadingData] = useState(true);
  const [localizations, setLocalizations] = useState([]);
  const [visitTypes, setVisitTypes] = useState([]);
  const [products, setProducts] = useState([]);
  const [localizationViews, setLocalizationViews] = useState([]);

  const breadcrumbPath = [
    {
      path: "/patients/list",
      name: "Pazienti",
    },
    // {
    //   path: `/patients/view/${patientID}`,
    //   name: "Paziente",
    // },
    {
      path: null,
      name: "Inserisci nuova visita",
    },
  ];

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    if (treatment) {
      fetchLocalizationViews(treatment.localizationId);
    }
  }, [treatment]);

  const fetchData = async () => {
    try {
      if (visitID) {
        const vis = await fetchVisitData();
        await fetchTreatmentData(vis.treatmentId);
      } else {
        await fetchTreatmentData(treatmentID);
      }
      await fetchVisitTypes();
      await fetchProducts();
      setLoadingData(false);
    } catch (error) {
      console.log("error", error);
    }
  };

  const fetchVisitData = async () => {
    const res = await getVisit(visitID);
    console.log("res", res);
    setVisit(res);
    return res;
  };

  const fetchLocalizations = async () => {
    if (patient) {
      const res = await getLocalizationsByGender(patient.gender);
      setLocalizations(res);
    }
  };

  const fetchVisitTypes = async () => {
    const res = await getVisitTypes();
    setVisitTypes(res);
  };

  const fetchProducts = async () => {
    const res = await getProducts();
    setProducts(res);
  };

  const fetchLocalizationViews = async (localizationId) => {
    const res = await getLocalizationViews(localizationId);
    setLocalizationViews(res);
  };

  /**
   * Retrieve treatment data
   */
  const fetchTreatmentData = async (treatmentId) => {
    try {
      const treat = await getTreatment(treatmentId);

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

  //   /**
  //    * Retrieve patient data
  //    */
  //   const fetchVisitData = async () => {
  //     try {
  //       const pat = await getVisit(patientID);
  //       setPatient(pat);
  //       await fetchLocalizations();
  //     } catch (error) {
  //       console.log("error", error);
  //     }
  //   };

  return (
    <>
      <Breadcrumb path={breadcrumbPath} />

      <Typography variant="h1" style={{ mb: 5, textAlign: "center" }}>
        Inserisci nuova visita
      </Typography>

      {!loadingData && (
        <VisitForm
          localizations={localizations}
          visitTypes={visitTypes}
          products={products}
          localizationViews={localizationViews}
        />
      )}
    </>
  );
};

export default CreateVisit;
