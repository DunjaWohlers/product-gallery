import {useEffect, useState} from "react";
import {Product} from "../type/Product";
import axios from "axios";

export default function useProducts() {

    const [allProducts, setAllProducts] = useState<Product[]>()

    useEffect(
        () => {
            axios.get("product-gallery/")
                .then(response => response.data)
                .then(setAllProducts)
                .catch(error => console.error(error));
        }, []
    );

    return {allProducts}
}