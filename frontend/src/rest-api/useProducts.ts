import {useEffect, useState} from "react";
import {NewProduct} from "../type/Product";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {ProductReducedInfo} from "../type/ProductReducedInfo";

export default function useProducts() {

    const [allProducts, setAllProducts] = useState<ProductReducedInfo[]>();

    const navigate = useNavigate();

    const getAllProducts = () => {
        axios.get("/api/products/")
            .then(response => response.data)
            .then(setAllProducts)
            .catch(error => console.error(error));
    }
    useEffect(
        getAllProducts, []
    );

    const getOneProductPerId = (id: string) => {
        return axios.get("/api/products/details/" + id)
            .then(response => response.data)
    }

    const addProduct = (newProduct: NewProduct) => {
        return axios.post("/api/products", newProduct)
            .then(response => response.data)
            .catch(error => console.error(error))
            .then(getAllProducts)
            .then(() => navigate("/"))
    }

    const deleteProduct = (id: string) => {
        return axios.delete(`/api/products/${id}`)
            .then(response => response.status)
            .then(() => getAllProducts());
    }

    const updateProduct = (id: string, newUpdateProduct: NewProduct) => {
        return axios.put("/api/products/" + id, newUpdateProduct)
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
