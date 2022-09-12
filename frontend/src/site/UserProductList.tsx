import axios from "axios";
import {UserInfo} from "../type/UserInfo";
import {NewSingleOrder, SingleOrderDetails} from "../type/SingleOrder";
import {toast} from "react-toastify";
import {OrderDetailsItem} from "../type/OrderItem";
import {useEffect, useState} from "react";
import "./userProductList.css";

type UserProductListProps = {
    userInfo: UserInfo | undefined,
    actualOrderDetailsItems: OrderDetailsItem[] | undefined,
}

export default function UserProductList(props: UserProductListProps) {
    const [oldOrders, setOldOrders] = useState<SingleOrderDetails[]>()
    useEffect(() => {
        axios.get("/api/orders")
            .then(response => setOldOrders(response.data))
            .catch(() => toast.error("Alte Bestellungen konnten nicht geladen werden."))
    }, [])

    const handleSave = () => {
        if (props.actualOrderDetailsItems) {
            const actualOrderList = props.actualOrderDetailsItems.map(orderItem => {
                return {
                    productId: orderItem.product.id,
                    count: orderItem.count,
                    price: orderItem.price
                }
            });
            console.log(actualOrderList);

            const saveOrder: NewSingleOrder = {
                orderItems: []
            }
            saveOrder.orderItems = actualOrderList;

            axios.post("/api/orders",
                saveOrder
            ).catch(() => toast.error("Hinzufügen des Produkts zur Bestellung fehlgeschlagen.")
            );
        }
    }

    return (
        <div>
            <div className={"orderCard"}>
                <h3>Neue Merkliste/Bestellung:</h3>
                <div>
                    <p>Titel</p>
                    <p>Bild</p>
                    <p>Anzahl</p>
                    <p>Preis</p>
                </div>
                {props.actualOrderDetailsItems?.map(orderItem =>
                    <div key={crypto.randomUUID()}>
                        <p>
                            {orderItem.product.title}
                        </p>
                        <p>
                            <img src={orderItem.product.pictureObj[0].url} alt={"bild"}/>
                        </p><p>
                        {orderItem.count}
                    </p>
                        <p>
                            {orderItem.price}
                        </p>
                    </div>
                )}
                <button onClick={handleSave}> Produkte speichern</button>
            </div>

            <h2>Alte Bestellungen / Merklisten:</h2>
            {oldOrders?.map(order =>
                <div className={"orderCard"} key={order.id}>
                    <h3>Merkliste/Bestellung Nr.</h3>
                    <p> {order.id}</p>
                    <div>
                        <p>Titel</p>
                        <p>Bild</p>
                        <p>Anzahl</p>
                        <p>Preis</p>
                    </div>
                    {order.orderItems.map(product =>
                        <div key={crypto.randomUUID()}>
                            <p>
                                {product.product.title}
                            </p>
                            <p>
                                <img src={product.product.pictureObj[0].url} alt={"bild"}/>
                            </p><p>
                            {product.count}
                        </p>
                            <p>
                                {product.price}
                            </p>
                        </div>)}
                </div>
            )}
        </div>
    )
}
