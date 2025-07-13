import React, { useState } from 'react';
import { Button } from "./ui/Button.jsx";
import { Input } from "./ui/Input.jsx";
import '../styles/styles.css';
import { useNavigate } from 'react-router-dom';
import { setThemeConfig } from '../utils/themeConfig';
import axios from "axios";
import { getApiUrl } from "../services/api.js";

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // 1. Hacer login
            const response = await axios.post(getApiUrl('/auth/login'), {
                username,
                password
            });

            const data = response.data;
            localStorage.setItem('token', data.token);

            // 2. Obtener configuración de la empresa
            const configResponse = await axios.get(getApiUrl('/company/1'), {
                headers: {
                    Authorization: `Bearer ${data.token}`
                }
            });

            if (configResponse.status === 200) {
                const configData = configResponse.data;
                setThemeConfig({
                    primaryColor: configData.primaryColor,
                    secondaryColor: configData.secondaryColor,
                    logo: configData.logoUrl
                });
            } else {
                console.warn('No se pudo cargar la configuración del tema');
            }

            navigate('/dashboard');

        } catch (err) {
            console.error('Error al iniciar sesión:', err);
            alert('Credenciales inválidas o error de conexión');
        }
    };

    return (
        <main className="d-flex align-items-center py-5">
            <div className="form-signin m-auto bg-white rounded shadow px-5">
                <form onSubmit={handleSubmit} className="p-5">
                    <div className="d-flex align-items-center justify-content-center">
                        <img
                            className="mb-4"
                            src="../../public/logo_dealergestor.svg"
                            alt="Logo DealerGestor"
                            width="200"
                            height="200"
                        />
                    </div>
                    <h1 className="text-center text-black font-bold mb-4 text-2xl mt-1">
                        Iniciar Sesión
                    </h1>

                    <div className="form-floating mb-3">
                        <Input
                            type="text"
                            className="form-control"
                            id="floatingInput"
                            placeholder="Usuario"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                        <label htmlFor="floatingInput">Usuario</label>
                    </div>

                    <div className="form-floating">
                        <Input
                            type="password"
                            className="form-control"
                            id="floatingPassword"
                            placeholder="Contraseña"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        <label htmlFor="floatingPassword">Contraseña</label>
                    </div>

                    <Button className="btn btn-primary w-100 py-2 mt-5" type="submit">
                        Iniciar Sesión
                    </Button>
                </form>
            </div>
        </main>
    );
};

export default Login;