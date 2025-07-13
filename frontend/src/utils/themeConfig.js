let themeConfig = {
    primaryColor: '#BD1522',
    secondaryColor: '#1f1f1f',
    logo: 'public/DealerGestor-logo_edited.jpg'
};

export const setThemeConfig = (config) => {
    themeConfig = { ...themeConfig, ...config };
    applyThemeToCSSVars(themeConfig);
};

export const getThemeConfig = () => themeConfig;

// Función que aplica los colores del objeto JS a las variables CSS
const applyThemeToCSSVars = (config) => {
    const root = document.documentElement;

    if(config.primaryColor) {
        root.style.setProperty('--color-primary', config.primaryColor);
        root.style.setProperty('--btn-color', config.primaryColor); // si usas esa variable en CSS
    }

    if(config.secondaryColor) {
        root.style.setProperty('--color-secondary', config.secondaryColor);
    }
};