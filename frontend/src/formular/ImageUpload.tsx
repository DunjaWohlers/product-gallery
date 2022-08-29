//import "./imageUpload.css"
import {FormEvent, useRef, useState} from "react";

import {PicObj} from "../type/PicObj";

export default function ImageUpload(
    props: {
        setPictureObj: (urls: PicObj[]) => void,
        pictureObj: PicObj[],
        setImagesUpload: (image: HTMLFormElement) => void,
    }) {
    const formRef = useRef<HTMLFormElement>(null);
    const [fileInputs, setFileInputs] = useState<number>(1);

    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        props.setImagesUpload(e.target as HTMLFormElement);
    }

    return (<>
        <div className={"addedImagesForProduct"}>
            {props.pictureObj.map(picObj => <img alt={"Bild"} key={picObj.url} src={picObj.url}></img>)}
        </div>
        <form onSubmit={(e) => handleSubmit(e)}>
            <div className={"container"}>
                <div className={"fileUploadInput"}>
                    <label> Neues Bild hinzufügen: </label>
                    {new Array(fileInputs).fill(null).map((_, index) =>
                        <div key={index}><input type={"file"} name={"file"}
                            // onChange={handleFileChange}
                        />
                        </div>)
                    }
                    <button type="button" onClick={() => setFileInputs(fileInputs + 1)}>Addfile</button>
                    <br/>
                    <button type={"submit"}> +</button>
                </div>
            </div>
        </form>
    </>)
}

