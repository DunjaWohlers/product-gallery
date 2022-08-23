import {NewProduct, Product} from "../type/Product";
import {FormEvent, useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import "./editAddDetails.css";

type EditProductFormProps = {
    updateProduct: (id: string, newProduct: NewProduct) => void,
    getOneProductPerId: (id: string) => Promise<Product>,
}

export default function EditProductFormular(props: EditProductFormProps) {

    const {id} = useParams();
    useEffect(() => {
        if (id) {
            props.getOneProductPerId(id)
                .then((data) => {
                    setTitle(data.title);
                    setDescription(data.description);
                    setAvailable(data.availableCount);
                    setPrice(data.price);
                    setPictureUrls(data.pictureUrls)
                })
                .catch(error => console.error(error));
        }
    }, [id, props])

    const [title, setTitle] = useState<string>();
    const [description, setDescription] = useState<string>();
    const [pictureUrls, setPictureUrls] = useState<string[]>();
    const [price, setPrice] = useState<number>();
    const [availableCount, setAvailable] = useState<number>();

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
