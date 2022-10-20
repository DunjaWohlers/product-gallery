import {PicObj} from "./PicObj";

export type Product = {
    id: string,
    title: string,
    description: string,
    pictureObj: PicObj[]
}

export type NewProduct = Omit<Product, "id">
