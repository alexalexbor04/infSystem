export {getAuthHeaders, updateRowCount,
    deleteRecord, getUserRole, checkUserRoleButton,
    checkUserRolePage}

window.getAuthHeaders = getAuthHeaders;
window.updateRowCount = updateRowCount;
window.deleteRecord = deleteRecord;
window.getUserRole = getUserRole;
window.checkUserRoleButton = checkUserRoleButton;
window.checkUserRolePage = checkUserRolePage;
// window.fetchDataList = fetchDataList;


function getAuthHeaders() {
    const token = localStorage.getItem("token");
    return {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
    };
}

function getUserRole() {
    const token = localStorage.getItem("token");
    if (!token) return null;
    const payload = JSON.parse(atob(token.split(".")[1]));

    return payload.roles.name;
}

function deleteRecord(id, apiUrl, fetchCallback) {
    const confirmDelete = confirm("Вы уверены, что хотите удалить эту запись?");
    if (!confirmDelete) return;

    const url = `${apiUrl}/${id}`;

    fetch(url, {
        method: "DELETE",
        headers: getAuthHeaders(),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Ошибка удаления записи");
            }
            alert("Запись успешно удалена");
            if (fetchCallback && typeof fetchCallback === "function") {
                fetchCallback();
            }
        })
        .catch(error => console.error("Ошибка удаления:", error));
}

function updateRowCount(count) {
    document.getElementById("row-count").textContent = count;
}

function checkUserRoleButton(id) {
    const addButton = document.getElementById(id);
    const userRole = getUserRole();
    if (userRole === 'admin') {
        addButton.style.display = "inline";
    } else {
        addButton.style.display = "none";
    }
}

function checkUserRolePage(path, message) {
    const userRole = getUserRole();
    if (userRole !== "admin") {
        alert(message);
        window.location.href = path;
    }
}

export function logout() {
    localStorage.removeItem('token');
    window.location.href = '/auth/logout';
}

