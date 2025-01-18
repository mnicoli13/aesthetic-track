import { createBrowserRouter } from "react-router-dom";
import { Navigate } from "react-router-dom";
//layouts
import LoginLayout from "../layouts/LoginLayout";
import MainLayout from "../layouts/MainLayout";
//guards
import GuestGuard from "../components/guards/GuestGuard";
import AuthGuard from "../components/guards/AuthGuard";
//pages
import Login from "../pages/auth/Login";
import PatientsList from "../pages/patients/PatientsList";
import CreatePatient from "../pages/patients/CreatePatient";
import ViewPatient from "../pages/patients/ViewPatient";
import CreateTreatment from "../pages/treatments/CreateTreatment";
import CreateVisit from "../pages/visits/CreateVisit";
import ViewVisit from "../pages/visits/ViewVisit";

const router = createBrowserRouter([
  // Auth Routes
  {
    path: "/auth",
    element: (
      <GuestGuard>
        <LoginLayout />
      </GuestGuard>
    ),
    children: [
      { element: <Navigate to="/auth/login" replace />, index: true },
      {
        path: "login",
        element: <Login />,
      },
    ],
  },
  // Patients Routes
  {
    path: "/patients",
    element: (
      <AuthGuard>
        <MainLayout />
      </AuthGuard>
    ),
    children: [
      { element: <Navigate to="/patients/list" replace />, index: true },
      {
        path: "list",
        element: <PatientsList />,
      },
      {
        path: "create",
        element: <CreatePatient />,
      },
      {
        path: "view/:patientID",
        element: <ViewPatient />,
      },
      {
        path: "edit/:patientID",
        element: <CreatePatient />,
      },
    ],
  },
  // Treatments Routes
  {
    path: "/treatments",
    element: (
      <AuthGuard>
        <MainLayout />
      </AuthGuard>
    ),
    children: [
      {
        path: "create/:patientID",
        element: <CreateTreatment />,
      },
      {
        path: "edit/:treatmentID",
        element: <CreateTreatment />,
      },
    ],
  },
  // Visits Routes
  {
    path: "/visits",
    element: (
      <AuthGuard>
        <MainLayout />
      </AuthGuard>
    ),
    children: [
      {
        path: "create/:treatmentID",
        element: <CreateVisit />,
      },
      {
        path: "edit/:visitID",
        element: <CreateVisit />,
      },
      {
        path: "view/:visitID",
        element: <ViewVisit />,
      },
    ],
  },
  // Default Routes
  {
    path: "/",
    element: <Navigate to="/patients/list" replace />,
    index: true,
  },
  // {
  //   path: "*",
  //   element: <div>404</div>,
  // },
]);

export default router;
