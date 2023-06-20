const User = require('../models/Users').model;


const createUser = async (username, password, displayName, profilePic) => {

    try {
        const user = new User({
            username: username, password: password, displayName: displayName,
            profilePic: profilePic
        })
        return await user.save();

    } catch (error) {
        return false;
    }
}


module.exports = {createUser}