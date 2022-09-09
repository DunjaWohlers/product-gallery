import {useEffect, useState} from "react";
import axios from "axios";

export default function useUser() {

    const [allNames, setAllNames] = useState<string[]>();

    const getAllNames = () => {
        axios.get("/api/users/")
            .then(response => response.data)
            .then(setAllNames)
            .catch(error => console.error(error));
    }

    useEffect(
        getAllNames, []
    );

    return {allNames}
}
