import React from 'react';
import './App.css';
import Footer from "./component/Footer";
import {BrowserRouter} from "react-router-dom";
import HeaderNav from "./component/HeaderNav";
import AllRoutes from "./AllRoutes";

export default function App() {

    return (
        <>
            <BrowserRouter>
                <HeaderNav/>
                <main>
                    <AllRoutes/>
                </main>
                <Footer/>
            </BrowserRouter>
        </>
    )
}
