const API_BASE = "http://127.0.0.1:8083/api";
window.onload = () => {
  fetch(`${API_BASE}/users/status`, {
    credentials: "include",
  })
    .then(res => {
      if (!res.ok) {
        alert("Please log in to view your cart.");
        window.location.href = "login.html";
        return;
      }
      loadCartItems();
    })
    .catch(err => {
      console.error("Login check failed:", err);
      window.location.href = "login.html";
    });
};

function checkLoginStatusAndLoadCart() {
  fetch(`${API_BASE}/users/status`, {
    credentials: "include"
  })
    .then(res => {
      if (!res.ok) {
        alert("Please log in to view your cart.");
        window.location.href = "/login.html";
        return;
      }
      loadCartItems();
    })
    .catch(err => {
      console.error("Login status check failed:", err);
      window.location.href = "login.html";
    });
}

function loadCartItems() {
  fetch(`${API_BASE}/cart/view`, {
    credentials: "include"
  })
    .then(res => {
      if (!res.ok) throw new Error("Failed to load cart");
      return res.json();
    })
    .then(cartItems => {
      if (!Array.isArray(cartItems) || cartItems.length === 0) {
        displayEmptyCart();
        return;
      }
      loadProductDetails(cartItems);
    })
    .catch(err => {
      console.error("Cart loading failed:", err);
      displayEmptyCart();
    });
}

function displayEmptyCart() {
  const container = document.getElementById("cart-list");
  container.innerHTML = "<p>Your cart is empty.</p>";
  updateSummary(0, 0);
  updateCartCount(); 
}

function loadProductDetails(cartItems) {
  fetch(`${API_BASE}/catalog`)
    .then(res => res.json())
    .then(products => {
      const container = document.getElementById("cart-list");
      container.innerHTML = "";
      let totalMRP = 0;

      cartItems.forEach(item => {
        const product = products.find(p => p.productID === item.productId);
        if (!product) return;

        const price = product.price;
        const quantity = item.quantity;
        const itemTotal = price * quantity;
        totalMRP += itemTotal;

        const div = document.createElement("div");
        div.className = "cart-item";
        div.innerHTML = `
          <img src="http://127.0.0.1:8083/images/${product.imageURL}" alt="${product.productName}" />
          <div>
            <h3>${product.productName}</h3>
            <p>Price: ₹${price}</p>
            <div class="quantity-controls">
              <button onclick="updateQuantity(${item.cartId}, ${quantity - 1})">−</button>
              <span>${quantity}</span>
              <button onclick="updateQuantity(${item.cartId}, ${quantity + 1})">+</button>
            </div>
            <button onclick="removeItem(${item.cartId})">🗑 Remove</button>
            <p>Size: ${item.size || "Not selected"}</p>
          </div>
        `;
        container.appendChild(div);
      });

      updateSummary(cartItems.length, totalMRP);
    })
    .catch(err => {
      console.error("Failed to load product details:", err);
    });
}

function updateQuantity(cartId, newQuantity) {
  if (newQuantity < 1) return;

  fetch(`${API_BASE}/cart/update/${cartId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ quantity: newQuantity }),
    credentials: "include"
  })
    .then(res => {
      if (!res.ok) throw new Error("Failed to update quantity");
      return res.text();
    })
    .then(() => {
      loadCartItems();         
      updateCartCount();      
    })
    .catch(err => {
      console.error(err);
      alert("Could not update quantity.");
    });
}
function removeItem(cartId) {
  fetch(`${API_BASE}/cart/delete/${cartId}`, {
    method: "DELETE",
    credentials: "include"
  })
    .then(res => {
      if (!res.ok) throw new Error("Failed to delete item");
      loadCartItems();       
      updateCartCount();     
    })
    .catch(err => {
      console.error(err);
      alert("Could not remove item.");
    });
}

function updateSummary(count, totalMRP) {
  document.getElementById("itemCount").textContent = count;
  document.getElementById("totalMRP").textContent = totalMRP;
  document.getElementById("discount").textContent = 0;
  document.getElementById("finalAmount").textContent = totalMRP + 20;
}

document.getElementById("placeOrderBtn").addEventListener("click", () => {
  fetch(`${API_BASE}/users/status`, {
    credentials: "include"
  })
    .then(res => {
      if (res.ok) {
        window.location.href = "placeorder.html";
      } else {
        alert("Please log in to place your order.");
        window.location.href = "login.html";
      }
    });
});

document.getElementById("logoutBtn")?.addEventListener("click", () => {
  fetch(`${API_BASE}/users/logout`, {
    method: "POST",
    credentials: "include"
  })
    .then(res => {
      if (res.ok) {
        alert("Logged out!");
        window.location.href = "login.html";
      } else {
        alert("Logout failed.");
      }
    })
    .catch(err => {
      console.error(err);
      alert("Error logging out.");
    });
});

