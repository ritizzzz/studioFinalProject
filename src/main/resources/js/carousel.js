document.querySelector('.leftSlide').addEventListener('click', moveLeft);
document.querySelector('.rightSlide').addEventListener('click', moveRight);


let containers = document.querySelectorAll('.imgContainer');
let currentSlide = 0;

function moveLeft(){
    containers[currentSlide].classList.remove('active');
    if(currentSlide === 0){
        currentSlide = 2;
    }else{
        currentSlide = currentSlide - 1;
    }    
    console.log(currentSlide)
    containers[currentSlide].classList.add('active');    
}


function moveRight(){
    containers[currentSlide].classList.remove('active');
    currentSlide = (currentSlide + 1) % containers.length;
    containers[currentSlide].classList.add('active');    
}