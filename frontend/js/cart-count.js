function updateCartCount() {
  fetch(`${API_BASE}/cart/count`, {
    credentials: "include"
  })
    .then(res => {
      if (!res.ok) {
        document.getElementById("cartCount").textContent = "0";
        return;
      }
      return res.json();
    })
    .then(count => {
      if (typeof count === "number") {
        document.getElementById("cartCount").textContent = count;
      }
    })
    .catch(err => {
      console.error("Error fetching cart count:", err);
      document.getElementById("cartCount").textContent = "0";
    });
}

window.addEventListener("DOMContentLoaded", updateCartCount);
