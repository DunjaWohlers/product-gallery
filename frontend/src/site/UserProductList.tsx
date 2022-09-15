import axios from "axios";
import {UserInfo} from "../type/UserInfo";
import {NewSingleOrder, SingleOrderDetails} from "../type/SingleOrder";
import {toast} from "react-toastify";
import {OrderDetailsItem, OrderItem} from "../type/OrderItem";
import {useEffect, useState} from "react";
import "./userProductList.css";
import OrderCard from "../component/OrderCard";

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
                    setBookmarkAxios(ordered, []);
                    loadOrders();
                })
                .catch(() => toast.error("Bestellung fehlgeschlagen.")
                );
        } else {
            setBookmarkAxios(ordered, actualOrderList);
        }
    }

    const setBookmarkAxios = (ordered: boolean, array: OrderItem[]) => {
        axios.post("/api/users/bookmarks", array)
            .then(() => !ordered && toast.info("Erfolgreich gespeichert"))
            .catch(() => toast.error("Speichern fehlgeschlagen."))
    }

    return (
        <div id={"orderComponent"}>
            <h2>Warenkorb:</h2>
            <OrderCard order={{
                id: undefined, date: undefined, orderItems:
                    props.actualOrderDetailsItems?.concat(savedOrders) ?
                        props.actualOrderDetailsItems?.concat(savedOrders)
                        : []
            }}/>
            <button id="saveProducts" onClick={() => handleSave(false)}> speichern</button>
            <button id={"saveOrder"} onClick={() => handleSave(true)}> bestellen</button>

            <div id={"oldOrders"}>
                <h2>Alte Bestellungen:</h2>
                {oldOrders?.map(order =>
                    <OrderCard order={order}/>
                )}
            </div>
        </div>
    )
}
