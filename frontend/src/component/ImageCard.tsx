import React from "react";

type ImageCardProps = {
    url: string,
    isZoomed: boolean,
}

export default function ImageCard(props: ImageCardProps) {
    const className = props.isZoomed ? "imgZoom" : "imgViewComplete";
    return (
        <div className={"cardContainer"}>
            <p className={"imageContainer"}>
                <img className={className} src={props.url}
                     alt={"img"}/>
            </p>
        </div>
    )
}
