import {OrderDetailsItem, OrderItem} from "./OrderItem";

export type SingleOrderDetails = {
    id: string,
    date?: string,
    orderItems: OrderDetailsItem[]
}

export type NewSingleOrder = {
    date?: string,
    orderItems: OrderItem[]
}
