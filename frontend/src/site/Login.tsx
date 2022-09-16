import React, {ChangeEvent, FormEvent, useState} from "react";
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
            .catch(() => toast.error("Registrierung fehlgeschlagen"));
    }

    const handlePassword = (e: ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value)
    }

    const pwHasLowerLetter = () => {
        return !!password.match("(?=.*[a-z])");
    }

    const pwHasCapital = () => {
        return !!password.match("(?=.*[A-Z])");
    }

    const pwContainsNumber = () => {
        return !!password.match("(?=.*\\d)");
    }

    const pwHasMin8Letters = () => {
        return password.length > 7;
    }

    const userNameMin4Letters = () => {
        return username.length > 3;
    }

    const pwIsOkay = () => {
        return password && pwHasLowerLetter() && pwHasCapital() && pwContainsNumber() && pwHasMin8Letters();
    }

    return (
        <div className="login">
            <form onSubmit={(allNames?.find(name => name === username)) ? login : register}
                  autoComplete={"off"}>
                <div>
                    <label>Name</label>
                    <input
                        autoFocus
                        onFocus={(e) => {
                            e.target.value = "";
                            setUsername("")
                        }}
                        name={"name"}
                        autoComplete={"off"}
                        type="text"
                        value={username}
                        onChange={(e) => {
                            setUsername(e.target.value);
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
                           onFocus={(e) => {
                               e.target.value = "";
                               setPassword("")
                           }}
                           onChange={(e) => handlePassword(e)}
                           pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
                           required
                           className={pwIsOkay() ? "greenBg" : "redBg"}
                    />
                </div>
                {(allNames?.find(name => name === username))
                    ? <button type="submit" disabled={!userNameMin4Letters() || !pwIsOkay()}>
                        Login
                    </button>
                    : <button type="submit" disabled={!userNameMin4Letters() || !pwIsOkay()}>
                        Register
                    </button>
                }
                <div id="message">
                    {!pwIsOkay() && <h3> Es fehlen noch: </h3>}
                    {!pwHasLowerLetter() &&
                        <p id="letter" className="invalid">
                            Ein <b>Kleinbuchstabe</b></p>
                    }
                    {!pwHasCapital() &&
                        <p id="capital" className="invalid">
                            Ein <b>Großbuchstabe</b></p>
                    }
                    {!pwContainsNumber() &&
                        <p id="number" className="invalid">
                            Eine <b>Zahl</b></p>
                    }
                    {!pwHasMin8Letters() && <p id="length" className="invalid">
                        Mindestens <b>8 Buchstaben</b> Länge</p>
                    }
                </div>
            </form>
        </div>
    );
}
