import axios from "axios";

export default function Secret(props: { authenticationChanged: () => void }) {
    function logout() {
        axios.get("/api/users/logout")
            .then(props.authenticationChanged)
    }

    return <div style={{border: "3px solid green"}}>
        <h1>Willkommen (Sie sind eingeloggt)</h1>
        <button onClick={logout}>Logout</button>
    </div>
}
