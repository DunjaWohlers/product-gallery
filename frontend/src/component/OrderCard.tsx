import {SingleOrderDetails} from "../type/SingleOrder";
import "./orderCard.css";

type OrderCardProps = {
    order: SingleOrderDetails
}

export default function OrderCard(props: OrderCardProps) {

    return <>
        <div className={"orderCard"} key={props.order.id ? props.order.id : crypto.randomUUID()}>
            {props.order.id &&
                <h3>Bestellung vom
                    {" " + props.order.date?.getDate()
                    }.
                    {" " + props.order.date?.getMonth()
                    }.
                    {" " + props.order.date?.getFullYear()
                    }
                </h3>
            }
            {props.order.id && <p>id: {props.order.id}</p>
            }
            <div className={"orderHead"}>
                <p>Name</p>
                <p>Bild</p>
                <p>Anzahl</p>
                <p>Preis</p>
            </div>
            {props.order.orderItems.length > 0 ?
                props.order.orderItems.map(product =>
                    <div className={"orderLine"} key={crypto.randomUUID()}>
                        <p>
                            {product.product.title}
                        </p>
                        <p>
                            <img src={product.product.pictureObj[0].url} alt={"bild"}/>
                        </p>
                        <p>
                            {product.count}
                        </p>
                        <p>
                            {product.price}
                        </p>
                    </div>)
                : <p> Keine Produkte vorhanden</p>
            }
        </div>
    </>
}