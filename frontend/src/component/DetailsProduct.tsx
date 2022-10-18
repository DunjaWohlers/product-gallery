import React, {useEffect, useState} from "react";
import {Product} from "../type/Product";
import {useParams} from "react-router-dom";
import {toast} from "react-toastify";
import "./detailsProduct.css";
import ImageCard from "./ImageCard";

type DetailsProductProps = {
    getOneProductPerId: (id: string) => Promise<Product>,
}

export default function DetailsProduct(props: DetailsProductProps) {
    const {id} = useParams();
    const [thisProduct, setDetailProduct] = useState<Product>();
    useEffect(() => {
        if (id) {
            props.getOneProductPerId(id)
                .then(data => setDetailProduct(data))
                .catch(() => toast.error("Produkt konnte nicht gefunden werden"));
        }
    }, [id, props])

    return (<>
            {thisProduct &&
                <div className={"details"}>
                    <h3>{thisProduct.title}
                    </h3>
                    <ImageCard url={thisProduct.pictureObj[0].url} isZoomed={true}/>
                    {thisProduct.pictureObj &&
                        <div className={"images3flex"}>
                            {thisProduct.pictureObj.length > 1 &&
                                <div className={"productCard"}>
                                    <ImageCard url={thisProduct.pictureObj[1].url} isZoomed={false}/>
                                </div>
                            }
                            {thisProduct.pictureObj.length > 2 &&
                                <div className={"productCard"}>
                                    <ImageCard url={thisProduct.pictureObj[2].url} isZoomed={false}/>
                                </div>
                            }
                            {thisProduct.pictureObj.length > 3 &&
                                <div className={"productCard"}>
                                    <ImageCard url={thisProduct.pictureObj[3].url} isZoomed={false}/>
                                </div>
                            }
                        </div>
                    }
                    <p>{thisProduct.description}</p>
                    <p>{thisProduct?.price} &euro;</p>
                    {thisProduct.availableCount > 0 &&
                        <><p> Dieses Produkt ist vorrätig.</p>
                            <p> Anzahl vorrätiger Produkte: {thisProduct.availableCount} </p></>
                    }
                    {thisProduct.availableCount === 0 && <p> Dieses Produkt kann hergestellt werden,
                        genauere Details zur Dauer hängen von vielen Faktoren ab und müssen
                        erfragt werden. </p>
                    }
                    {thisProduct.availableCount < 0 &&
                        <p> Dieses Produkt wurde aus dem Angebot entfernt. Schreiben Sie gerne eine
                            Nachricht, wenn Sie es dennoch wünschen, bei genügend Anfragen kann es
                            eventuell wieder angeboten werden. </p>
                    }
                </div>}
        </>
    )
}
