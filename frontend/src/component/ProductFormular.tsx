import {NewProduct, Product} from "../type/Product";
import React, {useState} from "react";
import "./productFormular.css";
import {toast} from "react-toastify";

type ProductFormProps = {
    product: Product | undefined,
    emptyProduct: NewProduct,
    addProduct: (newProduct: NewProduct) => Promise<Product | void>
    updateProduct: (id: number, newUpdateProduct: NewProduct) => Promise<string | number | void>,
    deleteProduct: (id: number) => Promise<void>,
    setActualProduct: (product: Product) => void,
    editPictures: boolean,
    deleteImage: (id: number) => void,
}

export default function ProductFormular(props: ProductFormProps) {

    const [title, setTitle] = useState<string>(props.product ? props.product.title : "");
    const [description, setDescription] = useState<string>(props.product ? props.product.description : "");
    const [deletableImage, setDeleteableImage] = useState<boolean>(false);

    const handleSubmit = async () => {
        const newProduct = {
            title, description
        }
        if (!props.product && title && title.length > 0) {
            await props.addProduct(newProduct);
        } else if (props.product && title && title.length > 0) {
            await props.updateProduct(props.product.id, newProduct)
        } else {
            toast.info("Gib bitte mindestens einen Titel an!");
        }
    }

    const handleDelete = () => {
        props.product && props.deleteProduct(props.product?.id)
    }

    const setId = () => {
        props.product && props.setActualProduct(props.product);
    }

    const toggleDeletableImage = () => {
        deletableImage ? setDeleteableImage(false) : setDeleteableImage(true);
    }

    const handleDeleteImage = (id: number) => {
        if (deletableImage) {
            props.deleteImage(id);
        }
    }

    return (
        <div className={"line"}>
            <input type="text"
                   autoComplete={"off"}
                   placeholder={"Titel"}
                   defaultValue={title}
                   name={"title"}
                   onChange={(event) =>
                       setTitle(
                           event.target.value
                       )}
                   className={title ? "good" : "bad"}/>

            {!props.editPictures && <textarea
                autoComplete={"off"}
                placeholder={"Beschreibung"}
                defaultValue={description}
                name={"description"}
                onChange={(event) =>
                    setDescription(
                        event.target.value
                    )}
                className={description ? "good" : "bad"}/>
            }
            {props.editPictures &&
                <div className={"imageContainer"}>
                    <div className={"imagesBox"}>
                        {(props.product?.pictureObj && props.product.pictureObj.length > 0) && <>
                            {props.product.pictureObj.map(picObj => <div key={picObj.url}>
                                    <img src={picObj.url}
                                         className={deletableImage ? "deleteImage" : ""}
                                         alt={"img"} onClick={() => handleDeleteImage(picObj.imageId)}/>
                                </div>
                            )}
                        </>
                        }
                    </div>
                    {props.product?.id &&
                        <div className={"imagesButtonBox"}>
                            <button type="button" onClick={toggleDeletableImage}> -</button>
                            <button type="button" onClick={setId}> +</button>
                        </div>
                    }

                </div>
            }

            <button className={"opacity"} type={"button"} onClick={handleSubmit}> &#10003;
            </button>
            {props.product && <button type={"button"} onClick={handleDelete}>&#10007;</button>}
        </div>)
}

