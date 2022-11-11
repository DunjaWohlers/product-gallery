import {useState} from "react";
import axios from "axios";
import {UserInfo} from "../type/UserInfo";

export default function useUser() {

    const [userInfo, setUserInfo] = useState<UserInfo | undefined>(undefined);

    function fetchUsername() {
        return axios.get("/api/users/me")
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

    return {userInfo}
}
