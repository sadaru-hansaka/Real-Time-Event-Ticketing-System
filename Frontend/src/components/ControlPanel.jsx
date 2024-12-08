import React,{useEffect,useState} from "react";

const ControlPanel = () => {

    const handleStart = async (e) => {
        e.preventDefault();
        try{
            const response = await fetch("http://localhost:8080/System/runAll",{
                method: "POST",
                headers: {
                "Content-Type": "application/json",
                },
            });

            if(response.ok){
                const msg = await response.text();
                alert(msg);
            }else{
                alert("Failed")
            }
        }catch (error){
            console.error("Error starting threads.",error)
        }
    }

    return(
        <div className="flex flex-row justify-center gap-10 mb-20">
            <button onClick={handleStart}>Start</button>
            <button>Stop</button>
        </div>
    );
}

export default ControlPanel;