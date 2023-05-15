import React from 'react';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import "../style/App.css";
import {Login} from "../pages/login";
import {Register} from "../pages/register";
import {Main} from "../pages/main";

function App() {
  return (
      <div className="App">
        <Router>
          <Routes>
              <Route path="/login" element={<Login/>}/>
              <Route path="/register" element={<Register/>}/>
              <Route path="/" element={<Main/>}/>
          </Routes>
        </Router>
      </div>
  );
}

export default App;
