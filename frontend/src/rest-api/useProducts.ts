import {useEffect, useState} from "react";
import {NewProduct} from "../type/Product";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {ProductReducedInfo} from "../type/ProductReducedInfo";

export default function useProducts() {

    const [allProducts, setAllProducts] = useState<ProductReducedInfo[]>();
    const [me, setMe] = useState<string>("anonymousUser");

    const navigate = useNavigate();

    const getAllProducts = () => {
        axios.get("/api/")
            .then(response => response.data)
            .then(setAllProducts)
            .catch(error => console.error(error));
    }
    useEffect(
        getAllProducts, []
    );

    const getOneProductPerId = (id: string) => {
        return axios.get("/api/details/" + id)
            .then(response => response.data)
    }

    const addProduct = (newProduct: NewProduct) => {
        return axios.post("/api/", newProduct)
            .then(response => response.data)
            .catch(error => console.error(error))
            .then(getAllProducts)
            .then(() => navigate("/"))
    }

    const deleteProduct = (id: string) => {
        return axios.delete(`/api/${id}`)
            .then(response => response.status)
            .then(() => getAllProducts());
    }

    const updateProduct = (id: string, newUpdateProduct: NewProduct) => {
        return axios.put("/api/product/" + id, newUpdateProduct)
            .then(getAllProducts)
    }

    return {
        allProducts,
        addProduct,
        deleteProduct,
        updateProduct,
        getOneProductPerId,
    }
}
