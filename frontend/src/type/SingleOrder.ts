import {OrderDetailsItem, OrderItem} from "./OrderItem";

export type SingleOrderDetails = {
    id: string,
    date: string | undefined,
    orderItems: OrderDetailsItem[]
}

export type NewSingleOrder = {
    date: string | undefined,
    orderItems: OrderItem[]
}
