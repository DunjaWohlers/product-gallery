import "./imageUpload.css"
import {ChangeEvent, useState} from "react";
import ImageCard from "../component/ImageCard";
import "../component/imageCard.css";

export default function ImageUpload() {
    const [fileInputs, setFileInputs] = useState<number>(1);
    const [imagePreload, setPicPreload] = useState<File[]>([]);

    const showImage = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files[0]) setPicPreload(imagePreload.concat(event.target.files[0]))
    }

    return (<>
        <label> Neues Bild hinzufügen: </label>
        <div className={"fileUploadInput"}>
            {new Array(fileInputs).fill(null).map((_, index) =>
                <div key={index}>
                    <input type={"file"} name={"file"} onChange={showImage}
                    />
                    <button type="button" onClick={() => setFileInputs(fileInputs + 1)}> +</button>
                </div>)
            }
        </div>
        <div className={"images3flex"}>
            {
                imagePreload.map(pic => <ImageCard
                    key={pic.name}
                    isZoomed={false}
                    url={URL.createObjectURL(pic)
                    }/>)
            }
        </div>
    </>)
}
