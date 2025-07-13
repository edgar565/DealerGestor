// src/components/ui/Input.jsx
export function Input(props) {
    return (
        <input
            {...props}
            className={`w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring ${props.className || ''}`}
        />
    )
}
