const loginService = require('../services/loginService')
const jwt = require("jsonwebtoken")
const androidTokens = require('../androidTokens')
const admin = require("firebase-admin");
const socketsManager = require("../socketsManager");


const login = async (req, res) => {
    if (!req.body.username || !req.body.password) {
        return res.status(400).json("Bad Request");
    }
    let x = await loginService.login(req.body.username, req.body.password);
    if (x) {
        if (req.headers['phonetoken']) {
            androidTokens[req.body.username] = req.headers['phonetoken'];
            delete socketsManager[req.body.username];
        }
        const data = {username: req.body.username}
        const token = jwt.sign(data, "OriItamarTalKey");
        res.send(token);
    } else {
        return res.status(404).json("not valid user/password.");
    }
}


const getUser = async (req, res) => {
    if (!req.params.username) {
        return res.status(400).json("Bad Request");
    }
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        const result = await loginService.getUser(token);
        if (!result) {
            return res.status(401).json("Unauthorized");
        } else {
            if (result.username !== req.params.username) {
                return res.status(401).json("Unauthorized");
            }
            const x = {username: result.username, displayName: result.displayName, profilePic: result.profilePic}
            return res.json(x);
        }
    } else {
        return res.status(400).json("Bad Request");
    }
}


module.exports = {login, getUser}
