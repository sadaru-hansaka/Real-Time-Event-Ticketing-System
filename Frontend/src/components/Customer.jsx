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
                alert("Done");
            }else{
                const errorData = await response.json();
                setResponseMessage(`Error: ${errorData.message || "Unknown error"}`)
            }
        }catch(error){
            console.error("Error",error)
        }

    } 

    return(
        <div className="flex flex-row">
            <div className="w-[400px]  p-4 border-2 border-black px-5 py-2 rounded-lg bg-slate-300">
                <h1>Enter customer data : </h1>
                <form className="flex flex-col" onSubmit={submit}>
                    <label>Customer ID : {nextId}</label>
                    <label>Enter your name : </label>
                    <input type="text" min={0}/>

                    <label>How many tickets do you need to buy : </label>
                    <input type="number" min={0} name="ticketCount" value={setCustomerData.ticketCount} onChange={saveCustomerChanges}/>

                    <div className="flex items-end justify-end">
                        <button type="submit" className="flex m-2 p-4 border-2 border-black px-4 py-2 rounded-lg">Add Customer</button>
                    </div>
                    {responseMessage && <p>{responseMessage}</p>}
                </form>
            </div>
            {/* display added cutomers */}
            <div className="w-[400px] p-4 border-2 border-black px-5 py-2 rounded-lg bg-slate-300">
                <h1>Customer Threads :</h1>
                {customers && customers.length > 0 ? (
                    <ul>
                        {customers.map((customer, index) => (
                            <li key={index}>
                                <p>
                                    <strong>ID:</strong> {customer.customer_id}
                                </p>
                                <p>
                                    <strong>Tickets:</strong> {customer.ticketCount}
                                </p>
                                <p>
                                    <strong>Name:</strong> {customer.name || "N/A"}
                                </p>
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