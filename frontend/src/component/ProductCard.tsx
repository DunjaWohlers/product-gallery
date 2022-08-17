import React from "react";
import {Product} from "../type/Product";

type ProductProps = {
    product: Product;
}

export default function ProductCard(props: ProductProps) {

    return <>
        <p> {props.product.title} </p>
        <p> {props.product.description} </p>
        <img src={props.product.pictureUrls[0]}
             alt={"Bild mit dem Titel " + props.product.title + "wird geladen"}/>
    </>
}