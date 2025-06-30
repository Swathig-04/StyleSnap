window.onload = () => {
  checkExistingAddress();
};

function checkExistingAddress() {
  fetch(`${API_BASE}/delivery/get`, {
    credentials: "include"
  })
    .then(res => {
      if (res.status === 404) {
        showAddressForm();
        return;
      }
      if (!res.ok) throw new Error("Failed to check address");

      return res.json();
    })
    .then(data => {
      if (data) {
        window.location.href = "payment.html";
      }
    })
    .catch(err => {
      console.error("Error:", err);
      document.getElementById("addressSection").innerHTML = "<p>Error checking address.</p>";
    });
}
function showAddressForm() {
  document.getElementById("addressSection").innerHTML = `
  <form id="addressForm">
    <input type="text" name="fullName" placeholder="Full Name" required /><br/>
    <input type="text" name="phoneNo" placeholder="Phone Number" required /><br/>
    <textarea name="addressLine" placeholder="Address" required></textarea><br/>
    <input type="text" name="city" placeholder="City" required /><br/>
    <input type="text" name="state" placeholder="State" required /><br/>
    <input type="text" name="pincode" placeholder="Pincode" required /><br/>
    <button type="submit">Save Address & Continue</button>
  </form>
  `;
  document.getElementById("addressForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const addressData = Object.fromEntries(formData.entries());

    fetch(`${API_BASE}/delivery/save`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(addressData),
      credentials: "include"
    })
      .then(res => {
        if (!res.ok) throw new Error("Failed to save address");
        return res.text();
      })
      .then(() => {
        alert("Address saved! Redirecting to payment...");
        window.location.href = "payment.html";
      })
      .catch(err => {
        console.error("Save failed:", err);
        alert("Failed to save address");
      });
  });
}
