import React from "react";

type ImageCardProps = {
    url: string,
    isZoomed: boolean,
}

export default function ImageCard(props: ImageCardProps) {
    const className = props.isZoomed ? "imgZoom" : "imgViewComplete";
    const boxShadow = props.isZoomed ? "boxShadow" : "";

    return (
        <div className={"cardContainer " + boxShadow}>
            <p className={"imageContainer"}>
                <img className={className} src={props.url}
                     alt={"img"}/>
            </p>
        </div>
    )
}
