import React from 'react';

const Table = ({ headers, id }) => {
    return (
        <table className="table table-bordered border-white table-hover mt-4">
            <thead>
            <tr className="text-center">
                {headers.map((header, index) => (
                    <th key={index} className="bg-secondary border-white text-white">{header}</th>
                ))}
            </tr>
            </thead>
            <tbody id={id} className="scrollable-tbody"></tbody>
        </table>
    );
};

export default Table;