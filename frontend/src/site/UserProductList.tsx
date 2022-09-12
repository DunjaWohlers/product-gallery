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
    setActualOrderDetailsItems: (product: OrderDetailsItem[]) => void;
}

export default function UserProductList(props: UserProductListProps) {
    const [oldOrders, setOldOrders] = useState<SingleOrderDetails[]>();
    const [savedOrders, setSavedOrders] = useState<OrderDetailsItem[]>([])
    const [actualOrderId, setActualOrderId] = useState<string>();

    useEffect(() => {
        axios.get("/api/orders")
            .then(response => {
                console.log(response.data);
                let actualOrder: SingleOrderDetails = response.data.find((orde: SingleOrderDetails) => (orde.date === null));
                let oldorders: SingleOrderDetails[] = response.data.filter((orde: SingleOrderDetails) => (orde.date !== null));
                actualOrder && setSavedOrders(actualOrder.orderItems);
                actualOrder && setActualOrderId(actualOrder.id);
                setOldOrders(oldorders);
            })
            .catch(() => toast.error("Alte Bestellungen konnten nicht geladen werden."))
    }, [])

    const handleSave = (ordered: boolean) => {
        let allActualItems: OrderDetailsItem[] = [];
        if (props.actualOrderDetailsItems) {
            allActualItems = allActualItems.concat(props.actualOrderDetailsItems);
        }
        const actualOrderList = allActualItems.concat(savedOrders).map(orderItem => {
            return {
                productId: orderItem.product.id,
                count: orderItem.count,
                price: orderItem.price
            }
        });

        console.log(actualOrderList);

        let day = new Date();

        const saveOrder: NewSingleOrder = {
            date: ordered ? day.toDateString() : undefined,
            orderItems: []
        }
        saveOrder.orderItems = actualOrderList;

        if (!actualOrderId) {
            axios.post("/api/orders",
                saveOrder
            ).then(() => ordered ? toast.info("Erfolgreich bestellt.") : toast.info("Erfolgreich gespeichert"))
                .then(() => {
                    props.setActualOrderDetailsItems([]);
                    setSavedOrders([]);
                })
                .catch(() => toast.error("Speichern der Produkte fehlgeschlagen.")
                );
        } else {
            axios.put("/api/orders/" + actualOrderId, saveOrder)
                .then(() => ordered ? toast.info("Erfolgreich bestellt!") : toast.info("Erfolgreich gespeichert"))
                .catch(() => toast.error("Speichern fehlgeschlagen."))
        }

    }

    return (
        <div>
            <div className={"orderCard"}>
                <h3>Neue Merkliste / Bestellung:</h3>
                <div>
                    <p>Titel</p>
                    <p>Bild</p>
                    <p>Anzahl</p>
                    <p>Preis</p>
                </div>
                {props.actualOrderDetailsItems?.concat(savedOrders).map(orderItem =>
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
                <button onClick={() => handleSave(false)}> Produkte speichern</button>
                <button onClick={() => handleSave(true)}> Produkte bestellen</button>

            </div>

            <h2>Alte Bestellungen:</h2>
            {oldOrders?.map(order =>
                <div className={"orderCard"} key={order.id}>
                    <h3>Merkliste/Bestellung vom {order.date}</h3>
                    <p>id: {order.id}</p>
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
