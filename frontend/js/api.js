const BASE_URL = "http://127.0.0.1:8083/api"; 
async function signupUser(data) {
  const res = await fetch(`${BASE_URL}/users/signup`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
    credentials: "include"
  });
  if (!res.ok) {
    const errText = await res.text();
    throw new Error(errText || "Signup failed");
  }

  return res.text();
}
async function loginUser(data) {
  const res = await fetch(`${BASE_URL}/users/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
    credentials: "include"
  });
  if (!res.ok) {
    const errText = await res.text();
    throw new Error(errText || "Login failed");
  }
  return res.text();
}
async function fetchProducts() {
  const res = await fetch(`${BASE_URL}/catalog`);
  if (!res.ok) {
    throw new Error("Failed to fetch products");
  }
  return res.json();
}
async function sendContactMessage(data) {
  const res = await fetch(`${BASE_URL}/contact`, { 
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
    credentials: "include"
  });
  if (!res.ok) {
    throw new Error("Failed to send message");
  }
  return res.text();
}
