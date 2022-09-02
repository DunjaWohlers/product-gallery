import React, {useEffect} from 'react';
import './App.css';
import Footer from "./component/Footer";
import {BrowserRouter} from "react-router-dom";
import HeaderNav from "./component/HeaderNav";
import AllRoutes from "./AllRoutes";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import axios from "axios";
import Secret from "./site/Secret";
import Login from "./site/Login";

export default function App() {
    const [username, setUsername] = React.useState(undefined);

    function fetchUsername() {
        axios.get("/api/users/me")
            .then(response =>
                response.data
            )
            .then(data => {
                setUsername(data);
            })
            .catch(() => {
                setUsername(undefined)
            });
    }

    useEffect(() => {
        fetchUsername();
    }, []);

    if (!username) {
        return <div>Loading...</div>
    }
    if (username === "anonymousUser") {
        return <Login authenticationChanged={fetchUsername}/>
    }

    return (
        <>
            <BrowserRouter>
                <Secret authenticationChanged={fetchUsername}/>
                <HeaderNav/>
                <main>
                    <AllRoutes/>
                </main>
                <Footer/>
            </BrowserRouter>
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
