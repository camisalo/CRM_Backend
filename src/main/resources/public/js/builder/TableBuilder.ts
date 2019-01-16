

interface TableBuilder {

    buildHeader();
    buildContent();
    buildFooter();
    getResoult();
}

class AccountBuilder implements TableBuilder{
    records: Account;
    table: String;

    constructor(data: Account) {
        this.records = data;
    }

    buildHeader() {
        this.table = "<table><tr><th>Name</th><th>address</th><th>phone</th></tr>";
    }

    buildContent() {
        var i;
        for (i=0;i<Object.keys(this.records).length;i++){
            this.table += "<tr class=\""+this.records[i].state+"\" id=\""+this.records[i].id+"\"><td>"+ this.records[i].name+"</td><td>"+this.records[i].address+"</td><td>"+this.records[i].phone+"</td></th>";
        }
    }

    buildFooter() {
        this.table += "</table>";
    }

    getResoult() {
        return this.table;
    }
}

class ContactBuilder implements TableBuilder{
    records: Contact;
    table: String;

    constructor(data: Contact) {
        this.records = data;
    }

    buildHeader() {
        this.table = "<table><tr><th>First Name</th><th>Last Name</th><th>Birth date</th><th>Email</th></tr>";
    }

    buildContent() {
        var i;
        for (i=0;i<Object.keys(this.records).length;i++){
            this.table += "<tr class=\""+this.records[i].state+"\" id=\""+this.records[i].id+"\"><td>"+ this.records[i].firstname+"</td><td>"+this.records[i].lastname+"</td><td>"+this.records[i].birthdate+"</td><td>"+this.records[i].email+"</td></th>";
        }
    }

    buildFooter() {
        this.table += "</table>";
    }

    getResoult() {
        return this.table;
    }
}

class AssetBuilder implements TableBuilder{
    records: Asset;
    table: String;

    constructor(data: Asset) {
        this.records = data;
    }

    buildHeader() {
        this.table = "<table><tr><th>Name</th><th>description</th><th>price</th></tr>";
    }

    buildContent() {
        var i;
        for (i=0;i<Object.keys(this.records).length;i++){
            this.table += "<tr class=\""+this.records[i].state+"\" id=\""+this.records[i].id+"\"><td>"+ this.records[i].name+"</td><td>"+this.records[i].description+"</td><td>"+this.records[i].price+" USD</td></th>";
        }
    }

    buildFooter() {
        this.table += "</table>";
    }

    getResoult() {
        return this.table;
    }
}

class OpportunityBuilder implements TableBuilder{
    records: Opportunity;
    table: String;

    constructor(data: Opportunity) {
        this.records = data;
    }

    buildHeader() {
        this.table = "<table><tr><th>Name</th><th>Amount</th><th>Open Date</th><th>Close Date</th><th>Stage</th></tr>";
    }

    buildContent() {
        var i;
        for (i=0;i<Object.keys(this.records).length;i++){
            this.table += "<tr class=\""+this.records[i].state+"\" id=\""+this.records[i].id+"\"><td>"+ this.records[i].name+"</td><td>"+this.records[i].amount+" USD</td><td>"+this.records[i].opendate+"</td><td>"+this.records[i].closedate+"</td><td>"+this.records[i].stage+"</td></th>";
        }
    }

    buildFooter() {
        this.table += "</table>";
    }

    getResoult() {
        return this.table;
    }
}