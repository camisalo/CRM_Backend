
var SizeEnum = {
    ONLINE: 1,
    OFFLINE: 2,
  };

function checkStatus() {
    var stat = document.getElementById("status");
    if (navigator.onLine) {
        status = SizeEnum.ONLINE;
        stat.innerHTML = "Online :D";
        stat.style.backgroundColor = "green";
    } else {
        status = SizeEnum.OFFLINE;
        stat.innerHTML = "Offline :(";
        stat.style.backgroundColor = "red"
    }
}

function onload() {
    
    checkStatus();
    controller = new Controller(Tab.ACCOUNTS);
//    var C = new localdb(this);
//    document.getElementById("content").innerHTML = C.read("00-01");
}

var status;
setInterval(checkStatus,1000);


               //prefixes of implementation that we want to test
               window.indexedDB = window.indexedDB || window.mozIndexedDB || 
               window.webkitIndexedDB || window.msIndexedDB;
               
               //prefixes of window.IDB objects
               window.IDBTransaction = window.IDBTransaction || 
               window.webkitIDBTransaction || window.msIDBTransaction;
               window.IDBKeyRange = window.IDBKeyRange || window.webkitIDBKeyRange || 
               window.msIDBKeyRange