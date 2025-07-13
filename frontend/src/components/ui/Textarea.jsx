// src/components/ui/Textarea.jsx
import React from 'react'

export const Textarea = ({ value, onChange, ...props }) => (
    <textarea
        value={value}
        onChange={onChange}
        className="w-full rounded-xl border border-gray-300 p-2 text-black focus:outline-none focus:ring-2 focus:ring-red-500"
        {...props}
    />
)
