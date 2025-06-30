const API_BASE = "http://127.0.0.1:8083/api";

fetch(`${API_BASE}/order/latest`, {
  credentials: "include"
})
  .then(res => {
    if (!res.ok) {
      throw new Error("Failed to fetch");
    }
    return res.json();
  })
  .then(order => {
    document.getElementById("order-info").innerHTML = `
  <h3>Order Summary</h3>
  <p><strong>Order ID:</strong> #${order.orderId}</p>
  <p><strong>Total Paid:</strong> ₹${order.totalCost}</p>
  <p><strong>Payment Mode:</strong> ${order.paymentMode}</p>
`;

  })
  .catch(err => {
    document.getElementById("order-info").innerHTML = "<p>Unable to load order details.</p>";
    console.error("Error loading order:", err);
  });
