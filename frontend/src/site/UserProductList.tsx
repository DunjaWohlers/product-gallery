import axios from "axios";
import {UserInfo} from "../type/UserInfo";
import {NewSingleOrder} from "../type/SingleOrder";
import {toast} from "react-toastify";
import {OrderDetailsItem} from "../type/OrderItem";

type UserProductListProps = {
    userInfo: UserInfo | undefined,
    actualOrderDetailsItems: OrderDetailsItem[] | undefined,
}
export default function UserProductList(props: UserProductListProps) {

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
        <>
            {props.actualOrderDetailsItems?.map(orderItem =>
                <p key={orderItem.toString()}>
                    {orderItem.product.title
                    }
                </p>
            )}
            <button onClick={handleSave}> Produkte speichern</button>
        </>
    )
}
