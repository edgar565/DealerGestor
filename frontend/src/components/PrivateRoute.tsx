// src/components/PrivateRoute.tsx
import { Navigate } from "react-router-dom";
import { useNavigate } from 'react-router-dom';

interface PrivateRouteProps {
    children: JSX.Element;
}

export const PrivateRoute = ({ children }: PrivateRouteProps) => {
    const token = localStorage.getItem("token");
    const navigate = useNavigate();

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    return (
        <>
            {children}

            <div className="position-fixed bottom-0 end-0 mb-3 me-3 z-50">
                <button
                    className="btn btn-dark d-flex align-items-center"
                    onClick={() => navigate('/notes')}
                    aria-label="Notas"
                >
                    <i className="fa-solid fa-note-sticky me-2"></i>Notas
                </button>
            </div>
        </>
    );
};