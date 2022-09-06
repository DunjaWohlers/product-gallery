import React, {useEffect, useState} from 'react';
import './App.css';
import Footer from "./component/Footer";
import {BrowserRouter} from "react-router-dom";
import HeaderNav from "./component/HeaderNav";
import AllRoutes from "./AllRoutes";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import axios from "axios";
import {UserInfo} from "./type/UserInfo";

export default function App() {
    const [userInfo, setUserInfo] = useState<UserInfo | undefined>(undefined);

    function fetchUsername() {
        axios.get("/api/users/me")
            .then(response =>
                response.data
            )
            .then(data => {
                setUserInfo(data);
            })
            .catch(() => {
                setUserInfo(undefined)
            });
    }

    useEffect(() => {
        fetchUsername();
    }, []);

    return (
        <>
            <h1> {userInfo?.name}</h1>
            <BrowserRouter>
                <HeaderNav
                    username={userInfo?.name}
                    authenticationChanged={fetchUsername}/>
                <main>
                    <AllRoutes userInfo={userInfo} authenticationChanged={fetchUsername}/>
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
