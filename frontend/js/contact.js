document.getElementById("contactForm").addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  const data = {
    name: form.name.value,
    email: form.email.value,
    message: form.message.value,
  };

  fetch("http://127.0.0.1:8083/api/contact", {
    method: "POST",
    credentials: "include", 
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  })
    .then((res) => {
      if (res.ok) alert("Thank you for contacting us!");
      else throw new Error("Error submitting form");
    })
    .catch((err) => alert(err.message));
});
