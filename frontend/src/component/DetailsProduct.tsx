import React, {useEffect, useState} from "react";
import "./editAddDetails.css";
import {Product} from "../type/Product";
import {useParams} from "react-router-dom";
import {toast} from "react-toastify";

type DetailsProductProps = {
    getOneProductPerId: (id: string) => Promise<Product>,
}

export default function DetailsProduct(props: DetailsProductProps) {
    const {id} = useParams();
    const [thisProduct, setDetailProduct] = useState<Product>();
    useEffect(() => {
        if (id) {
            props.getOneProductPerId(id)
                .then(setDetailProduct)
                .catch(() => toast.error("Produkt konnte nicht gefunden werden", {theme: "light"}));
        }
    }, [id, props])

    return (<>
            {thisProduct && <div className={"fullView"}>
                {thisProduct && <h3>{thisProduct.title}</h3>}
                {thisProduct && <p><img src={thisProduct.pictureObj[0].url} alt={"Loading..."}/></p>}
                <p className={"images3flex"}>
                    {thisProduct.pictureObj.length > 0 && <img alt="bild1" src={thisProduct.pictureObj[1].url}/>
                    }
                    {thisProduct.pictureObj.length > 1 && <img alt="bild2" src={thisProduct.pictureObj[2].url}/>
                    }
                    {thisProduct.pictureObj.length > 2 && <img alt="bild3" src={thisProduct.pictureObj[3].url}/>
                    }
                </p>
                {thisProduct && <p>{thisProduct.description}</p>}
                {thisProduct && <p>{thisProduct?.price} &euro;</p>}
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
            </div>}
        </>
    )
}
