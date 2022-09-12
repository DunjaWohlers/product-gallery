import {OrderDetailsItem, OrderItem} from "./OrderItem";

export type SingleOrder = {
    id: string,
    orderItems: OrderItem[]
}

export type SingleOrderDetails = {
    id: string,
    orderItems: OrderDetailsItem[]
}

export type NewSingleOrder = {
    orderItems: OrderItem[]
}

export type NewSingleOrderDetails = {
    orderItems: OrderDetailsItem[]
}
