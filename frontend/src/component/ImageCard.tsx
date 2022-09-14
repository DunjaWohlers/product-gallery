import React from "react";
import "./imageCard.css";

type ImageCardProps = {
    url: string;
}
export default function ImageCard(props: ImageCardProps) {
    return (
        <div className={"cardContainerX"}>
            <p className={"imageContainerX"}>
                <img src={props.url}
                     alt={"img"}/>
            </p>
        </div>)
}