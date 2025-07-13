import { createContext, useState } from 'react';

export const AppContext = createContext();

export const AppProvider = ({ children }) => {
    const [estado, setEstado] = useState(null);

    return (
        <AppContext.Provider value={{ estado, setEstado }}>
            {children}
        </AppContext.Provider>
    );
};