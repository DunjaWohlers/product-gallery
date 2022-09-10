import {useEffect, useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";

export default function useUser() {

    const [allNames, setAllNames] = useState<string[]>();

    const getAllNames = () => {
        axios.get("/api/users/")
            .then(response => response.data)
            .then(setAllNames)
            .catch(() => toast.error("Benutzernamen konnten nicht geladen werden."));
    }

    useEffect(
        getAllNames, []
    );

    return {allNames}
}
