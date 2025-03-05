import {updateRowCount, deleteRecord,
    getUserRole
} from "./app_funcs.js";

const apiUrl = "http://localhost:8081/data";

export {deleteDataRow, filterAndSortData, resetFilters, renderTableData, fetchData};

window.fetchData = fetchData;
window.deleteDataRow = deleteDataRow;
window.filterAndSortData = filterAndSortData;
window.resetFilters = resetFilters;
window.renderTableData = renderTableData;

let PDdata = [];

function fetchData() {
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
            const userName = JSON.parse(atob(localStorage.getItem("token").split(".")[1])).sub;
            PDdata = data.filter(item => item.userName === userName);
            renderTableData(PDdata);
        })
        .catch(error => {
            console.error("ошибка fetch:", error);
            console.log(localStorage.getItem("token"));
            alert("Ошибка загрузки данных. Пожалуйста, проверьте авторизацию.");
            window.location.href = "/auth/login";
        });
}

function renderTableData(data) {
    const tableBody = document.querySelector("#data-table tbody");
    tableBody.innerHTML = "";

    if (data.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="6">Нет доступных данных</td></tr>`;
        updateRowCount(0);
        return;
    }

    data.forEach(item => {
        const row = `
            <tr>
                <td>${item.id || "Нет"}</td>
                <td>${item.userName || "Нет"}</td>
                <td>${item.addressName || "Нет"}</td>
                <td>${item.url || "Нет"}</td>
                <td>${item.login || "Нет"}</td>
                <td>${item.userPassword || "Нет"}</td>
                <td>
                   <a href="../../src/update/update_data.html?id=${item.id}" class="can-edit">
                    Редактировать
                    </a>
                   <a href="#" class="can-delete" onclick="deleteDataRow(${item.id})">
                   Удалить
                    </a>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
    updateRowCount(data.length);
}

function filterAndSortData(sortBy = null) {
    const keyword = document.getElementById("keyword").value.toLowerCase();

    let filteredData = PDdata;

    filteredData = filteredData.filter(item => {
        return (
            (!keyword ||
                item.userName.toLowerCase().includes(keyword) ||
                item.addressName.toLowerCase().includes(keyword) ||
                item.url.toLowerCase().includes(keyword) ||
                item.login.toLowerCase().includes(keyword)
            )
        );
    });

    if (sortBy === 'address_name') {
        filteredData.sort((a, b) => a.addressName.localeCompare(b.addressName));
    }

    renderTableData(filteredData);
}

function resetFilters() {
    document.getElementById("keyword").value = "";
    renderTableData(PDdata);
}

function deleteDataRow(id) {
    const userRole = getUserRole();
    if (userRole === "user") {
        alert("У вас нет прав на удаление сервисов.");
        return;
    }
    deleteRecord(id, apiUrl, fetchData);
}

document.addEventListener("DOMContentLoaded", () => {
    fetchData();
});