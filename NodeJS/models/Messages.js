const User = require('./Users')
const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const Message = new Schema({
    chatId: {type: String, required: true},
    sender: {type: String, required: true},
    created: {type: Date, required: true},
    content: {type: String, required: true}

})

const model = mongoose.model('Message', Message);

module.exports = {Message, model};













