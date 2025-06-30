document.getElementById("loginForm").addEventListener("submit", function(event) {
  event.preventDefault();

  const user = {
    email: document.getElementById("email").value.trim(),
    password: document.getElementById("password").value.trim()
  };

  fetch("http://127.0.0.1:8083/api/users/login", {  
    method: "POST",
    credentials: "include",  
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(user)
  })
    .then(res => {
      if (res.ok) return res.text();
      else throw new Error("Invalid email or password");
    })
    .then(response => {
      alert("Login successful!");

      const returnURL = localStorage.getItem("returnAfterLogin");
      if (returnURL) {
        localStorage.removeItem("returnAfterLogin");
        window.location.href = returnURL;
      } else {
        window.location.href = "index.html"; 
      }
    })
    .catch(err => {
      alert(err.message);
    });
});
