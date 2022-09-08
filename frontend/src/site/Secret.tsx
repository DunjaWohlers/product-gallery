import axios from "axios";
import React from "react";
import {NavLink} from "react-router-dom";

export default function Secret(
    props: {
        authenticationChanged: () => void
        username: string | undefined,
    }) {
    function logout() {
        axios.get("/api/users/logout")
            .then(props.authenticationChanged)
    }

    return <>
        {(!props.username || props.username === "anonymousUser")
            ? <NavLink className={"navLink"} to={"/login"}>Login</NavLink>
            : <button onClick={logout}>Logout</button>
        }
    </>
}
