import {Product} from "../type/Product";
import React, {MouseEventHandler, useState} from "react";
import "./guestProduct.css";
import PicturePresentation from "./PicturePresentation";

type ProductFormProps = {
    product: Product | undefined,
}

export default function GuestProduct(props: ProductFormProps) {

    const [viewUrl, setViewUrl] = useState<string>(props.product?.pictureObj ? props.product.pictureObj[0].url : "")

    const mouseEnter: MouseEventHandler<HTMLImageElement> = (e) => {
        const htmlElement = e.target as HTMLElement;
        const url: string | null = htmlElement.getAttribute("src");
        url && setViewUrl(url)
    }

    return (<>
        <div className={"title"}> {props.product?.title}</div>
        <div className={"lineGuest"}>
            <div className={"imageContainer"}>
                <PicturePresentation picUrl={viewUrl}/>
                <div className={"allImages"}>
                    {(props.product?.pictureObj && props.product.pictureObj.length > 0) && <>
                        {props.product.pictureObj.map(picObj => <div key={picObj.url}>
                                <img onMouseEnter={mouseEnter} src={picObj.url} alt={"img"}/>
                            </div>
                        )}
                    </>
                    }
                </div>
            </div>
            <div className={"description"}> {props.product?.description} </div>
        </div>
    </>)
}

