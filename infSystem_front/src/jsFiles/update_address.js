import {checkUserRolePage, getAuthHeaders} from "./app_funcs.js";

const apiUrlInfo = "http://localhost:8081/api/addresses";
const apiUrlPut = "http://localhost:8081/addresses";
const urlParams = new URLSearchParams(window.location.search);
const serviceId = urlParams.get("id");

if (!serviceId) {
    alert("Ошибка: отсутствует ID сервиса.");
    window.location.href = "/addresses";
}

fetch(`${apiUrlInfo}/${serviceId}`, {
    method: "GET",
    headers: getAuthHeaders()
})
    .then(response => {
        if (!response.ok) throw new Error("Ошибка загрузки данных.");
        return response.json();
    })
    .then(service => {
        document.getElementById("address_name").value = service.address_name;
        document.getElementById("url").value = service.url;
        document.getElementById("description").value = service.description;
    })
    .catch(error => {
        console.error(error);
        alert("Не удалось загрузить данные.");
        window.location.href = "/addresses";
    });

checkUserRolePage("/addresses", "У вас нет прав на редактирование сервисов");

document.getElementById("edit-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const updatedService = {
        address_name: document.getElementById("address_name").value,
        url: document.getElementById("url").value,
        description: document.getElementById("description").value,
    };

    fetch(`${apiUrlPut}/edit/${serviceId}`, {
        method: "PUT",
        headers: getAuthHeaders(),
        body: JSON.stringify(updatedService),
    })
        .then(response => {
            if (!response.ok) throw new Error("Ошибка при обновлении.");
            alert("Данные обновлены!");
            window.location.href = "/addresses";
        })
        .catch(error => {
            console.error(error);
            alert("Ошибка при обновлении.");
        });
});
