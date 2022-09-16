import React from "react";

export type InputFormElementProps = {
    placeholder: string | number,
    value: string | undefined,
    onChangeSetFunction: (inputValue: string) => void,
}
export default function InputStringElement(props: InputFormElementProps) {
    return (
        <>
            <label> {props.placeholder} </label>
            <input type="text"
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
