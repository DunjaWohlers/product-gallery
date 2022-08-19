import React from 'react';
import './App.css';
import useProducts from "./rest-api/useProducts";
import ProductCard from "./component/ProductCard";
import Header from "./component/Header";
import Footer from "./component/Footer";
import AddProductFormular from "./component/AddProductFormular";

export default function App() {
    const {allProducts, addProduct, deleteProduct, updateProduct} = useProducts();
    const admin = true;

    return (
        <div className="App">
            <Header/>
            <main>
                {!allProducts && <div>Lade Produkt-Liste</div>}
                {allProducts && allProducts.map(product =>
                    <ProductCard product={product}
                                 deleteProduct={deleteProduct}
                                 admin={admin}
                                 updateProduct={updateProduct}
                                 key={product.id}/>
                )}
                <AddProductFormular addProduct={addProduct}/>

            </main>
            <Footer/>
        </div>
    )
}
