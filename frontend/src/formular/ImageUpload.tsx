//import "./imageUpload.css"
import {FormEvent, useRef, useState} from "react";

import {PicObj} from "../type/PicObj";

export default function ImageUpload(
    props: {
        setPictureObj: (urls: PicObj[]) => void,
        pictureObj: PicObj[],
        setImagesUpload: (image: HTMLFormElement) => void,
    }) {
    //useImperativeHandle(ref, () => ({
    //  uploadFiles() {
    //        return handleSubmit(formRef.current);
    //   }

    const formRef = useRef<HTMLFormElement>(null);
    const [fileInputs, setFileInputs] = useState<number>(1);
    //const handleFileChange = (e:ChangeEvent<HTMLInputElement>)=>{
    //    let ar = [...pictureFiles,e.target]
    //    setPictureFiles(ar);
    //    console.log(pictureFiles?.length);
    //}


    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
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

