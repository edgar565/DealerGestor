// src/App.tsx
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { PrivateRoute } from "./components/PrivateRoute";
import { PublicRoute } from "./components/PublicRoute";

import Login from "./components/Login";
import Dashboard from "./components/Dashboard";
import ClientManagement from "./components/ClientManagement.jsx";
import VehicleManagement from "./components/VehicleManagement.jsx";
import AppointmentManagement from "./components/AppointmentManagement.jsx";
import AccidentManagement from "./components/AccidentManagement.jsx";
import RepairManagement from "./components/RepairManagement.jsx";
import Note from "./components/Note.jsx";
import CompanyUserManagement from "./components/CompanyUserManagement.jsx";
import CompanyConfiguration from "./components/CompanyConfiguration.jsx";
import { setThemeConfig } from './utils/themeConfig.js';

setThemeConfig({
    primaryColor: '#0055FF',
    secondaryColor: '#222222',
    bgColor: '#F0F0F0'
});

function App() {
    return (
        <Router>
            <Routes>
                {/* Login pública */}
                <Route
                    path="/login"
                    element={
                        <PublicRoute>
                            <Login />
                        </PublicRoute>
                    }
                />

                {/* Rutas privadas */}
                <Route
                    path="/dashboard"
                    element={
                        <PrivateRoute>
                            <Dashboard />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/clientmanagement"
                    element={
                        <PrivateRoute>
                            <ClientManagement />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/vehiclemanagement"
                    element={
                        <PrivateRoute>
                            <VehicleManagement />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/appointmentmanagement"
                    element={
                        <PrivateRoute>
                            <AppointmentManagement />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/accidentmanagement"
                    element={
                        <PrivateRoute>
                            <AccidentManagement />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/repairmanagement"
                    element={
                        <PrivateRoute>
                            <RepairManagement />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/notes"
                    element={
                        <PrivateRoute>
                            <Note />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/configurationcompany"
                    element={
                        <PrivateRoute>
                            <CompanyConfiguration />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/usersmanagement"
                    element={
                        <PrivateRoute>
                            <CompanyUserManagement />
                        </PrivateRoute>
                    }
                />
                {/* Redirección de cualquier otra ruta a login */}
                <Route path="*" element={<Navigate to="/login" replace />} />
            </Routes>
        </Router>
    );
}

export default App;