import React, { useEffect, useState } from 'react';
import Header from './ui/Header';
import Modal from './ui/Modal';
import Table from './ui/Table';
import '../styles/management.css';
import { Button } from './ui/Button.jsx';
import axios from 'axios';
import { getApiUrl } from '../services/api.js';

const NoteManagement = () => {
    const [notes, setNotes] = useState([]);
    const token = localStorage.getItem('token');

    useEffect(() => {
        fetchNotes();
    }, []);

    const fetchNotes = async () => {
        try {
            const response = await axios.get(getApiUrl('/notes'), {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                }
            });
            setNotes(response.data);
        } catch (error) {
            console.error('Error fetching notes:', error);
        }
    };

    const saveNote = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const data = Object.fromEntries(formData.entries());

        try {
            await axios.post(getApiUrl('/notes/save'), JSON.stringify(data), {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            event.target.reset();
            fetchNotes();
            document.querySelector('#noteFormModal .btn-close').click();
        } catch (error) {
            console.error('Error saving note:', error);
        }
    };

    const confirmDelete = async () => {
        const id = document.getElementById('noteId').value;

        try {
            await axios.delete(getApiUrl(`/notes/${id}`), {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            fetchNotes();
            document.querySelector('#confirmModal .btn-close').click();
        } catch (error) {
            console.error('Error deleting note:', error);
        }
    };

    const leftButton = [
        <Button key="add" data-bs-toggle="modal" data-bs-target="#noteFormModal">
            <i className="fa-solid fa-plus me-2"></i>AÑADIR NOTA
        </Button>
    ];

    const headers = ['FECHA', 'TÍTULO', 'TEXTO', 'ACCIONES'];

    // Añadir keys a cada nota para evitar el warning de React
    const tableData = notes.map(note => ({
        ...note,
        key: note.id // necesario para evitar el warning de key
    }));

    return (
        <div className="container">
            <Header title="GESTIÓN DE NOTAS" leftButton={leftButton} />

            <Modal id="noteFormModal" title="Añadir/Editar Nota">
                <form id="noteForm" onSubmit={saveNote}>
                    <input type="hidden" id="noteId" name="id" />
                    <div className="form-group mb-3">
                        <label htmlFor="title" className="form-label">TÍTULO</label>
                        <input type="text" className="form-control border-black" id="title" name="title" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="text" className="form-label">TEXTO</label>
                        <textarea className="form-control border-black" id="text" name="text" rows="4" required></textarea>
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success">GUARDAR</button>
                    </div>
                </form>
            </Modal>

            <Modal id="confirmModal" title="¿ESTÁS SEGURO DE ELIMINAR ESTA NOTA?">
                <p id="noteInfo"></p>
                <input type="hidden" id="noteId" />
                <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">CANCELAR</button>
                    <button type="button" className="btn btn-danger" onClick={confirmDelete}>ELIMINAR</button>
                </div>
            </Modal>

            <Table headers={headers} data={tableData} onDelete={confirmDelete} />
        </div>
    );
};

export default NoteManagement;