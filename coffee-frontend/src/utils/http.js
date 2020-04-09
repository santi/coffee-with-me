function post(path, body) {
  return fetch(path, {
    credentials: "omit",
    headers: { "content-type": "application/json;charset=UTF-8", "sec-fetch-mode": "cors" },
    body: JSON.stringify(body),
    method: "POST",
    mode: "cors"
  })
  .then(response => response.text())
}

function get(path) {
  return fetch(path, {
    credentials: "omit",
    headers: { "content-type": "application/json;charset=UTF-8", "sec-fetch-mode": "cors" },
    method: "GET",
    mode: "cors"
  })
  .then(response => response.json())
}

const http = {
  post,
  get
};

export default http;
