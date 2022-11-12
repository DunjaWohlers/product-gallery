import React, {useState} from "react";
import {NewProduct, Product} from "../type/Product";
import "./productSite.css";
import {UserInfo} from "../type/UserInfo";
import ProductFormular from "../component/ProductFormular";
import useProducts from "../rest-api/useProducts";
import AddImageDialog from "../component/AddImageDialog";

type ProductsSiteProps = {
    userInfo: UserInfo | undefined,
}

export default function ProductsSite(props: ProductsSiteProps) {

    const {
        allProducts,
        addProduct,
        deleteProduct,
        updateProduct,
        uploadImage, deleteImage
    } = useProducts();

    const [actualProduct, setActualProduct] = useState<Product>();

    const newProduct: NewProduct = {title: "", description: "", pictureObj: []};

    const [editPictures, setEditPictures] = useState<boolean>(false);

    const toggleEditPictures = () => {
        editPictures ? setEditPictures(false) : setEditPictures(true);
    }

    return (<>
            {props.userInfo?.authorities.includes("ADMIN") ?
                <>
                    {!allProducts && <div> Lade Produkt-Liste... </div>}
                    {allProducts && allProducts.map(product =>
                        <ProductFormular
                            deleteImage={deleteImage}
                            editPictures={editPictures}
                            product={product}
                            emptyProduct={newProduct}
                            addProduct={addProduct}
                            updateProduct={updateProduct}
                            deleteProduct={deleteProduct}
                            setActualProduct={setActualProduct}
                            key={product.id}
                        />
                    )}
                    <div className={"newProductLabel"}> NeuesProdukt hinzufügen:
                    </div>
                    <ProductFormular
                        deleteImage={deleteImage}
                        editPictures={editPictures}
                        product={undefined}
                        emptyProduct={newProduct}
                        addProduct={addProduct}
                        updateProduct={updateProduct}
                        deleteProduct={deleteProduct}
                        setActualProduct={setActualProduct}
                    />

                    {actualProduct && <AddImageDialog uploadImage={uploadImage} setActualProduct={setActualProduct}
                                                      actualProduct={actualProduct}/>
                    }

                    <button className={"switchPicEditButton"}
                            onClick={() => toggleEditPictures()}> {editPictures ? "Beschreibung bearbeiten" : "Bilder bearbeiten"} </button>
                </> :
                "Authentifizierung war nicht erfolgreich"
            }
        </>
    )
}
