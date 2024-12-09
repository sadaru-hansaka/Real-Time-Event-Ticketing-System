
import React,{useState,useEffect} from 'react';

function SystemLogs() {

  const [logs,setLogs] = useState([]);

  useEffect(() => {
    const interval = setInterval(()=>{
      fetch('http://localhost:8080/Logging/logs')
      .then((response)=>response.json())
      .then((data) => setLogs(data))
      .catch((error) => console.error("Error fetching logs data:",error));
    },200);
      return ()=> clearInterval(interval);
  },[]);

  return (
    <div className='p-5 h-full overflow-y-auto'>
      <h1>Logs : </h1>
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
