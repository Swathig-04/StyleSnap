/*window.onload = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get("id");
    fetch(`http://localhost:8083/api/catalog/${productId}`)
      .then(res => res.json())
      .then(product => {
        document.getElementById("productId").value = product.productID;
        document.getElementById("productName").value = product.productName;
        document.getElementById("price").value = product.price;
        document.getElementById("rating").value = product.rating || 0;
        document.getElementById("size").value = product.size;
        document.getElementById("material").value = product.material;
        document.getElementById("description").value = product.description;
        document.getElementById("imageURL").value = product.imageURL;
      });
  };
  document.getElementById("editForm").addEventListener("submit", function (e) {
    e.preventDefault();
  
    const id = document.getElementById("productId").value;
    const updatedProduct = {
      productName: document.getElementById("productName").value,
      price: parseFloat(document.getElementById("price").value),
      rating: parseFloat(document.getElementById("rating").value) || 0,
      size: document.getElementById("size").value,
      material: document.getElementById("material").value,
      description: document.getElementById("description").value,
      imageURL: document.getElementById("imageURL").value
    };
    fetch(`http://localhost:8083/api/catalog/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(updatedProduct)
    })
      .then(res => {
        if (res.ok) {
          alert("✅ Product updated successfully!");
          window.location.href = "products.html";
        } else {
          alert("❌ Failed to update product.");
        }
      })
      .catch(err => {
        console.error("Error:", err);
        alert("❌ Server error.");
      });
  });
}*/
  