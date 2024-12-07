import React,{useState} from "react";
import { Link } from "react-router-dom";

const NavBar = () => {
    return(   
        <div className="flex text-4xl items-start justify-center p-10 m-0 h-10">
            <ul className="flex list-none p-0 m-0 gap-10">
                <li className="p-5 bg-cyan-800"><Link to="/">Configuration</Link></li>
                <li className="p-5 bg-cyan-800"><Link to="/vendor">Vendors</Link></li>
                <li className="p-5 bg-cyan-800"><Link to="/customer">Customers</Link></li>
            </ul>
        </div>
    )
};

export default NavBar;