document.getElementById("signupForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;

  const user = {
    name: form.name.value.trim(),
    email: form.email.value.trim(),
    passwordHash: form.password.value.trim(),
  };

  try {
    const res = await fetch("http://127.0.0.1:8083/api/users/signup", {
      method: "POST",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    });

    if (!res.ok) {
      const msg = await res.text();
      throw new Error(msg);
    }

    alert("✅ Signup successful! Please login.");
    window.location.href = "login.html";
  } catch (err) {
    const msgBox = document.getElementById("signupMessage");

    if (err.message.includes("Email already exists")) {
      msgBox.innerHTML = `
        <div style="padding: 12px; background-color: #fff3cd; border: 1px solid #ffeeba; border-radius: 8px; color: #856404;">
          <strong>Email already registered.</strong><br>
          You can <a href="login.html" style="color: #0056b3; font-weight: bold;">login here</a> to continue.
        </div>
      `;
    } else {
      msgBox.innerHTML = `
        <div style="padding: 12px; background-color: #f8d7da; border: 1px solid #f5c6cb; border-radius: 8px; color: #721c24;">
          ${err.message}
        </div>
      `;
    }
  }
});
