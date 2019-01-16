var AccountBuilder = /** @class */ (function () {
    function AccountBuilder(data) {
        this.records = data;
    }
    AccountBuilder.prototype.buildHeader = function () {
        this.table = "<table><tr><th>Name</th><th>address</th><th>phone</th></tr>";
    };
    AccountBuilder.prototype.buildContent = function () {
        var i;
        for (i = 0; i < Object.keys(this.records).length; i++) {
            this.table += "<tr class=\"" + this.records[i].state + "\" id=\"" + this.records[i].id + "\"><td>" + this.records[i].name + "</td><td>" + this.records[i].address + "</td><td>" + this.records[i].phone + "</td></th>";
        }
    };
    AccountBuilder.prototype.buildFooter = function () {
        this.table += "</table>";
    };
    AccountBuilder.prototype.getResoult = function () {
        return this.table;
    };
    return AccountBuilder;
}());
var ContactBuilder = /** @class */ (function () {
    function ContactBuilder(data) {
        this.records = data;
    }
    ContactBuilder.prototype.buildHeader = function () {
        this.table = "<table><tr><th>First Name</th><th>Last Name</th><th>Birth date</th><th>Email</th></tr>";
    };
    ContactBuilder.prototype.buildContent = function () {
        var i;
        for (i = 0; i < Object.keys(this.records).length; i++) {
            this.table += "<tr class=\"" + this.records[i].state + "\" id=\"" + this.records[i].id + "\"><td>" + this.records[i].firstname + "</td><td>" + this.records[i].lastname + "</td><td>" + this.records[i].birthdate + "</td><td>" + this.records[i].email + "</td></th>";
        }
    };
    ContactBuilder.prototype.buildFooter = function () {
        this.table += "</table>";
    };
    ContactBuilder.prototype.getResoult = function () {
        return this.table;
    };
    return ContactBuilder;
}());
var AssetBuilder = /** @class */ (function () {
    function AssetBuilder(data) {
        this.records = data;
    }
    AssetBuilder.prototype.buildHeader = function () {
        this.table = "<table><tr><th>Name</th><th>description</th><th>price</th></tr>";
    };
    AssetBuilder.prototype.buildContent = function () {
        var i;
        for (i = 0; i < Object.keys(this.records).length; i++) {
            this.table += "<tr class=\"" + this.records[i].state + "\" id=\"" + this.records[i].id + "\"><td>" + this.records[i].name + "</td><td>" + this.records[i].description + "</td><td>" + this.records[i].price + " USD</td></th>";
        }
    };
    AssetBuilder.prototype.buildFooter = function () {
        this.table += "</table>";
    };
    AssetBuilder.prototype.getResoult = function () {
        return this.table;
    };
    return AssetBuilder;
}());
var OpportunityBuilder = /** @class */ (function () {
    function OpportunityBuilder(data) {
        this.records = data;
    }
    OpportunityBuilder.prototype.buildHeader = function () {
        this.table = "<table><tr><th>Name</th><th>Amount</th><th>Open Date</th><th>Close Date</th><th>Stage</th></tr>";
    };
    OpportunityBuilder.prototype.buildContent = function () {
        var i;
        for (i = 0; i < Object.keys(this.records).length; i++) {
            this.table += "<tr class=\"" + this.records[i].state + "\" id=\"" + this.records[i].id + "\"><td>" + this.records[i].name + "</td><td>" + this.records[i].amount + " USD</td><td>" + this.records[i].opendate + "</td><td>" + this.records[i].closedate + "</td><td>" + this.records[i].stage + "</td></th>";
        }
    };
    OpportunityBuilder.prototype.buildFooter = function () {
        this.table += "</table>";
    };
    OpportunityBuilder.prototype.getResoult = function () {
        return this.table;
    };
    return OpportunityBuilder;
}());
