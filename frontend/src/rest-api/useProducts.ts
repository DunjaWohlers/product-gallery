import {useEffect, useState} from "react";
import {Product} from "../type/Product";
import axios from "axios";

export default function useProducts() {

    const [allProducts, setAllProducts] = useState<Product[]>();

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

    return {
        allProducts,
        getOneProductPerId,
    }
}
