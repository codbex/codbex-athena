const dashboard = angular.module('dashboard', ['ideUI', 'ideView']);

dashboard.controller('DashboardController', ['$scope', '$document', '$http', 'messageHub', function ($scope, $document, $http, messageHub) {
    $scope.state = {
        isBusy: true,
        error: false,
        busyText: "Loading...",
    };

    angular.element($document[0]).ready(async function () {
        const productData = await getProductData();
        // Doughnut Chart Data
        const doughnutData = {
            labels: ['Active Products', 'Inactive Products'],
            datasets: [{
                data: [productData.ActiveProducts, productData.InactiveProducts],
                backgroundColor: ['#36a2eb', '#ff6384']
            }]
        };

        // Doughnut Chart Configuration
        const doughnutOptions = {
            responsive: true,
            maintainAspectRatio: false,
            legend: {
                position: 'bottom'
            },
            title: {
                display: true,
                text: 'Product Status'
            },
            animation: {
                animateScale: true,
                animateRotate: true
            }
        };
        // Initialize Doughnut Chart
        const doughnutChartCtx = $document[0].getElementById('doughnutChart').getContext('2d');
        const doughnutChart = new Chart(doughnutChartCtx, {
            type: 'doughnut',
            data: doughnutData,
            options: doughnutOptions
        });
        $scope.$apply(function () {
            $scope.state.isBusy = false;
        });
    });

    $scope.openPerspective = function (perspective) {
        // if (perspective === 'sales-orders') {
        //     messageHub.postMessage('launchpad.switch.perspective', { perspectiveId: 'sales-orders' }, true);
        // } else if (perspective === 'products') {
        //     messageHub.postMessage('launchpad.switch.perspective', { perspectiveId: 'products' }, true);
        // } else if (perspective === 'sales-invoices') {
        //     messageHub.postMessage('launchpad.switch.perspective', { perspectiveId: 'sales-invoices' }, true);
        // }
        ;
        messageHub.postMessage('launchpad.switch.perspective', { perspectiveId: 'sales-invoices' }, true);
    }

    $scope.today = new Date();

    const invoiceServiceUrl = "/services/ts/codbex-athena/api/InvoiceService.ts/invoiceData";
    $http.get(invoiceServiceUrl)
        .then(function (response) {
            $scope.InvoiceData = response.data;
            calculateGrossProfit();
        });

    async function getProductData() {
        try {
            const response = await $http.get("/services/ts/codbex-athena/api/ProductService.ts/productData");
            return response.data;
        } catch (error) {
            console.error('Error fetching product data:', error);
        }
    }

    function calculateGrossProfit() {
        if ($scope.InvoiceData && $scope.OrderData) {
            $scope.GrossProfit = (($scope.InvoiceData.SalesInvoiceTotal + $scope.OrderData.SalesOrderTotal) - ($scope.InvoiceData.PurchaseInvoiceTotal + $scope.OrderData.PurchaseOrderTotal)).toFixed(2);
        }
    }

}]);