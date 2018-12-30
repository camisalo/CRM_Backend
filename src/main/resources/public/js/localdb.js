class localdb {

   constructor(){

      if (!window.indexedDB) {
         window.alert("Your browser doesn't support a stable version of IndexedDB.")
      }
      const account = [
         { id: "00-01", name: "Aloizy", phone: "827293928", email: "alakalsd@powalikos.com" },
         { id: "00-02", name: "Jamajka", phone: "329273627", email: "asfasfa@kolpaka.com" }
      ];
      const contact = [
         { id: "00-01", firstname: "Aloizy", lastname: "Mieczysław", age: 35, email: "gasga@asfasfa.com" },
         { id: "00-02", firstname: "Jamajka",lastname: "Kobyła", age: 32, email: "asfassag@asfasf.com" }
      ];
      this.db;
      var request = window.indexedDB.open("CRM", 1);

      request.onerror = function(event) {
         console.log("error: ");
      };
            
      request.onsuccess = function(event) {
         this.db = request.result;
         console.log("success: "+ this.db);
      };

      request.onupgradeneeded = function(event) {
      this.db = event.target.result;
      var objectStore1 = this.db.createObjectStore("account", {keyPath: "id"});
      var objectStore2 = this.db.createObjectStore("contact", {keyPath: "id"});

         for (var i in account) {
            objectStore1.add(account[i]);
         }
         for (var i in contact) {
            objectStore2.add(contact[i]);
         }
      }
   }

   opendb() {
      this.db;
      var request = indexedDB.open("CRM", 1);  
  
      request.onsuccess = function (evt) {
         this.db = request.result; 
      }
   }
    

    

   read() {
        var transaction = this.db.transaction(["account"],"readwrite");
        var objectStore = transaction.objectStore("account");
        var request = objectStore.get("00-01");
        
        request.onerror = function(event) {
           alert("Unable to retrieve daa from database!");
        };
        
        request.onsuccess = function(event) {
           // Do something with the request.result!
           if(request.result) {
              return "Name: " + request.result.name + ", Age: " + request.result.age + ", Email: " + request.result.email;
           } else {
              return "Kenny couldn't be found in your database!";
           }
      };
   }

   readAll(_tab) {
      var objectStore = this.db.transaction([_tab]).objectStore(_tab);
      
      objectStore.openCursor().onsuccess = function(event) {
         var cursor = event.target.result;
         
         if (cursor) {
            alert("Name for id " + cursor.key + " is " + cursor.value.name + ", Phone: " + cursor.value.phone + ", Email: " + cursor.value.email);
            cursor.continue();
         } else {
            alert("No more entries!");
         }
      };
   }
}