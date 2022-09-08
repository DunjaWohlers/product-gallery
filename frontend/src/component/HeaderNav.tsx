import {NavLink} from "react-router-dom";
import icon from "../sawIcon.png";
import Secret from "../site/Secret";
import React from "react";

export default function HeaderNav(props: {
                                      authenticationChanged: () => void,
                                      username: string | undefined,
                                  }
) {
    return <header>
        <div id="logo"><img src={icon} alt={"icon"}/></div>
        <NavLink className={"navLink"} to={"/"}> Product - Gallery </NavLink>
        <button>Search</button>
        <Secret authenticationChanged={props.authenticationChanged} username={props.username}/>
    </header>
}
