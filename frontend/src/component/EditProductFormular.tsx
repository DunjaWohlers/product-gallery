import {NewProduct, Product} from "../type/Product";
import {FormEvent, useState} from "react";

type EditProductFormProps = {
    updateProduct: (id: string, newProduct: NewProduct) => void;
    product: Product;
}

export default function EditProductFormular(props: EditProductFormProps) {
    const [title, setTitle] = useState<string>(props.product.title);
    const [description, setDescription] = useState<string>(props.product.description);
    const [pictureUrls, setPictureUrls] = useState<string[]>(props.product.pictureUrls);
    const [price, setPrice] = useState<number>(props.product.price);
    const [availableCount, setAvailable] = useState<number>(props.product.availableCount);

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (title && description && pictureUrls && price && availableCount &&
            title.length > 0 && description.length > 0 && pictureUrls.length > 0) {
            props.updateProduct(props.product.id, {title, description, pictureUrls, price, availableCount})
        } else {
            console.error("Produktinformationen fehlen")
        }
    }

    return <>
        <form onSubmit={handleSubmit}>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={title} name={"title"} onChange={(event) => setTitle(event.target.value)}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={description} name={"description"}
                   onChange={(event) => setDescription(event.target.value)}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={pictureUrls[0]} name={"pictureUrls"}
                   onChange={(event) => setPictureUrls([event.target.value])}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={price} name={"price"} onChange={(event) => setPrice(parseInt(event.target.value))}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={availableCount} name={"available"}
                   onChange={(event) => setAvailable(parseInt(event.target.value))}/>

            <button type={"submit"}> save</button>
        </form>
    </>
}
