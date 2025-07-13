import React from 'react';
import Header from './ui/Header';
import Modal from './ui/Modal';
import Table from './ui/Table';
import '../styles/styles.css';
import '../styles/management.css';

const TaskManagement = () => {
    const buttons = [];


    const headersJose = ["MATRÍCULA", "MARCA", "MODELO", "ACCIONES"];
    const headersSaid = ["MATRÍCULA", "MARCA", "MODELO", "ACCIONES"];

    return (
        <div className="container">
            <Header title="GESTIÓN DE TAREAS" buttons={buttons} />

            <div className="tareas d-flex justify-content-between gap-5">
                <div className="tareasJose w-50">
                    <h1 className="text-white fw-bold text-center">JOSE</h1>
                    <Table headers={headersJose} id="tareasJose" />
                </div>
                <div className="tareasSaid w-50">
                    <h1 className="text-white fw-bold text-center">SAID</h1>
                    <Table headers={headersSaid} id="tareasSaid" />
                </div>
            </div>

            <Modal id="taskFormModal" title="Añadir/Editar Tarea">
                <form id="taskForm" onSubmit="saveTask(event)">
                    <input type="hidden" id="taskId" />
                    <div className="form-group mb-3">
                        <label htmlFor="taskDescription" className="form-label">DESCRIPCIÓN</label>
                        <textarea className="form-control border-black" id="taskDescription" rows="3" required></textarea>
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="taskDate" className="form-label">FECHA</label>
                        <input type="date" className="form-control border-black" id="taskDate" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="taskStatus" className="form-label">ESTADO</label>
                        <select className="form-control border-black" id="taskStatus" required>
                            <option value="" disabled selected>Selecciona un estado</option>
                            <option value="PENDIENTE">PENDIENTE</option>
                            <option value="EN PROCESO">EN PROCESO</option>
                            <option value="COMPLETADA">COMPLETADA</option>
                        </select>
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success">GUARDAR</button>
                    </div>
                </form>
            </Modal>

            <Modal id="confirmModal" title="¿ESTÁS SEGURO DE ELIMINAR ESTA TAREA?">
                <p id="taskInfo"></p>
                <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">CANCELAR</button>
                    <button type="button" className="btn btn-danger" id="confirmDeleteBtn" onClick="confirmDelete()">ACEPTAR</button>
                </div>
            </Modal>
        </div>
    );
};

export default TaskManagement;
