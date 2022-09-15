import React from "react";
import {NewProduct} from "../type/Product";
import {NavLink} from "react-router-dom";
import "./productCard.css";
import {ProductReducedInfo} from "../type/ProductReducedInfo";
import {toast} from "react-toastify";
import ImageCard from "./ImageCard";

type ProductProps = {
    product: ProductReducedInfo,
    deleteProduct: (id: string) => Promise<number | void>,
    admin: boolean | undefined,
    updateProduct: (id: string, newUpdateProduct: NewProduct) => Promise<string | number | void>,
}

export default function ProductCard(props: ProductProps) {
    const handleDelete = () => {
        props.deleteProduct(props.product.id)
            .then(() => toast.success("Produkt und zugehörige Bilder wurden gelöscht", {theme: "light"}))
            .catch(() => toast.error("Löschen fehlgeschlagen!", {theme: "light"}));
    }

    return (
        <div className={"productCard"}>
            {props.admin &&
                <div className={"nullHeightBoxForOverflow"}>
                    <button onClick={handleDelete}> delete</button>
                </div>
            }
            <NavLink className="navLink" to={
                !props.admin
                    ? "/product/" + props.product.id
                    : "/product/edit/" + props.product.id}>
                <ImageCard url={props.product.pictureUrl} isZoomed={true}/>
                <div className={"textField"}>
                    <h3> {props.product.title}</h3>
                    <div><p className={"price"}>{props.product.price} &euro; </p></div>
                </div>
            </NavLink>
        </div>
    )
}
