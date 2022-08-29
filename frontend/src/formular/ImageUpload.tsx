//import "./imageUpload.css"
import {useState} from "react";

export default function ImageUpload() {
    const [fileInputs, setFileInputs] = useState<number>(1);
    return (<>
        <div className={"container"}>
            <div className={"fileUploadInput"}>
                <label> Neues Bild hinzufügen: </label>
                {new Array(fileInputs).fill(null).map((_, index) =>
                    <div key={index}>
                        <input type={"file"} name={"file"}
                        />
                    </div>)
                }
                <button type="button" onClick={() => setFileInputs(fileInputs + 1)}>Addfile</button>
                <br/>
            </div>
        </div>
    </>)
}
