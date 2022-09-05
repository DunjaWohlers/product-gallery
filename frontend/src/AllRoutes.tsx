import React from "react";
import {Navigate, Route, Routes,} from "react-router-dom";
import ProductsSite from "./site/ProductsSite";
import DetailsProduct from "./component/DetailsProduct";
import useProducts from "./rest-api/useProducts";
import ProductFormular from "./formular/ProductFormular";
import Login from "./site/Login";

export default function AllRoutes(
    props: {
        username: string | undefined,
        authenticationChanged: () => void,
    }) {
    const {
        allProducts,
        addProduct,
        deleteProduct,
        updateProduct,
        getOneProductPerId,
    } = useProducts();

    return (<>
            <Routes>
                <Route path={"/login"} element={<Login authenticationChanged={props.authenticationChanged}/>}/>
                <Route path={"/products"} element={<ProductsSite
                    updateProduct={updateProduct}
                    deleteProduct={deleteProduct}
                    addProduct={addProduct}
                    allProducts={allProducts}
                />}/>
                <Route path={"/product/:id"}
                       element={
                           <DetailsProduct
                               getOneProductPerId={getOneProductPerId}
                           />}/>
                <Route path={"product/edit/:id"}
                       element={
                           <ProductFormular
                               addProduct={addProduct}
                               updateProduct={updateProduct}
                               getOneProductPerId={getOneProductPerId}
                           />}
                />
                <Route path={"product/new"} element={
                    <ProductFormular
                        addProduct={addProduct}
                        updateProduct={updateProduct}
                        getOneProductPerId={getOneProductPerId}
                    />}
                />
                <Route path={"*"} element={
                    <Navigate to={"/products"} replace/>
                }/>
            </Routes>
        </>
    )
}
