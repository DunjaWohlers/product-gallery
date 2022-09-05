import React, {FormEvent} from "react";
import axios from "axios";
import "./login.css";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

export default function Login(
    props: {
        authenticationChanged: () => void
    }) {
    const [username, setUsername] = React.useState("frank");
    const [password, setPassword] = React.useState("frank123");

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
                    <label>Email</label>
                    <input
                        autoFocus
                        type="text"
                        defaultValue={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label>Password</label>
                    <input type="password"
                           defaultValue={password}
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
