import {OrderDetailsItem, OrderItem} from "./OrderItem";

export type SingleOrderDetails = {
    id: string,
    date?: Date,
    orderItems: OrderDetailsItem[]
}

export type NewSingleOrder = {
    date?: Date,
    orderItems: OrderItem[]
}
