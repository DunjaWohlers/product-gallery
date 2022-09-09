import React, {FormEvent, useState} from "react";
import axios from "axios";
import "./login.css";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import useUser from "../rest-api/useUser";

export default function Login(
    props: {
        authenticationChanged: () => void
    }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const {allNames} = useUser();

    const navigate = useNavigate();
    const login = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        axios.get("/api/users/login", {auth: {username, password}})
            .then(props.authenticationChanged)
            .then(() => navigate("/products"))
            .catch(() => toast.error("Login fehlgeschlagen"))
    }

    const register = () => {
        const newUser = {username, password}
        axios.post("/api/users", newUser)
            .catch(() => toast.error("Registration fehlgeschlagen"));
    }

    return (
        <div className="login">
            <form onSubmit={(allNames?.find(name => name === username)) ? login : register}>
                <div>
                    <label>Name</label>
                    <input
                        autoFocus
                        name={"name"}
                        autoComplete={"off"}
                        type="text"
                        value={username}
                        onChange={(e) => {
                            setUsername(e.target.value);
                            console.log(allNames)
                        }
                        }
                    />
                </div>
                <div>
                    <label>Passwort</label>
                    <input type="password"
                           autoComplete={"off"}
                           name={"password"}
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                {(allNames?.find(name => name === username))
                    ? <button type="submit">
                        Login
                    </button>
                    : <button type="submit">
                        Register
                    </button>
                }
            </form>
        </div>
    );
}
