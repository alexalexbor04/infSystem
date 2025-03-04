import { getAuthHeaders } from "./app_funcs.js";

const apiUrl = "http://localhost:8081/api/data";
const apiUrlPut = "http://localhost:8081/data";
const servicesUrl = "http://localhost:8081/addresses";
const urlParams = new URLSearchParams(window.location.search);
const recordId = urlParams.get("id");

if (!recordId) {
    alert("Ошибка: отсутствует ID записи.");
    window.location.href = "/data";
}

fetch(servicesUrl, { headers: getAuthHeaders() })
    .then(response => response.json())
    .then(services => {
        const serviceSelect = document.getElementById("address_name");
        services.forEach(service => {
            const option = document.createElement("option");
            option.value = service.id;
            option.textContent = `${service.id} - ${service.address_name} - ${service.url}`;
            serviceSelect.appendChild(option);
        });
    })
    .catch(error => console.error("Ошибка загрузки сервисов:", error));

fetch(`${apiUrl}/${recordId}`, {
    method: "GET",
    headers: getAuthHeaders()})
    .then(response => response.json())
    .then(data => {
        document.getElementById("login").value = data.user_login;
        document.getElementById("password").value = data.user_password;
        document.getElementById("address_name").value = data.address_id;
        document.getElementById("user_id").value = data.userId.username;
    })
    .catch(error => {
        console.error("Ошибка загрузки данных:", error);
        alert("Ошибка при загрузке данных.");
        window.location.href = "/data";
    });

document.getElementById("edit-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const updatedRecord = {
        user_login: document.getElementById("login").value,
        user_password: document.getElementById("password").value,
        addressId: { id: parseInt(document.getElementById("address_name").value)},
        userId: { id: parseInt(document.getElementById("user_id").value)}
    };

    fetch(`${apiUrlPut}/edit/${recordId}`, {
        method: "PUT",
        headers: getAuthHeaders(),
        body: JSON.stringify(updatedRecord),
    })
        .then(response => {
            if (!response.ok) throw new Error("Ошибка при обновлении.");
            alert("Данные обновлены!");
            window.location.href = "/data";
        })
        .catch(error => {
            console.error(error);
            alert("Ошибка при обновлении.");
        });
});
