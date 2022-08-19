import {useEffect, useState} from "react";
import {NewProduct, Product} from "../type/Product";
import axios from "axios";

export default function useProducts() {

    const [allProducts, setAllProducts] = useState<Product[]>()

    const getAllProducts = () => {
        axios.get("/api/")
            .then(response => response.data)
            .then(setAllProducts)
            .catch(error => console.error(error));
    }
    useEffect(
        getAllProducts, []
    );

    const addProduct = (newProduct: NewProduct) => {
        return axios.post("/api/", newProduct)
            .then(response => response.data)
            .catch(error => console.error(error));
    }

    const deleteProduct = (id: string) => {
        return axios.delete(`/api/${id}`)
            .then(response => response.status)
            .then(getAllProducts)
            .catch(error => console.error(error));
    }

    const updateProduct = (id: string, newUpdateProduct: NewProduct) => {
        return axios.put("/api/product/" + id, newUpdateProduct)
            .catch(error => console.error(error))
    }

    return {allProducts, addProduct, deleteProduct, updateProduct}
}
