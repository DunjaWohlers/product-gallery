import {NewProduct, Product} from "../type/Product";
import {FormEvent, useState} from "react";
import {useParams} from "react-router-dom";
import "./editAddDetails.css";

type EditProductFormProps = {
    products: Product[] | undefined,
    updateProduct: (id: string, newProduct: NewProduct) => void,
}

export default function EditProductFormular(props: EditProductFormProps) {
    let {id} = useParams();
    const thisProduct: Product | undefined = props.products?.find(element => element.id === id);

    const [title, setTitle] = useState<string>(thisProduct ? thisProduct.title : "");
    const [description, setDescription] = useState<string>(thisProduct ? thisProduct.description : "");
    const [pictureUrls, setPictureUrls] = useState<string[]>(thisProduct ? thisProduct.pictureUrls : []);
    const [price, setPrice] = useState<number>(thisProduct ? thisProduct.price : 0);
    const [availableCount, setAvailable] = useState<number>(thisProduct ? thisProduct.availableCount : 0);

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (id && title && description
            && pictureUrls
            && price && availableCount &&
            title.length > 0 && description.length > 0
            && pictureUrls.length > 0
        ) {
            props.updateProduct(id, {
                title, description,
                pictureUrls
                , price, availableCount
            })
        } else {
            console.error("Produktinformationen fehlen")
        }
    }

    return < >
        {
            <form className={"fullView"} onSubmit={handleSubmit}>
                <input type="text" onFocus={(event) => event.target.select()}
                       defaultValue={title} name={"title"} onChange={(event) => setTitle(event.target.value)}/>
                <input type="text" onFocus={(event) => event.target.select()}
                       defaultValue={description} name={"description"}
                       onChange={(event) => setDescription(event.target.value)}/>
                <input type="text" onFocus={(event) => event.target.select()}
                       defaultValue={pictureUrls ? pictureUrls[0] : ""} name={"pictureUrls"}
                       onChange={(event) => setPictureUrls([event.target.value])}/>
                <input type="text" onFocus={(event) => event.target.select()}
                       defaultValue={price} name={"price"}
                       onChange={(event) => setPrice(parseInt(event.target.value))}/>
                <input type="text" onFocus={(event) => event.target.select()}
                       defaultValue={availableCount} name={"available"}
                       onChange={(event) => setAvailable(parseInt(event.target.value))}/>
                <button type={"submit"}> save</button>
            </form>
        }
    </>
}
