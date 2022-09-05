import React from "react";
import axios from "axios";
import "./login.css";

export default function Login(
    props: {
        authenticationChanged: () => void
    }) {
    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");

    function login() {
        axios.get("/api/users/login", {auth: {username, password}})
            .then(props.authenticationChanged)
            .catch(() => alert("Login failed"));
    }

    // return <div style={{border: '3px solid blue', padding: '10px'}}>
    //     <h1>Login</h1>
    //     <input type="text" placeholder="Username" value={username} onChange={e => setUsername(e.target.value)}/>
    //     <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)}/>
    //     <button onClick={login}>Login</button>
    // </div>
    return (
        <div className="login">
            <form>
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
                <button type="submit" onClick={login}>
                    Login
                </button>
            </form>
        </div>
    );
}
