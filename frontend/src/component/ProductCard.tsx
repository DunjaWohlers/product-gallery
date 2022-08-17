import React from "react";
import {Product} from "../type/Product";

type ProductProps = {
    product: Product;
}

export default function ProductCard(props: ProductProps) {

    return <>
        {props.product.title}
    </>
}