import {NavLink} from "react-router-dom";
import icon from "../sawIcon.png";

export default function HeaderNav() {

    return <header>
        <div id="logo"><img src={icon} alt={"icon"}/></div>
        <NavLink className={"navLink"} to={"/"}><h1> Product - Gallery </h1></NavLink>
        <button>Search</button>
        <button>Login</button>
    </header>
}
