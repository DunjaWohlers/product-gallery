import "./imageUpload.css"
import {FormEvent} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import {PicObj} from "../type/PicObj";

export default function ImageUpload(
    props: {
        setPictureObj: (urls: PicObj[]) => void,
        pictureObj: PicObj[],
    }) {
    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const formData = new FormData(e.target as HTMLFormElement);
        axios.post("/api/image/uploadFile/", formData,
            //  {auth:{username:"frank", password:"frank123"}}
        ).then(data => data.data)
            .then(response => {
                let ar: PicObj[] = [];
                props.pictureObj.forEach(picObj => {
                    ar.push(picObj);
                })
                ar.push(response);
                props.setPictureObj(ar);
            })
            .then(() => toast.info("Bild wurde gespeichert"))
            .catch(() => toast.warn("Bild konnte nicht auf die Cloud geladen werden."));
    }

    return (<>
        <div className={"addedImagesForProduct"}>
            {props.pictureObj.map(picObj => <img alt={"Bild"} key={picObj.url} src={picObj.url}></img>)}
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
