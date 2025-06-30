/*document.getElementById("productForm").addEventListener("submit", function (e) {
    e.preventDefault();
  
    const productId = document.getElementById("productID").value.trim();
    const product = {
      productName: document.getElementById("productName").value,
      price: parseFloat(document.getElementById("price").value),
      rating: parseFloat(document.getElementById("rating").value) || 0,
      size: document.getElementById("size").value,
      material: document.getElementById("material").value,
      description: document.getElementById("description").value,
      imageURL: document.getElementById("imageURL").value
    };
  
    const method = productId ? "PUT" : "POST";
    const url = productId
      ? `http://localhost:8083/api/catalog/${productId}`
      : "http://localhost:8083/api/catalog";
  
    fetch(url, {
      method,
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(product)
    })
      .then(res => {
        if (res.ok) {
          alert(productId ? "✅ Product updated!" : "✅ Product added!");
          document.getElementById("productForm").reset();
        } else {
          alert("❌ Failed to save product");
        }
      })
      .catch(err => {
        console.error("Error:", err);
        alert("❌ Server error");
      });
  });*/
  