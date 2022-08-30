import {NewProduct, Product} from "../type/Product";
import React, {FormEvent, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import "./editAddDetails.css";
import {toast} from "react-toastify";
import ImageUpload from "./ImageUpload";
import {PicObj} from "../type/PicObj";
import axios from "axios";

type ProductFormProps = {
    addProduct: (newProduct: NewProduct) => Promise<Product | void>
    updateProduct: (id: string, newUpdateProduct: NewProduct) => Promise<string | number | void>,
    getOneProductPerId: (id: string) => Promise<Product>,
}

export default function ProductFormular(props: ProductFormProps) {
    const {id} = useParams();
    const navigate = useNavigate();
    useEffect(() => {
        if (id) {
            props.getOneProductPerId(id)
                .then((data) => {
                    setTitle(data.title);
                    setDescription(data.description);
                    setAvailable(data.availableCount);
                    setPrice(data.price);
                    setPictureObjects(data.pictureObj)
                })
                .catch(error => console.error(error));
        }
    }, [id, props])

    const [title, setTitle] = useState<string>();
    const [description, setDescription] = useState<string>();
    const [pictureObjects, setPictureObjects] = useState<PicObj[]>([]);
    const [price, setPrice] = useState<number>();
    const [availableCount, setAvailable] = useState<number>();

    const uploadImages = (htmlForm: HTMLFormElement) => {
        const formData = new FormData(htmlForm);
        return axios.post("/api/image/uploadFile", formData,
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
            && description.length > 0
        ) {
            let imagesObjList = await uploadImages(event.target as HTMLFormElement);
            imagesObjList = imagesObjList.concat(pictureObjects);
            const newProduct = {
                title, description,
                pictureObj: imagesObjList,
                price, availableCount
            }
            if (imagesObjList && imagesObjList.length > 0) {
                if (id) {
                    props.updateProduct(id, newProduct)
                        .then(() => toast.success("Produkt wurde erfolgreich editiert!"))
                        .catch(() => toast.error("Update fehlgeschlagen"))
                        .then(() => navigate("/"))
                } else {
                    props.addProduct(newProduct)
                        .then(() => toast.success("Produkt wurde gespeichert!"))
                        .catch(() => toast.error("Produkt konnte nicht gespeichert werden!"))
                        .then(() => navigate("/"));
                }
            } else {
                toast.info("Es muss mindestens ein Bild zum Produkt hinzugefügt werden.")
            }
        } else {
            toast.info("Bitte fülle alle Felder aus!");
        }
    }

    const deleteSinglePicture = (picObjToDelete: PicObj) => {
        if (pictureObjects && pictureObjects.length > 1) {
            axios.delete("/api/" + id + "/" + picObjToDelete.public_id)
                .then(response => response.data)
                .catch(() => toast.error("Bild löschen fehlgeschlagen."))
                .then(() => navigate("/product/edit/" + id));
        } else {
            toast.error("Du benötigst mindestens ein Bild für dein Produkt.")
        }
    }

    return (<>
        <form className={"form"} onSubmit={handleSubmit}>
            <label> Titel: </label>
            <input type="text"
                   placeholder={"Titel"}
                   defaultValue={title}
                   name={"title"}
                   onChange={(event) => setTitle(event.target.value)}
                   className={title ? "good" : "bad"}/>
            <label> Beschreibung: </label>
            <textarea placeholder={"Beschreibung"}
                      defaultValue={description}
                      name={"description"}
                      onChange={(event) => setDescription(event.target.value)}
                      className={description ? "good" : "bad"}>
            </textarea>
            <label> Preis: </label>
            <input type="text"
                   placeholder={"Preis"}
                   defaultValue={price}
                   name={"price"}
                   onChange={(event) => setPrice(parseInt(event.target.value))}
                   className={price ? "good" : "bad"}/>
            <label> Anzahl vorrätig: </label>
            <input type="text"
                   placeholder={"Anzahl verfügbar"}
                   defaultValue={availableCount}
                   name={"available"}
                   onChange={(event) => setAvailable(parseInt(event.target.value))}
                   className={availableCount ? "good" : "bad"}/>
            <ImageUpload/>
            <button type={"submit"}> save
            </button>
        </form>
        {pictureObjects ? <>
                {pictureObjects.map(picObj =>
                    <div key={picObj.url} className={"cardContainer"}>
                        <button onClick={() => deleteSinglePicture(picObj)}> delete</button>
                        <div className={"imageContainer"}>
                            <img alt={"Bild"} key={picObj.url} src={picObj.url}></img>
                        </div>
                    </div>
                )}
            </>
            : <p> Es konnten keine Bilder geladen werden. </p>
        }
    </>)
}