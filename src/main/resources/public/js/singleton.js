//prefixes of implementation that we want to test
window.indexedDB = window.indexedDB || window.mozIndexedDB || 
window.webkitIndexedDB || window.msIndexedDB;
               
//prefixes of window.IDB objects
window.IDBTransaction = window.IDBTransaction || 
window.webkitIDBTransaction || window.msIDBTransaction;
window.IDBKeyRange = window.IDBKeyRange || window.webkitIDBKeyRange || 
window.msIDBKeyRange


var localdb = (function () {
    var instance;
 
    function createInstance() {
        // var object = new Object("I am the instance");
        // return object;


        if (!window.indexedDB) {
           window.alert("Your browser doesn't support a stable version of IndexedDB.")
        }

        var request = window.indexedDB.open("CRM", 1);
   
        request.onerror = function(event) {
           console.log("error: ");
        };
               
        request.onsuccess = function(event) {
           instance = request.result;
           console.log("success: "+ instance);
        };
   
        request.onupgradeneeded = function(event) {
            instance = event.target.result;
            var objectStore1 = instance.createObjectStore("account", { keyPath: "id", autoIncrement:true });
            var objectStore2 = instance.createObjectStore("contact", { keyPath: "id", autoIncrement:true });
            var objectStore3 = instance.createObjectStore("asset", { keyPath: "id", autoIncrement:true });
            var objectStore4 = instance.createObjectStore("opportunity", { keyPath: "id", autoIncrement:true });
        }
    }
 
    return {
        getInstance: function () {
            if (!instance) {
                instance = createInstance();
            }
            return instance;
        }
    };
 })();
 