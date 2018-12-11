/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var headlineButton = document.getElementById("headlineButton");
var headlineList = document.getElementById("headlineList");

var headlineButton2 = document.getElementById("headlineButton2");
var headlineList2 = document.getElementById("headlineList2");

var stockButton = document.getElementById("showStocks");
var stockList = document.getElementById("stockList");


headlineButton.addEventListener("click", function(){
    var data = new FormData();
    data.append('filename','WSJjson');
    
    var xhttp = new XMLHttpRequest();
    var filename = "?filename=WSJjson";
    xhttp.open('GET', "http://localhost:8084/NewsNStocks/StockNewsServlet"+filename, true);
    
    xhttp.onload = function(){
        if (xhttp.status >= 200 && xhttp.status < 400){
            var headlines = JSON.parse(xhttp.responseText);
            addHeadline(headlines);
        } else {
            console.log("Connection error");
        }
}

xhttp.send();

});

stockButton.addEventListener('click', function(){
   var xhttp2 = new XMLHttpRequest();
   var filename = "?filename=stocksjson";
   xhttp2.open('GET',"http://localhost:8084/NewsNStocks/StockNewsServlet"+filename,true);
   xhttp2.onload = function(){
       if (xhttp2.status >= 200 && xhttp2.status < 400){
            var stocks = JSON.parse(xhttp2.responseText);
            addStock(stocks);
        } else {
            console.log("Connection error");
        }
   }
   
   xhttp2.send();
});


headlineButton2.addEventListener("click", function(){
    var xhttp3 = new XMLHttpRequest();
    var filename = "?filename=WaPojson";
    xhttp3.open('GET', "http://localhost:8084/NewsNStocks/StockNewsServlet"+filename, true);
    xhttp3.onload = function(){
        if (xhttp3.status >= 200 && xhttp3.status < 400){
            var headlines2 = JSON.parse(xhttp3.responseText);
            addHeadline2(headlines2);
        } else {
            console.log("Connection error");
        }
}

xhttp3.send();
});

var i = 0;
function addHeadline(headlineJSON){
    
    
    var headlineHTML = "";
    
       
    headlineHTML += "<p><a href="+ headlineJSON[i].Headline.Link + " target=\"_blank\" class=\"hltext\">" + "•" + headlineJSON[i].Headline.Title + "</a></p>";
    headlineList.insertAdjacentHTML('beforeend', headlineHTML);
    i++;
}

var j = 1;
function addStock(stockJSON){
    
    var stocksHTML = "";
    
    stocksHTML += "<p><table><tr>" 
            + "<th>Symbol</th>" + "<th>Name</th>" + "<th>Last Price</th>" 
            + "<th>Market Time</th>" + "<th>Change Percent</th>" 
            + "<th>Change Volume</th>" + "<th>Average Volume</th>" 
            + "<th>Market Cap</th>" + "<th>Intraday LowHigh</th></tr>" 
            + "<tr>" + "<td>"+stockJSON[j].Company.Symbol+"</td>" + "<td>"+stockJSON[j].Company.Name+"</td>"
            + "<td>"+stockJSON[j].Company.LastPrice+"</td>" + "<td>"+stockJSON[j].Company.MarketTime+"</td>"
            + "<td>"+stockJSON[j].Company.ChangePercent+"</td>" + "<td>"+stockJSON[j].Company.ChangeVolume+"</td>"
            + "<td>"+stockJSON[j].Company.AverageVolume+"</td>" + "<td>"+stockJSON[j].Company.MarketCap+"</td>"
            + "<td>"+stockJSON[j].Company.IntradayLowHigh+"</td>" + "</tr></table></p>";
            
    stockList.insertAdjacentHTML('beforeend',stocksHTML);
    j++;
    }
    
var k = 0;
function addHeadline2(headlineJSON2){
    
    
    var headlineHTML2 = " ";
      
    headlineHTML2 += "<p><a href="+ headlineJSON2[k].Headline.Link + " target=\"_blank\" class=\"hltext\">" + "• " + headlineJSON2[k].Headline.Title + "</a></p>";
    headlineList2.insertAdjacentHTML('beforeend', headlineHTML2);
    k++;
}
