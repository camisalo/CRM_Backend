
var Tab = {
    ACCOUNTS: 'account',
    CONTACTS: 'contact',
    ATTEMPTS: 'attempt',
    OPPORTUNITIES: 'opportunities',
  };

class Controller {

    constructor(_tab){
        this.actualTab = _tab;
        this.localdb = new localdb();

        var a = document.getElementsByClassName('tabs');
        a[0].addEventListener('click',listener);
        a[1].addEventListener('click',listener);
        a[2].addEventListener('click',listener);
        a[3].addEventListener('click',listener);


        this.content = document.getElementById('content');
    }

    loadata() {
        this.localdb.readAll(this.actualTab);
        this.content.appendChild('table')
    }
}

function listener(){
    var tabs = document.getElementsByClassName('tabs');
    for (var i=0; i<tabs.length; i++){
        tabs[i].style.backgroundColor = 'transparent';
    }
    this.style.backgroundColor = 'green';
    switch (this.innerHTML){
        case 'Accounts':
            controller.actualTab = Tab.ACCOUNTS;
            break;
        case 'Contacts':
        controller.actualTab = Tab.CONTACTS;
            break;
        case 'Attempts':
        controller.actualTab = Tab.ATTEMPTS;
            break;
        case 'Opportunities':
        controller.actualTab = Tab.OPPORTUNITIES;
            break
    }
    console.log(controller.actualTab);

    controller.loadata();
}



var controller;

