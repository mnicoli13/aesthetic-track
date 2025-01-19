// utils
import axios from "../utils/axios";

export default function useApi() {
  const savePatient = async (data) => {
    const res = await axios.post(`/patients/create`, data);

    if (res.data.success) {
      return res.data.patient;
    }

    return false;
  };

  return {
    savePatient,
  };
}
