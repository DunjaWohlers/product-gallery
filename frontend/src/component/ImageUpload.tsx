import "./imageUpload.css"
import {FormEvent} from "react";
import axios from "axios";

export default function ImageUpload() {

    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const formData = new FormData(e.target as HTMLFormElement);
        axios.post("/api/image/uploadFile/", formData,
            //  {auth:{username:"frank", password:"frank123"}}
        ).then();
    }

    return (<form action={"/api/image/uploadFile/"} method={"POST"}
                  encType={"multipart/form-data"} onSubmit={handleSubmit}>
            <div className={"container"}>
                <div className={"fileUploadInput"}>
                    <label>Neues Bild hinzufügen:</label>
                    <input type={"file"} name={"file"}/>
                    <button type={"submit"}> +</button>
                </div>
            </div>
        </form>
    )
}
