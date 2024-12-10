import React,{useEffect, useState} from "react";

const Vendor = () => {

    const[vendorData,setVendorData] = useState({
        ticketsPerRelease: 0,
        numOfTickets: 0
    });
    const [responseMessage, setResponseMessage] = useState("");
    const[nextId,setNextID] = useState(null);
    const[vendor,setVendor]=useState([]);

    const[errors,setErrors]=useState({});
    const[reaminSlots,setRemainSlots]=useState(null)

    const[runningVendors,setRunningVendors] = useState([]);
    const[completedVendors,setCompletedVendors] = useState([]);


    useEffect(()=> {
        const interval = setInterval(()=> {
            fetch("http://localhost:8080/Vendor/completedVendors")
            .then((response)=>response.json())
            .then((data) => setCompletedVendors(data))
            .catch((error) => console.error("Error fetching next vendor ID:",error));
        },400); //updates every 400 miliseconds
        return () => clearInterval(interval);
    },[]);

    useEffect(()=> {
        const interval = setInterval(()=> {
            fetch("http://localhost:8080/Vendor/nextID")
            .then((response)=>response.json())
            .then((data) => setNextID(data))
            .catch((error) => console.error("Error fetching next vendor ID:",error));
        },200);
        return () => clearInterval(interval);
    },[]);

// get the number of free slots which vendors can issue tickets
    useEffect(()=> {
        const interval = setInterval(()=> {
            fetch("http://localhost:8080/Vendor/NumberOfSlots")
            .then((response)=>response.json())
            .then((data) => setRemainSlots(data))
            .catch((error) => console.error("Error fetching next vendor ID:",error));
        },200);
        return () => clearInterval(interval);
    },[]);



    // get all created vendor's data
    useEffect(()=>{
        const fetchVendors = async () => {
            try{
                const response = await fetch ("http://localhost:8080/Vendor/allVendors");
                const data = await response.json();
                // const vendorArray = Object.values(data);
                const vendorArray = Object.values(data);
                setVendor(vendorArray);
            }catch{
                console.error("Error fetching vendors",error);
            }
        };
        fetchVendors();
    });

// save vendor's data
    const saveChanges = (e) => {
        const {name,value} = e.target;
        setVendorData({...vendorData,[name]: Number(value)});
    }

    // submit the form data to backend
    const submit = async (e) => {
        e.preventDefault();

        if(!validateInputs()){
            return
        }

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
            console.error("Error",error)
        }

    } 

// validate inputs
    const validateInputs=()=>{
        const err = {};

        if(vendorData.numOfTickets>reaminSlots){
            err.numOfTickets = `Number of tickets can not exceed ${reaminSlots}`;
        }

        setErrors(err);

        return Object.keys(err).length==0
    }


    // start vendor threads
    const handleStart = async (e) => {
        e.preventDefault();
        try{
            const response = await fetch("http://localhost:8080/Vendor/Start",{
                method: "POST",
                headers: {
                "Content-Type": "application/json",
                },
            });

            if(response.ok){
                const msg = await response.text();
                console.log("Start");
            }else{
                alert("Failed")
            }
        }catch (error){
            console.error("Error starting threads.",error)
        }
    }

    // start vendor Threads
    const runVendor = async (vendor_id) => {
        console.log("Starting thread for customer:", vendor_id); 
        try{
            const response = await fetch(`http://localhost:8080/Vendor/${vendor_id}/run`,{
                method: "POST",
                headers: {
                "Content-Type": "application/json",
                },
            });
    
            if(response.ok){
                console.log("started");
                setRunningVendors((prev) => [...prev, vendor_id]);
            }else{
                alert("Failed")
            }
        }catch (error){
            console.error("Error starting threads.",error)
        }
    }
    


    return(
        <div className="flex flex-row">
            <div className="w-[400px]  p-4 border-2 border-black px-5 py-2 rounded-lg bg-slate-300">
                <h1>Enter Vendor Detaisl :</h1>
                <p>Free Slots : {reaminSlots}</p>
                <form className="flex flex-col" onSubmit={submit}>
                    <label>Vendor ID: {nextId}</label>
                
                    <label>Tickets Per Release : </label>
                    <input type="number" min={0} name="ticketsPerRelease" value={setVendorData.ticketsPerRelease} onChange={saveChanges}/>

                    <label>Number of Tickets : </label>
                    <input type="number" min={0} name="numOfTickets" value={setVendorData.numOfTickets} onChange={saveChanges}/>
                    {errors.numOfTickets && <p className="text-red-500">{errors.numOfTickets}</p>}

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
                        {vendor.map((vendor) => (
                            <li key={vendor.vendor_id}>
                                <p>Vendor ID: {vendor.vendor_id}</p>
                                <p>Tickets Per Release: {vendor.ticketsPerRelease}</p>
                                <p>Number of Tickets: {vendor.numOfTickets}</p>
                                {completedVendors.includes(vendor.vendor_id)?(
                                    <span>Done</span>
                                ): runningVendors.includes(vendor.vendor_id)?(
                                    <span>Thread Running ...</span>
                                ):(
                                    <button onClick={() => runVendor(vendor.vendor_id)}>Start</button>
                                )}
                                <hr/>
                            </li>
                        ))}
                        
                    </ul>
                )}
                <button onClick={handleStart}>Run All Vendors</button>
            </div>
        </div>
    );
}

export default Vendor;