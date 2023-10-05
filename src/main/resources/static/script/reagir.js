const image = document.getElementById('img');

      // Get the image element


// Add a mouseover event listener to the image
image.addEventListener('mouseover', () => {
    // Change the background color of the body to black
    document.body.style.backgroundColor = 'black';
});

// Add a mouseout event listener to the image to reset the background color
image.addEventListener('mouseout', () => {
    // Change the background color of the body back to white
    document.body.style.backgroundColor = 'white';
});