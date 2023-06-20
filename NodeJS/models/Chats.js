const User = require('./Users');
const Message = require('./Messages')
const mongoose = require("mongoose");


const Schema = mongoose.Schema;

const Chat = new Schema({
    users : [{type: String, required: true}],
    messages :[Message.Message]
})



const model = mongoose.model('Chats', Chat);
module.exports = {Chat, model}