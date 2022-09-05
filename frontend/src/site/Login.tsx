import React, {FormEvent} from "react";
import axios from "axios";
import "./login.css";

export default function Login(
    props: {
        authenticationChanged: () => void
    }) {
    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");

    function login(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        axios.get("/api/users/login", {auth: {username, password}})
            .then(props.authenticationChanged)
            .catch(() => alert("Login failed"));
    }

    return (
        <div className="login">
            <form onSubmit={login}>
                <div>
                    <label>Email</label>
                    <input
                        autoFocus
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label>Password</label>
                    <input type="password"
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
