// src/components/ui/Button.jsx
import React from 'react'
import clsx from 'clsx'

export function Button({
                           variant = 'primary',
                           className,
                           children,
                           ...props
                       }) {
    // 1) Todo lo común para *todos* los botones:
    const baseStyles = clsx(
        'btn hover:outline-none hover:ring hover:ring-offset-1',
        'border border-transparent',
        'text-white hover:border-white',
        'hover:ring-white btn-md btn-md-md',
        className
    )

    const variantStyles = {
        primary: 'bg-primary',
        secondary: 'bg-secondary',
        success: 'bg-success',
        danger:  'bg-danger',
    }

    return (
        <button
            className={clsx(baseStyles, variantStyles[variant])}
            {...props}
        >
            {children}
        </button>
    )
}
