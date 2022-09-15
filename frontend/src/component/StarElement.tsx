import React from "react";
import "./starElement.css";

type StartElementProps = {
    width: number,
    height: number,
}

export default function StarElement(props: StartElementProps) {

    const viewBox = "0 0 240 240";

    return (
        <svg xmlns="http://www.w3.org/2000/svg" width={props.width} height={props.height} viewBox={viewBox}>
            <path fill="darkorange" d="m48,234 73-226 73,226-192-140h238z"/>
        </svg>
    )
}
