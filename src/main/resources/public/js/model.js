class Model {

    getRecordsFromServer(tab) {
        var url = "http://localhost:8080/crm/" + tab;
        return new Promise((resolve, reject) => { 
            var httpReq = new XMLHttpRequest(); 
            httpReq.onreadystatechange = () => { 
                if (httpReq.readyState === 4) { 
                    if (httpReq.status === 200) { 
                        resolve(JSON.parse(httpReq.responseText)); 
                    } else { 
                       reject(new Error(httpReq.statusText)); 
                    } 
                } 
            }
            httpReq.open("GET", url, true); 
            httpReq.setRequestHeader('Content-Type','application/json');
            httpReq.send(null); 
        });
    }
//TO DO !!!!!!!!!!!!!!!!

    sendRecordsToServer(data, tab) {
        // console.log(data);
        var url = "http://localhost:8080/crm/" + tab;
        return new Promise((resolve, reject) => { 
            var httpReq = new XMLHttpRequest(); 
            httpReq.onreadystatechange = () => { 
                if (httpReq.readyState === 4 && httpReq.status === 200) {
                    // console.log("WYSŁANO " + httpReq.statusText);
                    resolve("OK"); 
                } else { 
                    // console.log(httpReq.statusText);
                    // reject(new Error(httpReq.status)); 
                } 
                
            }
            httpReq.open("POST", url, true); 
            httpReq.setRequestHeader('Content-Type','application/json');
            httpReq.send(JSON.stringify(data));
        });  
    }

// zapisywanie całej tablicy w lokalnej bazie danych
    saveToLocalDB(data, tab) {
        return new Promise((resolve) => {

            var db = localdb.getInstance();
            // console.log(tab);
            var transaction = db.transaction(tab, 'readwrite');
            var itemStore = transaction.objectStore(tab);
            var i=0;
            putNext();
                
            function putNext() {
                if (i<data.length) {
                    // console.log(data[i]);
                    itemStore.put(data[i]).onsuccess = putNext;
                    ++i;
                } else {   // complete
                // console.log('populate complete');
                resolve("complete");
                }
            }   
        });
    }


    getRecords(tab){
        return new Promise((resolve, reject) => {

            var db = localdb.getInstance();
            // console.log(tab);
            var transaction = db.transaction(tab, 'readonly');
            var itemStore = transaction.objectStore(tab);
            
            if ('getAll' in itemStore) {
                // IDBObjectStore.getAll() will return the full set of items in our store.
                itemStore.getAll().onsuccess = function(event) {
                    resolve(event.target.result);
                };
            }
        });
    }

    getRecordsById(tab, id){
        return new Promise((resolve, reject) => {
                var db = localdb.getInstance();

                // console.log(id);
                var transaction = db.transaction(tab, 'readonly');
                var itemStore = transaction.objectStore(tab);
            
                var request = itemStore.get(parseInt(id));
                request.onsuccess = function() {
                    console.log("Model --> " + request.result);
                    resolve(request.result);
                };
                
        });
    }

    deleteAllRecordsFromLocalDb(tab) {
        return new Promise((resolve) => {

            var db = localdb.getInstance();
            // console.log(tab);
            var transaction = db.transaction(tab, 'readwrite');
            var itemStore = transaction.objectStore(tab);
            
            // Make a request to clear all the data out of the object store
            var objectStoreRequest = itemStore.clear();

            objectStoreRequest.onsuccess = function(event) {
                // report the success of our request
                // console.log("Wyczyszczono baze ze wszystkich rekordów");
                resolve("Wyczyszczono");
            };
        });
    }
}