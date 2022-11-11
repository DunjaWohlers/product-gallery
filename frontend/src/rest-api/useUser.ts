import {useEffect, useState} from "react";
import axios from "axios";
import {UserInfo} from "../type/UserInfo";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

export default function useUser() {

    const navigate = useNavigate();
    const [userInfo, setUserInfo] = useState<UserInfo | undefined>(undefined);

    useEffect(
        () => {
            fetchUsername();
        }, []
    )

    const fetchUsername = () => {
        axios.get("/api/users/me")
            .then(response =>
                response.data
            )
            .then(data => {
                setUserInfo(data);
            })
            .catch(() => {
                setUserInfo(undefined)
            });
    }

    const loginUser = (username: string, password: string) => {
        axios.get("/api/users/login", {auth: {username, password}})
            //  .then(props.authenticationChanged)
            .then(() => navigate("/admin"))
            .catch(() => toast.error("Login fehlgeschlagen"))
    }

    return {userInfo, loginUser}
}
