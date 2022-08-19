export type Product = {
    id: string,
    title: string,
    description: string,
    pictureUrls: string[]
    price: number,
    availableCount: number,
}

export type NewProduct = Omit<Product, "id">
