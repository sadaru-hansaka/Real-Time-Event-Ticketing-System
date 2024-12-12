import React,{useEffect,useState} from "react";

const Customer = () => {

    //when click "Add" button all customer's input data goes to this array
    const[customerData,setCustomerData]=useState({
        ticketCount:0,
    });

    const [responseMessage, setResponseMessage] = useState("");
    const [nextId,setNextId]=useState(null);
    // get customer's data from backend to display in frontend
    const[customers,setCustomers]=useState([]);
    const[remaintTickets,setRemainTickets]=useState(null);
    const[errors,setErrors]=useState({});

    const [runningThreads, setRunningThreads] = useState([]);
    const[completedThreads,setCompletedThreads]= useState([]);

    useEffect(()=> {
        const interval = setInterval(()=> {
            fetch("http://localhost:8080/Customer/completedThreads")
            .then((response)=>response.json())
            .then((data) => setCompletedThreads(data))
            .catch((error) => console.error("Error fetching next vendor ID:",error));
        },400); //updates every 400 miliseconds
        return () => clearInterval(interval);
    },[]);

    // fetch customer's id from backend
    useEffect(()=> {
        const interval = setInterval(()=> {
            fetch("http://localhost:8080/Customer/nextId")
            .then((response)=>response.json())
            .then((data) => setNextId(data))
            .catch((error) => console.error("Error fetching next vendor ID:",error));
        },200); //updates every 200 miliseconds
        return () => clearInterval(interval);
    },[]);

    // get the remain ticket count to buy
    useEffect(()=> {
        const interval = setInterval(()=> {
            fetch("http://localhost:8080/Customer/remain")
            .then((response)=>response.json())
            .then((data) => setRemainTickets(data))
            .catch((error) => console.error("Error fetching next vendor ID:",error));
        },200);
        return () => clearInterval(interval);
    },[]);


    // catch user's input and save them to an list
    const saveCustomerChanges = (e) => {
        const {name,value} = e.target;
        setCustomerData({...customerData,[name]: Number(value)});
    }

    // fetch all added customer's data from backend
    useEffect(()=>{
        const fetchCustomers = async () => {
            try{
                const response = await fetch ("http://localhost:8080/Customer/allCustomers");
                const data = await response.json();
                const customerArray = Object.values(data);

                setCustomers(customerArray);
            }catch{
                console.error("Error fetching customers",error);
            }
        };
        fetchCustomers();
    },[]);

    // submit user's data to backend to make threads
    const submit = async (e) => {
        e.preventDefault();

        if(!validateInputs()){
            return
        }

        try{
            const response = await fetch("http://localhost:8080/Customer/create",{
                method : "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(customerData),
            });
            if(response.ok){
                const data = await response.json();
                console.log("Data",data)
                setResponseMessage(`Customer created successfully with ID: ${data.customer_id}`);
                //when click the add button , customer's data displays real time
                const customersResponse = await fetch("http://localhost:8080/Customer/allCustomers");
                const updateCustomers = await customersResponse.json();
                const customerArray = Object.values(updateCustomers)
                setCustomers(customerArray);
                setCustomerData({
                    name: "",
                    ticketCount:0,
                });
            }else{
                const errorData = await response.json();
                setResponseMessage(`Error: ${errorData.message || "Unknown error"}`)
            }
        }catch(error){
            console.error("Error",error)
        }
    } 

    // start vendor Threads
    const handleStart = async (customer_id) => {
        console.log("Starting thread for customer:", customer_id); 
        try{
            const response = await fetch(`http://localhost:8080/Customer/${customer_id}/run`,{
                method: "POST",
                headers: {
                "Content-Type": "application/json",
                },
            });

            if(response.ok){
                const msg = await response.text();
                console.log("MSG",msg);
                setRunningThreads((prev) => [...prev, customer_id]);
            }else{
                alert("Failed")
            }
        }catch (error){
            console.error("Error starting threads.",error)
        }
    }


    // validate inputs
    const validateInputs=()=>{
        const err = {};

        if(customerData.ticketCount>remaintTickets){
            err.ticketCount = `Number of tickets can not exceed ${remaintTickets}`;
        }

        setErrors(err);

        return Object.keys(err).length==0
    }


    return(
        <div className="flex flex-row">
            <div className="w-[400px]  p-4 border-2 border-black px-5 py-2 rounded-lg bg-slate-300">
                <h1 className="text-xl font-semibold">Enter customer data : </h1><br></br>
                <div className="mb-[10px] p-1 bg-red-300">
                    <p className="font-medium">Remain Tickets : {remaintTickets}</p>
                </div>
                
                <form className="flex flex-col" onSubmit={submit}>
                    <label>Customer ID : {nextId}</label>
                    <label className="mb-[10px]">Enter your name : </label>
                    <input type="text" min={0}/>

                    <label className="mb-[10px]">How many tickets do you need to buy : </label>
                    <input type="number" min={0} name="ticketCount" value={setCustomerData.ticketCount} onChange={saveCustomerChanges}/>
                    {errors.ticketCount && <p className="text-red-500">{errors.ticketCount}</p>}

                    <div className="flex items-end justify-end">
                        <button type="submit" className="flex m-2 p-4 border-2 border-black px-4 py-2 rounded-lg">Add Customer</button>
                    </div>
                    {responseMessage && <p>{responseMessage}</p>}
                </form>
            </div>
            {/* display added cutomers */}
            <div className="w-[400px] p-4 border-2 border-black px-5 py-2 rounded-lg bg-slate-300">
                <h1 className="text-xl font-semibold" >Customer Threads :</h1><br></br>
                {customers && customers.length > 0 ? (
                    <ul>
                        {customers.map((customer, index) => (
                            <li key={index}>
                                <p>
                                    <strong>Customer  {customer.customer_id}</strong>
                                </p>
                                <p>
                                    <strong>Number of Tickets: {customer.ticketCount}</strong> 
                                </p>
                                <div className="flex items-end justify-end">
                                    {completedThreads.includes(customer.customer_id) ? (
                                        <span>Done !</span> // Change button to text
                                    ) : runningThreads.includes(customer.customer_id)? (
                                        <span>Thread Running ...</span>
                                    ):(
                                        <button className="flex m-1 border-2 border-black px-2 py-0 rounded-lg" onClick={() => handleStart(customer.customer_id)}>Start</button>
                                    )}
                                </div>
                                <hr />
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No customers available.</p>
                )}
               
            </div>
        </div>
    )
}

export default Customer;
