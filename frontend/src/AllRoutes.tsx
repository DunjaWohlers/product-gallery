import React from "react";
import {Navigate, Route, Routes,} from "react-router-dom";
import ProductsSite from "./site/ProductsSite";
import DetailsProduct from "./component/DetailsProduct";
import useProducts from "./rest-api/useProducts";


export default function AllRoutes() {

    const {
        allProducts,
        getOneProductPerId,
    } = useProducts();

    return (<>
            <Routes>
                <Route path={"/products"} element={<ProductsSite
                    allProducts={allProducts}
                />}/>
                <Route path={"/product/:id"}
                       element={
                           <DetailsProduct
                               getOneProductPerId={getOneProductPerId}
                           />}/>
                <Route path={"*"} element={
                    <Navigate to={"/products"} replace/>
                }/>
            </Routes>
        </>
    )
}
