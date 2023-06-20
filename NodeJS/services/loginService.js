const User = require('../models/Users').model;
const jwt = require("jsonwebtoken");

const login = async (username, password) => {
    var user;
    try{
        user = await User.findOne({username: username, password: password});
    }
    catch (error){
        return false;
    }
    if (user) {
        return true;
    } else {
        return false;
    }
}



const getUser = async (token)=>{
    try {
        const data = await jwt.verify(token, "OriItamarTalKey");
         return await User.findOne({username : data.username});
    }

    catch (error){
        return false;
    }
}

module.exports = {login,getUser}