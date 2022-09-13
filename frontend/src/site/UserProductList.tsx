import axios from "axios";
import {UserInfo} from "../type/UserInfo";
import {NewSingleOrder, SingleOrderDetails} from "../type/SingleOrder";
import {toast} from "react-toastify";
import {OrderDetailsItem, OrderItem} from "../type/OrderItem";
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

    useEffect(() => {
        loadOrders();
    }, [])

    const loadOrders = () => {
        axios.get("/api/orders")
            .then(response => {
                const oldorders: SingleOrderDetails[] = response.data;
                const oldOrdersMapped = oldorders.map(order => {
                    if (order.date) {
                        const orderDate = new Date(Date.parse(order.date.toString()));
                        return {
                            id: order.id, date: orderDate, orderItems: order.orderItems
                        }
                    }
                    return order;
                })
                setOldOrders(oldOrdersMapped);
            })
            .catch(() => toast.error("Alte Bestellungen konnten nicht geladen werden."))

        axios.get("/api/users/bookmarks")
            .then(response => {
                setSavedOrders(response.data)
            })
            .catch(() => toast.error("Merkliste konnte nicht geladen werden"));
    }

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

        let day = new Date();

        const saveOrder: NewSingleOrder = {
            date: ordered ? day : undefined,
            orderItems: []
        }
        saveOrder.orderItems = actualOrderList;

        if (ordered) {
            axios.post("/api/orders",
                saveOrder
            ).then(() => toast.info("Erfolgreich bestellt."))
                .then(() => {
                    props.setActualOrderDetailsItems([]);
                    setSavedOrders([]);
                    setBookmarkAxios([]);
                    loadOrders();
                })
                .catch(() => toast.error("Bestellung fehlgeschlagen.")
                );
        } else {
            setBookmarkAxios(actualOrderList);
        }
    }

    const setBookmarkAxios = (array: OrderItem[]) => {
        axios.post("/api/users/bookmarks", array)
            .then(() => toast.info("Erfolgreich gespeichert"))
            .catch(() => toast.error("Speichern fehlgeschlagen."))
    }

    return (
        <div id={"orderComponent"}>
            <div className={"orderCard"}>
                <h3>Merkliste:</h3>
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
                        </p>
                        <p>
                            {orderItem.count}
                        </p>
                        <p>
                            {orderItem.price}
                        </p>
                    </div>
                )}
            </div>
            <button onClick={() => handleSave(false)}> speichern</button>
            <button onClick={() => handleSave(true)}> bestellen</button>

            <div id={"oldOrders"}>
                <h2>Alte Bestellungen:</h2>
                {oldOrders?.map(order =>
                    <div className={"orderCard"} key={order.id}>
                        <h3>Bestellung vom
                            {" " + order.date?.getDate()
                            }.
                            {" " + order.date?.getMonth()
                            }.
                            {" " + order.date?.getFullYear()
                            }
                        </h3>
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
                                </p>
                                <p>
                                    {product.count}
                                </p>
                                <p>
                                    {product.price}
                                </p>
                            </div>)
                        }
                    </div>
                )}
            </div>
        </div>
    )
}
