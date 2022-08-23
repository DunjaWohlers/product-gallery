import {useEffect, useState} from "react";
import {NewProduct, Product} from "../type/Product";
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function useProducts() {

    const [allProducts, setAllProducts] = useState<Product[]>();
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
            .catch(error => console.error(error))
            .then(() => getAllProducts());
    }

    const updateProduct = (id: string, newUpdateProduct: NewProduct) => {
        return axios.put("/api/product/" + id, newUpdateProduct)
            .catch(error => console.error(error))
            .then(getAllProducts)
            .then(() => navigate("/"))
    }

    return {
        allProducts, addProduct, deleteProduct, updateProduct, getOneProductPerId

    }
}
