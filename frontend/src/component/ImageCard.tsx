import React from "react";

type ImageCardProps = {
    url: string;
}
export default function ImageCard(props: ImageCardProps) {
    return (
        <div className={"cardContainerX"}>
            <p className={"imageContainerX"}>
                <img className={"imgViewComplete"} src={props.url}
                     alt={"img"}/>
            </p>
        </div>
    )
}