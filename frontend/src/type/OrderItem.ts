import {Product} from "./Product";

export type OrderItem = {
    productId: string,
    count: number,
    price: number,
}

export type OrderDetailsItem = {
    product: Product,
    count: number,
    price: number,
}
