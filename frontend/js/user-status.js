function logoutUser() {
  fetch(`${API_BASE}/users/logout`, {
    method: "POST",
    credentials: "include"
  })
    .then(res => {
      if (!res.ok) throw new Error("Logout failed");
      window.location.href = "index.html";
    })
    .catch(err => {
      console.error("Logout error:", err);
      alert("Logout failed");
    });
}

document.addEventListener("DOMContentLoaded", () => {
  const profileIcon = document.getElementById("profileIcon");
  const profileDropdown = document.getElementById("profileDropdown");
  profileIcon.addEventListener("click", (e) => {
    e.stopPropagation(); 
    if (!profileDropdown.classList.contains("hidden")) {
      profileDropdown.classList.add("hidden");
      return;
    }
    fetch(`${API_BASE}/users/status`, {
      credentials: "include"
    })
      .then(res => {
        if (!res.ok) throw new Error("Not logged in");
        return res.json();
      })
      .then(data => {
        profileDropdown.innerHTML = `
          <p><strong>Welcome</strong></p>
          <p>${data.username}</p>
          <button id="logoutBtn">Logout</button>
        `;
        profileDropdown.classList.remove("hidden");

        document.getElementById("logoutBtn").addEventListener("click", logoutUser);
      })
      .catch(() => {
        profileDropdown.innerHTML = `
          <p><strong>Welcome</strong></p>
          <p>To access account and manage orders</p>
          <a href="login.html"><button>LOGIN / SIGNUP</button></a>
        `;
        profileDropdown.classList.remove("hidden");
      });
  });
  document.addEventListener("click", (event) => {
    const isClickInsideIcon = profileIcon.contains(event.target);
    const isClickInsideDropdown = profileDropdown.contains(event.target);

    if (!isClickInsideIcon && !isClickInsideDropdown) {
      profileDropdown.classList.add("hidden");
    }
  });
});
