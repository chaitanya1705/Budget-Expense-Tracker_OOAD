document.addEventListener('DOMContentLoaded', function() {
    // Get chart data from script tag
    const chartDataScript = document.getElementById('chartData');
    if (!chartDataScript) return;
    
    let chartData;
    try {
        chartData = JSON.parse(chartDataScript.textContent);
        console.log("Parsed Chart Data:", chartData);
    } catch (e) {
        console.error("Error parsing chart data:", e);
        return;
    }
    
    // Get canvas element
    const ctx = document.getElementById('budgetChart').getContext('2d');
    
    // Create chart
    new Chart(ctx, {
        type: 'bar',
        data: chartData,
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Amount Spent'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Categories'
                    }
                }
            },
            plugins: {
                title: {
                    display: true,
                    text: 'Category-wise Spending Comparison'
                },
                legend: {
                    display: true,
                    position: 'top'
                }
            }
        }
    });
});