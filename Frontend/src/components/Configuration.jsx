import React,{useEffect, useState} from "react";

const Configuration = () =>{

    const[formData, setFormData] = useState({
        totalTickets: 0,
        maxTicketCapacity: 0,
        customerRetrievalRate: 0,
        ticketReleaseRate: 0,
    });

    const [savedData,setSavedData] = useState(null);
    const [message,setMessage] = useState("");

    const handleChanges = (e) => {
        const {name,value}=e.target;
        setFormData({...formData,[name]: Number(value)});

    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
          const response = await fetch("http://localhost:8080/Configuration/Save", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(formData),
          });
    
          if (response.ok) {
            alert("Configuration saved successfully!");
            setFormData({
                totalTickets: 0,
                maxTicketCapacity: 0,
                customerRetrievalRate: 0,
                ticketReleaseRate: 0,
            });
          } else {
            console.error("Failed to save configuration");
          }
        } catch (error) {
          console.error("Error:", error);
        }
      };


    return(
      <div className="flex justify-center items-center w-full">
        <div className="w-[400px] m-2 p-4 border-2 border-black px-5 py-2 rounded-lg bg-slate-300">
          <h1 className="text-2xl mb-[20px]">Inputh configuration parameters : </h1>
            <form className="flex flex-col" onSubmit={handleSubmit}>
              <label className="mb-[10px]">Number of Total Tickets  :</label>
              <input type="number" min={0}  name="totalTickets" value={formData.totalTickets || ""} onChange={handleChanges}/>
                      
              <label  className="mb-[10px]">Maximum capacity of the system :</label>
              <input type="number" min={0} name="maxTicketCapacity" value={formData.maxTicketCapacity || ""} onChange={handleChanges}/>
                      
              <label  className="mb-[10px]">Vendor Tickets release rate :</label>
              <input type="number" min={0} name="ticketReleaseRate" value={formData.ticketReleaseRate || ""} onChange={handleChanges}/>
                  
              <label  className="mb-[10px]">Customer retrieval rate :</label>
              <input type="number" min={0} name="customerRetrievalRate" value={formData.customerRetrievalRate || ""} onChange={handleChanges}/>
                      
              <div className="flex items-end justify-end">
                <button type="submit" className="flex m-2 p-4 border-2 border-black px-4 py-2 rounded-lg">Configure System</button>
              </div>
            </form>
        </div>
      </div>
    )
}

export default Configuration;