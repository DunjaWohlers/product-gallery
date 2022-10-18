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
                <h3> {props.product.title}</h3>
                <div className={"imageAndText"}>
                    <ImageCard url={props.product.pictureObj[0].url} isZoomed={true}/>
                    <p> {props.product.description}
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. A ab aperiam assumenda at aut cumque
                        ex, impedit laudantium obcaecati perferendis quia repellendus sunt. Earum, exercitationem libero
                        maiores mollitia nobis sapiente?</p>
                </div>
            </NavLink>
        </div>
    )
}
