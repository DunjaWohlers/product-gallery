import React from "react";
import useProducts from "../rest-api/useProducts";
import GuestProduct from "./GuestProduct";


export default function GuestProductsSite() {

    const {
        allProducts
    } = useProducts();

    return (<>
            {!allProducts && <div> Lade Produkt-Liste... </div>}
            {allProducts && allProducts.map(product =>
                <GuestProduct
                    product={product}
                    key={product.id}
                />
            )}
        </>
    )
}
