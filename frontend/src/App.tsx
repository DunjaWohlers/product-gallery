import React from 'react';
import './App.css';
import useProducts from "./rest-api/useProducts";
import ProductCard from "./component/ProductCard";
import Header from "./component/Header";
import Footer from "./component/Footer";

export default function App() {
    const {allProducts} = useProducts();

    return (
        <div className="App">
            <Header/>
            <main>
                {!allProducts && <div>Lade Produkt-Liste</div>}
                {allProducts && allProducts.map(product =>
                    <ProductCard product={product}
                                 key={product.id}/>
                )}
            </main>
            <Footer/>
        </div>
    )
}