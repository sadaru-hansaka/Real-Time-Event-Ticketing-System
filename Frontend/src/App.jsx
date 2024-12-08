import Ract,{ useState } from 'react'
import { BrowserRouter as Router, Route, Routes, Link, BrowserRouter } from 'react-router-dom';
import Configuration from './components/Configuration'
import NavBar from './components/NavBar'
import Vendor from './components/Vendor';
import SystemLogs from './components/SystemLogs';
import Customer from './components/Customer'
import ControlPanel from './components/ControlPanel';

function App() {

  return (
    <BrowserRouter>
      <div className='flex w-screen h-screen'>
        <div className='flex w-3/4 flex-col'>
          <NavBar/>
          <div className="flex-grow flex justify-center items-center">
            <Routes>
              <Route path='/' element={<Configuration/>}/>
              <Route path='/vendor' element={<Vendor/>}/>
              <Route path='/customer' element={<Customer/>}/>
            </Routes>
          </div>
          <ControlPanel/>
        </div>
        <div className='w-1/4 bg-slate-400'>
          <SystemLogs/>
        </div>
      </div>
    </BrowserRouter>
    
  );
}

export default App
