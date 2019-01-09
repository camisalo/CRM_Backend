var model = new Model();

class Controller {

    constructor(){
        this.actualTab;
        this.localdb = localdb.getInstance();

        this.synchronize();
        this.content = document.getElementById('content');
    }

    synchronize(){
        model.getAccounts().then((data) => {
                console.log("callback");
                console.log(data);
            }).catch((err) => {alert(err);}
        );
            
       
        console.log("XXXXXXXXXXXXXXXXXXXXXXX");
     }
      
















     read() {
        return new Promise((resolve, reject) => {
           var transaction = db.transaction(["account"],"readwrite");
           var objectStore = transaction.objectStore("account");
           var request = objectStore.get("00-01");
           console.log("XDXDDXD");
           request.onerror = function(event) {
              alert("Unable to retrieve daa from database!");
           };
           
           // var x = document.getElementById("content");
           // x.innerHTML = "<table><th><td>BYCZ</td><td> ELO</td></th></table>";
           
           request.onsuccess = function(event) {
              var res = event.target.result;
  
              if(request.result) {
                 var x = document.getElementById("content");
                 x.innerHTML = "<table><th><td>"+res.name+"</td><td> "+ res.email + "</td></th></table>";
                 resolve("Name: " + request.result.name + ", Age: " + request.result.age + ", Email: " + request.result.email);
              } else {
                 reject("Kenny couldn't be found in your database!");
              }
           };
        }); 
     }
  
     readAll(_tab) {
        var objectStore = db.transaction([_tab]).objectStore(_tab);
        
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







    loadata() {
        console.log(this.actualTab);

        this.localdb.read().then((data) => {
            console.log(data);
            console.log("siema");
        })

        
        // getAsync(this.localdb.read(), function callback(data) { 
        //     console.log(data); 
        // });

        // this.content.appendChild('table')
    }

    readAllRecords() {
        if (status == Status.OFFLINE){
            console.log("OFFLINE");
            // wyświetl na View

        } else if ( status == Status.ONLINE){
            console.log("ONLINE");
             // JSON --> [ {  }, { } , { }  ]
            
             // zapisz w lokalnej bazie danych JSONa

             // wyświetl na View
    
        }
    }
}