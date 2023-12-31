const chatsService = require('../services/chatsService')
const jwt = require("jsonwebtoken")
const androidTokens = require('../androidTokens')
const admin = require("firebase-admin");
const socketsManager = require("../socketsManager")
const {model: User} = require("../models/Users");
const {model: Chat} = require("../models/Chats");


const postChats = async (req, res) => {
    if (!req.body.username) {
        return res.status(400).json("Bad Request");
    }
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        var data;
        try {
            data = jwt.verify(token, "OriItamarTalKey");
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        let x = await chatsService.postChats(req.body.username, data.username);
        if (x === -10) {
            return res.status(404).json("error");
        } else if (x === -1) {
            return res.status(400).json("Can't talk with yourself");
        } else if (x === -2) {
            return res.status(400).json("No such user");
        }
        if (x) {
            if (androidTokens[req.body.username]) {
                const message = {
                    notification: {
                        title: 'Chat',
                        body: data.username + " has started a conversation with you!",
                    },
                    data: {
                        action: 'add_contact',
                    },
                    token: androidTokens[req.body.username]
                };
                admin.messaging().send(message);
            } else if (androidTokens[data.username] && !androidTokens[req.body.username]) {
                if (socketsManager[req.body.username]) {
                    await socketsManager[req.body.username].emit("add-contact");
                }
            }
            res.send(x);
        } else {
            return res.status(404).json("error");
        }
    } else {
        return res.status(400).json("Bad Request");
    }
}

const getChats = async (req, res) => {
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        var data;
        try {
            data = jwt.verify(token, "OriItamarTalKey");
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        let x = await chatsService.getChats(data.username);
        if (x === -10) {
            return res.status(404).json("error");
        }
        res.send(x);
    } else {
        return res.status(400).json("Bad Request");
    }
}


const sendMessage = async (req, res) => {
    if (!req.params.id) {
        return res.status(400).json("Bad Request");
    }
    if (!req.body.msg) {
        return res.status(400).json("Bad Request");
    }
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        var user;
        var data;
        try {
            const token = req.headers.authorization.split(" ")[1];
            data = jwt.verify(token, "OriItamarTalKey")
            user = data.username;
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        const msg = req.body.msg;
        const id = req.params.id;
        const sent = await chatsService.sendMessage(user, msg, id);
        if (sent === -10) {
            return res.status(404).json("error");
        } else if (!sent) {
            return res.status(401).json("Unauthorized");
        }
        const chat = await Chat.findById(id);
        if (chat.users[0] === data.username) {
            var talkingTo = chat.users[1];
        } else {
            var talkingTo = chat.users[0];
        }
        if (androidTokens[talkingTo]) {
            for (let i = 0; i < sent.content.length; i += 3000) {
                const message = {
                    notification: {
                        title: 'Message from ' + sent.sender.displayName,
                        body: sent.content.substring(i, i + 3000),
                    },
                    data: {
                        action: 'send_message',
                        senderUsername: sent.sender.username.toString(),
                        senderDisplayName: sent.sender.displayName.toString(),
                        receiver: talkingTo.toString(),
                        date: sent.created.toISOString(),
                        msgID: sent.id.toString(),
                        chatID: id.toString(),
                    },
                    token: androidTokens[talkingTo]
                };
                await admin.messaging().send(message);
            }
        } else if (androidTokens[data.username] && !androidTokens[talkingTo]) {
            var newMsg = {
                    chatID: id,
                    message: {
                        message: msg,
                        pic: {[data.username]: sent.sender.profilePic},
                        me: data.username,
                        date: formatDate(sent.created)
                    },
                    receiverUsername: talkingTo
                }
            if (socketsManager[talkingTo]) {
                await socketsManager[talkingTo].emit("receive-message", newMsg);
            }
        }
        res.send(sent);

    } else {
        return res.status(400).json("Bad Request");
    }
}

const getMessagesById = async (req, res) => {
    if (!req.params.id) {
        return res.status(400).json("Bad Request");
    }
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        var data;
        try {
            data = jwt.verify(token, "OriItamarTalKey")
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        var x = await chatsService.getMessagesById(req.params.id, data.username);
        if (x === -10) {
            return res.status(404).json("error");
        } else if (x === -1) {
            return res.status(401).json("Unauthorized");
        } else {
            res.send(x);
        }
    } else {
        return res.status(400).json("Bad Request");
    }
}


const getOnlyMessages = async (req, res) => {
    var username;
    if (!req.params.id) {
        return res.status(400).json("Bad Request");
    }
    if (req.headers.authorization) {
        try {
            const token = req.headers.authorization.split(" ")[1];
            const data = jwt.verify(token, "OriItamarTalKey");
            username = data.username;
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        const id = req.params.id;
        const sent = await chatsService.getOnlyMessages(id, username);
        if (sent === -10) {
            return res.status(404).json("error");
        } else if (!sent) {
            return res.status(401).json("Unauthorized");
        }
        res.send(sent);
    } else {
        return res.status(400).json("Bad Request");
    }
}

const deleteChatById = async (req, res) => {
    var data;
    if (!req.params.id) {
        return res.status(400).json("Bad Request");
    }
    if (req.headers.authorization) {
        try {
            const token = req.headers.authorization.split(" ")[1];
            data = jwt.verify(token, "OriItamarTalKey")
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        const user = data.username;
        const id = req.params.id;
        const sent = await chatsService.deleteChatById(id, user);
        if (sent === -10) {
            return res.status(404).json("error");
        } else if (sent === -1) {
            return res.status(401).json("Unauthorized");
        } else {
            res.send();
        }
    } else {
        return res.status(400).json("Bad Request")
    }
}

function formatDate(dateString) {
    let date = new Date(dateString);
    let day = ("0" + date.getDate()).slice(-2);
    let month = ("0" + (date.getMonth() + 1)).slice(-2);
    let year = date.getFullYear();
    let hours = ("0" + date.getHours()).slice(-2);
    let minutes = ("0" + date.getMinutes()).slice(-2);
    return day + "." + month + "." + year + " " + hours + ":" + minutes;
}

module.exports = {postChats, getChats, sendMessage, getMessagesById, getOnlyMessages, deleteChatById}
