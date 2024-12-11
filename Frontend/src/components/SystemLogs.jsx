
import React,{useState,useEffect} from 'react';

function SystemLogs() {

  const [logs,setLogs] = useState([]);
  const[config,setConfig] = useState(null)

  useEffect(() => {
    const interval = setInterval(()=>{
      fetch('http://localhost:8080/Configuration/display')
      .then((response)=>response.json())
      .then((data) => setConfig(data))
      .catch((error) => console.error("Error fetching logs data:",error));
    },1000);
      return ()=> clearInterval(interval);
  },[]);

  useEffect(() => {
    const interval = setInterval(()=>{
      fetch('http://localhost:8080/tickets/logs')
      .then((response)=>response.json())
      .then((data) => setLogs(data))
      .catch((error) => console.error("Error fetching logs data:",error));
    },200);
      return ()=> clearInterval(interval)
  },[]);

  return (
    <div className='p-5 h-full overflow-y-auto'>
      <div>
        <h1 className="text-xl font-semibold">Configurations</h1>
        <hr></hr><br></br>
        {config ? (
          <div>
            <p><strong>Total Tickets for the event    : </strong> {config.totalTickets}</p>
            <p><strong>TicketPool's Capacity          : </strong> {config.maxTicketCapacity}</p>
            <p><strong>Vendor ticket Release rate     : </strong> {config.ticketReleaseRate}</p>
            <p><strong>Customer ticket Retrieval rate : </strong> {config.customerRetrievalRate}</p>
          </div>
        ) : (
          <p>Not</p>
        )}
      </div><br></br>
      <h1  className="text-xl font-semibold"> Logs : </h1>
      <hr></hr><br></br>
      {logs.length === 0 ? (
          <p>No logs yet.</p>
        ) : (
          <ul>
            {logs.map((log, index) => (
              <li key={index}>
                <p>{log}</p>
              </li>
            ))}
          </ul>
        )}
    </div>
  );
}

export default SystemLogs;
