import React from 'react';
import { Link } from 'react-router-dom';
import {Title} from "./Title.jsx";

const Header = ({ title, leftButton, rightContent }) => {
    return (
        <div className="container">
            <Title>{title}</Title>
            <div className="mb-3 d-flex justify-content-between align-items-center">
                {/* Botones a la izquierda */}
                <div className="d-flex align-items-center gap-2">
                    <Link to="/" className="btn bg-secondary border-white text-white">
                        <i className="fa-solid fa-house"></i>
                    </Link>
                    {leftButton}
                </div>

                {/* Contenido a la derecha */}
                <div className={"d-flex"}>
                    {rightContent}
                </div>
            </div>
        </div>
    );
};

export default Header;
