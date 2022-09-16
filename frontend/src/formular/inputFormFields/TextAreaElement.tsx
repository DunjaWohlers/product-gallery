import {InputFormElementProps} from "./InputStringElement";
import React from "react";

export default function TextAreaElement(props: InputFormElementProps) {
    return (
        <>
            <label> {props.placeholder} </label>
            <textarea
                autoComplete={"off"}
                placeholder={props.placeholder.toString()}
                defaultValue={props.value}
                name={"title"}
                onChange={(event) =>
                    props.onChangeSetFunction(
                        event.target.value
                    )}
                className={props.value ? "good" : "bad"}/>
        </>
    )
}