//(function() {
  // The width and height of the captured photo. We will set the
  // width to the value defined here, but the height will be
  // calculated based on the aspect ratio of the input stream.

  var width = 320;    // We will scale the photo width to this
  var height = 320;     // This will be computed based on the input stream

  // |streaming| indicates whether or not we're currently streaming
  // video from the camera. Obviously, we start at false.

  var streaming = false;

  // The various HTML elements we need to configure or control. These
  // will be set by the startup() function.

  var video = null;
  var canvas = null;
  var photo = null;
  var startbutton = null;

  function startup() {
    video = document.getElementById('video');
    canvas = document.getElementById('canvas');
    photo = document.getElementById('photo');
    startbutton = document.getElementById('startbutton');
    validatebutton = document.getElementById('startvalidation');

    navigator.mediaDevices.getUserMedia({video: true, audio: false})
    .then(function(stream) {
      video.srcObject = stream;
      video.play();
    })
    .catch(function(err) {
      console.log("An error occurred: " + err);
    });

    video.addEventListener('canplay', function(ev){
      if (!streaming) {
        video.setAttribute('width', width);
        video.setAttribute('height', height);
        canvas.setAttribute('width', width);
        canvas.setAttribute('height', height);
        streaming = true;
      }
    }, false);

    startbutton.addEventListener('click', function(ev){
      takepicture();
      ev.preventDefault();
    }, false);
    
    validatebutton.addEventListener('click', function(ev){
      validatePicture();
      ev.preventDefault();
    }, false);

    clearphoto();
  }

  // Fill the photo with an indication that none has been
  // captured.

  function clearphoto() {
    var context = canvas.getContext('2d');
    context.fillStyle = "#AAA";
    context.fillRect(0, 0, canvas.width, canvas.height);

    var data = canvas.toDataURL('image/png');
    photo.setAttribute('src', data);
  }
  
  // Capture a photo by fetching the current contents of the video
  // and drawing it into a canvas, then converting that to a PNG
  // format data URL. By drawing it on an offscreen canvas and then
  // drawing that to the screen, we can change its size and/or apply
  // other changes before drawing it.

  function takepicture() {
    var context = canvas.getContext('2d');
    if (width && height) {
      canvas.width = width;
      canvas.height = height;
      context.drawImage(video, 0, 0, width, height);
    
      var data = canvas.toDataURL('image/png');
      photo.setAttribute('src', data);
      post_image(data);
    } else {
      clearphoto();
    }
  }

  function validatePicture() {
    var context = canvas.getContext('2d');
    //alert("validate image");
    if (width && height) {
      canvas.width = width;
      canvas.height = height;
      context.drawImage(video, 0, 0, width, height);
    
      var data = canvas.toDataURL('image/png');
      photo.setAttribute('src', data);
      validate_image(data);
    } else {
      clearphoto();
    }
  }

  function post_image(data) {
    console.log(data);
    
    fetch('http://localhost:8080/image', {
      method: 'POST',
      body: data
    })
    .then(result => {
      console.log('Success:', result);
    })
    .catch(error => {
      console.error('Error:', error);
    });
  }

  function validate_image(data) {
    console.log(data);
    
    fetch('http://localhost:8080/validate', {
      method: 'POST',
      body: data
    })
    .then(response => response.json())
    .then(data => {
      result = document.getElementById('result');
      result.innerHTML = "";
      for (var i = 0; i < data.predictions.length; i++) {
        console.log("Tag Name: " + data.predictions[i].tagName + " Probability: " + data.predictions[i].probability);
        result.innerHTML += "Tag Name: " + data.predictions[i].tagName + " Probability: " + data.predictions[i].probability + "<br>";
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
  }
  // Set up our event listener to run the startup process
  // once loading is complete.
  window.addEventListener('load', startup, false);
//})();

function validateForm() {
  let name = document.forms["myForm"]["name"].value;
  let email = document.forms["myForm"]["email"].value;
  if (name == "") {
    alert("Name must be filled out");
  }
  if (email == "") {
    alert("Email must be filled out");
  }

  var context = canvas.getContext('2d');
    if (width && height) {
      canvas.width = width;
      canvas.height = height;
      context.drawImage(video, 0, 0, width, height);
    
      var data = canvas.toDataURL('image/png');
      photo.setAttribute('src', data);
      
      // Post user info and picture
      var jsonObj = {
        name: name,
        email: email,
        image: data
      }

      console.log(jsonObj);

    fetch('http://localhost:8080/form', {
      method: 'POST',
      body: JSON.stringify(jsonObj)
    })
    .then(result => {
      console.log('Success:', result);
    })
    .catch(error => {
      console.error('Error:', error);
    });
    } else {
      clearphoto();
    }

  return false;
}


