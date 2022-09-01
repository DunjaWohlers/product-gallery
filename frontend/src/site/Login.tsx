import React, {FormEvent, useState} from "react";
import "./login.css";

export default function Login() {
    const [name, setName] = useState("");
    const [password, setPassword] = useState("");

    function validateForm() {
        return name.length > 0 && password.length > 0;
    }

    function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
    }

    return (
        <div className="login">
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email</label>
                    <input
                        autoFocus
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
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
