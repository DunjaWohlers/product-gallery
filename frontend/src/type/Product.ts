import {ImageInfo} from "./ImageInfo";

export type Product = {
    id: number,
    title: string,
    description: string,
    pictureObj?: ImageInfo[]
}

export type NewProduct = Omit<Product, "id">
