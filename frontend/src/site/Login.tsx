import React, {FormEvent, useState} from "react";
import axios from "axios";
import "./login.css";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

export default function Login(
    props: {
        authenticationChanged: () => void
    }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const login = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        axios.get("/api/users/login", {auth: {username, password}})
            .then(props.authenticationChanged)
            .then(() => navigate("/products"))
            .catch(() => toast.error("Login fehlgeschlagen"))
    }

    return (
        <div className="login">
            <form onSubmit={login}>
                <div>
                    <label>Name</label>
                    <input
                        autoFocus
                        placeholder={""}
                        name={"name"}
                        autoComplete={"off"}
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label>Passwort</label>
                    <input type="password"
                           autoComplete={"off"}
                           placeholder={""}
                           name={"password"}
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit">
                    Login
                </button>
            </form>
        </div>
    );
}
