const fetch = require('node-fetch');
const express = require("express");
const app = express();
var port = process.env.PORT || 8045;
const data = require("./data.json");
const http = require('http');

const API_Key = "a032e8dbee5e41208ad6b248612de302";

//am creat o cale pentru a prelua datele din fisierul local data.json
app.get('/getRecipes', async(req, res) =>{
  res.send(JSON.stringify(data));
});



app.listen(port, () => {
  console.log("Server runs on the port: " + port);
});


//DACA PREIAU DATELE DIN API
//app.get('/getRecipes', async(req, res) =>{
  //var result = []; 
  // var id = 1;

  // for(id = 21; id < 31; id++){
  //   var url = `https://api.spoonacular.com/recipes/${id}/information?includeNutrition=false&apiKey=${API_Key}`;
  //   var fetch_response = await fetch(url) 
  //     .then(res => res.json())
  //     .catch(e => {
  //       console.error({
  //         "message": "Something is wrong..... ",
  //          error : e,
  //       });
  //     });
    //console.log("RESPONSE: ", fetch_response.json);
    //console.log("Titlu: ", fetch_response.title);
    //result.push(fetch_response);
    // }
    // var food = result;
    //res.send(JSON.stringify(food));
//});