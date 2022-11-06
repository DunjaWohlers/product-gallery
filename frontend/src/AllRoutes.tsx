import {Route, Routes,} from "react-router-dom";
import ProductsSite from "./site/ProductsSite";
import Login from "./formular/Login";
import {UserInfo} from "./type/UserInfo";

export default function AllRoutes(
    props: {
        userInfo: UserInfo | undefined,
        authenticationChanged: () => void,
    }) {


    return (<>
            <Routes>
                <Route path={"/login"} element={
                    <Login authenticationChanged={props.authenticationChanged}/>}/>
                <Route path={"/"} element={
                    <ProductsSite
                        userInfo={props.userInfo}
                    />}
                />

            </Routes>
        </>
    )
}
