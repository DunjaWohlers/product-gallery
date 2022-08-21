import React from "react";
import {Navigate, Route, Routes,} from "react-router-dom";
import ProductsSite from "./site/ProductsSite";
import DetailsProduct from "./component/DetailsProduct";
import useProducts from "./rest-api/useProducts";
import EditProductFormular from "./component/EditProductFormular";
import AddProductFormular from "./component/AddProductFormular";

export default function AllRoutes() {
    const {allProducts, addProduct, deleteProduct, updateProduct} = useProducts();

    return (<>
            <main>
                <Routes>
                    <Route path={"/products"} element={<ProductsSite
                        updateProduct={updateProduct}
                        deleteProduct={deleteProduct}
                        addProduct={addProduct}
                        allProducts={allProducts}/>}/>
                    <Route path={"/newProduct"}/>
                    <Route path={"/editProduct"}/>
                    <Route path={"/product/:id"
                    }
                           element={
                               <DetailsProduct products={allProducts}
                               />}/>
                    <Route path={"product/edit/:id"
                    }
                           element={
                               <EditProductFormular products={allProducts}
                                                    updateProduct={updateProduct}
                               />}/>
                    <Route path={"product/new"} element={<AddProductFormular addProduct={addProduct}/>}/>
                    <Route path={"*"} element={
                        <Navigate to={"/products"} replace/>
                    }/>
                </Routes>
            </main>
        </>
    )
}
