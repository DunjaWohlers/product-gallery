import React, {useEffect} from 'react';
import './App.css';
import Footer from "./component/Footer";
import {BrowserRouter} from "react-router-dom";
import HeaderNav from "./component/HeaderNav";
import AllRoutes from "./AllRoutes";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import axios from "axios";

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

    return (
        <>
            <h1> {username}</h1>
            <BrowserRouter>
                <HeaderNav
                    username={username}
                    authenticationChanged={fetchUsername}/>
                <main>
                    <AllRoutes username={username} authenticationChanged={fetchUsername}/>
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
