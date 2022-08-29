import {NewProduct, Product} from "../type/Product";
import {FormEvent, useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import "./editAddDetails.css";
import {toast} from "react-toastify";
import ImageUpload from "./ImageUpload";
import {PicObj} from "../type/PicObj";
import axios from "axios";

type EditProductFormProps = {
    updateProduct: (id: string, newUpdateProduct: NewProduct) => Promise<string | number | void>,
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
                    setPictureObj(data.pictureObj)
                })
                .catch(error => console.error(error));
        }
    }, [id, props])

    const [title, setTitle] = useState<string>();
    const [description, setDescription] = useState<string>();
    const [pictureObj, setPictureObj] = useState<PicObj[]>();
    const [price, setPrice] = useState<number>();
    const [availableCount, setAvailable] = useState<number>();

    const uploadImages = (htmlForm: HTMLFormElement) => {
        if (htmlForm === null) {
            return [];
        }
        const formData = new FormData(htmlForm);
        return axios.post("/api/image/uploadFile/", formData,
            //  {auth:{username:"frank", password:"frank123"}}
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
        const imagesObjList = await uploadImages(event.target as HTMLFormElement);
        const imagesObjListAll = await imagesObjList.push(pictureObj);

        if (id && title
            && description
            && imagesObjListAll
            && price && availableCount
            && title.length > 0
            && description.length > 0
            && imagesObjListAll.length > 0
        ) {
            props.updateProduct(id, {
                title, description,
                pictureObj: imagesObjListAll
                , price, availableCount
            }).then(() => toast.success("Produkt wurde erfolgreich editiert!", {theme: "light"}))
                .catch(() => toast.error("Update fehlgeschlagen", {theme: "light"}))
        } else {
            toast.info("Bitte fülle alle Felder aus!", {theme: "light"});
            console.log("id: " + id + ", title: " + title + ", description: " + description +
                ", imagesObjListAll: " + imagesObjListAll + ", price: " + price + ", title-length: " + title?.length
                + ", descritptionlength: " + description?.length + ", ImagesLengthAll: " + imagesObjListAll.length)
        }
    }

    return (<>
        <form className={"fullView"} onSubmit={handleSubmit}>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={title} name={"title"} onChange={(event) => setTitle(event.target.value)}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={description} name={"description"}
                   onChange={(event) => setDescription(event.target.value)}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={price} name={"price"}
                   onChange={(event) => setPrice(parseInt(event.target.value))}/>
            <input type="text" onFocus={(event) => event.target.select()}
                   defaultValue={availableCount} name={"available"}
                   onChange={(event) => setAvailable(parseInt(event.target.value))}/>
            <button type={"submit"}> save</button>
            <ImageUpload/>
        </form>
        {pictureObj ? <>
                <div className={"addedImagesForProduct"}>
                    {pictureObj.map(picObj => <img alt={"Bild"} key={picObj.url} src={picObj.url}></img>)}
                </div>
            </>
            : <p> Es konnten keine Bilder geladen werden. </p>
        }
    </>)
}
