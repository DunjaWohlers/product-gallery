import {NewProduct, Product} from "../type/Product";
import React, {FormEvent, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import "./productFormular.css";
import {toast} from "react-toastify";
import ImageUpload from "./ImageUpload";
import {PicObj} from "../type/PicObj";
import axios from "axios";
import ImageCard from "../component/ImageCard";
import InputStringElement from "./inputFormFields/InputStringElement";
import TextAreaElement from "./inputFormFields/TextAreaElement";

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
                    setPictureObjects(data.pictureObj)
                })
                .catch(() => toast.error("Daten des Produkts konnten nicht geladen werden."));
        }
    }, [id, props])

    const [title, setTitle] = useState<string>();
    const [description, setDescription] = useState<string>();
    const [pictureObjects, setPictureObjects] = useState<PicObj[]>([]);

    const uploadImages = (formData: FormData) => {
        return axios.post("/api/image/uploadFile", formData,
        ).then(data => data.data)
            .then(response => {
                !id && toast.info("Bild wurde gespeichert")
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
            && title.length > 0
            && description.length > 0
        ) {
            const htmlFormElement = event.target as HTMLFormElement;
            const formData = new FormData(htmlFormElement);
            const file: FormDataEntryValue | null = formData.get("file") as File;

            let imagesObjList = file.name.length > 0 && await uploadImages(formData);
            imagesObjList = imagesObjList.concat(pictureObjects);
            const newProduct = {
                title, description,
                pictureObj: imagesObjList
            }
            if (id) {
                props.updateProduct(id, newProduct)
                    .then(() => toast.success("Produkt wurde erfolgreich editiert!"))
                    .catch(() => toast.error("Update fehlgeschlagen"))
                    .then(() => navigate("/"))
            } else if (imagesObjList && imagesObjList.length > 0) {
                props.addProduct(newProduct)
                    .then(() => toast.success("Produkt wurde gespeichert!"))
                    .catch(() => toast.error("Produkt konnte nicht gespeichert werden!"))
                    .then(() => navigate("/"));
            } else {
                toast.info("Es muss mindestens ein Bild zum Produkt hinzugefügt werden.")
            }
        } else {
            toast.info("Bitte fülle alle Felder aus!");
        }
    }

    const deleteSinglePictureFromProduct = (picObjToDelete: PicObj) => {
        if (pictureObjects && pictureObjects.length > 1) {
            axios.delete("/api/products/" + id + "/" + picObjToDelete.publicId)
                .catch(() => toast.error("Bild löschen fehlgeschlagen."))
                .then(() => navigate("/product/edit/" + id));
        } else {
            toast.error("Du benötigst mindestens ein Bild für dein Produkt.")
        }
    }

    return (<>
        <form className={"form"}
              onSubmit={handleSubmit}>
            <InputStringElement placeholder="Titel"
                                onChangeSetFunction={setTitle}
                                value={title}
            />
            <TextAreaElement placeholder={"Beschreibung"}
                             onChangeSetFunction={setDescription}
                             value={description}
            />
            <ImageUpload/>
            <button className={"opacity"} type={"submit"}> save
            </button>
        </form>
        {(pictureObjects && pictureObjects.length > 0) && <>
            {pictureObjects.map(picObj =>
                <div key={picObj.url} className={"productCard"}>
                    <div className={"nullHeightBoxForOverflow"}>
                        <button onClick={() => deleteSinglePictureFromProduct(picObj)}> delete</button>
                    </div>
                    <ImageCard url={picObj.url} isZoomed={false}/>
                </div>
            )}
        </>
        }
    </>)
}
