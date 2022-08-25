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
    const [pictureUrls, setPictureUrls] = useState<string[]>(["Bild-URL"]);
    const [price, setPrice] = useState<number>();
    const [availableCount, setAvailable] = useState<number>();

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (pictureUrls) {
            checkImage(pictureUrls[0]);
        }
        if (title && description && pictureUrls && price && availableCount &&
            title.length > 0 && description.length > 0 && pictureUrls.length > 0) {
            checkImage(pictureUrls[0]);
            props.addProduct({title, description, pictureUrls, price, availableCount})
                .then(() => toast.success("Produkt wurde gespeichert!", {theme: "light"}))
                .catch(() => toast.error("Produkt konnte nicht gespeichert werden!", {theme: "light"}));
        } else {
            toast.info("Produktinformationen fehlen!", {theme: "light"});
        }
    }

    const checkImage = (url: string) => {
        axios.get(url)
            .then(result => result.headers)
            .then((() => toast.success("Bildpfad ist erreichbar.", {theme: "light"})))
            .catch(() => toast.warn("Bild-URL ist nicht erreichbar ... überprüfe die Eingabe!", {theme: "light"}))
    }

    return <>
        <form className={"fullView"} onSubmit={handleSubmit}>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={"Titel"} name={"title"} onChange={(event) => setTitle(event.target.value)}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={"Beschreibung"} name={"description"}
                   onChange={(event) => setDescription(event.target.value)}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   value={pictureUrls[0]} name={"pictureUrls"}
                   onChange={(event) => setPictureUrls([event.target.value])}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={"Preis"} name={"price"} onChange={(event) => setPrice(parseInt(event.target.value))}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={"Anzahl verfügbar"} name={"available"}
                   onChange={(event) => setAvailable(parseInt(event.target.value))}/>
            <button type={"submit"}> save</button>
        </form>
        <ImageUpload setPictureUrls={setPictureUrls}/>
    </>
}
