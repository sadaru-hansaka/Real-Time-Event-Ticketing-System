import React, {useState,useEffect} from 'react';
import axios from 'axios';
import ControlPanel from './ControlPanel';

const ProgressBar = () => {
    const [availableTickets, setAvailableTickets] = useState(0);
    const [maxTickets, setMaxTickets] = useState(null); // You can also fetch this from the backend if needed

    useEffect(() => {
        const interval = setInterval(()=>{
          fetch('http://localhost:8080/Configuration/max')
          .then((response)=>response.json())
          .then((data) => setMaxTickets(data))
          .catch((error) => console.error("Error fetching logs data:",error));
        },1000);
          return ()=> clearInterval(interval);
      },[]);

    // Fetch the available tickets from the backend periodically
    useEffect(() => {
        const fetchAvailableTickets = async () => {
            try {
                const response = await axios.get('http://localhost:8080/tickets/available');
                setAvailableTickets(response.data);
            } catch (error) {
                console.error('Error fetching tickets:', error);
            }
        };

        // Poll the backend every 2 seconds
        const interval = setInterval(fetchAvailableTickets, 200);

        return () => clearInterval(interval); // Clean up the interval when the component is unmounted
    }, []);

    // Calculate the progress percentage
    const progressPercentage = (availableTickets / maxTickets) * 100;

    return (
        <div className='flex flex-col w-full justify-center items-center'>
            <div style={{display:'flex',alignItems:'center', width: '80%', height:'30px',backgroundColor:"#e0e0e0",borderRadius:'8px', marginBottom:'50px' }}>
                <div
                    style={{
                        height: '30px',
                        width: `${progressPercentage}%`,
                        backgroundColor: '#4caf50',
                        borderRadius: '8px',
                        textAlign: 'center',
                        color: 'white',
                        lineHeight: '30px',
                    }}
                >
                    {progressPercentage}%
                </div>
            </div>
            <ControlPanel/>
        </div>
    );
};

export default ProgressBar;