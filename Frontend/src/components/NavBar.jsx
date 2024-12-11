import React,{useState} from "react";
import { Link } from "react-router-dom";

const NavBar = () => {
    return(   
        <div className="flex flex-col mt-10"> 
            <h1 className="flex justify-center text-[40px] text-lime-900 font-extrabold">Real Time Event Ticketing System</h1>
            <div className="flex text-3xl items-start justify-center p-10 m-0 h-10">
                <ul className="flex list-none p-0 m-0 gap-10">
                    <li className="p-3 bg-cyan-800 rounded-lg"><Link to="/">Configuration</Link></li>
                    <li className="p-3 bg-cyan-800 rounded-lg"><Link to="/vendor">Vendors</Link></li>
                    <li className="p-3 bg-cyan-800 rounded-lg"><Link to="/customer">Customers</Link></li>
                    <li className="p-3 bg-cyan-800 rounded-lg"><Link to="/progress">TicketPool</Link></li>
                </ul>
            </div>
        </div>
    )
};

export default NavBar;