import React from "react";
import {Product} from "../type/Product";

type ProductProps = {
    product: Product,
    deleteProduct: (id: string) => Promise<number | void>,
    admin: boolean
}

export default function ProductCard(props: ProductProps) {

    const handleDelete = () => {
        let existedAndDeleted: Promise<void | number> = props.deleteProduct(props.product.id);
    }

    return <>
        <p> {props.product.title} </p>
        <p> {props.product.description} </p>
        <img src={props.product.pictureUrls[0]}
             alt={"Bild mit dem Titel " + props.product.title + "wird geladen"}/>
        {props.admin && <button onClick={handleDelete}> delete </button>}
    </>
}
