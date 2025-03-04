import {
    getAuthHeaders, updateRowCount, deleteRecord,
    getUserRole, checkUserRolePage
} from "./app_funcs.js";

const apiUrl = "http://localhost:8081/admin/users";

export {fetchUsers, renderTable, filterAndSortUsers, resetFilters, deleteUsers}

window.fetchUsers = fetchUsers;
window.renderTableUsers = renderTable;
window.filterAndSortUsers = filterAndSortUsers;
window.resetFilters = resetFilters;
window.deleteUsers = deleteUsers;

checkUserRolePage("/addresses", "У Вас нет прав на посещение данной страницы");

let userData = [];

function fetchUsers() {
    const userRole = getUserRole();
    console.log(userRole) //отладка временно

    fetch(apiUrl, {
        method: "GET",
        headers: getAuthHeaders(),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Ошибка запроса: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log(localStorage.getItem("token")); // отладка
            userData = data;
            renderTable(userData);
        })
        .catch(error => {
            console.error("ошибка fetch:", error);
            console.log(localStorage.getItem("token"));
            alert("Ошибка загрузки данных. Пожалуйста, проверьте авторизацию.");
            // window.location.href = "/auth/login";
        });
}

function renderTable(userData) {
    const tableBody = document.querySelector("#users-table tbody");
    tableBody.innerHTML = "";

    if (userData.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="6">Нет доступных данных</td></tr>`;
        updateRowCount(0);
        return;
    }

    userData.forEach(item => {
        const row = `
            <tr>
                <td>${item.id || "Нет"}</td>
                <td>${item.username || "Нет"}</td>
                <td>${item.full_name || "Нет"}</td>
                <td>${item.email || "Нет"}</td>
                <td>${item.role.name || "Нет"}</td>
                <td>
                   <a href="../../src/update/update_role.html?id=${item.id}" class="can-edit">
                    Редактировать роль
                    </a>
                   <a href="#" class="can-delete" onclick="deleteUsers(${item.id})">
                   Удалить
                    </a>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
    updateRowCount(userData.length);
}

function filterAndSortUsers(sortBy = null) {
    const keyword = document.getElementById("keyword").value.toLowerCase();
    const selectedRole = document.getElementById("filter-status").value;

    let filteredData = userData;

    filteredData = filteredData.filter(user => {
        return (
            (!keyword ||
                user.username.toLowerCase().includes(keyword) ||
                user.full_name.toLowerCase().includes(keyword) ||
                user.email.toLowerCase().includes(keyword) ||
                user.role.name.includes(keyword)) &&
            (!selectedRole || user.role.name === selectedRole)
        );
    });

    if (sortBy) {
        switch (sortBy) {
            case "username":
                filteredData.sort((a, b) => a.username.localeCompare(b.username));
                break;
            case "role":
                filteredData.sort((a, b) => a.role.name.localeCompare(b.role.name));
                break;
            default:
                break;
        }
    }

    renderTable(filteredData);
}

function resetFilters() {
    document.getElementById("keyword").value = "";
    renderTable(userData);
}

function deleteUsers(id) {
    const userRole = getUserRole();
    if (userRole === "user") {
        alert("У вас нет прав на удаление юзеров.");
        return;
    }
    deleteRecord(id, apiUrl, fetchUsers);
}

document.addEventListener("DOMContentLoaded", () => {
    fetchUsers();
});

