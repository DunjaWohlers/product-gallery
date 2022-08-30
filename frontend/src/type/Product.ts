import {PicObj} from "./PicObj";

export type Product = {
    id: string,
    title: string,
    description: string,
    pictureObj: PicObj[]
    price: number,
    availableCount: number,
}

export type NewProduct = Omit<Product, "id">
