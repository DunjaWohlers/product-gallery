import {NewProduct, Product} from "../type/Product";
import {FormEvent, useState} from "react";

type AddProductFormProps = {
    addProduct: (newProduct: NewProduct) => Promise<Product>;
}

export default function AddProductFormular(props: AddProductFormProps) {
    const [title, setTitle] = useState<string>();
    const [description, setDescription] = useState<string>();
    const [pictureUrls, setPictureUrls] = useState<string[]>();
    const [price, setPrice] = useState<number>();
    const [available, setAvailable] = useState<number>();

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (title && description && pictureUrls && price && available &&
            title.length > 0 && description.length > 0 && pictureUrls.length > 0) {
            const prod = props.addProduct({title, description, pictureUrls, price, available})
            console.log(prod);
        } else {
            console.error("Produktinformationen fehlen")
        }
    }

    return <>
        <form onSubmit={handleSubmit}>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={"Titel"} name={"title"} onChange={(event) => setTitle(event.target.value)}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={"Beschreibung"} name={"description"}
                   onChange={(event) => setDescription(event.target.value)}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={"Bild-URL"} name={"pictureUrls"}
                   onChange={(event) => setPictureUrls([event.target.value])}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={"Preis"} name={"price"} onChange={(event) => setPrice(parseInt(event.target.value))}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={"Anzahl verfügbar"} name={"available"}
                   onChange={(event) => setAvailable(parseInt(event.target.value))}/>

            <button type={"submit"}> save</button>
        </form>
    </>
}
