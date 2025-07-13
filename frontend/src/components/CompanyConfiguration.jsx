import React, { useEffect, useState } from 'react';
import Header from './ui/Header';
import '../styles/management.css';
import { Button } from './ui/Button.jsx';
import axios from 'axios';
import { getApiUrl } from '../services/api.js';

const CompanyConfiguration = () => {
    const [formData, setFormData] = useState({
        id: '',
        primaryColor: '',
        secondaryColor: '',
        logoUrl: ''
    });
    const [logoFile, setLogoFile] = useState(null);


    useEffect(() => {
        fetchConfiguration();
    }, []);

    const fetchConfiguration = async () => {
        try {
            const companyId = 1; // o donde lo guardes
            const token = localStorage.getItem('token');

            const response = await axios.get(getApiUrl(`/company/${companyId}`), {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setFormData(response.data);
        } catch (error) {
            console.error('Error loading configuration:', error);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleFileChange = (e) => {
        setLogoFile(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');

            const form = new FormData();
            form.append('data', new Blob([JSON.stringify({
                id: formData.id,
                primaryColor: formData.primaryColor,
                secondaryColor: formData.secondaryColor
            })], { type: 'application/json' }));

            if (logoFile) {
                form.append('logo', logoFile);
            }

            const response = await axios.put(getApiUrl('/company/update'), form, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    Authorization: `Bearer ${token}`,
                },
            });

            setFormData(response.data);
            alert('Configuración actualizada correctamente');
        } catch (error) {
            console.error('Error updating configuration:', error);
            alert('Error al guardar los cambios');
        }
    };

    return (
        <div className="container">
            <Header title="CONFIGURACIÓN DE EMPRESA" />

            <form onSubmit={handleSubmit} className="mt-4">
                <div className="form-group mb-3">
                    <label className="form-label">Color Primario</label>
                    <input
                        type="color"
                        name="primaryColor"
                        className="form-control form-control-color border-black"
                        value={formData.primaryColor}
                        onChange={handleChange}
                    />
                </div>

                <div className="form-group mb-3">
                    <label className="form-label">Color Secundario</label>
                    <input
                        type="color"
                        name="secondaryColor"
                        className="form-control form-control-color border-black"
                        value={formData.secondaryColor}
                        onChange={handleChange}
                    />
                </div>

                <div className="form-group mb-3">
                    <label className="form-label">Logo Actual</label><br />
                    {formData.logoUrl ? (
                        <img src={formData.logoUrl} alt="Logo Empresa" style={{ maxWidth: '200px' }} className="border border-2 rounded" />
                    ) : (
                        <p className="text-muted">No hay logo cargado.</p>
                    )}
                </div>

                <div className="form-group mb-3">
                    <label className="form-label">Subir nuevo logo</label>
                    <input type="file" className="form-control border-black" accept="image/*" onChange={handleFileChange} />
                </div>

                <div className="d-flex justify-content-end">
                    <Button type="submit" className="btn btn-success">
                        <i className="fa-solid fa-save me-2"></i>GUARDAR CAMBIOS
                    </Button>
                </div>
            </form>
        </div>
    );
};

export default CompanyConfiguration;
