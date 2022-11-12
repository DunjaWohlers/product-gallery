import React, {ChangeEvent, FormEvent, MouseEventHandler, useState} from "react";
import {toast} from "react-toastify";
import {Product} from "../type/Product";
import "./addImageDialog.css";

type AddImageDialogProps = {
    uploadImage: (formData: FormData, id: number) => Promise<string | number>,
    setActualProduct: React.Dispatch<React.SetStateAction<Product | undefined>>,
    actualProduct: Product,
}

export default function AddImageDialog({uploadImage, setActualProduct, actualProduct}: AddImageDialogProps) {

    const [imagePreload, setPicPreload] = useState<File>();
    const showImage = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files[0])
            setPicPreload(event.target.files[0])
    }

    const addImageToProduct = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const htmlFormElement = event.target as HTMLFormElement;
        const formData = new FormData(htmlFormElement);
        const file: FormDataEntryValue | null = formData.get("file") as File;

        if (!file) {
            toast.error("Keine Datei zum Hochladen ausgewählt");
            return;
        }

        if (!actualProduct) {
            toast.error("Kein Produkt ausgewählt");
            return
        }

        await uploadImage(formData, actualProduct.id);
        setActualProduct(undefined);
    }

    const closeOnBackgroundClick: MouseEventHandler<HTMLDivElement> = (event) => {
        if ((event.target as HTMLElement).classList.contains("addImageBackground")) {
            setActualProduct(undefined);
        }
    }

    return (
        <div className={"addImageBackground"} onClick={closeOnBackgroundClick}>
            <div className={"addImageContainer"}>
                <p className={actualProduct ? "visible" : "invisible"}>
                    Bild zu {actualProduct.title} hinzufügen: ({actualProduct?.id})</p>
                <form
                    className={actualProduct ? "addImageForm visible" : "addImageForm invisible"}
                    onSubmit={addImageToProduct}>
                    <div className={"fileInputButton"}>
                        <input required
                               id="fileInput" type={"file"} name={"file"} onChange={showImage}/>
                    </div>
                    <label htmlFor={"fileInput"} className={"imageContainer"}>
                        {imagePreload && <img src={imagePreload && URL.createObjectURL(imagePreload)} alt={"img"}/>
                        }
                    </label>
                    <button type="submit"> &#10003; </button>
                </form>
            </div>
        </div>
    )
}
