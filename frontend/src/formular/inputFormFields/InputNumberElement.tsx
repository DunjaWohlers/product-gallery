import React from "react";

type InputFormElementProps = {
    placeholder: string | number,
    value: undefined | number,
    onChangeSetFunction: (inputValue: number) => void,
}
export default function InputNumberElement(props: InputFormElementProps) {
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
                           parseInt(event.target.value)
                       )}
                   className={props.value ? "good" : "bad"}/>
        </>
    )
}
