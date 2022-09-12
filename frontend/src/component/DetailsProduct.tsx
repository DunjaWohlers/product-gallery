import React, {useEffect, useState} from "react";
import "../formular/editAddDetails.css";
import {Product} from "../type/Product";
import {useParams} from "react-router-dom";
import {toast} from "react-toastify";
import {OrderDetailsItem} from "../type/OrderItem";

type DetailsProductProps = {
    getOneProductPerId: (id: string) => Promise<Product>,
    setActualOrderDetailsItems: (orderDetailsItems: OrderDetailsItem[]) => void,
    actualOrderDetailsItems: OrderDetailsItem[] | undefined,
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

    const handleSave = () => {
        if (thisProduct && props.actualOrderDetailsItems) {
            const orderItem: OrderDetailsItem = {product: thisProduct, count: 1, price: thisProduct.price}
            props.setActualOrderDetailsItems(
                props.actualOrderDetailsItems.concat(orderItem));
        } else {
            toast.error("Produkt wurde nicht gefunden.")
        }
    }

    return (<>
            {thisProduct &&
                <div className={"details"}>
                    <h3>{thisProduct.title}</h3>
                    <button id="saveFavouriteButton" onClick={handleSave}> *</button>
                    <div className={"cardContainer"}>
                        <div className={"imageContainer"}>
                            <img src={thisProduct.pictureObj[0].url} alt={"Loading..."}/>
                        </div>
                    </div>
                    {thisProduct.pictureObj &&
                        <div className={"images3flex"}>

                            {thisProduct.pictureObj.length > 1 &&
                                <div className={"cardContainer"}>
                                    <div className={"imageContainer"}>
                                        <img alt="bild1" src={thisProduct.pictureObj[1].url}/>
                                    </div>
                                </div>
                            }
                            {thisProduct.pictureObj.length > 2 &&
                                <div className={"cardContainer"}>
                                    <div className={"imageContainer"}>
                                        <img alt="bild2" src={thisProduct.pictureObj[2].url}/>
                                    </div>
                                </div>
                            }
                            {thisProduct.pictureObj.length > 3 &&
                                <div className={"cardContainer"}>
                                    <div className={"imageContainer"}>
                                        <img alt="bild3" src={thisProduct.pictureObj[3].url}/>
                                    </div>
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
