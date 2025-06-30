const API_BASE = "http://127.0.0.1:8083/api";
let currentProduct = null;

window.onload = () => {
  const params = new URLSearchParams(window.location.search);
  const productId = params.get("id");
  const addBtn = document.getElementById("addToCartBtn");
  addBtn.disabled = true;

  if (!productId) {
    alert("No product ID specified in URL.");
    return;
  }

  fetch(`${API_BASE}/catalog`)
    .then(res => res.json())
    .then(products => {
      const product = products.find(p => p.productID == productId);
      if (!product) {
        alert("Product not found.");
        return;
      }
      currentProduct = product;
      document.getElementById("detailImage").src = `${API_BASE.replace("/api", "")}/images/${product.imageURL}`;
      document.getElementById("detailName").textContent = product.productName;
      document.getElementById("detailDesc").textContent = product.description;
      document.getElementById("detailPrice").textContent = product.price;
      document.getElementById("detailRating").textContent = product.rating || "Not Rated";

      const starContainer = document.getElementById("starRating");
      starContainer.innerHTML = "";
      const fullStars = Math.floor(product.rating || 0);
      for (let i = 0; i < fullStars; i++) {
        starContainer.innerHTML += "⭐";
      }

      const sizeDropdown = document.getElementById("sizeDropdown");
      sizeDropdown.innerHTML = `<option value="">Select Size</option>`;
      const sizes = product.size ? product.size.split(",") : [];
      sizes.forEach(size => {
        const opt = document.createElement("option");
        opt.value = size.trim();
        opt.textContent = size.trim();
        sizeDropdown.appendChild(opt);
      });

      addBtn.disabled = false;
    })
    .catch(err => {
      console.error("Error fetching product:", err);
      alert("Failed to load product details.");
    });
};

function addToCart() {
  const urlParams = new URLSearchParams(window.location.search);
  const productId = parseInt(urlParams.get("id"));
  const size = document.getElementById("sizeDropdown").value;
  const quantity = 1;

  if (!size) {
    alert("Please select a size!");
    return;
  }

  const cartItem = { productId, size, quantity };

  fetch(`${API_BASE}/users/status`, {
    credentials: "include"
  })
    .then(res => {
      if (res.status === 200) {
        return fetch(`${API_BASE}/cart/add`, {
          method: "POST",
          credentials: "include",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(cartItem)
        });
      } else {
        let guestCart = JSON.parse(localStorage.getItem("guestCart")) || [];
        guestCart.push(cartItem);
        localStorage.setItem("guestCart", JSON.stringify(guestCart));

        alert("Please login to place the order.");
        localStorage.setItem("returnAfterLogin", window.location.href);
        window.location.href = "login.html";

        throw new Error("Guest flow");
      }
    })
    .then(res => {
      if (!res.ok) throw new Error("Failed to add to cart");
      return res.text();
    })
    .then(msg => {
      if (typeof updateCartCount === "function") {
        updateCartCount();
      }
      alert("✅ " + msg);
    })
    .catch(err => {
      if (err.message !== "Guest flow") {
        console.error(err);
        alert("Something went wrong while adding to cart.");
      }
    });
}
