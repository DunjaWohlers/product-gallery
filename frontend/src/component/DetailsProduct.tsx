import React from "react";
import {useParams} from "react-router-dom";
import "./editAddDetails.css";
import {Product} from "../type/Product";

type DetailsProductProps = {
    products: Product[] | undefined,
}
export default function DetailsProduct(props: DetailsProductProps) {
    let {id} = useParams();

    const thisProduct: Product | undefined = props.products?.find(element => element.id === id);

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
