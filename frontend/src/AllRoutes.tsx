import {Route, Routes,} from "react-router-dom";
import ProductsSite from "./site/ProductsSite";
import Login from "./formular/Login";
import useUser from "./rest-api/useUser";
import GuestProductsSite from "./GuestView/GuestProductsSite";

export default function AllRoutes() {

    const {userInfo, loginUser} = useUser();

    return (<>
            <Routes>
                <Route path={"/"} element={
                    <GuestProductsSite/>}
                />
                <Route path={"/admin"} element={<ProductsSite userInfo={userInfo}/>}
                />
                <Route path={"/login"} element={
                    <Login loginUser={loginUser}/>}/>
            </Routes>
        </>
    )
}
