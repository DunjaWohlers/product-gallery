import React, {useState} from "react";
import {Navigate, Route, Routes,} from "react-router-dom";
import ProductsSite from "./site/ProductsSite";
import DetailsProduct from "./component/DetailsProduct";
import useProducts from "./rest-api/useProducts";
import ProductFormular from "./formular/ProductFormular";
import Login from "./formular/Login";
import {UserInfo} from "./type/UserInfo";
import UserProductList from "./site/UserProductList";
import {OrderDetailsItem} from "./type/OrderItem";

export default function AllRoutes(
    props: {
        userInfo: UserInfo | undefined,
        authenticationChanged: () => void,
    }) {
    const [actualOrderDetailsItems, setActualOrderDetailsItems] = useState<OrderDetailsItem[]>([])

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
                    userInfo={props.userInfo}
                />}/>
                <Route path={"/product/:id"}
                       element={
                           <DetailsProduct
                               actualOrderDetailsItems={actualOrderDetailsItems}
                               setActualOrderDetailsItems={setActualOrderDetailsItems}
                               getOneProductPerId={getOneProductPerId}
                               username={props.userInfo?.name}
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
                <Route path={"/myproducts"} element={
                    <UserProductList
                        actualOrderDetailsItems={actualOrderDetailsItems}
                        setActualOrderDetailsItems={setActualOrderDetailsItems}
                        userInfo={props.userInfo}
                    />}
                />
                <Route path={"*"} element={
                    <Navigate to={"/products"} replace/>
                }/>
            </Routes>
        </>
    )
}
