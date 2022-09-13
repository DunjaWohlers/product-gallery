import {useNavigate} from "react-router-dom";
import Secret from "../site/Secret";
import React from "react";

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
