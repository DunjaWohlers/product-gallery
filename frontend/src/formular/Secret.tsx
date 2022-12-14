import axios from "axios";
import React from "react";
import {useNavigate} from "react-router-dom";
import StarElement from "../component/StarElement";

export default function Secret(
    props: {
        authenticationChanged: () => void,
        username: string | undefined,
    }) {
    function logout() {
        axios.get("/api/users/logout")
            .then(props.authenticationChanged)
    }

    const navigate = useNavigate();

    return <>
        {(!props.username || props.username === "anonymousUser")
            ? <button onClick={() => navigate("/login")}>Login</button>
            :
            <>
                <button onClick={logout}>Logout</button>
                <button className={"starButton"}
                        onClick={() => navigate("/myproducts")}>
                    <StarElement width={25} height={25}/>
                </button>
            </>
        }
    </>
}
