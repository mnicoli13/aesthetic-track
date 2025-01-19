// utils
import axios from "../utils/axios";

export default function useApi() {
  /**
   * Patients APIs
   */
  const getPatientsByDoctor = async (doctorId) => {
    const res = await axios.get(`/patient/listByDoctor/${doctorId}`);

    if (res.data.success) {
      return res.data.patients;
    }

    return false;
  };

  const savePatient = async (data) => {
    console.log(data);
    const res = await axios.post(`/patient/store`, data);

    if (res.data.success) {
      return res.data.patient;
    }

    return false;
  };

  const updatePatient = async (data) => {
    console.log(data);
    const res = await axios.post(`/patient/update`, data);

    if (res.data.success) {
      return res.data.patient;
    }

    return false;
  };

  const getPatient = async (patientID) => {
    const res = await axios.get(`/patient/${patientID}`);

    if (res.data.success) {
      return res.data.patient;
    }

    return false;
  };

  const getLocalizationsByGender = async (gender) => {
    const res = await axios.post(
      `/localization/getLocalizationsByGender`,
      gender
    );

    if (res.data.success) {
      return res.data.localizations;
    }

    return false;
  };

  const getLocalizationByTreatmentId = async (treatmentId) => {
    const res = await axios.get(
      `/localization/getLocalizationByTreatmentId/${treatmentId}`
    );

    if (res.data.success) {
      return res.data.localization;
    }

    return false;
  };

  /**
   * Localizations APIs
   */
  const saveLocalization = async (data) => {
    const res = await axios.post(`/localization/save`, data);

    if (res.data.success) {
      return res.data.localization;
    }

    return false;
  };

  const getLocalization = async (localizationId) => {
    const res = await axios.get(`/localization/${localizationId}`);

    if (res.data.success) {
      return res.data.localization;
    }

    return false;
  };

  /**
   * Treatments APIs
   */
  const saveTreatment = async (data) => {
    const res = await axios.post(`/treatment/store`, data);

    if (res.data.success) {
      return res.data.treatment;
    }

    return false;
  };

  const updateTreatment = async (data) => {
    const res = await axios.post(`/treatment/update`, data);

    if (res.data.success) {
      return res.data.treatment;
    }

    return false;
  };

  const getTreatment = async (treatmentId) => {
    const res = await axios.get(`/treatment/${treatmentId}`);

    if (res.data.success) {
      return res.data.treatment;
    }

    return false;
  };

  const getTreatmentsByPatientId = async (patientID) => {
    const res = await axios.get(`/treatment/listByPatient/${patientID}`);

    if (res.data.success) {
      return res.data.treatments;
    }

    return false;
  };

  /**
   * Visits APIs
   */
  const saveVisit = async (data) => {
    const res = await axios.post(`/visit/save`, data);

    if (res.data.success) {
      return res.data.visit;
    }

    return false;
  };

  const updateVisit = async (data) => {
    const res = await axios.post(`/visit/update`, data);

    if (res.data.success) {
      return res.data.visit;
    }

    return false;
  };

  const getVisitTypes = async () => {
    const res = await axios.get(`/visit/visitTypes`);

    if (res.data.success) {
      return res.data.visitTypes;
    }

    return false;
  };

  const getVisitsByPatientId = async (patientID) => {
    const res = await axios.get(`/visit/getVisitsByPatientId/${patientID}`);

    if (res.data.success) {
      return res.data.visits;
    }

    return false;
  };

  const getVisit = async (visitId) => {
    const res = await axios.get(`/visit/${visitId}`);
    if (res.data.success) {
      return res.data.visit;
    }

    return false;
  };

  const getVisitIdsByPatientId = async (patientID) => {
    const res = await axios.get(`/visit/getVisitIdsByPatientId/${patientID}`);
    if (res.data.success) {
      return res.data.visitIds;
    }

    return false;
  };

  const getProducts = async () => {
    const res = await axios.get(`/product/getProducts`);

    if (res.data.success) {
      return res.data.products;
    }

    return false;
  };

  const getLocalizationViews = async (localizationId) => {
    const res = await axios.get(
      `/localization-view/getLocalizationViewByLocalizationId/${localizationId}`
    );

    if (res.data.success) {
      return res.data.localizationViews;
    }

    return false;
  };

  const isVisitDataFilled = async (data) => {
    const res = await axios.post(`/visits/check-filled`, data);

    if (res.data.success) {
      return true;
    }

    return false;
  };

  /**
   * Photos APIs
   */
  const saveLocalizationPhoto = async (data) => {
    const res = await axios.post(`/photo/upload`, data);

    if (res.data.success) {
      return res.data.photo;
    }

    return false;
  };

  const getPhotoBase64ByUrl = async (data) => {
    const res = await axios.post(`/photo/getPhotoBase64ByUrl`, data);

    if (res.data.success) {
      return res.data.photo;
    }

    return false;
  };

  return {
    getPatientsByDoctor, // patients
    savePatient,
    updatePatient,
    getPatient,
    saveLocalization, // localizations
    getLocalizationsByGender,
    getLocalization,
    getLocalizationByTreatmentId,
    saveTreatment, // treatment
    getTreatment,
    updateTreatment,
    getTreatmentsByPatientId,
    saveVisit, // visits
    getVisit,
    getVisitIdsByPatientId,
    updateVisit,
    getVisitTypes,
    getVisitsByPatientId,
    getProducts, //products
    getLocalizationViews, //localizationViews
    saveLocalizationPhoto, // photos
    getPhotoBase64ByUrl,
    isVisitDataFilled,
  };
}
