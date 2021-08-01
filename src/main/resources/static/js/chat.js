var stompClient = null

$(document).ready(function(){
    connect();
})

$(document).on('click', '.sendButton', function(){
    sendMessage('message')
})

function connect(){

    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (){
        stompClient.subscribe('/topic/group', function(message){
            const value = JSON.parse(message.body);
            console.log(message)
        })
    })

}

function sendMessage(message){
    stompClient.send('/app/send/chat', {}, JSON.stringify(message));
}

