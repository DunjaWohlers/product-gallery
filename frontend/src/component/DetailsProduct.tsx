import React, {useEffect, useState} from "react";
import "./editAddDetails.css";
import {Product} from "../type/Product";
import {useParams} from "react-router-dom";
import axios from "axios";

type DetailsProductProps = {
    products: Product[] | undefined,
    getOneProductPerId: (id: string) => void,
}

export default function DetailsProduct(props: DetailsProductProps) {
    const {id} = useParams();
    const [thisProduct, setDetailProduct] = useState<Product>();
    useEffect(() => {
        if (id) {
            axios.get("/api/details/" + id)
                .then(response => response.data)
                .then(setDetailProduct)
                .catch(error => console.error(error));
        }

    }, [])
    return (
        <div className={"fullView"}>
            <h3>{thisProduct?.title}</h3>
            <p><img src={thisProduct?.pictureUrls[0]} alt={"Loading..."}/></p>
            <p>{thisProduct?.description}</p>
            <p>{thisProduct?.price} &euro;</p>
            {thisProduct && thisProduct.availableCount > 0 &&
                <><p> Dieses Produkt ist vorrätig.</p>
                    <p> Anzahl vorrätiger Produkte: {thisProduct.availableCount} </p></>
            }
            {thisProduct && thisProduct.availableCount === 0 && <p> Dieses Produkt kann hergestellt werden,
                genauere Details zur Dauer hängen von vielen Faktoren ab und müssen
                erfragt werden. </p>
            }
            {thisProduct && thisProduct.availableCount < 0 &&
                <p> Dieses Produkt wurde aus dem Angebot entfernt. Schreiben Sie gerne eine
                    Nachricht, wenn Sie es dennoch wünschen, bei genügend Anfragen kann es
                    eventuell wieder angeboten werden. </p>
            }
        </div>
    )
}
