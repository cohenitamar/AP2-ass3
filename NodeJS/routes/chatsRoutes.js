const chatsController = require('../controllers/chatsController');

const express = require('express');
var router = express.Router();

router.route('/')
    .post(chatsController.postChats)
    .get(chatsController.getChats);



router.route('/:id')
    .get(chatsController.getMessagesById)
    .delete(chatsController.deleteChatById);


router.route('/:id/Messages')
    .post(chatsController.sendMessage)
    .get(chatsController.getOnlyMessages);





module.exports = router;