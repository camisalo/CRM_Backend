var model = new Model();

class Controller {

   constructor(){
      this.actualTab = Tab.ACCOUNTS;
      this.localdb = localdb.getInstance();

      this.content = document.getElementById('content');
   }

   synchronize(){
      model.getRecords(Tab.ACCOUNTS)
      .then((data) => { console.log("pobrano rekordy"); return model.sendRecordsToServer(data,Tab.ACCOUNTS);})
      .then((a) => { console.log("wysłano rekordy na serwer " + a); return model.deleteAllRecordsFromLocalDb(Tab.ACCOUNTS);})
      .then((b) => { console.log("usunięto rekordy z bazy przeglądarki " + b); return model.getRecordsFromServer(Tab.ACCOUNTS);})
      .then((data) => { console.log("Pobrano rekordy z serwera"); model.saveToLocalDB(data, Tab.ACCOUNTS)})
      .then((data) => { controller.changeTab(); })
      .catch((err) => {alert(err);});

      model.getRecords(Tab.CONTACTS)
      .then((data) => { console.log("pobrano rekordy"); return model.sendRecordsToServer(data,Tab.CONTACTS);})
      .then((a) => { console.log("wysłano rekordy na serwer " + a); return model.deleteAllRecordsFromLocalDb(Tab.CONTACTS);})
      .then((b) => { console.log("usunięto rekordy z bazy przeglądarki " + b); return model.getRecordsFromServer(Tab.CONTACTS);})
      .then((data) => { console.log("Pobrano rekordy z serwera"); model.saveToLocalDB(data, Tab.CONTACTS)})
      .then((data) => { controller.changeTab(); })
      .catch((err) => {alert(err);});

      model.getRecords(Tab.ASSET)
      .then((data) => { console.log("pobrano rekordy"); return model.sendRecordsToServer(data,Tab.ASSET);})
      .then((a) => { console.log("wysłano rekordy na serwer " + a); return model.deleteAllRecordsFromLocalDb(Tab.ASSET);})
      .then((b) => { console.log("usunięto rekordy z bazy przeglądarki " + b); return model.getRecordsFromServer(Tab.ASSET);})
      .then((data) => { console.log("Pobrano rekordy z serwera"); model.saveToLocalDB(data, Tab.ASSET)})
      .then((data) => { controller.changeTab(); })
      .catch((err) => {alert(err);});

      model.getRecords(Tab.OPPORTUNITY)
      .then((data) => { console.log("pobrano rekordy"); return model.sendRecordsToServer(data,Tab.OPPORTUNITY);})
      .then((a) => { console.log("wysłano rekordy na serwer " + a); return model.deleteAllRecordsFromLocalDb(Tab.OPPORTUNITY);})
      .then((b) => { console.log("usunięto rekordy z bazy przeglądarki " + b); return model.getRecordsFromServer(Tab.OPPORTUNITY);})
      .then((data) => { console.log("Pobrano rekordy z serwera"); model.saveToLocalDB(data, Tab.OPPORTUNITY)})
      .then((data) => { controller.changeTab(); })
      .catch((err) => {alert(err);});

      
     }
      
   changeTab() {
      model.getRecords(this.actualTab)
         .then((data) => {
            controller.showRecords(data);
            addActionListenerToRecords();
         })
         .catch((err) => {alert(err);}
      );
   }

   checkTab() {
      return this.actualTab;
   }

   showRecords(data){
      var content = document.getElementById('content');
      var builder
      if (this.actualTab == Tab.ACCOUNTS) builder = new AccountBuilder(data);
      else if (this.actualTab == Tab.CONTACTS) builder = new ContactBuilder(data);
      else if (this.actualTab == Tab.ASSET) builder = new AssetBuilder(data);
      else if (this.actualTab == Tab.OPPORTUNITY) builder = new OpportunityBuilder(data);
      var supervisor = new Supervisor(builder);
      supervisor.build();
      var res = builder.getResoult();
      content.innerHTML = res;
   }

   showDetails(id) {
      return new Promise((resolve, reject) => {
         model.getRecordsById(this.actualTab, id)
            .then((data) => {
               resolve(model.getRecordsById(this.actualTab, id))
            })
            .catch((err) => {alert(err);}
         );
      });
   }

   saveRecord(record) {
      model.saveToLocalDB(record, this.actualTab)
      .then((data) => {
         console.log(data);
         controller.changeTab();
      });
   }

   deleteRecord(record_id) {
      model.getRecordsById(this.actualTab, record_id)
            .then((data) => {
               data.lastmodified = new Date().toMysqlFormat();
               data.state = "delete";
               data = [data];
               console.log(data);

               return model.saveToLocalDB(data, this.actualTab);
            })
            .then((data) => {
               controller.changeTab();
            })
            .catch((err) => {alert(err);}
         );
   }
}