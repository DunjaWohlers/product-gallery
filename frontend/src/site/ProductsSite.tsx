import ProductCard from "../component/ProductCard";
import React from "react";
import {NewProduct, Product} from "../type/Product";
import {NavLink} from "react-router-dom";
import "./productSite.css";
import {ProductReducedInfo} from "../type/ProductReducedInfo";

type ProductsSiteProps = {
    allProducts: ProductReducedInfo[] | undefined,
    addProduct: (newProduct: NewProduct) => Promise<Product | void>,
    deleteProduct: (id: string) => Promise<void>,
    updateProduct: (id: string, newUpdateProduct: NewProduct) => Promise<string | number | void>,
}
export default function ProductsSite(props: ProductsSiteProps) {
    const admin = false;
    return (<>
            {!props.allProducts && <div> Lade Produkt-Liste... </div>}
            {props.allProducts && props.allProducts.map(product =>
                <ProductCard product={product}
                             deleteProduct={props.deleteProduct}
                             admin={admin}
                             updateProduct={props.updateProduct}
                             key={product.id}
                />
            )}
            {admin &&
                <NavLink to={"/product/new"} className={"addNav cardContainer"}> + </NavLink>
            }
        </>
    )
}
