import React from "react";
import {Product} from "../type/Product";
import {NavLink} from "react-router-dom";
import "./productCard.css";
import ImageCard from "./ImageCard";
import "./imageCard.css";

type ProductProps = {
    product: Product,
}

export default function ProductCard(props: ProductProps) {


    return (
        <div className={"productCard"}>
            <NavLink className="navLink" to={
                "/product/" + props.product.id
            }>
                <ImageCard url={props.product.pictureObj[0].url} isZoomed={true}/>
                <div className={"textField"}>
                    <h3> {props.product.title}</h3>
                    <div><p className={"price"}>{props.product.price} &euro; </p></div>
                </div>
            </NavLink>
        </div>
    )
}
