import React from 'react';
import './App.css';
import useProducts from "./rest-api/useProducts";
import ProductCard from "./component/ProductCard";

function App() {
    const {allProducts} = useProducts();

    return (
        <div className="App">
            {!allProducts && <div>Lade Produkt-Liste</div>}
            {allProducts && allProducts.map(product =>
                <ProductCard product={product}
                             key={product.id}/>
            )}
        </div>
  );
}

export default App;
