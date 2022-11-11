export default function PicturePresentation(props: { picUrl: string }) {

    return (
        <div className={"picPresentation"}>
            <img src={props.picUrl} alt={"IMG"}/>
        </div>
    )
}
