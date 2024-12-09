import React,{useEffect, useState} from "react";

const Vendor = () => {

    const[vendorData,setVendorData] = useState({
        ticketsPerRelease: 0,
        numOfTickets: 0
    });
    const [responseMessage, setResponseMessage] = useState("");
    const[nextId,setNextID] = useState(null);
    const[vendor,setVendor]=useState([]);

    useEffect(()=> {
        const interval = setInterval(()=> {
            fetch("http://localhost:8080/Vendor/nextID")
            .then((response)=>response.json())
            .then((data) => setNextID(data))
            .catch((error) => console.error("Error fetching next vendor ID:",error));
        },200);
        return () => clearInterval(interval);
    },[]);

    useEffect(()=>{
        const fetchVendors = async () => {
            try{
                const response = await fetch ("http://localhost:8080/Vendor/allVendors");
                const data = await response.json();
                const vendorArray = Object.values(data);

                setVendor(vendorArray);
            }catch{
                console.error("Error fetching vendors",error);
            }
        };
        fetchVendors();
    });


    const saveChanges = (e) => {
        const {name,value} = e.target;
        setVendorData({...vendorData,[name]: Number(value)});
    }

    const submit = async (e) => {
        e.preventDefault();
        try{
            const response = await fetch("http://localhost:8080/Vendor/create",{
                method : "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(vendorData),
            });
            if(response.ok){
                const data = await response.json();
                console.log("Data",data)
                setResponseMessage(`Vendor created successfully with ID: ${data.vendor_id}`);
                
                const response = await fetch ("http://localhost:8080/Vendor/allVendors");
                const vendorData = await response.json();
                const vendorArray = Object.values(vendorData);

                setVendor(vendorArray);

                alert("Done");
            }else{
                const errorData = await response.json();
                setResponseMessage(`Error: ${errorData.message || "Unknown error"}`)
            }
        }catch(error){
            console.error("Error")
        }

    } 

    return(
        <div className="flex flex-row">
            <div className="w-[400px]  p-4 border-2 border-black px-5 py-2 rounded-lg bg-slate-300">
                <h1>Enter Vendor Detaisl :</h1>
                <form className="flex flex-col" onSubmit={submit}>
                    <label>Vendor ID: {nextId}</label>
                
                    <label>Tickets Per Release : </label>
                    <input type="number" min={0} name="ticketsPerRelease" value={setVendorData.ticketsPerRelease} onChange={saveChanges}/>

                    <label>Number of Tickets : </label>
                    <input type="number" min={0} name="numOfTickets" value={setVendorData.numOfTickets} onChange={saveChanges}/>

                    <div className="flex items-end justify-end">
                        <button type="submit" className="flex m-2 p-4 border-2 border-black px-4 py-2 rounded-lg">Add Vendor</button>
                    </div>
                    {responseMessage && <p>{responseMessage}</p>}
                </form>
                
            </div>
            <div className="w-[400px] p-4 border-2 border-black px-5 py-2 rounded-lg bg-slate-300">
                <h1>Vendor Threads : </h1>
                {vendor.length === 0 ? (
                    <p>No vendors added yet.</p>
                ) : (
                    <ul>
                        {vendor.map((vendor, index) => (
                            <li key={index}>
                                <p>Vendor ID: {vendor.vendor_id}</p>
                                <p>Tickets Per Release: {vendor.ticketsPerRelease}</p>
                                <p>Number of Tickets: {vendor.numOfTickets}</p>
                                <hr />
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}

export default Vendor;