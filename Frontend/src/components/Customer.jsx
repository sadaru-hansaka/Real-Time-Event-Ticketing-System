import React,{useEffect,useState} from "react";

const Customer = () => {
    const[customerData,setCustomerData]=useState({
        ticketCount:0,
    });
    const[customer,setCustomer] = useState([]);
    const [responseMessage, setResponseMessage] = useState("");
    const [nextId,setNextId]=useState(null);

    useEffect(()=> {
        const interval = setInterval(()=> {
            fetch("http://localhost:8080/Customer/nextId")
            .then((response)=>response.json())
            .then((data) => setNextId(data))
            .catch((error) => console.error("Error fetching next vendor ID:",error));
        },200);
        return () => clearInterval(interval);
    },[]);

    const saveCustomerChanges = (e) => {
        const {name,value} = e.target;
        setCustomerData({...customerData,[name]: Number(value)});
    }

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
                setCustomer([...customer, { ...customerData, id: data.customer_id }]);

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
            <div className="w-[400px] p-4 border-2 border-black px-5 py-2 rounded-lg bg-slate-300">
                <h1>Customer Threads :</h1>
                {customer.length === 0 ? (
                    <p>No customers added yet.</p>
                ) : (
                    <ul>
                        {customer.map((customer, index) => (
                            <li key={index}>
                                <p>Vendor ID : {customer.id}</p>
                                <p>Number of Tickets : {customer.ticketCount}</p>
                                <hr />
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    )
}

export default Customer;