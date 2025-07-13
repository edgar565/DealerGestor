// src/components/ui/Select.jsx
import React from 'react';

export function Select({ children, className = '', ...props }) {
    return (
        <select
            {...props}
            className={`w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring ${className}`}
        >
            {children}
        </select>
    );
}
