import {NavLink} from "react-router-dom";

export default function HeaderNav() {

    return <header>
        <div id="logo"></div>
        <h1><NavLink className={"navLink"} to={"/"}> Holz - Werkstatt </NavLink></h1>
        <button>Search</button>
        <button>Login</button>
    </header>
}
