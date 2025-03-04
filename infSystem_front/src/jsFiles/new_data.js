import {getAuthHeaders} from "./app_funcs.js";

const apiUrl = "http://localhost:8081/data/new";
const servicesUrl = "http://localhost:8081/addresses";
const userUrl = "http://localhost:8081/api/cur-user"; // Исправленный URL

fetch(servicesUrl, { headers: getAuthHeaders() })
    .then(response => response.json())
    .then(services => {
        const serviceSelect = document.getElementById("address_id");
        services.forEach(service => {
            const option = document.createElement("option");
            option.value = service.id;
            option.textContent = `${service.id} - ${service.address_name} - ${service.url}`;
            serviceSelect.appendChild(option);
        });
    })
    .catch(error => console.error("Ошибка загрузки сервисов:", error));

fetch(userUrl, { headers: getAuthHeaders() })
    .then(response => response.text())
    .then(userId => {
        document.getElementById("user_id").value = userId;
    })
    .catch(error => console.error("Ошибка загрузки данных пользователя:", error));

document.getElementById("add-form").addEventListener("submit", function(event) {
    event.preventDefault();

    console.log(document.getElementById("address_id"))

    const newAddress = {
        user_login: document.getElementById("login").value,
        user_password: document.getElementById("password").value,
        addressId: { id: parseInt(document.getElementById("address_id").value) },
        userId: { id: parseInt(document.getElementById("user_id").value) },
    };

    fetch(apiUrl, {
        method: "POST",
        headers: getAuthHeaders(),
        body: JSON.stringify(newAddress),
    })
        .then(response => {
            if (!response.ok) throw new Error("Ошибка при добавлении.");
            return response.json();
        })
        .then(() => {
            alert("Адрес добавлен!");
            window.location.href = "/data";
        })
        .catch(error => {
            console.error(error);
            alert("Ошибка при добавлении. Проверьте вводимые данные.");
        });
});
