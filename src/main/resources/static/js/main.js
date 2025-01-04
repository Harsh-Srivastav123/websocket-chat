'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var loadingIcon=document.querySelector('#loading-icon')
var connectingElement = document.querySelector('.connecting');

var mediaButton = document.querySelector('#mediaButton');

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

        var socket = new SockJS('https://chatroom.harshbuild.in/server');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

mediaButton.addEventListener('click',
    function(){
        document.getElementById('fileInput').click(); 
    }
)



document.getElementById('fileInput').addEventListener('change', function(event) {
    const file = event.target.files[0];
    var token=generateUUID()
    console.log(token)
    if (file) {
        const formData = new FormData();
        formData.append('media', file);
        formData.append("reference",token );


            if(stompClient) {
                var chatMessage = {
                sender: username,
                content: "CLICK HERE TO SEE MEDIA CONTENT",
                type: 'MEDIA',
                groupTopic : groupTopic,
                referenceId : token
                };
                stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
                messageInput.value = '';
            }

        fetch('/chats/uploadMedia', {
            method: 'POST',
            body: formData,
            redirect: "follow"
        })
        .then(response => {
            if (response.ok) {
                console.log('File uploaded successfully');

                // Handle success, maybe display a message to the user
            } else {
                console.error('Failed to upload file');
                // Handle error, maybe display an error message to the user
            }
        })
        .catch(error => {
            console.error('Error uploading file:', error);
            // Handle error, maybe display an error message to the user
        });
    }
});


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

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);

    if(message.type==='MEDIA'){
    //     var coloredTextElement = document.createElement('span');
    //     coloredTextElement.style.color = 'blue'; // Set the color to red (you can use any color here)

    // coloredTextElement.appendChild(messageText);

       textElement.style.color = 'blue'; 
    }
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;

    messageElement.addEventListener('click', function() {
        // Your action when a chat message is clicked
        console.log('Clicked on message:', message);
        // Example: Open a modal with the message details
        // openModal(message);
        if(message.type=='MEDIA'){
    //         fetch(`)
    // .then(response => response.blob())
    // .then(blob => {
    //     // Create a URL for the blob
    //     const url = URL.createObjectURL(blob);

    //     // Open the media file in a new tab
    //     window.open(url, '_blank');
    // })
    // .catch(error => {
    //     console.error('Error fetching media:', error);
    //     // Handle the error, show an alert or a message to the user
    // });

    var url = window.location.origin + "/chats/download/" + message.referenceId;
    window.open(url, '_blank');


    // window.open("/chats/downloads/"+message.referenceId, '_blank');



    // const requestOptions = {
    //     method: "GET",
    //     redirect: "follow"
    //   };
      
    //   fetch("/chats/download/${message.referenceId}", requestOptions)
    //     .then((response) => response.text())
    //     .then((result) => console.log(result))
    //     .catch((error) => console.error(error));



        }
    });

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


function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'
    .replace(/[xy]/g, function (c) {
        const r = Math.random() * 16 | 0, 
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}
