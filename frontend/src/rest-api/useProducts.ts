import {useEffect, useState} from "react";
import {NewProduct, Product} from "../type/Product";
import axios from "axios";
import {toast} from "react-toastify";

export default function useProducts() {

    const [allProducts, setAllProducts] = useState<Product[]>();

    const getAllProducts = () => {
        console.log("get ausgeführt")
        axios.get("/api/products/")
            .then(response => response.data)
            .then(setAllProducts)
            .catch(error => console.error(error));
    }
    useEffect(
        getAllProducts, []
    );

    const addProduct = (newProduct: NewProduct) => {
        return axios.post("/api/products", newProduct)
            .then(response => response.data)
            .then(() => toast.success("Produkt wurde gespeichert!"))
            .catch(() => toast.error("Produkt konnte nicht gespeichert werden!"))
            .then(getAllProducts)
    }

    const deleteProduct = (id: number) => {
        return axios.delete(`/api/products/${id}`)
            .then(response => response.status)
            .then(() => toast.success("Produkt wurde gelöscht", {theme: "light"}))
            .catch(() => toast.error("Löschen fehlgeschlagen!", {theme: "light"}))
            .then(getAllProducts);
    }

    const updateProduct = (id: number, newUpdateProduct: NewProduct) => {
        return axios.put("/api/products/" + id, newUpdateProduct)
            .then(() => toast.success("Produkt wurde erfolgreich editiert!"))
            .catch(() => toast.error("Update fehlgeschlagen"))
            .then(getAllProducts)
    }


    const uploadImage = (formData: FormData, id: number) => {
        return axios.post("/api/image/uploadFile/" + id, formData,
        ).then(data => data.data)
            .then(getAllProducts)
            .then(() => toast.info("Bild wurde gespeichert"))
            .catch(() => toast.warn("Bild konnte nicht gespeichert werden."));
    }

    const deleteImage = (imageId: number) => {
        return axios.delete("/api/image/delete/" + imageId)
            .then(getAllProducts)
            .catch(() => toast.error("Bild löschen fehlgeschlagen."))
    }

    return {
        allProducts,
        addProduct,
        deleteProduct,
        updateProduct,
        uploadImage,
        deleteImage
    }
}

