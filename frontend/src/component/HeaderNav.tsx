import {useNavigate} from "react-router-dom";
import React from "react";
import "./headerNav.css";

export default function HeaderNav() {
    const navigate = useNavigate();

    return (
        <header>
            <button onClick={() => navigate("/")}>
                Product - Gallery
            </button>
        </header>
    )
}
