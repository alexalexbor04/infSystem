import Chart from 'chart.js/auto';
import {getAuthHeaders} from "./app_funcs.js"; // Автоматическая настройка типов

const apiDataUrl = "http://localhost:8081/data";
const servicesUrl = "http://localhost:8081/addresses";

async function loadChartData() {
    try {
        const servicesResponse = await fetch(servicesUrl, {
            headers: getAuthHeaders(),
        });
        if (!servicesResponse.ok) throw new Error("Ошибка загрузки сервисов");
        const services = await servicesResponse.json();

        const dataResponse = await fetch(apiDataUrl, {
            headers: getAuthHeaders(),
        });
        if (!dataResponse.ok) throw new Error("Ошибка загрузки данных");
        const passwordData = await dataResponse.json();

        const serviceCounts = {};
        services.forEach(service => {
            serviceCounts[service.id] = 0;
        });

        passwordData.forEach(data => {
            const addressId = data.addressId;
            if (addressId && serviceCounts[addressId] !== undefined) {
                serviceCounts[addressId]++;
            }
        });

        const labels = services.map(service => service.address_name);
        const data = services.map(service => serviceCounts[service.id]);

        console.log(labels);
        console.log(data);

        const ctx = document.getElementById("myChart").getContext("2d");
        new Chart(ctx, {
            type: "bar",
            data: {
                labels: labels,
                datasets: [{
                    label: "Количество записей",
                    data: data,
                    backgroundColor: "rgba(75, 192, 192, 0.2)",
                    borderColor: "rgba(75, 192, 192, 1)",
                    borderWidth: 1,
                }],
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        title: { display: true, text: "Количество записей" },
                    },
                    x: {
                        title: { display: true, text: "Сервисы" },
                    },
                },
            },
        });
    } catch (error) {
        console.error("Ошибка при загрузке данных для графика:", error);
        alert("Не удалось загрузить данные для графика.");
    }
}

loadChartData();