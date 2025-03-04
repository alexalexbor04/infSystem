import {checkUserRolePage, getAuthHeaders} from "./app_funcs.js";

const apiUrl = "http://localhost:8081/admin/users";
const urlParams = new URLSearchParams(window.location.search);
const userID = urlParams.get("id");

if (!userID) {
    alert("Ошибка: отсутствует ID пользователя.");
    window.location.href = "/admin/users";
}

async function loadUserData() {
    try {
        const response = await fetch(apiUrl, {
            method: "GET",
            headers: getAuthHeaders(),
        });
        if (!response.ok) {
            throw new Error(`Ошибка запроса: ${response.status}`);
        }
        const users = await response.json();
        const user = users.find(u => u.id === parseInt(userID));
        if (!user) {
            throw new Error("Пользователь не найден");
        }

        document.getElementById("edit-user-id").value = user.id;
        document.getElementById("edit-username").value = user.username || "Неизвестно";
        document.getElementById("roleSelect").value = user.role.name.toLowerCase(); // "admin" или "user"
    } catch (error) {
        console.error("Ошибка загрузки данных:", error);
        alert("Не удалось загрузить данные пользователя.");
        window.location.href = "/admin/users";
    }
}

loadUserData();

checkUserRolePage("/addresses", "У Вас нет прав на редактирование роли пользователей");

document.getElementById("edit-form").addEventListener("submit", async function(event) {
    event.preventDefault();

    const newRole = document.getElementById("roleSelect").value;

    try {
        const response = await fetch(`${apiUrl}/${userID}/change-role`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify({ role: newRole }),
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Ошибка ${response.status}: ${errorData.message || "Неизвестная ошибка"}`);
        }

        alert("Роль успешно обновлена!");
        window.location.href = "/admin/users";
    } catch (error) {
        console.error("Ошибка при обновлении роли:", error);
        alert("Ошибка: " + error.message);
    }
});