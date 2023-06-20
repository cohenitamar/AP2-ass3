const registerService = require ('../services/registerServices');


const createUser = async(req,res) =>{

    if(!req.body.username || !req.body.password ||   !req.body.displayName || !req.body.profilePic  ){
        return res.status(400).json("Bad Request")
    }
    let x = await registerService.createUser (req.body.username,req.body.password,
        req.body.displayName,req.body.profilePic);
    if(!x) {
        return res.status(409).json("User already exist.")
    }
    else{
        res.send();
    }
}



module.exports = {createUser}