'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var loadingIcon=document.querySelector('#loading-icon')
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var groupTopic=null;
var status=false;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];
loadingIcon.style.visibility = 'hidden';

function connect(event) {
    username = document.querySelector('#name').value.trim();
    groupTopic = document.querySelector('#groupTopic').value.trim().toLowerCase();

    if(groupTopic.length==0){
        groupTopic="public";
    }




    //api fetch 


    const requestOptions = {
        method: "GET",
        redirect: "follow"
      };
      
      fetch("/chats/"+groupTopic, requestOptions)
      .then((response) => response.json())
      .then((result) => {
          result.forEach((message) => {
              onMessageReceived({ body: JSON.stringify(message) });
          });
      })
      .catch((error) => console.error(error));

      
    
    if(username) {
   
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/server');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    loadingIcon.style.visibility = 'hidden';
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/'+groupTopic, onMessageReceived);
    
    
    
    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN',groupTopic : groupTopic})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    loadingIcon.style.visibility = 'visible';
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
    console.log("error occur    ->"+error.message)
    // alert("connection lost retrying")
    setTimeout(() => {
        connect(event);
    }, 3000);
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            groupTopic : groupTopic
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}




 


function onMessageReceived(payload) {
    console.log(payload);
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    messageElement.addEventListener('click', function() {
        // Your action when a chat message is clicked
        console.log('Clicked on message:', message);
        // Example: Open a modal with the message details
        // openModal(message);
    });

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
