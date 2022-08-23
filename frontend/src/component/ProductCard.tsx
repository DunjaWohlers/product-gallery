import React from "react";
import {NewProduct} from "../type/Product";
import {NavLink} from "react-router-dom";
import "./productCard.css";
import {ProductListType} from "../type/ProductListType";

type ProductProps = {
    product: ProductListType,
    deleteProduct: (id: string) => Promise<number | void>,
    admin: boolean,
    updateProduct: (id: string, newProduct: NewProduct) => void,
}

export default function ProductCard(props: ProductProps) {
    const handleDelete = () => {
        props.deleteProduct(props.product.id);
    }

    return (
        <div className={"cardContainer"}>
            {props.admin && <button onClick={handleDelete}> delete </button>}
            <NavLink className="navLink"
                     to={
                         !props.admin
                             ? "/product/" + props.product.id
                             : "/product/edit/" + props.product.id}>
                <p className={"imageContainer"}>
                    <img src={props.product.pictureUrl}
                         alt={"Bild mit dem Titel " + props.product.title + "wird geladen"}/>
                </p>
                <div><p>{props.product.price} &euro; </p></div>
                <h3> {props.product.title}
                </h3>
            </NavLink>
        </div>
    )
}
