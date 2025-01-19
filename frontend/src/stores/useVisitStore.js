import { create } from "zustand";

const initialValues = {
  patient: null,
  treatments: null,
  treatment: null,
  visit: null,
  visits: null,
};

const useVisitStore = create((set) => ({
  ...initialValues,
  setPatient: (data) => set({ patient: data }),
  setTreatments: (data) => set({ treatments: data }),
  setTreatment: (data) => set({ treatment: data }),
  setVisit: (data) => set({ visit: data }),
  setVisits: (data) => set({ visits: data }),
}));

export default useVisitStore;
