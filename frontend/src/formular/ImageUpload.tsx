import "./imageUpload.css"
import {ChangeEvent, useState} from "react";
import ImageCard from "../component/ImageCard";

export default function ImageUpload() {
    const [fileInputs, setFileInputs] = useState<number>(1);
    const [picPreload, setPicPreload] = useState<File[]>([]);

    const showImage = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) setPicPreload(picPreload.concat(event.target.files[0]))
    }

    return (<>
        <div className={"fileUploadInput"}>
            <label> Neues Bild hinzufügen: </label>
            {new Array(fileInputs).fill(null).map((_, index) =>
                <div key={index}>
                    <input type={"file"} name={"file"} onChange={showImage}
                    />
                    <button type="button" onClick={() => setFileInputs(fileInputs + 1)}> +</button>
                </div>)
            }
        </div>
        {picPreload.map(pic => <ImageCard url={URL.createObjectURL(pic)}/>)
        }
    </>)
}
