import {getAuthHeaders, checkUserRolePage} from "./app_funcs.js";

const apiUrl = "http://localhost:8081/addresses/new";

checkUserRolePage("/addresses", "У Вас нет прав на добавление сервисов");

document.getElementById("add-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const newAddress = {
        address_name: document.getElementById("address_name").value,
        url: document.getElementById("url").value,
        description: document.getElementById("description").value,
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
            window.location.href = "/addresses";
        })
        .catch(error => {
            console.error(error);
            alert("Ошибка при добавлении. Проверьте вводимые данные.");
        });
});
