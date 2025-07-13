import React from 'react';
import { useNavigate } from 'react-router-dom';
import Calendar from './ui/Calendar';
import Modal from './ui/Modal';
import '../styles/dashboard.css';
import {Title} from "./ui/Title.jsx";
import {Button} from "./ui/Button.jsx";

const Dashboard = () => {
    const navigate = useNavigate();

    return (
        <div className="container-dashboard">
            <Title> PANEL DE ADMINISTRACIÓN</Title>
            <div className={"d-grid gap-2 d-md-flex justify-content-md-center mb-4"}>
                <Button onClick={() => navigate("/appointmentmanagement")}>GESTIÓN DE CITAS</Button>
                <Button onClick={() => navigate("/repairmanagement")}>GESTIÓN DE REPARACIONES</Button>
                <Button onClick={() => navigate("/accidentmanagement")}>GESTIÓN DE SINIESTROS</Button>
                <Button onClick={() => navigate("/clientmanagement")}>GESTIÓN DE CLIENTES</Button>
                <Button onClick={() => navigate("/parts")}>GESTIÓN DE PARTES</Button>
            </div>

            <Calendar />

            <Modal id="eventModal" title="Detalles de la Cita">
                <p id="eventDetails"></p>
            </Modal>
        </div>
    );
};

export default Dashboard;
