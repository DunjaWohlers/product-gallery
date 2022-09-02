import React from "react";
import axios from "axios";

export default function Login(props: { authenticationChanged: () => void }) {
    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");

    function login() {
        axios.get("/api/users/login", {auth: {username, password}})
            .then(props.authenticationChanged)
            .catch(() => alert("Login failed"));
    }

    return <div style={{border: '3px solid blue', padding: '10px'}}>
        <h1>Login</h1>
        <input type="text" placeholder="Username" value={username} onChange={e => setUsername(e.target.value)}/>
        <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)}/>
        <button onClick={login}>Login</button>
    </div>
}


//import React, {FormEvent, useState} from "react";
//import "./login.css";
//
//type LoginProps = {
//    login: (username: string, password: string) => void,
//}
//
//export default function Login(props: LoginProps) {
//    const [username, setUsername] = useState("");
//    const [password, setPassword] = useState("");
//
//    const validateForm = () => {
//        return username.length > 0 && password.length > 0;
//    }
//
//    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
//        event.preventDefault();
//        props.login(username, password);
//    }
//
//    return (
//        <div className="login">
//            <form onSubmit={handleSubmit}>
//                <div>
//                    <label>Email</label>
//                    <input
//                        autoFocus
//                        type="text"
//                        value={username}
//                        onChange={(e) => setUsername(e.target.value)}
//                    />
//                </div>
//                <div>
//                    <label>Password</label>
//                    <input type="password"
//                           value={password}
//                           onChange={(e) => setPassword(e.target.value)}
//                    />
//                </div>
//                <button type="submit" disabled={!validateForm()}>
//                    Login
//                </button>
//            </form>
//        </div>
//    );
//}
//