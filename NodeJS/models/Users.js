const mongoose = require('mongoose');

const Schema = mongoose.Schema;
const User = new Schema({
    username: {type: String, required: true, unique: true, index: true},
    password: {type: String, required: true},
    displayName: {type: String, required: true},
    profilePic: {type: String, required: true},
});

const model = mongoose.model('Users', User);

module.exports = {User, model};