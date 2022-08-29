//import "./imageUpload.css"
import {forwardRef, useImperativeHandle, useRef, useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import {PicObj} from "../type/PicObj";

export const ImageUpload = forwardRef((
        props: {
            setPictureObj: (urls: PicObj[]) => void,
            pictureObj: PicObj[],
        }, ref
    ) => {
        useImperativeHandle(ref, () => ({
            uploadFiles() {
                return handleSubmit(formRef.current);
            }
        }));
        const formRef = useRef<HTMLFormElement>(null);
        const [fileInputs, setFileInputs] = useState<number>(1);
        //const handleFileChange = (e:ChangeEvent<HTMLInputElement>)=>{
        //    let ar = [...pictureFiles,e.target]
        //    setPictureFiles(ar);
        //    console.log(pictureFiles?.length);
        //}
        const handleSubmit = (form: HTMLFormElement | null) => {
            if (form === null) {
                return [];
            }
            const formData = new FormData(form);
            return axios.post("/api/image/uploadFile/", formData,
                //  {auth:{username:"frank", password:"frank123"}}
            ).then(data => data.data)
                .then(response => {
                    props.setPictureObj(response);
                    toast.info("Bild wurde gespeichert")
                    return response;
                })
                .catch(() => {
                        toast.warn("Bild konnte nicht auf die Cloud geladen werden.");
                        return [];
                    }
                );
        }

        return (<>
            <div className={"addedImagesForProduct"}>
                {props.pictureObj.map(picObj => <img alt={"Bild"} key={picObj.url} src={picObj.url}></img>)}
            </div>
            <form ref={formRef}>
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
)
