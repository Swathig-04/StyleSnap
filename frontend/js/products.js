function updateCartCount() {
  fetch(`${API_BASE}/cart/view`, {
    credentials: "include"
  })
    .then(res => {
      if (!res.ok) throw new Error("Not logged in");
      return res.json();
    })
    .then(cartItems => {
      document.getElementById("cartCount").textContent = cartItems.length;
    })
    .catch(() => {
      const guestCart = JSON.parse(localStorage.getItem("guestCart")) || [];
      document.getElementById("cartCount").textContent = guestCart.length;
    });
}
window.onload = () => {
  updateCartCount();
  const params = new URLSearchParams(window.location.search);
  const category = params.get("category");
  fetch(`${API_BASE}/catalog`)
    .then(res => res.json())
    .then(products => {
      console.log("✅ Products received:", products);
      const container = document.getElementById("productsContainer");
      if (!container) {
        console.error("❌ Container element not found: #productsContainer");
        return;
      }
      container.innerHTML = "";
      const filtered = category
        ? products.filter(p => p.category.toLowerCase() === category.toLowerCase())
        : products;
      if (filtered.length === 0) {
        container.innerHTML = "<p>No products found for this category.</p>";
        return;
      }
      filtered.forEach(product => {
        const card = document.createElement("div");
        card.className = "product-card";
        const image = document.createElement("img");
        image.src = `http://127.0.0.1:8083/images/${product.imageURL}`;
        image.alt = product.productName;
        const name = document.createElement("h3");
        name.textContent = product.productName;
        const price = document.createElement("p");
        price.textContent = `₹${product.price}`;
        const rating = document.createElement("p");
        rating.innerHTML = `Rating: ${product.rating || "Not Rated"} ` +
          "⭐".repeat(Math.floor(product.rating || 0));
        card.addEventListener("click", () => {
          window.location.href = `product-details.html?id=${product.productID}`;
        });
        card.appendChild(image);
        card.appendChild(name);
        card.appendChild(price);
        card.appendChild(rating);

        container.appendChild(card);
      });
    })
    .catch(err => {
      console.error("Error loading products:", err);
      const container = document.getElementById("productsContainer");
      if (container) {
        container.innerHTML = "<p>Failed to load products.</p>";
      }
    });
};
