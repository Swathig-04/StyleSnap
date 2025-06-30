const API_BASE = "http://127.0.0.1:8083/api";

window.onload = () => {
  const totalCostEl = document.getElementById("totalCost");
  const discountEl = document.getElementById("discount");
  const platformFeeEl = document.getElementById("platformFee");
  const finalPriceEl = document.getElementById("finalPrice");
  const payBtn = document.getElementById("payNowBtn");

  // Disable button until data loads
  payBtn.disabled = true;

  // Load Order Preview
  fetch(`${API_BASE}/order/preview`, {
    credentials: "include"
  })
    .then(res => {
      if (!res.ok) throw new Error("Preview fetch failed");
      return res.json();
    })
    .then(data => {
      console.log("✅ Order Preview Data:", data);
      totalCostEl.innerText = `₹${data.totalCost}`;
      discountEl.innerText = `₹${data.discount}`;
      platformFeeEl.innerText = `₹${data.platformFee}`;
      finalPriceEl.innerText = `₹${data.finalPrice}`;

      // Enable button once data is loaded
      payBtn.disabled = false;
    })
    .catch(err => {
      console.error("❌ Preview error:", err);
      alert("Failed to load order preview. Please try again.");
    });

  // Handle Pay Now
  payBtn.addEventListener("click", () => {
    fetch(`${API_BASE}/order/place`, {
      method: "POST",
      credentials: "include"
    })
      .then(res => {
        if (!res.ok) throw new Error("Order failed");
        return res.text();
      })
      .then(msg => {
        alert("✅ Order placed successfully!");
        window.location.href = "thankyou.html";
      })
      .catch(err => {
        console.error("❌ Payment error:", err);
        alert("Payment failed. Try again.");
      });
  });
};
