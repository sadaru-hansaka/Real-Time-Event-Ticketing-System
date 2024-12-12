import React from "react";

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
                console.log("Start");
            }else{
                alert("Failed")
            }
        }catch (error){
            console.error("Error starting threads.",error)
        }
    }


    const handleStop = async (e) => {
        e.preventDefault();
        try{
            const response = await fetch("http://localhost:8080/System/stop",{
                method: "POST",
                headers: {
                "Content-Type": "application/json",
                },
            });

            if(response.ok){
                const msg = await response.text();
                console.log("Stop");
            }else{
                alert("Failed")
            }
        }catch (error){
            console.error("Error starting threads.",error)
        }
    }

    return(
        <div className="flex flex-row justify-center gap-5 mb-20">
            <button className="flex text-[20px] font-medium m-1 px-5 py-2 border-2 border-black rounded-lg" onClick={handleStart}>Start</button>
            <button className="flex text-[20px] font-medium m-1 px-5 py-2 border-2 border-black rounded-lg" onClick={handleStop}>Stop</button>
        </div>
    );
}

export default ControlPanel;