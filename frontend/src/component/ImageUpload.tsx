import "./imageUpload.css"
import {FormEvent} from "react";
import axios from "axios";
import {toast} from "react-toastify";

export default function ImageUpload(
    props: {
        setPictureUrls: (urls: string[]) => void,
        pictureUrls: string[],
    }) {
    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const formData = new FormData(e.target as HTMLFormElement);
        axios.post("/api/image/uploadFile/", formData,
            //  {auth:{username:"frank", password:"frank123"}}
        ).then(data => data.data)
            .then(response => {
                let ar: string[] = [];
                props.pictureUrls.forEach(picUrl => {
                    ar.push(picUrl);
                })
                ar.push(response);
                props.setPictureUrls(ar);
            })
            .then(() => toast.info("Bild wurde gespeichert"))
            .catch(() => toast.warn("Bild konnte nicht auf die Cloud geladen werden."));
    }

    return (<>
        <div className={"addedImagesForProduct"}>
            {props.pictureUrls.map(picUrl => <img key={picUrl} src={picUrl}></img>)}
        </div>
        <form onSubmit={handleSubmit}>
            <div className={"container"}>
                <div className={"fileUploadInput"}>
                    <label> Neues Bild hinzufügen: </label>
                    <input type={"file"} name={"file"}/>
                    <button type={"submit"}> +</button>
                </div>
            </div>
        </form>
    </>)
}
