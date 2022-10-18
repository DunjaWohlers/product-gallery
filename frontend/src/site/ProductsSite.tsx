import ProductCard from "../component/ProductCard";
import React from "react";
import "./productSite.css";
import {Product} from "../type/Product";

type ProductsSiteProps = {
    allProducts: Product[] | undefined,
}

export default function ProductsSite(props: ProductsSiteProps) {


    return (<>
            {!props.allProducts && <div> Lade Produkt-Liste... </div>}
            {props.allProducts && props.allProducts.map(product =>
                <ProductCard product={product}
                             key={product.id}
                />
            )}
        </>
    )
}
