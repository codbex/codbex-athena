const viewData = {
    id: "codbex-athena-purchase-invoices",
    label: "Purchase Invoices",
    lazyLoad: true,
    link: "/services/web/codbex-invoices/gen/ui/purchaseinvoice/index.html?embedded"
};
if (typeof exports !== 'undefined') {
    exports.getView = function () {
        return viewData;
    }
}