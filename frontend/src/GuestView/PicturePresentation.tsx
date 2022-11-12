export default function PicturePresentation(props: { picUrl: string }) {

    return (
        <div className={"picPresentation"}>
            {props.picUrl && <img src={props.picUrl} alt={"IMG"}/>
            }
        </div>
    )
}
