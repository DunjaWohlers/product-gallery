import React from "react";
import {NewProduct, Product} from "../type/Product";
import EditProductFormular from "./EditProductFormular";

type ProductProps = {
    product: Product,
    deleteProduct: (id: string) => Promise<number | void>,
    admin: boolean,
    updateProduct: (id: string, newProduct: NewProduct) => void
}

export default function ProductCard(props: ProductProps) {

    const handleDelete = () => {
        props.deleteProduct(props.product.id);
    }

    return <>
        <p> {props.product.title} </p>
        <p> {props.product.description} </p>
        <img src={props.product.pictureUrls[0]}
             alt={"Bild mit dem Titel " + props.product.title + "wird geladen"}/>
        {props.admin && <button onClick={handleDelete}> delete </button>}
        <EditProductFormular updateProduct={props.updateProduct} product={props.product}/>
    </>
}
