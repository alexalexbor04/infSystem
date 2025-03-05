import {
    getAuthHeaders, updateRowCount, deleteRecord,
    getUserRole, logout
} from "./app_funcs.js";

const apiUrl = "http://localhost:8081/addresses";

export { fetchAddresses, renderTable,
    deleteAddresses, filterAndSortAddr, resetFilters};

window.fetchAddresses = fetchAddresses;
window.renderTable = renderTable;
window.deleteAddresses = deleteAddresses;
window.filterAndSortAddr = filterAndSortAddr;
window.resetFilters = resetFilters;

let addressData = [];

function fetchAddresses() {
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
            console.log(localStorage.getItem("token"));
            addressData = data;
            renderTable(addressData);
        })
        .catch(error => {
            console.error("ошибка fetch:", error);
            console.log(localStorage.getItem("token"));
            alert("Ошибка загрузки данных. Пожалуйста, проверьте авторизацию.");
            logout();
        });
}

function renderTable(data) {
    const tableBody = document.querySelector("#addresses-table");
    tableBody.innerHTML = "";

    const userRole = getUserRole();

    if (data.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="6">Нет доступных данных</td></tr>`;
        updateRowCount(0);
        return;
    }

    data.forEach(item => {
        const row = `
            <tr>
                <td>${item.id || "Нет"}</td>
                <td>${item.address_name || "Нет"}</td>
                <td>${item.url || "Нет"}</td>
                <td>${item.description || "Нет"}</td>
                <td>
                   <a href="../../src/update/update_address.html?id=${item.id}" class="can-edit" style="${userRole === 'admin' ? 'display:inline;' : 'display:none;'}">
                    Редактировать
                    </a>
                   <a href="#" class="can-delete" style="${userRole === 'admin' ? 'display:inline;' : 'display:none;'}"
                   onclick="deleteAddresses(${item.id})">
                   Удалить
                    </a>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
    updateRowCount(data.length);
}

function filterAndSortAddr(sortBy = null) {
    const keyword = document.getElementById("keyword").value.toLowerCase();

    let filteredData = addressData;

    filteredData = filteredData.filter(item => {
        return (
            (!keyword ||
                item.address_name.toLowerCase().includes(keyword) ||
                item.description.toLowerCase().includes(keyword) ||
                item.url.toLowerCase().includes(keyword))
        );
    });

    if (sortBy === 'address_name') {
        filteredData.sort((a, b) => a.address_name.localeCompare(b.address_name));
    }

    renderTable(filteredData);
}

function resetFilters() {
    document.getElementById("keyword").value = "";
    renderTable(addressData);
}

function deleteAddresses(id) {
    const userRole = getUserRole();
    if (userRole === "user") {
        alert("У вас нет прав на удаление сервисов.");
        return;
    }
    deleteRecord(id, apiUrl, fetchAddresses);
}

document.getElementById('logout-button').addEventListener('click', () => {
    logout();
});

document.addEventListener("DOMContentLoaded", () => {
    fetchAddresses();
});

