// src/components/ui/Title.jsx
export function Title(props) {
    return (
        <h1 {...props} className={`text-center text-white fw-bold mb-4 h1 mt-4 ${props.className || ''}\``}></h1>
    )
}