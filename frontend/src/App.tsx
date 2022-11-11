import React from 'react';
import './App.css';
import Footer from "./component/Footer";
import HeaderNav from "./component/HeaderNav";
import AllRoutes from "./AllRoutes";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

export default function App() {

    return (
        <>
            <HeaderNav/>
            <main>
                <AllRoutes/>
            </main>
            <Footer/>

            <ToastContainer
                position="top-center"
                autoClose={2000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
            />
        </>
    )
}
