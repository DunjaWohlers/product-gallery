import React, {FormEvent, useState} from "react";
import "./login.css";

type LoginProps = {
    login: (username: string, password: string) => void,
}
export default function Login(props: LoginProps) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const validateForm = () => {
        return username.length > 0 && password.length > 0;
    }

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        props.login(username, password);
    }

    return (
        <div className="login">
            <form onSubmit={handleSubmit}>
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
                <button type="submit" disabled={!validateForm()}>
                    Login
                </button>
            </form>
        </div>
    );
}
