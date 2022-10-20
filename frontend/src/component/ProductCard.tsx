import React from "react";
import {NewProduct, Product} from "../type/Product";
import {NavLink} from "react-router-dom";
import "./productCard.css";
import {toast} from "react-toastify";
import ImageCard from "./ImageCard";

type ProductProps = {
    product: Product,
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
                props.admin
                    ? "/product/edit/" + props.product.id
                    : "/product/" + props.product.id
            }>
                <ImageCard url={props.product.pictureObj[0].url} isZoomed={true}/>
                <div className={"textField"}>
                    <h3> {props.product.title}</h3>
                </div>
            </NavLink>
        </div>
    )
}
