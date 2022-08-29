import {NewProduct, Product} from "../type/Product";
import {FormEvent, useState} from "react";
import "./editAddDetails.css";
import ImageUpload from "./ImageUpload";
import {toast} from "react-toastify";
import axios from "axios";

type AddProductFormProps = {
    addProduct: (newProduct: NewProduct) => Promise<Product | void>
}

export default function AddProductFormular(props: AddProductFormProps) {
    const [title, setTitle] = useState<string>();
    const [description, setDescription] = useState<string>();
    const [price, setPrice] = useState<number>();
    const [availableCount, setAvailable] = useState<number>();

    const uploadImages = (htmlForm: HTMLFormElement) => {
        const formData = new FormData(htmlForm);
        return axios.post("/api/image/uploadFile/", formData,
        ).then(data => data.data)
            .then(response => {
                toast.info("Bild wurde gespeichert")
                return response;
            })
            .catch(() => {
                    toast.warn("Bild konnte nicht auf die Cloud geladen werden.");
                    return [];
                }
            );
    }

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (title
            && description
            && price
            && availableCount
            && title.length > 0
            && description.length > 0) {
            const imagesObjList = await uploadImages(event.target as HTMLFormElement);
            if (imagesObjList && imagesObjList.length > 0) {
                props.addProduct({
                    title, description, pictureObj: imagesObjList,
                    price, availableCount
                })
                    .then(() => toast.success("Produkt wurde gespeichert!", {theme: "light"}))
                    .catch(() => toast.error("Produkt konnte nicht gespeichert werden!", {theme: "light"}));
            } else {
                toast.info("Es muss mindestens ein Bild zum Produkt hinzugefügt werden.")
            }
        } else {
            toast.info("Produktinformationen fehlen!", {theme: "light"});
        }
    }

    return <>
        <form className={"fullView"} onSubmit={handleSubmit}>
            <input type="text" style={{backgroundColor: title ? 'white' : 'red'}}
                   placeholder={"Titel"} name={"title"} onChange={(event) => setTitle(event.target.value)}/>
            <input type="text" style={{backgroundColor: description ? 'white' : 'red'}}
                   placeholder={"Beschreibung"} name={"description"}
                   onChange={(event) => setDescription(event.target.value)}/>
            <input type="text" style={{backgroundColor: price ? 'white' : 'red'}}
                   placeholder={"Preis"} name={"price"}
                   onChange={(event) => setPrice(parseInt(event.target.value))}/>
            <input type="text" style={{backgroundColor: availableCount ? 'white' : 'red'}}
                   placeholder={"Anzahl verfügbar"} name={"available"}
                   onChange={(event) => setAvailable(parseInt(event.target.value))}/>
            <button disabled={!title || !description || !price || !availableCount}
                    type={"submit"}> save
            </button>
            <ImageUpload/>
        </form>
    </>
}
