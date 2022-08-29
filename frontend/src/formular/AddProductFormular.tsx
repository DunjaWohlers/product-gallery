import {NewProduct, Product} from "../type/Product";
import {FormEvent, useState} from "react";
import "./editAddDetails.css";
import {ImageUpload} from "./ImageUpload";
import {toast} from "react-toastify";
import {PicObj} from "../type/PicObj";

type AddProductFormProps = {
    addProduct: (newProduct: NewProduct) => Promise<Product | void>
}

export default function AddProductFormular(props: AddProductFormProps) {
    const [title, setTitle] = useState<string>();
    const [description, setDescription] = useState<string>();
    const [pictureObj, setPictureObj] = useState<PicObj[]>([]);
    const [price, setPrice] = useState<number>();
    const [availableCount, setAvailable] = useState<number>();
    const [imageUploads, setImageUploads] = useState<HTMLFormElement>();
    // const imageUploadRef = useRef<typeof ImageUpload>(null);
    // const uploadImages = () => {
    // return (imageUploadRef?.current as unknown as { uploadFiles: () => Promise<PicObj[]> })?.uploadFiles();
    //};

    const uploadImages
    ()
    {

    }

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const imagesObjList = await uploadImages();
        if (title && description &&
            imagesObjList && price && availableCount &&
            title.length > 0 && description.length > 0 && imagesObjList.length > 0) {
            props.addProduct({
                title, description, pictureObj: imagesObjList,
                //props.addProduct({title, description, pictureObj: imagesObjList,
                price, availableCount
            })
                .then(() => toast.success("Produkt wurde gespeichert!", {theme: "light"}))
                .catch(() => toast.error("Produkt konnte nicht gespeichert werden!", {theme: "light"}));
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
                   placeholder={"Preis"} name={"price"} onChange={(event) => setPrice(parseInt(event.target.value))}/>
            <input type="text" style={{backgroundColor: availableCount ? 'white' : 'red'}}
                   placeholder={"Anzahl verfügbar"} name={"available"}
                   onChange={(event) => setAvailable(parseInt(event.target.value))}/>
            <button disabled={!title || !description || !price || !availableCount}

                    type={"submit"}> save
            </button>
        </form>

        <ImageUpload setImagesUpload={setImageUploads}
            //ref={imageUploadRef}
                     pictureObj={pictureObj} setPictureObj={setPictureObj}/>
    </>
}
