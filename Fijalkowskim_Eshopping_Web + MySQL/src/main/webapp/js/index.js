function changeItem(nextItem) {
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            const shopItemContainer = JSON.parse(this.responseText);

            displayShopItemContainer(shopItemContainer);
        }
    };

    xhttp.open("GET", "changeItem?arg1=" + nextItem, true);
    xhttp.send();
}
function buyCurrentItem() {
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            const displayedData = JSON.parse(this.responseText);
            displayShopItemContainer(displayedData.shopItemContainer);
            displayException(displayedData.exceptionType)
            document.getElementById("cash").innerHTML = displayedData.cash.toFixed(2) + "$";
        }
    };

    xhttp.open("GET", "buyItem", true);
    xhttp.send();
}

function initData(){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            const initData = JSON.parse(this.responseText);

            displayShopItemContainer(initData.shopItemContainer);
            displayException("NONE")
            document.getElementById("cash").innerHTML = initData.cash.toFixed(2) + "$";
        }
    };

    xhttp.open("GET", "init", true);
    xhttp.send();
}
function displayShopItemContainer(shopItemContainer){

    if(shopItemContainer === null){
        document.getElementById("item-name").innerHTML = "No item found";
        document.getElementById("item-image").src = "images/noItem.png";
        document.getElementById("item-description").innerHTML = "";
        document.getElementById("item-price").innerHTML = "";
        document.getElementById("item-amount").innerHTML = "";
        document.getElementById("buy-btn").style.display = "none";
    }
    else{
        document.getElementById("item-name").innerHTML = shopItemContainer.shopItem.name;
        document.getElementById("item-image").src = "images/" + shopItemContainer.shopItem.imageUrl;
        document.getElementById("item-description").innerHTML = shopItemContainer.shopItem.description;
        document.getElementById("item-price").innerHTML = "$" + shopItemContainer.shopItem.price.toFixed(2);
        document.getElementById("item-amount").innerHTML = + shopItemContainer.count + " in stock";
        document.getElementById("buy-btn").style.display = "block";
    }
}
document.addEventListener('DOMContentLoaded', function() {
    initData();
});

function displayException(exceptionType){
    var errorText = ""
    switch(exceptionType){
        case "NONE":
            errorText = ""
            break;
        case "ITEM_NOT_IN_STOCK":
            errorText = "Item Not In Stock"
            break;
        case "NOT_ENOUGH_MONEY":
            errorText = "Not Enough Money"
            break;
        case "ILLEGAL_ARGUMENT":
            errorText = "Illegal Argument"
            break;
        case "ITEM_ALREADY_IN_DATABASE":
            errorText = "Item already in database"
            break;
        case "ITEM_NOT_IN_DATABASE":
            errorText = "Item not in database"
            break;
        default:
            errorText = ""
                break;
    }
    document.getElementById("error-container").innerHTML = errorText;
}