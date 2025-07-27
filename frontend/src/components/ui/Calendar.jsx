import React, { useRef, useEffect, useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import Modal from './Modal';
import 'bootstrap/dist/js/bootstrap.bundle';
import 'bootstrap/dist/css/bootstrap.min.css';
import { getThemeConfig } from '../../utils/themeConfig';
import { getApiUrl } from '../../services/api'; // Asegúrate de que este path es correcto


const Calendar = () => {
    const { primaryColor } = getThemeConfig();
    const calendarRef = useRef(null);
    const [calendarHeight, setCalendarHeight] = useState('auto');
    const [selectedEvent, setSelectedEvent] = useState(null);
    const [events, setEvents] = useState([]);
    const token = localStorage.getItem('token');

    useEffect(() => {
        const adjustHeight = () => {
            const headerHeight = document.querySelector('h1')?.offsetHeight || 0;
            const buttonsHeight = document.querySelector('.d-grid')?.offsetHeight || 0;
            const padding = 150;
            const availableHeight = window.innerHeight - (headerHeight + buttonsHeight + padding);
            setCalendarHeight(availableHeight);
        };

        adjustHeight();
        window.addEventListener('resize', adjustHeight);
        return () => window.removeEventListener('resize', adjustHeight);
    }, []);

    useEffect(() => {
        const fetchAppointments = async () => {
            try {
                const response = await fetch(getApiUrl('/appointments'), {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    }
                });
                const data = await response.json();
                const formattedEvents = data.map((appointment) => ({
                    title: `${appointment.licensePlate} ${appointment.nameClient}`,
                    start: appointment.dateTime,
                    description: appointment.task
                }));
                setEvents(formattedEvents);
            } catch (error) {
                console.error('Error fetching appointments:', error);
            }
        };

        fetchAppointments();
    }, []);

    const handleEventClick = (info) => {
        setSelectedEvent({
            title: info.event.title,
            description: `Fecha: ${info.event.start?.toLocaleString() || 'Sin fecha'}\nTarea: ${info.event.extendedProps.description || 'Sin descripción'}`,
        });

        const modalElement = document.getElementById('eventModal');
        if (modalElement) {
            const modalInstance = new window.bootstrap.Modal(modalElement);
            modalInstance.show();
        }
    };

    return (
        <div className="calendar-container border-black rounded shadow p-3 bg-white">
            <FullCalendar
                ref={calendarRef}
                plugins={[dayGridPlugin, timeGridPlugin]}
                initialView="dayGridMonth"
                headerToolbar={{
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay',
                }}
                locale="es"
                events={events}
                eventDidMount={(info) => {
                    info.el.style.backgroundColor = 'transparent';
                }}
                eventClick={handleEventClick}
                nowIndicator={true}
                hiddenDays={[0, 6]}
                height={calendarHeight}
                dayMaxEventRows={2}
                aspectRatio={2.5}
                eventColor={primaryColor}
            />
            <Modal id="eventModal" title={selectedEvent?.title || ''}>
                {selectedEvent?.description?.split('\n').map((line, index) => (
                    <p key={index}>{line}</p>
                ))}
            </Modal>
        </div>
    );
};

export default Calendar;
