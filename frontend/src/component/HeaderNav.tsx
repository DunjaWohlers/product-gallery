import {useNavigate} from "react-router-dom";
import Secret from "../formular/Secret";
import React from "react";
import "./headerNav.css";

export default function HeaderNav(props: {
                                      authenticationChanged: () => void,
                                      username: string | undefined,
                                  }
) {
    const navigate = useNavigate();

    return (
        <header>
            <button onClick={() => navigate("/")}>
                Product - Gallery
            </button>
            <Secret authenticationChanged={props.authenticationChanged} username={props.username}/>
        </header>
    )
}
